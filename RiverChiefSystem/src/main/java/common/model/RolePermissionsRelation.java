package common.model;

public class RolePermissionsRelation {

	private int relationId;
	
	private int permissionsId;
	
	private int roleId;
	
	private int status;

	public int getRelationId() {
		return relationId;
	}

	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}

	public int getPermissionsId() {
		return permissionsId;
	}

	public void setPermissionsId(int permissionsId) {
		this.permissionsId = permissionsId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
