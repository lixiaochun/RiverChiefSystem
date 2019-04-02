package common.model;


import java.util.Date;


public class InlandWaterQualityData {
    /** 版本号 */
    private static final long serialVersionUID = 3583096689427938794L;

    private Integer inlandWaterQualityIdDataId;

    private Integer inlandWaterQualityId;

    private Double transparent;

    private Double oxygen;

    private Double nh3;

    private Double redoxPotential;

    private Date time;

    private String period;

    public Integer getInlandWaterQualityIdDataId() {
        return this.inlandWaterQualityIdDataId;
    }

    public void setInlandWaterQualityIdDataId(Integer inlandWaterQualityIdDataId) {
        this.inlandWaterQualityIdDataId = inlandWaterQualityIdDataId;
    }

    public Integer getInlandWaterQualityId() {
        return this.inlandWaterQualityId;
    }

    public void setInlandWaterQualityId(Integer inlandWaterQualityId) {
        this.inlandWaterQualityId = inlandWaterQualityId;
    }

    public Double getTransparent() {
        return this.transparent;
    }

    public void setTransparent(Double transparent) {
        this.transparent = transparent;
    }

    public Double getOxygen() {
        return this.oxygen;
    }

    public void setOxygen(Double oxygen) {
        this.oxygen = oxygen;
    }

    public Double getNh3() {
        return this.nh3;
    }

    public void setNh3(Double nh3) {
        this.nh3 = nh3;
    }

    public Double getRedoxPotential() {
        return this.redoxPotential;
    }

    public void setRedoxPotential(Double redoxPotential) {
        this.redoxPotential = redoxPotential;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}