package foundation.webservice.help;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OneSport {

	private int id;
	private String date;
	private Date startTime;
	private Date endTime;
	private Integer count;
	private User user;
	private List<MinuteSportData> minuteSportDatas = new ArrayList<MinuteSportData>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<MinuteSportData> getMinuteSportData() {
		return minuteSportDatas;
	}

	public void setMinuteSportData(List<MinuteSportData> minuteSportDatas) {
		this.minuteSportDatas = minuteSportDatas;
	}

}
