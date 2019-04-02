package conferenceManagement.controller;

import java.sql.Timestamp;
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

import conferenceManagement.service.ConferenceService;
import usermanage.service.LogService;
import usermanage.service.UserService;

/**
 * 会议管理
 *
 */
@Controller
@RequestMapping({"/conferenceController"})
public class ConferenceController {
	
	@Autowired
	private ConferenceService conferenceService;
	@Autowired
	private LogService logService;
	@Autowired
	private UserService userService;
	/**
	* @Title: queryConference
	* @Des: 查看会议列表־
	* @Params: userId,token,pageNo,pageSize,conferenceId,conferenceName,theme,site,contact,time，status
	* @Return: map
	* @Author: cxy
	* @Date: 2017/12/27
	*/
	@RequestMapping(value = {"/queryConference"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  queryConference(HttpServletRequest request) throws Exception {
		String strUserId = request.getParameter("userId")!=null ? request.getParameter("userId"):"";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";		
		int userId = Integer.parseInt(strUserId);
		int check = userService.checkToken(""+userId, token);
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
			if(request.getParameter("status")!=null&&request.getParameter("status")!="")
			{
				int status =Integer.parseInt (request.getParameter("status"));
				map.put("status", status);
			}			
			if(request.getParameter("conferenceId")!=null&&request.getParameter("conferenceId")!="")
			{
				int conferenceId =Integer.parseInt (request.getParameter("conferenceId"));
				map.put("conferenceId", conferenceId);
			}			
			if(request.getParameter("conferenceName")!=null&&request.getParameter("conferenceName")!="")
			{
				String conferenceName = request.getParameter("conferenceName");
				map.put("conferenceName", conferenceName);
			}
			if(request.getParameter("theme")!=null&&request.getParameter("theme")!="")
			{
				String theme = request.getParameter("theme");
				map.put("theme", theme);
			}
			if(request.getParameter("site")!=null&&request.getParameter("site")!="")
			{
				String site = request.getParameter("site");
				map.put("site", site);
			}
			if(request.getParameter("contact")!=null&&request.getParameter("contact")!="")
			{
				String contact = request.getParameter("contact");
				map.put("contact", contact);
			}
			if(request.getParameter("stepStatus")!=null&&request.getParameter("stepStatus")!="")
			{
				String stepStatus = request.getParameter("stepStatus");
				map.put("stepStatus", stepStatus);
			}
			if(request.getParameter("followThrough")!=null&&request.getParameter("followThrough")!="")
			{
				String followThrough= request.getParameter("followThrough");
				map.put("followThrough", followThrough);
			}
			if(request.getParameter("time")!=null&&request.getParameter("time")!="")
			{
				Timestamp time=Timestamp.valueOf (request.getParameter("time")+" 00:00:00");
				map.put("time",time);
			}
			result=conferenceService.queryConference(map);
			if((result.get("Conference")!=null))
			{
				result.put("result", 1);
				logService.addLog(strUserId,"queryConference", "查看会议列表", "select", "info","success");
			}
			else
			{
				result.put("result", 0);
				logService.addLog(strUserId,"queryConference", "查看会议列表", "select", "info","fail");
			}
		}
		else
		{
			logService.addLog("queryConference", "查看会议列表", "select", "info","fail");
			result.put("result", -1);
		}
		return result;			
	}
	/**
	* @Title: insertConference
	* @Des: 添加会议
	* @Params: userId,token,conferenceName,theme,site,advertisingMap,notice,announcements,contact,agenda,arrangement,manual,remark,time
	* @Return: map
	* @Author: cxy
	* @Date: 2017/12/27
	*/
	@RequestMapping(value = {"/insertConference"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  insertConference(HttpServletRequest request) throws Exception {
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";		
		int userId =Integer.parseInt (request.getParameter("userId")!=null ? request.getParameter("userId"):"");
		int check = userService.checkToken(""+userId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) 
		{
			if(request.getParameter("conferenceName")==null)
			{
				String conferenceName ="";
				map.put("conferenceName", conferenceName);
			}
			else
			{
				String conferenceName = request.getParameter("conferenceName");
				map.put("conferenceName", conferenceName);
			}
			if(request.getParameter("stepStatus")==null)
			{
				String stepStatus ="";
				map.put("stepStatus", stepStatus);
			}
			else
			{
				String stepStatus = request.getParameter("stepStatus");
				map.put("stepStatus", stepStatus);
			}
			if(request.getParameter("theme")==null)
			{
				String theme ="";
				map.put("theme", theme);
			}
			else
			{
				String theme = request.getParameter("theme");
				map.put("theme", theme);
			}
			if(request.getParameter("site")==null)
			{
				String site ="";
				map.put("site", site);
			}
			else
			{
				String site = request.getParameter("site");
				map.put("site", site);
			}
			if(request.getParameter("advertisingMap")==null)
			{
				String advertisingMap ="";
				map.put("advertisingMap", advertisingMap);
			}
			else
			{
				String advertisingMap = request.getParameter("advertisingMap");
				map.put("advertisingMap", advertisingMap);
			}
			if(request.getParameter("notice")==null)
			{
				String notice ="";
				map.put("notice", notice);
			}
			else
			{
				String notice = request.getParameter("notice");
				map.put("notice", notice);
			}
			if(request.getParameter("announcements")==null)
			{
				String announcements ="";
				map.put("announcements", announcements);
			}
			else
			{
				String announcements = request.getParameter("announcements");
				map.put("announcements", announcements);
			}
			if(request.getParameter("contact")==null)
			{
				String contact ="";
				map.put("contact", contact);
			}
			else
			{
				String contact = request.getParameter("contact");
				map.put("contact", contact);
			}
			if(request.getParameter("agenda")==null)
			{
				String agenda ="";
				map.put("agenda", agenda);
			}
			else
			{
				String agenda = request.getParameter("agenda");
				map.put("agenda", agenda);
			}
			if(request.getParameter("arrangement")==null)
			{
				String arrangement ="";
				map.put("arrangement", arrangement);
			}
			else
			{
				String arrangement = request.getParameter("arrangement");
				map.put("arrangement", arrangement);
			}
			if(request.getParameter("manual")==null)
			{
				String manual ="";
				map.put("manual", manual);
			}
			else
			{
				String manual = request.getParameter("manual");
				map.put("manual", manual);
			}
			if(request.getParameter("stepStatus")==null)
			{
				String stepStatus ="";
				map.put("stepStatus",stepStatus);
			}
			else
			{
				String stepStatus = request.getParameter("stepStatus");
				map.put("stepStatus",stepStatus);
			}
			if(request.getParameter("followThrough")==null)
			{
				String followThrough ="";
				map.put("followThrough", followThrough);
			}
			else
			{
				String followThrough = request.getParameter("followThrough");
				map.put("followThrough", followThrough);
			}
			if(request.getParameter("remark")==null)
			{
				String remark ="";
				map.put("remark", remark);
			}
			else
			{
				String remark = request.getParameter("remark");
				map.put("remark", remark);
			}
			int status =1;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(request.getParameter("time")!=null) {
				String strTime = request.getParameter("time");
				Date time=sdf.parse(strTime); 
				map.put("time", time);
			}
			map.put("status", status);

			result =conferenceService.insertConference(map);
			if((result.get("conferenceId")!=null))
			{
				result.put("result", 1);
				logService.addLog(""+userId,"insertConference", "添加会议", "insert", "info","success");
			}
			else
			{
				result.put("result", 0);
				logService.addLog(""+userId,"insertConference", "添加会议", "insert", "info","fail");
			}
		}
		else
		{
			logService.addLog("insertConference", "添加会议", "insert", "info","fail");
			result.put("result", -1);
		}		
		return result;
	}
	/**
	* @Title: updateConference
	* @Des: 更新会议
	* @Params: userId,token,conferenceId,conferenceName,theme,site,advertisingMap,notice,announcements,contact,agenda,arrangement,manual,remark,time
	* @Return: map
	* @Author: cxy
	* @Date: 2017/12/27
	*/
	@RequestMapping(value = {"/updateConference"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  updateConference(HttpServletRequest request) throws Exception {
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int userId =Integer.parseInt (request.getParameter("userId")!=null ? request.getParameter("userId"):"");
		int check = userService.checkToken(""+userId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check == 1) 
		{		
			//获取数据
			if(request.getParameter("conferenceId")!=null)
			{
				int conferenceId =Integer.parseInt (request.getParameter("conferenceId"));
				map.put("conferenceId", conferenceId);
			}
			if(request.getParameter("status")!=null)
			{
				int status =Integer.parseInt (request.getParameter("status"));
				map.put("status", status);
			}
			if(request.getParameter("conferenceName")!=null)
			{
				String conferenceName = request.getParameter("conferenceName");
				map.put("conferenceName", conferenceName);
			}
			if(request.getParameter("theme")!=null)
			{
				String theme = request.getParameter("theme");
				map.put("theme", theme);
			}
			if(request.getParameter("site")!=null)
			{
				String site = request.getParameter("site");
				map.put("site", site);
			}
			if(request.getParameter("advertisingMap")!=null)
			{
				String advertisingMap = request.getParameter("advertisingMap");
				map.put("advertisingMap", advertisingMap);
			}
			if(request.getParameter("notice")!=null)
			{
				String notice = request.getParameter("notice");
				map.put("notice", notice);
			}
			if(request.getParameter("announcements")!=null)
			{
				String announcements = request.getParameter("announcements");
				map.put("announcements", announcements);
			}
			if(request.getParameter("contact")!=null)
			{
				String contact = request.getParameter("contact");
				map.put("contact", contact);
			}
			if(request.getParameter("agenda")!=null)
			{
				String agenda = request.getParameter("agenda");
				map.put("agenda", agenda);
			}
			if(request.getParameter("arrangement")!=null)
			{
				String arrangement = request.getParameter("arrangement");
				map.put("arrangement", arrangement);
			}
			if(request.getParameter("manual")!=null)
			{
				String manual = request.getParameter("manual");
				map.put("manual", manual);
			}
			if(request.getParameter("stepStatus")!=null)
			{
				String stepStatus = request.getParameter("stepStatus");
				map.put("stepStatus", stepStatus);
			}
			if(request.getParameter("followThrough")!=null)
			{
				String followThrough = request.getParameter("followThrough");
				map.put("followThrough", followThrough);
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(request.getParameter("time")!=null) {
				String strTime = request.getParameter("time");
				Date time=sdf.parse(strTime); 
				map.put("time", time);
			}
			if(request.getParameter("remark")!=null)
			{
				String remark = request.getParameter("remark");
				map.put("remark", remark);
			}
			result=conferenceService.updateConference(map);
			if(Integer.parseInt(result.get("result").toString())==1)
			{
				result.put("result", 1);
				logService.addLog(""+userId,"updateConference", "更新会议", "update", "info","success");
			}
			else
			{
				result.put("result",0);
				logService.addLog(""+userId,"updateConference", "更新会议", "update", "info","fail");
			}
		}
		else
		{
			result.put("result",-1);
			logService.addLog("updateConference", "更新会议", "update", "info","fail");
		}
		return result;
	}
	/**
	* @Title: deleteConference
	* @Des: 删除会议
	* @Params:userId,token,conferenceId
	* @Return: map
	* @Author: cxy
	* @Date: 2017/12/27
	*/
	@RequestMapping(value = {"/deleteConference"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  deleteConference(HttpServletRequest request) throws Exception {
		int userId =Integer.parseInt (request.getParameter("userId")!=null ? request.getParameter("userId"):"");
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(""+userId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check == 1) 
		{		
			//获取数据
			if(request.getParameter("conferenceId")!=null)
			{
				int conferenceId =Integer.parseInt (request.getParameter("conferenceId"));
				map.put("conferenceId", conferenceId);
			}
			result=conferenceService.deleteConference(map);
			if(Integer.parseInt(result.get("result").toString())==1)
			{
				result.put("result", 1);
				logService.addLog(""+userId,"deleteConference", "删除会议", "delete", "info","success");
			}
			else
			{
				result.put("result",0);
				logService.addLog(""+userId,"deleteConference", "删除会议", "delete", "info","fail");
			}
		}
		else
		{
			result.put("result",-1);
			logService.addLog("deleteConference", "删除会议", "delete", "info","fail");
		}
		return result;
	}
}
