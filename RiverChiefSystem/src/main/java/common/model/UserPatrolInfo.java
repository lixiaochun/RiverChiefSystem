package common.model;

public class UserPatrolInfo {//用户巡河信息
    private String userId;
    private String realName;
    private int days;//巡河天数
    private int times;//巡河次数
    private double mileage;//巡河里程数
    private int eventNum;//巡河上报事件数
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMilage(double mileage) {
		this.mileage = mileage;
	}
	public int getEventNum() {
		return eventNum;
	}
	public void setEventNum(int eventNum) {
		this.eventNum = eventNum;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
    
    
}
