package conferenceManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.util.FileUploadUtil;
import conferenceManagement.service.ConferenceFileService;
import usermanage.service.LogService;
import usermanage.service.UserService;

/**
 * 会议文件管理
 *
 */
@Controller
@RequestMapping({ "/conferenceFileController" })
public class ConferenceFileController {

	@Autowired
	private ConferenceFileService conferenceFileService;

	@Autowired
	private LogService logService;

	@Autowired
	private UserService userService;

	FileUploadUtil uploadinfo = new FileUploadUtil();

	/**
	 * @Title: uploadFile
	 * @Des: 文件上传
	 * @Params: userId,token,conferenceId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/1/4
	 */
	@RequestMapping(value = { "/uploadFile" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> uploadFile(HttpServletRequest request) throws Exception {
		String path = "/assets/conference/file";
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> uploadinfomation = uploadinfo.fileuploaduilfuc(request, path);
		if ((Integer) uploadinfomation.get("result") == 1) {
			Map<Object, Object> info = (Map<Object, Object>) uploadinfomation.get("info");
			List<Map<Object, Object>> fileinfo = (List<Map<Object, Object>>) uploadinfomation.get("fileinfo");
			String struserId = info.get("userId") != null ? info.get("userId").toString() : "0";
			String token = info.get("token") != null ? info.get("token").toString() : "";
			int check = userService.checkToken(struserId, token);
			if (check == 1) {
				String conferenceId = (String) (info.get("conferenceId") != null ? info.get("conferenceId") : "");
				map.put("fileinfo", fileinfo);
				map.put("conferenceId", conferenceId);
				result = conferenceFileService.uploadFile(map);
				logService.addLog(struserId, "uploadFile", "上传事件文件", "insert", "info", result.get("message").toString());
				return result;
			}
		}
		logService.addLog("uploadFile", "上传事件文件", "insert", "info", "fail，请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: queryFile
	 * @Des: 查询文件
	 * @Params: userId,token,conferenceId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/1/4
	 */
	@RequestMapping(value = { "/queryFile" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryFile(HttpServletRequest request) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId").toString() : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token").toString() : "";
		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			map.put("conferenceId", request.getParameter("conferenceId"));
			map.put("type", request.getParameter("type"));
			result = conferenceFileService.queryFile(map);
			logService.addLog("queryFile", "查询文件", "select", "info", result.get("message").toString());
		} else {
			logService.addLog("queryFile", "查询文件", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}

		return result;
	}

	/**
	 * @Title: deleteFile
	 * @Des: 删除文件
	 * @Params: userId,token,conferenceFileId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/1/4
	 */
	@RequestMapping(value = { "/deleteFile" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> deleteFile(HttpServletRequest request) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId").toString() : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token").toString() : "";
		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			map.put("conferenceFileId", request.getParameter("conferenceFileId"));
			result = conferenceFileService.deleteFile(map);
			logService.addLog("queryFile", "删除文件", "update", "info", result.get("message").toString());
		} else {
			logService.addLog("queryFile", "删除文件", "update", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}

		return result;
	}

	/**
	 * @Title: downloadFile
	 * @Des: 下载文件
	 * @Params: userId,token,conferenceFileId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/1/4
	 */
	@RequestMapping(value = { "/downloadFile" }, method = { RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> downloadFile(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId").toString() : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token").toString() : "";
		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			map.put("conferenceFileId", request.getParameter("conferenceFileId"));
			result = conferenceFileService.downloadFile(map, request, reponse);
			logService.addLog("downloadFile", "下载文件", "select", "info", result.get("message").toString());
		} else {
			logService.addLog("downloadFile", "下载文件", "select", "info", "请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
		}

		return result;
	}
}
