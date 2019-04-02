package common.model;

import java.util.Date;

public class Notice {
    private Integer noticeId;

    private String noticeTitle;

    private String noticeLevel;

    private Date submitTime;

    private Integer userId;

    private String noticeBody;

    private String directoryId;

    private String outLink;
    private String fountImgUrl;
    private String summary;
    private int userOpen;

    public int getUserOpen() {
        return userOpen;
    }

    public void setUserOpen(int userOpen) {
        this.userOpen = userOpen;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
    }

    public String getNoticeLevel() {
        return noticeLevel;
    }

    public void setNoticeLevel(String noticeLevel) {
        this.noticeLevel = noticeLevel == null ? null : noticeLevel.trim();
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNoticeBody() {
        return noticeBody;
    }

    public void setNoticeBody(String noticeBody) {
        this.noticeBody = noticeBody == null ? null : noticeBody.trim();
    }

    public String getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
    }

    public String getOutLink() {
        return outLink;
    }

    public void setOutLink(String outLink) {
        this.outLink = outLink;
    }

    public String getFountImgUrl() {
        return fountImgUrl;
    }

    public void setFountImgUrl(String fountImgUrl) {
        this.fountImgUrl = fountImgUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}