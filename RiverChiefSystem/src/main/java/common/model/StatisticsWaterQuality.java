package common.model;

public class StatisticsWaterQuality {
    private int qualityCount;
    private String regionId;
    private String regionName;

    private int type;
    private String typeName;

    private int riverCount;
    private float standardPercent;
    private int standardCount;

    private int pollSrcCount251;
    private int pollSrcCount252;
    private int pollSrcCount253;
    private int pollSrcCount254;

    public int getPollSrcCount251() {
        return pollSrcCount251;
    }

    public void setPollSrcCount251(int pollSrcCount251) {
        this.pollSrcCount251 = pollSrcCount251;
    }

    public int getPollSrcCount252() {
        return pollSrcCount252;
    }

    public void setPollSrcCount252(int pollSrcCount252) {
        this.pollSrcCount252 = pollSrcCount252;
    }

    public int getPollSrcCount253() {
        return pollSrcCount253;
    }

    public void setPollSrcCount253(int pollSrcCount253) {
        this.pollSrcCount253 = pollSrcCount253;
    }

    public int getPollSrcCount254() {
        return pollSrcCount254;
    }

    public void setPollSrcCount254(int pollSrcCount254) {
        this.pollSrcCount254 = pollSrcCount254;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getStandardCount() {
        return standardCount;
    }

    public void setStandardCount(int standardCount) {
        this.standardCount = standardCount;
    }

    public float getStandardPercent() {
        return standardPercent;
    }

    public void setStandardPercent(float standardPercent) {
        this.standardPercent = standardPercent;
    }

    public int getRiverCount() {
        return riverCount;
    }

    public void setRiverCount(int riverCount) {
        this.riverCount = riverCount;
    }

    public int getQualityCount() {
        return qualityCount;
    }

    public void setQualityCount(int qualityCount) {
        this.qualityCount = qualityCount;
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
}
