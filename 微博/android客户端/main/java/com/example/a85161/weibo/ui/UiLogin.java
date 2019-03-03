package com.example.a85161.weibo.ui;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a85161.weibo.R;
import com.example.a85161.weibo.base.BaseAuth;
import com.example.a85161.weibo.base.BaseMessage;
import com.example.a85161.weibo.base.BaseService;
import com.example.a85161.weibo.base.BaseUi;
import com.example.a85161.weibo.base.C;
import com.example.a85161.weibo.model.Customer;
import com.example.a85161.weibo.registerActivity;
import com.example.a85161.weibo.service.NoticeService;

public class UiLogin extends BaseUi{

	private EditText mEditName;
	private EditText mEditPass;
	private CheckBox mCheckBox;
	private ImageView logo;
	private ImageView usernameIcon;
	private ImageView passwdIcon;
	private ImageView mIvLoginUsernameDel;
	private ImageView mIvLoginPwdDel;
	private Button submitBtn;
	private LinearLayout userName;
	private LinearLayout passWord;
	private Button register;
	private SharedPreferences settings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// check if login
		if (BaseAuth.isLogin()) {
			this.forward(UiIndex.class);
		}
		
		// set view after check login
		setContentView(R.layout.ui_login);

		register=(Button)findViewById(R.id.app_register_btn_submit);
		registerListener registerListener=new registerListener();
		register.setOnClickListener(registerListener);
		
		// remember password
		mEditName = (EditText) this.findViewById(R.id.app_login_edit_name);
		mEditPass = (EditText) this.findViewById(R.id.app_login_edit_pass);
		mCheckBox = (CheckBox) this.findViewById(R.id.app_login_check_remember);
		logo=(ImageView)this.findViewById(R.id.iv_login_logo);
		usernameIcon=(ImageView)this.findViewById(R.id.iv_login_username_icon);
		passwdIcon=(ImageView)this.findViewById(R.id.iv_login_pwd_icon);
		mIvLoginUsernameDel=(ImageView)this.findViewById(R.id.iv_login_username_del);
		mIvLoginUsernameDel.setVisibility(View.VISIBLE);
		mIvLoginPwdDel=(ImageView)this.findViewById(R.id.iv_login_pwd_del);
		mIvLoginPwdDel.setVisibility(View.VISIBLE);
		submitBtn=(Button)this.findViewById(R.id.app_login_btn_submit) ;
		userName=(LinearLayout)this.findViewById(R.id.ll_login_username);
		passWord=(LinearLayout)this.findViewById(R.id.ll_login_pwd);
		settings = getPreferences(Context.MODE_PRIVATE);
		if (settings.getBoolean("remember", false)) {
			mCheckBox.setChecked(true);
			mEditName.setText(settings.getString("username", ""));
			mEditPass.setText(settings.getString("password", ""));
		}
		// remember checkbox
		mCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = settings.edit();
				if (mCheckBox.isChecked()) {
					editor.putBoolean("remember", true);
					editor.putString("username", mEditName.getText().toString());
					editor.putString("password", mEditPass.getText().toString());
				} else {
					editor.putBoolean("remember", false);
					editor.putString("username", "");
					editor.putString("password", "");
				}
				editor.commit();
			}
		});

		userNameClear userNameClear=new userNameClear();
		mIvLoginUsernameDel.setOnClickListener(userNameClear);
		passwdClear passwdClear=new passwdClear();
		mIvLoginPwdDel.setOnClickListener(passwdClear);

		// login submit
		OnClickListener mOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.app_login_btn_submit :
						if(TextUtils.isEmpty(mEditName.getText())){
							TranslateAnimation animation = new TranslateAnimation(0, -20, 0, 0);
							animation.setInterpolator(new OvershootInterpolator());
							animation.setDuration(100);
							animation.setRepeatCount(3);
							animation.setRepeatMode(Animation.REVERSE);
							userName.startAnimation(animation);
							Toast edittoast = Toast.makeText(UiLogin.this,"用户名不能为空",Toast.LENGTH_SHORT);
							edittoast.show();
						}
						if(TextUtils.isEmpty(mEditPass.getText())){
							TranslateAnimation animation = new TranslateAnimation(0, -5, 0, 0);
							animation.setInterpolator(new OvershootInterpolator());
							animation.setDuration(100);
							animation.setRepeatCount(3);
							animation.setRepeatMode(Animation.REVERSE);
							passWord.startAnimation(animation);
							Toast passtoast = Toast.makeText(UiLogin.this,"密码不能为空",Toast.LENGTH_SHORT);
							passtoast.show();
						}
							doTaskLogin();
						break;
				}
			}
		};
		findViewById(R.id.app_login_btn_submit).setOnClickListener(mOnClickListener);
	}

	
	private void doTaskLogin() {
		app.setLong(System.currentTimeMillis());
		if (mEditName.length() > 0 && mEditPass.length() > 0) {
			HashMap<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("name", mEditName.getText().toString());
			urlParams.put("pass", mEditPass.getText().toString());
			try {
				this.doTaskAsync(C.task.login, C.api.login, urlParams);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
			case C.task.login:
				Customer customer = null;
				// login logic
				try {
					customer = (Customer) message.getResult("Customer");
					// login success
					if (customer.getName() != null) {
						BaseAuth.setCustomer(customer);
						BaseAuth.setLogin(true);
					// login fail
					} else {
						BaseAuth.setCustomer(customer); // set sid
						BaseAuth.setLogin(false);
						toast(this.getString(R.string.msg_loginfail));
					}
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				// login complete
				long startTime = app.getLong();
				long loginTime = System.currentTimeMillis() - startTime;
				Log.w("LoginTime", Long.toString(loginTime));
				// turn to index
				if (BaseAuth.isLogin()) {
					// start service
					BaseService.start(this, NoticeService.class);
					// turn to index
					forward(UiIndex.class);
				}
				break;
		}
	}

	class registerListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent registerIntent=new Intent();
			registerIntent.setClass(UiLogin.this, registerActivity.class);
			startActivity(registerIntent);
		}
	}

	class userNameClear implements OnClickListener{

		@Override
		public void onClick(View v) {
			mEditName.setText("");
		}
	}

	class passwdClear implements OnClickListener{

		@Override
		public void onClick(View v) {
			mEditPass.setText("");
		}
	}





	
	@Override
	public void onNetworkError (int taskId) {
		super.onNetworkError(taskId);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// other methods
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doFinish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}