package assessmentNanPing.controller;

import assessment.service.RegionService;
import assessmentNanPing.service.AssessRegionService;
import common.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import usermanage.service.LogService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("assessRegionController")
public class AssessRegionController {
    @Autowired
    private AssessRegionService assessRegionService;
    @Autowired
    private LogService logService;
    @Autowired
    private RegionService regionService;

    /**
     * 按日期查看区域平均
     *
     * @param req
     * @return
     */
    //todo: add log
    @RequestMapping("/getRegionAvg")
    public @ResponseBody
    Map<Object, Object> getRegionAvg(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String region = req.getParameter("regionId");
        String time = req.getParameter("time");
        try {
            map.put("result", 1);
            map.put("assess", assessRegionService.findByTimeAndRegion(region, time));

        } catch (Exception e) {
            map.put("result", 0);
            map.put("message", e.getMessage());

        }

        return map;
    }

    /**
     * 查看上月平均
     * @param req
     * @return
     */
    @RequestMapping("/getRegionAvgNow")
    public @ResponseBody
    Map<Object, Object> getRegionAvgNow(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String region = req.getParameter("regionId");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        try {
            map.put("result", 1);
            map.put("assess", assessRegionService.findByTimeAndRegion(region, time));

        } catch (Exception e) {
            map.put("result", 0);
            map.put("message", e.getMessage());
        }

        return map;
    }

    /**
     * 查看整年度各月
     * @param req
     * @return
     */
    @RequestMapping("/getRegionLastYear")
    public @ResponseBody
    Map<Object, Object> getRegionLastYear(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String region = req.getParameter("regionId");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String end = format.format(c.getTime()) + "-01";
        c.add(Calendar.YEAR, -1);
        c.add(Calendar.MONTH, 1);
        String start = format.format(c.getTime()) + "-01";
        try {
            map.put("list", assessRegionService.findCompleteLastYear(start, end, region));
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", -10);
            return map;
        }
        map.put("result", 1);
        return map;
    }

    /**
     * 父区域查看子区域
     * @param req
     * @return
     */
    @RequestMapping("/getRegionByParent")
    public @ResponseBody
    Map<Object, Object> getRegionByParent(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();

        String regionParent = req.getParameter("parentId");
        List<Region> regions = null;
        try {
            regions = regionService.getTree(regionParent);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", -10);
            return map;
        }
        if (regions.size() != 0) {
            map.put("regionList", regions);
            map.put("result", 1);
//                logService.addLog(struserId, "/assess/getRegionByParent", "父区域查子区域", "select", "info", "success");
        } else {
            map.put("result", 0);
//                logService.addLog(struserId, "/assess/getRegionByParent", "父区域查子区域", "select", "info", "none");

        }
        return map;
    }

    /**
     * 获得当月平均
     *
     * @param req
     * @return
     */
    @RequestMapping("/getRegionNowData")
    public @ResponseBody
    Map<Object, Object> getRegionNowData(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String region = req.getParameter("regionId");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        try {
            map.put("result", 1);
            map.put("assess", assessRegionService.findRegionData(region, time));

        } catch (Exception e) {
            map.put("result", 0);
            map.put("message", e.getMessage());
        }

        return map;
    }

    /**
     * 按用户查看当前巡河次数
     *
     * @param req
     * @return
     */
    @RequestMapping("/getRegionPatrolTimeEach")
    public @ResponseBody
    Map<Object, Object> getRegionPatrolTimeEach(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String region = req.getParameter("regionId");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        try {
            map.put("result", 1);
            map.put("assess", assessRegionService.queryRegionPatrolTimeEach(region, time));

        } catch (Exception e) {
            map.put("result", 0);
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * 按用户查看当前巡河里程
     *
     * @param req
     * @return
     */
    @RequestMapping("/getRegionPatrolMileageEach")
    public @ResponseBody
    Map<Object, Object> getRegionPatrolMileageEach(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String region = req.getParameter("regionId");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        try {
            map.put("result", 1);
            map.put("assess", assessRegionService.queryRegionPatrolMileageEach(region, time));

        } catch (Exception e) {
            map.put("result", 0);
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * 按用户查看当前事件数
     *
     * @param req
     * @return
     */
    @RequestMapping("/getRegionEventNumEach")
    public @ResponseBody
    Map<Object, Object> getRegionEventNumEach(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String region = req.getParameter("regionId");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        try {
            map.put("result", 1);
            map.put("assess", assessRegionService.queryRegionEventNumEach(region, time));

        } catch (Exception e) {
            map.put("result", 0);
            map.put("message", e.getMessage());
        }
        return map;
    }

    /**
     * 按用户查看当前巡河天数
     *
     * @param req
     * @return
     */
    @RequestMapping("/getRegionPatrolDaysEach")
    public @ResponseBody
    Map<Object, Object> getRegionPatrolDaysEach(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String region = req.getParameter("regionId");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        try {
            map.put("result", 1);
            map.put("assess", assessRegionService.queryRegionPatrolDaysEach(region, time));

        } catch (Exception e) {
            map.put("result", 0);
            map.put("message", e.getMessage());
        }
        return map;
    }

    //todo:end add log
}
