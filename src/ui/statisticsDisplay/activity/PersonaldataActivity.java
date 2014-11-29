package ui.statisticsDisplay.activity;

import ui.systemManaConfig.activity.LoginActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidui_sample_demo.R;

public class PersonaldataActivity extends Activity {

	private EditText et_meter, et_time, et_speedrate, et_heartrate;
	// 保存按钮
	private Button btn_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_data);
		initView();

		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "保存成功",
						Toast.LENGTH_SHORT).show();
				Intent mainIntent = new Intent(PersonaldataActivity.this,
						MainActivity.class);
				PersonaldataActivity.this.startActivity(mainIntent);
				PersonaldataActivity.this.finish();
			}
		});

	}

	private void initView() {

		et_heartrate = (EditText) findViewById(R.id.et_heartrate);
		et_speedrate = (EditText) findViewById(R.id.et_speedrate);
		et_time = (EditText) findViewById(R.id.et_time);
		et_meter = (EditText) findViewById(R.id.et_meter);
	}
}