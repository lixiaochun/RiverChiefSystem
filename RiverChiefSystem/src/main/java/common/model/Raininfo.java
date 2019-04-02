package common.model;


public class Raininfo {
    
    private Integer raininfoId;

    private Double longitute;

    private Double latitude;

    private String name;

    private Double heavy;

    private String regionId;

    private String riverId;

    private Integer warning;

    private Integer level;

    public Integer getRaininfoId() {
        return this.raininfoId;
    }

    public void setRaininfoId(Integer raininfoId) {
        this.raininfoId = raininfoId;
    }

    public Double getLongitute() {
        return this.longitute;
    }

    public void setLongitute(Double longitute) {
        this.longitute = longitute;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHeavy() {
        return this.heavy;
    }

    public void setHeavy(Double heavy) {
        this.heavy = heavy;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRiverId() {
        return this.riverId;
    }

    public void setRiverId(String riverId) {
        this.riverId = riverId;
    }

    public Integer getWarning() {
        return this.warning;
    }

    public void setWarning(Integer warning) {
        this.warning = warning;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}