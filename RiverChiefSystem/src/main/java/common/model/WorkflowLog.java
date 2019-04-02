package common.model;

import java.sql.Timestamp;
import java.util.List;

public class WorkflowLog {

	private int workflowLogId;

	private int eventId;

	private int userId;

	private String taskId;

	private String content;

	/*
	 * private String imgurl;
	 * 
	 * private String imgsmallurl;
	 */

	private String logStatus;

	private String logStatusName;

	private String point;

	private String visitDays;

	private Timestamp operaTime;

	private Timestamp acceptTime;

	private String realName;

	private int nextUserId;

	private String nextRealName;

	private String remark;

	private List<EventFile> eventFileList;

	public int getWorkflowLogId() {
		return workflowLogId;
	}

	public void setWorkflowLogId(int workflowLogId) {
		this.workflowLogId = workflowLogId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	// public String getImgurl() {
	// return imgurl;
	// }
	//
	// public void setImgurl(String imgurl) {
	// this.imgurl = imgurl;
	// }

	public String getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public Timestamp getOperaTime() {
		return operaTime;
	}

	public void setOperaTime(Timestamp operaTime) {
		this.operaTime = operaTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLogStatusName() {
		return logStatusName;
	}

	public void setLogStatusName(String logStatusName) {
		this.logStatusName = logStatusName;
	}

	public int getNextUserId() {
		return nextUserId;
	}

	public void setNextUserId(int nextUserId) {
		this.nextUserId = nextUserId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNextRealName() {
		return nextRealName;
	}

	public void setNextRealName(String nextRealName) {
		this.nextRealName = nextRealName;
	}

	public Timestamp getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Timestamp acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getVisitDays() {
		return visitDays;
	}

	public void setVisitDays(String visitDays) {
		this.visitDays = visitDays;
	}

	public List<EventFile> getEventFileList() {
		return eventFileList;
	}

	public void setEventFileList(List<EventFile> eventFileList) {
		this.eventFileList = eventFileList;
	}

	// public String getImgsmallurl() {
	// return imgsmallurl;
	// }
	//
	// public void setImgsmallurl(String imgsmallurl) {
	// this.imgsmallurl = imgsmallurl;
	// }

}
