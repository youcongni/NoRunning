package ui.systemManaConfig.activity;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ui.statisticsDisplay.activity.MainActivity;

import com.File.TxtFileUtil;
import com.alibaba.fastjson.JSON;
import com.example.androidui_sample_demo.R;

import foundation.dataService.util.LoadingDialog;
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
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,
		WebServiceDelegate {
	private Button btn_login_regist, btn_login;
	private String email, password;
	private boolean webflag = false;
	private WebServiceUtils webService;
	private EditText et_Username, et_Passwd;
	public static final int MENU_PWD_BACK = 1;
	public static final int MENU_HELP = 2;
	public static final int MENU_EXIT = 3;
	private LoadingDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// 使得打开界面后不自动跳出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initView();
	}

	private void initView() {
		et_Username = (EditText) findViewById(R.id.et_login_username);
		et_Passwd = (EditText) findViewById(R.id.et_login_passwd);

		btn_login_regist = (Button) findViewById(R.id.btn_login_regist);
		btn_login_regist.setOnClickListener(this);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);

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

		case R.id.btn_login_regist:
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			LoginActivity.this.startActivity(intent);
			break;
		case R.id.btn_login:
			email = et_Username.getText().toString().trim();
			password = et_Passwd.getText().toString().trim();

			if (email.equals("")) {
				Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
			} else if (password.equals("")) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			} else if (!isEmail(email)) {
				Toast.makeText(this, "邮箱格式不对", Toast.LENGTH_SHORT).show();
			} else {
				webflag = ConnetNet.isConnect(getApplicationContext());
				webService = new WebServiceUtils(MemoWebPara.NAMESPACE,
						MemoWebPara.USER_URL, this);
				HashMap<String, Object> args = new HashMap<String, Object>();
				args.put("email", email);
				args.put("password", password);
				webService.callWebService("login", args, String.class);
				dialog =new LoadingDialog(this);
				dialog.show();
			}
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
			int flag = JSON.parseObject((String) result, int.class);
			if (flag == -2) {// 用户名不存在
				Toast.makeText(this, "用户名不存在", Toast.LENGTH_SHORT).show();
			} else if (flag == -1) {// 密码错误
				Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
				TxtFileUtil.writeLoginFlag("login," + email.toString());// 将登陆标志，邮箱写入文件
				dialog.dismiss();
				Intent mainIntent = new Intent(LoginActivity.this,
						MainActivity.class);
				LoginActivity.this.startActivity(mainIntent);
				LoginActivity.this.finish();
			}
		}
	}
}
