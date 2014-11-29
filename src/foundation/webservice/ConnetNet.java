package foundation.webservice;

import java.util.HashMap;

import com.File.TxtFileUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnetNet {
	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}
	
//	public void reConnectNet(String nameSpace,String url){
//		webFlag = ConnetNet.isConnect(getActivity());
//		webService = new WebServiceUtils(nameSpace,
//				MemoWebPara.Model_URL, this);
//		HashMap<String, Object> args = new HashMap<String, Object>();
//		String loginFlag = null;
//		try {
//			loginFlag = TxtFileUtil.readTxtFile(TxtFileUtil.loginFlag);//读取用户email
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (loginFlag != null) {
//			String[] str = loginFlag.split(",");
//			args.put("email", str[1].toString().trim());
//			webService.callWebService("getCurrentModel", args, String.class);
//		}	
//	}
}
