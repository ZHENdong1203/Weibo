package com.example.a85161.weibo.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.a85161.weibo.R;
import com.example.a85161.weibo.base.BaseHandler;
import com.example.a85161.weibo.base.BaseMessage;
import com.example.a85161.weibo.base.BaseTask;
import com.example.a85161.weibo.base.BaseUi;
import com.example.a85161.weibo.base.BaseUiAuth;
import com.example.a85161.weibo.base.C;
import com.example.a85161.weibo.codeActivity;
import com.example.a85161.weibo.list.SimpleList;
import com.example.a85161.weibo.model.Config;
import com.example.a85161.weibo.model.Customer;
import com.example.a85161.weibo.util.AppCache;
import com.example.a85161.weibo.util.AppUtil;
import com.example.a85161.weibo.util.UIUtil;
import com.example.a85161.weibo.view.wave.WaveView;
import com.example.a85161.weibo.aboutActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UiConfig extends BaseUiAuth {
	
	private ListView listConfig;
	private ImageView faceImage;
	private String faceImageUrl;
	private ImageView imageView;
	private WaveView waveView;
	private TextView about;
	private TextView QRcode;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_config);
		
		// set handler
		this.setHandler(new ConfigHandler(this));
		
		// tab button
		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_3);
		ib.setImageResource(R.drawable.tab_conf_2);
		
		// init views
		listConfig = (ListView) findViewById(R.id.app_config_list_main);

		about=(TextView)findViewById(R.id.About);
		about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(UiConfig.this,aboutActivity.class);
				startActivity(intent);
			}
		});

		QRcode=(TextView)findViewById(R.id.QRcode);
		QRcode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent codeIntent=new Intent();
				codeIntent.setClass(UiConfig.this,codeActivity.class);
				startActivity(codeIntent);
			}
		});



		imageView = (ImageView) findViewById(R.id.tpl_list_info_image_face);
		waveView = (WaveView) findViewById(R.id.wave_view);

		final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2,-2);
		lp.gravity = Gravity.BOTTOM|Gravity.CENTER;
		waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
			@Override
			public void OnWaveAnimation(float y) {
				lp.setMargins(0,0,0,(int)y+2);
				imageView.setLayoutParams(lp);
			}
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// config list
		final ArrayList<Config> dataList = new ArrayList<Config>();
		dataList.add(new Config(getResources().getString(R.string.config_face), customer.getFace()));
		dataList.add(new Config(getResources().getString(R.string.config_sign), customer.getSign()));
		String[] from = {Config.COL_NAME};
		int[] to = {R.id.tpl_list_menu_text_name};
		listConfig.setAdapter(new SimpleList(this, AppUtil.dataToList(dataList, from), R.layout.tpl_list_menu, from, to));
		listConfig.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				// change face
				if (pos == 0) {
					overlay(UiSetFace.class);
				// edit customer info
				} else {
					Bundle data = new Bundle();
					data.putInt("action", C.action.edittext.CONFIG);
					data.putString("value", dataList.get(pos).getValue());
					doEditText(data);
				}
			}
		});
		
		// prepare customer data
		HashMap<String, String> cvParams = new HashMap<String, String>();
		cvParams.put("customerId", customer.getId());
		this.doTaskAsync(C.task.customerView, C.api.customerView, cvParams);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
			case C.task.customerView:
				try {
					final Customer customer = (Customer) message.getResult("Customer");
					TextView textTop = (TextView) this.findViewById(R.id.tpl_list_info_text_top);
					TextView textBottom = (TextView) this.findViewById(R.id.tpl_list_info_text_bottom);
					textTop.setText(customer.getSign());
					textBottom.setText(UIUtil.getCustomerInfo(this, customer));
					// load face image async
					faceImage = (ImageView) this.findViewById(R.id.tpl_list_info_image_face);
					faceImageUrl = customer.getFaceurl();
					loadImage(faceImageUrl);
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				break;
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
			this.forward(UiIndex.class);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// inner classes
	
	private class ConfigHandler extends BaseHandler {
		public ConfigHandler(BaseUi ui) {
			super(ui);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				switch (msg.what) {
					case BaseTask.LOAD_IMAGE:
						Bitmap face = AppCache.getImage(faceImageUrl);
						faceImage.setImageBitmap(face);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ui.toast(e.getMessage());
			}
		}
	}
}