package common.model;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class PatrolRecord {

	private int patrolRecordId;

	// @NotBlank(message = "recordName不为null")
	private String recordName;

	@NotNull(message = "patrolId不为null")
	private int patrolId;

	@NotNull(message = "userId不为null")
	private int userId;

	@NotBlank(message = "riverId不为null")
	@Size(min = 14, max = 14, message = "riverId长度为14位")
	private String riverId;

	private String patrolRange;

	private Timestamp startTime;

	private Timestamp endTime;

	private Timestamp updateTime;

	@NotNull(message = "submitTime不为null")
	private Timestamp submitTime;

	private byte[] point;

	@NotBlank(message = "recordStatus不为null")
	private String recordStatus;

	private String remark;

	private String recordDetail;

	// @Pattern(regexp = "[0-9]{2}:[0-9]{2}:[0-9]{2}", message =
	// "totalTime格式为00:00:00")
	private String totalTime;

	private String riverName;

	private String regionName;

	private String taskName;

	private String userName;

	private String realName;

	// @Pattern(regexp = "[1-9]\\d*", message = "totalMileage格式为正数")
	private String totalMileage;

	private List<Event> eventList;

	private int solve;// 事件已处理数量

	private int toSolve;// 事件未处理的数量

	public String getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(String totalMileage) {
		this.totalMileage = totalMileage;
	}

	public String getRiverName() {
		return riverName;
	}

	public void setRiverName(String riverName) {
		this.riverName = riverName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getRecordDetail() {
		return recordDetail;
	}

	public void setRecordDetail(String recordDetail) {
		this.recordDetail = recordDetail;
	}

	public int getPatrolRecordId() {
		return patrolRecordId;
	}

	public void setPatrolRecordId(int patrolRecordId) {
		this.patrolRecordId = patrolRecordId;
	}

	public int getPatrolId() {
		return patrolId;
	}

	public void setPatrolId(int patrolId) {
		this.patrolId = patrolId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte[] getPoint() {
		return point;
	}

	public void setPoint(byte[] point) {
		this.point = point;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getRiverId() {
		return riverId;
	}

	public void setRiverId(String riverId) {
		this.riverId = riverId;
	}

	public Timestamp getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getPatrolRange() {
		return patrolRange;
	}

	public void setPatrolRange(String patrolRange) {
		this.patrolRange = patrolRange;
	}

	public int getSolve() {
		return solve;
	}

	public void setSolve(int solve) {
		this.solve = solve;
	}

	public int getToSolve() {
		return toSolve;
	}

	public void setToSolve(int toSolve) {
		this.toSolve = toSolve;
	}
}
