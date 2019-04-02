package common.model;

import java.util.Date;

public class Briefing {
    private Integer briefingId;

    private String briefingTitle;

    private String briefingSummary;

    private Integer briefingUserId;

    private String briefingStatus;

    private String regionId;

    private Date submitTime;

    private String briefingBody;
    private double briefingScore;

    public double getBriefingScore() {
        return briefingScore;
    }

    public void setBriefingScore(double briefingScore) {
        this.briefingScore = briefingScore;
    }

    private int briefingSendtoUserId;

    public int getBriefingSendtoUserId() {
        return briefingSendtoUserId;
    }

    public void setBriefingSendtoUserId(int briefingSendtoUserId) {
        this.briefingSendtoUserId = briefingSendtoUserId;
    }

    public Integer getBriefingId() {
        return briefingId;
    }

    public void setBriefingId(Integer briefingId) {
        this.briefingId = briefingId;
    }

    public String getBriefingTitle() {
        return briefingTitle;
    }

    public void setBriefingTitle(String briefingTitle) {
        this.briefingTitle = briefingTitle == null ? null : briefingTitle.trim();
    }

    public String getBriefingSummary() {
        return briefingSummary;
    }

    public void setBriefingSummary(String briefingSummary) {
        this.briefingSummary = briefingSummary == null ? null : briefingSummary.trim();
    }

    public Integer getBriefingUserId() {
        return briefingUserId;
    }

    public void setBriefingUserId(Integer briefingUserId) {
        this.briefingUserId = briefingUserId;
    }

    public String getBriefingStatus() {
        return briefingStatus;
    }

    public void setBriefingStatus(String briefingStatus) {
        this.briefingStatus = briefingStatus == null ? null : briefingStatus.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getBriefingBody() {
        return briefingBody;
    }

    public void setBriefingBody(String briefingBody) {
        this.briefingBody = briefingBody == null ? null : briefingBody.trim();
    }
}