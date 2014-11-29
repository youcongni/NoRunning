package ui.statisticsDisplay.viewModel;

public class FriendsListModel {

	private String name;   //显示的数据
	private String email;
	private String personalWord;//个性签名
	private byte[] protrait;
	public FriendsListModel(String name, String email, String personalWord,
			byte[] protrait) {
		super();
		this.name = name;
		this.email = email;
		this.personalWord = personalWord;
		this.protrait = protrait;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPersonalWord() {
		return personalWord;
	}
	public void setPersonalWord(String personalWord) {
		this.personalWord = personalWord;
	}
	public byte[] getProtrait() {
		return protrait;
	}
	public void setProtrait(byte[] protrait) {
		this.protrait = protrait;
	}
	
	
}
