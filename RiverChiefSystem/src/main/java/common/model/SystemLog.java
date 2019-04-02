package common.model;

import java.sql.Timestamp;

public class SystemLog {

	private int systemLogId;
	
	private int userId;
	
	private String logStatus;
	
	private String operaInterface;
	
	private String operaName;
	
	private String operaType;
	
	private String operaTypeName;
	
	private String logType;
	
	private String logTypeName;
	
	private Timestamp operaTime;
	
	private String logDetail;
	
	private String remark;
	
	private String userName;
	
	private String realName;

	public int getSystemLogId() {
		return systemLogId;
	}

	public void setSystemLogId(int systemLogId) {
		this.systemLogId = systemLogId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}

	public String getOperaInterface() {
		return operaInterface;
	}

	public void setOperaInterface(String operaInterface) {
		this.operaInterface = operaInterface;
	}

	public String getOperaName() {
		return operaName;
	}

	public void setOperaName(String operaName) {
		this.operaName = operaName;
	}

	public String getOperaType() {
		return operaType;
	}

	public void setOperaType(String operaType) {
		this.operaType = operaType;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public Timestamp getOperaTime() {
		return operaTime;
	}

	public void setOperaTime(Timestamp operaTime) {
		this.operaTime = operaTime;
	}

	public String getLogDetail() {
		return logDetail;
	}

	public void setLogDetail(String logDetail) {
		this.logDetail = logDetail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperaTypeName() {
		return operaTypeName;
	}

	public void setOperaTypeName(String operaTypeName) {
		this.operaTypeName = operaTypeName;
	}

	public String getLogTypeName() {
		return logTypeName;
	}

	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
