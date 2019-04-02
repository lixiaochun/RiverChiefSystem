package usermanage.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.Organization;
import common.model.Region;
import common.model.User;
import usermanage.mapper.OrganizationMapper;
import usermanage.mapper.UserMapper;
import usermanage.service.OrganizationService;

@Service("OrganizationService")
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationMapper organizationMapper;

	@Autowired
	private UserMapper userMapper;

	public Map<Object, Object> queryOrgList(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Organization> list = organizationMapper.queryOrgList(map);
			int count = organizationMapper.countOrganization(map);
				result.put("result", 1);
				result.put("message", "查询成功");
				result.put("countOrganization", count);
				result.put("organizationList", list);
				return result;
		} catch (Exception e) {
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> newOrganization(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Organization organization = new Organization();
			if (map.get("organizationCode") != null) {
				organization.setOrganizationCode(map.get("organizationCode").toString());
			}
			if (map.get("organizationName") != null) {
				organization.setOrganizationName(map.get("organizationName").toString());
			}
			if (map.get("level") != null) {
				organization.setLevel(Integer.parseInt(map.get("level").toString()));
			}
			if (map.get("parentId") != null) {
				organization.setParentId(Integer.parseInt(map.get("parentId").toString()));
			} else {
				organization.setParentId(0);
			}
			if (map.get("regionId") != null) {
				organization.setRegionId(map.get("regionId").toString());
			}

			int count = organizationMapper.newOrganization(organization);
			if (count != 0 && organization.getOrganizationId() != 0) {
				result.put("result", 1);
				result.put("message", "操作成功");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作异常");
		}
		result.put("result", 0);
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> addUsertoOrg(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		Map<Object, Object> search1 = new HashMap<Object, Object>();
		String[] userIds = (map.get("userIds").toString()).split(",");
		for (int i = 0; i < userIds.length; i++) {
			search1.put("userId", userIds[i]);
			List<User> list = userMapper.queryUserList(search1);
			if (list != null && list.size() > 0) {
				//
				//...
				
				try {
					search.put("userId", list.get(0).getUserId());
					search.put("organizationId", Integer.parseInt(map.get("organizationId").toString()));
					userMapper.updateUser(search);
					result.put("result", 1);
					result.put("message", "操作成功");
				} catch (Exception e) {
					result.put("result", 0);
					result.put("message", "操作失败");
				}
			}
		}
		return result;
	}

	public Map<Object, Object> updateUser(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			search.put("userId", map.get("userId"));
			List<User> list = userMapper.queryUserList(search);
			if(list!=null&list.size()>0){
				map.put("userId", list.get(0).getUserId());
				userMapper.updateUser(map);
				result.put("result", 1);
				result.put("message", "更新成功");
			}else {
				result.put("result", 0);
				result.put("message", "无该数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "更新异常");
		}
		return result;
	}

}
