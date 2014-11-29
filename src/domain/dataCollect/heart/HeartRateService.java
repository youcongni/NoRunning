package domain.dataCollect.heart;



import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.File.TxtFileUtil;

import domain.dataCollect.DataCollectService;

import foundation.ble.BLEService;

import foundation.ble.ITimeout;
import foundation.dataService.DataCollectDataService;



import android.content.Intent;



public class HeartRateService implements ITimeout{
	//文件
  	private File file=new File("/sdcard/HeartRates.txt"/**文件路径名**/);
	private BLEService bleService=null;
	
	//存放每一分钟的最佳心率数据
	private List<HeartRateData> minuteHeartRates=null;
	
	private IHeartRateTimeOut listener=null;
	
	public void setListener(IHeartRateTimeOut listener) {
		this.listener = listener;
	 }
	
	
	public HeartRateService(List<HeartRateData> minuteHeartRates,
			IHeartRateTimeOut listener) {
		super();
		this.minuteHeartRates = minuteHeartRates;
		this.listener = listener;
	}



	

	public HeartRateService() {
		
		bleService=new BLEService();
		
		
		 minuteHeartRates=new ArrayList<HeartRateData>();
		 bleService.setLis(this);
		 
		 
	 }

	//开始
	public void start(){
		bleService.work();
	}
	//结束采集，得到所有心率集合
	public List<HeartRateData> stop(){
		//heartTimerTask.stop();
		
		List<HeartRateData> heartRateDataList=new ArrayList<HeartRateData>();
		heartRateDataList.addAll(minuteHeartRates);
		//minuteHeartRates.clear();
	  	return heartRateDataList;
		
		
	}
	//***set,get方法****//
	


	public List<HeartRateData> getMinuteHeartRates() {
		return minuteHeartRates;
		
	}


	public void setMinuteHeartRates(List<HeartRateData> minuteHeartRates) {
		this.minuteHeartRates = minuteHeartRates;
	}
	
	//求最佳心率值
	public HeartRateData getMinuteHeartRateData(Date collectTime,List<Integer> heartRates){
		HeartRateData heartRateData=null;
		int size=heartRates.size();
		
		//当采集时间不为空,和心率集合不为空才解析，否则不做事
		if(collectTime!=null){
		 //求去除后的心率值的总和
		 int sum = 0;
		 //去平均值
		 int avg=0;
		
		
		 if(size==0){
			 if(minuteHeartRates.size()==0){
				 avg=0;
			 }
			 else{
			 //如果这分钟没有心率值则拷贝上一分钟的心率值作为这一分钟的心率值
			 avg=minuteHeartRates.get(minuteHeartRates.size()-1).getHeartRate();
			 }
		 }
		 else if(size==1){
			 avg=heartRates.get(0);
		 }else if(size==2){
			 sum=heartRates.get(0)+heartRates.get(1);
			 avg=sum/2;
		 }else{
			 int n1=size/8+1;//去除的心率值个数
			 int n2=n1/2+1;//去除的头部的下标
			 int n3=size-(n1-n2);//去除的尾部的下标
			 
			 int lastSize=size-n1;//去除后心率的总个数
		
			 for(int i=n2;i<n3;i++){
				 sum += heartRates.get(i).doubleValue();
		
			 }
			 //求出平均心率值
			 avg=sum/lastSize;
		 }
		 
	
		 heartRateData=new HeartRateData(collectTime, avg);
		 
		}
		return heartRateData;
	}
		 
		 
	@Override
	public void onTimeout(Date collectTime,List<Integer> heartRates) {
		// TODO Auto-generated method stub
		
		
		List<Integer> LheartRates=new ArrayList<Integer>();
		LheartRates.addAll(heartRates);
		for(int i=0;i<LheartRates.size();i++){
		TxtFileUtil.appendToFile("onTimeout"+LheartRates.get(i)+"\r\n", file);
		}
		
		//求出这一分钟的最佳心率
		HeartRateData heartRateData=getMinuteHeartRateData(collectTime,LheartRates);
		
		//添加到分钟心率中
		if(heartRateData!=null){
		minuteHeartRates.add(heartRateData);
		}
		
		//解析到一个心率值后通知上层
		listener.HOnTimeOut(heartRateData);
		
	}
}
		 
	
		
		

	

		 
		
		 
   	    
   	    
		
		
		




