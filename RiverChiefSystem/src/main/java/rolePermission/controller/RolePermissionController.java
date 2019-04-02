package rolePermission.controller;

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

import common.model.Organization;
import common.model.PermissionIno;
import common.model.RoleInfo;
import common.model.UserBasisInfo;
import rolePermission.service.RolePermissionService;

@Controller
@RequestMapping({ "/rolePermissionController" })
public class RolePermissionController {
	   
		@Autowired
		private RolePermissionService rolePermissionService;
	    
//		@RequiresPermissions(value = { "amdin:view" })
		@RequestMapping({ "/queryRoleList" })
		@ResponseBody
		public Map<String,Object> queryRoleList(HttpServletRequest request, HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				List<RoleInfo> list = rolePermissionService.queryRoleList();
				map.put("roleList", list);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","查询角色分组列表失败");
			}
		return map;
		}
		/*  --------------------------视图维护         start-----------------------------------------------------*/
//		@RequiresPermissions(value = { "amdin:view" })
		@RequestMapping({ "/showViewOfRole" })
		@ResponseBody
		public Map<String,Object> showViewOfRole(HttpServletRequest request, HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				int roleId = Integer.parseInt(request.getParameter("roleId"));
				List<PermissionIno> roleList = rolePermissionService.queryViewPermissionByRoleId(roleId);
				List<PermissionIno> allList = rolePermissionService.queryViewPermissionOfAll();
				map.put("roleId", roleId);
				for (PermissionIno pi : allList) {
					boolean isChecked = false;
					for (PermissionIno rolePI : roleList) {
						if (rolePI.getPermissionId() == pi.getPermissionId())
							isChecked = true;
					}
					pi.setChecked(isChecked);
				}
				map.put("roleId", roleId);
				map.put("rolePermissionList", allList);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","查询一级菜单权限失败");
			}
			return map;
		}
		
//		@RequiresPermissions(value = { "amdin:view" })
		@RequestMapping({ "/saveViewOfRole" })
		@ResponseBody
		public Map<String,Object> saveViewOfRole(HttpServletRequest request, HttpServletResponse response){
			Map<String,Object> map = new HashMap<String, Object>();
			try {
				int roleId = Integer.parseInt(request.getParameter("roleId"));
				String permissionIdStr = request.getParameter("permissionIdStr");
				String[] permissionIdArray = null;
				permissionIdArray = permissionIdStr.split(",");
				for (String str : permissionIdArray) {
					if (str.equals("1")) {
						map.put("message", "无权设置该权限!");
						return map;
					}
				}
				rolePermissionService.updateRolePermissionRelation(roleId, permissionIdArray);
				map.put("message", "success");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","保存一级菜单权限失败");
			}
			return map;
		}
		/*  --------------------------视图维护         end-----------------------------------------------------*/ 
		
		/*  --------------------------权限维护         start-----------------------------------------------------*/
//		@RequiresPermissions(value = { "amdin:view" })
		@RequestMapping({ "/showPermissionManage" })
		@ResponseBody
		public Map<String,Object> showPermissionManage(HttpServletRequest request, HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				int roleId = Integer.parseInt(request.getParameter("roleId"));
				List<PermissionIno> viewList = rolePermissionService.queryViewPermissionOfAll();
				List<PermissionIno> secondMenuList = rolePermissionService.querySecondMenuPermissionOfAll();
				List<PermissionIno> roleList = rolePermissionService.querySecondMenuPermissionByRoleId(roleId);
				for (PermissionIno pi : secondMenuList) {
					boolean isChecked = false;
					for (PermissionIno rolePI : roleList) {
						if (rolePI.getPermissionId() == pi.getPermissionId())
							isChecked = true;
					}
					pi.setChecked(isChecked);
				}
				map.put("roleId", roleId);
				map.put("firstMenu", viewList);
				map.put("secondMenuList", secondMenuList);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","查询二级菜单权限失败");
			}
			return map;
		}
		
//		@RequiresPermissions(value = { "amdin:view" })
		@RequestMapping({ "/savePermissionManage" })
		@ResponseBody
		public Map<String,Object> savePermissionManage(HttpServletRequest request, HttpServletResponse response){
		  	Map<String,Object> map = new HashMap<String,Object>();
			try {
				int roleId = Integer.parseInt(request.getParameter("roleId"));
				String permissionIdStr = request.getParameter("permissionIdStr");
				String[] permissionIdArray = null;
				permissionIdArray = permissionIdStr.split(",");
				for (String str : permissionIdArray) {
					if (str.equals("1")) {
						map.put("message", "无权设置该权限!");
						return map;
					}
				}
				rolePermissionService.updateSecondRolePermissionRelation(roleId, permissionIdArray);
				map.put("message", "success");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","保存二级菜单权限失败");
			}
			return map;
		}
		/*  --------------------------权限维护      end-----------------------------------------------------*/
		
		/*  --------------------------成员维护     start -----------------------------------------------------*/
