package filemanage.controller;

import common.model.Notice;
import filemanage.service.NoticeMapperService;

import org.activiti.engine.impl.util.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import usermanage.service.LogService;
import usermanage.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeMapperService noticeMapperService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;


    /**
     * 新闻图片上传
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> imgUpload(HttpServletRequest req) throws IOException {
        Map<Object, Object> map = new HashMap<>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        MultipartFile file = multipartRequest.getFile("file");
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        Date now = new Date();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMM");
        if (check == 1) {

            InputStream is = file.getInputStream();
            //图片上传路径
            String uploadPath = req.getSession().getServletContext().getRealPath("/img/uploadImg/" + dateFormat1.format(now));
            String fileName = java.util.UUID.randomUUID().toString().replaceAll("-", ""); // 采用UUID的方式随机命名
            fileName += file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".", file.getOriginalFilename().length()));
            File file1 = new File(uploadPath);
            if (!file1.exists()) { // 如果路径不存在，创建
                file1.mkdirs();
            }
            File toFile = new File(uploadPath, fileName);
            FileOutputStream os = new FileOutputStream(toFile);
            byte[] buffer = new byte[1024];
            int length = 0;
            try {
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                map.put("result", 1);
                map.put("url", "/img/uploadImg/" + dateFormat1.format(now) + "/" + fileName);
                logService.addLog(struserId, "/notice/imgUpload", "图片上传", "insert", "info", "success");
            } catch (Exception e) {
                map.put("result", 0);
                map.put("message", e.getMessage());

                logService.addLog(struserId, "/notice/imgUpload", "图片上传", "insert", "info", "failed,");
            } finally {
                is.close();
                os.close();
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/notice/imgUpload", "图片上传", "insert", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 新闻提交
     *
     * @param map1
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> submit(@RequestBody Map<String, String> map1) { //@RequestBody 接收前端发来的有且只有的一个json 后接map转换
        Map<Object, Object> map = new HashMap<>();

        String id = map1.get("id");
        String title = map1.get("title");
        String body = map1.get("body");
        String directoryId = map1.get("directoryId");
        String struserId = map1.get("userId") != null && map1.get("userId") != "" ? map1.get("userId") : "-1";
        String token = map1.get("token") != null ? map1.get("token") : "-1";
        String level = map1.get("level");
        String weblink = map1.get("outlink");
        String fountImgUrl = map1.get("imgurl");
        String summary = map1.get("summary");
        String useropen = map1.get("useropen");
        //get不到为null

        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            if (id == null || id == "") {
                try {
                    noticeMapperService.insert(title, body, directoryId, struserId, level, weblink, fountImgUrl, summary, useropen);
                    map.put("result", 1);
                    logService.addLog(struserId, "/notice/submit", "富文本上传", "insert", "info", "success");
                } catch (Exception e) {
                    map.put("result", 0);
                    map.put("message", e.getMessage());

                    logService.addLog(struserId, "/notice/submit", "富文本上传", "insert", "info", "failed");
                }
            } else {
                try {
                    noticeMapperService.updateByPrimaryKeyWithBLOBs(id, title, body, directoryId, struserId, level, weblink, fountImgUrl, summary, useropen);
                    map.put("result", 1);
                    logService.addLog(struserId, "/notice/submit", "富文本更新", "update", "info", "success");
                } catch (Exception e) {
                    map.put("result", 0);
                    map.put("message", e.getMessage());

                    logService.addLog(struserId, "/notice/submit", "富文本更新", "update", "info", "failed");
                }
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/notice/submit", "富文本上传/更新", "insert/update", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 新闻删除
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> delete(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String id = req.getParameter("id");
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            try {
                noticeMapperService.deleteByPrimaryKey(Integer.valueOf(id));
                map.put("result", 1);
                logService.addLog(struserId, "/notice/delete", "删除" + id, "delete", "info", "success");
            } catch (Exception e) {
                map.put("result", 0);
                map.put("message", e.getMessage());

                logService.addLog(struserId, "/notice/delete", "删除", "delete", "info", "failed,");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/notice/delete", "删除", "delete", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 新闻高级搜索
     * 字段 目录 起止时间 等级
     *
     * @param req
     * @return
     */
    @RequestMapping("/selectWithOption")
    public @ResponseBody
    Map<Object, Object> selectWithOption(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String directoryId = req.getParameter("directoryId");
            String title = req.getParameter("search");
            String page = req.getParameter("page");
            String pageSize = req.getParameter("pagesize");
            String startTime = req.getParameter("startTime");
            String endTime = req.getParameter("endTime");
            String level = req.getParameter("level");
            int pagenum;
            int pagesize;
            if (page == null)
                pagenum = 1;
            else
                pagenum = Integer.parseInt(page);
            if (pageSize == null)
                pagesize = 10;
            else
                pagesize = Integer.parseInt(pageSize);
            int i;
            if (startTime == null && endTime != null) {
                map.put("result", -2);//开始时间空
                map.put("message", "开始时间为空");
                logService.addLog(struserId, "/notice/selectWithOption", "条件查询包含[" + title + "]目录[" + directoryId + "]的列表,", "select", "info", "failed,lost start time");
            } else if (startTime != null && endTime == null) {
                map.put("result", -3);//结束时间空
                map.put("message", "结束时间为空");
                logService.addLog(struserId, "/notice/selectWithOption", "条件查询包含[" + title + "]目录[" + directoryId + "]的列表,", "select", "info", "failed,lost end time");
            } else {
                List<Notice> notices = noticeMapperService.selectByDirectoryAndTitleWithTime(directoryId, title, startTime, endTime, level);
                if (notices.isEmpty()) {
                    map.put("result", 0);
                    logService.addLog(struserId, "/notice/selectWithOption", "条件查询包含[" + title + "]目录[" + directoryId + "]的列表,", "select", "info", "success,,but none");
                } else {
                    if (notices.size() % pagesize == 0) {
                        map.put("totalPage", notices.size() / pagesize);
                    } else {
                        map.put("totalPage", notices.size() / pagesize + 1);
                    }
                    if ((pagenum - 1) * pagesize < notices.size()) {
                        List<Notice> notices1 = new ArrayList<>();
                        for (i = 0 + (pagenum - 1) * pagesize; i < notices.size() && i < pagenum * pagesize; i++) {
                            notices1.add(notices.get(i));
                        }
                        map.put("noticeList", notices1);
                        map.put("result", 1);
                        map.put("totalLength", notices.size());
                        logService.addLog(struserId, "/notice/selectWithOption", "条件查询包含[" + title + "]目录[" + directoryId + "]的列表,", "select", "info", "success");
                    } else {
                        map.put("result", -5); //超页
                        map.put("message", "页数超过上限");
                        logService.addLog(struserId, "/notice/selectWithOption", "条件查询包含[" + title + "]目录[" + directoryId + "]的列表,", "select", "info", "failed,overpage");
                    }
                }
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/notice/selectWithOption", "条件查询列表", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 统计新闻数量
     *
     * @param req
     * @return
     */
    @RequestMapping("/countByDirectoryId")
    public @ResponseBody
    Map<Object, Object> countByDirectoryId(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            List<JSONObject> countList = new ArrayList<>();
            for (int i = 5; i <= 6; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("content", i);
                jsonObject.put("totalNum", noticeMapperService.countByDirectory(String.valueOf(i)));
                jsonObject.put("oneWeek", noticeMapperService.countInAWeek(String.valueOf(i)));
                countList.add(jsonObject);
            }
            map.put("countList", countList);
            map.put("result", 1);
            logService.addLog(struserId, "/notice/countByDirectoryId", "统计总量/一周", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/notice/countByDirectoryId", "统计总量/一周", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 获得单个新闻
     *
     * @param req
     * @return
     */
    @RequestMapping("/getNotice")
    public @ResponseBody
    Map<Object, Object> getNotice(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String noticeId = req.getParameter("id");
            map.put("return", noticeMapperService.selectByPrimaryKey(Integer.valueOf(noticeId)));
            map.put("result", 1);
            logService.addLog(struserId, "/notice/getNotice", "查看id=" + noticeId + "正文", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/notice/getNotice", "查看正文", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 新闻按等级展示
     *
     * @param req
     * @return
     */
    @RequestMapping("/showByLevel")
    public @ResponseBody
    Map<Object, Object> showByLevel(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String level = req.getParameter("level");
            String directoryId = req.getParameter("directoryId");
            try {
                map.put("noticeList", noticeMapperService.selectByLevel(level, directoryId));
                map.put("result", 1);
                logService.addLog(struserId, "/notice/showByLevel", "查找level大等于" + level + "的列表", "select", "info", "success");

            } catch (Exception e) {
                map.put("message", e.getMessage());
                map.put("result", 0);
                logService.addLog(struserId, "/notice/showByLevel", "查找level大等于" + level + "的列表", "select", "info", e.getMessage());
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/notice/showByLevel", "查找列表", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 带图新闻首页展示
     *
     * @param req
     * @return
     */
    @RequestMapping("/fountPageShow")
    public @ResponseBody
    Map<Object, Object> fountPageShow(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            map.put("noticeList", noticeMapperService.selectByTimeDesc());
            map.put("result", 1);
            logService.addLog(struserId, "/notice/fountPageShow", "带封面图片列表", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/notice/fountPageShow", "带封面图片列表", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    //todo:add log

    /**
     * 公众号新闻列表
     *
     * @param req
     * @return
     */
    @RequestMapping("/getNewsListForPublic")
    public @ResponseBody
    Map<Object, Object> getNewsListForPublic(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String page = req.getParameter("page");
        String size = req.getParameter("size");
        try {
            map.put("list", noticeMapperService.selectNewsForPublic(page, size));
            map.put("result", 1);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", 0);
        }
        return map;
    }

    /**
     * 公众号查看新闻
     *
     * @param req
     * @return
     */
    @RequestMapping("/getNewsForPublic")
    public @ResponseBody
    Map<Object, Object> getNewsForPublic(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(req.getParameter("newsid"));
        try {
            map.put("news", noticeMapperService.selectByPKForPublic(id));
            map.put("result", 1);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", 0);
        }
        return map;
    }

    /**
     * 带图新闻首页展示
     *
     * @param req
     * @return
     */
    @RequestMapping("/fountPageShowForPublic")
    public @ResponseBody
    Map<Object, Object> fountPageShowForPublic(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        try {
            map.put("noticeList", noticeMapperService.selectWithPicForPublic());
            map.put("result", 1);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("result", 0);
        }
        return map;
    }
}
