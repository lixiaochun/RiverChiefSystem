package filemanage.controller;

import common.model.FileCount;
import common.model.FileDoc;
import common.model.PatrolRecord;
import common.model.RiverLog;
import common.util.Converter;
import common.util.DocToPDFConverter;
import common.util.DocxToPDFConverter;
//import common.util.FtpUtil;
import filemanage.service.DirectoryMapperService;
import filemanage.service.FileDocMapperService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import usermanage.service.LogService;
import usermanage.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * incoming:权限能力限制
 */

@Controller
@RequestMapping("/file")
public class FileDocController {
    @Autowired
    private FileDocMapperService fileDocMapperService;
    @Autowired
    private DirectoryMapperService directoryMapperService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    /**
     * 统计大类下的文件
     *
     * @param req
     * @return
     */
    @RequestMapping("/showByDirectoryId")
    public @ResponseBody
    Map<Object, Object> showByDirectoryId(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            Map<Object, Object> re = fileDocMapperService.countDirectory();
            map.put("countList", re.get("countList"));
            map.put("result", 1);
            logService.addLog(struserId, "/file/showByDirectoryId", "统计各分类数据", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/showByDirectoryId", "统计各分类数据", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 统计一周内新加文件数
     *
     * @param req
     * @return
     */
    @RequestMapping("/showOneWeek")
    public @ResponseBody
    Map<Object, Object> showOneWeek(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            map.put("countWeek", fileDocMapperService.countFileInAWeek());
            map.put("result", 1);
            logService.addLog(struserId, "/file/showOneWeek", "统计一周总量", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/showOneWeek", "统计一周总量", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 显示单个文件详细信息 版本
     *
     * @param req
     * @return
     */
    @RequestMapping("/fileDetail")
    public @ResponseBody
    Map<Object, Object> fileDetail(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String fileNum = req.getParameter("fileNum");
            String page = req.getParameter("page");
            String pageSize = req.getParameter("pagesize");
            Map<Object, Object> re = fileDocMapperService.cutDocPage(page, pageSize, fileDocMapperService.selectByNum(fileNum));
            if (re.get("result").equals(1)) {
                map.put("fileList", re.get("fileList"));
                map.put("result", 1);
                map.put("totalLength", re.get("totalLength"));
                logService.addLog(struserId, "/file/fileDetail", "列出[" + fileNum + "]详细文件列表", "select", "info", "success");
            } else if (re.get("result").equals(-5)) {
                map.put("result", -5); //超页
                map.put("message", "页数超过上限");
                logService.addLog(struserId, "/file/fileDetail", "列出[" + fileNum + "]详细文件列表", "select", "info", "over page");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/fileDetail", "列出详细文件列表", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 获取目录树
     *
     * @param req
     * @return
     */
    @RequestMapping("/getDirectory")
    public @ResponseBody
    Map<Object, Object> getDirectory(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String directoryId = req.getParameter("directoryId");
            map.put("directoryList", directoryMapperService.selectAllWithBeginer(directoryId));
            map.put("result", 1);
            logService.addLog(struserId, "/file/getDirectory", "列出目录列表", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/getDirectory", "列出目录列表", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 获取单个目录名字
     *
     * @param req
     * @return
     */
    @RequestMapping("/getDirectoryById")
    public @ResponseBody
    Map<Object, Object> getDirectoryById(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String directoryId = req.getParameter("directoryId");
            map.put("directoryDetail", directoryMapperService.selectByPrimaryKey(directoryId));
            map.put("result", 1);
            logService.addLog(struserId, "/file/getDirectoryById", "列出单个目录列表", "select", "info", "success");
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/getDirectoryById", "列出单个目录列表", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 上传更新
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> upload(HttpServletRequest req) throws IOException { //上传更新合并 一步判断

        Map<Object, Object> map = new HashMap<Object, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        MultipartFile file = multipartRequest.getFile("file");
        String directory_id = req.getParameter("directory_id");
        String file_num = req.getParameter("file_num");
        String riverID = req.getParameter("river_id");
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            InputStream i = file.getInputStream();
            // xxx:上传ftp
//                String path=;//ftp存放地址
            String path = req.getSession().getServletContext().getRealPath("/file/" + directoryMapperService.selectByPrimaryKey(directory_id).getName());
            Date now = new Date();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = file.getOriginalFilename();
            String url = path + "/" + dateFormat1.format(now) + "_" + fileName;
            File file1 = new File(path);
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
//                FtpUtil ftpUtil = new FtpUtil();
//                ftpUtil.uploadFile(path,dateFormat1.format(now) + "_" + fileName,i); //boolean型
            //ftp完
//                if (type != "doc" || type != "docx") {
//                    map.put("result", -2);
//                    logService.addLog(struserId, "/file/upload", "上传", "insert/update", "info", "fail,文件格式不正确");
//                } else {
            FileDoc fileDoc = fileDocMapperService.selectByNameWithMaxVer(file_num, directory_id);
            if (fileDoc == null) {
                fileDocMapperService.insert(directory_id, name, type, Integer.parseInt(struserId), file_num, url, 1, now, riverID);
            } else {
                fileDocMapperService.insert(fileDoc.getDirectoryId(), fileDoc.getFileName(), type, Integer.parseInt(struserId), fileDoc.getFileNum(), url, fileDoc.getVersion() + 1, now, riverID.equals(null) ? fileDoc.getRiverId() : riverID);
            }
            if (!file1.exists()) {
                file1.mkdirs();
            }
            FileOutputStream o = new FileOutputStream(url);
            byte[] buffer = new byte[1024];
            int length = 0;
            try {
                while ((length = i.read(buffer)) > 0) {
                    o.write(buffer, 0, length);
                }
                map.put("result", 1);
                logService.addLog(struserId, "/file/upload", "上传[" + name + "." + type + "]", "insert/update", "info", "success");
//                }
            } catch (Exception e) {
                map.put("result", 0);
                map.put("message", e.getMessage());

                logService.addLog(struserId, "/file/upload", "上传", "insert/update", "info", "fail,");
            } finally {
                i.close();
                o.close();
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/upload", "上传", "insert/update", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 下载
     *
     * @param req
     * @param res
     * @return
     * @throws IOException
     */
    @RequestMapping("download")
    public @ResponseBody
    Map<Object, Object> download(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            //
            int id = Integer.parseInt(req.getParameter("id"));
            FileDoc fileDoc = fileDocMapperService.selectByPrimaryKey(id);
//            FtpUtil ftpUtil = new FtpUtil();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
            //下载
            File file = new File(fileDoc.getUrl());
            if (file.exists() && file.isFile()) {
                InputStream i = new BufferedInputStream(new FileInputStream(file));
                String filename = fileDoc.getFileName() + "_v" + fileDoc.getVersion() + "." + fileDoc.getFileType();
                filename = URLEncoder.encode(filename, "UTF-8");
                res.addHeader("Content-Disposition", "attachment;filename=" + filename);
                res.addHeader("Content-Length", String.valueOf(file.length()));
                res.setContentType("multipart/form-data");
                //xxx:ftp下载
//                Map<Object,Object> re=ftpUtil.downloadFile(fileDoc.getUrl().replace(dateFormat1.format(fileDoc.getSubmitTime()),""),dateFormat1.format(fileDoc.getSubmitTime()+filename));
//                if ((Boolean) re.get("flag")) {
//                    InputStream i = new ByteArrayInputStream((byte[]) re.get("b"));
//                }
                fileDoc.setDownloadNum(fileDoc.getDownloadNum() + 1);
                fileDocMapperService.updateByPrimaryKey(fileDoc);
                BufferedOutputStream o = new BufferedOutputStream(res.getOutputStream());
                byte[] buff = new byte[1024];
                int len = 0;
                try {
                    while ((len = i.read(buff)) != -1) {
                        o.write(buff, 0, len);
                        o.flush();
                    }
                } catch (IOException e) {
                    map.put("result", -2);
                    map.put("message", e.getMessage());
                } finally {
                    o.close();
                    i.close();
                }
                map.put("result", 1);
                logService.addLog(struserId, "/file/download", "下载[" + filename + "]", "update", "info", "success");
            } else {
                map.put("result", 0);
                map.put("message", "cant find the file,connect to the administrator");
                logService.addLog(struserId, "/file/download", "下载[" + id + "]", "update", "info", "failed,cant find the file\nconnect to the administrator");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/download", "下载", "update", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 删除文件状态
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody
    Map<Object, Object> delete(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        int id = Integer.parseInt(req.getParameter("id"));
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
//            FtpUtil ftpUtil =new FtpUtil();
//            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                FileDoc fileDoc = fileDocMapperService.selectByPrimaryKey(id);
                String filePath = fileDoc.getUrl();
                File file = new File(filePath);
//                String filename = fileDoc.getFileName() + "." + fileDoc.getFileType();
//                        if (file.exists() && file.isFile()) {
//                            file.delete();
//                        }
//                xxx:ftp删除
//                if(ftpUtil.existFile(filePath)){
//                    ftpUtil.deleteFile(fileDoc.getUrl().replace(dateFormat1.format(fileDoc.getSubmitTime()),""),dateFormat1.format(fileDoc.getSubmitTime()+filename));
//                }
                fileDocMapperService.deleteByPrimaryKey(id);
                List<FileDoc> fileDocs = fileDocMapperService.selectByNameAndDir(fileDoc.getFileName(), fileDoc.getDirectoryId());
                map.put("isEmpty", fileDocs.isEmpty());
                map.put("result", 1);
                logService.addLog(struserId, "/file/delete", "删除[" + fileDoc.getFileName() + "_" + fileDoc.getVersion() + "]", "delete", "info", "success");
            } catch (Exception e) {
                map.put("result", 0);
                map.put("message", e.getMessage());

                logService.addLog(struserId, "/file/delete", "删除", "delete", "info", "failed \n");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/delete", "删除", "delete", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 高级搜索
     * 字段 起止时间 目录
     *
     * @param req
     * @return
     */
    @RequestMapping("searchWithOption")
    public @ResponseBody
    Map<Object, Object> searchWithOption(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String directoryId = req.getParameter("directoryId");
            String search = req.getParameter("search");
            String page = req.getParameter("page");
            String pageSize = req.getParameter("pagesize");
            String startTime = req.getParameter("startTime");
            String endTime = req.getParameter("endTime");
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
                logService.addLog(struserId, "/file/selectWithOption", "条件查询包含[" + search + "]目录[" + directoryId + "]的列表,", "select", "info", "failed,lost start time");
            } else if (startTime != null && endTime == null) {
                map.put("result", -3);//结束时间空
                logService.addLog(struserId, "/file/selectWithOption", "条件查询包含[" + search + "]目录[" + directoryId + "]的列表,", "select", "info", "failed,lost end time");
            } else if (startTime == null && endTime == null) {
                List<FileCount> fileCounts = fileDocMapperService.selectByNameAndDirectoryFuzzy(directoryId, search);

                if (fileCounts.isEmpty()) {
                    map.put("result", 0);
                    map.put("message", "无结果");
                    logService.addLog(struserId, "/file/searchWithOption", "查找不到", "select", "info", "success");
                } else {
                    for (i = 0; i < fileCounts.size(); i++) {
                        fileCounts.get(i).setDirectoryName(directoryMapperService.selectByPrimaryKey(fileCounts.get(i).getDirectoryId()).getName());
                    }
                    if (fileCounts.size() % pagesize == 0) {
                        map.put("totalPage", fileCounts.size() / pagesize);
                    } else {
                        map.put("totalPage", fileCounts.size() / pagesize + 1);
                    }
                    if ((pagenum - 1) * pagesize < fileCounts.size()) {
                        List<FileCount> fileCounts1 = new ArrayList<>();
                        for (i = 0 + (pagenum - 1) * pagesize; i < fileCounts.size() && i < pagenum * pagesize; i++) {
                            fileCounts1.add(fileCounts.get(i));
                        }
                        map.put("fileList", fileCounts1);
                        map.put("result", 1);
                        map.put("totalLength", fileCounts.size());
                        logService.addLog(struserId, "/file/searchWithOption", "列出名字包含[" + search + "]目录[" + directoryId + "]的列表第" + pagenum + "页", "select", "info", "success");
                    } else {
                        map.put("result", -5); //超页
                        map.put("message", "页数超过上限");
                        logService.addLog(struserId, "/file/searchWithOption", "列出名字包含[" + search + "]目录[" + directoryId + "]的列表第" + pagenum + "页", "select", "info", "over page");
                    }
                }
            } else {
                List<FileDoc> fileDocs = fileDocMapperService.selectByNameAndDirectoryAndTimeFuzzy(directoryId, search, startTime, endTime);
                if (fileDocs.isEmpty()) {
                    map.put("result", 0);
                    map.put("message", "无结果");
                    logService.addLog(struserId, "/file/searchWithOption", "查找不到", "select", "info", "success");
                } else {
                    if (fileDocs.size() % pagesize == 0) {
                        map.put("totalPage", fileDocs.size() / pagesize);
                    } else {
                        map.put("totalPage", fileDocs.size() / pagesize + 1);
                    }
                    if ((pagenum - 1) * pagesize < fileDocs.size()) {
                        List<FileDoc> fileDocs1 = new ArrayList<>();
                        for (i = 0 + (pagenum - 1) * pagesize; i < fileDocs.size() && i < pagenum * pagesize; i++) {
                            fileDocs1.add(fileDocs.get(i));
                        }
                        map.put("fileList", fileDocs1);
                        map.put("result", 1);
                        map.put("totalLength", fileDocs.size());
                        logService.addLog(struserId, "/file/searchWithOption", "列出名字包含[" + search + "]目录[" + directoryId + "]的列表,日期" + startTime + "->" + endTime + "第" + pagenum + "页", "select", "info", "success");
                    } else {
                        map.put("result", -5); //超页
                        map.put("message", "页数超过上限");
                        logService.addLog(struserId, "/file/searchWithOption", "列出名字包含[" + search + "]目录[" + directoryId + "]的列表,日期" + startTime + "->" + endTime + "第" + pagenum + "页", "select", "info", "over page");
                    }
                }
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/searchWithOption", "列出列表", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * 一般user查询列表只显示最大版本文件列表 细表
     *
     * @param req
     * @return
     */
    @RequestMapping("/fileQuery")
    public @ResponseBody
    Map<Object, Object> fileQuery(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String directoryId = req.getParameter("directoryId");
            String search = req.getParameter("search");
            String page = req.getParameter("page");
            String pageSize = req.getParameter("pagesize");
            String startTime = req.getParameter("startTime");
            String endTime = req.getParameter("endTime");
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
                logService.addLog(struserId, "/file/fileQuery", "条件查询包含[" + search + "]目录[" + directoryId + "]的列表,", "select", "info", "failed,lost start time");
            } else if (startTime != null && endTime == null) {
                map.put("result", -3);//结束时间空
                map.put("message", "结束时间为空");
                logService.addLog(struserId, "/file/fileQuery", "条件查询包含[" + search + "]目录[" + directoryId + "]的列表,", "select", "info", "failed,lost end time");
            } else {
                List<FileDoc> fileDocs = fileDocMapperService.selectByNameAndDirectoryWithMaxVer(directoryId, search, startTime, endTime);
                if (fileDocs.isEmpty()) {
                    map.put("result", 0);
                    logService.addLog(struserId, "/file/fileQuery", "查找不到", "select", "info", "success");
                } else {
                    if (fileDocs.size() % pagesize == 0) {
                        map.put("totalPage", fileDocs.size() / pagesize);
                    } else {
                        map.put("totalPage", fileDocs.size() / pagesize + 1);
                    }
                    if ((pagenum - 1) * pagesize < fileDocs.size()) {
                        List<FileDoc> fileDocs1 = new ArrayList<>();
                        for (i = 0 + (pagenum - 1) * pagesize; i < fileDocs.size() && i < pagenum * pagesize; i++) {
                            fileDocs1.add(fileDocs.get(i));
                        }
                        map.put("fileList", fileDocs1);
                        map.put("result", 1);
                        map.put("totalLength", fileDocs.size());
                        logService.addLog(struserId, "/file/fileQuery", "列出名字包含[" + search + "]目录[" + directoryId + "]的列表第" + pagenum + "页", "select", "info", "success");
                    } else {
                        map.put("result", -5); //超页
                        map.put("message", "页数超过上限");
                        logService.addLog(struserId, "/file/fileQuery", "列出名字包含[" + search + "]目录[" + directoryId + "]的列表第" + pagenum + "页", "select", "info", "over page");
                    }
                }
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/fileQuery", "列出表", "select", "info", "fail，请登录后操作");
        }
        return map;
    }

    /**
     * doc转pdf
     *
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("/doc2pdf")
    public @ResponseBody
    Map<Object, Object> doc2pdf(HttpServletRequest req) throws Exception {
        Map<Object, Object> map = new HashMap<>();
        //检查权限
        String struserId = req.getParameter("userId") != null && req.getParameter("userId") != "" ? req.getParameter("userId") : "-1";
        String token = req.getParameter("token") != null ? req.getParameter("token") : "-1";
        int check = userService.checkToken(struserId, token);
        if (check == 1) {
            String id = req.getParameter("fileId");
            FileDoc fileDoc = fileDocMapperService.selectByPrimaryKey(Integer.valueOf(id));
            Converter converter;
            String path = req.getSession().getServletContext().getRealPath("/temp/");
            File file1 = new File(path);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            String path1 = path + fileDoc.getFileName() + ".pdf";
            File file = new File(path1);
            OutputStream outputStream = new FileOutputStream(file);
            String url = fileDoc.getUrl();
            InputStream inputStream = new FileInputStream(url);

            if (file.exists()) {
                file.delete();
            }
            if (url.endsWith(".docx") || url.endsWith(".doc")) {
                if (url.endsWith(".docx")) {
                    converter = new DocxToPDFConverter(inputStream, outputStream, true, true);
                    converter.convert();
                    FileInputStream fileInputStream = new FileInputStream(file);
                } else if (url.endsWith(".doc")) {
                    converter = new DocToPDFConverter(inputStream, outputStream, true, true);
                    converter.convert();
                    FileInputStream fileInputStream = new FileInputStream(file);
                }
                map.put("pdf", "/temp/" + fileDoc.getFileName() + ".pdf");
                map.put("result", 1);
                logService.addLog(struserId, "/file/doc2pdf", "[" + fileDoc.getFileName() + "]" + "doc转pdf", "select", "info", "success");
            } else {
                map.put("result", -2);
                map.put("message", "doc/docx格式不正确");
                logService.addLog(struserId, "/file/doc2pdf", "[" + fileDoc.getFileName() + "]" + "doc转pdf", "select", "info", "doc/docx格式不正确");
            }
        } else {
            map.put("result", -1);
            map.put("message", "请登录后操作");
            logService.addLog("/file/doc2pdf", "doc转pdf", "select", "info", "fail，请登录后操作");
        }
        return map;
    }


    @RequestMapping("/queryRiverFile")
    public @ResponseBody
    Map<Object, Object> queryRiverFile(HttpServletRequest req) {
        Map<Object, Object> map = new HashMap<>();
        String river = req.getParameter("riverId");
        return fileDocMapperService.selectByRiver(river);
    }
}