package common.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import common.model.RoleAuthority;
import common.model.User;
import usermanage.service.UserService;

public class MyRealm extends AuthorizingRealm{
	
	@Autowired
	private UserService userService;
    
	@Override  // 为当前登陆成功的用户授予权限和角色，已经登陆成功了
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		@SuppressWarnings("unchecked")
		List<Principal> list = principals.asList(); //获取用户名
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<RoleAuthority> roleList = userService.findRoleInfo(list.get(0).getUserName());
      //用户 角色名可能包含多个，用set集合可以掉一些重复的角色名字，方便同一个角色名可以拥有多个权限，并且方便角色之间的权限有出现交集，可以去掉重复的权限
        Set<String> roleName = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();
        for(RoleAuthority role:roleList){
        	roleName.add(role.getName());
        	permissions.add(role.getPermission());
        }
        authorizationInfo.setRoles(roleName);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
	}
    
	
	@Override // 验证当前登录的用户，获取认证信息
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		 String userName = (String) token.getPrincipal(); // 获取用户名
		 User user = userService.findUserByName(userName);
	        if(user != null && !"".equals(user.getUserName())) {
	        	AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(new Principal(user), user.getPassword(),"myRealm");
//	        	AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(new Principal(user), user.getPassword(),credentialsSalt,"myRealm");
	            UserUtil.putKeyId(user);
	            UserUtil.putKeyName(user);
	            return authcInfo;
	        } else {
	            return null;
	        }       
	}
	
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		private int id; // 编号
		private String userName; // 登录名
		private String name; // 姓名
		

		public Principal(User user) {
			
			this.id = user.getUserId();//user.getId();
			this.userName = user.getUserName();
			this.name = user.getRealName();
		}

		public int getId() {
			return id;
		}

		public String getUserName() {
			return userName;
		}

		public String getName() {
			return name;
		}
		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try{
				return (String) UserUtil.getSession().getId();
			}catch (Exception e) {
				return "";
			}
		}
	}
}
