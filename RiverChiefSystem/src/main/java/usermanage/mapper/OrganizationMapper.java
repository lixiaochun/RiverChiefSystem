package usermanage.mapper;
import java.util.List;
import java.util.Map;

import common.model.Organization;
import common.model.Region;

public interface OrganizationMapper {
	
    public List<Organization> queryOrgList(Map<Object, Object> map);
    
    public List<Organization> queryOrganization(Map<Object, Object> map);

    public int countOrganization(Map<Object, Object> map);

    public int newOrganization(Organization o);

    public int selectOrganizationId();

    public int insertOrganization(List<Organization> organizaition);
    
    public int updateOrganization(List<Organization> organizaition);
}
