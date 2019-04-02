package usermanage.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.model.RoleAuthority;
import common.model.User;
import common.util.ImageUtil;
import usermanage.service.LogService;
import usermanage.service.UserService;


@Controller
@RequestMapping({"/loginController"})
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LogService logService;
	
	/**
	* @Title: login
	* @Des: login
	* @Params: username, password
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/17
	*/
	@RequestMapping(value = {"/loginTest"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  loginTest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		String randCode = request.getParameter("randCode");
		String type = request.getParameter("type");
		map.put("userName", name);
		map.put("password", password);
		map.put("type", type);
		result = userService.findUserByNameAndPassword(map);
			if ((User) result.get("user") != null) {
				User user = (User) result.get("user");
				logService.addLog(Integer.toString(user.getUserId()), "login", "登录", "select", "info","登录成功" + name);
			} else {
				logService.addLog("login", "登录", "select", "info", "登录失败");
			}
		return result;
	}
	
	/**
	* @Title: checkToken
	* @Des: checkToken
	* @Params: userID, token
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/17
	*/
	@RequestMapping(value = {"/checkToken"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	int checkToken(HttpServletRequest request) throws Exception{
		String userId = request.getParameter("userId");
		String token = request.getParameter("token");

		int map = userService.checkToken(userId, token);
		return map;
	}
	
	/**
	* @Title: getImg
	* @Des: 获取验证码图片
	* @Params: userID, token
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/17
	*/
	@RequestMapping(value = {"/getImg"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> getImg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // response.setContentType("image/png");
        Map<Object, Object> imgsrc = ImageUtil.getImage(request,response);
        return imgsrc;
	}
	
	/**
	* @Title: updateUser
	* @Des: 修改用户
	* @Params: userID, token
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/17
	*/
	@RequestMapping(value = {"/updateUser"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> updateUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";
//		int userId = Integer.parseInt(struserId);
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check != 1){
			logService.addLog("updateUser", "修改用户", "select", "info","请登录后操作");
			result.put("result", -1);
			result.put("message", "请登陆后操作");
			return result;
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		String password = request.getParameter("password");
		String newPassword =  request.getParameter("newPassword");
		map.put("password", password);
		map.put("userId", struserId);
		map.put("newPassword", newPassword);
		result = userService.updateUser(map);
		logService.addLog(struserId,"updateUser", "修改用户", "select", "info",result.get("message").toString());
		return result;
	}
	
	/**
	* @Title: getDataInfo
	* @Des: 获取数据字典
	* @Params: userID, token
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/17
	*/
	@RequestMapping(value = {"/getDataInfo"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> getDataInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			int userId = Integer.parseInt(struserId);
			map.put("userId", userId);
			String regionId = request.getParameter("regionId");
//			String riverId = request.getParameter("riverId");
			map.put("regionId", regionId);
//			map.put("riverId", riverId);
			String type = request.getParameter("type");
			map.put("type", type);
			result = userService.getDataInfo(map);
			logService.addLog(struserId,"getDataInfo", "获取数据字典", "select", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("getDataInfo", "获取数据字典", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	@RequestMapping(value = {"/login"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		 Map<Object, Object> map = new HashMap<Object, Object>();
		 Map<Object, Object> result = new HashMap<Object, Object>();
		 String userName = request.getParameter("username");
		 String password = request.getParameter("password");
		 String type = request.getParameter("type");
			map.put("userName", userName);
			map.put("password", password);
			map.put("type", type);
		 Subject subject = SecurityUtils.getSubject();
		 UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		 try{
	            subject.login(token);//会跳到我们自定义的realm中
	            
	            result = userService.findUserByNameAndPassword(map);
	            User user = (User)result.get("user");
	            if(user == null){
	            	return result;
	            }
	            List<RoleAuthority> roleList = userService.findRoleInfo(user.getUserName());
	            Set<String> roleName = new HashSet<String>();
	            Set<String> permissions = new HashSet<String>();
	            for(RoleAuthority role:roleList){
	            	roleName.add(role.getName());
	            	permissions.add(role.getPermission());
	            }
	            result.put("roles", roleName);
	            result.put("permissions", permissions);
	            return result;
	        }catch(Exception e){
	            e.printStackTrace();
	            result.put("result", 0);
	            result.put("message", "用户名或者密码不正确!");
	            return result;
	        }
	}
	
}
