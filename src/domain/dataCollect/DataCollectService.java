package domain.dataCollect;

import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.R.bool;
import android.util.Log;
import android.widget.Toast;

import com.File.TxtFileUtil;
import com.baidu.location.f;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import domain.dataCollect.heart.HeartRateData;
import domain.dataCollect.heart.HeartRateService;
import domain.dataCollect.heart.IHeartRateTimeOut;

import domain.dataCollect.speed.ISpeedTimeOut;
import domain.dataCollect.speed.SpeedData;
import domain.dataCollect.speed.SpeedService;

import domain.systemManaConfig.*;
import foundation.dataService.DataCollectDataService;
import foundation.dataService.base.DataContext;
import foundation.dataService.util.DateService;
import foundation.dataService.util.LoadingDialog;
import foundation.webservice.ConnetNet;
import foundation.webservice.MemoWebPara;
import foundation.webservice.WebServiceDelegate;
import foundation.webservice.WebServiceUtils;

public class DataCollectService implements IHeartRateTimeOut, ISpeedTimeOut{

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private ConfigurationService configurationService;
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */

	private File fv = new File("/sdcard/Speeds.txt"/** 文件路径名 **/
	);
	private SpeedService speedService;
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	// 文件
	private File fh = new File("/sdcard/HeartRates.txt"/** 文件路径名 **/
	);
	private HeartRateService heartRateService;
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	// private MinuteSportData minuteSportData;

	public OneSport oneSport;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 得到每分钟采集数据(返回值List&lt;MinuteSpartData&gt;)
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private WebServiceUtils webService;
	private DataCollectDataService dataCollectDataService;
	private CurrentSportData currentSportData = new CurrentSportData();
	private double tempDistance = 0.0;// 临时用于计算总距离
	private double tempTotalCalorie = 0.0;// 临时用于计算总卡路里

	private IDataCollectTimeOut dataCollectTimeOut = null;// UI监听
	private DataContext dtx;

	public void setDataCollectTimeOut(IDataCollectTimeOut dataCollectTimeOut) {
		this.dataCollectTimeOut = dataCollectTimeOut;
	}

	public DataCollectService() {
		// 实例化各服务
		dataCollectDataService = new DataCollectDataService();
		heartRateService = new HeartRateService();
		speedService = new SpeedService();
		heartRateService.setListener(this);// 设置heartRateService监听者为DataCollectService
		speedService.setSpeedTimeOut(this);// 设置speedService监听者为DataCollectService
		currentSportData = new CurrentSportData();
	}

