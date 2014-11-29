package ui.statisticsDisplay.activity;

import com.example.androidui_sample_demo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.os.CountDownTimer;

public class CountdownRunningActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 4000; // —”≥ŸÀƒ√Î
	private TextView txt_timing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_countdown_running);
		txt_timing = (TextView) findViewById(R.id.txt_timing);
		new CountDownTimer(4000, 1000) {

			public void onTick(long millisUntilFinished) {
				long temp = millisUntilFinished / 1000;
				txt_timing.setText(String.valueOf(temp));

			}

			public void onFinish() {
				txt_timing.setText("0");
				Intent intent = new Intent(CountdownRunningActivity.this,
						RunningActivity.class);
				startActivity(intent);
				CountdownRunningActivity.this.finish();
			}

		}.start();
	}
}
