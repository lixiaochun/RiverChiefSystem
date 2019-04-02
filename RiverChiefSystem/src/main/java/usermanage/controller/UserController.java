package usermanage.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import common.model.User;
import usermanage.service.LogService;
import usermanage.service.UserService;

@Controller
@RequestMapping({"/userController"})
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * @Title: queryUser
	 * @Des: 查找对应的人员
	 * @Params: userId, token, riverId, regionId, roleType
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/17
	 */
	@RequestMapping(value = { "/queryUser" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String riverId = request.getParameter("riverId");
			String organizationId = request.getParameter("organizationId");
			String roleType = request.getParameter("roleType") != null ? request.getParameter("roleType") : "0";
			// type=0 巡河人员 type=1 请求上级
			String type = request.getParameter("type") != null ? request.getParameter("type") : "0";
			map.put("type", type);
			// map.put("riverId", riverId);//泉州版不传riverId
			map.put("roleType", roleType);
			// 拆分角色代码
			String rolehead = roleType.substring(0, 1);
			String roletail = roleType.substring(1, 2);
			map.put("rolehead", rolehead);
			map.put("roletail", roletail);
			map.put("organizationId", organizationId);
			map.put("userId", struserId);
			result = userService.queryUserRoleType(map);
			logService.addLog(struserId, "queryRole", "查询角色列表", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("queryRole", "查询角色列表", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	* @Title: getWeatherInform
	* @Des: 获取天气信息
	* @Params: userId, token, riverId, regionId, roleType
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/17
	*/
	@RequestMapping(value = {"/getWeatherInform"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  getWeatherInform(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String baiduUrl = "http://www.weather.com.cn/";  
            StringBuffer strBuf;  
//            try {                              
//                //通过浏览器直接访问http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ  
//                //5slgyqGDENN7Sy7pw29IUvrZ 是我自己申请的一个AK(许可码)，如果访问不了，可以自己去申请一个新的ak  
//                //百度ak申请地址：http://lbsyun.baidu.com/apiconsole/key  
//                //要访问的地址URL，通过URLEncoder.encode()函数对于中文进行转码                              
//                baiduUrl = "http://api.map.baidu.com/telematics/v3/weather?location="+URLEncoder.encode(cityName, "utf-8")+"&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";                    
//            } catch (Exception e1) {               
//                e1.printStackTrace();                     
//            }  
  
            strBuf = new StringBuffer();  
                  
            try{  
                URL url = new URL(baiduUrl);  
                URLConnection conn = url.openConnection();  
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。  
                String line = null;  
                while ((line = reader.readLine()) != null)  
                    strBuf.append(line + " ");  
                    reader.close();  
            }catch(MalformedURLException e) {  
                e.printStackTrace();   
            }catch(IOException e){  
                e.printStackTrace();   
            }     
            System.out.println(strBuf.toString());
//            return strBuf.toString(); 
//			logService.addLog(struserId,"getWeatherInform", "查询角色列表", "select", "info",result.get("message").toString());
			return result;
		}
//		logService.addLog("getWeatherInform", "查询角色列表", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	@RequestMapping(value = {"/userRegister"}, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object>  userRegister(HttpServletRequest request,HttpServletResponse response){
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
		int check = userService.checkToken(struserId, token);
		Map<String, Object> map = new HashMap<String,Object>();
		if(check != 1){
			map.put("result", -1);
			map.put("message", "请登陆后操作");
			return map;
		}
		try {
			String userStr = request.getParameter("user");
			User user = JSON.parseObject(userStr, User.class);
			String message = userService.register(user);
			if(message.equals("success")){
				map.put("result", 1);
				map.put("message", "注册成功");
			}else{
				map.put("result", 0);
				map.put("message", message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", -1);
			map.put("message", e.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value = {"/userSelectedOne"}, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object>  userSelectedOne(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String,Object>();
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
		int check = userService.checkToken(struserId, token);
		if(check != 1){
			map.put("result", -1);
			map.put("message", "请登陆后操作");
			return map;
		}
		try {
			String userID = request.getParameter("userIdS");
			User user  = userService.showSelectedOne(userID);
			map.put("result", "1");
			map.put("message", "查询成功");
			map.put("user", user);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "0");
			map.put("message", "查询失败");
		}
		return map;
	}
	
	@RequestMapping(value = {"/userModify"}, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object>  userModify(HttpServletRequest request,HttpServletResponse response) {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
		int check = userService.checkToken(struserId, token);
		Map<String, Object> map = new HashMap<String,Object>();
		if(check != 1){
			logService.addLog("updateUser", "修改用户", "select", "info","请登录后操作");
			map.put("result", -1);
			map.put("message", "请登陆后操作");
			return map;
		}
		try {
			String userStr = request.getParameter("user");
			User user = JSON.parseObject(userStr, User.class);
			String message = userService.userModify(user);
			if(message.equals("success")){
				map.put("result", 1);
				map.put("message", "修改成功");
			}else{
				map.put("result", 0);
				map.put("message", message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", -1);
			map.put("message", e.getMessage());
		}
		return map;
	}
	
	
	@RequestMapping(value = {"/userDelete"}, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object>  userDelete(HttpServletRequest request,HttpServletResponse response) {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
		int check = userService.checkToken(struserId, token);
		Map<String, Object> map = new HashMap<String,Object>();
		if(check != 1){
			logService.addLog("updateUser", "修改用户", "select", "info","请登录后操作");
			map.put("result", -1);
			map.put("message", "请登陆后操作");
			return map;
		}
		try {
			String userIdS = request.getParameter("userIdS");
			@SuppressWarnings("unused")
			String message = userService.userDelete(userIdS);
			map.put("result", 1);
			map.put("message", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", -1);
			map.put("message", e.getMessage());
		}
		return map;
	}
}
