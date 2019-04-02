package usermanage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import common.model.RoleAuthority;
import common.model.User;

@Component
public interface UserService {

	public Map<Object, Object> findUserByNameAndPassword(Map<Object, Object> map);
	
	public int checkToken(String userId, String token);
	
	public Map<Object, Object> queryUserList(Map<Object, Object> map);
	
//	public Map<Object, Object> getUserRole(Map<Object, Object> map);
	
	public List<Integer> queryUserRoleId(Map<Object, Object> map);
	
	public Map<Object, Object> getDataInfo(Map<Object, Object> map);
	
	public Map<Object, Object> queryUserRoleType(Map<Object, Object> map);
	
	public Map<Object, Object> updateUser(Map<Object, Object> map);
	
	/* edit by liwq111 ---start*/	
	public User findUserByName(String username);

	public List<RoleAuthority> findRoleInfo(String userName);

	public String register(User user);

	public User showSelectedOne(String userID);
	
	public String userModify(User user);
	
	public String userDelete(String userIdS);
	/* edit by liwq111 ---end*/

	

	

	
}
