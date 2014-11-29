package foundation.speed.step;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import foundation.speed.step.DetectorStep.StepListener;

public class StepService extends Service {
    private SensorManager mSensorManager;
    private Sensor mSensor;
	private DetectorStep mStepDetector;
	private static StepListener mStepListener = null;

	private final IBinder mBinder = new StepBinder();
	public class StepBinder extends Binder {
		public StepService getService() {
			return StepService.this;
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mStepDetector = new DetectorStep();
		mStepDetector.addStepListener(mStepListener);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER );
        mSensorManager.registerListener(mStepDetector,mSensor,SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	public static void setLis(StepListener sd) {
		mStepListener = sd;	
	}
	
	public static void removeLis() {
		mStepListener = null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mSensorManager.unregisterListener(mStepDetector);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
