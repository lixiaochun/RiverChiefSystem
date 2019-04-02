package workmanage.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.model.Patrol;
import usermanage.service.LogService;
import usermanage.service.UserService;
import workmanage.service.PatrolRecordService;
import workmanage.service.PatrolService;

/**
 * 综合门户
 *
 */
@Controller
@RequestMapping({ "/patrolController" })
public class PatrolController {

	@Autowired
	private PatrolService patrolService;
	@Autowired
	private PatrolRecordService patrolRecordService;
	@Autowired
	private LogService logService;
	@Autowired
	private UserService userService;

	/**
	 * @Title: queryPatrol
	 * @Des: 根据userId查任务以及下属任务־
	 * @Params: regionId,riverName,userID,token,pageNo,pageSize,appType,taskName,userName,patrolType,startPublishTime,endPublishTime
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/10/27
	 */
	@RequestMapping(value = { "/queryPatrol" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryPatrol(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";

		// int userId = Integer.parseInt(strUserId);
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			int userId = Integer.parseInt(strUserId);
			int pageNo = 0;
			if (request.getParameter("pageNo") != null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));// 当前页
			}
			if (request.getParameter("pageSize") != null) {
				int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页行数
				int min = pageSize * (pageNo - 1);
				map.put("pageSize", pageSize);
				map.put("min", min);
			}
			map.put("userId", userId);

			// 根据树行政区查找
			String strregionIds = request.getParameter("regionIds");// 树的regionId查询
			if (strregionIds != null && !(strregionIds.equals(""))) {
				String[] regionIds = strregionIds.split(",");
				map.put("regionIds", regionIds);
			}
			String regionId = request.getParameter("regionId");// 查询下级的任务
			map.put("regionId", regionId);
			// appType=1 不查下属
			// appType=2 查下属
			int appType = Integer.parseInt(request.getParameter("appType") != null ? request.getParameter("appType") : "1");
			map.put("appType", appType);
			String taskName = request.getParameter("taskName");
			map.put("taskName", taskName);
			String userName = request.getParameter("userName");
			map.put("userName", userName);
			String riverName = request.getParameter("riverName");
			map.put("riverName", riverName);
			if (request.getParameter("patrolType") != null) {
				int patrolType = Integer.parseInt(request.getParameter("patrolType"));
				map.put("patrolType", patrolType);
			}
			if (request.getParameter("startPublishTime") != null && !(request.getParameter("startPublishTime").toString().equals("undefined"))) {
				Timestamp startPublishTime = Timestamp.valueOf(request.getParameter("startPublishTime") + " 00:00:00");
				map.put("startPublishTime", startPublishTime);
			}
			if (request.getParameter("endPublishTime") != null && !(request.getParameter("endPublishTime").toString().equals("undefined"))) {
				Timestamp endPublishTime = Timestamp.valueOf(request.getParameter("endPublishTime") + " 23:59:59");
				map.put("endPublishTime", endPublishTime);
			}
			result = patrolService.queryPatrol(map);
			if ((result.get("Patrol") != null)) {
				result.put("result", 1);
				result.put("message", "查询成功");
				logService.addLog(strUserId, "queryPatrol", "根据userId查任务以及下属任务", "select", "info", "查询成功");
			} else {
				result.put("result", 0);
				result.put("message", "查询失败");
				logService.addLog(strUserId, "queryPatrol", "根据userId查任务以及下属任务", "select", "info", "查询失败");
			}
		} else {
			logService.addLog("queryPatrol", "根据userId查任务以及下属任务", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}

		return result;
	}

