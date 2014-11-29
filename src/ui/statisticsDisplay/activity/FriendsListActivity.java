package ui.statisticsDisplay.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ui.statisticsDisplay.viewModel.FriendsListModel;

import com.example.androidui_sample_demo.R;

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
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsListActivity extends Activity implements IXListViewListener {
	// 可上拉刷新下拉显示更多数据的listview
	private PullRefreshListView pullRefreshListView;
	// 线程handle
	private Handler mHandler;
	private int sizeOfAllFriend;
	private List<Friends> friends;

	private SystemManageService systemManagerService;
	private DataContext dataContext;

	private TextView dialog;
	private FriendsListAdapter adapter;
	private ClearEditText mClearEditText;
    private List<User> users;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<FriendsListModel> friendsDataList;

	/**
	 * 标记从该页面跳转到好友信息的页面
	 */
	public static int FROMFRIENDSListACTIVITY = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		/** 使得打开界面后不自动跳出软键盘 */
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("我的好友");
		initViews();
	}

	private void initViews() {
		systemManagerService = new SystemManageService();
		
		dataContext = new DataContext();
//try {
//	dataContext.deleteAll(Friends.class, Integer.class);
//} catch (SQLException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
	
		//模拟数据
//				users=new ArrayList<User>();
//				User user=new User();
//				user.setBirthday("1994-06-08");
//				user.setCity("福州");
//				user.setEmail("1215605211@qq.com");
//				user.setHeight(176);
//				user.setNickName("黄耀辉");
//				user.setPassword("123456");
//				user.setPersonalword("爱跑步，爱特步");
//				user.setProtrait(ImageTools.drawableToBytes(getResources().getDrawable(R.drawable.start_running_title)));
//				user.setSex("男");
//				user.setWeight(66);
//				user.setLogin(true);
//				
//				User user2=new User();
//				user2.setBirthday("1994-06-08");
//				user2.setCity("福州");
//				user2.setEmail("2454703958@qq.com");
//				user2.setHeight(176);
//				user2.setNickName("郑震培");
//				user2.setPassword("123456");
//				user2.setPersonalword("福建师范大学南区区草");
//				user2.setProtrait(ImageTools.drawableToBytes(getResources().getDrawable(R.drawable.card_title_2)));
//				user2.setSex("男");
//				user2.setWeight(66);
//				user2.setLogin(false);
//				
//				User user3=new User();
//				user3.setBirthday("1994-06-08");
//				user3.setCity("福州");
//				user3.setEmail("1245689034@qq.com");
//				user3.setHeight(173);
//				user3.setNickName("林秋");
//				user3.setPassword("123456");
//				user3.setPersonalword("今天跑了一公里很享受");
//				user3.setProtrait(ImageTools.drawableToBytes(getResources().getDrawable(R.drawable.cover_1)));
//				user3.setSex("男");
//				user3.setWeight(60);
//				user3.setLogin(false);
//				
//				User user4=new User();
//				user4.setBirthday("1994-08-08");
//				user4.setCity("福州");
//				user4.setEmail("2454603958@qq.com");
//				user4.setHeight(176);
//				user4.setNickName("游敏");
//				user4.setPassword("123456");
//				user4.setPersonalword("要跑步就用i酷跑吧");
//				user4.setProtrait(ImageTools.drawableToBytes(getResources().getDrawable(R.drawable.cover_2)));
//				user4.setSex("男");
//				user4.setWeight(66);
//				user4.setLogin(false);
//				
//				User user5=new User();
//				user5.setBirthday("1994-03-08");
//				user5.setCity("福州");
//				user5.setEmail("2453403958@qq.com");
//				user5.setHeight(174);
//				user5.setNickName("林浩宇");
//				user5.setPassword("123456");
//				user5.setPersonalword("昨天跑了两个小时很满足");
//				user5.setProtrait(ImageTools.drawableToBytes(getResources().getDrawable(R.drawable.cover_3)));
//				user5.setSex("男");
//				user5.setWeight(62);
//				user5.setLogin(false);
//				try {
////				dataContext.delete(user, User.class, String.class);
////				
////				dataContext.delete(user2, User.class, String.class);
////				dataContext.delete(user3, User.class, String.class);
////				dataContext.delete(user4, User.class, String.class);
////				dataContext.delete(user5, User.class, String.class);
//				dataContext.add(user, User.class, String.class);
//				dataContext.add(user2, User.class, String.class);
//				dataContext.add(user3, User.class, String.class);
//				dataContext.add(user4, User.class, String.class);
//				dataContext.add(user5, User.class, String.class);
//				
//				} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				}
				
		

		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pullRefreshListView = (PullRefreshListView) findViewById(R.id.pullRefreshListView);
		pullRefreshListView.setPullLoadEnable(true);
		pullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Bundle bundle = new Bundle();

				String email = ((FriendsListModel) adapter
						.getItem(position - 1)).getEmail();
				String anotherName=((FriendsListModel) adapter
						.getItem(position - 1)).getName();
				bundle.putString("email", email);
				bundle.putString("anotherName", anotherName);
				Intent intent = new Intent(FriendsListActivity.this,
						FriendsMessageActivity.class);
				intent.addFlags(FROMFRIENDSListACTIVITY);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		// 添加长按点击
		pullRefreshListView
				.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {
						menu.setHeaderTitle("选择菜单");
						menu.add(0, 0, 0, "修改备注");
						menu.add(0, 1, 0, "删除好友");
					}
				});

		// 下拉刷新后显示更新数据后的界面信息
		getItemsForRefresh();

		// 刚打开界面时不显示任何数据

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

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");
		if (item.getItemId() == 0) {
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.activity_friends_dialog,
					null);
			// 获取长按了listview的哪个item
			final AdapterView.AdapterContextMenuInfo menuinfo;
			menuinfo = (AdapterContextMenuInfo) item.getMenuInfo();
			
			final EditText et_friendsAnotherName = (EditText) layout
					.findViewById(R.id.et_friendsAnotherName);

			new AlertDialog.Builder(this)
					.setTitle("好友备注修改")
					.setView(layout)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									String friendsAnotherName = et_friendsAnotherName
											.getText().toString();

									// 获得listview上面的数据
									String email = ((TextView) menuinfo.targetView
											.findViewById(R.id.tv_acountnum)).getText()
											.toString();
									
									
									Friends friend=systemManagerService.gerFriend(email,systemManagerService.getCurrentLoginedUser());
									friend.setAnotherName(friendsAnotherName);
									// 更新好友信息
									try {
										dataContext.update(friend,
												Friends.class,
												Integer.class);
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									// 更新列表
									// 因为自定义的pullrefreshListview的listview是从1开始的，所以menuinfo.position要减去1
									friendsDataList
											.get(menuinfo.position - 1)
											.setName(
													friendsAnotherName);
									adapter.updateListView(friendsDataList);
									
										

									
									
								}
							}).setNegativeButton("取消", null).show();

		} else if (item.getItemId() == 1) {
			// 获取长按了哪个listview
			final AdapterView.AdapterContextMenuInfo menuinfo;
			menuinfo = (AdapterContextMenuInfo) item.getMenuInfo();

			// 弹出一个确认对话框
			new AlertDialog.Builder(this)
					.setTitle("删除提示框")
					.setMessage("确认删除该好友？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									// 获得listview上面的数据
									String email = ((TextView) menuinfo.targetView
											.findViewById(R.id.tv_acountnum)).getText()
											.toString();
									
									
									Friends frined=systemManagerService.gerFriend(email,systemManagerService.getCurrentLoginedUser());
									// 根据id号删除好友
									systemManagerService.deleteFriendById(frined.getId());
									// 将该好友从列表中移除
									friendsDataList
											.remove(menuinfo.position - 1);
									// 更新列表
									adapter.updateListView(friendsDataList);
								}
							}).setNegativeButton("取消", null).show();

		}
		return super.onContextItemSelected(item);
	}

	// /**
	// * 为ListView填充数据
	// * @param date
	// * @return
	// */
	// private List<FriendsListModel> filledData(String [] date){
	// List<FriendsListModel> mSortList = new ArrayList<FriendsListModel>();
	//
	// for(int i=0; i<date.length; i++){
	// FriendsListModel sortModel = new FriendsListModel();
	// sortModel.setName(date[i]);
	// //汉字转换成拼音
	// String pinyin = characterParser.getSelling(date[i]);
	// String sortString = pinyin.substring(0, 1).toUpperCase();
	//
	//
	// mSortList.add(sortModel);
	// }
	// return mSortList;
	//
	// }

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<FriendsListModel> filterDateList = new ArrayList<FriendsListModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = friendsDataList;
		} else {
			filterDateList.clear();
			for (FriendsListModel sortModel : friendsDataList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		adapter.updateListView(filterDateList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {// 创建系统功能菜单
		// TODO Auto-generated method stub
		// 将自己写的菜单项添加到actiovbar
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.menu_addfriend, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		case R.id.addfriend:
			Intent intent = new Intent(FriendsListActivity.this,
					AddFriendsActivity.class);
			startActivity(intent);
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	private void getItemsForRefresh() {
		// 每次刷新都得先从数据库获得最新的数据
		User user=systemManagerService.getCurrentLoginedUser();
		friends = user.getFriends();
		sizeOfAllFriend = friends.size();

		// 装配到viewModal
		int i;
		friendsDataList = new ArrayList<FriendsListModel>();
		int head;
		int tail;
		if (friends.size() - 1 < 0) {
			head = -1;
		} else {
			head = friends.size() - 1;// 7
		}
		//设置每一页最多只显示15条好友信息，多余的则通过上拉加载更多
		if (friends.size() - 15 <= 0) {
			tail = 0;
			sizeOfAllFriend = 0;
		} else {
			tail = friends.size() - 15;// 3
			sizeOfAllFriend = friends.size() - 15;// 3
		}
		for (i = head; i >= tail; i--) {

			Friends myFriend = new Friends();
			myFriend = friends.get(i);

			FriendsListModel friendsListModal = new FriendsListModel(
					myFriend.getAnotherName(),myFriend.getEmail(), myFriend.getPersonalWord(),myFriend.getProtrait());

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
		if (sizeOfAllFriend - 15 <= 0) {
			tail = 0;
		} else {
			tail = sizeOfAllFriend - 15;
		}
		for (i = head; i >= tail; i--) {

			Friends myFriend = new Friends();
			myFriend = friends.get(i);

			FriendsListModel friendsListModal = new FriendsListModel(
					myFriend.getAnotherName(), myFriend.getEmail(),myFriend.getPersonalWord(),myFriend.getProtrait());

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
//				adapter = new FriendsListAdapter(FriendsListActivity.this,
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
//				adapter = new FriendsListAdapter(FriendsListActivity.this,
//						friendsDataList);
//				pullRefreshListView.setAdapter(adapter);
				onLoad();
			}
		}, 2000);
	}

}
