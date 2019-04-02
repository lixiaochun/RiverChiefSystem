package common.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class Patrol {

	private int patrolId;

	@NotBlank(message = "taskName不为null")
	private String taskName;

	@NotNull(message = "userId不为null")
	private int userId;

	// @NotBlank(message = "regionId不为null")
	// @Size(min = 12, max = 12, message = "regionId长度为12位")
	private String regionId;

	@NotBlank(message = "riverId不为null")
	private String riverId;

	private String path;

	// @NotNull(message = "patrolNum不为null")
	private int patrolNum;

	// @NotBlank(message = "patrolPeriod不为null")
	// @Pattern(regexp = "1|2|3|4|5|6", message = "patrolPeriod取值为1或2或3或4或5或6")
	private String patrolPeriod;

	private int completeNum;

	private String grade;

	private String nowGrade;

	private String point;

	private int opuserId;

	private Timestamp publishTime;

	private int patrolStatus;

	private String remark;

	@NotBlank(message = "patrolType不为null")
	@Pattern(regexp = "0|1|2", message = "patrolType取值为0或1或2")
	private String patrolType;

	private String regionName;

	private String userName;

	private String riverName;

	private String patrolPeriodName;

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRiverName() {
		return riverName;
	}

	public void setRiverName(String riverName) {
		this.riverName = riverName;
	}

	public String getPatrolType() {
		return patrolType;
	}

	public void setPatrolType(String patrolType) {
		this.patrolType = patrolType;
	}

	public int getPatrolId() {
		return patrolId;
	}

	public void setPatrolId(int patrolId) {
		this.patrolId = patrolId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRiverId() {
		return riverId;
	}

	public void setRiverId(String riverId) {
		this.riverId = riverId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPatrolNum() {
		return patrolNum;
	}

	public void setPatrolNum(int patrolNum) {
		this.patrolNum = patrolNum;
	}

	public String getPatrolPeriod() {
		return patrolPeriod;
	}

	public void setPatrolPeriod(String patrolPeriod) {
		this.patrolPeriod = patrolPeriod;
	}

	public int getCompleteNum() {
		return completeNum;
	}

	public void setCompleteNum(int completeNum) {
		this.completeNum = completeNum;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getNowGrade() {
		return nowGrade;
	}

	public void setNowGrade(String nowGrade) {
		this.nowGrade = nowGrade;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public int getOpuserId() {
		return opuserId;
	}

	public void setOpuserId(int opuserId) {
		this.opuserId = opuserId;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public int getPatrolStatus() {
		return patrolStatus;
	}

	public void setPatrolStatus(int patrolStatus) {
		this.patrolStatus = patrolStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPatrolPeriodName() {
		return patrolPeriodName;
	}

	public void setPatrolPeriodName(String patrolPeriodName) {
		this.patrolPeriodName = patrolPeriodName;
	}
}
