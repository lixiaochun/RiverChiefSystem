package common.model;

import java.util.Date;

public class Assessment {
    private Integer assessId;

    private String riverId;

    private Date submitTime;

    private int year;

    private int month;

    private Double organizationalScore;

    private Double waterEnvironmentScore;

    private Double strategyScore;

    private Double complaintScore;

    private Double publicityScore;

    private Double additionalScore;

    private String detailArray;

    private Integer assessUserId;

    private String regionName;

    private String riverName;

    private String regionId;

    private int ownerId;

    public Integer getAssessId() {
        return assessId;
    }

    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
    }

    public String getRiverId() {
        return riverId;
    }

    public void setRiverId(String riverId) {
        this.riverId = riverId == null ? null : riverId.trim();
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Double getOrganizationalScore() {
        return organizationalScore;
    }

    public void setOrganizationalScore(Double organizationalScore) {
        this.organizationalScore = organizationalScore;
    }

    public Double getWaterEnvironmentScore() {
        return waterEnvironmentScore;
    }

    public void setWaterEnvironmentScore(Double waterEnvironmentScore) {
        this.waterEnvironmentScore = waterEnvironmentScore;
    }

    public Double getStrategyScore() {
        return strategyScore;
    }

    public void setStrategyScore(Double strategyScore) {
        this.strategyScore = strategyScore;
    }

    public Double getComplaintScore() {
        return complaintScore;
    }

    public void setComplaintScore(Double complaintScore) {
        this.complaintScore = complaintScore;
    }

    public Double getPublicityScore() {
        return publicityScore;
    }

    public void setPublicityScore(Double publicityScore) {
        this.publicityScore = publicityScore;
    }

    public Double getAdditionalScore() {
        return additionalScore;
    }

    public void setAdditionalScore(Double additionalScore) {
        this.additionalScore = additionalScore;
    }

    public String getDetailArray() {
        return detailArray;
    }

    public void setDetailArray(String detailArray) {
        this.detailArray = detailArray == null ? null : detailArray.trim();
    }

    public Integer getAssessUserId() {
        return assessUserId;
    }

    public void setAssessUserId(Integer assessUserId) {
        this.assessUserId = assessUserId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}