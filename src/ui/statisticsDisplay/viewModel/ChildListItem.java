package ui.statisticsDisplay.viewModel;

import java.util.Date;

public class ChildListItem {
	private float distance;
	private int avgHeartRate;
	private double avgSpeed;
	private Date startTime;
	private int count;
	private Date endTime;
	private double Calouis;
	
	
	public ChildListItem(float distance, int avgHeartRate, double avgSpeed,
			Date startTime, int count, Date endTime, double calouis) {
		super();
		this.distance = distance;
		this.avgHeartRate = avgHeartRate;
		this.avgSpeed = avgSpeed;
		this.startTime = startTime;
		this.count = count;
		this.endTime = endTime;
		Calouis = calouis;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public double getCalouis() {
		return Calouis;
	}
	public void setCalouis(double calouis) {
		Calouis = calouis;
	}
	
	
	
	
	

	
	
	


	
	
}
