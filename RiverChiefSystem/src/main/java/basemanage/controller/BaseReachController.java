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

import basemanage.mapper.BaseReachMapper;
import basemanage.model.BaseReach;
import basemanage.model.BaseReachInformation;

@Controller
@RequestMapping({ "/baseReachController" })
public class BaseReachController {
	
	@Autowired
	private BaseReachMapper baseReachMapper;
	
	@RequestMapping(value = { "/getBaseReachInformation" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getBaseReachInformation(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		String id = request.getParameter("id");

		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("id", id);

		List<BaseReach> listGrid = baseReachMapper.selectBaseReach(parameter);
		map.put("items", listGrid);
		map.put("result", 1);
		map.put("message", "操作成功");
	
		return map;
	}
	
	@RequestMapping(value = { "/getBaseReachInformationById" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getBaseReachInformationById(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		String id = request.getParameter("id");
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("id", id);

		BaseReachInformation baseReachInformation = baseReachMapper.selectBaseReachInformationById(parameter);
		map.put("items", baseReachInformation);
		map.put("result", 1);
		map.put("message", "操作成功");
	
		return map;
	}
	
	
}
