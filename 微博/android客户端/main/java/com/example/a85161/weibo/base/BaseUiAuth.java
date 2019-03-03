package com.example.a85161.weibo.base;

import com.example.a85161.weibo.R;
import com.example.a85161.weibo.base.BaseAuth;
import com.example.a85161.weibo.demo.DemoMap;
import com.example.a85161.weibo.demo.DemoWeb;
import com.example.a85161.weibo.model.Customer;
import com.example.a85161.weibo.test.TestUi;
import com.example.a85161.weibo.ui.UiBlogs;
import com.example.a85161.weibo.ui.UiConfig;
import com.example.a85161.weibo.ui.UiIndex;
import com.example.a85161.weibo.ui.UiLogin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class BaseUiAuth extends BaseUi {
	
	private final int MENU_APP_WRITE = 0;
	private final int MENU_APP_LOGOUT = 1;
	private final int MENU_APP_ABOUT = 2;
	private final int MENU_DEMO_WEB = 3;
	private final int MENU_DEMO_MAP = 4;
	private final int MENU_DEMO_TEST = 5;
	
	protected static Customer customer = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!BaseAuth.isLogin()) {
			this.forward(UiLogin.class);
			this.onStop();
		} else {
			customer = BaseAuth.getCustomer();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		this.bindMainTop();
		this.bindMainTab();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_APP_WRITE, 0, R.string.menu_app_write).setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, MENU_APP_LOGOUT, 0, R.string.menu_app_logout).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		menu.add(0, MENU_APP_ABOUT, 0, R.string.menu_app_about).setIcon(android.R.drawable.ic_menu_info_details);
		menu.add(0, MENU_DEMO_WEB, 0, R.string.menu_demo_web).setIcon(android.R.drawable.ic_menu_search);
		menu.add(0, MENU_DEMO_MAP, 0, R.string.menu_demo_map).setIcon(android.R.drawable.ic_menu_mylocation);
		menu.add(0, MENU_DEMO_TEST, 0, R.string.menu_demo_test).setIcon(android.R.drawable.ic_menu_preferences);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_APP_WRITE: {
				doEditBlog();
				break;
			}
			case MENU_APP_LOGOUT: {
				doLogout(); // do logout first
				forward(UiLogin.class);
				break;
			}
			case MENU_APP_ABOUT:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.menu_app_about);
				String appName = this.getString(R.string.app_name);
				String appVersion = this.getString(R.string.app_version);
				builder.setMessage(appName + " " + appVersion);
				builder.setIcon(R.drawable.version);
				builder.setPositiveButton(R.string.btn_cancel, null);
				builder.show();
				break;
			case MENU_DEMO_WEB:
				forward(DemoWeb.class);
				break;
			case MENU_DEMO_MAP:
				forward(DemoMap.class);
				break;
			case MENU_DEMO_TEST:
				forward(TestUi.class);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void bindMainTop () {
		Button bTopQuit = (Button) findViewById(R.id.main_top_quit);
		if (bTopQuit != null) {
			OnClickListener mOnClickListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
						case R.id.main_top_quit:
							AlertDialog.Builder isExit=new AlertDialog.Builder(BaseUiAuth.this);
							isExit.setTitle("提醒");
							isExit.setMessage("确定要退出吗？");
							isExit.setPositiveButton("确定",diaListener);
							isExit.setNegativeButton("取消",diaListener);
							isExit.show();
							break;
					}
				}
			};
			bTopQuit.setOnClickListener(mOnClickListener);
		}
	}

	DialogInterface.OnClickListener diaListener=new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int buttonId) {
			switch (buttonId) {
				case AlertDialog.BUTTON_POSITIVE:
					doFinish();
					break;
				case AlertDialog.BUTTON_NEGATIVE:
					break;
				default:
					break;
			}
		}
	};
	
	private void bindMainTab () {
		ImageButton bTabHome = (ImageButton) findViewById(R.id.main_tab_1);
		ImageButton bTabBlog = (ImageButton) findViewById(R.id.main_tab_2);
		ImageButton bTabConf = (ImageButton) findViewById(R.id.main_tab_3);
		ImageButton bTabWrite = (ImageButton) findViewById(R.id.main_tab_4);
		if (bTabHome != null && bTabBlog != null && bTabConf != null) {
			OnClickListener mOnClickListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
						case R.id.main_tab_1:
							forward(UiIndex.class);
							break;
						case R.id.main_tab_2:
							forward(UiBlogs.class);
							break;
						case R.id.main_tab_3:
							forward(UiConfig.class);
							break;
						case R.id.main_tab_4:
							doEditBlog();
							break;
					}
				}
			};
			bTabHome.setOnClickListener(mOnClickListener);
			bTabBlog.setOnClickListener(mOnClickListener);
			bTabConf.setOnClickListener(mOnClickListener);
			bTabWrite.setOnClickListener(mOnClickListener);
		}
	}
}