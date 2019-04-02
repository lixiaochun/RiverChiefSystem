package common.model;

public class PermissionIno {
		private int permissionId;
		private String permission;
		private String permissionName;
		private int menuId;
		private int status;//权限等级   1.一级菜单  2。二级菜单 以此类推，可以控制到按钮级
		
		private boolean  isChecked;
		
		public int getPermissionId() {
			return permissionId;
		}
		public void setPermissionId(int permissionId) {
			this.permissionId = permissionId;
		}
		public String getPermission() {
			return permission;
		}
		public void setPermission(String permission) {
			this.permission = permission;
		}
		public String getPermissionName() {
			return permissionName;
		}
		public void setPermissionName(String permissionName) {
			this.permissionName = permissionName;
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
		public boolean isChecked() {
			return isChecked;
		}
		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}
		
		
		
}
