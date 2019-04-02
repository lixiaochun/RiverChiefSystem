package common.model;


public class WaterIntake {
    
    private Integer waterIntakeId;

    private Double longitute;

    private Double latitude;

    private String name;

    private String regionId;

    private String riverId;

    private String type;

    private Double area;

    private String administration;

    private String imgurl;

    public Integer getWaterIntakeId() {
        return this.waterIntakeId;
    }

    public void setWaterIntakeId(Integer waterIntakeId) {
        this.waterIntakeId = waterIntakeId;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getArea() {
        return this.area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getAdministration() {
        return this.administration;
    }

    public void setAdministration(String administration) {
        this.administration = administration;
    }

    public String getImgurl() {
        return this.imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}