package statisticalform.controller;

import assessment.service.RegionService;
import common.model.PollutantSource;
import common.model.StatisticsWaterQuality;
import filemanage.service.RegionCut;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import statisticalform.service.StatisticalWaterQualityService;
import usermanage.service.LogService;
import usermanage.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statistic/waterQuality")
public class StatisticalWaterQualityController {
    @Autowired
    private StatisticalWaterQualityService statisticalWaterQualityService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private RegionCut regionCut;

    @RequestMapping("/qualityStatistics")
    public @ResponseBody
    Map<Object, Object> qualityDistribution(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String regionId = req.getParameter("regionId");
            int level = regionService.selectByPrimeryKey(regionId).getLevel();
            if (level != 5) {
                List<StatisticsWaterQuality> list = null;
                try {
                    list = statisticalWaterQualityService.countByType(regionCut.cut(regionId));
                } catch (Exception e) {
                    map.put("message", e.getMessage());
                    map.put("result", -10);
                    return map;
                }
                for (int i = 0; i < list.size(); i++) {
                    switch (list.get(i).getType()) {
                        case 111:
                            list.get(i).setTypeName("I类");
                            break;
                        case 112:
                            list.get(i).setTypeName("II类");
                            break;
                        case 113:
                            list.get(i).setTypeName("II类");
                            break;
                        case 114:
                            list.get(i).setTypeName("IV类");
                            break;
                        case 115:
                            list.get(i).setTypeName("V类");
                            break;
                        case 116:
                            list.get(i).setTypeName("劣V类");
                            break;
                    }
                }


//            int wq1 = statisticalWaterQualityService.countByType("111", string);
//            int wq2 = statisticalWaterQualityService.countByType("112", string);
//            int wq3 = statisticalWaterQualityService.countByType("113", string);
//            int wq4 = statisticalWaterQualityService.countByType("114", string);
//            int wq5 = statisticalWaterQualityService.countByType("115", string);
//            int wq6 = statisticalWaterQualityService.countByType("116", string);
//            int sum = wq1 + wq2 + wq3 + wq4 + wq5 + wq6;
//            map.put("wq1", wq1);
//            map.put("wq2", wq2);
//            map.put("wq3", wq3);
//            map.put("wq4", wq4);
//            map.put("wq5", wq5);
//            map.put("wq6", wq6);
//            map.put("sum", sum);
                map.put("result", 1);
                map.put("list", list);
                logService.addLog("/statistic/waterQuality/qualityStatistics", "水质统计", "select", "info", "success");
            } else {
                map.put("result", 0);
                logService.addLog("/statistic/waterQuality/qualityStatistics", "水质统计", "select", "info", "村级无下属");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/statistic/waterQuality/qualityStatistics", "水质统计", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping("/qualityStandard")
    public @ResponseBody
    Map<Object, Object> qualityStandard(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String regionId = req.getParameter("regionId");
            int level = regionService.selectByPrimeryKey(regionId).getLevel();
            List<String> strings = regionService.selectByParentId(regionId);
            List<StatisticsWaterQuality> statisticsWaterQualities = new ArrayList<>();
            int j = 0;
            if (strings.size() != 0) {
                for (; j < strings.size(); j++) {
                    StatisticsWaterQuality statisticsWaterQuality = new StatisticsWaterQuality();
                    switch (level) {
                        case 1:
                            statisticsWaterQuality.setRiverCount(statisticalWaterQualityService.countByRegion(strings.get(j).substring(0, 4)));
                            statisticsWaterQuality.setStandardCount(statisticalWaterQualityService.countByWarn(strings.get(j).substring(0, 4)));
                            break;
                        case 2:
                            statisticsWaterQuality.setRiverCount(statisticalWaterQualityService.countByRegion(strings.get(j).substring(0, 6)));
                            statisticsWaterQuality.setStandardCount(statisticalWaterQualityService.countByWarn(strings.get(j).substring(0, 6)));
                            break;
                        case 3:
                            statisticsWaterQuality.setRiverCount(statisticalWaterQualityService.countByRegion(strings.get(j).substring(0, 9)));
                            statisticsWaterQuality.setStandardCount(statisticalWaterQualityService.countByWarn(strings.get(j).substring(0, 9)));
                            break;
                    }
                    statisticsWaterQuality.setRegionId(strings.get(j));
                    statisticsWaterQuality.setRegionName(regionService.selectByPrimeryKey(strings.get(j)).getRegionName());
                    statisticsWaterQuality.setStandardPercent((float) statisticsWaterQuality.getStandardCount() / (float) statisticsWaterQuality.getRiverCount());
                    statisticsWaterQualities.add(j, statisticsWaterQuality);
                }
                map.put("statisticsList", statisticsWaterQualities);
                map.put("result", 1);
                logService.addLog("/statistic/waterQuality/qualityStandard", "水质达标统计", "select", "info", "success");

            } else {
                map.put("result", 0);
                logService.addLog("/statistic/waterQuality/qualityStandard", "水质达标统计", "select", "info", "村级无下属");

            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/statistic/waterQuality/qualityStandard", "水质达标统计", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping("/pollutantStatistics")
    public @ResponseBody
    Map<Object, Object> polluantStatistics(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String regionId = req.getParameter("regionId");
            List<String> strings = regionService.selectByParentId(regionId);
            int level = regionService.selectByPrimeryKey(regionId).getLevel();
            List<StatisticsWaterQuality> statisticsWaterQualities = new ArrayList<>();
            int j = 0;
            if (strings.size() != 0) {
                for (; j < strings.size(); j++) {
                    StatisticsWaterQuality statisticsWaterQuality = new StatisticsWaterQuality();
                    switch (level) {
                        case 1:
                            statisticsWaterQuality.setPollSrcCount251(statisticalWaterQualityService.countPollSrcByType("251", strings.get(j).substring(0, 4)));
                            statisticsWaterQuality.setPollSrcCount252(statisticalWaterQualityService.countPollSrcByType("252", strings.get(j).substring(0, 4)));
                            statisticsWaterQuality.setPollSrcCount253(statisticalWaterQualityService.countPollSrcByType("253", strings.get(j).substring(0, 4)));
                            statisticsWaterQuality.setPollSrcCount254(statisticalWaterQualityService.countPollSrcByType("254", strings.get(j).substring(0, 4)));
                            break;
                        case 2:
                            statisticsWaterQuality.setPollSrcCount251(statisticalWaterQualityService.countPollSrcByType("251", strings.get(j).substring(0, 6)));
                            statisticsWaterQuality.setPollSrcCount252(statisticalWaterQualityService.countPollSrcByType("252", strings.get(j).substring(0, 6)));
                            statisticsWaterQuality.setPollSrcCount253(statisticalWaterQualityService.countPollSrcByType("253", strings.get(j).substring(0, 6)));
                            statisticsWaterQuality.setPollSrcCount254(statisticalWaterQualityService.countPollSrcByType("254", strings.get(j).substring(0, 6)));
                            break;
                        case 3:
                            statisticsWaterQuality.setPollSrcCount251(statisticalWaterQualityService.countPollSrcByType("251", strings.get(j).substring(0, 9)));
                            statisticsWaterQuality.setPollSrcCount252(statisticalWaterQualityService.countPollSrcByType("252", strings.get(j).substring(0, 9)));
                            statisticsWaterQuality.setPollSrcCount253(statisticalWaterQualityService.countPollSrcByType("253", strings.get(j).substring(0, 9)));
                            statisticsWaterQuality.setPollSrcCount254(statisticalWaterQualityService.countPollSrcByType("254", strings.get(j).substring(0, 9)));
                            break;
                    }
                    statisticsWaterQuality.setRegionId(strings.get(j));
                    statisticsWaterQuality.setRegionName(regionService.selectByPrimeryKey(strings.get(j)).getRegionName());
                    statisticsWaterQualities.add(j, statisticsWaterQuality);
                }
                map.put("statisticsList", statisticsWaterQualities);
                map.put("result", 1);
                logService.addLog("/statistic/waterQuality/pollutantStatistics", "污染源统计", "select", "info", "success");

            } else {
                map.put("result", 0);
                logService.addLog("/statistic/waterQuality/pollutantStatistics", "污染源统计", "select", "info", "村级无下属");

            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/statistic/waterQuality/pollutantStatistics", "污染源统计", "select", "info", "fail，请登录后操作");
        }

        return map;
    }


}
