package usermanage.service.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.Dict;
import common.model.FocusRiver;
import common.model.Region;
import common.model.River;
import common.model.RoleAuthority;
import common.model.User;
import common.model.UserOldPassword;
import common.util.Encryption;
import usermanage.mapper.DateInfoMapper;
import usermanage.mapper.UserMapper;
import usermanage.service.UserService;

@Service("UserService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private DateInfoMapper dateInfoMapper;
	
	private Encryption encrypt = new Encryption();
	
	static String reg = "^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).*$";//密码必须是大小写、数字和特殊字符的组合
	
	public Map<Object, Object> findUserByNameAndPassword(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Map<Object, Object> tokenUpdate = new HashMap<Object, Object>();
			map.put("userName",map.get("userName"));
			Md5Hash md5Hash = new Md5Hash(map.get("password"), "", 2);
			String mduuid = md5Hash.toString();
			map.put("password",mduuid);
			if(map.get("type")==null||(!map.get("type").toString().equals("web")&& !map.get("type").toString().equals("app"))) {
				result.put("result", -2);
				result.put("message", "请使用web或app登录");
				return result;
			}
			User user = userMapper.findUserByNameAndPassword(map);
			if (user!= null){
				//Create token
				int userId = user.getUserId();
				String uuid = encrypt.uuidFactory();
				String MDUuid = "";
				MDUuid += uuid + userId;
				String token = encrypt.MD5(MDUuid);
				//更新token
				tokenUpdate.put("userId",userId);
				tokenUpdate.put("token",token);
				tokenUpdate.put("lastTime", new Date());
				userMapper.updateToken(tokenUpdate);
				user.setToken(token);
				if(map.get("type")!=null&&map.get("type").toString().equals("web")) {
					if(user.getRoleId()==5) {
						result.put("result", -2);
						result.put("message", "该账号为app端登录");
					}else {
						result.put("user",user);
						result.put("result", 1);
						result.put("message", "登录成功");
					}
				}else {
					result.put("user",user);
					result.put("result", 1);
					result.put("message", "登录成功");
				}
				return result;
			}else {
				result.put("result", 0);
				result.put("message", "账号或密码不正确");
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", e.getMessage());
		}
		return result;
		
	}

	public Map<Object, Object> queryUserList(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<User> list = userMapper.queryUserList(map);
			int count = userMapper.countUser(map);
			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("countUser", count);
			result.put("userList",list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	public Map<Object, Object> queryUserRoleType(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<User> list = userMapper.queryUserByRoleType(map);
			int count = userMapper.countUserByRoleType(map);
			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("countUser", count);
			result.put("userList",list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	public int checkToken(String struserId, String token) {
		int userId = 0;
		if(struserId!=null&&!(struserId.equals("undifined"))&&!(struserId.equals(""))) {
			userId = Integer.parseInt(struserId);
		}
		String checkToken = userMapper.checkToken(userId);
		if (token.equals(checkToken)){
			return 1;
		}
		return 0;
	}

//	public Map<Object, Object> getUserRole(Map<Object, Object> map) {
//		Map<Object, Object> result = new HashMap<Object, Object>();
//		List<UserRoleRelation> list = userMapper.queryUserRole(map);
//		if(list!=null&&list.size()>0) {
//			result.put("userRole",list);
//			return result;
//		}
//		result.put("result", 0);
//		return result;
//	}

	public List<Integer> queryUserRoleId(Map<Object, Object> map) {
		List<Integer> list = userMapper.queryUserRoleId(map);
		return list;
	}

	public Map<Object, Object> getDataInfo(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<River> riverList =  dateInfoMapper.queryRiver(map);
			List<Region> regionList = dateInfoMapper.queryRegion(map);
			List<FocusRiver> focusRiverList = dateInfoMapper.queryFocusRiver(map);
			List<Dict> dictList = dateInfoMapper.queryDict(map);
			
			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("riverList", riverList);
			result.put("regionList", regionList);
			result.put("focusRiverList", focusRiverList);
			result.put("dictList", dictList);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", e.getMessage());
		}
		return result;
	}

	public Map<Object, Object> updateUser(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			String password = map.get("password").toString();
			Md5Hash md5Hash = new Md5Hash(password, "", 2);
			String mduuid = md5Hash.toString();
			map.put("password",mduuid);
			List<User> userList = userMapper.queryUserList(map);
			if(userList!=null&&userList.size()>0) {//如果旧密码错误返回用户密码错误
				password = map.get("newPassword").toString();
				//验证新密码格式 ----start
				if(password.length()<6){
					throw new Exception ("新密码长度不能小于6位。");
				}
				char[] chars = password.toCharArray();
		        for (int i = 0;i < chars.length;i++) {
		            int times = 0;
		            for(int j = 0;j < chars.length;j++) {
		                if (chars[j] == chars[i]) {
		                    times++;
		                    if(times>2){
		                   	 throw new Exception ("新密码重复字符不能超过2个。");
		                    }
		                }
		            }
		        }
				if(!password.matches(reg)){
					throw new Exception ("新密码必须包含大小写字母、数字和特殊字符。");
				}
				/* 在更新之前需要验证用户的密码是否与历史密码重复：各个口令中相同位置的字符相同的不得多于2个。*/
				List<UserOldPassword> oldPassword = userMapper.findOldPasswordList(map.get("userId").toString());
				for (UserOldPassword uop : oldPassword) { 
					 int times = 0;
					 String str = uop.getOldPassword();
					 int len = str.length() < password.length() ? str.length():password.length();
					 for(int i = 0; i < len;i++){
						 if(password.charAt(i)==str.charAt(i)){
							 times++;
							 if(times>2)
								 throw new Exception("新密码同一位置的字符与旧密码相同的个数不能超过2个");
						 }
					   }
				}
				//验证新密码格式 ----end
				int count = userMapper.updateUser(map);
				/* 将新密码当成历史密码插入历史表中，并将最早的密码删除*/
				userMapper.insertHisPassword(map);
				if(oldPassword.size()>=5){//当旧密码个数大于5个时需要删除最早的记录
					userMapper.deleteHisPaFIFO(oldPassword.get(4).getId());
				}
				
				result.put("result", 1);
				result.put("message", "操作成功");
			}else {
				result.put("result", 0);
				result.put("message", "旧密码错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", e.getMessage());
		}
		return result;
	}

	public User findUserByName(String username) {
		User user= userMapper.findUserByName(username);
		return user;
	}

	public List<RoleAuthority> findRoleInfo(String userName) {
		List<RoleAuthority> list = userMapper.findRoleInfo(userName);
		return list;
	}

	public String register(User user) {
		String password = user.getPassword();
		User temp= userMapper.findUserByName(user.getUserName());
		if(temp != null){
			return "用户名以存在";
		}else if(password.length()<6){
			return "密码长度不能小于6位.";
		}
		char[] chars = password.toCharArray();
        for (int i = 0;i < chars.length;i++) {
            int times = 0;
            for(int j = 0;j < chars.length;j++) {
                if (chars[j] == chars[i]) {
                    times++;
                    if(times>2){
                   	 return "密码重复字符不能超过2个。";
                    }
                }
            }
        }
        if(!password.matches(reg)){
        	return "密码必须包含大小写字母、数字和特殊字符。";
		}
        Md5Hash md5Hash = new Md5Hash(password, "", 2);
		String mduuid = md5Hash.toString();
		user.setPassword(mduuid);
        userMapper.register(user);
        /* 用户注册成功后将第一个密码加入历史密码表中 */
        Map<Object,Object> map = new HashMap<Object,Object>();
        map.put("userId", user.getUserId());
        map.put("newPassword", password);
        userMapper.insertHisPassword(map);
		return "success";
	}

	public User showSelectedOne(String userID) {
		User user = userMapper.showSelectedOne(userID);
		return user;
	}

	public String userModify(User user) {
		String password = user.getPassword();
		if(user.getPassword()!=null&&!"".equals(user.getPassword())){
			if(user.getPassword().length()<6){
				return "密码长度不能小于6位.";
			}
			char[] chars = user.getPassword().toCharArray();
	        for (int i = 0;i < chars.length;i++) {
	            int times = 0;
	            for(int j = 0;j < chars.length;j++) {
	                if (chars[j] == chars[i]) {
	                    times++;
	                    if(times>2){
	                   	 return "密码重复字符不能超过2个。";
	                    }
	                }
	            }
	        }
	        if(!user.getPassword().matches(reg)){
	        	return "密码必须包含大小写字母、数字和特殊字符。";
			}
	        /* 在更新之前需要验证用户的密码是否与历史密码重复：各个口令中相同位置的字符相同的不得多于2个。*/
			List<UserOldPassword> oldPassword = userMapper.findOldPasswordList(user.getUserId()+"");
			for (UserOldPassword uop : oldPassword) { 
				 int times = 0;
				 String str = uop.getOldPassword();
				 int len = str.length() < password.length() ? str.length():password.length();
				 for(int i = 0; i < len;i++){
					 if(password.charAt(i)==str.charAt(i)){
						 times++;
						 if(times>2)
							 return "新密码同一位置的字符与旧密码相同的个数不能超过2个";
					 }
				   }
			}
	        Md5Hash md5Hash = new Md5Hash(password, "", 2);
			String mduuid = md5Hash.toString();
			user.setPassword(mduuid);
	        userMapper.userModify(user);
	        /* 将新密码当成历史密码插入历史表中，并将最早的密码删除*/
	        Map<Object,Object> map = new HashMap<Object,Object>();
	        map.put("userId", user.getUserId());
	        map.put("newPassword", password);
			userMapper.insertHisPassword(map);
			if(oldPassword.size()>=5){//当旧密码个数大于5个时需要删除最早的记录
				userMapper.deleteHisPaFIFO(oldPassword.get(4).getId());
			}
		}else{
			userMapper.userModify(user);
		}
		return "success";
	}

	public String userDelete(String userIdS) {
		userMapper.deleteRoleRelation(userIdS);
		userMapper.userDelete(userIdS);
		return "success";
	}
}
