package usermanage.service;

import java.util.Map;

public interface RoleService {

	public Map<Object,Object> queryRoleList(Map<Object, Object> map);
	
	public Map<Object,Object> newRole(Map<Object, Object> map);
	
	public Map<Object,Object> deleteRole(Map<Object, Object> map);
	
	public Map<Object,Object> updateRole(Map<Object, Object> map);
	
	public Map<Object,Object> updatePermissions(Map<Object, Object> map);
	
	public Map<Object,Object> queryPermissions(Map<Object, Object> map);
}
