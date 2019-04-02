package common.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Organization {

	@NotNull(message = "organizationId不为null")
	private int organizationId;

	@NotBlank(message = "organizationCode不为null")
	private String organizationCode;

	@NotBlank(message = "organizationName不为null")
	private String organizationName;

	@NotBlank(message = "organizationType不为null")
	@Pattern(regexp = "org|dept", message = "organizationType取值为org或dept")
	private String organizationType;

	@NotBlank(message = "regionId不为null")
	@Size(min = 12, max = 12, message = "regionId长度为12位")
	private String regionId;

	private String regionName;

	@DecimalMin(value = "0", message = "level取值为0~5")
	@DecimalMax(value = "5", message = "level取值为0~5")
	@NotNull(message = "level不为null")
	private int level;

	private String levelName;

	@NotBlank(message = "parentId不为null")
	private int parentId;

	private String parentName;

	private String typeName;

	@DecimalMin(value = "0", message = "isParent取值为0或1")
	@DecimalMax(value = "1", message = "isParent取值为0或1")
	@NotNull(message = "isParent不为null")
	private int isParent;

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if ((object == null) || (object.getClass() != this.getClass()))
			return false;
		// object must be Test at this point
		Organization organization = (Organization) object;
		return (this.organizationId == organization.organizationId && (this.organizationCode != null && this.organizationCode.equals(organization.organizationCode))
				&& (this.organizationName != null && this.organizationName.equals(organization.organizationName))
				&& (this.organizationType != null && this.organizationType.equals(organization.organizationType)) && (this.regionId != null && this.regionId.equals(organization.regionId))
				&& (this.level == organization.level) && (this.parentId == organization.parentId) && (this.isParent == organization.isParent));
	}
}
