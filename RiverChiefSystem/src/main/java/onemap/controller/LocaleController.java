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

import onemap.service.PublicsignsService;
import usermanage.service.UserService;

@Controller
@RequestMapping({ "/localeController" })
public class LocaleController {
	
	@Autowired
	private PublicsignsService publicSignsService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取巡河轨迹附近的公示牌河流
	 * point:巡河轨迹点串,格式x,y,x,y,x,y...，riverId:默认显示附近河流，查询结果去掉改id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getPublicsignsRiverName" }, method = {
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> getPublicsignsRiverName(HttpServletRequest request,
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
		String point = request.getParameter("point")!=null ? request.getParameter("point"):"0,0";
		String riverId = request.getParameter("riverId")!=null ? request.getParameter("riverId"):"";
		Map<Object, Object> parameter = new HashMap<Object, Object>();
		parameter.put("point", point);
		parameter.put("riverId", riverId);

		map=publicSignsService.getRiverName(parameter);
		
		return map;
	}
	
}
