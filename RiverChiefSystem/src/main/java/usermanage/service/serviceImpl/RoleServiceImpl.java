package usermanage.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.Menu;
import common.model.Permissions;
import common.model.Role;
import common.model.RolePermissionsRelation;
import usermanage.mapper.RoleMapper;
import usermanage.service.RoleService;

@Service("RoleService")
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper  roleMapper;

	public Map<Object, Object> queryRoleList(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Role> list = roleMapper.queryRoleList(map);
			int count = roleMapper.countRole(map);
			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("countRole", count);
			result.put("roleList",list);
		} catch (Exception e) {
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> newRole(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			map.put("status", 1);
			int count = roleMapper.newRole(map);
			if(count!=0) {
				result.put("result",1);
				result.put("message", "新增成功");
			}else {
				result.put("result", 0);
				result.put("message", "新增失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "新增异常");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> updatePermissions(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			//获取角色的所有权限
			search.put("roleId", map.get("roleId"));
			List<RolePermissionsRelation> list = roleMapper.queryRolePermissionsRelation(search);
			List<RolePermissionsRelation> list1 = new ArrayList<RolePermissionsRelation>();
			String[] menus = (map.get("menus").toString()).split(",");
			for (int i = 0; i < menus.length; i++) {
				search.put("menuId", menus[i]);
				//查找权限
				List<Permissions> permissionsList = roleMapper.queryPermissions(search);
				search.put("permissionsId", permissionsList.get(0).getPermissionsId());
				//角色与权限的关系
				list1 = roleMapper.queryRolePermissionsRelation(search);	
				if(list1!=null&&list1.size()>0) {			
					for(int j=0;j<list.size();j++) {
						if(list.get(j).getRelationId()==(list1.get(0).getRelationId())) {
							list.remove(j);
						}
					}
					if(list1.get(0).getStatus()==0) {
						search.put("status", 1);
						search.put("permissionsId", permissionsList.get(0).getPermissionsId());
						roleMapper.updateRolePermissionsRelation(search);
					}
				}else {
					RolePermissionsRelation rpr = new RolePermissionsRelation();
					rpr.setRoleId(Integer.parseInt(map.get("roleId").toString()));
					rpr.setPermissionsId(permissionsList.get(0).getPermissionsId());
					rpr.setStatus(1);
					roleMapper.newRolePermissionsRelation(rpr);	
				}
				list1.clear();
			}
			for(RolePermissionsRelation rpr: list) {
				if(rpr.getStatus()==1) {
					map.put("status", 0);
					map.put("permissionsId", rpr.getPermissionsId());
					roleMapper.updateRolePermissionsRelation(map);
				}
			}
			result.put("result", 1);
			result.put("message", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "更新失败");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> deleteRole(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Role> list = roleMapper.queryRoleList(map);
			if (list != null && list.size() > 0) {
				Role role = list.get(0);
				map.put("roleId", role.getRoleId());
//				map.put("organizationId", role.getOrganizationId());
				map.put("status", 0);
				roleMapper.updateRole(map);
				result.put("result", 1);
				result.put("message", "删除成功");
				return result;
			}else {
				result.put("result", 0);
				result.put("message", "无该对象");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "删除异常");
		}
		return result;
	}

	public Map<Object, Object> queryPermissions(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Menu> list = roleMapper.queryMenuList(map);
			result.put("menuList",list);
			result.put("message", "查询成功");
			return result;
		}catch (Exception e) {
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> updateRole(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			search.put("roleId", map.get("roleId"));
			List<Role> list = roleMapper.queryRoleList(search);
			if (list != null && list.size() > 0) {
				Role role = list.get(0);
				map.put("roleId", role.getRoleId());
				roleMapper.updateRole(map);
				result.put("result", 1);
				result.put("message", "修改成功");
			}else {
				result.put("result", 0);
				result.put("message", "无改角色");
			}
		} catch (Exception e) {
			result.put("result", 0);
			result.put("message", "修改异常");
		}
		return result;
	}
}
