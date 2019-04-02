package basemanage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import basemanage.util.WordToPDFUtil;
import onemap.util.Constant;

@Controller
@RequestMapping({ "/wordToPDFController" })
public class WordToPDFController {

	
	@RequestMapping(value = { "/getWordFile" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getWordFile(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			String NginxPath = Constant.getProperty("path");
			savaFile(map,request,"WordToPDF/");
			FileInputStream source = new FileInputStream(NginxPath+map.get("imgurl"));
			File file = new File(NginxPath+"WordToPDF"+"/"+map.get("UUID")+".pdf");
			FileOutputStream target = new FileOutputStream(file);
			WordToPDFUtil.WordToPDF(source,target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map.put("result", 1);
		map.put("message", "操作成功");
	
		return map;
	}
	
	// 保存文件
	public static Map<Object, Object> savaFile(Map<Object, Object> parameter, HttpServletRequest request,
			String saveFilePath) {
		String NginxPath = Constant.getProperty("path");
		// 创建一个多分解的容器
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (cmr.isMultipart(request)) {
			// 将request转换成多分解请求
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			// 创建文件保存名
			String fileName = multipartFile.getOriginalFilename();
			String fileType = "";
			if (fileName.indexOf('.') != -1)
				fileType = fileName.substring(fileName.indexOf('.'), fileName.length());
			UUID uuid = UUID.randomUUID();
			String saveFileName = uuid.toString() + fileType;
			File fileMkdir = new File(NginxPath + saveFilePath);
			if (!fileMkdir.exists())
				fileMkdir.mkdirs();
			parameter.put("imgurl", saveFilePath + saveFileName);
			parameter.put("UUID", uuid);
			File file = new File(fileMkdir + "/" + saveFileName);
			System.out.println("文件保存路径：" + file.getAbsolutePath());
			// 上传文件
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}

		return parameter;
	}
	
}
