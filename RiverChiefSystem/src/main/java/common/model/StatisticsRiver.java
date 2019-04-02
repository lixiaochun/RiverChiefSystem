package common.model;

public class StatisticsRiver {
    private int riverCount;
    private int ownerCount;
    private String regionId;
    private String regionName;
    private Integer riverLevel;
    private String levelName;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getRiverLevel() {
        return riverLevel;
    }

    public void setRiverLevel(Integer riverLevel) {
        this.riverLevel = riverLevel;
    }

    public int getRiverCount() {
        return riverCount;
    }

    public void setRiverCount(int riverCount) {
        this.riverCount = riverCount;
    }

    public int getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(int ownerCount) {
        this.ownerCount = ownerCount;
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
