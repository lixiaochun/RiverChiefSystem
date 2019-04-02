package common.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Region {

	@NotBlank(message = "regionId不为null")
	@Size(min = 12, max = 12, message = "regionId长度为12位")
	private String regionId;

	@NotBlank(message = "regionName不为null")
	private String regionName;

	@NotBlank(message = "parentId不为null")
	@Size(min = 12, max = 12, message = "parentId长度为12位")
	private String parentId;

	@DecimalMin(value = "0", message = "isParent取值为0或1")
	@DecimalMax(value = "1", message = "isParent取值为0或1")
	@NotNull(message = "isParent不为null")
	private int isParent;

	@DecimalMin(value = "0", message = "level取值为0~5")
	@DecimalMax(value = "5", message = "level取值为0~5")
	@NotNull(message = "level不为null")
	private int level;

	private String levelName;

	@Pattern(regexp = "[1-9]\\d*\\.?\\d*", message = "经度为正数，不包括其他字符")
	private String longitute;

	@Pattern(regexp = "[1-9]\\d*\\.?\\d*", message = "经度为正数，不包括其他字符")
	private String latitude;

	private String remark;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLongitute() {
		return longitute;
	}

	public void setLongitute(String longitute) {
		this.longitute = longitute;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
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
		Region region = (Region) object;
		return ((this.regionId != null && this.regionId.equals(region.regionId) && (this.regionName != null && this.regionName.equals(region.regionName))
				&& (this.parentId != null && this.parentId.equals(region.parentId)) && (this.longitute != null && this.longitute.equals(region.longitute))
				&& (this.latitude != null && this.latitude.equals(region.latitude)) && (this.level == region.level) && (this.isParent == region.isParent)));
	}
}
