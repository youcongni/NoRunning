package domain.systemManaConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import domain.dataCollect.MinuteSportData;

import foundation.webservice.help.Model;
import foundation.webservice.help.OneSport;
@DatabaseTable(tableName = "T_User")
public class User {
	
	public static final String ISLOGIN = "isLogin";
	@DatabaseField(id = true)
	private String email;
	@DatabaseField(canBeNull = false)
	private String password;
	@DatabaseField(canBeNull = false)
	private String nickName;//用户名
	@DatabaseField(canBeNull =true)
	private String sex;
	@DatabaseField(canBeNull = true)
	private String city;
	@DatabaseField(canBeNull = false)
	private double height;
	@DatabaseField(canBeNull = false)
	private double weight;
	@DatabaseField(canBeNull = true)
	private String birthday;
	@DatabaseField(columnDefinition = "LONGBLOB not null",
			dataType = DataType.BYTE_ARRAY)
	private byte[] protrait;
	@DatabaseField(canBeNull = true)
	private String personalword;//个性签名
	@DatabaseField(canBeNull =false,columnName = ISLOGIN)
	private boolean isLogin;//标志是否为当前登陆的用户
	
//	@ForeignCollectionField(eager = false)
//	ForeignCollection<OneSport> oneSports;
	
	public User(double height, double weight) {
		super();
		this.height = height;
		this.weight = weight;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@ForeignCollectionField(eager = false)
	ForeignCollection<Friends> friends;
	
	//private List<Model> models=new ArrayList<Model>();
	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getPersonalword() {
		return personalword;
	}

	public void setPersonalword(String personalword) {
		this.personalword = personalword;
	}



	public List<Friends> getFriends() {
		List<Friends> lfriends = new ArrayList<Friends>();

		Iterator ite = this.friends.iterator();
		while (ite.hasNext()) {
			Friends friend=new Friends();
			friend = (Friends) ite.next();

			lfriends.add(friend);

		}
		return lfriends;
	}

	public void setFriends(ForeignCollection<Friends> friends) {
		this.friends = friends;
	}
	


	
	
}
