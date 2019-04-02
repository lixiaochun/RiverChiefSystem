package newonemap.controller;

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

import newonemap.mapper.PublicSignsMapper;
import newonemap.model.BasicInformation;

@Controller
@RequestMapping({ "/onemapInformationController" })
public class OnemapInformationController {
	
	@Autowired
	private PublicSignsMapper publicSignsMapper;
	
	@RequestMapping(value = { "/getOnemapInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getOnemapInformation(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		// 处理参数
		String type = request.getParameter("type");
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("type", type);
		List<BasicInformation> list = null;
		if(type.startsWith("21")) {
			 list = publicSignsMapper.findPublicsignsBasicInformation(parameter);
		}
		map.put("items", list);
		map.put("result", 1);
		map.put("message", "操作成功");
	
		return map;
	}

	

}
