package common.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class River {

	@NotBlank(message = "riverId不为null")
	private String riverId;

	@NotBlank(message = "riverName不为null")
	private String riverName;

	@NotNull(message = "riverLevel不为null")
	private Integer riverLevel;

	@DecimalMin(value = "0", message = "isParent取值为0或1")
	@DecimalMax(value = "1", message = "isParent取值为0或1")
	@NotNull(message = "isParent不为null")
	private Integer isParent;

	@NotBlank(message = "parentId不为null")
	private String parentId;

	private String fId;

	@NotBlank(message = "regionId不为null")
	@Size(min = 12, max = 12, message = "regionId长度为12位")
	private String regionId;

	@NotNull(message = "userId不为null")
	private int userId;

	private String remark;

	private String levelName;

	private String regionName;

	private String realName;

	@DecimalMin(value = "0", message = "type取值为0或1")
	@DecimalMax(value = "1", message = "type取值为0或1")
	@NotNull(message = "type不为null")
	private int type;

	public String getRiverId() {
		return this.riverId;
	}

	public void setRiverId(String riverId) {
		this.riverId = riverId;
	}

	public String getRiverName() {
		return this.riverName;
	}

	public void setRiverName(String riverName) {
		this.riverName = riverName;
	}

	public Integer getRiverLevel() {
		return this.riverLevel;
	}

	public void setRiverLevel(Integer riverLevel) {
		this.riverLevel = riverLevel;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if ((object == null) || (object.getClass() != this.getClass()))
			return false;
		// object must be Test at this point
		River river = (River) object;
		return ((this.riverId != null && this.riverId.equals(river.riverId) && (this.riverName != null && this.riverName.equals(river.riverName))
				&& (this.parentId != null && this.parentId.equals(river.parentId)) && (this.regionId != null && this.regionId.equals(river.regionId)) && (this.userId == river.userId)
				&& (this.type == river.type)) && (this.riverLevel == river.riverLevel) && (this.isParent == river.isParent) && (this.fId == river.fId))
				&& (this.remark != null && this.remark.equals(river.remark));
	}

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public String getFId() {
		return fId;
	}

	public void setFId(String fId) {
		this.fId = fId;
	}
}