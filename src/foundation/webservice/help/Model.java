package foundation.webservice.help;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import foundation.webservice.WebServiceDelegate;

public class Model{
	
	private int id;
	private Date startTime;
	private Date endTime;
	private Parameter parameter;
	private User user;
	private List<Scheme> schemes = new ArrayList<Scheme>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Parameter getParameter() {
		return parameter;
	}
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
	
	public List<Scheme> getSchemes() {
		return schemes;
	}
	public void setSchemes(List<Scheme> schemes) {
		this.schemes = schemes;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}



}
