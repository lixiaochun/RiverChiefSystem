package assessment.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import assessment.service.ExcelAssessService;
import assessment.service.GroupAssessService;
import common.model.GroupAssess;
import common.model.UserRank;
import common.model.User;
import common.model.UserScore;
import common.model.UserScoreOne;
import common.util.DateHelper;
import usermanage.service.UserService;

@Controller
@RequestMapping("/groupAssessController")
public class GroupAssessController {

	@Autowired
	private ExcelAssessService excelAssessService;
	@Autowired
	private GroupAssessService groupAssessService;
	@Autowired
	private UserService userService;

	// 查询小组评分列表
	@RequestMapping(value = "/findGroupList")
	@ResponseBody
	public Map<String,Object> findGroupList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		
		try {
			GroupAssess ga = new GroupAssess();
			int pageNo = 0;
			int pageSize = 10 ; 
			ga.setMin(0);
			ga.setPageSize(pageSize);
			if(request.getParameter("pageNo")!=null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));//当前页
			}
			if(request.getParameter("pageSize")!=null) {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));//每页行数
				int min = pageSize*(pageNo-1);
				if(min<0) min = 0;
				ga.setMin(min);
				ga.setPageSize(pageSize);
			}
			String groupName = request.getParameter("groupName");//考核名称
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			ga.setCreateTime(DateHelper.parseString(startTime));
			ga.setDueTime(DateHelper.parseString(endTime));
			ga.setGroupName(groupName);
			List<GroupAssess> list = groupAssessService.findAllList(ga);
			int count =  groupAssessService.countList(ga);
			map.put("ga", ga);
			map.put("pageNo",pageNo);
			map.put("pageSize", pageSize);
			map.put("count",count);
			map.put("list", list);
			map.put("result", 1);
			map.put("message", "查询成功");
		} catch (Exception e) {
			map.put("result", 0);
			map.put("message", "查询失败");
			e.printStackTrace();
		}
		return map;
	}
	// 小组评分明细
	@RequestMapping(value = "/selectedOneGroup")
	@ResponseBody
	public Map<String, Object> selectedOneGroup(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		
		try {
			String groupID = request.getParameter("groupId");
			int groupId = Integer.parseInt(groupID);
			List<UserScore> list = groupAssessService.userScoreList(groupId);
			GroupAssess ga = groupAssessService.fetchExcelId(groupId);
			String html = excelAssessService.showRules(ga.getExcelId());
			map.put("ga", ga);
			map.put("list", list);
			map.put("html", html);
			map.put("result", 1);
			map.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", 0);
			map.put("message", "查询失败");
		}
		return map;
	}
	// 显示小组中单个用户的具体得分情况
	@RequestMapping(value = "/showOneUserScore")
	@ResponseBody
	public Map<String, Object> showOneUserScore(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		try {
			String groupID = request.getParameter("groupId");
			String selectedUserId = request.getParameter("selectedUserId");
			int groupId = Integer.parseInt(groupID);
			int userID = Integer.parseInt(selectedUserId);
			List<UserScoreOne> list = groupAssessService.findOneUserScore(groupId,userID);
			map.put("list", list);
			map.put("result", 1);
			map.put("message", "查询成功");
		} catch (Exception e) {
			map.put("result", 0);
			map.put("message", "查询失败");
			e.printStackTrace();
		}
		return map;
	}
	//新增小组评分情况
	@RequestMapping(value = "/createGroupScore")
	@ResponseBody
	public Map<String, Object> createGroupScore(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		
		try {
			String gpAssess = request.getParameter("gpAssess");
			String usList = request.getParameter("userscoreList");
			String usOneList = request.getParameter("userscoreOneList");
			GroupAssess ga= JSON.parseObject(gpAssess, GroupAssess.class);//小组评分的列表信息
			List<UserScore> userscoreList = JSON.parseArray(usList, UserScore.class);//批量插入用户总分数
			List<UserScoreOne> userscoreOneList = JSON.parseArray(usOneList, UserScoreOne.class);//批量插入用户在每个规则上的得分
			int groupId = groupAssessService.insertGroupAssess(ga);
			for(UserScore us:userscoreList){
				us.setGroupId(groupId);
			}
			for(UserScoreOne uso:userscoreOneList){
				uso.setGroupId(groupId);
			}
			groupAssessService.insertGroupScore(userscoreList);
			groupAssessService.insertGroupScoreOne(userscoreOneList);
			
			List<GroupAssess> gaList = new ArrayList<GroupAssess>();
			
			for(UserScore us:userscoreList){//list去重了。。
				GroupAssess gaOne = new GroupAssess();
				gaOne.setId(groupId);
				gaOne.setUserId(us.getUserId());
				gaOne.setExcelId(ga.getExcelId());
				gaOne.setType(ga.getType());
				gaOne.setGroupName(ga.getGroupName());
				gaOne.setCreateTime(ga.getCreateTime());
				gaOne.setDueTime(ga.getDueTime());
				gaList.add(gaOne);
			}
			groupAssessService.addUserToBeAssess(gaList);//添加用户待办评分
			map.put("result", 1);
			map.put("message", "新增成功");
		} catch (Exception e) {
			map.put("result", 0);
			map.put("message", "新增失败");
			e.printStackTrace();
		}
		
		return  map;
	}
	
	    //选择用户列表
		@RequestMapping(value = "/findUserList")
		@ResponseBody
		public Map<String,Object> findUserList(HttpServletRequest request, HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			// 验证登录权限
			String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
			String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
			int check = userService.checkToken(userId, token);
			if (check != 1) {
				map.put("result", -1);
				map.put("message", "请登录后操作");
				return map;
			}
			try {
				User user = new User();
				int pageNo = 0;
				int pageSize = 10 ; 
				user.setMin(0);
				user.setPageSize(pageSize);
				if(request.getParameter("pageNo")!=null) {
					pageNo = Integer.parseInt(request.getParameter("pageNo"));//当前页
				}
				if(request.getParameter("pageSize")!=null) {
					pageSize = Integer.parseInt(request.getParameter("pageSize"));//每页行数
					int min = pageSize*(pageNo-1);
					if(min<0) min = 0;
					user.setMin(min);
					user.setPageSize(pageSize);
				}
				String regionName =  request.getParameter("regionName");
				String realName =  request.getParameter("realName");
				user.setRealName(realName);
				user.setRegionName(regionName);
				List<User> list = groupAssessService.findUserList(user);
				int count =  groupAssessService.countUserList(user);
				map.put("user", user);
				map.put("pageNo",pageNo);
				map.put("pageSize", pageSize);
				map.put("count",count);
				map.put("list", list);
				map.put("result", 1);
				map.put("message", "查询成功");
			} catch (Exception e) {
				map.put("result", 0);
				map.put("message", "查询失败");
				e.printStackTrace();
			}
			return  map;
		}
	
	    //修改小组 明细
 		@RequestMapping(value = "/modifyGroupScoreDetail")
		@ResponseBody
		public Map<String,Object> modifyGroupScoreDetail(HttpServletRequest request, HttpServletResponse response){
 			Map<String,Object> map = new HashMap<String,Object>();
			// 验证登录权限
			String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
			String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
			int check = userService.checkToken(userId, token);
			if (check != 1) {
				map.put("result", -1);
				map.put("message", "请登录后操作");
				return map;
			}
			try {
				String gpAssess = request.getParameter("gpAssess");
				GroupAssess ga= JSON.parseObject(gpAssess, GroupAssess.class);//小组评分的列表信息
				groupAssessService.modifyGroupAssess(ga);
				map.put("message", "修改小组评分成功");
				map.put("result", 1);
			} catch (Exception e) {
				map.put("result", 0);
				map.put("message", "修改小组评分失败");
				e.printStackTrace();
			}
			return  map;
		}
 		
 		//修改小组个人得分 明细
 		@RequestMapping(value = "/modifyGroupOneScore")
		@ResponseBody
		public Map<String,Object> modifyGroupOneScore(HttpServletRequest request, HttpServletResponse response){
 			Map<String,Object> map = new HashMap<String,Object>();
			// 验证登录权限
			String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
			String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
			int check = userService.checkToken(userId, token);
			if (check != 1) {
				map.put("result", -1);
				map.put("message", "请登录后操作");
				return map;
			}
 			
 			try {
				String usOneList = request.getParameter("userscoreOneList");
				List<UserScoreOne> userscoreOneList = JSON.parseArray(usOneList, UserScoreOne.class);//批量插入用户在每个规则上的得分
				double totalScore = 0;
				for(UserScoreOne uso : userscoreOneList){
					totalScore = totalScore + uso.getUserScore();//个人得分
				}
				UserScore us =  new UserScore();
				int modifidUserId = Integer.valueOf(request.getParameter("modifidUserId"));	//被修改的用户ID
				us.setUserId(Integer.valueOf(modifidUserId));
				us.setUserScore(totalScore);
				int groupId = Integer.valueOf(request.getParameter("groupId"));	//获取小组ID
				us.setGroupId(groupId);
				groupAssessService.modifyUserOneScore(us);
				groupAssessService.modifyUserOneScoreDetail(userscoreOneList);
				map.put("message", "修改小组个人得分成功");
				map.put("result", 1);
			} catch (Exception e) {
				map.put("result", 0);
				map.put("message", "修改小组个人得分失败");
				e.printStackTrace();
			}
			return  map;
		}
 		//查询我的排名 
 		@RequestMapping(value = "/findMyAssessRank")
		@ResponseBody
		public Map<String,Object> findMyAssessRank(HttpServletRequest request, HttpServletResponse response){
 			Map<String,Object> map = new HashMap<String,Object>();
			// 验证登录权限
			String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
			String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
			int check = userService.checkToken(userId, token);
			if (check != 1) {
				map.put("result", -1);
				map.put("message", "请登录后操作");
				return map;
			}
			try {
				String excelId = request.getParameter("excelId");
				List <UserRank> list = groupAssessService.findAssessRank(excelId);
				map.put("rankList", list);
				map.put("message", "查询排名成功");
				map.put("result", 1);
			} catch (Exception e) {
				map.put("message", "查询排名失败");
				map.put("result", 0);
				e.printStackTrace();
			}
			
			return map;
 		}
 		
 		//查询我的区域排名 
 		@RequestMapping(value = "/findMyRegionRank")
		@ResponseBody
		public Map<String,Object> findMyRegionRank(HttpServletRequest request, HttpServletResponse response){
 			Map<String,Object> map = new HashMap<String,Object>();
			// 验证登录权限
			String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
			String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
			int check = userService.checkToken(userId, token);
			if (check != 1) {
				map.put("result", -1);
				map.put("message", "请登录后操作");
				return map;
			}
			try {
				String excelId = request.getParameter("excelId");
				String regionId =  request.getParameter("regionId");
				regionId =regionId + "%";
				List <UserRank> list = groupAssessService.findRegionRank(excelId,regionId);
				map.put("rankList", list);
				map.put("message", "查询排名成功");
				map.put("result", 1);
			} catch (Exception e) {
				map.put("message", "查询排名失败");
				map.put("result", 0);
				e.printStackTrace();
			}
			
			return map;
 		}
 		
 		
 		/*----------------------------------------------手机app端---------------------------------------------------------*/
 		//查询用户待办评分
 		@RequestMapping(value = "/findToAssessList")
		@ResponseBody
		public Map<String,Object> findToAssessList(HttpServletRequest request, HttpServletResponse response){
 			Map<String,Object> map = new HashMap<String,Object>();
			// 验证登录权限
			String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
			String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
			int check = userService.checkToken(userId, token);
			if (check != 1) {
				map.put("result", -1);
				map.put("message", "请登录后操作");
				return map;
			}
 			
 			try {
 				List<GroupAssess> gaList = groupAssessService.findToAssessList(userId);
 				map.put("gaList", gaList);
				map.put("message", "查询成功");
				map.put("result", 1);
			} catch (Exception e) {
				map.put("result", 0);
				map.put("message", "查询失败");
				e.printStackTrace();
			}
			return  map;
		}
 		
 		//查询一个待办评分详细
 		@RequestMapping(value = "/selectOneToAssess")
		@ResponseBody
		public Map<String,Object> selectOneToAssess(HttpServletRequest request, HttpServletResponse response){
 			Map<String,Object> map = new HashMap<String,Object>();
			// 验证登录权限
			String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
			String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
			int check = userService.checkToken(userId, token);
			if (check != 1) {
				map.put("result", -1);
				map.put("message", "请登录后操作");
				return map;
			}
			try {
				String id = request.getParameter("id");
				GroupAssess ga = groupAssessService.selectOneToAssess(id);
				String html = excelAssessService.showRules(ga.getExcelId());
				map.put("ga", ga);
				map.put("html", html);
				map.put("message", "查询成功");
				map.put("result", 1);
			} catch (Exception e) {
				map.put("result", 0);
				map.put("message", "查询失败");
				e.printStackTrace();
			}
			return map;
 		}
 		
 		
 		//完成用户自评
 		@RequestMapping(value = "/completeToAssessList")
		@ResponseBody
		public Map<String,Object> completeToAssessList(HttpServletRequest request, HttpServletResponse response){
 			Map<String,Object> map = new HashMap<String,Object>();
			// 验证登录权限
			String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
			String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
			int check = userService.checkToken(userId, token);
			if (check != 1) {
				map.put("result", -1);
				map.put("message", "请登录后操作");
				return map;
			}
			try {
				String usList = request.getParameter("userscore");
				String usOneList = request.getParameter("userscoreOne");
				UserScore us=  JSON.parseObject(usList, UserScore.class);//批量插入用户总分数
				List<UserScoreOne> uso=  JSON.parseArray(usOneList, UserScoreOne.class);//批量插入用户在每个规则上的得分
				groupAssessService.insertUserselfAssess(us);
				groupAssessService.insertUserselfScore(uso);
				map.put("message", "插入成功");
				map.put("result", 1);
			} catch (Exception e) {
				map.put("result", 0);
				map.put("message", "插入失败");
				e.printStackTrace();
			}
			return map;
 		}
}
