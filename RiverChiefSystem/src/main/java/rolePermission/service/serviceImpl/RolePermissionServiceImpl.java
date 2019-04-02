package rolePermission.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.model.Organization;
import common.model.PermissionIno;
import common.model.RoleInfo;
import common.model.UserBasisInfo;
import rolePermission.mapper.RolePermissionMapper;
import rolePermission.service.RolePermissionService;

@Service("RolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService{

	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	
	public List<RoleInfo> queryRoleList() {
		List<RoleInfo> list = rolePermissionMapper.queryRoleList();
		for(RoleInfo rpi : list){
			List<UserBasisInfo>  userList= rolePermissionMapper.queryUserOfRole(rpi.getRoleId());
			rpi.setUserBasisList(userList);
		}
		return list;
	}

	public List<PermissionIno> queryViewPermissionByRoleId(int roleId) {
		List<PermissionIno> piList = rolePermissionMapper.queryViewPermissionByRoleId(roleId);
		return piList;
	}

	public List<PermissionIno> queryViewPermissionOfAll() {
		List<PermissionIno> piList = rolePermissionMapper.queryViewPermissionOfAll();
		return piList;
	}

	/**
	 * 更新之前要先删除旧的权限信息
	 */
	public void updateRolePermissionRelation(int roleId, String[] permissionList) {
		rolePermissionMapper.deleteOldRelation(roleId);//删除旧的权限信息
		if(permissionList.length>0){
		rolePermissionMapper.insertNewRelation(roleId,permissionList);//插入新的一级菜单权限  
		}
		String[] secondPermissionList =  rolePermissionMapper.querySecondMenu(permissionList);//查询父级菜单时上述新一级菜单的权限ID
		if(secondPermissionList.length>0){
		rolePermissionMapper.insertNewSecondRelation(roleId,secondPermissionList);//并把一级菜单下的二级菜单权限赋予该角色
		}
	}

	public List<PermissionIno> querySecondMenuPermissionOfAll() {
		List<PermissionIno> list = rolePermissionMapper.querySecondMenuPermissionOfAll();
		return list;
	}

	public List<PermissionIno> querySecondMenuPermissionByRoleId(int roleId) {
		List<PermissionIno> list = rolePermissionMapper.querySecondMenuPermissionByRoleId(roleId);
		return list;
	}

	public void updateSecondRolePermissionRelation(int roleId, String[] permissionIdStr) {
		rolePermissionMapper.deleteOldSecondRelation(roleId);//删掉旧的二级权限
		if(permissionIdStr.length>0){
		rolePermissionMapper.insertNewSecondRelation(roleId,permissionIdStr);//插入新的二级菜单权限  
		}
	}

	public List<Organization> queryTreeOfOrganization() {
		List<Organization> list =rolePermissionMapper.queryTreeOfOrganization();
		return list;
	}

	public List<UserBasisInfo> queryWithinGroupUser(int roleId) {
		List<UserBasisInfo>  userList= rolePermissionMapper.queryUserOfRole(roleId);
		return userList;
	}

	public List<UserBasisInfo> queryOutsideGroupUser(int roleId, int organizationId) {
		
		List<UserBasisInfo> hasPermissionOutGroup = rolePermissionMapper.queryUserInRelationOutGpoup(roleId,organizationId);
		List<UserBasisInfo> notPermission =  rolePermissionMapper.queryUserNotRole(organizationId);
		hasPermissionOutGroup.addAll(notPermission);
		return hasPermissionOutGroup;
	}

	public void updateUserRoleRelation(int roleId,String[] isChecked) {
		rolePermissionMapper.deleteOldUserRoleRelation(roleId);
		if(isChecked.length>0){
		rolePermissionMapper.insertNewUserRoleRelation(roleId,isChecked);
		}
	}

	public RoleInfo queryRoleInfoById(int roleId) {
		RoleInfo ri = rolePermissionMapper.queryRoleInfoById(roleId);
		return ri;
	}

	public void updateRoleInfo(RoleInfo ri) {
		rolePermissionMapper.updateRoleInfo(ri);
	}

	public void deleteRoleInfo(int roleId) {
		rolePermissionMapper.deleteUserRelationBeforeRole(roleId);
		rolePermissionMapper.deletePermissionRelationBeforeRole(roleId);
		rolePermissionMapper.deleteRole(roleId);
	}

	public void createRoleInfo(RoleInfo ri) {
		rolePermissionMapper.createRoleInfo(ri);
	}

}
