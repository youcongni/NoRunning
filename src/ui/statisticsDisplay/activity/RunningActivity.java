package ui.statisticsDisplay.activity;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.File.TxtFileUtil;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.map.Symbol.Color;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.androidui_sample_demo.R;

import domain.dataCollect.CurrentSportData;
import domain.dataCollect.DataCollectService;
import domain.dataCollect.IDataCollectTimeOut;
import domain.systemManaConfig.User;
import foundation.ble.BLEService;
import foundation.dataService.DataCollectDataService;
import foundation.dataService.util.DateService;
import foundation.speed.step.StepService;
import foundation.speed.step.StepService.StepBinder;

@SuppressWarnings({ "unused", "unused" })
public class RunningActivity extends Activity implements IDataCollectTimeOut {

	private Button btn_finish_running;
	private Chronometer timer;// 界面计时器
	private TextView tv_heartRates, tv_speeds, tv_distance, tv_calorie;
	// 业务服务类
	private DataCollectService dataCollectService;
	// 文件
	private File f;

	private Handler mainHandler;
	private TextView timeLable;

	private CurrentSportData curSportData = new CurrentSportData();
	private Handler mHandler;
	private Thread clockThread;
	// private AcBaseService mAcCollectService;
	public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
	private String mDeviceAddress;
	private BLEService mBluetoothLeService;

	private Toast mToast;
	private BMapManager mBMapManager;
	private MapView mMapView = null;
	private MapController mMapController = null;
	LocationListener mLocationListener = null;
	private LocationClient mLocClient;
	private LocationData mLocData;
	// 定位图层
	private LocationOverlay myLocationOverlay = null;

	private boolean isRequest = false;// 是否手动触发请求定位
	private boolean isFirstLoc = true;// 是否首次定位
	
	protected CountDownTimer brewCountDownTimer;
	public MediaPlayer mediaPlayer = new MediaPlayer();
	
	private MediaPlayer mediaPlayerFast;
	private MediaPlayer mediaPlayerSlow;

	// private PopupOverlay mPopupOverlay = null;// 弹出泡泡图层，浏览节点时使用
	// private View viewCache;
	private BDLocation location;

