package onemap.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import onemap.service.OnemapEventService;
import onemap.service.WaterQualityService;
import onemap.util.IdUitl;
import usermanage.service.UserService;

@Controller
@RequestMapping({ "/statisticsController" })
public class StatisticsController {
	
	@Autowired
	private WaterQualityService waterQualityService;
	
	@Autowired
	private OnemapEventService onemapEventService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 水质统计
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getStatisticsInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getBasicInformation(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		String type = request.getParameter("type");
		String regionId = request.getParameter("value");
		regionId=IdUitl.getRegionId(regionId);
		parameter.put("regionId", regionId);
		
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String value = request.getParameter(paraName);
			parameter.put(paraName, value);
			System.out.println(paraName + ": " + request.getParameter(paraName));
		}	
		
		map=waterQualityService.getStatisticsInformation(parameter);
	
		return map;
	}

	/** 
	 * 事件统计，统计各行政区事件数据量
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getEventStatisticsInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getEventStatisticsInformation(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		String code="";
		if(request.getParameter("code")!=null) {
			code = request.getParameter("code");
		}else {
			map.put("result", 0);
			map.put("message", "参数错误");
			return map;
		}
		parameter.put("reportTpye", code.length()==3?code.substring(2, 3):"");
		
		map = onemapEventService.getEventStatisticsInformation(parameter);
		map.put("result", 1);
		map.put("message", "操作成功");
		return map;
	}
	
}
