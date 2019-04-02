package statisticalform.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import statisticalform.service.statisticalPatrolService;
import usermanage.service.LogService;
import usermanage.service.UserService;

@Controller
@RequestMapping({"/statisticalPatrolController"})
public class StatisticalPatrolController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private statisticalPatrolService staticPatrolService;
	
	/**
	* @Title: statisticsRiver
	* @Des: 河流统计查询
	* @Params: userId, token,regionId
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	*/
	@RequestMapping(value = {"/statisticsRiver"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  statisticsRiver(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
//		int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String regionId = request.getParameter("regionId");
			map.put("regionId", regionId);
			result = staticPatrolService.statisticsRiver(map);
			if(Integer.parseInt(result.get("result").toString())==1) {
				logService.addLog(struserId,"statisticsRiver", "河流统计查询", "select", "info","success");
			}else {
				logService.addLog(struserId,"statisticsRiver", "河流统计查询", "select", "info","统计查询失败");
			}
			return result;
		}
		logService.addLog("statisticsRiver", "河流统计查询", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	* @Title: statisticsLogin
	* @Des: 河长app活跃度
	* @Params: userId, token,regionId
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	*/
	@RequestMapping(value = {"/statisticsLogin"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  statisticsLogin(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
//		int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String regionId = request.getParameter("regionId");
			String dateType = request.getParameter("dateType");
			map.put("regionId", regionId);
			map.put("dateType", dateType);
			result = staticPatrolService.statisticsLogin(map);
			if(Integer.parseInt(result.get("result").toString())==1) {
				logService.addLog(struserId,"statisticsRiver", "河流统计查询", "select", "info","success");
			}else {
				logService.addLog(struserId,"statisticsRiver", "河流统计查询", "select", "info","统计查询失败");
			}
			return result;
		}
		logService.addLog("statisticsRiver", "河流统计查询", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	* @Title: statisticsNotLogin
	* @Des: 河长app使用统计
	* @Params: userId, token,regionId
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	*/
	@RequestMapping(value = {"/statisticsNotLogin"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  statisticsNotLogin(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
//		int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String regionId = request.getParameter("regionId");
			String dateType = request.getParameter("dateType");
			map.put("regionId", regionId);
			map.put("dateType", dateType);
			result = staticPatrolService.statisticsNotLogin(map);
			if(Integer.parseInt(result.get("result").toString())==1) {
				logService.addLog(struserId,"statisticsRiver", "河流统计查询", "select", "info","success");
			}else {
				logService.addLog(struserId,"statisticsRiver", "河流统计查询", "select", "info","统计查询失败");
			}
			return result;
		}
		logService.addLog("statisticsRiver", "河流统计查询", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	* @Title: statisticsPatrol
	* @Des: 巡查频次达标情况
	* @Params: userId, token,regionId
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	*/
	@RequestMapping(value = {"/statisticsPatrol"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  statisticsPatrol(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
//		int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String regionId = request.getParameter("regionId");
			String dateType = request.getParameter("dateType");
			map.put("regionId", regionId);
			map.put("dateType", dateType);
			result = staticPatrolService.statisticsPatrol(map);
			logService.addLog(struserId,"statisticsRiver", "河流统计查询", "select", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsRiver", "河流统计查询", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	* @Title: statisticsPatrolRecord
	* @Des: 巡河次数
	* @Params: userId, token,regionId
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	*/
	@RequestMapping(value = {"/statisticsPatrolRecord"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  statisticsPatrolRecord(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
//		int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String regionId = request.getParameter("regionId");
			map.put("regionId", regionId);
			
			String strriverIds = request.getParameter("riverIds");
			if (strriverIds != null && !(strriverIds.equals(""))) {
				String[] strriverId = strriverIds.split(",");
				List<String> riverIds = Arrays.asList(strriverId);
				map.put("riverIds", riverIds);
			}
			result = staticPatrolService.statisticsPatrolRecord(map);
			logService.addLog(struserId,"statisticsPatrolRecord", "巡河次数", "select", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsPatrolRecord", "巡河次数", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	* @Title: statisticsPublicsigns
	* @Des: 统计公示牌
	* @Params: userId, token,regionIds,riverIds
	* @Return: map
	* @Author: lyf
	* @Date: 2018/04/18
	*/
	@RequestMapping(value = {"/statisticsPublicsigns"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  statisticsPublicsigns(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
//		int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String regionId = request.getParameter("regionId");
			map.put("regionId", regionId);
			
			String strriverIds = request.getParameter("riverIds");
			if (strriverIds != null && !(strriverIds.equals(""))) {
				String[] strriverId = strriverIds.split(",");
				List<String> riverIds = Arrays.asList(strriverId);
				map.put("riverIds", riverIds);
			}
			result = staticPatrolService.statisticsPublicsigns(map);
			logService.addLog(struserId,"statisticsPublicsigns", "统计公示牌", "select", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsPublicsigns", "统计公示牌", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
}
