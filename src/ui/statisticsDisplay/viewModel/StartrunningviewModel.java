package ui.statisticsDisplay.viewModel;

import java.util.Date;

import domain.dataCollect.CurrentSportData;



public class StartrunningviewModel {

private double accumulateDistance;
private Date accumulateTime;
private int aveheartRate;
public double getAccumulateDistance() {
	return accumulateDistance;
}
public void setAccumulateDistance(double accumulateDistance) {
	this.accumulateDistance = accumulateDistance;
}
public Date getAccumulateTime() {
	return accumulateTime;
}
public void setAccumulateTime(Date accumulateTime) {
	this.accumulateTime = accumulateTime;
}
public int getAveheartRate() {
	return aveheartRate;
}
public void setAveheartRate(int aveheartRate) {
	this.aveheartRate = aveheartRate;
}



	

}
