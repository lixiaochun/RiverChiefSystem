package workmanage.controller;

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
import workmanage.service.RecordPointService;

@Controller
@RequestMapping({"/recordPointController"})
public class RecordPointController {
	
	@Autowired
	private RecordPointService recordPointService;
	
	@Autowired
	private LogService logService;
	@Autowired
	private UserService userService;
	/**
	* @Title: queryRecordPointByPatrolRecordId
	* @Des: 根据PatrolRecordId查记录点
	* @Params: patrolRecordId,userId,token
	* @Return: map
	* @Author: cxy
	* @Date: 2017/10/27
	*/
	@RequestMapping(value = {"/queryRecordPointByPatrolRecordId"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  queryRecordPointByPatrolRecordId(HttpServletRequest request) throws Exception {
		int patrolRecordId = Integer.parseInt(request.getParameter("patrolRecordId")!=null ? request.getParameter("userId"):"");
		String strUserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
//		int userId = Integer.parseInt(strUserId);
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) 
		{		
			result=recordPointService.queryRecordPointByPatrolRecordId(patrolRecordId);
			if((result.get("PatrolRecord")!=null))
			{
				result.put("result", 1);
				result.put("message", "操作成功");
				logService.addLog(strUserId,"queryRecordPointByPatrolRecordId", "根据PatrolRecordId查记录点", "select", "info","操作成功");
			}
			else
			{
				result.put("result", 0);
				result.put("message", "操作失败");
				logService.addLog(strUserId,"queryRecordPointByPatrolRecordId", "根据PatrolRecordId查记录点", "select", "info","操作失败");
			}
		}
		else
		{
			logService.addLog("queryRecordPointByPatrolRecordId", "根据PatrolRecordId查记录点", "select", "info","请登录后操作");
			result.put("result", -1);
			result.put("message", "请登录后操作");
		}

		return result;			
	}

}
