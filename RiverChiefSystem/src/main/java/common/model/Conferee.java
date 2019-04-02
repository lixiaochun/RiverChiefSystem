package common.model;

public class Conferee {
	
	private int confereeId;
	private int conferenceId;
	private int userId;
	private String confereeName;
	private String contactInfo;
	private String company;
	private String duty;
	private int status;
	
	
	public int getConfereeId() {
		return confereeId;
	}
	public void setConfereeId(int confereeId) {
		this.confereeId = confereeId;
	}
	public int getConferenceId() {
		return conferenceId;
	}
	public void setConferenceId(int conferenceId) {
		this.conferenceId = conferenceId;
	}
	public String getConfereeName() {
		return confereeName;
	}
	public void setConfereeName(String confereeName) {
		this.confereeName = confereeName;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
}
