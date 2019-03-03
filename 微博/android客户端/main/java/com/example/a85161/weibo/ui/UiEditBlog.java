package com.example.a85161.weibo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.a85161.weibo.R;
import com.example.a85161.weibo.base.BaseMessage;
import com.example.a85161.weibo.base.BaseUiAuth;
import com.example.a85161.weibo.base.C;
import com.example.a85161.weibo.util.AppUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UiEditBlog extends BaseUiAuth {
	
	private static final int FLAG_CHOOSE_IMG = 1;
	private EditText writeBlog;
	private ImageView app_write_img;
	private String app_write_img_path;
	private Button editTextClear;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_write);
		
		// show keyboard
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

		editTextClear=(Button)findViewById(R.id.write_blog_clear);
		writeBlog=(EditText)findViewById(R.id.app_write_text);
		editTextClearListener editTextClearListener=new editTextClearListener();
		editTextClear.setOnClickListener(editTextClearListener);
		// set image button
		app_write_img = (ImageView) findViewById(R.id.app_write_img);
		app_write_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ActivityCompat.checkSelfPermission(UiEditBlog.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(UiEditBlog.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
				}
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, FLAG_CHOOSE_IMG);
			}
		});
		
		// bind action logic
		findViewById(R.id.app_write_submit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText mWriteText = (EditText) findViewById(R.id.app_write_text);
				HashMap<String, String> urlParams = new HashMap<String, String>();
				urlParams.put("content", mWriteText.getText().toString());
				if (app_write_img_path != null) {
					List<NameValuePair> files = new ArrayList<NameValuePair>();
					files.add(new BasicNameValuePair("file0", app_write_img_path));
					doTaskAsync(C.task.blogCreate, C.api.blogCreate, urlParams, files);
				} else {
					doTaskAsync(C.task.blogCreate, C.api.blogCreate, urlParams);
				}
			}
		});
	}

	class editTextClearListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			writeBlog.setText("");
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((requestCode == FLAG_CHOOSE_IMG) && resultCode == RESULT_OK) {
			if (data != null) {
				Uri uri = data.getData();
				if (uri != null) {
					try {
						// get image path
						String[] filePathColumn = { MediaStore.Images.Media.DATA };
						Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
						cursor.moveToFirst();
						String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
						cursor.close();
						// debug path
						this.toast(path);
						// create bitmap
						Bitmap bm = AppUtil.createBitmap(path, 100, 100);
						if (bm == null) {
							this.toast("can not find img");
						} else {
							app_write_img.setImageBitmap(bm);
							app_write_img_path = path;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		doFinish();
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