	// Code to manage Service lifecycle.
	private final ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service) {
			mBluetoothLeService = ((BLEService.LocalBinder) service)
					.getService();
			if (!mBluetoothLeService.initialize()) {

				finish();
			}
			// Automatically connects to the device upon successful start-up
			// initialization.
			mBluetoothLeService.connect(mDeviceAddress);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			mBluetoothLeService = null;
		}
	};
	private boolean speedFlag = false;
	private StepService mStepService;
	private ServiceConnection mSpeedServiceConnection = new ServiceConnection() {             
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        	mStepService = ((StepService.StepBinder) service)
					.getService();
        }

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			
		}
    };
	private void bindSpeedService(){
		Intent it = new Intent(RunningActivity.this,StepService.class);
		bindService(it, mSpeedServiceConnection, Context.BIND_AUTO_CREATE);
        speedFlag = true;
	}
	private void unBindSpeedService(){
        if(speedFlag == true){
            unbindService(mSpeedServiceConnection);
            speedFlag = false;
        }
	}

	// private CurrentSportData currentSportData;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 使用地图sdk前需先初始化BMapManager，这个必须在setContentView()前先初始化
		mBMapManager = new BMapManager(this);

		// 第一个参数是API key,
		// 第二个参数是常用事件监听，用来处理通常的网络错误，授权验证错误等，你也可以不添加这个回调接口
		mBMapManager.init("VcqTz3XpXz8413haxdvLM39F",
				new MKGeneralListenerImpl());

		setContentView(R.layout.activity_running);
		
		mediaPlayerFast=MediaPlayer.create(this, R.raw.fast);
		mediaPlayerSlow=MediaPlayer.create(this, R.raw.slow);
		
	
		
		
		// 新建文件
		f = new File("/sdcard/HeartRates.txt"/** 文件路径名 **/
		);
		// 创建文件
		try {
			TxtFileUtil.createFile(f);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final Intent intent = getIntent();

		mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

		// 启动BluetoothLeService
		Intent gattServiceIntent = new Intent(this, BLEService.class);
		// 将该activity与BluetoothLeService绑定
		bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
		dataCollectService = new DataCollectService();// 实例化DataCollectService
		dataCollectService.setDataCollectTimeOut(this);// 设置监听者为UI
		btn_finish_running = (Button) findViewById(R.id.btn_finish_running);
		tv_heartRates = (TextView) findViewById(R.id.tv_Heartrates);
		tv_speeds = (TextView) findViewById(R.id.tv_speeds);
		tv_distance = (TextView) findViewById(R.id.tv_distance);
		tv_calorie = (TextView) findViewById(R.id.tv_calorie);

		timer = (Chronometer) findViewById(R.id.timer);
		timer.setBase(SystemClock.elapsedRealtime());
		
        bindSpeedService();
		//在start中加入user对象
		User user = new User(170.0,55.0);
		dataCollectService.start(DateService.getDate(),user);// 开启开始跑步
		timer.start();// 界面计时器开始
		startBrew();
		initModel();// 初始化模型

		/**
		 * 结束运动
		 */
		btn_finish_running.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				unBindSpeedService();
				try {
					dataCollectService.stop(DateService.getDate());
				} catch (Exception e) {

				}
				RunningActivity.this.finish();
			}
		});

		// 在handler中接收信息,更新UI
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				updateView(curSportData);
				if(curSportData.getCurrentSpeed()<2){
					mediaPlayerSlow.start();
				}
				if(curSportData.getCurrentSpeed()>6){
					mediaPlayerFast.start();
				}
			}

		};

		mMapView = (MapView) findViewById(R.id.bmapView); // 获取百度地图控件实例
		mMapController = mMapView.getController(); // 获取地图控制器
		mMapController.enableClick(true); // 设置地图是否响应点击事件
		mMapController.setZoom(18); // 设置地图缩放级别
		mMapView.setBuiltInZoomControls(true); // 显示内置缩放控件

		mLocData = new LocationData();

		// 实例化定位服务，LocationClient类必须在主线程中声明
		mLocClient = new LocationClient(getApplicationContext());

		/**
		 * 设置定位参数
		 */
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开GPRS
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(2000); // 设置发起定位请求的间隔时间为2000ms
		option.disableCache(false);// 禁止启用缓存定位

		mLocClient.setLocOption(option);
		mLocClient.start(); // 调用此方法开始定位

		// 定位图层初始化
		myLocationOverlay = new LocationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(mLocData);

		// myLocationOverlay.setMarker(getResources().getDrawable(R.drawable.location_arrows));

		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();

		// 注册位置监听器
		mLocClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation locationChange) {
				// TODO Auto-generated method stub
				if (locationChange == null) {
					return;
				}
				if (location == null) {
					location = locationChange;
					mLocData.latitude = location.getLatitude();
					mLocData.longitude = location.getLongitude();
					// 如果不显示定位精度圈，将accuracy赋值为0即可
					mLocData.accuracy = location.getRadius();
					mLocData.direction = location.getDerect();

					// 将定位数据设置到定位图层里
					myLocationOverlay.setData(mLocData);
					// 更新图层数据执行刷新后生效
					mMapView.refresh();

					mMapController.animateTo(new GeoPoint((int) (location
							.getLatitude() * 1e6), (int) (location
							.getLongitude() * 1e6)));
				}
				GraphicsOverlay graphicsOverlay = new GraphicsOverlay(mMapView);
				mMapView.getOverlays().add(graphicsOverlay);
				graphicsOverlay.setData(drawLine(locationChange.getLatitude(),
						locationChange.getLongitude(), location.getLatitude(),
						location.getLongitude()));
				mMapView.refresh();
				location = locationChange;
			}

			@Override
			public void onReceivePoi(BDLocation location) {
			}

		});

		mLocationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location arg0) {
				// TODO Auto-generated method stub
				if (location != null) {
					// 添加折线
					// showToast("locationChanged " + arg0.getAltitude() + ":"
					// + arg0.getLongitude());
					GraphicsOverlay graphicsOverlay = new GraphicsOverlay(
							mMapView);
					mMapView.getOverlays().add(graphicsOverlay);
					graphicsOverlay.setData(drawLine(arg0.getLatitude(),
							arg0.getLongitude(), location.getLatitude(),
							location.getLongitude()));
					mMapView.refresh();
				}
			}

			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub

			}

		};

		// 修改定位数据后刷新图层生效
		mMapView.refresh();

	}

	// /* 线程体是Clock对象本身，线程名字为"Clock" */
	// clockThread = new Thread(new Runnable() {
	// @Override
	// public void run() {
	// /*
	// * 该线程的作用是每隔一分钟提醒一次
	// */
	// Message message = new Message();
	// mHandler.sendMessage(message);//传递信息
	// }
	//
	// });
	// clockThread.start(); /* 启动线程 */
	//
	// }
	/**
	 * 初始模型
	 */


	protected void initModel() {
		tv_heartRates.setText("0");
		tv_speeds.setText("0");
		tv_distance.setText("0");
		tv_calorie.setText("0");
	}

	// 更新UI
	protected void updateView(CurrentSportData currentSportData) {
		tv_heartRates.setText(currentSportData.getCurrentHeartRate() + "");
		tv_speeds.setText(currentSportData.getCurrentSpeed() + "");
		tv_calorie.setText(currentSportData.getTotalCalorie() + "");
		tv_distance.setText(currentSportData.getDistance() + "");
		
	}

	@Override
	public void DOnTimeOut(CurrentSportData currentSportData) {
		// 更新UI
		this.curSportData = currentSportData;
		// clockThread.run();
		Message message = new Message();
		mHandler.sendMessage(message);// 传递信息
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mMapView.onResume();
		super.onResume();
		// registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		if (mBluetoothLeService != null) {
			final boolean result = mBluetoothLeService.connect(mDeviceAddress);

		}
	}

	@Override
	protected void onPause() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mMapView.onPause();
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.destroy();

		// 退出应用调用BMapManager的destroy()方法
		if (mBMapManager != null) {
			mBMapManager.destroy();
			mBMapManager = null;
		}

		// 退出时销毁定位
		if (mLocClient != null) {
			mLocClient.stop();
		}
		super.onDestroy();

		unbindService(mServiceConnection);
		unBindSpeedService();
		mBluetoothLeService = null;
		
		stopBrew();
		mediaPlayer.stop();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			// Intent intent = new Intent(this, StartrunningActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
			// return true;

		}
		return super.onOptionsItemSelected(item);

	}
	
	/** * 绘制折线，该折线状态随地图状态变化 * * @return 折线对象 */
	public Graphic drawLine(double mLat1, double mLon1, double mLat2,
			double mLon2) {
		int lat = (int) (mLat1 * 1E6);
		int lon = (int) (mLon1 * 1E6);
		GeoPoint pt1 = new GeoPoint(lat, lon);
		lat = (int) (mLat2 * 1E6);
		lon = (int) (mLon2 * 1E6);
		GeoPoint pt2 = new GeoPoint(lat, lon);
		// 构建线
		Geometry lineGeometry = new Geometry();
		// 设定折线点坐标
		GeoPoint[] linePoints = new GeoPoint[2];
		linePoints[0] = pt1;
		linePoints[1] = pt2;
		lineGeometry.setPolyLine(linePoints);
		// 设定样式
		Symbol lineSymbol = new Symbol();
		Symbol.Color lineColor = lineSymbol.new Color();
		lineColor.red = 255;
		lineColor.green = 0;
		lineColor.blue = 0;
		lineColor.alpha = 255;
		lineSymbol.setLineSymbol(lineColor, 5);
		// 生成Graphic对象
		Graphic lineGraphic = new Graphic(lineGeometry, lineSymbol);
		return lineGraphic;
	}

	/**
	 * 定位接口，需要实现两个方法
	 * 
	 * 
	 * 
	 */
	public class BDLocationListenerImpl implements BDLocationListener {

		/**
		 * 接收异步返回的定位结果，参数是BDLocation类型参数
		 */
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}

			RunningActivity.this.location = location;

			mLocData.latitude = location.getLatitude();
			mLocData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			mLocData.accuracy = location.getRadius();
			mLocData.direction = location.getDerect();

			// 将定位数据设置到定位图层里
			myLocationOverlay.setData(mLocData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();

			if (isFirstLoc || isRequest) {
				mMapController.animateTo(new GeoPoint((int) (location
						.getLatitude() * 1e6),
						(int) (location.getLongitude() * 1e6)));

				// showPopupOverlay(location);

				isRequest = false;
			}

			isFirstLoc = false;
		}

		/**
		 * 接收异步返回的POI查询结果，参数是BDLocation类型参数
		 */
		@Override
		public void onReceivePoi(BDLocation poiLocation) {

		}

	}

	/**
	 * 常用事件监听，用来处理通常的网络错误，授权验证错误等
	 * 
	 * 
	 */
	public class MKGeneralListenerImpl implements MKGeneralListener {

		/**
		 * 一些网络状态的错误处理回调函数
		 */
		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				showToast("您的网络出错啦！");
			}
		}

		/**
		 * 授权错误的时候调用的回调函数
		 */
		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				showToast("API KEY错误, 请检查！");
			}
		}

	}

	private class LocationOverlay extends MyLocationOverlay {

		public LocationOverlay(MapView arg0) {
			super(arg0);
		}

		@Override
		protected boolean dispatchTap() {
			// showPopupOverlay(location);
			return super.dispatchTap();
		}

		@Override
		public void setMarker(Drawable arg0) {
			super.setMarker(arg0);
		}

	}

	/**
	 * 显示Toast消息
	 * 
	 * @param msg
	 */
	private void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	/**
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}
	
	/*
	 * 开始计时
	 */
