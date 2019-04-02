package usermanage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import usermanage.service.LogService;
import usermanage.service.RoleService;
import usermanage.service.UserService;

@Controller
@RequestMapping({"/roleController"})
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LogService logService;
	
	/**
	* @Title: queryRole
	* @Des: 查询角色列表
	* @Params: userId, token, organizationId,pageNo,pageSize,roleName,organizationName,level
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	*/
	@RequestMapping(value = {"/queryRole"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  queryRole(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			int pageNo = 0;
			if(request.getParameter("pageNo")!=null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));//当前页
			}
			if(request.getParameter("pageSize")!=null) {
				int pageSize = Integer.parseInt(request.getParameter("pageSize"));//每页行数
				int min = pageSize*(pageNo-1);
				map.put("pageSize", pageSize);
				map.put("min", min);
			}
			String organizationId = request.getParameter("organizationId");
			String roleName = request.getParameter("roleName");
			String organizationName = request.getParameter("organizationName");
			String level = request.getParameter("level");
			String regionId = request.getParameter("regionId");
			String riverId = request.getParameter("riverId");
			String riverName = request.getParameter("riverName");
			map.put("organizationId", organizationId);
			map.put("organizationId", organizationId);
			map.put("roleName", roleName);
			map.put("organizationId", organizationId);
			map.put("roleName", roleName);
			map.put("organizationName", organizationName);
			map.put("level", level);
			map.put("regionId", regionId);
			map.put("riverId", riverId);
			map.put("riverName", riverName);
			result = roleService.queryRoleList(map);
			logService.addLog(struserId,"queryRole", "查询角色列表", "select", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("queryRole", "查询角色列表", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	 * @Title: newRole
	* @Des: 新建角色
	* @Params: userId, token, organizationId, roleName,roleType,roleDesc
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	 */
	@RequestMapping(value = {"/newRole"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  newRole(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String organizationId = request.getParameter("organizationId");
			String roleName = request.getParameter("roleName");
			String regionId = request.getParameter("regionId");
			String riverId = request.getParameter("riverId");
			String roleType = request.getParameter("roleType");
			String roleDesc = request.getParameter("roleDesc");
			map.put("organization_id", organizationId);
			map.put("role_name",roleName);
			map.put("role_type",roleType);
			map.put("region_id",regionId);
			map.put("river_id",riverId);
			map.put("role_desc",roleDesc);
			result = roleService.newRole(map);
			logService.addLog(struserId,"newRole", "新建角色", "insert", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("newRole", "新建角色", "insert", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	 * @Title: updateRole
	* @Des: 更新角色
	* @Params: userId, token, organizationId, roleId, roleName,roleType,roleDesc,regionId,riverId
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	 */
	@RequestMapping(value = {"/updateRole"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  updateRole(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String organizationId = request.getParameter("organizationId");
			String roleId = request.getParameter("roleId");
			map.put("organizationId", organizationId);
			map.put("roleId",roleId);
			map.put("roleName",request.getParameter("roleName"));
			map.put("roleType",request.getParameter("roleType"));
			map.put("roleDesc",request.getParameter("roleDesc"));
			map.put("riverId", request.getParameter("riverId"));
			map.put("regionId", request.getParameter("regionId"));
			result = roleService.updateRole(map);
			logService.addLog(struserId,"updateRole", "更新角色", "update", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("updateRole", "更新角色", "update", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	 * @Title: deleteRole
	* @Des: 删除角色
	* @Params: userId, token, organizationId, roleId
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	 */
	@RequestMapping(value = {"/deleteRole"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  deleteRole(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String strorganizationId = request.getParameter("organizationId")!=null ? request.getParameter("organizationId"):"";
			String roleId = request.getParameter("roleId")!=null ? request.getParameter("roleId"):"";
			int organizationId = Integer.parseInt(strorganizationId);
			map.put("organizationId", organizationId);
			map.put("roleId",roleId);
			result = roleService.deleteRole(map);
			logService.addLog(struserId,"deleteRole", "删除角色", "delete", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("deleteRole", "删除角色", "delete", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	 * @Title: updatePermissions
	* @Des: 权限分配
	* @Params: userId, token, roleId,menus[,]
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	 */
	@RequestMapping(value = {"/updatePermissions"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  updatePermissions(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String strroleId = request.getParameter("roleId")!=null ? request.getParameter("roleId"):"";
			String menus = request.getParameter("menus")!=null ? request.getParameter("menus"):"";
			int roleId = Integer.parseInt(strroleId);
			map.put("roleId", roleId);
			map.put("menus",menus);
			result = roleService.updatePermissions(map);
			logService.addLog(struserId,"updatePermissions", "权限分配", "update", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("updatePermissions", "权限分配", "update", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
	
	/**
	 * @Title: queryPermissions
	* @Des: 查询权限
	* @Params: userId, token, roleId
	* @Return: map
	* @Author: lyf
	* @Date: 2017/10/18
	 */
	@RequestMapping(value = {"/queryPermissions"}, method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object>  queryPermissions(HttpServletRequest request) throws Exception {
		String struserId = request.getParameter("userId")!=null? request.getParameter("userId"):"0";
		String token = request.getParameter("token")!=null ? request.getParameter("token"):"";	
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(check == 1) {
			String roleId = request.getParameter("roleId");
			map.put("roleId", roleId);
			result = roleService.queryPermissions(map);
			logService.addLog(struserId,"queryPermissions", "查询权限", "select", "info",result.get("message").toString());
			return result;
		}
		logService.addLog("queryPermissions", "查询权限", "select", "info","请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
}
