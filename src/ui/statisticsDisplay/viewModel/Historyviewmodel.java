package ui.statisticsDisplay.viewModel;

import java.util.Date;

public class Historyviewmodel {  
    private int id; //信息ID  
    private String title;   //信息标题  
    private String details; //详细信息  
    private int avatar; //图片ID  
    public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	private Date date;//日期
      
  
    //信息ID处理函数  
    public void setId(int id) {    
        this.id = id;  
    }  
    public int getId() {    
        return id;  
    }  
    //标题  
    public void setTitle(String title) {    
        this.title = title;  
    }  
    public String getTitle() {  
        return title;    
    }  
      
    //详细信息  
    public void setDetails(String info) {    
        this.details = info;  
    }  
    public String getDetails() {    
        return details;    
    }  
      
    //图片  
    public void setAvatar(int avatar) {    
        this.avatar = avatar;  
    }  
    public int getAvatar() {    
        return avatar;  
    }  
      
  
}  