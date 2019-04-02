package usermanage.controller;

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
import usermanage.service.DateInfoService;
import usermanage.service.LogService;
import usermanage.service.UserService;

@Controller
@RequestMapping({ "/dateInfoController" })
public class DateInfoController {

	FileUploadUtil uploadinfo = new FileUploadUtil();

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	@Autowired
	private DateInfoService dateInfoService;

	/**
	 * 
	 * @Title: exportExcel
	 * @Des:导出河流
	 * @Params: userId,token,
	 * @Return: Map<Object,Object>
	 * @Author: lyf
	 * @Date: 2017年12月26日
	 */
	@RequestMapping(value = { "/exportExcel" }, method = { RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();

		// String struserId = request.getParameter("userId") != null ?
		// request.getParameter("userId") : "0";
		// String token = request.getParameter("token") != null ?
		// request.getParameter("token") : "";
		// int check = userService.checkToken(struserId, token);
		// if (check == 1) {
		String tableName = request.getParameter("tableName");
		String path = "/assets/excel/" + tableName + "/";
		String savePath = request.getSession().getServletContext().getRealPath(path);
		uploadinfo.mkfiledir(savePath);
		map.put("savePath", savePath);
		map.put("region", request.getParameter("region"));
		map.put("type", request.getParameter("type"));// 0:导出模板，1导出数据
		map.put("tableName", tableName);
		result = dateInfoService.exportExcel(map, response);
		// logService.addLog(struserId, "exportExcel", "导出excel", "select", "info",
		// result.get("message").toString());
		return result;
		// }
		// logService.addLog("exportExcel", "导出excel", "select", "info", "请登录后操作");
		// result.put("result", -1);
		// result.put("message", "请登陆后操作");
		// return result;
	}

	/**
	 * 导入excel
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/importExcel" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> importExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = "/assets/excel";
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> uploadinfomation = uploadinfo.fileuploaduilfuc(request, path);
		if ((Integer) uploadinfomation.get("result") == 1) {
			Map<Object, Object> info = (Map<Object, Object>) uploadinfomation.get("info");
			// String struserId = info.get("userId") != null ? info.get("userId").toString()
			// : "0";
			// String token = info.get("token") != null ? info.get("token").toString() : "";
			// //
			// int check = userService.checkToken(struserId, token);
			// if (check == 1) {
			// int userId = Integer.parseInt(struserId);
			String fileurl = (String) (info.get("fileurl") != null ? info.get("fileurl") : "");
			map.put("fileurl", fileurl);
			// map.put("userId", userId);
			map.put("tableName", info.get("tableName").toString());
			result = dateInfoService.importExcel(map, response);
			logService.addLog("importExcel", "导入excel", "insert", "info", result.get("message").toString());
		} else {
			logService.addLog("importExcel", "导入excel", "insert", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}
		// } else {
		// logService.addLog("importExcel", "导入excel", "insert", "info", "文件上传失败");
		// result.put("result", 0);
		// result.put("message", "文件上传失败");
		// }
		return result;
	}

	/**
	 * @Title: queryRegion
	 * @Des: 查询行政区列表
	 * @Params: userId,token,regionId,parentId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/17
	 */
	@RequestMapping(value = { "/queryRegion" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryRegion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
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
			String regionId = request.getParameter("regionId");
			String parentId = request.getParameter("parentId");
			map.put("regionId", regionId);
			map.put("parentId", parentId);
			String level1 = request.getParameter("level1");
			map.put("level1", level1);// 查询不等于乡镇级的行政区
			result = dateInfoService.queryRegion(map);
			logService.addLog(struserId, "queryRegion", "查询行政区列表", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("queryRegion", "查询行政区列表", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: queryRegionByParent
	 * @Des: 通过父子节点查询行政区列表
	 * @Params: userId,token,regionId,parentId,type
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/17
	 */
	@RequestMapping(value = { "/queryRegionByParent" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryRegionByParent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String requeststr = "/n requeststr:";
			String responstr = "/n responstr:";
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
			String regionId = request.getParameter("regionId");
			String parentId = request.getParameter("parentId");
			String type = request.getParameter("type") != null ? request.getParameter("type") : "0";
			map.put("regionId", regionId);
			map.put("parentId", parentId);
			map.put("type", type);
			result = dateInfoService.queryRegion(map);
			for (Map.Entry<Object, Object> m : map.entrySet()) {
				requeststr += "　　" + m.getValue() + "　　" + m.getKey();
			}
			for (Map.Entry<Object, Object> m : result.entrySet()) {
				responstr += "　　" + m.getValue() + "　　" + m.getKey();
			}
			logService.addLog(struserId, "queryRegionByParent", "通过父子节点查询行政区列表", "select", "info", result.get("message").toString() + requeststr + responstr);
			return result;
		}
		logService.addLog("queryRegionByParent", "通过父子节点查询行政区列表", "select", "info", "请登录后操作" + " userId=" + struserId + "   token" + token);
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: queryProblemType
	 * @Des: 查询问题列表
	 * @Params: userId,token
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/17
	 */
	@RequestMapping(value = { "/queryProblemType" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryProblemType(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			map.put("eventId", request.getParameter("eventId"));
			result = dateInfoService.queryProblemType(map);
			logService.addLog(struserId, "queryProblemType", "查询问题列表", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("queryProblemType", "查询问题列表", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
}
