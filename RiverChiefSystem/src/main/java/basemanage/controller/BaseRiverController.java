package basemanage.controller;

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

import basemanage.mapper.BaseRiverMapper;
import basemanage.model.BaseRiver;

@Controller
@RequestMapping({ "/baseRiverController" })
public class BaseRiverController {
	
	@Autowired
	private BaseRiverMapper baseRiverMapper;
	
	@RequestMapping(value = { "/getBaseRiverInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getBaseRiverInformation(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		String id = request.getParameter("id");
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("id", id);
		
		List<BaseRiver> listRiver = baseRiverMapper.selectBaseRiver(parameter);
		map.put("items", listRiver);
		map.put("result", 1);
		map.put("message", "操作成功");
		
		
		return map;
		
	}
	
	
	

}
