package common.model;

import java.sql.Timestamp;

public class Conference {
	
	private int conferenceId;
	private String conferenceName;
	private String theme;
	private Timestamp time;
	private String site;
	private String advertisingMap;
	private String notice;
	private String announcements;
	private String contact;
	private String agenda;
	private String arrangement;
	private String manual;
	private int status;
	private String stepStatus;
	private String followThrough;
	private String remark;
	
	
	public int getConferenceId() {
		return conferenceId;
	}
	public void setConferenceId(int conferenceId) {
		this.conferenceId = conferenceId;
	}
	public String getConferenceName() {
		return conferenceName;
	}
	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getAdvertisingMap() {
		return advertisingMap;
	}
	public void setAdvertisingMap(String advertisingMap) {
		this.advertisingMap = advertisingMap;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(String announcements) {
		this.announcements = announcements;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAgenda() {
		return agenda;
	}
	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}
	public String getArrangement() {
		return arrangement;
	}
	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}
	public String getManual() {
		return manual;
	}
	public void setManual(String manual) {
		this.manual = manual;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getStepStatus() {
		return stepStatus;
	}
	public void setStepStatus(String stepStatus) {
		this.stepStatus = stepStatus;
	}
	
	public String getFollowThrough() {
		return followThrough;
	}
	public void setFollowThrough(String followThrough) {
		this.followThrough = followThrough;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
