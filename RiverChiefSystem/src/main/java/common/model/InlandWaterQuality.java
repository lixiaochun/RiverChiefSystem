package common.model;


public class InlandWaterQuality {

    private Integer inlandWaterQualityId;

    private String name;

    private String sectionLevel;

    private String monitorType;

    private String regionId;

    private String riverId;

    private Double longitute;

    private Double latitude;

    private String requirement;

    private String baseInfoType;

    private String pollutionIndex;

    private String url;

    private String model;

    private String remark;

    private Integer warning;

    public Integer getInlandWaterQualityId() {
        return this.inlandWaterQualityId;
    }

    public void setInlandWaterQualityId(Integer inlandWaterQualityId) {
        this.inlandWaterQualityId = inlandWaterQualityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSectionLevel() {
        return this.sectionLevel;
    }

    public void setSectionLevel(String sectionLevel) {
        this.sectionLevel = sectionLevel;
    }

    public String getMonitorType() {
        return this.monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
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

    public String getRequirement() {
        return this.requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getBaseInfoType() {
        return this.baseInfoType;
    }

    public void setBaseInfoType(String baseInfoType) {
        this.baseInfoType = baseInfoType;
    }

    public String getPollutionIndex() {
        return this.pollutionIndex;
    }

    public void setPollutionIndex(String pollutionIndex) {
        this.pollutionIndex = pollutionIndex;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
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