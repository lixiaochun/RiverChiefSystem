package usermanage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import usermanage.service.LogService;
import usermanage.service.UserService;

@Controller
@RequestMapping({ "/logController" })
public class LogController {

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	/**
	 * @Title: queryLog
	 * @Des:查询日志
	 * @Params: userId, token, operaTime, userName, operaType,pageNo,pageSize
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/19
	 */
	@RequestMapping(value = { "/queryLog" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryLog(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		// int userId = Integer.parseInt(struserId);
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
			String stroperaTime = request.getParameter("operaTime");
			String operaType = request.getParameter("operaType");
			map.put("operaType", operaType);
			String userName = request.getParameter("userName");
			map.put("userName", userName);
			if (stroperaTime != null && stroperaTime != "") {
				String strstartTime = stroperaTime + " 00:00:00";
				String strendTime = stroperaTime + " 23:59:59";

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startTime = sdf.parse(strstartTime);
				Date endTime = sdf.parse(strendTime);
				map.put("startTime", startTime);
				map.put("endTime", endTime);
			}
			result = logService.queryLog(map);
			logService.addLog(struserId, "queryLog", "查询系统日志", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("queryLog", "查询系统日志", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
}