	/**
	 * @Title: insertPatrol
	 * @Des: 添加巡查任务־
	 * @Params: patrolUserId,token,patrolType,patrolId,userId,regionId,riverId,patrolNum,completeTime,point,opuserId,patorlStatus,taskName,path,patrolPeriod,grade,nowGrade,remark
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/10/27
	 */
	@RequestMapping(value = { "/insertPatrol" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> insertPatrol(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			map.put("userId", strUserId);
			String patrolUserId = request.getParameter("patrolUserId");// 巡查人
			String riverId = request.getParameter("riverId");
			String taskName = request.getParameter("taskName");// 任务名
			String patrolPeriod = request.getParameter("patrolPeriod");
			String patrolNum = request.getParameter("patrolNum") != null ? request.getParameter("patrolNum") : "1";
			String patrolType = request.getParameter("patrolType") != null ? request.getParameter("patrolType") : "0";
			if (request.getParameter("point") != null) {
				String point = patrolRecordService.stringArrayToString(request.getParameter("point").toString());// 巡查点
				map.put("point", point);
			}
			map.put("patrolType", patrolType);
			map.put("riverId", riverId);
			map.put("taskName", taskName);
			map.put("patrolUserId", patrolUserId);
			map.put("patrolPeriod", patrolPeriod);
			map.put("patrolNum", patrolNum);
			result = patrolService.insertPatrol(map);
			logService.addLog(strUserId, "insertPatrol", "添加巡查任务", "insert", "info", result.get("message").toString());
		} else {
			logService.addLog("insertPatrol", "添加巡查任务", "insert", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		return result;

	}

	/**
	 * @Title: updatePatrol
	 * @Des: 更新巡查任务
	 * @Params: token,patrolId,userId,point,patorlStatus
	 * @Return: void
	 * @Author: cxy
	 * @Date: 2017/10/27
	 */
	@RequestMapping(value = { "/updatePatrol" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> updatePatrol(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId =Integer.parseInt (strUserId);
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			int userId = Integer.parseInt(strUserId);
			int patrolStatus = Integer.parseInt(request.getParameter("patrolStatus") != null ? request.getParameter("patrolStatus") : "");
			int patrolId = Integer.parseInt(request.getParameter("patrolId") != null ? request.getParameter("patrolId") : "");
			int point = Integer.parseInt(request.getParameter("point") != null ? request.getParameter("point") : "");
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("patrolId", patrolId);
			map.put("userId", userId);
			map.put("patrolStatus", patrolStatus);
			map.put("point", point);
			result = patrolService.updatePatrol(map);
			if (Integer.parseInt(result.get("result").toString()) == 1) {
				result.put("result", 1);
				result.put("message", "操作成功");
				logService.addLog("" + userId, "updatePatrol", "更新巡查任务", "update", "info", "操作成功");
			} else {
				result.put("result", 0);
				result.put("message", "操作失败");
				logService.addLog("" + userId, "updatePatrol", "更新巡查任务", "update", "info", "操作失败");
			}
		} else {
			result.put("result", -1);
			result.put("message", "请登陆后操作");
			logService.addLog("updatePatrol", "更新巡查任务", "update", "info", "请登陆后操作");
		}

		return result;

	}

	/**
	 * @Title: deletePatrol
	 * @Des: 删除巡查任务
	 * @Params: patrolId,userId,token
	 * @Return: int
	 * @Author: cxy
	 * @Date: 2017/10/27
	 */
	@RequestMapping(value = { "/deletePatrol" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> deletePatrol(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId =Integer.parseInt (strUserId);
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			int userId = Integer.parseInt(strUserId);
			int patrolId = Integer.parseInt(request.getParameter("patrolId") != null ? request.getParameter("patrolId") : "");
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("patrolId", patrolId);
			map.put("userId", userId);
			result = patrolService.deletePatrol(patrolId);
			if (Integer.parseInt(result.get("result").toString()) == 1) {
				result.put("result", 1);
				result.put("message", "操作成功");
				logService.addLog("" + userId, "deletePatrol", "删除巡查任务", "delete", "info", "操作成功");
			} else {
				result.put("result", 0);
				result.put("message", "操作失败");
				logService.addLog("" + userId, "deletePatrol", "删除巡查任务", "delete", "info", "操作失败");
			}
		} else {
			result.put("result", -1);
			result.put("message", "请登陆后操作");
			logService.addLog("deletePatrol", "删除巡查任务", "delete", "info", "请登陆后操作");
		}

		return result;

	}

	/**
	 * @Title: queryPointFromPatrol
	 * @Des: 查询巡查任务点
	 * @Params: patrolId,userId,token
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/11/21
	 */
	@RequestMapping(value = { "/queryPointFromPatrol" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryPointFromPatrol(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId =Integer.parseInt (strUserId);
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			int userId = Integer.parseInt(strUserId);
			if (request.getParameter("patrolId") != null) {
				int patrolId = Integer.parseInt(request.getParameter("patrolId"));
				result = patrolService.queryPointFromPatrol(patrolId);
				List<Patrol> list = (List<Patrol>) result.get("Patrol");
				String point = list.get(0).getPoint();
				result.put("point", patrolRecordService.stringToStringArray(point));
				if ((result.get("point") != null)) {
					result.put("result", 1);
					result.put("message", "操作成功");
					logService.addLog("" + userId, "queryPointFromPatrol", "查询巡查任务点", "select", "info", "操作成功");
				} else {
					result.put("result", 0);
					result.put("message", "操作失败");
					logService.addLog("" + userId, "queryPointFromPatrol", "查询巡查任务点", "select", "info", "操作失败");
				}
			} else {
				logService.addLog("queryPointFromPatrol", "查询巡查任务点", "select", "info", "操作失败");
				result.put("result", 0);
				result.put("message", "操作失败");
			}

		} else {
			logService.addLog("queryPointFromPatrol", "查询巡查任务点", "select", "info", "请登陆后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}

		return result;
	}

	/**
	 * @Title: queryEventByPatrol
	 * @Des: 查询事件列表
	 * @Params: userId,
	 *          token,reportType,regionId,startTime,endTime,userName,riverName,pageNo,pageSize,eventLevel
	 *          事件管理高级查询
	 * @Params: userId,
	 *          token,reportType,eventLevel,startTime,endTime,eventType,pageNo,pageSize
	 *          巡查事件 公众投诉高级查询
	 * @Params: userId,
	 *          token,eventName,riverName,startTime,endTime,userName,pageNo,pageSize
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/queryEventByPatrol" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryEventByPatrol(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			int userId = Integer.parseInt(struserId);
			int pageNo = 0;
			if (request.getParameter("pageNo") != null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));// 当前页
			}
			if (request.getParameter("pageSize") != null) {
				int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页行数
				int min = pageSize * (pageNo - 1);
				map.put("pageSize", pageSize);
				map.put("min", min);
			}
			map.put("userId", userId);
			result = patrolService.queryEventByPatrol(map);
			logService.addLog(struserId, "queryEventByPatrol", "查询事件列表", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("queryEventByPatrol", "查询事件列表", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: countPatrol
	 * @Des: 查询事件列表
	 * @Params: userId,
	 *          token,reportType,regionId,startTime,endTime,userName,riverName,pageNo,pageSize,eventLevel
	 *          事件管理高级查询
	 * @Params: userId,
	 *          token,reportType,eventLevel,startTime,endTime,eventType,pageNo,pageSize
	 *          巡查事件 公众投诉高级查询
	 * @Params: userId,
	 *          token,eventName,riverName,startTime,endTime,userName,pageNo,pageSize
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/countPatrol" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> countPatrol(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			int userId = Integer.parseInt(struserId);
			map.put("appType", 1);
			map.put("userId", userId);
			result = patrolService.countPatrol(map);
			logService.addLog(struserId, "countPatrol", "查询任务列表", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("countPatrol", "查询任务列表", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
}
