package basemanage.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import basemanage.mapper.BaseGridMapper;
import basemanage.model.BaseGrid;

@Controller
@RequestMapping({ "/baseGridController" })
public class BaseGridController {

	@Autowired
	private BaseGridMapper baseGridMapper; 
	
	@RequestMapping(value = { "/getBaseGridInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getBaseGridInformation(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		String id = request.getParameter("id");
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("id", id);

		List<BaseGrid> listGrid = baseGridMapper.selectBaseGrid(parameter);
		map.put("items", listGrid);
		map.put("result", 1);
		map.put("message", "操作成功");
	
		return map;
	}
	
	@RequestMapping(value = { "/getBaseGridGeojsonById" }, method = {
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getBaseGridGeojsonById(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		String id = request.getParameter("id");
		try {
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition",
				"attachment;filename=" + URLEncoder.encode(id+".geojson", "UTF-8"));
			//设置允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			OutputStream os = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("/");
			System.out.println("filepath:"+path+"/WEB-INF/classes/basemanage/geojson/"+ id +".geojson");
			FileInputStream fis=new FileInputStream(path+"/WEB-INF/classes/basemanage/geojson/"+ id +".geojson");
			byte []bytes=new byte[1024];
			int length = 0 ;
			while((length = fis.read(bytes))!=-1){
				os.write(bytes, 0, length);		
			}			
			fis.close();
			os.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	
	
	
}
