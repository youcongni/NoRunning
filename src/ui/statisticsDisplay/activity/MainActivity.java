/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ui.statisticsDisplay.activity;

import java.util.HashMap;
import java.util.List;

import ui.statisticsDisplay.fragment.MenuFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.File.TxtFileUtil;
import com.alibaba.fastjson.JSON;
import com.example.androidui_sample_demo.R;

import domain.systemManaConfig.SystemManageService;
import foundation.dataService.util.LoadingDialog;
import foundation.webservice.ConnetNet;
import foundation.webservice.MemoWebPara;
import foundation.webservice.WebServiceDelegate;
import foundation.webservice.WebServiceUtils;
import foundation.webservice.help.MinuteSportData;
import foundation.webservice.help.OneSport;

/**
 * This example illustrates a common usage of the DrawerLayout widget in the
 * Android support library.
 * <p/>
 * <p>
 * When a navigation (left) drawer is present, the host activity should detect
 * presses of the action bar's Up affordance as a signal to open and close the
 * navigation drawer. The ActionBarDrawerToggle facilitates this behavior. Items
 * within the drawer should fall into one of two categories:
 * </p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic
 * policies as list or tab navigation in that a view switch does not create
 * navigation history. This pattern should only be used at the root activity of
 * a task, leaving some form of Up navigation active for activities further down
 * the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an
 * alternate parent for Up navigation. This allows a user to jump across an
 * app's navigation hierarchy at will. The application should treat this as it
 * treats Up navigation from a different task, replacing the current task stack
 * using TaskStackBuilder or similar. This is the only form of navigation drawer
 * that should be used outside of the root activity of a task.</li>
 * </ul>
 * <p/>
 * <p>
 * Right side drawers should be used for actions, not navigation. This follows
 * the pattern established by the Action Bar that navigation should be to the
 * left and actions to the right. An action should be an operation performed on
 * the current contents of the window, for example enabling or disabling a data
 * overlay on top of the current content.
 * </p>
 */
public class MainActivity extends Activity implements WebServiceDelegate {
	public DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private WebServiceUtils webService;
	private String sportData = null;
	private boolean webFlag = false;
	private LoadingDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		dialog = new LoadingDialog(this);
		reConnectNet(MemoWebPara.NAMESPACE, MemoWebPara.OneSport_URL,
				"getDataStatistics");
		// mDrawerFrame = (FrameLayout) findViewById(R.id.left_drawer);

		addMenu();
		dialog.show();
		addFragment();
		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void addMenu() {
		Fragment fragment = new MenuFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.left_drawer, fragment)
				.commit();
	}

	// 这边联网
	public void addFragment() {
		Fragment fragment = new StartrunningActivity();
		FragmentManager fragmentManager = getFragmentManager();
		Bundle bundle = new Bundle();
		bundle.putString("oneSport", null);
		fragment.setArguments(bundle);
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void reConnectNet(String nameSpace, String url, String method) {
		webFlag = ConnetNet.isConnect(getApplication());
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
	public void handleException(Object ex) {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(getApplication(), "请检查网络连接",
				Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public void handleResultOfWebService(String methodName, Object result) {
		// TODO Auto-generated method stub
		sportData=(String)result;
		Fragment fragment = new StartrunningActivity();
		FragmentManager fragmentManager = getFragmentManager();
		Bundle bundle = new Bundle();
		bundle.putString("sportData", sportData);
		fragment.setArguments(bundle);
		dialog.dismiss();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

	}

}