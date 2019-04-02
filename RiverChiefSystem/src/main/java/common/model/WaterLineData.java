package common.model;


import java.util.Date;

public class WaterLineData {
   
	 private int sensorId;//站点id
     /**  type数据字典：1.水污染；2。雨情；3.水量；4。水位；5.水质；6.内部水质；7.水功能；8水源地;* 9.公示牌；10.取水口；11.排污口；12.水利工程；13.易涝点；14.事件；15。视频监控*/
     private int type;
     private int code;//监测指标，具体监测内容见sensor_threshold表
     private double value;//站点值
     private String dateTime;
     private String reservedField;//预留字段
	public int getSensorId() {
		return sensorId;
	}
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getReservedField() {
		return reservedField;
	}
	public void setReservedField(String reservedField) {
		this.reservedField = reservedField;
	}
     
     
}