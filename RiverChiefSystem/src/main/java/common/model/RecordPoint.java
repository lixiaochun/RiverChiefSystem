package common.model;

public class RecordPoint {

	private int recordPointId;
	
	private byte[] point;
	
	private String strpoint;
	
	public String getStrpoint() {
		return strpoint;
	}

	public void setStrpoint(String strpoint) {
		this.strpoint = strpoint;
	}

	private int patrolRecordId;
	
	private String imgurl;
	
	private String detail;
	
	private int recordType;

	public int getRecordPointId() {
		return recordPointId;
	}

	public void setRecordPointId(int recordPointId) {
		this.recordPointId = recordPointId;
	}

	public byte[] getPoint() {
		return point;
	}

	public void setPoint(byte[] point) {
		this.point = point;
	}

	public int getPatrolRecordId() {
		return patrolRecordId;
	}

	public void setPatrolRecordId(int patrolRecordId) {
		this.patrolRecordId = patrolRecordId;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}
}
