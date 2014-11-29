package foundation.dataService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;

import domain.dataCollect.MinuteSportData;
import domain.dataCollect.OneSport;

import foundation.dataService.base.DataContext;
import foundation.dataService.util.DateService;



public class DataCollectDataService {
private DataContext dtx;
	
	public DataCollectDataService() {
		dtx=new DataContext();
	}
	//添加分钟运动数据
	public void saveMinuteSportData(MinuteSportData minuteSportData){
		try {
			dtx.add(minuteSportData, MinuteSportData.class, Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//添加一次运动数据
	public void saveOneSportData(OneSport oneSport){
			try {
				dtx.add(oneSport, OneSport.class, Integer.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public void saveSportData(OneSport oneSport){
		saveOneSportData(oneSport);
		List<MinuteSportData> minuteSportDatas=oneSport.getlMinuteSportDatas();
		for(int i=0;i<minuteSportDatas.size();i++){
			saveMinuteSportData(minuteSportDatas.get(i));
		}
	}
	
	//取出当天的最大运动次数
	public int getMaxSportNum(Date startTime){
		String sportDay=DateService.changeDateFormat(startTime);
		
		int maxNum = 0;
		try {
		
			List<String[]> max=dtx.queryBySql(OneSport.class, Integer.class, "select max(count) from T_OneSport where date='"+sportDay+"'");
			if(max.get(0)[0]==null){
			}	
			else{
				maxNum=Integer.parseInt(max.get(0)[0]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxNum;
	
	}
	public List<OneSport> getAllOneSport(){
		List<OneSport> oneSports=null;
		try {
			oneSports=dtx.queryForAll(OneSport.class, Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oneSports;
		
	}
}
