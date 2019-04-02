package rolePermission.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import common.model.Organization;
import common.model.PermissionIno;
import common.model.RoleInfo;
import common.model.UserBasisInfo;

public interface RolePermissionMapper {
	
	public List<RoleInfo> queryRoleList();
	public List<UserBasisInfo> queryUserOfRole(int roleId);
	
	public List<PermissionIno> queryViewPermissionByRoleId(int roleId);
	public List<PermissionIno> queryViewPermissionOfAll();
	
	public void deleteOldRelation(int roleId);
	public void insertNewRelation(@Param("roleId")int roleId, @Param("list")String[] permissionList);
	public String[] querySecondMenu(@Param("list")String[] permissionList);
	public void insertNewSecondRelation(@Param("roleId")int roleId, @Param("list")String[] secondPermissionList);
	
	public List<PermissionIno> querySecondMenuPermissionOfAll();
	public List<PermissionIno> querySecondMenuPermissionByRoleId(int roleId);
	public void deleteOldSecondRelation(int roleId);
	
	public List<Organization> queryTreeOfOrganization();
	public List<UserBasisInfo> queryUserInRelationOutGpoup(@Param("roleId")int roleId, @Param("organizationId")int organizationId);
	public List<UserBasisInfo> queryUserNotRole(@Param("organizationId")int organizationId);
	
	public void deleteOldUserRoleRelation(int roleId);
	public void insertNewUserRoleRelation(@Param("roleId")int roleId, @Param("list")String[] isChecked);
	
	
	public RoleInfo queryRoleInfoById(int roleId);
	public void updateRoleInfo(RoleInfo ri);
	
	public void deleteUserRelationBeforeRole(int roleId);
	public void deletePermissionRelationBeforeRole(int roleId);
	public void deleteRole(int roleId);
	
	public void createRoleInfo(RoleInfo ri);
	
	
	
}
