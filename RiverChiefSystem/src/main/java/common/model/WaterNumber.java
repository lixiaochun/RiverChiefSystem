package common.model;


public class WaterNumber {
    
    private Integer waterNumberId;

    private String name;

    private String regionId;

    private String riverId;

    private Double normal;

    private Double flood;

    private Double storage;

    private Double longitute;

    private Double latitude;

    private Integer warning;

    private String remark;

    public Integer getWaterNumberId() {
        return this.waterNumberId;
    }

    public void setWaterNumberId(Integer waterNumberId) {
        this.waterNumberId = waterNumberId;
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

    public Double getStorage() {
        return this.storage;
    }

    public void setStorage(Double storage) {
        this.storage = storage;
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

    public Integer getWarning() {
        return this.warning;
    }

    public void setWarning(Integer warning) {
        this.warning = warning;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}