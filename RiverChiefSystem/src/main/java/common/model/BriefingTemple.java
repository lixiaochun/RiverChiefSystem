package common.model;

import java.util.Date;

public class BriefingTemple {
    private Integer briefingId;

    private String briefingName;

    private String briefingTitle;

    private String briefingSummary;

    private String regionId;

    private Date submitTime;

    private String briefingBody;

    public Integer getBriefingId() {
        return briefingId;
    }

    public void setBriefingId(Integer briefingId) {
        this.briefingId = briefingId;
    }

    public String getBriefingName() {
        return briefingName;
    }

    public void setBriefingName(String briefingName) {
        this.briefingName = briefingName;
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