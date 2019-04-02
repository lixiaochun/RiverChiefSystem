package assessment.controller;

import assessment.service.AssessmentService;
import assessment.service.RegionService;
import assessment.service.ValueCheck;
import common.model.Assessment;
import common.model.Region;
import common.model.River;
import onemap.service.RiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import usermanage.service.LogService;
import usermanage.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/assess")
public class AssessmentController {
    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private LogService logService;
    @Autowired
    private ValueCheck valueCheck;
    @Autowired
    private UserService userService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private RiverService riverService;

    @RequestMapping(value = "/dataInsert", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> dataInsert(HttpServletRequest req, @RequestBody Map<Object,Object> objectMap) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        Assessment assessment = new Assessment();
        //检查权限
        String struserId = objectMap.get("userId") != null && objectMap.get("userId") != "" ? (String) objectMap.get("userId") : "-1";
        String token = objectMap.get("token") != null ? (String) objectMap.get("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String riverId = (String) objectMap.get("riverId");
            int year= Integer.parseInt((String) objectMap.get("year"));
            int month = Integer.parseInt((String)objectMap.get("month"));
            String regionId = riverId == null ? (String) objectMap.get("regionId") : riverService.selectByPrimaryKey(riverId).getRegionId();
            int ownerId= riverId==null? Integer.parseInt((String) objectMap.get("ownerId")) :riverService.selectByPrimaryKey(riverId).getUserId();
            Assessment assessment1 = assessmentService.selectExistByRiverId(year, month, riverId, regionId);
            if (assessment1 != null) {
                map.put("result", -2);
            } else {
//                String detail = req.getParameter("detail");
//                assessment.setDetailArray(detail);
//                List<String> score = Arrays.asList(detail.split(","));
                List<Number> score = (List<Number>) objectMap.get("detail");
                assessment.setDetailArray(score.toString());
                double sum = 0;
                int i;
                for (i = 0; i < 12; i++) {
                    sum = sum + Double.valueOf(score.get(i).toString());
                }
                assessment.setOrganizationalScore(sum);
                sum = 0;
                for (i = 12; i < 32; i++) {
                    sum = sum + Double.valueOf(score.get(i).toString());
                }
                assessment.setWaterEnvironmentScore(sum);
                assessment.setStrategyScore(Double.valueOf(score.get(32).toString()));
                assessment.setComplaintScore(Double.valueOf(score.get(33).toString()));
                assessment.setPublicityScore(Double.valueOf(score.get(34).toString()) + Double.valueOf(score.get(35).toString()));
                assessment.setAdditionalScore(Double.valueOf(score.get(36).toString()) + Double.valueOf(score.get(37).toString()));
                assessment.setYear(year);
                assessment.setMonth(month);
                assessment.setRiverId(riverId);
                assessment.setRegionId(regionId);
                Date now = new Date();
                assessment.setSubmitTime(now);
                assessment.setOwnerId(ownerId);
                assessment.setAssessUserId(Integer.valueOf(struserId));
                try {
                    assessmentService.insert(assessment);
                    map.put("result", 1);
                    logService.addLog(struserId, "/assess/dataInsert", "考核信息输入", "insert", "info", "success");
                } catch (Exception e) {
                    map.put("result", 0);
                    logService.addLog(struserId, "/assess/dataInsert", "考核信息输入", "insert", "info", "failed,");
                }
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/dataInsert", "考核信息输入", "insert", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping(value = "/dataUpdate", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> dataUpdate(HttpServletRequest req,@RequestBody Map<Object,Object> objectMap) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = objectMap.get("userId") != null && req.getParameter("userId") != "" ? (String) objectMap.get("userId") : "-1";
        String token = objectMap.get("token") != null ? (String) objectMap.get("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            //
            String riverId = (String) objectMap.get("riverId");
            int year= Integer.parseInt((String) objectMap.get("year"));
            int month = Integer.parseInt((String)objectMap.get("month"));
            String regionId = riverId == null ? (String) objectMap.get("regionId") : riverService.selectByPrimaryKey(riverId).getRegionId();
            int ownerId= riverId==null? Integer.parseInt((String) objectMap.get("ownerId")) :riverService.selectByPrimaryKey(riverId).getUserId();
            Assessment assessment = assessmentService.selectExistByRiverId(year, month, riverId, regionId);
            if (assessment == null) {
                map.put("result", -2);
            } else {
                List<String> score = (List<String>) objectMap.get("detail");
                assessment.setDetailArray(score.toString());
                double sum;
                int i;
                for (i = 0, sum = 0; i < 12; i++) {
                    sum = sum + Double.valueOf(score.get(i));
                }
                assessment.setOrganizationalScore(sum);
                for (i = 12, sum = 0; i < 32; i++) {
                    sum = sum + Double.valueOf(score.get(i));
                }
                assessment.setWaterEnvironmentScore(sum);
                assessment.setStrategyScore(Double.valueOf(score.get(32)));
                assessment.setComplaintScore(Double.valueOf(score.get(33)));
                assessment.setPublicityScore(Double.valueOf(score.get(34) + score.get(35)));
                assessment.setAdditionalScore(Double.valueOf(score.get(36) + score.get(37)));
                assessment.setYear(year);
                assessment.setMonth(month);
                assessment.setRiverId(riverId);
                assessment.setRegionId(regionId);
                Date now = new Date();
                assessment.setSubmitTime(now);
                assessment.setOwnerId(ownerId);
                assessment.setAssessUserId(Integer.valueOf(struserId));
                try {
                    assessmentService.updateByPrimaryKey(assessment);
                    map.put("result", 1);
                    logService.addLog(struserId, "/assess/dataUpdate", "考核信息更新", "update", "info", "suceess");

                } catch (Exception e) {
                    map.put("result", 0);
                    logService.addLog(struserId, "/assess/dataUpdate", "考核信息更新", "update", "info", "fail,");
                }
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/dataUpdate", "考核信息更新", "update", "info", "fail，请登录后操作");
        }
        return map;
    }

    //查询按照region划分groupby范围
    //查询region表的相同parent部分找到当前区域的所有子项进行groupby
    @RequestMapping("/showByMonth")
    public @ResponseBody
    Map<Object, Object> showByMonth(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            int i;
            int year = Integer.parseInt(req.getParameter("year"));
            int month = Integer.parseInt(req.getParameter("month"));
            String parentRegion = req.getParameter("parentId");
//
            List<String> regions = regionService.selectByParentId(parentRegion);
            List<Assessment> assessments = assessmentService.avgByRiverListInMonth(year, month, regions);
            for (i = 0; i < assessments.size(); i++) {
                assessments.get(i).setRegionName(regionService.selectByPrimeryKey(assessments.get(i).getRegionId()).getRegionName());
            }
            //
            map.put("assessmentList", assessments);
            map.put("result", 1);
            logService.addLog(struserId, "/assess/showByMonth", "考核按月展示", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/showByMonth", "考核按月展示", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping("/showBySeason")
    public @ResponseBody
    Map<Object, Object> showBySeason(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            int i;
            int year = Integer.parseInt(req.getParameter("year"));
            int season = Integer.parseInt(req.getParameter("season"));
            String parentRegion = req.getParameter("parentId");
            List<Assessment> assessments = assessmentService.avgByRiverListInSeason(year, season, regionService.selectByParentId(parentRegion));
            for (i = 0; i < assessments.size(); i++) {
                assessments.get(i).setRegionName(regionService.selectByPrimeryKey(assessments.get(i).getRegionId()).getRegionName());
            }
            map.put("assessmentList", assessments);
            map.put("result", 1);
            logService.addLog(struserId, "/assess/showBySeason", "考核按季展示", "select", "info", "success");

        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/showBySeason", "考核按季展示", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping("/showByYear")
    public @ResponseBody
    Map<Object, Object> showByYear(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            int i;
            int year = Integer.parseInt(req.getParameter("year"));
            String parentRegion = req.getParameter("parentId");
            List<Assessment> assessments = assessmentService.avgByRiverListInYear(year, regionService.selectByParentId(parentRegion));
            for (i = 0; i < assessments.size(); i++) {
                assessments.get(i).setRegionName(regionService.selectByPrimeryKey(assessments.get(i).getRegionId()).getRegionName());
            }
            map.put("assessmentList", assessments);
            map.put("result", 1);
            logService.addLog(struserId, "/assess/showByYear", "考核按年展示", "select", "info", "success");

        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/showByYear", "考核按年展示", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping("/getoneInMonth")
    public @ResponseBody
    Map<Object, Object> getoneByMonth(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            int year = Integer.parseInt(req.getParameter("year"));
            int month = Integer.parseInt(req.getParameter("month"));
            String riverId = req.getParameter("riverId");
            String regionId = riverId == null ? req.getParameter("regionId") : riverService.selectByPrimaryKey(riverId).getRegionId();
            Assessment assessment = assessmentService.selectExistByRiverId(year, month, riverId, regionId);
            assessment.setRiverName(riverService.selectByPrimaryKey(assessment.getRiverId()).getRiverName());
            String str = assessment.getDetailArray().replace("[", "").replace("]", "");
            String[] strings = str.split(",");
            List<String> strings1 = new ArrayList<>();
            for (int i = 0; i < strings.length; i++) {
                strings1.add(strings[i]);
            }
            map.put("return", assessment);
            map.put("detail", strings1);
            map.put("result", 1);
            logService.addLog(struserId, "/assess/getoneInMonth", "得到单月详情", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/getoneInMonth", "得到单月详情", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

//    @RequestMapping("/getoneInSeason")
//    public @ResponseBody
//    Map<Object, Object> getoneInSeason(HttpServletRequest req) {
//        Map<Object, Object> map = new HashMap<>();
//        int year = Integer.parseInt(req.getParameter("year"));
//        int season = Integer.parseInt(req.getParameter("season"));
//        String riverId = req.getParameter("riverId");
//        String regionId = riverId == null ? req.getParameter("regionId") : riverService.selectByPrimaryKey(riverId).getRegionId();
//        Assessment assessment = assessmentService.avgByRiverInSeason(year, season, riverId, regionId);
//        assessment.setRiverName(riverService.selectByPrimaryKey(assessment.getRiverId()).getRiverName());
//        map.put("result", assessment);
//        return map;
//    }
//
//    @RequestMapping("/getoneInYear")
//    public @ResponseBody
//    Map<Object, Object> getoneInYear(HttpServletRequest req) {
//        Map<Object, Object> map = new HashMap<>();
//        int year = Integer.parseInt(req.getParameter("year"));
//        String riverId = req.getParameter("riverId");
//        String regionId = riverId == null ? req.getParameter("regionId") : riverService.selectByPrimaryKey(riverId).getRegionId();
//        Assessment assessment = assessmentService.avgByRiverInYear(year, riverId, regionId);
//        assessment.setRiverName(riverService.selectByPrimaryKey(assessment.getRiverId()).getRiverName());
//        assessment.setRegionName(regionService.selectByPrimeryKey(regionId).getRegionName());
//        map.put("result", assessment);
//        return map;
//    }

    @RequestMapping(value = "/dataDelete", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> dataDelete(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            //
            String id = req.getParameter("assessId");
            try {
                assessmentService.deleteByPrimaryKey(id);
                map.put("result", 1);
                logService.addLog(struserId, "/assess/dataDelete", "考核信息删除", "delete", "info", "success");
            } catch (Exception e) {
                map.put("result", 0);
                logService.addLog(struserId, "/assess/dataDelete", "考核信息删除", "delete", "info", "failed,");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/dataDelete", "考核信息删除", "delete", "info", "fail，请登录后操作");
        }
        return map;
    }

//    @RequestMapping("/getRegionId")
//    public @ResponseBody
//    Map<Object, Object> getRegionId(HttpServletRequest req) {
//        Map<Object, Object> map = new HashMap<>();
//        String regionName = req.getParameter("name");
//        try {
//            map.put("regionId", regionService.selectByName(regionName));
//            logService.addLog("getRegionId", "用名字查regionId", "select", "info", "success");
//        } catch (Exception e) {
//            map.put("result", "failed");
//            map.put("e", e);
//            logService.addLog("getRegionId", "用名字查regionId", "select", "info", "failed\n" + e);
//        }
//        return map;
//    }

    @RequestMapping("/getRegionByParent")
    public @ResponseBody
    Map<Object, Object> getRegionByParent(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String regionParent = req.getParameter("parentId");
            List<Region> regions = regionService.getListByParent(regionParent);
            if (regions.size() != 0) {
                map.put("regionList", regions);
                map.put("result", 1);
                logService.addLog(struserId, "/assess/getRegionByParent", "父区域查子区域", "select", "info", "success");
            } else {
                map.put("result", 0);
                logService.addLog(struserId, "/assess/getRegionByParent", "父区域查子区域", "select", "info", "none");

            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/getRegionByParent", "父区域查子区域", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping("/getRiverByParent")
    public @ResponseBody
    Map<Object, Object> getRiverByParent(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String riverParent = req.getParameter("parentId");
            List<River> rivers = riverService.getListByParent(riverParent);
            if (rivers.size() != 0) {
                map.put("riverList", rivers);
                map.put("result", 1);
                logService.addLog(struserId, "/assess/getRiverByParent", "父河流查子河流", "select", "info", "success");
            } else {
                map.put("result", 0);
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/getRiverByParent", "父河流查子河流", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping("/queryRiverInfo")
    public @ResponseBody Map<Object,Object> queryRiverInfo(HttpServletRequest req){
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String riverId = req.getParameter("riverId");
            map.put("return", riverService.selectByPrimaryKey(riverId));
            map.put("result", 1);
            logService.addLog(struserId, "/assess/queryRiverInfo", "查询河流信息", "select", "info", "success");

        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/assess/queryRiverInfo", "查询河流信息", "select", "info", "fail，请登录后操作");
        }
        return map;
    }
}

