package ui.statisticsDisplay.viewModel;

public class GroupListItem {
	private String date;
	private float distance;
	
	private int avgHeartRate;
	private double avgSpeed;
	
	public GroupListItem() {
		super();
	}

	
	public GroupListItem(String date, float distance, int avgHeartRate,
			double avgSpeed) {
		super();
		this.date = date;
		this.distance = distance;
		this.avgHeartRate = avgHeartRate;
		this.avgSpeed = avgSpeed;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
