package ui.statisticsDisplay.viewModel;

import java.util.Date;

public class FriendsMessageModal {
	//备注名
	private String anotherName;
	//性别
	private String sex;
	private int age;
	private String city;
	private Date lastRunTime;
	//用户名
	private String userName;
	private String acountNum;
	private String birthday;
	private String personalWord;
	private byte[] protrait;
	
	
	public byte[] getProtrait() {
		return protrait;
	}
	public void setProtrait(byte[] protrait) {
		this.protrait = protrait;
	}
	public String getAnotherName() {
		return anotherName;
	}
	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getLastRunTime() {
		return lastRunTime;
	}
	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAcountNum() {
		return acountNum;
	}
	public void setAcountNum(String acountNum) {
		this.acountNum = acountNum;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPersonalWord() {
		return personalWord;
	}
	public void setPersonalWord(String personalWord) {
		this.personalWord = personalWord;
	}
	
	
	
	
	
}
