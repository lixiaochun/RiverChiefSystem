package common.model;


public class PollutantSource {

    private Integer pollutantSourceId;

    private String pollutantSourceName;

    private String baseInfoType;

    private String regionId;

    private String riverId;

    private String focusLevel;

    private String access;

    private String monitor;

    private Double longitute;

    private Double latitude;

    private String port;

    private String remark;

    private Integer warning;

    public Integer getPollutantSourceId() {
        return this.pollutantSourceId;
    }

    public void setPollutantSourceId(Integer pollutantSourceId) {
        this.pollutantSourceId = pollutantSourceId;
    }

    public String getPollutantSourceName() {
        return this.pollutantSourceName;
    }

    public void setPollutantSourceName(String pollutantSourceName) {
        this.pollutantSourceName = pollutantSourceName;
    }

    public String getBaseInfoType() {
        return this.baseInfoType;
    }

    public void setBaseInfoType(String baseInfoType) {
        this.baseInfoType = baseInfoType;
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

    public String getFocusLevel() {
        return this.focusLevel;
    }

    public void setFocusLevel(String focusLevel) {
        this.focusLevel = focusLevel;
    }

    public String getAccess() {
        return this.access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getMonitor() {
        return this.monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
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

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
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