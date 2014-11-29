package ui.statisticsDisplay.activity;

import com.example.androidui_sample_demo.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*
 * �趨Ŀ��ʱ�����
 */
public class BrewClockActivity extends Activity implements OnClickListener {
	/** Properties **/
	protected Button brewAddTime;
	protected Button brewDecreaseTime;
	private String mDeviceAddress;
	protected Button startBrew;
	//protected TextView brewCountLabel;
	protected TextView brewTimeLabel;
	public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
	protected int brewTime = 3;
	protected CountDownTimer brewCountDownTimer;
	// protected int brewCount = 0;
	protected boolean isBrewing = false;

	//private MediaPlayer mediaPlayer = new MediaPlayer();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_target);

		// Connect interface elements to properties
		brewAddTime = (Button) findViewById(R.id.brew_time_up);
		brewDecreaseTime = (Button) findViewById(R.id.brew_time_down);
		startBrew = (Button) findViewById(R.id.brew_start);
		// brewCountLabel = (TextView) findViewById(R.id.brew_count_label);
		brewTimeLabel = (TextView) findViewById(R.id.brew_time);

		// Setup ClickListeners
		brewAddTime.setOnClickListener(this);
		brewDecreaseTime.setOnClickListener(this);
		startBrew.setOnClickListener(this);

		// Set the initial brew values
		// setBrewCount(0);
		setBrewTime(3);
		startBrew.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//String str=brewTimeLabel.getText().toString();
				final Intent intent2 = getIntent();
				mDeviceAddress = intent2.getStringExtra(EXTRAS_DEVICE_ADDRESS);
				Intent intent = new Intent();
				Bundle bundle=new Bundle();
				bundle.putInt("time", brewTime);
				bundle.putString(RunningActivity.EXTRAS_DEVICE_ADDRESS,mDeviceAddress);
				intent.putExtras(bundle);
				intent.setClass(BrewClockActivity.this,RunningActivity.class);
				startActivity(intent);
				BrewClockActivity.this.finish();
			}
			
		});
	}

	/** Methods **/

	/**
	 * Set an absolute value for the number of minutes to brew. Has no effect if
	 * a brew is currently running.
	 * 
	 * @param minutes
	 *            The number of minutes to brew.
	 */
	public void setBrewTime(int minutes) {
		if (isBrewing)
			return;

		brewTime = minutes;

		if (brewTime < 1) {
			brewTime = 1;
		}

		brewTimeLabel.setText(String.valueOf(brewTime) + "����");
	}

	/**
	 * Set the number of brews that have been made, and update the interface.
	 * 
	 * @param count
	 *            The new number of brews
	 */
	// public void setBrewCount(int count)
	// {
	// brewCount = count;
	// brewCountLabel.setText(String.valueOf(brewCount));
	// }

	/**
	 * Start the brew timer
	 */
//	public void startBrew() {
//		Intent intent = new Intent(BrewClockActivity.this,
//				RunningActivity.class);
//		startActivity(intent);
//		
//		// Create a new CountDownTimer to track the brew time
//		brewCountDownTimer = new CountDownTimer(brewTime * 60 * 1000, 1000) {
//			@Override
//			public void onTick(long millisUntilFinished) {
////				brewTimeLabel.setText(String
////						.valueOf(millisUntilFinished / 1000) + "s");
//			}
//
//			@Override
//			public void onFinish() {
//				isBrewing = false;
//				// setBrewCount(brewCount + 1);
//
//				// brewTimeLabel.setText("Brew Up!");
//				
//				ring();
//				startBrew.setText("Start");
//			}
//		};
//
//		brewCountDownTimer.start();
//
//		startBrew.setText("Stop");
//		isBrewing = true;
//	}
//
//	/**
//	 * Stop the brew timer
//	 */
//	public void stopBrew() {
//		if (brewCountDownTimer != null) {
//			brewCountDownTimer.cancel();
//		}
//
//		isBrewing = false;
//		startBrew.setText("Start");
//	}

	/** Interface Implementations **/
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		if (v == brewAddTime) {
			setBrewTime(brewTime + 1);
		} else if (v == brewDecreaseTime) {
			setBrewTime(brewTime - 1);
		} else if (v == startBrew) {
			if (isBrewing) {
				//stopBrew();
			} else {
				//startBrew();
			}
		}
	}

//	public boolean ring() {
//		Uri ringToneUri = RingtoneManager
//				.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//		try {
//			mediaPlayer.setDataSource(this, ringToneUri);
//
//			final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//			if (audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL) != 0) {
//				mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
//				mediaPlayer.setLooping(false);
//				mediaPlayer.prepare();
//				mediaPlayer.start();
//				return true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

}