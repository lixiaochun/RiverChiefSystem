package common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import common.model.User;
import common.util.MyRealm.Principal;

public class UserUtil {
     public static User getById(int id){
    	String key = String.valueOf(id);
    	User user = (User)CacheUtil.get("userCache", key);
		return user;
     }
     
     public static User getByName(String key){
    	 User user = (User)CacheUtil.get("userCache", key);
 		return user;
      }
     
     public static void putKeyId(User user){
    	 String key = String.valueOf(user.getUserId());
    	 try {
			CacheUtil.put("userCache",key, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
     }
     
     public static void putKeyName(User user){
    	 CacheUtil.put("userCache",user.getUserName(), user);
     }
     
     /**
 	 * 获取当前用户
 	 * @return 取不到返回 new User()
 	 */
 	public static User getUser(){
 		Principal principal = getPrincipal();
 		if (principal!=null){
 			User user = getById(principal.getId());
 			if (user != null){
 				return user;
 			}
 			return new User();
 		}
 		// 如果没有登录，则返回实例化空的User对象。
 		return new User();
 	}
 	
 	//当前认证用户获取
 	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
     
 	//当前用户所拥有的session对象
     public static Session getSession(){
 		try{
 			Subject subject = SecurityUtils.getSubject();
 			Session session = subject.getSession(false);
 			if (session == null){
 				session = subject.getSession();
 			}
 			if (session != null){
 				return session;
 			}
 		}catch (InvalidSessionException e){
 		}
 		return null;
 	}
     
}
