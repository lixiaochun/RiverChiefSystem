package conferenceManagement.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import conferenceManagement.service.ConfereeService;
import usermanage.service.LogService;
import usermanage.service.UserService;

/**
 * 参会人员管理
 *
 */
@Controller
@RequestMapping({"/confereeController"})
public class ConfereeController {
	
	@Autowired
	private ConfereeService confereeService;
	@Autowired
	private LogService logService;
	@Autowired
	private UserService userService;
	/**
	* @Title: queryConferee
	* @Des: 查看参会人员־
	* @Params: userId,token,pageNo,pageSize,confereeId,conferenceId,confereeUserId,confereeName,contactInfo,company,duty
	* @Return: map
	* @Author: cxy
	* @Date: 2017/12/27
	*/
	@RequestMapping(value = {"/queryConferee"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  queryConferee(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId")!=null ? request.getParameter("userId"):"";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";		
		int userId = Integer.parseInt(strUserId);
		int check = userService.checkToken(strUserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) 
		{
			int pageNo = 0;
			if(request.getParameter("pageNo")!=null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));//当前页
			}
			if(request.getParameter("pageSize")!=null) {
				int pageSize = Integer.parseInt(request.getParameter("pageSize"));//每页行数
				int min = pageSize*(pageNo-1);
				map.put("pageSize", pageSize);
				map.put("min", min);
			}
			//获取数据
			if(request.getParameter("confereeId")!=null)
			{
				int confereeId =Integer.parseInt (request.getParameter("confereeId"));
				map.put("confereeId", confereeId);
			}
			if(request.getParameter("conferenceId")!=null)
			{
				int conferenceId =Integer.parseInt (request.getParameter("conferenceId"));
				map.put("conferenceId", conferenceId);
			}
			if(request.getParameter("confereeUserId")!=null)
			{
				int confereeUserId =Integer.parseInt (request.getParameter("confereeUserId"));
				map.put("userId", confereeUserId);
			}
			if(request.getParameter("confereeName")!=null)
			{
				String confereeName = request.getParameter("confereeName");
				map.put("confereeName", confereeName);
			}
			if(request.getParameter("contactInfo")!=null)
			{
				String contactInfo = request.getParameter("contactInfo");
				map.put("contactInfo", contactInfo);
			}
			if(request.getParameter("company")!=null)
			{
				String company = request.getParameter("company");
				map.put("company", company);
			}
			if(request.getParameter("duty")!=null)
			{
				String duty = request.getParameter("duty");
				map.put("duty", duty);
			}
			result=confereeService.queryConferee(map);
			if((result.get("Conferee")!=null))
			{
				result.put("result", 1);
				logService.addLog(strUserId,"queryConferee", "查看参会人员", "select", "info","success");
			}
			else
			{
				result.put("result", 0);
				logService.addLog(strUserId,"queryConferee", "查看参会人员", "select", "info","fail");
			}
		}
		else
		{
			logService.addLog("queryConferee", "查看参会人员", "select", "info","fail");
			result.put("result", -1);
		}
		return result;			
	}
	/**
	* @Title: insertConferee
	* @Des: 添加参会人员
	* @Params: userId,token,conferenceId,confereeUserId,status
	* @Return: map
	* @Author: cxy
	* @Date: 2017/12/27
	*/
	@RequestMapping(value = {"/insertConferee"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  insertConferee(HttpServletRequest request) throws Exception {
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";		
		int userId =Integer.parseInt (request.getParameter("userId")!=null ? request.getParameter("userId"):"");
		int check = userService.checkToken(""+userId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) 
		{
//			获取数据
			if(request.getParameter("conferenceId")==null)
			{
				int conferenceId =0;
				map.put("conferenceId", conferenceId);
			}
			else
			{
				int conferenceId =Integer.parseInt (request.getParameter("conferenceId"));
				map.put("conferenceId", conferenceId);
			}
			if(request.getParameter("confereeUserId")==null)
			{
				int confereeUserId =0;
				map.put("userId", confereeUserId);
			}
			else
			{
				int confereeUserId =Integer.parseInt (request.getParameter("confereeUserId"));
				map.put("userId", confereeUserId);
			}
			if(request.getParameter("status")==null)
			{
				int status =0;
				map.put("status", status);
			}
			else
			{
				int status =Integer.parseInt (request.getParameter("status"));
				map.put("status",status);
			}
			result =confereeService.insertConferee(map);
			if((result.get("conferenceId")!=null))
			{
				result.put("result", 1);
				logService.addLog(""+userId,"insertConferee", "添加参会人员", "insert", "info","success");
			}
			else
			{
				result.put("result", 0);
				logService.addLog(""+userId,"insertConferee", "添加参会人员", "insert", "info","fail");
			}
		}
		else
		{
			logService.addLog("insertConferee", "添加参会人员", "insert", "info","fail");
			result.put("result", -1);
		}		
		return result;
	}
	/**
	* @Title: updateConferee
	* @Des: 更新参会人员
	* @Params: userId,token,confereeId,status
	* @Return: map
	* @Author: cxy
	* @Date: 2017/12/27
	*/
	@RequestMapping(value = {"/updateConferee"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  updateConferee(HttpServletRequest request) throws Exception {
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int userId =Integer.parseInt (request.getParameter("userId")!=null ? request.getParameter("userId"):"");
		int check = userService.checkToken(""+userId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check == 1) 
		{		
			//获取数据
			if(request.getParameter("confereeId")!=null)
			{
				int confereeId =Integer.parseInt (request.getParameter("confereeId"));
				map.put("confereeId", confereeId);
			}
			if(request.getParameter("status")!=null)
			{
				int status =Integer.parseInt (request.getParameter("status"));
				map.put("status", status);
			}
			result=confereeService.updateConferee(map);
			if(Integer.parseInt(result.get("result").toString())==1)
			{
				result.put("result", 1);
				logService.addLog(""+userId,"updateConferee", "更新参会人员", "update", "info","success");
			}
			else
			{
				result.put("result",0);
				logService.addLog(""+userId,"updateConferee", "更新参会人员", "update", "info","fail");
			}
		}
		else
		{
			result.put("result",-1);
			logService.addLog("updateConferee", "更新参会人员", "update", "info","fail");
		}
		return result;
	}
	/**
	* @Title: deleteConferee
	* @Des: 删除参会人员
	* @Params:userId,token,confereeId,confereeUserId
	* @Return: map
	* @Author: cxy
	* @Date: 2017/12/27
	*/
	@RequestMapping(value = {"/deleteConferee"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  deleteConferee(HttpServletRequest request) throws Exception {
		int userId =Integer.parseInt (request.getParameter("userId")!=null ? request.getParameter("userId"):"");
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(""+userId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check == 1) 
		{		
			//获取数据
			if(request.getParameter("confereeId")!=null)
			{
				int confereeId =Integer.parseInt (request.getParameter("confereeId"));
				map.put("confereeId", confereeId);
			}
			if(request.getParameter("confereeUserId")!=null)
			{
				int confereeUserId =Integer.parseInt (request.getParameter("confereeUserId"));
				map.put("userId", confereeUserId);
			}
			if(request.getParameter("conferenceId")!=null)
			{
				int conferenceId =Integer.parseInt (request.getParameter("conferenceId"));
				map.put("conferenceId", conferenceId);
			}
			result=confereeService.deleteConferee(map);
			if(Integer.parseInt(result.get("result").toString())==1)
			{
				result.put("result", 1);
				logService.addLog(""+userId,"deleteConferee", "删除参会人员", "delete", "info","success");
			}
			else
			{
				result.put("result",0);
				logService.addLog(""+userId,"deleteConferee", "删除参会人员", "delete", "info","fail");
			}
		}
		else
		{
			result.put("result",-1);
			logService.addLog("deleteConferee", "删除参会人员", "delete", "info","fail");
		}
		return result;
	}
	

}
