package common.model;

import java.util.List;

public class FdStatistical {

	private String regionId;

	private String regionName;

	// solve 巡河次数
	// toSolve 缺勤次数
	// remark 缺勤日期
	// userName 用户姓名
	private List<PatrolRecord> patrolRecordList;

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

	public List<PatrolRecord> getPatrolRecordList() {
		return patrolRecordList;
	}

	public void setPatrolRecordList(List<PatrolRecord> patrolRecordList) {
		this.patrolRecordList = patrolRecordList;
	}

}
