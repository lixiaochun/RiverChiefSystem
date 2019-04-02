package workmanage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.util.FileUploadUtil;
import usermanage.service.LogService;
import usermanage.service.UserService;
import workmanage.mapper.PatrolRecordMapper;
import workmanage.service.PatrolRecordService;

@Controller
@RequestMapping({ "/patrolRecordController" })
public class PatrolRecordController {

	@Autowired
	private PatrolRecordService patrolRecordService;

	@Autowired
	private LogService logService;
	@Autowired
	private UserService userService;

	@Autowired
	private PatrolRecordMapper patrolRecordMapper;

	FileUploadUtil uploadinfo = new FileUploadUtil();

	/**
	 * @Title: queryPatrolRecordByPatrolId
	 * @Des: 根据patrolId查巡查记录表
	 * @Params: patrolId,userId,token,pageNo,pageSize
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/10/27
	 */
	@RequestMapping(value = { "/queryPatrolRecord" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryPatrolRecord(HttpServletRequest request) throws Exception {

		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		int check = userService.checkToken(strUserId, token);
		if (check == 1) {
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
			if (request.getParameter("startTime") != null && !(request.getParameter("startTime").toString().equals("undefined"))) {
				Timestamp startPublishTime = Timestamp.valueOf(request.getParameter("startTime") + " 00:00:00");
				map.put("startPublishTime", startPublishTime);
			}
			if (request.getParameter("endTime") != null && !(request.getParameter("endTime").toString().equals("undefined"))) {
				Timestamp endPublishTime = Timestamp.valueOf(request.getParameter("endTime") + " 23:59:59");
				map.put("endPublishTime", endPublishTime);
			}
			String strregionIds = request.getParameter("regionIds");// 树的regionId查询
			if (strregionIds != null && !(strregionIds.equals(""))) {
				String[] regionIds = strregionIds.split(",");
				map.put("regionIds", regionIds);
			}
			// appType=1 不查下属
			// appType=2 查下属
			String appType = request.getParameter("appType") != null ? request.getParameter("appType") : "1";
			map.put("appType", appType);
			map.put("patrolId", request.getParameter("patrolId"));
			map.put("patrolType", request.getParameter("patrolType"));
			map.put("regionId", request.getParameter("regionId"));
			map.put("taskName", request.getParameter("taskName"));
			map.put("riverName", request.getParameter("riverName"));
			map.put("userName", request.getParameter("userName"));
			map.put("userId", strUserId);

			result = patrolRecordService.queryPatrolRecord(map);
			if ((result.get("PatrolRecord") != null)) {
				result.put("result", 1);
				result.put("message", "操作成功");
				logService.addLog(strUserId, "queryPatrolRecordByPatrolId", "根据patrolId查巡查记录表", "select", "info", "操作成功");
			} else {
				result.put("result", 0);
				result.put("message", "操作失败");
				logService.addLog(strUserId, "queryPatrolRecordByPatrolId", "根据patrolId查巡查记录表", "select", "info", "操作失败");
			}
		} else {
			logService.addLog("queryPatrolRecordByPatrolId", "根据patrolId查巡查记录表", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登录后操作");
		}
		return result;

	}

	/**
	 * @Title: deletePatrolRecordByPatrolId
	 * @Des: 根据patrolId删除巡查记录表
	 * @Params: patrolId,userId,token
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/10/27
	 */
	@RequestMapping(value = { "/deletePatrolRecordByPatrolRecordId" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> deletePatrolRecordByPatrolRecordId(HttpServletRequest request) throws Exception {
		int patrolRecordId = Integer.parseInt(request.getParameter("patrolRecordId") != null ? request.getParameter("patrolRecordId") : "");
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		Map<Object, Object> result = new HashMap<Object, Object>();
		// int userId = Integer.parseInt(strUserId);
		int check = userService.checkToken(strUserId, token);
		if (check == 1) {
			result = patrolRecordService.deletePatrolRecordByPatrolId(patrolRecordId);
			if (Integer.parseInt(result.get("result").toString()) == 1) {
				result.put("result", 1);
				result.put("message", "操作成功");
				logService.addLog(strUserId, "deletePatrolRecordByPatrolId", "根据patrolId删除巡查记录表", "delete", "info", "操作成功");
			} else {
				result.put("result", 0);
				result.put("message", "操作失败");
				logService.addLog(strUserId, "deletePatrolRecordByPatrolId", "根据patrolId删除巡查记录表", "delete", "info", "操作失败");
			}
		} else {
			result.put("result", -1);
			result.put("message", "请登录后操作");
			logService.addLog("deletePatrolRecordByPatrolId", "根据patrolId删除巡查记录表", "delete", "info", "请登录后操作");
		}

		return result;

	}

	/**
	 * @Title: insertPatrolRecord
	 * @Des: 添加巡查记录־
	 * @Params: token,userId，patrolId,startTime,endTIme,point,recordDetail
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/10/27
	 */
	@RequestMapping(value = { "/insertPatrolRecord" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> insertPatrolRecord(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String requeststr = "  request:";
		String responstr = "   response:";
		if (check == 1) {
			int userId = Integer.parseInt(strUserId);
			map.put("userId", userId);
			String patrolId = request.getParameter("patrolId") != null ? request.getParameter("patrolId") : "0";
			map.put("patrolId", patrolId);
			// String startTime = request.getParameter("startTime");// 时间戳
			// map.put("startTime", startTime);
			// String totalTime = request.getParameter("totalTime");
			// map.put("totalTime", totalTime);
			// String endTime = request.getParameter("endTime");// 时间戳
			// map.put("endTime", endTime);
			// if (request.getParameter("point") != null) {
			// String point = request.getParameter("point").toString();
			// point = patrolRecordService.stringArrayToString(point);
			// map.put("point", point);
			// }
			String riverId = request.getParameter("riverId") != null ? request.getParameter("riverId") : "";
			map.put("riverId", riverId);
			String riverName = request.getParameter("riverName") != null ? request.getParameter("riverName") : "";
			map.put("riverName", riverName);
			// String recordDetail = request.getParameter("recordDetail");
			// map.put("recordDetail", recordDetail);
			// String totalMileage = request.getParameter("totalMileage");
			// map.put("totalMileage", totalMileage);
			result = patrolRecordService.insertPatrolRecord(map);
			for (Map.Entry<Object, Object> m : map.entrySet()) {
				requeststr += "　　" + m.getKey() + ":" + m.getValue();
			}
			for (Map.Entry<Object, Object> m : result.entrySet()) {
				responstr += "　　" + m.getKey() + ":" + m.getValue();
			}
			logService.addLog(strUserId, "insertPatrolRecord", "添加巡查记录", "insert", "info", result.get("message").toString() + requeststr + responstr);

		} else {
			logService.addLog("insertPatrol", "添加巡查记录", "insert", "info", "请登录后操作" + " userId=" + strUserId + "   token" + token);
			result.put("result", -1);
			result.put("message", "请登录后操作");
		}
		return result;

	}

	/**
	 * @Title: updatePatrolRecord
	 * @Des: 添加巡查记录־
	 * @Params: token,userId，patrolId,startTime,endTIme,point,recordDetail
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/10/27
	 */
	@RequestMapping(value = { "/updatePatrolRecord" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> updatePatrolRecord(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String requeststr = "  request:";
		String responstr = "   response:";
		if (check == 1) {
			String patrolRecordId = request.getParameter("patrolRecordId") != null ? request.getParameter("patrolRecordId") : "0";
			map.put("patrolRecordId", patrolRecordId);
			map.put("startTime", request.getParameter("startTime"));
			map.put("totalTime", request.getParameter("totalTime") != null ? request.getParameter("totalTime") : "0");
			map.put("endTime", request.getParameter("endTime"));// 时间戳
			if (request.getParameter("point") != null) {
				String point = request.getParameter("point").toString();
				point = patrolRecordService.stringArrayToString(point);
				map.put("point", point);
				String savePath = request.getSession().getServletContext().getRealPath("/patrolRecord/point");
				// point输出到文件
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				savePath = savePath + "/" + patrolRecordId + "/" + dateFormat.format(new Date()) + ".txt";
				File file = new File(savePath);
				try (FileOutputStream fop = new FileOutputStream(file)) {
					if (!file.exists()) {
						file.createNewFile();
					}
					byte[] contentInBytes = point.getBytes();
					fop.write(contentInBytes);
					fop.flush();
					fop.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			map.put("riverId", request.getParameter("riverId"));
			map.put("riverName", request.getParameter("riverName"));
			map.put("recordDetail", request.getParameter("recordDetail"));
			map.put("totalMileage", request.getParameter("totalMileage") != null ? request.getParameter("totalMileage") : "0");
			result = patrolRecordService.updatePatrolRecord(map);
			for (Map.Entry<Object, Object> m : map.entrySet()) {
				requeststr += "　　" + m.getKey() + ":" + m.getValue();
			}
			for (Map.Entry<Object, Object> m : result.entrySet()) {
				responstr += "　　" + m.getKey() + ":" + m.getValue();
			}
			logService.addLog(strUserId, "updatePatrolRecord", "更新巡查记录", "update", "info", result.get("message").toString() + requeststr + responstr);
		} else {
			logService.addLog("updatePatrolRecord", "更新巡查记录", "update", "info", "请登录后操作" + " userId=" + strUserId + "   token" + token);
			result.put("result", -1);
			result.put("message", "请登录后操作");
		}
		return result;

	}

	/**
	 * @Title: queryPointFromPatrolRecord
	 * @Des: 查询巡查记录点
	 * @Params: patrolRecordId,userId,token
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/11/21
	 */
	@RequestMapping(value = { "/queryPointFromPatrolRecord" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryPointFromPatrolRecord(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String patrolRecordId = request.getParameter("patrolRecordId");
			result = patrolRecordService.queryPointFromPatrolRecord(patrolRecordId);
			logService.addLog(strUserId, "queryPointFromPatrolRecord", "查询巡查记录点", "select", "info", result.get("message").toString());
		} else {
			logService.addLog("queryPointFromPatrolRecord", "查询巡查记录点", "select", "info", "fail");
			result.put("result", -1);
			result.put("message", "请登录后操作");
		}
		return result;
	}

	/**
	 * @Title: statisticalMileageAndTime
	 * @Des: 统计巡河里程和巡河时长
	 * @Params: patrolUserId,token,userId
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/12/01
	 */
	@RequestMapping(value = { "/statisticalMileageAndTime" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> statisticalMileageAndTime(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String patrolPeriod = request.getParameter("patrolPeriod") != null ? request.getParameter("patrolPeriod") : "5";
			map.put("userId", strUserId);
			map.put("patrolPeriod", patrolPeriod);// patrolPeriod（每周 6/每月5）
			result = patrolRecordService.statisticalMileageAndTime(map);
			logService.addLog("" + strUserId, "statisticalMileageAndTime", "统计巡河里程巡河时长", "select", "info", result.get("message").toString());
		} else {
			result.put("result", -1);
			result.put("message", "请登录后操作");
			logService.addLog(strUserId, "statisticalMileageAndTime", "统计巡河里程巡河时长", "select", "info", "请登录后操作     userId=" + strUserId + "    token=" + token);
		}
		return result;
	}

	/**
	 * @Title: exportPatrolRecord
	 * @Des: 导出巡查日志
	 * @Params: token,userId
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/12/01
	 */
	@RequestMapping(value = { "/exportPatrolRecord" }, method = { RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> exportPatrolRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(strUserId);
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			// 路径
			String path = "/assets/patrolRecord/";
			String savePath = request.getSession().getServletContext().getRealPath(path);
			uploadinfo.mkfiledir(savePath);

			String month = request.getParameter("month");
			map.put("month", month);
			map.put("appType", request.getParameter("appType"));
			map.put("userId", strUserId);
			map.put("path", savePath);
			result = patrolRecordService.exportPatrolRecord(map, response);
			logService.addLog(strUserId, "exportPatrolRecord", "导出巡查日志", "select", "info", result.get("message").toString());
		} else {
			result.put("result", -1);
			result.put("message", "请登录后操作");
			logService.addLog(strUserId, "exportPatrolRecord", "导出巡查日志", "select", "info", "请登录后操作");
		}
		return result;
	}

	/**
	 * @Title: deletePatrolRecord
	 * @Des: 删除垃圾日志
	 * @Params: token,userId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2018/03/25
	 */
	@RequestMapping(value = { "/deletePatrolRecord" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> deletePatrolRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(strUserId);
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			map.put("userId", strUserId);
			result = patrolRecordService.deletePatrolRecord(map);
			logService.addLog(strUserId, "exportPatrolRecord", "删除垃圾日志", "delete", "info", result.get("message").toString());
		} else {
			result.put("result", -1);
			result.put("message", "请登录后操作");
			logService.addLog(strUserId, "exportPatrolRecord", "删除垃圾日志", "delete", "info", "请登录后操作");
		}
		return result;
	}

	/**
	 * @Title: exportPatrolRecordByUser
	 * @Des: 福鼎导出河道专管员的巡河日志
	 * @Params: token,userId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2018/04/27
	 */
	@RequestMapping(value = { "/exportPatrolRecordByUser" }, method = { RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> exportPatrolRecordByUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String regionId = request.getParameter("regionId");
			map.put("regionId", regionId);
			String date = request.getParameter("date");
			map.put("date", date);
			result = patrolRecordService.exportPatrolRecordByUser(map, request, response);
			logService.addLog(struserId, "exportPatrolRecordByUser", "福鼎导出河道专管员的巡河日志", "select", "info", result.get("message").toString());
		} else {
			logService.addLog("exportPatrolRecordByUser", "福鼎导出河道专管员的巡河日志", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		return result;
	}

	/**
	 * @Title: staticalPatrolRecordByUser
	 * @Des: 泉州巡查日志统计
	 * @Params: token,userId
	 * @Return: map
	 * @Author: cxy
	 * @Date: 2017/12/01
	 */
	@RequestMapping(value = { "/staticalPatrolRecordByUser" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> staticalPatrolRecordByUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			map.put("userId", strUserId);
			result = patrolRecordService.staticalPatrolRecordByUser(map);
			logService.addLog(strUserId, "staticalPatrolRecordByUser", "泉州巡查日志统计", "select", "info", result.get("message").toString());
		} else {
			result.put("result", -1);
			result.put("message", "请登录后操作");
			logService.addLog(strUserId, "staticalPatrolRecordByUser", "泉州巡查日志统计", "select", "info", "请登录后操作");
		}
		return result;
	}
}