	public List<MinuteSportData> getMinuteSportData(Date startTime, Date endTime) {
		// begin-user-code
		// TODO 自动生成的方法存根
		return null;
	}

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @param count
	 * @param date
	 * @return
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private OneSport getSportByCount(Integer count, String date) {
		List<OneSport> oneSports = null;

		try {
			/*
			 * 构造查询生成器
			 */
			QueryBuilder<OneSport, Integer> queryBuilder = dtx.getQueryBuilder(
					OneSport.class, Integer.class);
			// 查询日期为date,第count次的OneSport
			queryBuilder.where().eq(OneSport.Date_NAME, date).and()
					.eq(OneSport.COUNT_NAME, count);

			// prepare our sql statement
			PreparedQuery<OneSport> query = queryBuilder.prepare();
			oneSports = dtx.query(OneSport.class, Integer.class, query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return oneSports.get(0);
	}

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @param SportData
	 * @return
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private List<OneSport> getSportByDate(String SportDate) {

		List<OneSport> oneSports = null;
		try {
			/*
			 * 构造查询生成器
			 */
			QueryBuilder<OneSport, Integer> queryBuilder = dtx.getDao(
					OneSport.class, Integer.class).queryBuilder();
			// 查询日期为date的所有OneSport
			queryBuilder.where().eq(OneSport.Date_NAME, SportDate);
			// prepare our sql statement
			PreparedQuery<OneSport> query = queryBuilder.prepare();
			oneSports = dtx.query(OneSport.class, Integer.class, query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return oneSports;
	}

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean start(Date startTime,User user) {
		// begin-user-code
		// TODO 自动生成的方法存根

		// 实例化OneSport
		oneSport = new OneSport();
		// 设置开始时间
		oneSport.setStartTime(startTime);
		String curDate = DateService.getStringDate();
		oneSport.setDate(curDate);

		// 去数据库比对开始时间
		int maxNum = dataCollectDataService.getMaxSportNum(startTime);
		oneSport.setCount(maxNum + 1);

		// 开启心率采集服务
		heartRateService.start();
		// 开启速度采集服务
		speedService.start(user);

		return true;

		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void stop(Date endTime) {
		// begin-user-code
		// TODO 自动生成的方法存根

		// 设置结束时间
		oneSport.setEndTime(endTime);
		// 停止心率采集服务,从心率服务获取心率链表
		List<HeartRateData> tempHeartRateList = heartRateService.stop();
		// 停止速度采集服务
		speedService.stop();

		// 从速度服务获取速度链表
		List<SpeedData> tempSpeedList = speedService.getSpeedDataList();

		// 每分钟数据链表
		List<MinuteSportData> minuteSportDataList = new ArrayList<MinuteSportData>();

		for (int i = 0; i < tempHeartRateList.size(); i++) {
			MinuteSportData minuteSportData = new MinuteSportData();

			// 装配到minuteSportData
			minuteSportData.setHeartRate(tempHeartRateList.get(i)
					.getHeartRate());// 装配心率
			minuteSportData.setSpeed(tempSpeedList.get(i).getSpeed());// 装配速度
			minuteSportData.setNumber(i + 1);// 装配采集次数
			minuteSportData.setCollectTime(tempHeartRateList.get(i)
					.getCollectTime());// 装配采集时间
			minuteSportData.setOneSport(oneSport);

			// 添加到每分钟数据链表
			minuteSportDataList.add(minuteSportData);
		}
		/** 当oneSport至少有对应一条minuteSportData才存入数据库,否则不存入 */
		if (minuteSportDataList.size() != 0) {
			oneSport.setlMinuteSportDatas(minuteSportDataList);
			// 一次性保存到数据库
			dataCollectDataService.saveSportData(oneSport);
			
//			//后台存储
//			String loginFlag = null;
//			try {
//				loginFlag = TxtFileUtil.readTxtFile(TxtFileUtil.loginFlag);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			String[] str = loginFlag.split(",");
//			foundation.webservice.help.OneSport fwho = new foundation.webservice.help.OneSport();
//			foundation.webservice.help.User u = new foundation.webservice.help.User();
//			u.setEmail(str[1]);
//			fwho.setStartTime(oneSport.getStartTime());
//			fwho.setEndTime(oneSport.getEndTime());
//			fwho.setDate(oneSport.getDate());
//			fwho.setCount(oneSport.getCount());
//			fwho.setUser(u);
//			List<foundation.webservice.help.MinuteSportData> fwhms = new ArrayList<foundation.webservice.help.MinuteSportData>();
//			for(MinuteSportData n:minuteSportDataList){
//				foundation.webservice.help.MinuteSportData fwhm = new foundation.webservice.help.MinuteSportData();
//				fwhm.setCollectTime(n.getCollectTime());
//				fwhm.setNumber(n.getNumber());
//				fwhm.setHeartRate(n.getHeartRate());
//				fwhm.setSpeed(n.getSpeed());
//			}
//			webService = new WebServiceUtils(MemoWebPara.NAMESPACE,
//					MemoWebPara.USER_URL, this);
//			HashMap<String, Object> args = new HashMap<String, Object>();
//				
//			args.put("onesport", fwho);
//			webService.callWebService("addOneSport", args, boolean.class);
		}

		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * 得到本次运动数据
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	// public void getCurrentSportData(Date startTime,Date endTime) {
	// // begin-user-code
	// //实例化currentSportData
	// currentSportData=new CurrentSportData();
	//
	// //回调UI层的onTimeOut
	//
	// dataCollectTimeOut.DOnTimeOut(currentSportData);
	// // end-user-code
	// }

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @param weight
	 * @param height
	 * @param distance
	 * @param speed
	 * @return
	 * @generated 
	 *            "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private double calCalorie(double weight, double speed, double preCalorie) {
		// begin-user-code
		DecimalFormat df1 = new DecimalFormat("0.00");
		double temp = Double.valueOf(df1.format(preCalorie + speed * weight
				/ 400));
		return temp;

		// end-user-code
	}

	@Override
	public void SOnTimeOut(SpeedData speedData) {
		// TODO 实现速度服务层的onTimeOut方法
		// 写入日志
		Log.w("发送速度", speedData.getSpeed()+"");
		currentSportData.setCurrentSpeed(speedData.getSpeed());// 装配当前速度
		tempDistance += speedData.getDistance();// 总距离
		DecimalFormat df1 = new DecimalFormat("0.00");
		double temp = Double.valueOf(df1.format(tempDistance));
		currentSportData.setDistance(temp);// 假设送上来速度单位为m/s，且为平均速度
		// 计算卡路里
		//oneSport.setUser(new User(60));// 测试使用，体重60KG
//		currentSportData.setTotalCalorie(calCalorie(oneSport.getUser()
//				.getWeight(), currentSportData.getCurrentSpeed(),
//				tempTotalCalorie));// 设置卡路里值
		currentSportData.setTotalCalorie(speedData.getCalorie());// 设置卡路里值
		// 如果getCurrentHeartRate!=-1说明装配心率成功,谁先做完谁调上层的onTimeOut
		if (currentSportData.getCurrentHeartRate() != -1) {
			dataCollectTimeOut.DOnTimeOut(currentSportData
					.CopyCurrentSportData(currentSportData));// 回调上层服务

			// 清空currentSportData中数据
			currentSportData.ClearCurrentSportData(currentSportData);
		}
	}

	// 实现心率服务层的onTimeOut方法
	@Override
	public void HOnTimeOut(HeartRateData heartRateData) {
		Log.w("发送心率", heartRateData.getHeartRate()+"");
		currentSportData.setCurrentHeartRate(heartRateData.getHeartRate());// 装配当前心率
		TxtFileUtil
				.appendToFile(
						"HOnTimeOut:" + currentSportData.getCurrentHeartRate()
								+ "\r\n", fh);

		// 谁先做完谁调上层的onTimeOut
		if (currentSportData.getTotalCalorie() != -1) {
			TxtFileUtil.writeSpeed("DaSe - 速度已装配\n");
			dataCollectTimeOut.DOnTimeOut(currentSportData
					.CopyCurrentSportData(currentSportData));// 拷贝数据回调给上层服务

			// 清空currentSportData中数据
			currentSportData.ClearCurrentSportData(currentSportData);
		}

	}



}
