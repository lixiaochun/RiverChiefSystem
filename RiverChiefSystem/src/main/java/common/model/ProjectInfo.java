package common.model;


import java.util.Date;


public class ProjectInfo {
    

    private Integer projectId;

    private Integer regionId;

    private String projectName;

    private String baseInfoType;

    private Integer progress;

    private Integer investment;

    private String personliable;

    private String telephone;

    private String site;

    private Double longitute;

    private Double latitude;

    private Date startTime;

    private String company;

    private String contentAndScale;

    private String problems;

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRegionId() {
        return this.regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBaseInfoType() {
        return this.baseInfoType;
    }

    public void setBaseInfoType(String baseInfoType) {
        this.baseInfoType = baseInfoType;
    }

    public Integer getProgress() {
        return this.progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getInvestment() {
        return this.investment;
    }

    public void setInvestment(Integer investment) {
        this.investment = investment;
    }

    public String getPersonliable() {
        return this.personliable;
    }

    public void setPersonliable(String personliable) {
        this.personliable = personliable;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContentAndScale() {
        return this.contentAndScale;
    }

    public void setContentAndScale(String contentAndScale) {
        this.contentAndScale = contentAndScale;
    }

    public String getProblems() {
        return this.problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }
}