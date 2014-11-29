package domain.dataCollect.heart;

import java.util.Date;

public class HeartRateData {

	private Date collectTime;
	private Integer heartRate;
	
	
	public HeartRateData(Date collectTime, Integer heartRate) {
		super();
		this.collectTime = collectTime;
		this.heartRate = heartRate;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public int getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}
	
	
}
