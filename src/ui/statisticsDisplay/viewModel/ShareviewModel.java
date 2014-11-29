package ui.statisticsDisplay.viewModel;

import java.util.Date;


public class ShareviewModel {
	/**精确到天的日期*/
	private String date;
	/**只显示小时分钟秒的日期*/
	private String startTime;
	/**格式?小时?分钟*/
	private String useTime;
	private double distance;
	private int avgHeartRate;
	private double avgSpeed;
	private double Calouis;
	
	public ShareviewModel(String date, String startTime, String useTime,
			double distance, int avgHeartRate, double avgSpeed, double calouis) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.useTime = useTime;
		this.distance = distance;
		this.avgHeartRate = avgHeartRate;
		this.avgSpeed = avgSpeed;
		Calouis = calouis;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getAvgHeartRate() {
		return avgHeartRate;
	}
	public void setAvgHeartRate(int avgHeartRate) {
		this.avgHeartRate = avgHeartRate;
	}
	public double getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(double avgSpeed) {
		this.avgSpeed = avgSpeed;
	}
	public double getCalouis() {
		return Calouis;
	}
	public void setCalouis(double calouis) {
		Calouis = calouis;
	}
	
	
	
	
	
}