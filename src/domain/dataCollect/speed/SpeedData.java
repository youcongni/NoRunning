package domain.dataCollect.speed;

import java.util.Date;

public class SpeedData {
	private Date collectTime;
	private double speed;
	private double distance;
	private double calorie;

	public SpeedData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SpeedData(Date collectTime,double speed,double distance,double calorie){
		this.collectTime = collectTime;
		this.speed = speed;
		this.distance = distance;
		this.calorie = calorie;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getCalorie() {
		return calorie;
	}
	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}
}

