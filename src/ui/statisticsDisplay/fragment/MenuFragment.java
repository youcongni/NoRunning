package ui.statisticsDisplay.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ui.statisticsDisplay.activity.MainActivity;
import ui.statisticsDisplay.activity.StartrunningActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.File.TxtFileUtil;
import com.alibaba.fastjson.JSON;
import com.example.androidui_sample_demo.R;
import foundation.dataService.DataCollectDataService;
import foundation.webservice.ConnetNet;
import foundation.webservice.MemoWebPara;
import foundation.webservice.WebServiceDelegate;
import foundation.webservice.WebServiceUtils;
import foundation.webservice.help.MinuteSportData;
import foundation.webservice.help.OneSport;

public class MenuFragment extends Fragment implements WebServiceDelegate {
	private MenuFragment menuFragment;
	private FragmentManager fragmentManager;
	private WebServiceUtils webService;
	private String model = null, oneSport = null, sportData = null;
	private boolean webFlag = false;
	private LinearLayout home, personal_info, start_running, running_scheme,
			setting, running_model, mine, history, exit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuFragment = this;
		reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.Model_URL,
				"getCurrentModel");
		reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.OneSport_URL,
				"getCurrentSport");
		reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.OneSport_URL,
				"getDataStatistics");

	}

	/** 网络重新连（未优化） */
	public void reConnectNet(String nameSpace, String url, String method) {
		webFlag = ConnetNet.isConnect(getActivity());
		webService = new WebServiceUtils(nameSpace, url, this);
		HashMap<String, Object> args = new HashMap<String, Object>();
		String loginFlag = null;
		try {
			loginFlag = TxtFileUtil.readTxtFile(TxtFileUtil.loginFlag);// 读取用户email
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (loginFlag != null) {
			String[] str = loginFlag.split(",");
			args.put("email", str[1].toString().trim());
			webService.callWebService(method, args, String.class);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, null);

		home = (LinearLayout) view.findViewById(R.id.home);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				fragmentManager = menuFragment.getActivity()
						.getFragmentManager();
				StartrunningActivity startrunningActivity = new StartrunningActivity();
				Bundle bundle = new Bundle();
				bundle.putString("sportData", sportData);
				// if (!webFlag) {
				// reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.Model_URL,
				// "getCurrentModel");
				// }
				startrunningActivity.setArguments(bundle);
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, startrunningActivity)
						.commit();
				((MainActivity) menuFragment.getActivity()).mDrawerLayout
						.closeDrawers();
			}
		});

		running_scheme = (LinearLayout) view.findViewById(R.id.running_scheme);
		running_scheme.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragmentManager = menuFragment.getActivity()
						.getFragmentManager();
				RunningSchemeFragment runningSchemeFragment = new RunningSchemeFragment();
				if (!webFlag) {
					reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.Model_URL,
							"getCurrentModel");
				}
				Bundle bundle = new Bundle();
				bundle.putString("model", model);// 传对象给子fragment
				runningSchemeFragment.setArguments(bundle);
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, runningSchemeFragment)
						.commit();
				((MainActivity) menuFragment.getActivity()).mDrawerLayout
						.closeDrawers();
			}
		});

		running_model = (LinearLayout) view.findViewById(R.id.running_model);
		running_model.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragmentManager = menuFragment.getActivity()
						.getFragmentManager();
				RunningModelFragment runningModelFragment = new RunningModelFragment();
				if (!webFlag) {
//					reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.OneSport_URL,
//							"getCurrentSport");
					reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.Model_URL,
							"getCurrentModel");
					reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.OneSport_URL,
							"getCurrentSport");
				}
				Bundle bundle = new Bundle();
				bundle.putString("model", model);
				bundle.putString("onesport",oneSport);
				runningModelFragment.setArguments(bundle);
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, runningModelFragment)
						.commit();
				((MainActivity) menuFragment.getActivity()).mDrawerLayout
						.closeDrawers();

			}
		});

		mine = (LinearLayout) view.findViewById(R.id.mine);
		mine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				fragmentManager = menuFragment.getActivity()
						.getFragmentManager();
				MineFrament mineFragment = new MineFrament();
				if (!webFlag) {
					reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.Model_URL,
							"getCurrentModel");
				}
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, mineFragment).commit();
				((MainActivity) menuFragment.getActivity()).mDrawerLayout
						.closeDrawers();
			}
		});

		exit = (LinearLayout) view.findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (TxtFileUtil.deleteLoginFlagFile()) {
					Toast.makeText(getActivity(), "退出登录", Toast.LENGTH_SHORT)
							.show();
					System.exit(0);// 退出程序
				} else {
					Toast.makeText(getActivity(), "退出登录失败", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
		return view;
	}

	@Override
	public void handleException(Object ex) {
		Toast toast = Toast.makeText(getActivity(), "请检查网络连接",
				Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public void handleResultOfWebService(String methodName, Object result) {
		if (methodName.equals("getCurrentModel")) {
			model = (String) result;

		} else if (methodName.equals("getCurrentSport")) {
			oneSport = (String) result;
		} else {
			sportData = (String) result;
		}
	}
}