public void startBrew() {
		

		Bundle bundle=this.getIntent().getExtras();
		int brewTime=bundle.getInt("time");
		// Create a new CountDownTimer to track the brew time
		brewCountDownTimer = new CountDownTimer(brewTime * 60 * 1000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
//				brewTimeLabel.setText(String
//						.valueOf(millisUntilFinished / 1000) + "s");
			}

			@Override
			public void onFinish() {
				//isBrewing = false;
				// setBrewCount(brewCount + 1);

				// brewTimeLabel.setText("Brew Up!");
			
				unBindSpeedService();
				try {
					dataCollectService.stop(DateService.getDate());
				} catch (Exception e) {

				}
				new Handler().postDelayed(new Runnable(){    
				    public void run() {    
				    
				    	timer.stop();   
				    }    
				 }, 1000);  
				Session session=Session.getSession();
				session.put("player",mediaPlayer);
				
				ring();
				stopBrew();
				//startBrew.setText("Start");
				Intent intent=new Intent(RunningActivity.this,AlertActivity.class);
				startActivity(intent);
		
			}
		};

		brewCountDownTimer.start();

		//startBrew.setText("Stop");
		//isBrewing = true;
	}
/*
 * 停止计时
 */
	public void stopBrew() {
		if (brewCountDownTimer != null) {
			brewCountDownTimer.cancel();
		}

		//isBrewing = false;
		//startBrew.setText("Start");
	}
	/*
	 * 目标时间到调用系统的默认铃声
	 */
	public boolean ring() {
		Uri ringToneUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		try {
			mediaPlayer.setDataSource(this, ringToneUri);

			final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			if (audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL) != 0) {
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
				mediaPlayer.setLooping(false);
				mediaPlayer.prepare();
				mediaPlayer.start();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
