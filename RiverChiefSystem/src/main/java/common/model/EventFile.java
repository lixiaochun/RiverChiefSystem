package common.model;

import java.sql.Timestamp;

public class EventFile {

	private int eventFileId;

	private int eventId;

	private int workflowLogId;

	private String fileName;

	private String fileUrl;

	private String type;

	private int status;

	private String smallUrl;

	private Timestamp imageTime;

	private Timestamp submitTime;

	private String remark;

	public int getEventFileId() {
		return eventFileId;
	}

	public void setEventFileId(int eventFileId) {
		this.eventFileId = eventFileId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getWorkflowLogId() {
		return workflowLogId;
	}

	public void setWorkflowLogId(int workflowLogId) {
		this.workflowLogId = workflowLogId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Timestamp getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public Timestamp getImageTime() {
		return imageTime;
	}

	public void setImageTime(Timestamp imageTime) {
		this.imageTime = imageTime;
	}
}
