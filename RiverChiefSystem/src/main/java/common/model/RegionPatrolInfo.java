package common.model;

public class RegionPatrolInfo {
	 	private String regionId;
	    private String regionName;
	    private double days;//巡河天数
	    private double times;//巡河次数
	    private double mileage;//巡河里程数
	    private double eventNum;//巡河上报事件数
		public String getRegionId() {
			return regionId;
		}
		public void setRegionId(String regionId) {
			this.regionId = regionId;
		}
		public String getRegionName() {
			return regionName;
		}
		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}
		public double getDays() {
			return days;
		}
		public void setDays(double d) {
			this.days = d;
		}
		public double getTimes() {
			return times;
		}
		public void setTimes(double times) {
			this.times = times;
		}
		public double getMileage() {
			return mileage;
		}
		public void setMileage(double mileage) {
			this.mileage = mileage;
		}
		public double getEventNum() {
			return eventNum;
		}
		public void setEventNum(double eventNum) {
			this.eventNum = eventNum;
		}
	    
	    
}
