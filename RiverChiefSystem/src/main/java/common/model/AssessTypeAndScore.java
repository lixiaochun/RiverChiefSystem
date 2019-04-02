package common.model;

public class AssessTypeAndScore {
    private String type;
    private double score;
    private double maxscore;
    private double dataSum;
    private double dataAvg;

    public double getDataSum() {
        return dataSum;
    }

    public void setDataSum(double dataSum) {
        this.dataSum = dataSum;
    }

    public double getDataAvg() {
        return dataAvg;
    }

    public void setDataAvg(double dataAvg) {
        this.dataAvg = dataAvg;
    }

    public double getMaxscore() {
        return maxscore;
    }

    public void setMaxscore(double maxscore) {
        this.maxscore = maxscore;
    }

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
