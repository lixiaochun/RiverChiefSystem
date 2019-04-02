package rolePermission.service;

import java.util.List;

import common.model.Organization;
import common.model.PermissionIno;
import common.model.RoleInfo;
import common.model.UserBasisInfo;

public interface RolePermissionService {
		public List<RoleInfo> queryRoleList();
		
		public List<PermissionIno> queryViewPermissionByRoleId(int roleId);
		public List<PermissionIno> queryViewPermissionOfAll();
		
		public void updateRolePermissionRelation(int roleId, String[] permissionIdStr);

		public List<PermissionIno> querySecondMenuPermissionOfAll();
		public List<PermissionIno> querySecondMenuPermissionByRoleId(int roleId);

		public void updateSecondRolePermissionRelation(int roleId, String[] permissionIdStr);

		public List<Organization> queryTreeOfOrganization();
		public List<UserBasisInfo> queryWithinGroupUser(int roleId);
		public List<UserBasisInfo> queryOutsideGroupUser(int roleId,int organizationId);

		public void updateUserRoleRelation(int roleId, String[] isChecked);

		public RoleInfo queryRoleInfoById(int roleId);
		public void updateRoleInfo(RoleInfo ri);

		public void deleteRoleInfo(int roleId);

		public void createRoleInfo(RoleInfo ri);
}
