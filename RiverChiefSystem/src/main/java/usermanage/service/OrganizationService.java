package usermanage.service;

import java.util.Map;

public interface OrganizationService {

	/**
	 * 查询组织机构列表
	 */
	public Map<Object, Object> queryOrgList(Map<Object, Object> map );
	
	public Map<Object, Object> newOrganization(Map<Object, Object> map );
	
	public Map<Object, Object> addUsertoOrg(Map<Object, Object> map );
	
	public Map<Object, Object> updateUser(Map<Object, Object> map );
}
