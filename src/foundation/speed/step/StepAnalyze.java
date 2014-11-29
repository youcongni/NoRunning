package foundation.speed.step;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

import domain.dataCollect.speed.SpeedData;
import domain.systemManaConfig.User;
import foundation.dataService.util.DateService;
import foundation.speed.step.DetectorStep.StepListener;

public class StepAnalyze implements StepListener{
	DecimalFormat df1 = new DecimalFormat("0.0");
	DecimalFormat df2 = new DecimalFormat("0.00");
	DecimalFormat df3 = new DecimalFormat("0.000");
	
	private VcTimeOut mVcTimeOut = null;
	private double height = 0;
	private double weight = 0;
	private double total = 0;
	
	public void start(User user,VcTimeOut mVcTimeOut){
		this.mVcTimeOut = mVcTimeOut;
		this.height = user.getHeight();
		this.weight = user.getWeight();
		StepService.setLis(this);
		startPaceTask(2);
		startSpeedTask(60);
	}
	
	public void stop(){		
		StepService.removeLis();
		stopPaceTask();
		stopSpeedTask();
	} 
		
    private long mLastStepTime;
    private long mThisStepTime;
    private long mTime;
    private int steps = 0;
    
	private paceTask mPaceTask;
	private Timer paceTimer;
	public class paceTask extends TimerTask{    
	    public void run(){
	    	calculatePace();
	    }
	}
	
	public void startPaceTask(int time){
		mLastStepTime = mThisStepTime = System.currentTimeMillis();
		mPaceTask = new paceTask();
		paceTimer = new Timer();
		paceTimer.schedule(mPaceTask, time*1000, time*1000);
	}
	
	public void stopPaceTask(){
		mPaceTask.cancel();
		paceTimer.purge();
		paceTimer.cancel();
	}
	
    public void calculatePace(){
    	double mpace;
    	if(mTime == 0)
    		mpace = 0;
    	else
    		mpace = steps/(mTime/1000f);
    	
    	analyzePace(mpace);
    	
    	mPace = mpace;
    	mTime = 0;
    	steps = 0;
    }
    
	@Override
	public void onStep() {
		// TODO Auto-generated method stub
    	steps++;
    	mThisStepTime = System.currentTimeMillis();
    	mTime += mThisStepTime - mLastStepTime;
    	mLastStepTime = mThisStepTime;
    	total++;
	}
	
	private SpeedData sd = new SpeedData();
	private double speed = 0;
	private double distance = 0;
	private double calories = 0;
	
	private Timer speedTimer;
	private speedTask mSpeedTask;
	public class speedTask extends TimerTask{    
	    public void run(){
	    	sendData();
	    }
	}
	
	public void startSpeedTask(int time){
		mSpeedTask = new speedTask();
		speedTimer = new Timer();
		speedTimer.schedule(mSpeedTask, time*1000, time*1000);
	}
	
	public void stopSpeedTask(){
		mSpeedTask.cancel();
		speedTimer.purge();
		speedTimer.cancel();
	}
	
	public void analyzePace(double pace) {
    	speed = caculateSpeed(pace);//两秒内的平均速度，单位：米/每秒
    	distance += caculateDistance(speed);//对两秒内的路程进行累积，单位：米
    	calories += caculatecalories(speed);//对两秒内的卡路里进行累积，单位：卡
    	
    	Log.e("speed", speed+"");
    	Log.e("distance", distance+"");
    	Log.e("calories", calories+"");
    }
    
    public double caculateSpeed(double pace){
    	double feetLength = 0;
    	double mPace = pace*2;
    	if(mPace <= 0){
    		feetLength = 0;
    	}else if(mPace < 2){
    		feetLength = height/4f;
    	}else if(mPace < 3){
    		feetLength = height/3f;
    	}else if(mPace < 4){
    		feetLength = height/2f;
    	}else if(mPace < 5){
    		feetLength = height/1.2f;
    	}else if(mPace < 6){
    		feetLength = height;
    	}else{
    		feetLength = height*1.2f;
    	}
    	return (feetLength/100f)*pace;//米/每秒
    }
    
    public double caculateDistance(double speed){
    	return speed*2;//米
    }
    
    public double caculatecalories(double speed){
    	return 1.25*(speed*3.6)*weight/1800f;//卡
    }
    
    double mPace = 0;
	public void sendData(){
		SpeedData sd = new SpeedData(DateService.getDate(),Double.valueOf(df1.format(speed)),Double.valueOf(df2.format(distance/1000)),Double.valueOf(df3.format(calories/1000)));
		mVcTimeOut.getSpeed(sd);
	} 
}
