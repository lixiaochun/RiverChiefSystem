package statisticalform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.model.Statistics;
import statisticalform.service.StatisticalEventService;
import statisticalform.service.StatisticalExcelService;
import statisticalform.service.statisticalPatrolService;
import usermanage.service.LogService;
import usermanage.service.UserService;

@Controller
@RequestMapping("/statistic/excel")
public class StatisticalExcelController {
	@Autowired
	private LogService logService;

	@Autowired
	private UserService userService;

	@Autowired
	private StatisticalEventService statisticalEventService;

	@Autowired
	private statisticalPatrolService staticPatrolService;

	@Autowired
	private StatisticalExcelService statisticalExcelService;

	@RequestMapping(value = "/statisticsProblemTypeExcel")
	@ResponseBody
	public Map<Object, Object> statisticsProblemTypeExcel(HttpServletRequest request, HttpServletResponse response) {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			result = statisticalEventService.statisticsProblemTypes(map);
			logService.addLog(struserId, "statisticsProblemTypes", "事件统计查询", "select", "info", result.get("message").toString());
			/* 执行下载 */
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> problemTypeList = (List<Map<String, Object>>) result.get("problemTypeList");
			if (problemTypeList.size() > 0) {
				boolean isDownload = statisticalExcelService.ExportProblemTypeExcel(request, response, problemTypeList);
				result.put("isDownload", isDownload);
			} else {
				result.put("message", "该用户没有数据");
			}
		} else {
			logService.addLog("statisticsProblemTypes", "事件类型统计", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		return result;
	}

	@RequestMapping(value = "/statisticsPatrolExcel")
	@ResponseBody
	public Map<Object, Object> statisticsPatrolExcel(HttpServletRequest request, HttpServletResponse response) {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String regionId = request.getParameter("regionId");
			String dateType = request.getParameter("dateType");
			map.put("regionId", regionId);
			map.put("dateType", dateType);
			result = staticPatrolService.statisticsPatrol(map);
			logService.addLog(struserId, "statisticsRiver", "河流统计查询", "select", "info", result.get("message").toString());
			@SuppressWarnings("unchecked")
			List<Statistics> patrolRecordList = (List<Statistics>) result.get("patrolRecord");
			if (patrolRecordList.size() > 0) {
				boolean isDownload = statisticalExcelService.ExportPatrolExcel(request, response, patrolRecordList);
				result.put("isDownload", isDownload);
			} else {
				result.put("message", "该用户没有数据");
			}

		} else {
			logService.addLog("statisticsRiver", "河流统计查询", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		return result;
	}

	@RequestMapping(value = "/statisticsEventTypeExcel")
	@ResponseBody
	public Map<Object, Object> statisticsEventTypeExcel(HttpServletRequest request, HttpServletResponse response) {

		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String regionId = request.getParameter("regionId");
			map.put("regionIds", regionId);
			result = statisticalEventService.statisticsEventType(map);
			logService.addLog(struserId, "statisticsEventType", "三查事件类型统计查询", "select", "info", result.get("message").toString());

			/* 执行下载 */
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> eventTypeList = (List<Map<String, Object>>) result.get("eventTypeList");
			if (eventTypeList.size() > 0) {
				boolean isDownload = statisticalExcelService.ExportEventTypeExcel(request, response, eventTypeList);
				result.put("isDownload", isDownload);
			} else {
				result.put("message", "该用户没有数据");
			}
		} else {
			logService.addLog("statisticsEventType", "三查事件类型统计查询", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		return result;
	}
}
