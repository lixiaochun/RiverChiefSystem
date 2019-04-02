package common.model;

public class RiverRegionRelation {

	private int riverRegionRelationId;
	
	private String riverId;
	
	private int regionId;

	public int getRiverRegionRelationId() {
		return riverRegionRelationId;
	}

	public void setRiverRegionRelationId(int riverRegionRelationId) {
		this.riverRegionRelationId = riverRegionRelationId;
	}

	public String getRiverId() {
		return riverId;
	}

	public void setRiverId(String riverId) {
		this.riverId = riverId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
}
