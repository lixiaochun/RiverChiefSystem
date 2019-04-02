package common.model;

public class Permissions {

	private int permissionsId;
	
	private String permissionsName;
	
	private int menuId;
	
	private int status;

	public int getPermissionsId() {
		return permissionsId;
	}

	public void setPermissionsId(int permissionsId) {
		this.permissionsId = permissionsId;
	}

	public String getPermissionsName() {
		return permissionsName;
	}

	public void setPermissionsName(String permissionsName) {
		this.permissionsName = permissionsName;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
