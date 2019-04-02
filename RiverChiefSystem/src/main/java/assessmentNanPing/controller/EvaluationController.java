package assessmentNanPing.controller;

import java.util.Date;
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

import assessment.service.GroupAssessService;
import assessmentNanPing.service.EvaluationService;
import common.model.EvaluationCriteria;
import common.model.EvaluationPieCharts;
import common.model.User;
import common.util.DateHelper;

@Controller
@RequestMapping("/evaluationController")
public class EvaluationController {
		
		@Autowired
		private GroupAssessService groupAssessService;
	    @Autowired
		private EvaluationService evaluationService;
	
		@RequestMapping(value = "/showUserEvaluation")
		@ResponseBody
		public Map<String,Object> showUserEvaluation(HttpServletRequest request,HttpServletResponse response){
			int userId = Integer.parseInt(request.getParameter("userId"));
			Map<String,Object> map = new HashMap<String,Object>();
			Date date = new Date();
			String temp = DateHelper.formatDateByFormat(date, "yyyy-MM");
			String currentDate = temp+"%";
			int dayNum = evaluationService.queryUserPatrolDays(userId,currentDate);
			int patrolTime = evaluationService.queryUserPatrolTime(userId,currentDate);
			double patrolMileage = evaluationService.queryUserPatrolMileage(userId,currentDate);
			int eventNum = evaluationService.queryUserEventNum(userId,currentDate);
			String dateSubOneMonth = DateHelper.getPreviousMonth(temp)+"%";
			List<EvaluationPieCharts> epcList = evaluationService.queryUserPieChart(userId,dateSubOneMonth);
			map.put("dayNum", dayNum);
			map.put("patrolTime", patrolTime);
			map.put("patrolMileage", patrolMileage);
			map.put("eventNum", eventNum);
			map.put("pieCharts", epcList);
			return map;
		}
		
		@RequestMapping(value = "/showUserDateEvaluation")
		@ResponseBody
		public Map<String,Object> showUserDateEvaluation(HttpServletRequest request,HttpServletResponse response){
			int userId = Integer.parseInt(request.getParameter("userId"));
			String dateStr = request.getParameter("dateStr");
			dateStr = dateStr + "%";
			Map<String,Object> map = new HashMap<String,Object>();
			int dayNum = evaluationService.queryUserPatrolDays(userId,dateStr);
			int patrolTime = evaluationService.queryUserPatrolTime(userId,dateStr);
			double patrolMileage = evaluationService.queryUserPatrolMileage(userId,dateStr);
			int eventNum = evaluationService.queryUserEventNum(userId,dateStr);
			List<EvaluationPieCharts> epcList = evaluationService.queryUserPieChart(userId,dateStr);
			System.out.println(dayNum+":"+patrolTime+":"+patrolMileage+":"+eventNum);
			for(EvaluationPieCharts epc:epcList){
				System.out.println("种类:"+epc.getType()+";用户得分："+epc.getUserScore()+";百分比："+epc.getRatio()+";实际数据："+epc.getDetailData());
			}
			map.put("dayNum", dayNum);
			map.put("patrolTime", patrolTime);
			map.put("patrolMileage", patrolMileage);
			map.put("eventNum", eventNum);
			map.put("pieCharts", epcList);
			return map;
		}
		
		//选择用户列表
		@RequestMapping(value = "/findUserList")
		@ResponseBody
		public Map<String,Object> findUserList(HttpServletRequest request, HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
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
				
		//选择用户列表
		@RequestMapping(value = "/downLoadUserRank")
		@ResponseBody
		public Map<String,Object> downLoadUserRank(HttpServletRequest request, HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			String dateStr = request.getParameter("dateStr");
		    String isSuccess =	evaluationService.downLoadUserRank(request, response,dateStr);
			if(isSuccess.equals("success")){
				map.put("result", "1");
				map.put("message", "下载成功");
			}else{
				map.put("result", "0");
				map.put("message", "下载失败");
				map.put("reason",isSuccess);
			}
			return map;
		}
			
		//展示当月考评规则
		@RequestMapping(value = "/queryCurrentCriteria")
		@ResponseBody
		public Map<String,Object> queryCurrentCriteria(HttpServletRequest request, HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			List<EvaluationCriteria> eaList = evaluationService.queryCurrentCriteria();
			map.put("criteriaList", eaList);
			return map;
		}
		
		//修改当月考评规则
		@RequestMapping(value = "/modifyCurrentCriteria")
		@ResponseBody
		public Map<String,Object> modifyCurrentCriteria(HttpServletRequest request, HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				String criteriaList = request.getParameter("criteriaList");
				List<EvaluationCriteria> eaList = JSON.parseArray(criteriaList, EvaluationCriteria.class);
				evaluationService.updateCurrentCriteria(eaList);
				map.put("result", "1");
				map.put("message", "修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("result", "0");
				map.put("message", "修改失败");
				map.put("reason", e.getStackTrace());
			}
			return map;
		}
		
}
