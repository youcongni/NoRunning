package ui.statisticsDisplay.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.androidui_sample_demo.R;

public class StartrunningActivity extends Fragment {
	private Context context;

	private ImageButton image_presonal_info;

	private Button btn_start_running;

	private ImageButton image_presonal_setting;

	private ImageButton image_music;

	private FragmentManager fragmentManager;
	
	private TextView tv_aveHeart, tv_aveSpeed, tv_toDistance, tv_toTime;
	private String aveHeartRate , aveSpeed,totalDistance, totalTime;
	private String sportData=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = (MainActivity) getActivity();
		Bundle bundle = this.getArguments();
		// Toast.makeText(getActivity(), "模型更新中...", Toast.LENGTH_SHORT).show();
		String s = null;
		s = bundle.getString("sportData");// 接受menuFragment信息
		if (s != null) {
			if (!s.equals("null")) {
				sportData = s;
				String[] temp = sportData.split(","); 
				aveHeartRate = temp[0];
				aveSpeed=temp[1];
				totalDistance=temp[2];
				totalTime=temp[3];
			} else {
				System.out.println("--");
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, null);
		getActivity().setTitle("i酷跑");
		tv_aveHeart = (TextView) view.findViewById(R.id.tv_aveHeart);
		tv_aveSpeed = (TextView) view.findViewById(R.id.tv_aveSpeed);
		tv_toDistance = (TextView) view.findViewById(R.id.tv_toDistance);
		tv_toTime = (TextView) view.findViewById(R.id.tv_toTime);
		if (sportData != null) {		
			tv_aveHeart.setText(aveHeartRate);
			tv_aveSpeed.setText(aveSpeed);
			tv_toDistance.setText(totalDistance);
			tv_toTime.setText(totalTime);
		}
		/**
		 * 个人信息设置
		 */
		image_presonal_info = (ImageButton) view
				.findViewById(R.id.image_presonal_info);
		image_presonal_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						PersonalInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("skip", "fromStartRunning");
				intent.putExtra("key", bundle);
				startActivity(intent);
				StartrunningActivity.this.onDestroy();
			}
		});

		/**
		 * 点击开始跑步
		 */
		btn_start_running = (Button) view.findViewById(R.id.btn_start_running);
		btn_start_running.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						DeviceScanActivity.class);
				startActivity(intent);
				StartrunningActivity.this.onDestroy();
			}
		});

		/**
		 * 音乐
		 */
		image_music = (ImageButton) view.findViewById(R.id.image_music);
		image_music.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
				startActivity(intent);
				StartrunningActivity.this.onDestroy();
				
			}
		});
		/**
		 * 设置目标
		 */
		image_presonal_setting = (ImageButton) view
				.findViewById(R.id.image_presonal_setting);
		image_presonal_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						PersonaldataActivity.class);
				startActivity(intent);
				StartrunningActivity.this.onDestroy();
			}
		});

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu_home, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.personalinfo:
			Intent intent = new Intent(getActivity(),
					PersonalInfoActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Bundle bundle = new Bundle();
			bundle.putString("skip", "fromStartRunning");
			intent.putExtra("key", bundle);
			startActivity(intent);
			StartrunningActivity.this.onDestroy();
			
			break;

		case R.id.history:
			Intent intent1 = new Intent(getActivity(), HistoryActivity.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent1);
			StartrunningActivity.this.onDestroy();
			break;

		case R.id.friend:
			Intent intent2 = new Intent(getActivity(),
					FriendsListActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2);
			StartrunningActivity.this.onDestroy();
			break;

		case R.id.friendnews:
			Intent intent3 = new Intent(getActivity(),
					FriendsMessageActivity.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent3);
			StartrunningActivity.this.onDestroy();
			break;

		}
		return super.onOptionsItemSelected(item);
	}
}
