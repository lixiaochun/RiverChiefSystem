package assessment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import assessment.service.ExcelAssessService;
import common.model.ExcelAssess;
import common.model.ExcelColumn;
import usermanage.service.UserService;

@Controller
@RequestMapping("/excelAssessController")
public class ExcelAssessController {

	@Autowired
	private ExcelAssessService excelAssessService;
	@Autowired
	private UserService userService;
	// excel模板下载
		@RequestMapping(value = "/exportAssessExcel")
		@ResponseBody
		public Map<String,Object> exportAssessExcel(HttpServletRequest request, HttpServletResponse response) {
			Map<String,Object> map = new HashMap<String,Object>();
			// 验证登录权限
			String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
			String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
			int check = userService.checkToken(userId, token);
			if (check != 1) {
				map.put("result", -1);
				map.put("message", "请登录后操作");
				return map;
			}
			
			try {
				excelAssessService.exportTempRules(request,response,true);
				map.put("result", 1);
				map.put("message", "下载成功");
			} catch (Exception e) {
				map.put("result", 0);
				map.put("message", "下载失败");
				e.printStackTrace();
			}
			return  map;
		}
	
	// excel导入
	@RequestMapping(value = "/importAssessExcel")
	@ResponseBody
	public Map<String,Object> importAssessExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		try {
			excelAssessService.importRules(request);
			map.put("result", 1);
			map.put("message", "导入成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  map;
	}
	
	@RequestMapping(value = "/findAssessExcel")
	@ResponseBody
	public Map<String,Object> findAssessExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		try {
			ExcelAssess ea = new ExcelAssess();
			int pageNo = 0;
			int pageSize = 10 ; 
			ea.setMin(0);
			ea.setPageSize(pageSize);
			if(request.getParameter("pageNo")!=null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));//当前页
			}
			if(request.getParameter("pageSize")!=null) {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));//每页行数
				int min = pageSize*(pageNo-1);
				if(min<0) min = 0;
				ea.setMin(min);
				ea.setPageSize(pageSize);
			}
			String name = request.getParameter("name");//考核名称
			String tempFlag = request.getParameter("tempFlag");//考核名称
			if(tempFlag ==null ||"".equals(tempFlag)){
				tempFlag = "全部";
			}
			ea.setName(name);
			ea.setTempFlag(tempFlag);
			List<ExcelAssess> list = excelAssessService.findAllList(ea);
			int count =  excelAssessService.countList(ea);
			map.put("ea", ea);
			map.put("pageNo",pageNo);
			map.put("pageSize", pageSize);
			map.put("count",count);
			map.put("list", list);
			map.put("result", 1);
			map.put("message", "查询成功");
		} catch (Exception e) {
			map.put("result", 0);
			map.put("message", "查询失败");
			e.printStackTrace();
		}
		return  map;
	}
	
	@RequestMapping(value = "/showOneExcel")
	@ResponseBody
	public Map<String,Object> showOneExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object>  map = new HashMap<String,Object>();
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		try {
			String excelId = request.getParameter("excelId");
			String html = excelAssessService.showRules(excelId);
			map.put("html", html);
			map.put("result", 1);
			map.put("message", "查询成功");
		} catch (Exception e) {
			map.put("result", 0);
			map.put("message", "查询失败");
			e.printStackTrace();
		}
		return  map;
	}
	
	@RequestMapping(value = "/selectOneModify")
	@ResponseBody
	public Map<String,Object> selectOneModify(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object>  map = new HashMap<String,Object>();
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		try {
			String excelId = request.getParameter("excelId");
			String html = excelAssessService.modifyRules(excelId);
			map.put("html", html);
			map.put("result", 1);
			map.put("message", "查询成功");
		} catch (Exception e) {
			map.put("result", 0);
			map.put("message", "查询失败");
			e.printStackTrace();
		}
		return  map;
	}
	
	@RequestMapping(value = "/doExcelModify")
	@ResponseBody
	public Map<String,Object> doExcelModify(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object>  map = new HashMap<String,Object>();
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		try {
			String strList = request.getParameter("strList");
			List<ExcelColumn> list = JSON.parseArray(strList,ExcelColumn.class);
			for(ExcelColumn ec:list){
				if(ec.getColumnNum().equals("assessContent")){//第一列  需要查询旧的列表把重复项全部修改
					 ExcelAssess ea = excelAssessService.findOldAssessContent(ec.getRowId());
					 excelAssessService.updateAssessContent(ea,ec.getContent());
				}else if(ec.getColumnNum().equals("assessNorm")){//第二列 需要查询旧的列表把重复项全部修改
					 ExcelAssess ea = excelAssessService.findOldAssessNorm(ec.getRowId());
					 excelAssessService.updateAssessNorm(ea,ec.getContent());
				}else if(ec.getColumnNum().equals("scoreExplain")){
					excelAssessService.updateScoreExplain(ec);
				}else if(ec.getColumnNum().equals("description")){
					excelAssessService.updateExplain(ec);
				}
			}
			map.put("flag", "success");
			map.put("result", 1);
			map.put("message", "查询成功");
		} catch (Exception e) {
			map.put("result", 0);
			map.put("message", "查询失败");
			e.printStackTrace();
		}
		return  map;
	}
	
	@RequestMapping(value = "/deleteExcel")
	@ResponseBody
	public Map<String,Object> deleteExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object>  map = new HashMap<String,Object>();
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		try {
			String excelId = request.getParameter("excelId");
			excelAssessService.deleteExcel(excelId);
			map.put("result", 1);
			map.put("message", "查询成功");
			map.put("flag", "success");
		} catch (Exception e) {
			map.put("result", 0);
			map.put("message", "查询失败");
			e.printStackTrace();
		}
		return  map;
	}
	
	@RequestMapping(value = "/confirmTemp")//确认为模板
	@ResponseBody
	public Map<String,Object> confirmTemp(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object>  map = new HashMap<String,Object>();
		String userId = request.getParameter("userId").equals("undefined") ? "0":request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "":request.getParameter("token");		
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		try {
			String excelId = request.getParameter("");
			excelAssessService.confirmTemp(excelId);
			map.put("result", 1);
			map.put("message", "查询成功");
			map.put("flag", "success");
		} catch (Exception e) {
			map.put("result", 0);
			map.put("message", "查询失败");
			e.printStackTrace();
		}
		return  map;
	}
}

			
