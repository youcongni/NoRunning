package ui.statisticsDisplay.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import ui.statisticsDisplay.viewModel.FriendsListModel;
import ui.statisticsDisplay.viewModel.FriendsMessageModal;

import com.baidu.location.f;
import com.example.androidui_sample_demo.R;

import domain.systemManaConfig.Friends;
import domain.systemManaConfig.SystemManageService;
import domain.systemManaConfig.User;

import foundation.dataService.base.DataContext;
import foundation.dataService.util.DateService;
import foundation.dataService.util.ImageTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsMessageActivity extends Activity implements
		android.view.View.OnClickListener {
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 1;
	private static final int SCALE = 5;// 照片缩小比例
	// 数据库操作类
	private DataContext dataContext;
	// 业务服务类
	private SystemManageService sysManagerService;
	private Button btn_addFriend, btn_alterName;
	private TextView tv_anotherName, tv_sex, tv_age, tv_city, tv_lastRunTime,
			tv_userName, tv_acountnum, tv_birthday, tv_personalword;
	private Friends friendOfCurrentUser;
	private ImageView ib_portrait;
	// 存放图片的字节流
	private byte[] portrait = null;
	// 好友表实体类
	private Friends friend;
	private String email;
	// 用户表实体类
	private User user;
	// viewmodal
	private FriendsMessageModal friendsMessageModal;

	// 标志从哪个界面跳转过来的标签
	private int flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_message);
		/** 使得打开界面后不自动跳出软键盘 */
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		init();
	}

	public void init() {
		btn_addFriend = (Button) findViewById(R.id.btn_addFriend);
		btn_addFriend.setOnClickListener(this);
		btn_alterName = (Button) findViewById(R.id.btn_altername);
		btn_alterName.setOnClickListener(this);
		tv_anotherName = (TextView) findViewById(R.id.tv_anothername);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_lastRunTime = (TextView) findViewById(R.id.tv_lastrunninttime);
		tv_userName = (TextView) findViewById(R.id.tv_username);
		tv_acountnum = (TextView) findViewById(R.id.tv_acountnum);
		tv_birthday = (TextView) findViewById(R.id.tv_birthday);
		tv_personalword = (TextView) findViewById(R.id.tv_personalword);
		tv_age = (TextView) findViewById(R.id.tv_age);

		ib_portrait = (ImageView) findViewById(R.id.ib_portrait);
		ib_portrait.setOnClickListener(this);

		dataContext = new DataContext();
		sysManagerService = new SystemManageService();
		friendsMessageModal = new FriendsMessageModal();

		/** 接收从上一个界面传过来的数据 */
		// 判断是从哪个界面跳转过来的
		Bundle bundle = this.getIntent().getExtras();
		email = bundle.getString("email");

		flag = this.getIntent().getFlags();
		
		friendOfCurrentUser = sysManagerService.gerFriend(email, sysManagerService.getCurrentLoginedUser());

		try {
			// 取出用户信息
			user = dataContext.queryById(User.class, String.class, email);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 假如是从好友列表界面跳转过来的
		if (flag == FriendsListActivity.FROMFRIENDSListACTIVITY) {
			// 假如该用户是我的好友则按钮显示为删除好友
			btn_addFriend.setText("删除该好友");

			String anotherName = bundle.getString("anotherName");

			friendsMessageModal = initModal(user, anotherName);
			loadView(friendsMessageModal);

		}
		// 假如是从添加好友界面跳转过来的
		if (flag == AddFriendsActivity.FROMADDfRIENDSACTIVITY) {
			
			
			friendsMessageModal = initModal(user);
			loadView(friendsMessageModal);
			if (friendOfCurrentUser == null) {
				// 假如该用户不是我的好友
				// friend = new Friends(1, Name, Name, "哎呦,不错哦");
			} else {
				// 假如该用户是我的好友则按钮显示为删除好友
				btn_addFriend.setText("删除该好友");
			}
		}

	}

	// 初始化viewModal
	public FriendsMessageModal initModal(User user, String anotherName) {
		FriendsMessageModal friendsMessageModal = new FriendsMessageModal();

		friendsMessageModal.setProtrait(user.getProtrait());
		friendsMessageModal.setAcountNum(user.getEmail());
		friendsMessageModal.setAge(22);
		friendsMessageModal.setAnotherName(anotherName);
		friendsMessageModal.setBirthday(user.getBirthday());
		friendsMessageModal.setCity(user.getCity());
		friendsMessageModal.setLastRunTime(new Date());
		friendsMessageModal.setPersonalWord(user.getPersonalword());
		friendsMessageModal.setSex(user.getSex());
		friendsMessageModal.setUserName(user.getNickName());
		return friendsMessageModal;
	}

	// 初始化viewModal
	public FriendsMessageModal initModal(User user) {
		FriendsMessageModal friendsMessageModal = new FriendsMessageModal();
		friendsMessageModal.setProtrait(user.getProtrait());
		friendsMessageModal.setAcountNum(user.getEmail());
		friendsMessageModal.setAge(22);
		friendsMessageModal.setAnotherName(user.getNickName());
		friendsMessageModal.setBirthday(user.getBirthday());
		friendsMessageModal.setCity(user.getCity());
		friendsMessageModal.setLastRunTime(new Date());
		friendsMessageModal.setPersonalWord(user.getPersonalword());
		friendsMessageModal.setSex(user.getSex());
		friendsMessageModal.setUserName(user.getNickName());
		return friendsMessageModal;
	}

	// 装载界面
	public void loadView(FriendsMessageModal viewModal) {
		Bitmap bmProtrait = ImageTools.byteToBitmap(viewModal.getProtrait());
		ib_portrait.setImageBitmap(ImageTools.zoomBitmap(bmProtrait, 200, 210));
		tv_anotherName.setText(viewModal.getAnotherName());
		tv_sex.setText(viewModal.getSex());
		tv_age.setText(viewModal.getAge() + "");
		tv_city.setText(viewModal.getCity());
		tv_lastRunTime.setText(DateService.changeDateFormat(viewModal
				.getLastRunTime()));
		tv_userName.setText(viewModal.getUserName());
		tv_acountnum.setText(viewModal.getAcountNum());
		tv_birthday.setText(viewModal.getBirthday());
		tv_personalword.setText(viewModal.getPersonalWord());

	}

	/**
	 * 将user表的信息装载到friend表中
	 */
	public Friends loadToFriend(User user) {
	
		Friends friend = new Friends();
		friend.setAnotherName(user.getNickName());
		friend.setEmail(user.getEmail());
		friend.setNickName(user.getNickName());
		friend.setOneAndOneUser(user);
		friend.setPersonalWord(user.getPersonalword());
		friend.setProtrait(user.getProtrait());
		friend.setOneAndMoreUser(sysManagerService.getCurrentLoginedUser());

		return friend;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_addFriend:
			if (btn_addFriend.getText().equals("加为好友")) {
				String message = null;
				User currentLoginedUser=sysManagerService.getCurrentLoginedUser();
				if (currentLoginedUser.getEmail().equals(this.user.getEmail())) {
					message = "不能添加自己为好友";
				} else {
					friendOfCurrentUser = sysManagerService.gerFriend(email,
							sysManagerService.getCurrentLoginedUser());
					if (friendOfCurrentUser != null) {
						message = "好友请求发送成功，等待对方验证";
					} else {

						friend = loadToFriend(this.user);
						try {
							dataContext.add(friend, Friends.class,
									Integer.class);
							message = "好友请求发送成功，等待对方验证";
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				Toast.makeText(getApplication(), message, Toast.LENGTH_LONG)
						.show();
			} else {

				// 根据id号删除好友
				try {
					dataContext.delete(friendOfCurrentUser, Friends.class,
							Integer.class);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(getApplication(), "成功删除好友", Toast.LENGTH_LONG)
						.show();
			}
			break;
		case R.id.btn_altername:

			break;
		case R.id.ib_portrait:

			// showPicturePicker(FriendsMessageActivity.this);
			break;
		}
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (resultCode == RESULT_OK) {
	// switch (requestCode) {
	// case TAKE_PICTURE:
	// // 将保存在本地的图片取出并缩小后显示在界面上
	// Bitmap bitmap = BitmapFactory.decodeFile(Environment
	// .getExternalStorageDirectory() + "/image.jpg");
	// Bitmap newBitmap = ImageTools.zoomBitmap(bitmap,
	// bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
	// // 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
	// bitmap.recycle();
	//
	// // 将处理过的图片显示在界面上，并保存到本地
	// ib_portrait.setImageBitmap(newBitmap);
	// ImageTools.savePhotoToSDCard(newBitmap, Environment
	// .getExternalStorageDirectory().getAbsolutePath(),
	// String.valueOf(System.currentTimeMillis()));
	// portrait = ImageTools.bitmapToBytes(newBitmap);
	//
	// break;
	//
	// case CHOOSE_PICTURE:
	// ContentResolver resolver = getContentResolver();
	// // 照片的原始资源地址
	// Uri originalUri = data.getData();
	// try {
	// // 使用ContentProvider通过URI获取原始图片
	// Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,
	// originalUri);
	// if (photo != null) {
	// // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
	// Bitmap smallBitmap = ImageTools.zoomBitmap(photo, 200,
	// 210);
	// // Bitmap smallBitmap = ImageTools.zoomBitmap(photo,
	// // photo.getWidth() / SCALE, photo.getHeight()
	// // / SCALE);
	// // 释放原始图片占用的内存，防止out of memory异常发生
	// photo.recycle();
	// ib_portrait.setImageBitmap(smallBitmap);
	// portrait = ImageTools.bitmapToBytes(smallBitmap);
	// }
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// break;
	//
	// default:
	// break;
	// }
	// }
	// }
	//
	// public void showPicturePicker(Context context) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(context);
	// builder.setTitle("图片来源");
	// builder.setNegativeButton("取消", null);
	// builder.setItems(new String[] { "拍照", "相册" },
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// switch (which) {
	// case TAKE_PICTURE:
	// Intent openCameraIntent = new Intent(
	// MediaStore.ACTION_IMAGE_CAPTURE);
	// Uri imageUri = Uri.fromFile(new File(Environment
	// .getExternalStorageDirectory(), "image.jpg"));
	// // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
	// openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	// imageUri);
	// startActivityForResult(openCameraIntent,
	// TAKE_PICTURE);
	// break;
	//
	// case CHOOSE_PICTURE:
	// Intent openAlbumIntent = new Intent(
	// Intent.ACTION_GET_CONTENT);
	// openAlbumIntent.setType("image/*");
	// startActivityForResult(openAlbumIntent,
	// CHOOSE_PICTURE);
	// break;
	//
	// default:
	// break;
	// }
	// }
	// });
	// builder.create().show();
	// }

}
