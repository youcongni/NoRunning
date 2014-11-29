package ui.statisticsDisplay.viewModel;

import java.util.Date;

import domain.dataCollect.CurrentSportData;

public class RunningviewModel {
private Date restTime;
public Date getRestTime() {
	return restTime;
}

public void setRestTime(Date restTime) {
	this.restTime = restTime;
}

public Date getStartTime() {
	return startTime;
}

public void setStartTime(Date startTime) {
	this.startTime = startTime;
}

public Date getEndTime() {
	return endTime;
}

public void setEndTime(Date endTime) {
	this.endTime = endTime;
}

public Date getCurrentTime() {
	return currentTime;
}

public void setCurrentTime(Date currentTime) {
	this.currentTime = currentTime;
}

public Date getGoalTime() {
	return goalTime;
}

public void setGoalTime(Date goalTime) {
	this.goalTime = goalTime;
}

public CurrentSportData getCurrentSportData() {
	return currentSportData;
}

public void setCurrentSportData(CurrentSportData currentSportData) {
	this.currentSportData = currentSportData;
}

private Date startTime;
private Date endTime;
private Date currentTime;
private Date goalTime;
	
private CurrentSportData currentSportData;

public RunningviewModel() {
	
}


}
