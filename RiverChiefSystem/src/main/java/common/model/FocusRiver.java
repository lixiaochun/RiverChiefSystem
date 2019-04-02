package common.model;

import java.sql.Timestamp;

public class FocusRiver {

	private int focusRiverId;
	
	private String riverId;
	
	private String riverName;
	
	private int userId;
	
	private Timestamp time;
	
	private String status;

	public int getFocusRiverId() {
		return focusRiverId;
	}

	public void setFocusRiverId(int focusRiverId) {
		this.focusRiverId = focusRiverId;
	}

	public String getRiverId() {
		return riverId;
	}

	public void setRiverId(String riverId) {
		this.riverId = riverId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRiverName() {
		return riverName;
	}

	public void setRiverName(String riverName) {
		this.riverName = riverName;
	}
}
