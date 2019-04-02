package usermanage.mapper;


import java.util.List;
import java.util.Map;

import common.model.RoleAuthority;
import common.model.User;
import common.model.UserOldPassword;

public interface UserMapper {

    public User findUserByNameAndPassword(Map<Object, Object> map);

    public void updateToken(Map<Object, Object> tokenUpdate);

    public String checkToken(int userId);

    public List<User> queryUserList(Map<Object, Object> map);

    public int countUser(Map<Object, Object> map);

    // public List<UserRoleRelation> queryUserRole(Map<Object, Object> map);

    public int updateUser(Map<Object, Object> map);

    public List<Integer> queryUserRoleId(Map<Object, Object> map);

    public List<User> queryUserByRoleType(Map<Object, Object> map);

    public int countUserByRoleType(Map<Object, Object> map);

    public int insertUser(List<User> user);

    public int updateUserList(List<User> user);
    
    /*edit by liwq111 start---*/
	public List<RoleAuthority> findRoleInfo(String userName);

	public List<UserOldPassword> findOldPasswordList(String userId);

	public void insertHisPassword(Map<Object, Object> map);

	public void deleteHisPaFIFO(int id);
	
	public User findUserByName(String username);

	public void register(User user);

	public User showSelectedOne(String userId);
	
	public void userModify(User user);
	
	public void deleteRoleRelation(String userIdS);
	
	public void userDelete(String userIdS);
	/*edit by liwq111 end---*/

	

	

	
}
