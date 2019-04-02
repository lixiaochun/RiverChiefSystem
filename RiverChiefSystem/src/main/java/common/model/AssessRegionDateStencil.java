package common.model;

public class AssessRegionDateStencil {
    private String regionId;
    private String date;
    private double PatrolTime;
    private double PatrolMileage;
    private double EventNum;
    private double PatrolDays;

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

    public double getPatrolTime() {
        return PatrolTime;
    }

    public void setPatrolTime(double patrolTime) {
        PatrolTime = patrolTime;
    }

    public double getPatrolMileage() {
        return PatrolMileage;
    }

    public void setPatrolMileage(double patrolMileage) {
        PatrolMileage = patrolMileage;
    }

    public double getEventNum() {
        return EventNum;
    }

    public void setEventNum(double eventNum) {
        EventNum = eventNum;
    }

    public double getPatrolDays() {
        return PatrolDays;
    }

    public void setPatrolDays(double patrolDays) {
        PatrolDays = patrolDays;
    }
}
