package common.model;

import java.util.Date;

public class AssessCompleteScore {
    private double score;
    private double maxscore;
    private Date dateTime;
    private double dataAvg;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getMaxscore() {
        return maxscore;
    }

    public void setMaxscore(double maxscore) {
        this.maxscore = maxscore;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public double getDataAvg() {
        return dataAvg;
    }

    public void setDataAvg(double dataAvg) {
        this.dataAvg = dataAvg;
    }
}
