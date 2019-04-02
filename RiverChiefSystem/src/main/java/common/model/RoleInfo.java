package common.model;

import java.util.List;

/**
 * 
 * create by liwq 18/4/11 
 */
public class RoleInfo {
     private int roleId;
     private String roleName;
     private String roleDesc;
     private int organizationId;
     private List<UserBasisInfo> userBasisList;
     
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public int getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	public List<UserBasisInfo> getUserBasisList() {
		return userBasisList;
	}
	public void setUserBasisList(List<UserBasisInfo> userBasisList) {
		this.userBasisList = userBasisList;
	}
     
     
}
