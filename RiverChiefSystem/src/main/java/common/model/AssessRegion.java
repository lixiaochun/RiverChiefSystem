package common.model;

import java.util.Date;
import java.util.List;

public class AssessRegion {
    private String regionId;
    private String date;
    private List<AssessTypeAndScore> list;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<AssessTypeAndScore> getList() {
        return list;
    }

    public void setList(List<AssessTypeAndScore> list) {
        this.list = list;
    }
}
