package common.model;

public class Statistics {

	private String pt;
	
	private int count;
	
	private String percent;
	
	private String problemTypeName;
	
	private String regionId;
	
	private String regionName;
	
	private String reportTypeName;
	
	private String patrolSum;
	
	private String completeSum;//巡河次数
	
	private String patrolgrade;
	
	private String recordGrade;//达标次数
	
	private String longitute;

    private String latitude; 
	
	private int date;

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getProblemTypeName() {
		return problemTypeName;
	}

	public void setProblemTypeName(String problemTypeName) {
		this.problemTypeName = problemTypeName;
	}

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

	public String getReportTypeName() {
		return reportTypeName;
	}

	public void setReportTypeName(String reportTypeName) {
		this.reportTypeName = reportTypeName;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getPatrolSum() {
		return patrolSum;
	}

	public void setPatrolSum(String patrolSum) {
		this.patrolSum = patrolSum;
	}

	public String getCompleteSum() {
		return completeSum;
	}

	public void setCompleteSum(String completeSum) {
		this.completeSum = completeSum;
	}

	public String getPatrolgrade() {
		return patrolgrade;
	}

	public void setPatrolgrade(String patrolgrade) {
		this.patrolgrade = patrolgrade;
	}

	public String getRecordGrade() {
		return recordGrade;
	}

	public void setRecordGrade(String recordGrade) {
		this.recordGrade = recordGrade;
	}

	public String getLongitute() {
		return longitute;
	}

	public void setLongitute(String longitute) {
		this.longitute = longitute;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}
