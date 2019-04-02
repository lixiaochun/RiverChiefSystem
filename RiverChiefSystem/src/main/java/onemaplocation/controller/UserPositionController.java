 package onemaplocation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import onemaplocation.mapper.OnemapUserInformation;
import onemaplocation.model.UserPathGeojson;
import onemaplocation.model.UserPositionGeojson;
import onemaplocation.redis.RedisUtil;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping({ "/userPositionController" })
public class UserPositionController {
	
	@Autowired
	private OnemapUserInformation onemapUserInformation;
	
	private static int ExpiredTime = 10*60;//数据过期时间(秒)
		
	/**
	 * 接收app推送的实时位置
	 * userId：用户id，point：位置  
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/receiveUserPosition" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> receiveUserPosition(HttpServletRequest request,
			HttpServletResponse response) {		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		// 处理参数
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String point = request.getParameter("point").equals("undefined") ? "0,0" : request.getParameter("point");
		System.out.println("userId:"+userId+"   point:"+point);
		// 当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateTime = df.format(new Date());
		//获取连接
		Jedis jedis = RedisUtil.getJedis();
		//判断位置是否改变
		String userPosition = jedis.hget("UserPosition", userId) == null ? "" : jedis.hget("UserPosition", userId);
		System.out.println("curUserPosition:"+userPosition);
		if(userPosition.equals("") || !userPosition.contains(point)) {		
			// 更新用户最新位置信息
			jedis.hset("UserPosition", userId, point);
			//设置键值过期时间
			jedis.setex("UserPosition:" + userId, ExpiredTime, "KeyExpired");
			//System.out.println("用户：" + jedis.hget("UserPosition", userId));
			// 更新用户最新位置时间
			jedis.hset("UserPositionUpdateTime", userId, updateTime);
			//System.out.println("用户位置更新时间：" + jedis.hget("UserPositionUpdateTime", userId));
			// 存储用户轨迹
			String userPath = jedis.hget("UserPath", userId) == null ? "" : jedis.hget("UserPath", userId);
			jedis.hset("UserPath", userId, userPath + "[" + point + "];");
			//System.out.println("用户当前轨迹：" + jedis.hget("UserPath", userId));	
		}
		// 关闭连接
		jedis.close();
		map.put("result", "1");
		return map;
	}

	/**
	 * 巡河结束，清除位置信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/flushUserPosition" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> flushUserPosition(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		// 处理参数
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		//获取连接
		Jedis jedis = RedisUtil.getJedis();
		System.out.println(userId + "巡河结束");
		jedis.hdel("UserPosition", userId);
		jedis.hdel("UserPath", userId);
		jedis.hdel("UserPositionUpdateTime", userId);
		// 关闭连接
		jedis.close();
		map.put("result", "1");
		return map;
	}

	/**
	 * 查询所有巡河员的当前位置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryUserPosition" }, method = {
			RequestMethod.GET }, produces = "application/octet-stream; charset=utf-8")
	public void queryUserPosition(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition",
					"attachment;filename=" + URLEncoder.encode("UserPosition.geojson", "UTF-8"));
			// 设置允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			//获取连接
			Jedis jedis = RedisUtil.getJedis();
			Map<String, String> userPosition = jedis.hgetAll("UserPosition");
			UserPositionGeojson userPositionGeojson = new UserPositionGeojson(userPosition);
			// 关闭连接
			jedis.close();
			Gson gson = new Gson();
			PrintWriter pw = response.getWriter();
			pw.write(gson.toJson(userPositionGeojson));

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 查询所有巡河员的当前位置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryUserPositionByList" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryUserPositionByList(HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		//获取连接
		Jedis jedis = RedisUtil.getJedis();
		Map<String, String> userPosition = jedis.hgetAll("UserPosition");
		for (Entry<String, String> entry : userPosition.entrySet()) {
			Map<Object, Object> temp = new HashMap<>();
			String userId = entry.getKey();
			String point = entry.getValue();
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			double x = Double.valueOf(point.split(",")[0]);
			double y = Double.valueOf(point.split(",")[1]);
			temp.put("id", userId);
			temp.put("x", x);
			temp.put("y", y);
			result.add(temp);
		}
		map.put("count", result.size());
		map.put("items", result);
		map.put("result", 1);
		map.put("message", "查询所有巡河员的当前位置");
		// 关闭连接
		jedis.close();

		return map;

	}
	
	/**
	 * 查询所有巡河员的轨迹分布
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryUserPath" }, method = {
			RequestMethod.GET }, produces = "application/octet-stream; charset=utf-8")
	public void queryUserPath(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition",
					"attachment;filename=" + URLEncoder.encode("UserPath.geojson", "UTF-8"));
			// 设置允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			//获取连接
			Jedis jedis = RedisUtil.getJedis();
			Map<String, String> userPath = jedis.hgetAll("UserPath");
			UserPathGeojson userPathGeojson = new UserPathGeojson(userPath);
			// 关闭连接
			jedis.close();
			Gson gson = new Gson();
			PrintWriter pw = response.getWriter();
			pw.write(gson.toJson(userPathGeojson));

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 查询巡河员信息和当前巡河轨迹
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryUserInformationAndPath" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryUserInformationAndPath(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		// 处理参数
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		//获取连接
		Jedis jedis = RedisUtil.getJedis();
		String userPath = jedis.hget("UserPath", userId) == null ? "" : jedis.hget("UserPath", userId);
		List<Double[]> pathList = new ArrayList<>();
		String points[] = userPath.split(";");
		for (String point : points) {
			point=point.substring(1, point.length()-1);
			pathList.add(new Double[] {Double.valueOf(point.split(",")[0]),Double.valueOf(point.split(",")[1])}); 
		
		}
		// 关闭连接
		jedis.close();
		
		Map<Object, Object>  userInformation = onemapUserInformation.selectUserInformation(Integer.valueOf(userId));
		String real_name = userInformation.get("real_name") != null?(String) userInformation.get("real_name"):"";
		String phone = userInformation.get("phone") != null?(String) userInformation.get("phone"):"0";
		String region_name = userInformation.get("region_name") != null?(String) userInformation.get("region_name"):"";
		String organization_name = userInformation.get("organization_name") != null?(String) userInformation.get("organization_name"):"";
		map.put("real_name", real_name);
		map.put("phone", phone);
		map.put("region_name", region_name);
		map.put("organization_name", organization_name);
		map.put("path", pathList);
		List<Map<Object, Object>> riverList = onemapUserInformation.selectUserRelationRiver(Integer.valueOf(userId));
		String riverName ="";
		List<String> riverNameList =new ArrayList<>();
		for (Map<Object, Object> item : riverList) {
			riverName += item.get("river_name")+",";
			riverNameList.add((String) item.get("river_name"));
		}
		if(!riverName.equals(""))
			riverName = riverName.substring(0, riverName.length()-1);
		map.put("river_list", riverNameList);	
		map.put("river_name", riverName);
		return map;
	}

}
