package domain.statisticsDisplay;

public class RunningRecord {

	private String start_time;
	private String total_running;
	
	
	public RunningRecord(String start_time, String total_running) {
		super();
		this.start_time = start_time;
		this.total_running = total_running;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getTotal_running() {
		return total_running;
	}
	public void setTotal_running(String total_running) {
		this.total_running = total_running;
	}
	
	
	
}
