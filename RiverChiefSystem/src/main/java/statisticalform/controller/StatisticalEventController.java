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

import statisticalform.service.StatisticalEventService;
import usermanage.service.LogService;
import usermanage.service.UserService;

@Controller
@RequestMapping({ "/statisticalEventController" })
public class StatisticalEventController {

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	@Autowired
	private StatisticalEventService statisticalEventService;

	/**
	 * @Title: statisticsEvent
	 * @Des: 事件统计查询
	 * @Params: userId, token,regionId[,]
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/statisticsEvent" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> statisticsEvent(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String sregionIds = request.getParameter("regionIds");
			String dateType = request.getParameter("dateType");
			String[] strregionIds = sregionIds.split(",");
			List<String> regionIds = Arrays.asList(strregionIds);
			map.put("regionIds", regionIds);
			map.put("dateType", dateType);
			result = statisticalEventService.statisticsEvent(map);
			logService.addLog(struserId, "statisticsEvent", "事件统计查询", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsEvent", "事件统计查询", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: statisticsProblemType
	 * @Des: 事件类型统计
	 * @Params: userId, token,regionId,dateType
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/statisticsProblemType" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> statisticsProblemType(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String sregionIds = request.getParameter("regionIds");
			String strriverIds = request.getParameter("riverIds");
			String dateType = request.getParameter("dateType");
			String regionId = request.getParameter("regionId");
			if (sregionIds != null && !(sregionIds.equals(""))) {
				String[] strregionIds = sregionIds.split(",");
				List<String> regionIds = Arrays.asList(strregionIds);
				map.put("regionIds", regionIds);
			}
			if (strriverIds != null && !(strriverIds.equals(""))) {
				String[] strriverId = strriverIds.split(",");
				List<String> riverIds = Arrays.asList(strriverId);
				map.put("riverIds", riverIds);
			}
			map.put("regionId", regionId);
			map.put("dateType", dateType);
			result = statisticalEventService.statisticsProblemType(map);
			logService.addLog(struserId, "statisticsProblemType", "事件统计查询", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsProblemType", "事件类型统计", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: statisticsEventCount
	 * @Des: 事件类型统计
	 * @Params: userId, token,regionId,dateType
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/statisticsEventCount" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> statisticsEventCount(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String sregionIds = request.getParameter("regionIds");
			String dateType = request.getParameter("dateType");
			String[] strregionIds = sregionIds.split(",");
			List<String> regionIds = Arrays.asList(strregionIds);
			map.put("regionIds", regionIds);
			map.put("dateType", dateType);
			result = statisticalEventService.statisticsEventCount(map);
			logService.addLog(struserId, "statisticsEventCount", "事件类型统计", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsEventCount", "事件类型统计", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: everyEventCount
	 * @Des: 每个月事件总数统计
	 * @Params: userId, token,regionId,dateType
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/everyEventCount" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> everyEventCount(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String sregionIds = request.getParameter("regionIds");
			String dateType = request.getParameter("dateType");
			String[] strregionIds = sregionIds.split(",");
			List<String> regionIds = Arrays.asList(strregionIds);
			map.put("regionIds", regionIds);
			map.put("dateType", dateType);
			result = statisticalEventService.everyEventCount(map);
			logService.addLog(struserId, "statisticsEventCount", "事件类型统计", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsEventCount", "事件类型统计", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: statisticalReportType
	 * @Des: 事件来源统计
	 * @Params: userId, token,regionId,dateType
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/statisticalReportType" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> statisticalReportType(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String sregionIds = request.getParameter("regionIds");
			String dateType = request.getParameter("dateType");
			String[] strregionIds = sregionIds.split(",");
			List<String> regionIds = Arrays.asList(strregionIds);
			map.put("regionIds", regionIds);
			map.put("dateType", dateType);
			result = statisticalEventService.statisticalReportType(map);
			logService.addLog(struserId, "statisticalReportType", "事件来源统计", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("statisticalReportType", "事件来源统计", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: statisticalRegion
	 * @Des: 地区事件统计
	 * @Params: userId, token,regionId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/statisticalRegion" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> statisticalRegion(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String regionId = request.getParameter("regionId");
			map.put("regionId", regionId);
			result = statisticalEventService.statisticalRegion(map);
			logService.addLog(struserId, "statisticalReportType", "地区事件统计", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("statisticalReportType", "地区事件统计", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: publicComplaints
	 * @Des: 公众投诉热力图分析
	 * @Params: userId, token,regionId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/publicComplaints" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> publicComplaints(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String regionId = request.getParameter("regionId");
			map.put("regionId", regionId);
			result = statisticalEventService.publicComplaints(map);
			logService.addLog(struserId, "publicComplaints", "公众投诉热力图分析", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("publicComplaints", "公众投诉热力图分析", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: statisticsProblemTypes
	 * @Des: 事件类型统计
	 * @Params: userId, token,regionId,dateType
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/statisticsProblemTypes" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> statisticsProblemTypes(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			map.put("userId", struserId);
			result = statisticalEventService.statisticsProblemTypes(map);
			logService.addLog(struserId, "statisticsProblemTypes", "事件统计查询", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsProblemTypes", "事件类型统计", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: statisticsEventType
	 * @Des: 三查事件类型统计查询
	 * @Params: userId, token,regionId[,]
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/statisticsEventType" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> statisticsEventType(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String regionId = request.getParameter("regionId");
			// String dateType = request.getParameter("dateType");
			map.put("regionIds", regionId);
			// map.put("dateType", dateType);
			result = statisticalEventService.statisticsEventType(map);
			logService.addLog(struserId, "statisticsEventType", "三查事件类型统计查询", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsEventType", "三查事件类型统计查询", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: statisticsHome
	 * @Des: 统计首页-》事件问题类型，巡河次数行政区
	 * @Params: userId, token,regionId[,]
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/statisticsHome" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> statisticsHome(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String sregionIds = request.getParameter("regionIds");
			String strriverIds = request.getParameter("riverIds");
			String dateType = request.getParameter("dateType");
			String regionId = request.getParameter("regionId");
			if (sregionIds != null && !(sregionIds.equals(""))) {
				String[] strregionIds = sregionIds.split(",");
				List<String> regionIds = Arrays.asList(strregionIds);
				map.put("regionIds", regionIds);
			}
			if (strriverIds != null && !(strriverIds.equals(""))) {
				String[] strriverId = strriverIds.split(",");
				List<String> riverIds = Arrays.asList(strriverId);
				map.put("riverIds", riverIds);
			}
			map.put("regionId", regionId);
			map.put("dateType", dateType);
			result = statisticalEventService.statisticsHome(map);
			logService.addLog(struserId, "statisticsHome", "统计首页", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("statisticsHome", "统计首页", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
}
