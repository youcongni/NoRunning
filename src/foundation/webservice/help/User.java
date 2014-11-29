package foundation.webservice.help;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String email;
	private String password;
	private String nickName;//用户名
	private String sex;
	private String city;
	private double height;
	private double weight;
	private String birthday;
	private byte[] protrait;
	private String personalword;//个性签名
	private List<OneSport> oneSports = new ArrayList<OneSport>();
	private List<Model> models=new ArrayList<Model>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public byte[] getProtrait() {
		return protrait;
	}

	public void setProtrait(byte[] protrait) {
		this.protrait = protrait;
	}


	public List<OneSport> getOneSports() {
		return oneSports;
	}

	public void setOneSports(List<OneSport> oneSports) {
		this.oneSports = oneSports;
	}
	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}

}
