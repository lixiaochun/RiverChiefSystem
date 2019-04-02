package usermanage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import usermanage.service.LogService;
import usermanage.service.OrganizationService;
import usermanage.service.UserService;

@Controller
@RequestMapping({ "/organizeController" })
public class OrganizeController {

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	/**
	 * @Title: queryOrganization
	 * @Des: 查询组织机构列表
	 * @Params: userId,
	 *          token,organizationId,(organizationName||regionName||level),pageNo,pageSize
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/17
	 */
	@RequestMapping(value = { "/queryOrganization" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryOrganization(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			int pageNo = 0;
			if (request.getParameter("pageNo") != null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));// 当前页
			}
			if (request.getParameter("pageSize") != null) {
				int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页行数
				int min = pageSize * (pageNo - 1);
				map.put("pageSize", pageSize);
				map.put("min", min);
			}
			String regionId = request.getParameter("regionId");
			String organizationName = request.getParameter("organizationName");
			String organizationType = request.getParameter("organizationType");
			String regionName = request.getParameter("regionName");
			String level = request.getParameter("level");
			if (regionId != null && regionId != "") {
				map.put("regionId", regionId);
			}
			if (organizationType != null && organizationType != "") {
				map.put("organizationType", organizationType);
			}
			if (organizationName != null && organizationName != "") {
				map.put("organizationName", organizationName);
			}
			if (regionName != null && regionName != "") {
				map.put("regionName", regionName);
			}
			if (level != null && level != "") {
				map.put("level", level);
			}
			String parentId = request.getParameter("parentId");
			if (parentId != null && parentId != "") {
				map.put("parentId", parentId);
			}
			result = organizationService.queryOrgList(map);
			logService.addLog(struserId, "queryOrganization", "查询组织机构列表", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("queryOrganization", "查询组织机构列表", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: queryUserList
	 * @Des: 查询组织机构下的用户列表
	 * @Params: userId, token, organizationId,pageNo,pageSize,type
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/17
	 */
	@RequestMapping(value = { "/queryUserList" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> queryUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			int pageNo = 0;
			if (request.getParameter("pageNo") != null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo"));// 当前页
			}
			if (request.getParameter("pageSize") != null) {
				int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页行数
				int min = pageSize * (pageNo - 1);
				map.put("pageSize", pageSize);
				map.put("min", min);
			}
			String organizationId = request.getParameter("organizationId");
			String regionId = request.getParameter("regionId");
			String roleType = request.getParameter("roleType");
			String regionIds = request.getParameter("regionIds");
			map.put("regionIds", regionIds);
			map.put("organizationId", organizationId);
			map.put("regionId", regionId);
			map.put("roleType", roleType);
			result = userService.queryUserList(map);
			logService.addLog(struserId, "queryUserList", "查询组织机构下的用户列表", "select", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("queryOrganization", "查询组织机构下的用户列表", "select", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: newOrganization
	 * @Des: 新建组织机构
	 * @Params: userId, token, organizationName, organizationCode , regionId , level
	 *          , parentId
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/17
	 */
	@RequestMapping(value = { "/newOrganization" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> newOrganization(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String organizationName = request.getParameter("organizationName");
			String organizationCode = request.getParameter("organizationCode");
			String organizationType = request.getParameter("organizationType");
			String regionId = request.getParameter("regionId");
			String level = request.getParameter("level");
			String parentId = request.getParameter("parentId");
			String isParent = request.getParameter("isParent");
			map.put("organizationName", organizationName);
			map.put("organizationCode", organizationCode);
			map.put("organizationType", organizationType);
			map.put("regionId", regionId);
			map.put("level", level);
			map.put("parentId", parentId);
			map.put("isParent", isParent);
			result = organizationService.newOrganization(map);
			logService.addLog(struserId, "newOrganization", "新建组织机构", "insert", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("newOrganization", "新建组织机构", "insert", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: addUsertoOrg
	 * @Des: 添加用户到组织机构
	 * @Params: userId, token, organizationId, userIds[,]
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/17
	 */
	@RequestMapping(value = { "/addUsertoOrg" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> addUsertoOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String organizationId = request.getParameter("organizationId");
			String userIds = request.getParameter("userIds");
			map.put("organizationId", organizationId);
			map.put("userIds", userIds);
			result = organizationService.addUsertoOrg(map);
			logService.addLog(struserId, "addUsertoOrg", "添加用户到组织机构", "update", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("addUsertoOrg", "添加用户到组织机构", "update", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}

	/**
	 * @Title: updateUser
	 * @Des: 修改用户信息
	 * @Params: userId, token, userId,organizationId,roleId,realName,status
	 * @Return: map
	 * @Author: lyf
	 * @Date: 2017/10/17
	 */
	@RequestMapping(value = { "/updateUser" }, method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<Object, Object> updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String struserId = request.getParameter("userId") != null ? request.getParameter("userId") : "0";
		String token = request.getParameter("token") != null ? request.getParameter("token") : "";
		int check = userService.checkToken(struserId, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		if (check == 1) {
			String organizationId = request.getParameter("organizationId");
			String operaUserId = request.getParameter("operaUserId");
			String roleId = request.getParameter("roleId");
			String realName = request.getParameter("realName");
			String status = request.getParameter("status");
			map.put("organizationId", organizationId);
			map.put("userId", operaUserId);
			map.put("realName", realName);
			map.put("roleId", roleId);
			map.put("status", status);
			result = organizationService.updateUser(map);
			logService.addLog(struserId, "updateUser", "修改用户信息", "update", "info", result.get("message").toString());
			return result;
		}
		logService.addLog("updateUser", "修改用户信息", "update", "info", "请登录后操作");
		result.put("result", -1);
		result.put("message", "请登陆后操作");
		return result;
	}
}
