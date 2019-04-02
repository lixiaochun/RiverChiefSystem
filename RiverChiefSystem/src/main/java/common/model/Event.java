package common.model;

import java.sql.Timestamp;
import java.util.List;

public class Event {

	private int eventId;

	private String eventName;

	private String eventCode;

	private String eventLevel;

	private String eventLevelName;

	private int patrolRecordId;

	// private int sanType;
	//
	// private String sanTypeName;

	private String reportType;

	private String reportTypeName;

	private String eventContent;

	// private String fileurl;
	//
	// private String beforeimgurl;
	//
	// private String beforeimgsmallurl;

	private String eventPoint;

	private String address;

	private String eventType;

	private String eventTypeName;

	private String operaType;// 处理情况

	private String operaTypeName;

	private String riverId;

	private String riverName;

	private String regionId;

	private String regionName;

	private String problemType;

	private String problemTypeName;

	private Timestamp eventTime;

	private Timestamp updateTime;

	private Timestamp limitTime;

	private String rectification;

	// private int visitDays;

	private int userId;

	private String realName;

	private String recordName;

	// private String processInstanceId;

	private int eventStatus;

	private String remark;

	private PatrolRecord patrolRecord;

	private String patrolRange;

	private Timestamp startTime;

	private Timestamp endTime;

	private int isAccountability;

	private String nowRealName;

	private List<EventFile> eventFileList;

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(String eventLevel) {
		this.eventLevel = eventLevel;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	// public String getBeforeimgurl() {
	// return beforeimgurl;
	// }
	//
	// public void setBeforeimgurl(String beforeimgurl) {
	// this.beforeimgurl = beforeimgurl;
	// }

	public String getEventPoint() {
		return eventPoint;
	}

	public void setEventPoint(String eventPoint) {
		this.eventPoint = eventPoint;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public Timestamp getEventTime() {
		return eventTime;
	}

	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}

	public Timestamp getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Timestamp limitTime) {
		this.limitTime = limitTime;
	}

	public String getRectification() {
		return rectification;
	}

	public void setRectification(String rectification) {
		this.rectification = rectification;
	}

	// public int getVisitDays() {
	// return visitDays;
	// }
	//
	// public void setVisitDays(int visitDays) {
	// this.visitDays = visitDays;
	// }

	// public int getNowUserId() {
	// return nowUserId;
	// }
	//
	// public void setNowUserId(int nowUserId) {
	// this.nowUserId = nowUserId;
	// }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	// public String getProcessInstanceId() {
	// return processInstanceId;
	// }
	//
	// public void setProcessInstanceId(String processInstanceId) {
	// this.processInstanceId = processInstanceId;
	// }

	public String getRiverId() {
		return riverId;
	}

	public void setRiverId(String riverId) {
		this.riverId = riverId;
	}

	public String getEventLevelName() {
		return eventLevelName;
	}

	public void setEventLevelName(String eventLevelName) {
		this.eventLevelName = eventLevelName;
	}

	public String getReportTypeName() {
		return reportTypeName;
	}

	public void setReportTypeName(String reportTypeName) {
		this.reportTypeName = reportTypeName;
	}

	public String getEventTypeName() {
		return eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getRiverName() {
		return riverName;
	}

	public void setRiverName(String riverName) {
		this.riverName = riverName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getOperaType() {
		return operaType;
	}

	public void setOperaType(String operaType) {
		this.operaType = operaType;
	}

	public String getOperaTypeName() {
		return operaTypeName;
	}

	public void setOperaTypeName(String operaTypeName) {
		this.operaTypeName = operaTypeName;
	}

	public int getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(int eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	// public String getBeforeimgsmallurl() {
	// return beforeimgsmallurl;
	// }
	//
	// public void setBeforeimgsmallurl(String beforeimgsmallurl) {
	// this.beforeimgsmallurl = beforeimgsmallurl;
	// }
	//
	// public String getFileurl() {
	// return fileurl;
	// }
	//
	// public void setFileurl(String fileurl) {
	// this.fileurl = fileurl;
	// }

	public String getProblemTypeName() {
		return problemTypeName;
	}

	public void setProblemTypeName(String problemTypeName) {
		this.problemTypeName = problemTypeName;
	}

	// public int getSanType() {
	// return sanType;
	// }
	//
	// public void setSanType(int sanType) {
	// this.sanType = sanType;
	// }
	//
	// public String getSanTypeName() {
	// return sanTypeName;
	// }
	//
	// public void setSanTypeName(String sanTypeName) {
	// this.sanTypeName = sanTypeName;
	// }

	public int getPatrolRecordId() {
		return patrolRecordId;
	}

	public void setPatrolRecordId(int patrolRecordId) {
		this.patrolRecordId = patrolRecordId;
	}

	public List<EventFile> getEventFileList() {
		return eventFileList;
	}

	public void setEventFileList(List<EventFile> eventFileList) {
		this.eventFileList = eventFileList;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public PatrolRecord getPatrolRecord() {
		return patrolRecord;
	}

	public void setPatrolRecord(PatrolRecord patrolRecord) {
		this.patrolRecord = patrolRecord;
	}

	public String getPatrolRange() {
		return patrolRange;
	}

	public void setPatrolRange(String patrolRange) {
		this.patrolRange = patrolRange;
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

	public int getIsAccountability() {
		return isAccountability;
	}

	public void setIsAccountability(int isAccountability) {
		this.isAccountability = isAccountability;
	}

	public String getNowRealName() {
		return nowRealName;
	}

	public void setNowRealName(String nowRealName) {
		this.nowRealName = nowRealName;
	}
}
