package statisticalform.controller;

import assessment.service.RegionService;
import common.model.StatisticsRiver;
import filemanage.service.RegionCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import statisticalform.service.StatisticalRiverService;
import usermanage.service.LogService;
import usermanage.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statistic/river")
public class StatisticalRiverController {
    @Autowired
    private StatisticalRiverService statisticalRiverService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private RegionCut regionCut;

    @RequestMapping("/levelDistribution")
    public @ResponseBody
    Map<Object, Object> levelDistribution(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String regionId = req.getParameter("regionId");
            int level = regionService.selectByPrimeryKey(regionId).getLevel();
            if (level != 5) {
                List<StatisticsRiver> list = null;
                try {
                    list = statisticalRiverService.countByLevel(regionCut.cut(regionId));
                } catch (Exception e) {
                    map.put("message", e.getMessage());
                    map.put("result", -10);
                    return map;
                }
                for (int i = 0; i < list.size(); i++) {
                    switch (list.get(i).getRiverLevel()) {
                        case 1:
                            list.get(i).setLevelName("省级河道");
                            break;
                        case 2:
                            list.get(i).setLevelName("市级河道");
                            break;
                        case 3:
                            list.get(i).setLevelName("县级河道");
                            break;
                        case 4:
                            list.get(i).setLevelName("镇级河道");
                            break;
                        case 5:
                            list.get(i).setLevelName("村级河道");
                            break;
                    }
                }
                map.put("list", list);
//            int city = statisticalRiverService.countByLevel(1, string);
//            int county = statisticalRiverService.countByLevel(2, string);
//            int town = statisticalRiverService.countByLevel(3, string);
//            int village = statisticalRiverService.countByLevel(4, string);
//            int sum = city + county + town + village;
//            map.put("sum", sum);
//            map.put("city", city);
//            map.put("county", county);
//            map.put("town", town);
//            map.put("village", village);
                map.put("result", 1);
                logService.addLog(struserId, "/statistic/river/levelDistribution", "河道等级分布", "select", "info", "success");
            } else {
                map.put("result", 0);
                logService.addLog(struserId, "/statistic/river/levelDistribution", "河道等级分布", "select", "info", "村级无下属");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/statistic/river/levelDistribution", "河道等级分布", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping("/riverStatistics")
    public @ResponseBody
    Map<Object, Object> riverStatistics(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String regionId = req.getParameter("regionId");
            int level = regionService.selectByPrimeryKey(regionId).getLevel();
            List<String> strings = regionService.selectByParentId(regionId);
            List<StatisticsRiver> statisticsRivers = new ArrayList<>();
            int j = 0;
            if (strings.size() != 0) {
                for (; j < strings.size(); j++) {
                    StatisticsRiver statisticsRiver = new StatisticsRiver();
                    switch (level) {
                        case 1:
                            statisticsRiver.setRiverCount(statisticalRiverService.countRiverByParent(strings.get(j).substring(0, 4)));
                            statisticsRiver.setOwnerCount(statisticalRiverService.countUserByParent(strings.get(j).substring(0, 4)));
                            break;
                        case 2:
                            statisticsRiver.setRiverCount(statisticalRiverService.countRiverByParent(strings.get(j).substring(0, 6)));
                            statisticsRiver.setOwnerCount(statisticalRiverService.countUserByParent(strings.get(j).substring(0, 6)));
                            break;
                        case 3:
                            statisticsRiver.setRiverCount(statisticalRiverService.countRiverByParent(strings.get(j).substring(0, 9)));
                            statisticsRiver.setOwnerCount(statisticalRiverService.countUserByParent(strings.get(j).substring(0, 9)));
                            break;
                    }
                    statisticsRiver.setRegionId(strings.get(j));
                    statisticsRiver.setRegionName(regionService.selectByPrimeryKey(strings.get(j)).getRegionName());
                    statisticsRivers.add(j, statisticsRiver);
                }
                map.put("statisticsList", statisticsRivers);
                map.put("result", 1);
                logService.addLog(struserId, "/statistic/river/riverStatistics", regionService.selectByPrimeryKey(regionId).getRegionName() + "下属河道河长统计", "select", "info", "success");

            } else {
                map.put("result", 0);
                logService.addLog(struserId, "/statistic/river/riverStatistics", regionService.selectByPrimeryKey(regionId).getRegionName() + "下属河道河长统计", "select", "info", "村级无下属");

            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/statistic/river/riverStatistics", "下属河道河长统计", "select", "info", "fail，请登录后操作");
        }
        return map;
    }
}
