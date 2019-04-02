package assessmentFuDing.controller;

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

import assessmentFuDing.service.AssessmentUserService;
import common.model.UserBasisInfo;
import common.model.UserPatrolInfo;
import common.util.DateHelper;
import filemanage.service.RegionCut;

@Controller
@RequestMapping("/assessmentUserController")
public class AssessmentUserController {
	
		@Autowired
		private AssessmentUserService assessmentUserService;
		@Autowired
	    private RegionCut regionCut;
		
		@RequestMapping(value = "/queryRegionUserList")
		@ResponseBody
		public Map<String,Object> queryRegionUserList(HttpServletRequest request,HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				String regionIdStr = request.getParameter("regionIds");
				String[] regionIds = null;
				if(regionIdStr !=null && !"".equals(regionIdStr)){
					regionIds = regionIdStr.split(",");
					for(int i=0;i<regionIds.length;i++){
						regionIds[i] = regionCut.cut(regionIds[i])+"%";
					}
				}
				List<UserBasisInfo> selectedRegion = assessmentUserService.querySelectedRegion(regionIds);
				map.put("result", "success");
				map.put("message", "查询成功");
				map.put("selectedRegion", selectedRegion);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("result", "fail");
				map.put("message", e.getMessage());
			}
			return map;
		}
		
		@RequestMapping(value = "/queryUsersPatrolInfo")
		@ResponseBody
		public Map<String,Object> queryUsersPatrolInfo(HttpServletRequest request,HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				String userIdStr = request.getParameter("userIds");
				String[] userIds = userIdStr.split(",");
				String startStr = request.getParameter("startTime");
				String endStr = request.getParameter("endTime");
				Date startTime = DateHelper.parseTimesDate(startStr);
				Date endTime = DateHelper.parseTimesDate(endStr);
				List<UserPatrolInfo> upiList= assessmentUserService.queryUsersPatrolInfo(userIds,startTime,endTime);
				map.put("result", "success");
				map.put("message", "查询成功");
				map.put("UserPatrolInfo", upiList);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("result", "fail");
				map.put("message", e.getMessage());
			}
			return map;
		}
}
