package filemanage.controller;

import assessment.service.RegionService;
import common.model.Briefing;
import common.model.BriefingTemple;
import common.model.User;
import common.util.Html2doc;
import common.util.Html2pdf;
import filemanage.service.BriefingService;
import filemanage.service.BriefingTempleService;
import filemanage.service.RegionCut;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import usermanage.service.LogService;
import usermanage.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/briefing")
public class BriefingController {
    @Autowired
    private BriefingService briefingService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private RegionCut regionCut;
    @Autowired
    private BriefingTempleService briefingTempleService;

    /**
     * 简报提交
     *
     * @param map1
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> submit(@RequestBody Map<String, String> map1) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String id = map1.get("briefingId");
        String title = map1.get("title");
        String body = map1.get("body");
        String struserId = map1.get("userId") != null && map1.get("userId") != "" ? map1.get("userId") : "-1";
        String token = map1.get("token") != null ? map1.get("token") : "-1";
        String summary = map1.get("summary");
        String regionid = map1.get("regionId");
        String sendto = map1.get("sendto");
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            if (briefingService.findOrganization(Integer.parseInt(struserId)).getRoleId() == 4) {
                if (id == null || id == "") {
                    briefingService.insert(title, body, summary, regionid, struserId, sendto);
                    map.put("result", 1);
                    logService.addLog(struserId, "/briefing/submit", "简报提交", "insert", "info", "success");
                } else {
                    briefingService.updateByPrimaryKeyWithBLOBs(id, title, body, summary, struserId, regionid, sendto);
                    map.put("result", 1);
                    logService.addLog(struserId, "/briefing/submit", "简报提交", "update", "info", "success");
                }
            } else {
                map.put("result", -100);
                map.put("message", "你不是河长办");
                logService.addLog(struserId, "/briefing/submit", "简报提交", "insert/update", "info", "over power");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/submit", "简报提交", "insert/update", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 简报状态修改
     *
     * @param req
     * @return
     */
    @RequestMapping("/audit")
    public @ResponseBody
    Map<Object, Object> audit(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String status = req.getParameter("status");
            String briefingId = req.getParameter("briefingId");
            Briefing briefing = briefingService.selectByPrimaryKey(Integer.valueOf(briefingId));
            if (briefing.getBriefingSendtoUserId() == Integer.parseInt(struserId)) {
                briefing.setBriefingStatus(status);
                briefingService.updateStatus(briefing);
                map.put("result", 1);
                logService.addLog(struserId, "/briefing/audit", "简报审阅[" + briefingId + "]", "update", "info", "success");
            } else {
                map.put("result", -100);
                map.put("message", "你不是河长");
                logService.addLog(struserId, "/briefing/audit", "简报审阅", "update", "info", "overpower");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/audit", "简报审阅", "update", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 查看简报
     * 字段 起止时间 状态
     *
     * @param req
     * @return
     */
    @RequestMapping("/findBriefing")
    public @ResponseBody
    Map<Object, Object> findBriefing(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String title = req.getParameter("title");
            String page = req.getParameter("page");
            String pageSize = req.getParameter("pagesize");
            String startTime = req.getParameter("startTime");
            String endTime = req.getParameter("endTime");
            String status = req.getParameter("status");
            int pagenum;
            int pagesize;
            int i;
            if (page == null)
                pagenum = 1;
            else
                pagenum = Integer.parseInt(page);
            if (pageSize == null)
                pagesize = 10;
            else
                pagesize = Integer.parseInt(pageSize);
            if (startTime == null && endTime != null) {
                map.put("result", -2);//开始时间空
                map.put("message", "开始时间为空");
            } else if (startTime != null && endTime == null) {
                map.put("result", -3);//结束时间空
                map.put("message", "结束时间为空");
            } else {
                List<Briefing> briefings = new ArrayList<>();
                int role = briefingService.findOrganization(Integer.parseInt(struserId)).getRoleId();
                if (role == 2) {
                    briefings = briefingService.selectByUser(startTime, endTime, Integer.parseInt(struserId), status, title);
                } else if (role == 4) {
                    briefings = briefingService.selectSelf(startTime, endTime, Integer.parseInt(struserId), status, title);
                }
                if (briefings.size() != 0) {
                    if (briefings.size() % pagesize == 0) {
                        map.put("totalPage", briefings.size() / pagesize);
                    } else {
                        map.put("totalPage", briefings.size() / pagesize + 1);
                    }
                    if ((pagenum - 1) * pagesize < briefings.size()) {
                        List<Briefing> briefings1 = new ArrayList<>();
                        for (i = 0 + (pagenum - 1) * pagesize; i < briefings.size() && i < pagenum * pagesize; i++) {
                            briefings1.add(briefings.get(i));
                        }
                        map.put("fileList", briefings1);
                        map.put("result", 1);
                        map.put("totalLength", briefings.size());
                        logService.addLog(struserId, "/briefing/findBriefing", "简报事务查看 ", "select", "info", "success");
                    } else {
                        map.put("result", -5); //超页
                        map.put("message", "页数超过上限");
                        logService.addLog(struserId, "/briefing/findBriefing", "简报事务查看 ", "select", "info", "overpage");
                    }
                } else {
                    map.put("result", 0);
                    map.put("message", "无结果");
                    logService.addLog(struserId, "/briefing/findBriefing", "简报事务查看", "select", "info", "none or overpower:" + role);
                }
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/findBriefing", "简报事务查看", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 简报区域查看
     *
     * @param req
     * @return
     */
    @RequestMapping("/findByRegion")
    public @ResponseBody
    Map<Object, Object> findByRegion(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String title = req.getParameter("title");
            String page = req.getParameter("page");
            String pageSize = req.getParameter("pagesize");
            String startTime = req.getParameter("startTime");
            String endTime = req.getParameter("endTime");
            String status = req.getParameter("status");
            String region = req.getParameter("regionId");
            int pagenum;
            int pagesize;
            int i;
            if (page == null)
                pagenum = 1;
            else
                pagenum = Integer.parseInt(page);
            if (pageSize == null)
                pagesize = 10;
            else
                pagesize = Integer.parseInt(pageSize);
            if (startTime == null && endTime != null) {
                map.put("result", -2);//开始时间空
                map.put("message", "开始时间为空");
            } else if (startTime != null && endTime == null) {
                map.put("result", -3);//结束时间空
                map.put("message", "结束时间为空");
            } else {
                List<Briefing> briefings = new ArrayList<>();
                String regionid = null;
                int role = briefingService.findOrganization(Integer.parseInt(struserId)).getRoleId();
                if (role == 2 || role == 1) {
                    int level = regionService.selectByPrimeryKey(region).getLevel();
                    try {
                        briefings = briefingService.selectByRegion(startTime, endTime, regionCut.cut(region), status, title);
                    } catch (Exception e) {
                        map.put("message", e.getMessage());
                        map.put("result", -10);
                        return map;
                    }
                    if (briefings.size() != 0) {
                        if (briefings.size() % pagesize == 0) {
                            map.put("totalPage", briefings.size() / pagesize);
                        } else {
                            map.put("totalPage", briefings.size() / pagesize + 1);
                        }
                        if ((pagenum - 1) * pagesize < briefings.size()) {
                            List<Briefing> briefings1 = new ArrayList<>();
                            for (i = 0 + (pagenum - 1) * pagesize; i < briefings.size() && i < pagenum * pagesize; i++) {
                                briefings1.add(briefings.get(i));
                            }
                            map.put("fileList", briefings1);
                            map.put("result", 1);
                            map.put("totalLength", briefings.size());
                            logService.addLog(struserId, "/briefing/findByRegion", "简报区域查看 ", "select", "info", "success");
                        } else {
                            map.put("result", -5); //超页
                            map.put("message", "页数超过上限");
                            logService.addLog(struserId, "/briefing/findByRegion", "简报区域查看 ", "select", "info", "overpage");
                        }
                    } else {
                        map.put("result", 0);
                        map.put("message", "无结果");
                        logService.addLog(struserId, "/briefing/findByRegion", "简报区域查看 ", "select", "info", "none");
                    }
                } else {
                    map.put("result", -100);
                    map.put("message", "无权限");
                    logService.addLog(struserId, "/briefing/findByRegion", "简报区域查看 ", "select", "info", "overpower:" + role);
                }
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/findByRegion", "简报区域查看", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 查看下属简报
     *
     * @param req
     * @return
     */
    @RequestMapping("/findUnderling")
    public @ResponseBody
    Map<Object, Object> findUnderling(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String title = req.getParameter("title");
            String page = req.getParameter("page");
            String pageSize = req.getParameter("pagesize");
            String startTime = req.getParameter("startTime");
            String endTime = req.getParameter("endTime");
            String status = req.getParameter("status");
            int pagenum;
            int pagesize;
            int i;
            if (page == null)
                pagenum = 1;
            else
                pagenum = Integer.parseInt(page);
            if (pageSize == null)
                pagesize = 10;
            else
                pagesize = Integer.parseInt(pageSize);
            if (startTime == null && endTime != null) {
                map.put("result", -2);//开始时间空
                map.put("message", "开始时间为空");
            } else if (startTime != null && endTime == null) {
                map.put("result", -3);//结束时间空
                map.put("message", "结束时间为空");
            } else {
                List<Briefing> briefings = new ArrayList<>();
                int role = briefingService.findOrganization(Integer.parseInt(struserId)).getRoleId();
                if (role == 2) {
                    briefings = briefingService.selectSelf(startTime, endTime, briefingService.findUnderling(Integer.parseInt(struserId)), status, title);
                    if (briefings.size() != 0) {
                        if (briefings.size() % pagesize == 0) {
                            map.put("totalPage", briefings.size() / pagesize);
                        } else {
                            map.put("totalPage", briefings.size() / pagesize + 1);
                        }
                        if ((pagenum - 1) * pagesize < briefings.size()) {
                            List<Briefing> briefings1 = new ArrayList<>();
                            for (i = 0 + (pagenum - 1) * pagesize; i < briefings.size() && i < pagenum * pagesize; i++) {
                                briefings1.add(briefings.get(i));
                            }
                            map.put("fileList", briefings1);
                            map.put("result", 1);
                            map.put("totalLength", briefings.size());
                            logService.addLog(struserId, "/briefing/findUnderling", "下属简报查看 ", "select", "info", "success:");
                        } else {
                            map.put("result", -5); //超页
                            map.put("message", "页数超过上限");
                            logService.addLog(struserId, "/briefing/findUnderling", "下属简报查看 ", "select", "info", "overpage");
                        }
                    } else {
                        map.put("result", 0);
                        map.put("message", "无结果");
                        logService.addLog(struserId, "/briefing/findUnderling", "下属简报查看 ", "select", "info", "none");
                    }
                } else {
                    map.put("result", -100);
                    map.put("message", "无权限");
                    logService.addLog(struserId, "/briefing/findUnderling", "下属简报查看 ", "select", "info", "overpower:" + role);
                }
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/findUnderling", "下属简报查看", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 获取发送人
     *
     * @param req
     * @return
     */
    @RequestMapping("/getSendTo")
    public @ResponseBody
    Map<Object, Object> getSendTo(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            int role = briefingService.findOrganization(Integer.parseInt(struserId)).getRoleId();
            if (role == 4) {
                map.put("list", briefingService.findLeader(Integer.parseInt(struserId)));
                map.put("result", 1);
                logService.addLog(struserId, "/briefing/getSendTo", "查找发送人", "select", "info", "success");

            } else {
                map.put("result", -100);
                map.put("message", "无权限");
                logService.addLog(struserId, "/briefing/getSendTo", "查找发送人", "select", "info", "overpower:" + role);
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/getSendTo", "查找发送人", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 删除简报
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/delBriefing", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> delBriefing(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            int role = briefingService.findOrganization(Integer.parseInt(struserId)).getRoleId();
            String id = req.getParameter("briefingId");
            if (role == 4 || role == 1) {
                briefingService.deleteByPrimaryKey(Integer.valueOf(id));
                map.put("result", 1);
                logService.addLog(struserId, "/briefing/delBriefing", "删除简报[" + id + "]", "delete", "info", "success");

            } else {
                map.put("result", -100);
                map.put("message", "无权限");
                logService.addLog(struserId, "/briefing/delBriefing", "删除简报", "delete", "info", "overpower:" + role);
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/delBriefing", "删除简报", "delete", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 简报详细信息
     *
     * @param req
     * @return
     */
    @RequestMapping("/briefingDetail")
    public @ResponseBody
    Map<Object, Object> briefingDetail(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String id = req.getParameter("briefingId");
            map.put("briefing", briefingService.selectByPrimaryKey(Integer.valueOf(id)));
            map.put("result", 1);
            logService.addLog(struserId, "/briefing/briefingDetail", "查看简报[" + id + "]", "select", "info", "success");

        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/briefingDetail", "查看简报", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    //模版相关

    /**
     * 模版提交
     *
     * @param map1
     * @return
     */
    @RequestMapping(value = "/templeSubmit", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> templeSubmit(@RequestBody Map<String, String> map1) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String id = map1.get("briefingId");
        String name = map1.get("name"); //模版名字
        String title = map1.get("title"); //简报标题
        String body = map1.get("body");
        String struserId = map1.get("userId") != null && map1.get("userId") != "" ? map1.get("userId") : "-1";
        String token = map1.get("token") != null ? map1.get("token") : "-1";
        String summary = map1.get("summary");
        String regionid = map1.get("regionId");
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            if (id == null || id == "") {
                briefingTempleService.insert(name, title, body, summary, regionid);
                map.put("result", 1);
                logService.addLog(struserId, "/briefing/templeSubmit", "简报模版提交", "insert", "info", "success");
            } else {
                briefingTempleService.updateByPrimaryKeyWithBLOBs(id, name, title, body, summary, regionid);
                map.put("result", 1);
                logService.addLog(struserId, "/briefing/templeSubmit", "简报模版提交", "update", "info", "success");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/templeSubmit", "简报模版提交", "insert", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 删除模版
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/delTemple", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> delTemple(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String id = req.getParameter("templeId");
            briefingTempleService.deleteByPrimaryKey(Integer.valueOf(id));
            map.put("result", 1);
            logService.addLog(struserId, "/briefing/delTemple", "简报模版删除", "delete", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/delTemple", "简报模版删除", "delete", "info", "fail，请登录后操作");
        }
        return map;
    }

    @RequestMapping("/getTempleList")
    public @ResponseBody
    Map<Object, Object> getTempleList(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String region = req.getParameter("regionId");
            map.put("List", briefingTempleService.selectTitleByRegion(region));
            map.put("result", 1);
            logService.addLog(struserId, "/briefing/getTempleList", "得到模版列表", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/getTempleList", "得到模版列表", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 获取模版
     *
     * @param req
     * @return
     */
    @RequestMapping("/getTemple")
    public @ResponseBody
    Map<Object, Object> getTemple(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String id = req.getParameter("templeId");
            map.put("temple", briefingTempleService.selectByPrimaryKey(Integer.valueOf(id)));
            map.put("result", 1);
            logService.addLog(struserId, "/briefing/getTemple", "得到模版[" + id + "]", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/briefing/getTemple", "得到模版", "select", "info", "fail，请登录后操作");
        }
        return map;
    }


    //todo:add log
    /**
     * html拼接转pdf
     *
     * @param req
     * @return
     */
    @RequestMapping("/html2pdf")
    public @ResponseBody
    Map<Object, Object> html2pdf(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String ids = req.getParameter("id");
        String[] strings = ids.split(",");
        List<Briefing> briefings = new ArrayList<>();
        for (String id : strings) {
            briefings.add(briefingService.selectByPrimaryKey(Integer.valueOf(id)));
        }
        Html2pdf html2pdf = new Html2pdf();
        String p = req.getSession().getServletContext().getRealPath("/temp/");
        File file1 = new File(p);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String fileName = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        String path = req.getSession().getServletContext().getRealPath("/temp/") + fileName + ".pdf";
        List<String> s = new ArrayList<>();
        for (Briefing briefing : briefings) {
            s.add(briefing.getBriefingBody());
        }
        html2pdf.parsePdf(html2pdf.getHtmlCode(s), path);
        map.put("return", 1);
        map.put("path", path);
        return map;
    }

    /**
     * 评分
     *
     * @param req
     * @return
     */
    @RequestMapping("giveScore")
    public @ResponseBody
    Map<Object, Object> giveScore(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String id = req.getParameter("id");
        String score = req.getParameter("score");
        try {
            briefingService.updateScore(Double.parseDouble(score), Integer.valueOf(id));
            map.put("result", 1);
        } catch (Exception e) {
            map.put("result", 0);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("/delete")
    public @ResponseBody
    Map<Object, Object> delete(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String id = req.getParameter("id");
        try {
            briefingService.deleteByPrimaryKey(Integer.valueOf(id));
            map.put("result", 1);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", 0);
        }
        return map;
    }

    @RequestMapping("/statistics")
    public @ResponseBody
    Map<Object, Object> statistics(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String regionid = req.getParameter("regionId");
        try {
            String region = regionCut.cut(regionid);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", -10);
            return map;
        }

        return map;
    }


    /**
     * 公众号查看简报列表
     *
     * @param req
     * @return
     */
    @RequestMapping("/getBriefingListForPublic")
    public @ResponseBody
    Map<Object, Object> getBriefingListForPublic(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String page = req.getParameter("page");
        String size = req.getParameter("size");
        try {
            map.put("list", briefingService.findListForPublic(page, size));
            map.put("result", 1);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", 0);
        }
        return map;
    }

    /**
     * 公众号查看简报
     *
     * @param req
     * @return
     */
    @RequestMapping("/getBriefingForPublic")
    public @ResponseBody
    Map<Object, Object> getBriefingForPublic(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String id = req.getParameter("briefingid");
        try {
            map.put("briefing", briefingService.selectByPrimaryKey(Integer.valueOf(id)));
            map.put("result", 1);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", 0);
        }
        return map;
    }
}
