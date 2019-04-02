package assessmentFuDing.controller;

import assessmentFuDing.service.AssessmentRegionService;
import common.model.RegionPatrolInfo;
import common.util.DateHelper;
import filemanage.service.RegionCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("assessmentRegionController")
public class AssessmentRegionController {
    @Autowired
    private AssessmentRegionService assessRegionService;
    @Autowired
    private RegionCut regionCut;

    /**
     * 按日期查看区域平均
     *
     * @param req
     * @return
     */
    //todo: add log
    @RequestMapping("/getRegionEachMonth")
    public @ResponseBody
    Map<Object, Object> getRegionEachMonth(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String regionId = req.getParameter("regionId");
        String region = null;
        try {
            region = regionCut.cut(regionId);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", -10);
            return map;
        }
        String time = req.getParameter("time");
        String[] times = time.split(",");
        String mode = req.getParameter("mode");
        if (mode.equals("1")) {
            try {
                map.put("result", 1);
                map.put("assess", assessRegionService.findRegionDataMonthSum(region, times));

            } catch (Exception e) {
                map.put("result", 0);
                map.put("message", e.getMessage());
                e.printStackTrace();
            }
        } else if (mode.equals("2")) {
            try {
                map.put("result", 1);
                map.put("assess", assessRegionService.findRegionDataMonthAvg(region, times));

            } catch (Exception e) {
                map.put("result", 0);
                map.put("message", e.getMessage());
                e.printStackTrace();
            }
        } else {
            map.put("msg", "(≧∇≦)b -输错了mode");
        }
        return map;
    }

    @RequestMapping("/queryRegionContrast")
    @ResponseBody
    public Map<String, Object> queryRegionContrast(HttpServletRequest request,HttpServletResponse response){
    	Map<String,Object> map = new HashMap<String,Object>();
    	try {
			String regionIdStr = request.getParameter("regionIds");
			String startStr = request.getParameter("startTime");
			String endStr = request.getParameter("endTime");
			String countMethod = request.getParameter("countMethod");
			if(regionIdStr == null || "".equals(regionIdStr)){
				map.put("result", "fail");
				map.put("message", "请选择所要对比的区域！");
				return map;
			}
			if(startStr == null || "".equals(startStr)||endStr == null || "".equals(endStr)){
				map.put("result", "fail");
				map.put("message", "请输入起、止日期！");
				return map;
			}
			if(countMethod == null || "".equals(countMethod)){
				map.put("result", "fail");
				map.put("message", "请选择统计方式！");
				return map;
			}
			String[] regionIds = null;
			regionIds = regionIdStr.split(",");
			List<String> regionIdsLike = new ArrayList<String>();
			//根据用户选择的区域Id查询区域名称
			List<RegionPatrolInfo> temp = assessRegionService.queryRegionName(regionIds);
			for(int i=0;i<temp.size();i++){
				regionIdsLike.add(regionCut.cut(temp.get(i).getRegionId())+"%");
			}
			Date startTime = DateHelper.parseTimesDate(startStr);
			Date endTime = DateHelper.parseTimesDate(endStr);

            List<RegionPatrolInfo> regionPatrolList = assessRegionService.queryRegionContrastSUM(regionIdsLike,startTime,endTime);
			if("sum".equals(countMethod)){
				for(int i=0;i<temp.size();i++){
					DecimalFormat df = new DecimalFormat("#.00");
					regionPatrolList.get(i).setRegionId(temp.get(i).getRegionId());
					regionPatrolList.get(i).setRegionName(temp.get(i).getRegionName());
					regionPatrolList.get(i).setMileage(regionPatrolList.get(i).getMileage()==0?0:Double.parseDouble(df.format(regionPatrolList.get(i).getMileage()/1000)));
				}
			}else{
				ArrayList<Integer> peopleNum = assessRegionService.queryRegionPeopleNum(regionIds);
				for(int i=0;i<temp.size();i++){
					regionPatrolList.get(i).setRegionId(temp.get(i).getRegionId());
					regionPatrolList.get(i).setRegionName(temp.get(i).getRegionName());
                    DecimalFormat df = new DecimalFormat("#.00");
					regionPatrolList.get(i).setDays(regionPatrolList.get(i).getDays()==0?0:Double.parseDouble(df.format((regionPatrolList.get(i).getDays())/peopleNum.get(i))));
					regionPatrolList.get(i).setTimes(regionPatrolList.get(i).getTimes()==0?0:Double.parseDouble(df.format(regionPatrolList.get(i).getTimes()/peopleNum.get(i))));
					regionPatrolList.get(i).setMileage(regionPatrolList.get(i).getMileage()==0?0:Double.parseDouble(df.format(regionPatrolList.get(i).getMileage()/peopleNum.get(i)/1000)));
					regionPatrolList.get(i).setEventNum(regionPatrolList.get(i).getEventNum()==0?0:Double.parseDouble(df.format(regionPatrolList.get(i).getEventNum()/peopleNum.get(i))));
				}
			}
			map.put("result", "success");
			map.put("message", "查询成功");
			map.put("RegionPatrol", regionPatrolList);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "fail");
			map.put("message", e.getMessage());
		}
		return map;
    }
    //todo:end add log
}
