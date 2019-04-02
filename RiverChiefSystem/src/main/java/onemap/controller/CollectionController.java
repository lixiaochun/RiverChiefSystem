package onemap.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import onemap.service.CollectionRiverService;
import usermanage.service.UserService;

/**
 * @author wangwch
 * 河道收藏功能
 */
@Controller
@RequestMapping({ "/collectionController" })
public class CollectionController {

	@Autowired
	private CollectionRiverService collectionRiverService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查询收藏河道列表
	 * userId：查询用户id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/selectCollectionRiver" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> selectCollectionRiver(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		map = collectionRiverService.findCollectionRiverByUserId(Integer.valueOf(userId));
		
		return map;
	}
	
	/**
	 * 新增河道收藏
	 * userId:用户id,riverId:河流id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/insertCollectionRiver" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> insertCollectionRiver(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		String riverId = request.getParameter("riverId");
		parameter.put("userId", userId);
		parameter.put("riverId", riverId);
		map = collectionRiverService.insertCollectionRiver(parameter);
	
		return map;
	}
	
	/**
	 * 删除收藏河流
	 * userId:用户id,riverId:河流id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/deleteCollectionRiver" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> deleteCollectionRiver(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		// 验证登录权限
		String userId = request.getParameter("userId").equals("undefined") ? "0" : request.getParameter("userId");
		String token = request.getParameter("token").equals("undefined") ? "" : request.getParameter("token");
		int check = userService.checkToken(userId, token);
		if (check != 1) {
			map.put("result", -1);
			map.put("message", "请登录后操作");
			return map;
		}
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		String riverId = request.getParameter("riverId");
		parameter.put("userId", userId);
		parameter.put("riverId", riverId);
		map = collectionRiverService.deleteCollectionRiverById(parameter);
	
		return map;
	}
	
}
