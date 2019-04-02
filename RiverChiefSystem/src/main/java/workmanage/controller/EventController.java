package workmanage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.model.WorkflowLog;
import common.util.Constant;
import common.util.FileUploadUtil;
import usermanage.service.LogService;
import usermanage.service.UserService;
import workmanage.service.EventService;
import workmanage.service.PatrolRecordService;

@Controller
@RequestMapping({ "/eventController" })
public class EventController {

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	@Autowired
	private EventService eventService;

	@Autowired
	private PatrolRecordService patrolRecordService;

	private static final Log logger = LogFactory.getLog("workmanage");

	FileUploadUtil uploadinfo = new FileUploadUtil();

	/**
	 * @Title: newEvent
	 * @Des: 新建事件
	 * @Params: userId(必填), token(必填),
	 *          eventName,eventCode,eventLevel(必填),eventContent,beforeimg,recordPoint,
	 *          address,riverId(必填),regionId(必填),problemType(必填),limitTime,rectification
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/newEvent" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> newEvent(HttpServletRequest request) throws Exception {

		String path = "/event/beforeimg";
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> uploadinfomation = uploadinfo.fileuploaduilfuc(request, path);
		if ((Integer) uploadinfomation.get("result") == 1) {
			Map<Object, Object> info = (Map<Object, Object>) uploadinfomation.get("info");
			String struserId = info.get("userId") != null ? info.get("userId").toString() : "0";
			String token = info.get("token") != null ? info.get("token").toString() : "";
			int check = userService.checkToken(struserId, token);
			if (check == 1) {
				int userId = Integer.parseInt(struserId);
				String reportType = (String) (info.get("reportType") != null ? info.get("reportType") : "5");
				String type = (String) (info.get("type") != null ? info.get("type") : "1");// 是否现场处理 1:是 0:否
				String eventName = (String) (info.get("eventName") != null ? info.get("eventName") : "");
				String eventCode = (String) (info.get("eventCode") != null ? info.get("eventCode") : "");
				String eventTime = (String) (info.get("eventTime") != null ? info.get("eventTime") : "");
				if (eventTime.equals("")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					eventTime = sdf.format(new Date());
				} else {
					String streventTime = eventTime + " 00:00:00";
					eventTime = streventTime;
				}
				if (eventCode.equals("")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					String dateString = sdf.format(new Date());
					int r = (int) (Math.random() * 90 + 10);
					eventCode = dateString + r;
				}
				String limitTime = (String) (info.get("limitTime") != null ? info.get("limitTime") : "");
				if (!(limitTime.equals(""))) {
					String strlimitTime = limitTime + " 59:59:59";
					limitTime = strlimitTime;
					map.put("limitTime", limitTime);
				}
				String eventLevel = (String) (info.get("eventLevel") != null ? info.get("eventLevel") : "1");
				String eventContent = (String) (info.get("eventContent") != null ? info.get("eventContent") : "");
				String eventPoint = (String) (info.get("eventPoint") != null ? info.get("eventPoint") : "");
				if (!(eventPoint.equals(""))) {
					eventPoint = patrolRecordService.stringArrayToString(eventPoint);
				}
				String patrolRecordId = (String) (info.get("patrolRecordId") != null ? info.get("patrolRecordId") : "0");
				String address = (String) (info.get("address") != null ? info.get("address") : "");
				String riverId = (String) (info.get("riverId") != null ? info.get("riverId") : "");
				String regionId = (String) (info.get("regionId") != null ? info.get("regionId") : "");
				String problemType = (String) (info.get("problemType") != null ? info.get("problemType") : "");
				String rectification = (String) (info.get("rectification") != null ? info.get("rectification") : "");
				String remark = (String) (request.getParameter("remark") != null ? request.getParameter("remark") : "");
				String solve = (String) (info.get("solve") != null ? info.get("solve") : "0");
				// String reportUserId = (String) (info.get("reportUserId") != null ?
				// info.get("reportUserId") : "0");
				// String phone = (String) (request.getParameter("phone") != null ?
				// request.getParameter("phone") : "");
				map.put("patrolRecordId", patrolRecordId);
				map.put("userId", userId);
				// map.put("submitUserId", userId);// 提交人
				// map.put("userId", reportUserId);// 上报人
				map.put("reportType", reportType);
				map.put("eventName", eventName);
				map.put("eventCode", eventCode);
				map.put("eventLevel", eventLevel);
				map.put("eventContent", eventContent);
				map.put("eventPoint", eventPoint);
				map.put("address", address);
				map.put("riverId", riverId);
				map.put("regionId", regionId);
				map.put("problemType", problemType);
				map.put("rectification", rectification);
				map.put("remark", remark);
				map.put("eventTime", eventTime);
				map.put("eventStatus", 1);
				map.put("solve", solve);
				// if((reportType.equals("1")&&phone=="")||reportUserId.equals("0")) {
				// result.put("result", 0);
				// result.put("message", "参数错误");
				// logService.addLog(struserId, "newEvent", "新建事件", "insert", "info",
				// result.get("message").toString());
				// return result;
				// }
				if (info.get("type") != null && info.get("type").toString().equals("1")) {// type=1新建事件启动工作流
					result = eventService.addEven(map);
				} else {
					result = eventService.newEven(map);
				}
				logService.addLog(struserId, "newEvent", "新建事件", "insert", "info", result.get("message").toString());
			} else {
				logService.addLog("newEvent", "新建事件", "insert", "info", "请登录后操作");
				result.put("result", -1);
				result.put("message", "请登陆后操作 userId=" + struserId + "   token" + token);
			}
		} else {
			logService.addLog("newEvent", "新建事件", "insert", "info", "文件上传失败");
			result.put("result", 0);
			result.put("message", "文件上传失败");
		}

		return result;
	}

	/**
	 * @Title: newEventByImg
	 * @Des: 新建事件
	 * @Params: userId(必填), token(必填),
	 *          eventName,eventCode,eventLevel(必填),eventContent,beforeimg,recordPoint,
	 *          address,riverId(必填),regionId(必填),problemType(必填),limitTime,rectification
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/newEventByImg" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> newEventByImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String target = "/event/beforeimg";
		String requeststr = "  request:";
		String responstr = "   response:";
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> query = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";

		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			int userId = Integer.parseInt(struserId);
			String beforeimgurls = (String) (request.getParameter("base64Image") != null ? request.getParameter("base64Image") : "");
			List<Map<Object, Object>> imagelist = new ArrayList<Map<Object, Object>>();
			if (!(beforeimgurls.equals("")) && !(beforeimgurls.equals("undefined"))) {
				String[] beforeimgurl = beforeimgurls.split(";;");
				for (int i = 0; i < beforeimgurl.length; i++) {
					String image = beforeimgurl[i];
					image = image.replaceAll("data:image/jpeg;base64,", "");
					image = image.replaceAll(" ", "+");
					System.out.println("base64 =   " + image);
					result = uploadinfo.uploadfileBase64(request, image, target);
					imagelist.add(result);
				}
			}
			String reportType = (String) (request.getParameter("reportType") != null ? request.getParameter("reportType") : "5");
			String patrolRecordId = (String) (request.getParameter("patrolRecordId") != null ? request.getParameter("patrolRecordId") : "0");
			String eventName = (String) (request.getParameter("eventName") != null ? request.getParameter("eventName") : "");
			String eventCode = (String) (request.getParameter("eventCode") != null ? request.getParameter("eventCode") : "");
			String eventTime = (String) (request.getParameter("eventTime") != null ? request.getParameter("eventTime") : "");
			if (eventTime.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = sdf.format(new Date());
				eventTime = dateString;
			} else {
				String streventTime = eventTime + " 00:00:00";
				eventTime = streventTime;
			}
			if (eventCode.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String dateString = sdf.format(new Date());
				eventCode = dateString;
			}
			String limitTime = (String) (request.getParameter("limitTime") != null ? request.getParameter("limitTime") : "");
			if (!(limitTime.equals(""))) {
				String strlimitTime = limitTime + " 59:59:59";
				limitTime = strlimitTime;
				map.put("limitTime", limitTime);
			}
			String eventLevel = (String) (request.getParameter("eventLevel") != null ? request.getParameter("eventLevel") : "1");
			String eventContent = (String) (request.getParameter("eventContent") != null ? request.getParameter("eventContent") : "");
			if (eventContent.equals("")) {
				eventContent = (String) (request.getParameter("problemName") != null ? request.getParameter("problemName") : "");
			}
			String eventPoint = (String) (request.getParameter("eventPoint") != null ? request.getParameter("eventPoint") : "");
			if (!(eventPoint.equals(""))) {
				eventPoint = patrolRecordService.stringArrayToString(eventPoint);
			}
			String address = (String) (request.getParameter("address") != null ? request.getParameter("address") : "");
			String riverId = (String) (request.getParameter("riverId") != null ? request.getParameter("riverId") : "");
			String regionId = (String) (request.getParameter("regionId") != null ? request.getParameter("regionId") : "");
			String problemType = (String) (request.getParameter("problemType") != null ? request.getParameter("problemType") : "");
			String rectification = (String) (request.getParameter("rectification") != null ? request.getParameter("rectification") : "");
			String remark = (String) (request.getParameter("remark") != null ? request.getParameter("remark") : "");
			String solve = (String) (request.getParameter("solve") != null ? request.getParameter("solve") : "0");
			map.put("patrolRecordId", patrolRecordId);
			map.put("userId", userId);
			map.put("reportType", reportType);
			map.put("eventName", eventName);
			map.put("eventCode", eventCode);
			map.put("eventLevel", eventLevel);
			map.put("eventContent", eventContent);
			map.put("eventPoint", eventPoint);
			map.put("address", address);
			map.put("riverId", riverId);
			map.put("regionId", regionId);
			map.put("problemType", problemType);
			map.put("rectification", rectification);
			map.put("eventTime", eventTime);
			map.put("remark", remark);
			map.put("eventStatus", 0);
			map.put("solve", solve);
			result = eventService.addEven(map);
			for (Map.Entry<Object, Object> m : map.entrySet()) {
				requeststr += "　　" + m.getKey() + ":" + m.getValue();
			}
			for (Map.Entry<Object, Object> m : result.entrySet()) {
				responstr += "　　" + m.getKey() + ":" + m.getValue();
			}
			if (result.get("result") != null && result.get("result").toString().equals("1") && imagelist.size() > 0) {
				query.put("fileinfo", imagelist);
				query.put("eventId", result.get("eventId") != null ? result.get("eventId") : "0");
				query.put("workflowLogId", result.get("workflowLogId") != null ? result.get("workflowLogId") : "0");
				query.put("eventStatus", 0);
				result = eventService.updateEventByImg(query);
			}
			logService.addLog(struserId, "newEventByImg", "新建事件", "insert", "info", result.get("message").toString() + requeststr + responstr);
		} else {
			logService.addLog("newEventByImg", "新建事件", "insert", "info", "请登陆后操作 userId=" + struserId + "   token" + token);
			result.put("result", -1);
			result.put("message", "请登陆后操作 userId=" + struserId + "   token" + token);
		}

		return result;
	}

	/**
	 * @Title: queryEvent
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
	@RequestMapping(value = { "/queryEvent" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryEvent(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
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
			String eventLevel = request.getParameter("eventLevel");
			map.put("eventLevel", eventLevel);
			String regionId = request.getParameter("regionId");// 用户的regionId
			map.put("regionId", regionId);
			String strregionIds = request.getParameter("regionIds");// 树的regionId查询
			if (strregionIds != null && !(strregionIds.equals(""))) {
				String[] regionIds = strregionIds.split(",");
				map.put("regionIds", regionIds);
			}
			String type = request.getParameter("type") != null ? request.getParameter("type") : "0";
			map.put("type", type);
			String reportType = request.getParameter("reportType");
			map.put("reportType", reportType);
			String eventCode = request.getParameter("eventCode");
			map.put("eventCode", eventCode);
			String eventName = request.getParameter("eventName");
			map.put("eventName", eventName);
			String riverName = request.getParameter("riverName");
			map.put("riverName", riverName);
			String userName = request.getParameter("userName");
			map.put("userName", userName);
			String problemType = request.getParameter("problemType");
			if (problemType != null && (!problemType.equals("")) && !(problemType.equals("undefined"))) {
				problemType = problemType.substring(0, 2);
				map.put("problemType", problemType);
			}
			String riverId = request.getParameter("riverId");// 用户的riverId
			map.put("riverId", riverId);
			String patrolRecordId = request.getParameter("patrolRecordId");// 用户的riverId
			map.put("patrolRecordId", patrolRecordId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (request.getParameter("startTime") != null && !(request.getParameter("startTime").toString().equals("undefined"))) {
				String strstartTime = request.getParameter("startTime");
				strstartTime = strstartTime + " 00:00:00";
				Date startTime = sdf.parse(strstartTime);
				map.put("startTime", startTime);
			}
			if (request.getParameter("endTime") != null && !(request.getParameter("endTime").toString().equals("undefined"))) {
				String strendTime = request.getParameter("endTime");
				strendTime = strendTime + " 23:59:59";
				Date endTime = sdf.parse(strendTime);
				map.put("endTime", endTime);
			}
			result = eventService.queryEvent(map);
			logger.info(result);
			logService.addLog(struserId, "queryEvent", "查询事件列表", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("queryEvent", "查询事件列表", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: queryWorkflowLog
	 * @Des: 查询事件详情
	 * @Params: userId, token,eventId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/queryWorkflowLog" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryWorkflowLog(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String eventId = request.getParameter("eventId");
			map.put("eventId", eventId);
			result = eventService.queryEventDetail(map);
			logService.addLog(struserId, "queryWorkflowLog", "查询事件详情", "select", "info", result.get("message").toString());
		} else {
			logService.addLog("queryWorkflowLog", "查询事件列表", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		return result;
	}

	/**
	 * @Title: claimEvent
	 * @Des: 拾取事件
	 * @Params: userId, token, roleId, eventId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/claimEvent" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> claimEvent(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			int userId = Integer.parseInt(struserId);
			String eventId = request.getParameter("eventId");
			map.put("eventId", eventId);
			map.put("userId", userId);
			result = eventService.claimEvent(map);
			logService.addLog(struserId, "acceptEvent", "处理事件", "select", "info", result.get("message").toString());
		} else {
			logService.addLog("acceptEvent", "处理事件", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		return result;
	}

	/**
	 * @Title: solveEvent
	 * @Des: 处理事件
	 * @Params: userId, token, nextUserId, eventId, solve,
	 *          content,limitTime(时限要求),rectification(整改方案),visitDays(回访天数),imgurl,content
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/solveEvent" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> solveEvent(HttpServletRequest request) throws Exception {
		String path = "/event/afterimg";
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> uploadinfomation = uploadinfo.fileuploaduilfuc(request, path);
		if ((Integer) uploadinfomation.get("result") == 1) {
			Map<Object, Object> info = (Map<Object, Object>) uploadinfomation.get("info");
			String struserId = info.get("userId") != null ? info.get("userId").toString() : "0";
			String token = info.get("token") != null ? info.get("token").toString() : "";
			// int userId = Integer.parseInt(struserId);
			int check = userService.checkToken(struserId, token);
			if (check == 1) {
				int userId = Integer.parseInt(struserId);
				String eventId = (String) (info.get("eventId") != null ? info.get("eventId") : "");
				map.put("eventId", eventId);
				map.put("solve", (String) (info.get("solve") != null ? info.get("solve") : ""));
				map.put("userId", userId);
				map.put("nextUserId", (String) (info.get("nextUserId") != null ? info.get("nextUserId") : "0"));
				String limitTime = (String) (info.get("limitTime") != null ? info.get("limitTime") : "");
				if (!(limitTime.equals(""))) {
					String strlimitTime = limitTime + " 59:59:59";
					limitTime = strlimitTime;
					map.put("limitTime", limitTime);
				}
				map.put("rectification", (String) (info.get("rectification") != null ? info.get("rectification") : ""));
				map.put("visitDays", (String) (info.get("visitDays") != null ? info.get("visitDays") : ""));
				map.put("content", (String) (info.get("content") != null ? info.get("content") : ""));
				map.put("eventStatus", 1);
				result = eventService.solveEvent(map);
				logService.addLog(struserId, "solveEvent", "处理事件", "select", "info", result.get("message").toString());
			} else {
				logService.addLog("solveEvent", "处理事件", "select", "info", "fail，请登录后操作");
				result.put("result", 0);
				result.put("message", "请操作后登录");
			}
		} else {
			logService.addLog("solveEvent", "处理事件", "insert", "info", "文件上传失败");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		return result;
	}

	/**
	 * @Title: solveEventByImg
	 * @Des: 处理事件
	 * @Params: userId, token, nextUserId, eventId, solve,
	 *          content,limitTime(时限要求),rectification(整改方案),visitDays(回访天数),imgurl,content
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/solveEventByImg" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> solveEventByImg(HttpServletRequest request) throws Exception {
		String target = "/event/afterimg";
		// String path = "D:\\Program Files\\nginx-1.13.6\\html\\RiverChiefSystem" +
		// target;
		// String path = request.getSession().getServletContext().getRealPath(target);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> query = new HashMap<Object, Object>();
		Map<Object, Object> resultmap = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";

		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			int userId = Integer.parseInt(struserId);
			String eventId = (String) (request.getParameter("eventId") != null ? request.getParameter("eventId") : "");
			map.put("eventId", eventId);
			String afterimg = (String) (request.getParameter("base64Image") != null ? request.getParameter("base64Image") : "");
			List<Map<Object, Object>> imagelist = new ArrayList<Map<Object, Object>>();
			if (!(afterimg.equals("")) && !(afterimg.equals("undefined"))) {
				String[] afterimgurl = afterimg.split(";;");
				for (int i = 0; i < afterimgurl.length; i++) {
					String image = afterimgurl[i];
					image = image.replaceAll("data:image/jpeg;base64,", "");
					image = image.replaceAll(" ", "+");
					System.out.println("base64 =   " + image);
					result = uploadinfo.uploadfileBase64(request, image, target);
					imagelist.add(result);
				}
			}
			map.put("solve", (String) (request.getParameter("solve") != null ? request.getParameter("solve") : ""));
			map.put("userId", userId);
			if ((request.getParameter("nextUserId") == null || request.getParameter("nextUserId").equals(""))&&!(request.getParameter("solve").equals("40"))) {
				resultmap = eventService.queryEventDetail(map);
				if (resultmap.get("workflowLog") == null || ((List<WorkflowLog>) resultmap.get("workflowLog")).size() == 0) {
					resultmap.put("result", 0);
					resultmap.put("message", "无可分配的人员");
					return resultmap;
				} else {
					List<WorkflowLog> wfList = (List<WorkflowLog>) resultmap.get("workflowLog");
					Integer nextUserId = wfList.get(0).getUserId();
					map.put("nextUserId", nextUserId);
				}
			} else {
				map.put("nextUserId", request.getParameter("nextUserId"));
			}
			if((request.getParameter("solve").equals("40"))) {
				map.put("eventStatus", 0);
				map.put("nextUserId", 0);
			}
			String limitTime = (String) (request.getParameter("limitTime") != null ? request.getParameter("limitTime") : "");
			if (!(limitTime.equals(""))) {
				String strlimitTime = limitTime + " 59:59:59";
				limitTime = strlimitTime;
				map.put("limitTime", limitTime);
			}
			map.put("rectification", (String) (request.getParameter("rectification") != null ? request.getParameter("rectification") : ""));
			map.put("visitDays", (String) (request.getParameter("visitDays") != null ? request.getParameter("visitDays") : ""));
			map.put("content", (String) (request.getParameter("content") != null ? request.getParameter("content") : ""));
			result = eventService.solveEvent(map);
			if (result.get("result") != null && result.get("result").toString().equals("1") && !(afterimg.equals("")) && !(afterimg.equals("undefined"))) {
				query.put("fileinfo", imagelist);
				query.put("workflowLogId", result.get("workflowLogId") != null ? result.get("workflowLogId") : "0");
				query.put("eventId", result.get("eventId") != null ? result.get("eventId") : "0");
				query.put("eventStatus", 1);
				result = eventService.updateEventByImg(query);
			}
			logService.addLog(struserId, "solveEventByImg", "处理事件", "select", "info", result.get("message").toString());
		} else {
			logService.addLog("solveEventByImg", "处理事件", "select", "info", "fail，请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		return result;
	}

	/**
	 * @Title: updateEvent
	 * @Des: 修改事件
	 * @Params: userId, token,eventId,points[{},{},{}...],eventName
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/updateEvent" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> updateEvent(HttpServletRequest request) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			String eventId = (String) (request.getParameter("eventId") != null ? request.getParameter("eventId") : "");
			String eventStatus = (String) (request.getParameter("eventStatus") != null ? request.getParameter("eventStatus") : "1");
			map.put("eventId", eventId);
			map.put("eventStatus", eventStatus);
			result = eventService.updateEventByImg(map);
			logService.addLog(struserId, "updateEvent", "修改事件", "update", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("updateEvent", "修改事件", "update", "info", "fail，请登录后操作");
		result.put("result", -1);
		// result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: updateEventByImg
	 * @Des: 上传事件文件
	 * @Params: userId, token,eventId,points[{},{},{}...],eventName
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/updateEventByImg" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> updateEventByImg(HttpServletRequest request) throws Exception {
		String path = "/event/beforeimg";
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> uploadinfomation = uploadinfo.fileuploaduilfuc(request, path);
		if ((Integer) uploadinfomation.get("result") == 1) {
			Map<Object, Object> info = (Map<Object, Object>) uploadinfomation.get("info");
			String struserId = info.get("userId") != null ? info.get("userId").toString() : "0";
			String token = info.get("token") != null ? info.get("token").toString() : "";
			int check = userService.checkToken(struserId, token);
			if (check == 1) {
				String eventId = (String) (info.get("eventId") != null ? info.get("eventId") : "0");
				String workflowLogId = (String) (info.get("workflowLogId") != null ? info.get("workflowLogId") : "0");
				String eventStatus = (String) (info.get("eventStatus") != null ? info.get("eventStatus") : "1");
				map.put("eventId", eventId);
				map.put("workflowLogId", workflowLogId);
				map.put("eventStatus", eventStatus);
				map.put("fileinfo", uploadinfomation.get("fileinfo"));
				result = eventService.updateEventByImg(map);
				logService.addLog(struserId, "updateEventByImg", "上传事件文件", "update", "info", result.get("message").toString());
				return result;
			} else {
				logService.addLog("updateEventByImg", "上传事件文件", "select", "info", "请登录后操作" + " userId=" + struserId + "   token" + token);
				result.put("result", -1);
				result.put("message", "请登陆后操作");
			}
		}
		logService.addLog("updateEventByImg", "上传事件文件", "update", "info", "fail，请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: updateEventByFTPImg->updateEventByAppImg
	 * @Des: 通过FTP上传事件文件
	 * @Params: userId, token,eventId,points[{},{},{}...],eventName
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/updateEventByFTPImg" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody int updateEventByFTPImg(HttpServletRequest request) throws Exception {
		String path = "/event/beforeimg";
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> uploadinfomation = uploadinfo.fileuploaduilfuc(request, path);
		if ((Integer) uploadinfomation.get("result") == 1) {
			Map<Object, Object> info = (Map<Object, Object>) uploadinfomation.get("info");
			String struserId = info.get("userId") != null ? info.get("userId").toString() : "0";
			String token = info.get("token") != null ? info.get("token").toString() : "";
			int check = userService.checkToken(struserId, token);
			if (check == 1) {
				String eventId = (String) (info.get("eventId") != null ? info.get("eventId") : "0");
				String workflowLogId = (String) (info.get("workflowLogId") != null ? info.get("workflowLogId") : "0");
				String eventStatus = (String) (info.get("eventStatus") != null ? info.get("eventStatus") : "1");
				String imageTime = (String) (info.get("imageTime") != null ? info.get("imageTime") : "");
				if (imageTime.equals("")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					imageTime = sdf.format(new Date());
					map.put("imageTime", imageTime);
				}
				map.put("eventId", eventId);
				map.put("workflowLogId", workflowLogId);
				map.put("eventStatus", eventStatus);
				map.put("fileinfo", uploadinfomation.get("fileinfo"));

				result = eventService.updateEventByImg(map);
				logService.addLog(struserId, "updateEventByFTPImg", "上传事件文件", "update", "info", result.get("message").toString());
				return Integer.parseInt(result.get("result").toString());
			} else {
				logService.addLog("updateEventByFTPImg", "上传事件文件", "select", "info", "请登录后操作" + " userId=" + struserId + "   token" + token);
				// result.put("result", -1);
				// result.put("message", "请登陆后操作");
			}
		}
		logService.addLog("updateEventByFTPImg", "上传事件文件", "update", "info", "fail，请登录后操作");
		// result.put("result", -1);
		// result.put("message", "请登陆后操作");
		return -1;
	}

	/**
	 * @Title: deleteEventByImg
	 * @Des: 删除事件图片
	 * @Params: userId, token,eventId,points[{},{},{}...],eventName
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/deleteEventByImg" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> deleteEventByImg(HttpServletRequest request) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			String eventId = (String) (request.getParameter("eventId") != null ? request.getParameter("eventId") : "");
			String eventStatus = (String) (request.getParameter("eventStatus") != null ? request.getParameter("eventStatus") : "1");
			map.put("eventId", eventId);
			map.put("eventStatus", eventStatus);
			result = eventService.deleteEventByImg(map);
			logService.addLog(struserId, "deleteEventByImg", "删除事件图片", "update", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("deleteEventByImg", "删除事件图片", "update", "info", "fail，请登录后操作");
		result.put("result", -1);
		// result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: deleteEvent
	 * @Des: 删除事件
	 * @Params: userId, token,eventId,points[{},{},{}...],eventName
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/deleteEvent" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> deleteEvent(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String eventId = (String) (request.getParameter("eventId") != null ? request.getParameter("eventId") : "0");
			map.put("eventId", eventId);
			String eventStatus = (String) (request.getParameter("eventStatus") != null ? request.getParameter("eventStatus") : "0");
			map.put("eventStatus", eventStatus);
			result = eventService.updateEvent(map);
			logService.addLog(struserId, "deleteEvent", "统计事件", "delete", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("deleteEvent", "统计事件", "delete", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: countEvent
	 * @Des: 统计事件
	 * @Params: userId, token,regionId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/countEvent" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> countEvent(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String regionId = request.getParameter("regionId");
			map.put("regionId", regionId);
			int userId = Integer.parseInt(struserId);
			map.put("userId", userId);
			result = eventService.countEvent(map);
			logService.addLog(struserId, "countEvent", "统计事件", "update", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("countEvent", "统计事件", "update", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: isAccountability
	 * @Des: 是否问责
	 * @Params: userId, token,regionId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/18
	 */
	@RequestMapping(value = { "/isAccountability" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> isAccountability(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String isAccountability = request.getParameter("isAccountability");
			map.put("isAccountability", isAccountability);
			String eventId = request.getParameter("eventId");
			map.put("eventId", eventId);
			result = eventService.updateEvent(map);
			logService.addLog(struserId, "isAccountability", "是否问责", "update", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("isAccountability", "是否问责", "update", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: exportEvent
	 * @Des: 导出问题事件
	 * @Params: token,userId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2018/04/02
	 */
	@RequestMapping(value = { "/exportEvent" }, method = { RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> exportEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strUserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			// 路径
			String path = "/assets/Event/";
			Constant constant = new Constant();
			String savePath = constant.getProperty("path") + path;
			uploadinfo.mkfiledir(savePath);
			String streventIds = request.getParameter("eventIds") != null ? request.getParameter("eventIds") : "0";
			if (streventIds != null && !(streventIds.equals(""))) {
				String[] eventIds = streventIds.split(",");
				map.put("eventIds", eventIds);
			}
			map.put("path", savePath);
			result = eventService.exportEvent(map, response);
			logService.addLog(strUserId, "exportEvent", "导出问题事件", "select", "info", result.get("message").toString());
		} else {
			result.put("result", -1);
			result.put("message", "请登录后操作");
			logService.addLog(strUserId, "exportEvent", "导出问题事件", "select", "info", "请登录后操作");
		}
		return result;
	}
}
