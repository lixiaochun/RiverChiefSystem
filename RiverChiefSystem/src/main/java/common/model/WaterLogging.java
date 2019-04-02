package common.model;


public class WaterLogging {
    
    private Integer waterLoggingId;

    private Double longitute;

    private Double latitude;

    private String name;

    private String regionId;

    private String riverId;

    public Integer getWaterLoggingId() {
        return this.waterLoggingId;
    }

    public void setWaterLoggingId(Integer waterLoggingId) {
        this.waterLoggingId = waterLoggingId;
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
}