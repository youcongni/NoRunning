package ui.statisticsDisplay.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ui.statisticsDisplay.viewModel.FriendsListModel;

import com.example.androidui_sample_demo.R;
import com.example.androidui_sample_demo.R.drawable;

import domain.statisticsDisplay.CharacterParser;
import domain.statisticsDisplay.ClearEditText;
import domain.statisticsDisplay.FriendsListAdapter;
import domain.statisticsDisplay.PullRefreshListView;
import domain.statisticsDisplay.PullRefreshListView.IXListViewListener;
import domain.systemManaConfig.Friends;
import domain.systemManaConfig.SystemManageService;
import domain.systemManaConfig.User;
import foundation.dataService.SystemManagerDataService;
import foundation.dataService.base.DataContext;
import foundation.dataService.util.ImageTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class AddFriendsActivity extends Activity implements IXListViewListener {
	// 可上拉刷新下拉显示更多数据的listview
	private PullRefreshListView pullRefreshListView;
	// 线程handle
	private Handler mHandler;
	private FrameLayout frameLayout;
	private int sizeOfAllFriend;
	private List<Friends> friends;
	private List<User> users;
	//private SystemManageService systemManagerService;
	private DataContext dataContext;

	private TextView dialog;
	private FriendsListAdapter adapter;
	private ClearEditText mClearEditText;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;

	private List<FriendsListModel> friendsDataList;
	
	// 装载所有的用户
	private List<FriendsListModel> allFriendsDataList;
	// 装载过滤后的所有的用户
	private List<FriendsListModel> allFriendsDataListAfterFilter;
	/**
	 * 标记从该页面跳转到好友信息的页面
	 */
	public static int FROMADDfRIENDSACTIVITY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_addfriend);
		/** 使得打开界面后不自动跳出软键盘 */
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initViews();
	}

	private void initViews() {
		//systemManagerService = new SystemManageService();

		dataContext = new DataContext();
		frameLayout=(FrameLayout) findViewById(R.id.frameLayoutOfAddFriends);
		pullRefreshListView = (PullRefreshListView) findViewById(R.id.pullRefreshListView);
		//将listview设为不可见
		pullRefreshListView.setVisibility(View.GONE);
		pullRefreshListView.setPullLoadEnable(true);
		
//		//模拟数据
//		users=new ArrayList<User>();
//		User user=new User();
//		user.setBirthday("1994-06-08");
//		user.setCity("福州");
//		user.setEmail("1215605211@qq.com");
//		user.setHeight(176);
//		user.setNickName("黄耀辉");
//		user.setPassword("123456");
//		user.setPersonalword("爱跑步，爱特步");
//		user.setProtrait(ImageTools.drawableToBytes(getResources().getDrawable(R.drawable.start_running_title)));
//		user.setSex("男");
//		user.setWeight(66);
//		user.setLogin(true);
//		
//		User user2=new User();
//		user2.setBirthday("1994-06-08");
//		user2.setCity("福州");
//		user2.setEmail("2454703958@qq.com");
//		user2.setHeight(176);
//		user2.setNickName("郑震培");
//		user2.setPassword("123456");
//		user2.setPersonalword("福建师范大学南区区草");
//		user2.setProtrait(ImageTools.drawableToBytes(getResources().getDrawable(R.drawable.card_title_2)));
//		user2.setSex("男");
//		user2.setWeight(66);
//		user2.setLogin(false);
//		try {
//		
//		dataContext.add(user, User.class, String.class);
//		dataContext.add(user2, User.class, String.class);
//		} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		}

		//模拟数据
		users=new ArrayList<User>();
		try {
			users=dataContext.queryForAll(User.class, String.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//装载所有的用户信息到modal
		loadUsersToModal(users);
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();

				String email = ((FriendsListModel) adapter
						.getItem(position - 1)).getEmail();
				bundle.putString("email", email);
				Intent intent = new Intent(AddFriendsActivity.this,
						FriendsMessageActivity.class);
				intent.addFlags(FROMADDfRIENDSACTIVITY);

				intent.putExtras(bundle);
				startActivity(intent);

			}

		});
		
		

		/**
		 * 刚进入界面时没经过过滤(按关键字查询过滤)，
		 * 所有allFriendsDataListAfterFilter等于allFriendsDataList
		 */
		allFriendsDataListAfterFilter = new ArrayList<FriendsListModel>();

		

		sizeOfAllFriend = allFriendsDataListAfterFilter.size();

		// 下拉刷新后显示更新数据后的界面信息
		getItemsForRefresh();

		// 刚打开界面时显示的数据

		adapter = new FriendsListAdapter(this, friendsDataList);

		pullRefreshListView.setAdapter(adapter);

		pullRefreshListView.setXListViewListener(this);
		mHandler = new Handler();

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	
	/**
	 * 装载所有的用户信息到viewModal
	 */
	public void loadUsersToModal(List<User> users){
		/**
		 * 装载所有的好友
		 */
		allFriendsDataList = new ArrayList<FriendsListModel>();
		for (int i = users.size() - 1; i >= 0; i--) {

			User user = new User();
			user= users.get(i);

			FriendsListModel friendsListModal = new FriendsListModel(
					user.getNickName(), user.getEmail(),user.getPersonalword(),user.getProtrait());

			allFriendsDataList.add(friendsListModal);
		}

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		//将listview设为可见
		pullRefreshListView.setVisibility(View.VISIBLE);
		//将背景图设为不可见
		frameLayout.setBackground(null);
		List<FriendsListModel> filterDateList = new ArrayList<FriendsListModel>();
		List<FriendsListModel> nullDataList=new ArrayList<FriendsListModel>();
		if (TextUtils.isEmpty(filterStr)) {
			//将listv设为不可见
			pullRefreshListView.setVisibility(View.GONE);
			//设置背景图
			frameLayout.setBackground(getResources().getDrawable(R.drawable.start_running_title));
			filterDateList = nullDataList;

		} else {
			filterDateList.clear();
			for (FriendsListModel sortModel : allFriendsDataList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// adapter.updateListView(filterDateList);
		// 将过滤后的好友信息集合赋给allFriendsDataListAfterFilter
		allFriendsDataListAfterFilter = filterDateList;
		// 得到此时allFriendsDataListAfterFilter的大小
		sizeOfAllFriend = allFriendsDataListAfterFilter.size();
		// 刷新界面
		getItemsForRefresh();
		adapter = new FriendsListAdapter(AddFriendsActivity.this,
				friendsDataList);
		pullRefreshListView.setAdapter(adapter);
	}

	private void getItemsForRefresh() {
		// 装配到viewModal
		int i;
		// 用于动态装载信息的集合类
		friendsDataList = new ArrayList<FriendsListModel>();
		int head;
		int tail;
		if (allFriendsDataListAfterFilter.size() - 1 < 0) {
			head = -1;
		} else {
			head = allFriendsDataListAfterFilter.size() - 1;
		}
		// 每次刷新都只显示每一页10条好友信息
		if (allFriendsDataListAfterFilter.size() - 10 <= 0) {
			tail = 0;
			sizeOfAllFriend = 0;
		} else {
			tail = allFriendsDataListAfterFilter.size() - 10;
			sizeOfAllFriend = allFriendsDataListAfterFilter.size() - 10;
		}
		for (i = head; i >= tail; i--) {

			FriendsListModel friendsListModal = allFriendsDataListAfterFilter
					.get(i);

			friendsDataList.add(friendsListModal);
		}

	}

	private void geneItemsForLoadMore() {
		// 装配到viewModal
		int i;

		int head;
		int tail;
		if (sizeOfAllFriend - 1 < 0) {
			head = -1;
		} else {
			head = sizeOfAllFriend - 1;
		}
		// 当第一页的好友信息有10条了，要显示更多好友信息时，通过上拉加载更多
		if (sizeOfAllFriend - 10 <= 0) {
			tail = 0;
		} else {
			tail = sizeOfAllFriend - 10;
		}
		for (i = head; i >= tail; i--) {

			FriendsListModel friendsListModal = allFriendsDataListAfterFilter
					.get(i);

			friendsDataList.add(friendsListModal);
		}
		sizeOfAllFriend = i + 1;
	}

	private void onLoad() {
		pullRefreshListView.stopRefresh();
		pullRefreshListView.stopLoadMore();
		pullRefreshListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {

				getItemsForRefresh();
				adapter.updateListView(friendsDataList);
//				adapter = new FriendsListAdapter(AddFriendsActivity.this,
//						friendsDataList);
//				pullRefreshListView.setAdapter(adapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItemsForLoadMore();
				adapter.notifyDataSetChanged();
				adapter.updateListView(friendsDataList);
//				adapter = new FriendsListAdapter(AddFriendsActivity.this,
//						friendsDataList);
//				pullRefreshListView.setAdapter(adapter);
				onLoad();
			}
		}, 2000);
	}

}
