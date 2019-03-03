package com.example.a85161.weibo.ui;

import com.example.a85161.weibo.R;
import com.example.a85161.weibo.base.BaseMessage;
import com.example.a85161.weibo.base.BaseUiAuth;

import android.os.Bundle;
import android.view.KeyEvent;

public class Ui_ extends BaseUiAuth {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_index);
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);	
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
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}