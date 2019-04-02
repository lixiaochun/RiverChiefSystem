package common.model;


public class WaterLine {
    
    private Integer waterLineId;

    private String name;

    private String regionId;

    private String riverId;

    private Double normal;

    private Double flood;

    private Double longitute;

    private Double latitude;

    private String remark;

    private Integer warning;

    public Integer getWaterLineId() {
        return this.waterLineId;
    }

    public void setWaterLineId(Integer waterLineId) {
        this.waterLineId = waterLineId;
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

    public Double getNormal() {
        return this.normal;
    }

    public void setNormal(Double normal) {
        this.normal = normal;
    }

    public Double getFlood() {
        return this.flood;
    }

    public void setFlood(Double flood) {
        this.flood = flood;
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

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getWarning() {
        return this.warning;
    }

    public void setWarning(Integer warning) {
        this.warning = warning;
    }
}