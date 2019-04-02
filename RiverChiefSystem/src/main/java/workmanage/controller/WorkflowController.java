package workmanage.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import usermanage.service.LogService;
import usermanage.service.UserService;
import workmanage.service.WorkflowService;

@Controller
@RequestMapping({ "/workflowController" })
public class WorkflowController {

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	FileUploadUtil uploadinfo = new FileUploadUtil();

	/**
	 * @Title: newDeploye
	 * @Des: 部署流程
	 * @Params: userId,token,file
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/24
	 */
	@RequestMapping(value = { "/newDeploye" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> newDeploye(HttpServletRequest request) throws Exception {
		String path = "/bpmn";
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> uploadinfomation = uploadinfo.fileuploaduilfuc(request, path);
		if ((Integer) uploadinfomation.get("result") == 1) {
			Map<Object, Object> info = (Map<Object, Object>) uploadinfomation.get("info");
				List<Map<Object, Object>> fileinfo = (List<Map<Object, Object>>) uploadinfomation.get("fileinfo");
				Map<Object, Object> fileinfomap = fileinfo.get(0);
				String fileurl = (String) fileinfomap.get("fileurl");
				String fileName = (String) fileinfomap.get("filename");
				Date now = (Date) fileinfomap.get("date");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				fileName = dateFormat.format(now) + fileName;
				File file = new File(fileurl);
				map = workflowService.saveNewDeploye(file, fileName);
				
				return map;
			}
		return map;
	}

	/**
	 * @Title: startProcess
	 * @Des: 启动流程实例
	 * @Params: userId,token,processDefinitionKey
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/24
	 */
	@RequestMapping(value = { "/startProcess" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> startProcess(HttpServletRequest request) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			int userId = Integer.parseInt(struserId);
			String processDefinitionKey = request.getParameter("processDefinitionKey");// event
			// map = workflowService.saveStartProcess(processDefinitionKey,new Event());
			if ((map.get("deployment") != null)) {
				logService.addLog(struserId, "startProcess", "启动流程实例", "insert", "info", "success");
			} else {
				logService.addLog(struserId, "startProcess", "启动流程实例", "insert", "info", "fail");
			}
			return map;
		}
		logService.addLog("newDeploye", "启动流程实例", "select", "info", "请登录后操作");
		map.put("result", -1);
		map.put("message", "请登录后操作");
		return map;
	}

	/**
	 * @Title: queryPersonalTask
	 * @Des: 完成任务
	 * @Params: userId,token,
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/24
	 */
	@RequestMapping(value = { "/queryPersonalTask" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryPersonalTask(HttpServletRequest request) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			int userId = Integer.parseInt(struserId);
			String processDefinitionKey = request.getParameter("processDefinitionKey");// event
			// map = workflowService.saveStartProcess(processDefinitionKey);
			if ((map.get("deployment") != null)) {
				logService.addLog(struserId, "startProcess", "启动流程实例", "insert", "info", "success");
			} else {
				logService.addLog(struserId, "startProcess", "启动流程实例", "insert", "info", "fail");
			}
			return map;
		}
		logService.addLog("newDeploye", "启动流程实例", "select", "info", "请登录后操作");
		map.put("result", -1);
		map.put("message", "请登录后操作");
		return map;
	}

	/**
	 * @Title: queryTask
	 * @Des: 查找任务
	 * @Params: userId,token,
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/24
	 */
	@RequestMapping(value = { "/queryTask" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryTask(HttpServletRequest request) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		int eventId = 103;
		map.put("eventId", eventId);
		workflowService.findTask(map);
		return map;
	}

	/**
	 * @Title: queryImage
	 * @Des: 查找流程图
	 * @Params: userId,token,processInstanceId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/27
	 */
	@RequestMapping(value = { "/queryImage" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		if (check == 1) {
			int userId = Integer.parseInt(struserId);
			/** 将生成图片放到文件夹下 */
			String eventId = request.getParameter("eventId");
			map.put("eventId", eventId);
			// 获取图片资源名称
			map = workflowService.queryImage(map, request, response);
			logService.addLog(struserId, "queryImage", "查看流程图", "insert", "info", map.get("message").toString());
			return map;
		}
		logService.addLog("queryImage", "查看流程图", "select", "info", "请登录后操作");
		map.put("result", -1);
		map.put("message", "请登录后操作");
		return map;
	}

	@RequestMapping(value = { "/querytest" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<Object, Object> querytest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();

		/** 将生成图片放到文件夹下 */
		String processInstanceId = request.getParameter("processInstanceId");
		// 获取图片资源名称
		workflowService.generateImage(processInstanceId);
		return map;
	}
}
