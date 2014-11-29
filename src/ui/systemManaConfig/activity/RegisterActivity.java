package ui.systemManaConfig.activity;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ui.statisticsDisplay.activity.PersonalInfoActivity;
import com.alibaba.fastjson.JSON;
import com.example.androidui_sample_demo.R;
import foundation.webservice.ConnetNet;
import foundation.webservice.MemoWebPara;
import foundation.webservice.WebServiceDelegate;
import foundation.webservice.WebServiceUtils;
import foundation.webservice.help.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener,
		WebServiceDelegate {
	
	// 点击退回到原来activity的按钮
	private ImageButton imbtn_back;
	private EditText res_email, res_passwd, res_conpasswd;
	private Button btn_setting;
	private String email, password, conPassword;
	private boolean webflag = false;
	private WebServiceUtils webService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		/** 使得打开界面后不自动跳出软键盘 */
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setTitle("注册");

		initView();
	}

	private void initView() {
		res_email = (EditText) findViewById(R.id.et_res_email);
		res_passwd = (EditText) findViewById(R.id.et_res_passwd);
		res_conpasswd = (EditText) findViewById(R.id.et_res_conpasswd);
		// imbtn_back=(ImageButton) findViewById(R.id.imbtn_back);
		btn_setting = (Button) findViewById(R.id.btn_res_setting);
		btn_setting.setOnClickListener(this);
		// imbtn_back.setOnClickListener(this);

	}

	// 判断email格式是否正确
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_res_setting:
			// 取出文本框的值
			email = res_email.getText().toString().trim();
			password = res_passwd.getText().toString().trim();
			conPassword = res_conpasswd.getText().toString().trim();
			if (email.equals("")) {
				Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
			} else if (password.equals("")) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			} else if (conPassword.equals("")) {
				Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
			} else if (!password.equals(conPassword)) {
				Toast.makeText(this, "两次输入密码不一致，请重新校验!", Toast.LENGTH_SHORT)
						.show();
			} else if (!isEmail(email)) {
				Toast.makeText(this, "邮箱格式不对", Toast.LENGTH_SHORT).show();
			} else {
				webflag = ConnetNet.isConnect(getApplicationContext());
				webService = new WebServiceUtils(MemoWebPara.NAMESPACE,
						MemoWebPara.USER_URL, this);
				HashMap<String, Object> args = new HashMap<String, Object>();
				args.put("email", email);
				webService.callWebService("getUserInfo", args, String.class);
			}
			break;
		// case R.id.imbtn_back:
		// //Intent intent = new
		// Intent(RegisterActivity.this,MainActivity.class);
		// RegisterActivity.this.finish();
		// break;
		}

	}

	@Override
	public void handleException(Object ex) {
		Toast toast = Toast.makeText(getApplicationContext(), "请检查网络连接",
				Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public void handleResultOfWebService(String methodName, Object result) {
		if (webflag == true) {
			User user = JSON.parseObject((String) result, User.class);
			if (user != null) {
				Toast toast = Toast.makeText(getApplicationContext(), "该用户已存在",
						Toast.LENGTH_SHORT);
				toast.show();
			} else {
				Intent intent = new Intent(RegisterActivity.this,
						PersonalInfoActivity.class);

				Bundle bundle = new Bundle();
				bundle.putString("skip", "fromRegister");
				bundle.putString("email", email);
				bundle.putString("password", password);
				intent.putExtra("key", bundle);

				RegisterActivity.this.startActivity(intent);
				RegisterActivity.this.finish();
			}
		}
	}
}
