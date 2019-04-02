package common.model;

import java.util.Date;

public class EvaluationCriteria {
		
	private int id;
	private String type;
	private double ratio;
	private double min;
	private double max;
	private double score;
	private Date yearMonth;
	private String reserve;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Date getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
	
}
