package onemap.controller;

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

import onemap.mapper.CatalogTreeMapper;

@Controller
@RequestMapping({ "/catalogTreeController" })
public class CatalogTreeController {
	
	@Autowired
	private CatalogTreeMapper catalogTreeMapper; 
	
	@RequestMapping(value = { "/getBasicInformationTree" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getBasicInformationTree(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		List<Map<Object, Object>> list = catalogTreeMapper.findBasicInformationTree();
		map.put("list", list);
	
		return map;
	}
}
