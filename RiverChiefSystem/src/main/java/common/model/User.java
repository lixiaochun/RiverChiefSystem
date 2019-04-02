package common.model;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class User {

	@NotNull(message = "userId不为null")
	private int userId;

	@NotBlank(message = "userName不为null")
	private String userName;

	private String password;

	@NotBlank(message = "realName不为null")
	@Pattern(regexp = "[\u4e00-\u9fa5]{2,10}", message = "realName名字只能为中文，且长度为2-10")
	private String realName;

	private Timestamp createTime;

	private Timestamp modifiedTime;

	private Timestamp lastTime;

	@Email(message = "邮箱格式错误")
	private String email;

	@Pattern(regexp = "(^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\\\d{8}$)" + "|(^(0\\\\d{2}-\\\\d{8}(-\\\\d{1,4})?)|(0\\\\d{3}-\\\\d{7,8}(-\\\\d{1,4})?)$)", message = "号码有误")
	private String phone;

	@NotBlank(message = "regionId不为null")
	@Size(min = 12, max = 12, message = "regionId长度为12位")
	private String regionId;

	private Region region;

	private String regionName;

	private List<River> riverList;

	private String orgionRole;

	private String orgionOrganization;

	private String token;

	@NotNull(message = "organizationId不为null")
	private int organizationId;

	private String organizationName;

	@NotNull(message = "roleId不为null")
	private int roleId;

	private String roleName;

	private String roleShortName;

	private String roleType;

	@DecimalMin(value = "0", message = "status取值为0或1")
	@DecimalMax(value = "1", message = "status取值为0或1")
	@NotNull(message = "status不为null")
	private int status;

	private String remark;

	private int min; // 分页开始位置

	private int pageSize;// 分页页码

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Timestamp getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrgionRole() {
		return orgionRole;
	}

	public void setOrgionRole(String orgionRole) {
		this.orgionRole = orgionRole;
	}

	public String getOrgionOrganization() {
		return orgionOrganization;
	}

	public void setOrgionOrganization(String orgionOrganization) {
		this.orgionOrganization = orgionOrganization;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<River> getRiverList() {
		return riverList;
	}

	public void setRiverList(List<River> riverList) {
		this.riverList = riverList;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastTime() {
		return lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if ((object == null) || (object.getClass() != this.getClass()))
			return false;
		// object must be Test at this point
		User user = (User) object;
		return ((this.userId == user.userId) && (this.userName != null && this.userName.equals(user.userName)) && (this.roleId == user.roleId)
				&& (this.regionId != null && this.regionId.equals(user.regionId)) && (this.organizationId == user.organizationId) && (this.realName != null && this.realName.equals(user.realName))
				&& (this.email != null && this.email.equals(user.email)) && (this.phone != null && this.phone.equals(user.phone))
				&& (this.orgionOrganization != null && this.orgionOrganization.equals(user.orgionOrganization)) && (this.orgionRole != null && this.orgionRole.equals(user.orgionRole))
				&& (this.status == user.status));
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleShortName() {
		return roleShortName;
	}

	public void setRoleShortName(String roleShortName) {
		this.roleShortName = roleShortName;
	}
}