//		@RequiresPermissions(value = { "amdin:view" })
		@RequestMapping({ "/showUserManage" })
		@ResponseBody
		public Map<String,Object> showUserManage(HttpServletRequest request, HttpServletResponse response){
		  	Map<String,Object> map = new HashMap<String,Object>();
			try {
				int roleId = Integer.parseInt(request.getParameter("roleId"));
				String organizationIdStr = request.getParameter("organizationId") != null
						? request.getParameter("organizationId") : "0";
				int organizationId = Integer.parseInt(organizationIdStr);
				List<Organization> organizationTree = rolePermissionService.queryTreeOfOrganization();//先显示左边组织机构树
				List<UserBasisInfo> inGroupUSer = rolePermissionService.queryWithinGroupUser(roleId);//显示角色组内用户
				List<UserBasisInfo> outGroupUSer = rolePermissionService.queryOutsideGroupUser(roleId, organizationId);//显示角色组外用户
				map.put("roleId", roleId);
				map.put("organizationTree", organizationTree);
				map.put("inGroupUSer", inGroupUSer);
				map.put("outGroupUSer", outGroupUSer);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","查询成员信息失败");
			}
			return map;
		}
		
//		@RequiresPermissions(value = { "amdin:view" })
		@RequestMapping({ "/saveUserManage" })
		@ResponseBody
		public Map<String,Object> saveUserManage(HttpServletRequest request, HttpServletResponse response){
		  	Map<String,Object> map = new HashMap<String,Object>();
			try {
				int roleId = Integer.parseInt(request.getParameter("roleId"));
				String isChecked = request.getParameter("isChecked");
				String[] isCheckedArray = null;
				isCheckedArray = isChecked.split(",");
				rolePermissionService.updateUserRoleRelation(roleId, isCheckedArray);
				map.put("message", "success");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","保存成员信息失败");
			}
			return map;
		}
		/*  --------------------------成员维护     end -----------------------------------------------------*/
		
		/*----------------------------编辑分组     start ---------------------------------------------------*/
//		@RequiresPermissions(value = { "amdin:view" })
		@RequestMapping({ "/showRoleInfoManage" })
		@ResponseBody
		public Map<String,Object> showRoleInfoManage(HttpServletRequest request, HttpServletResponse response){
		  	Map<String,Object> map = new HashMap<String,Object>();
			try {
				int roleId = Integer.parseInt(request.getParameter("roleId"));
				RoleInfo ri = rolePermissionService.queryRoleInfoById(roleId);
				map.put("RoleInfo", ri);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","查询分组信息失败");
			}
			return map;
		}
		
		
//		@RequiresPermissions(value = { "amdin:view" })
		@RequestMapping({ "/updateRoleInfoManage" })
		@ResponseBody
		public Map<String,Object> updateRoleInfoManage(HttpServletRequest request, HttpServletResponse response){
		  	Map<String,Object> map = new HashMap<String,Object>();
		  	try {
				String roleStr = request.getParameter("roleInfo");
				RoleInfo ri = JSON.parseObject(roleStr, RoleInfo.class);
				rolePermissionService.updateRoleInfo(ri);
				map.put("message", "success");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","保存分组信息失败");
			}
			return map;
		}
		/*----------------------------编辑分组     end ---------------------------------------------------*/
		/*----------------------------删除分组      start-------------------------------------------------------*/
		@RequestMapping({ "/deleteRole" })
		@ResponseBody
		public Map<String,Object> deleteRole(HttpServletRequest request, HttpServletResponse response){
		  	Map<String,Object> map = new HashMap<String,Object>();
		  	try {
				int roleId = Integer.parseInt(request.getParameter("roleId"));
				rolePermissionService.deleteRoleInfo(roleId);
				map.put("message", "success");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","删除分组失败");
			}
			return map;
		}
		
		/*----------------------------删除分组      end-------------------------------------------------------*/
		/*----------------------------新增分组      start-------------------------------------------------------*/
		@RequestMapping({ "/createRole" })
		@ResponseBody
		public Map<String,Object> createRole(HttpServletRequest request, HttpServletResponse response){
		  	Map<String,Object> map = new HashMap<String,Object>();
		  	try {
				String roleStr = request.getParameter("roleInfo");
				RoleInfo ri = JSON.parseObject(roleStr, RoleInfo.class);
				rolePermissionService.createRoleInfo(ri);
				map.put("message", "success");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message","新增分组失败");
			}
			return map;
		}
		/*----------------------------新增分组      end-------------------------------------------------------*/
}
