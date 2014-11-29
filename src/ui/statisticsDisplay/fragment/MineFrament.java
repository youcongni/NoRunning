package ui.statisticsDisplay.fragment;

import ui.community.FriendNewsActivity;
import ui.statisticsDisplay.activity.FriendsListActivity;
import ui.statisticsDisplay.activity.HistoryActivity;
import ui.statisticsDisplay.activity.PersonalInfoActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.androidui_sample_demo.R;

public class MineFrament extends Fragment {
	private LinearLayout history;
	private LinearLayout personal_info;
	private LinearLayout friendsList;
	private LinearLayout friendnews;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_mine, null);
		getActivity().setTitle("我");

		/** 跳转到个人信息界面*/
		personal_info = (LinearLayout) view.findViewById(R.id.tv_personal_info);
		personal_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						PersonalInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("skip", "fromMineFrament");
				intent.putExtra("key", bundle);
				startActivity(intent);
				MineFrament.this.onDestroy();

			}
		});

		/** 跳转到历史记录界面*/
		history = (LinearLayout) view.findViewById(R.id.tv_history);
		history.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), HistoryActivity.class);
				startActivity(intent);
				MineFrament.this.onDestroy();

			}
		});

		/** 跳转到好友列表界面*/
		friendsList = (LinearLayout) view.findViewById(R.id.layout_friendsList);
		friendsList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						FriendsListActivity.class);
				startActivity(intent);
			   MineFrament.this.onDestroy();

			}
		});

		/** 跳转到好友动态界面*/
		friendnews = (LinearLayout) view.findViewById(R.id.friendnews);
		friendnews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						FriendNewsActivity.class);
				startActivity(intent);
			   MineFrament.this.onDestroy();

			}
		});
		return view;
	}

	
	
	
	
	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		menu.clear();
//		inflater = getActivity().getMenuInflater();
//		inflater.inflate(R.menu.menu_main, menu);
//		super.onCreateOptionsMenu(menu, inflater);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.setting:
//			Intent intent = new Intent(getActivity(), SettingActivity.class);
//			startActivity(intent);
//			MineFrament.this.onDestroy();
//			break;
//		}
//		return super.onOptionsItemSelected(item);
//	}

}
