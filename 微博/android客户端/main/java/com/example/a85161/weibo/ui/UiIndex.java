package com.example.a85161.weibo.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.a85161.weibo.R;
import com.example.a85161.weibo.WeatherBean;
import com.example.a85161.weibo.WeatherDetail;
import com.example.a85161.weibo.base.BaseHandler;
import com.example.a85161.weibo.base.BaseMessage;
import com.example.a85161.weibo.base.BaseTask;
import com.example.a85161.weibo.base.BaseUi;
import com.example.a85161.weibo.base.BaseUiAuth;
import com.example.a85161.weibo.base.C;
import com.example.a85161.weibo.list.BlogList;
import com.example.a85161.weibo.model.Blog;
import com.example.a85161.weibo.sqlite.BlogSqlite;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

public class UiIndex extends BaseUiAuth {

	RequestQueue queue = null;
	private RelativeLayout weatherTop;
	private TextView weatherTemp;
	private TextView weatherWea;
	private ImageButton mainMenu;
	private ListView blogListView;
	private BlogList blogListAdapter;
	private BlogSqlite blogSqlite;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_index);

		mainMenu=(ImageButton)findViewById(R.id.menu_btn);
		mainMenuListener mainMenuListener=new mainMenuListener();
		mainMenu.setOnClickListener(mainMenuListener);
		
		// set handler
		this.setHandler(new IndexHandler(this));
		
		// tab button
		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_1);
		ib.setImageResource(R.drawable.tab_blog_2);
		
		// init sqlite
		blogSqlite = new BlogSqlite(this);

		weatherTop=(RelativeLayout)findViewById(R.id.weather_top_layout);
		weatherTopListener weatherTopListener=new weatherTopListener();
		weatherTop.setOnClickListener(weatherTopListener);

		queue = Volley.newRequestQueue(this);
		weatherTemp=(TextView)findViewById(R.id.weather_temp_count);
		weatherWea=(TextView)findViewById(R.id.weather_wea_count);
		String url = "https://free-api.heweather.com/s6/weather/now?location=changsha&key=5735efbb14f84bf48392a90d1f8be9e7";
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jsonObject) {

				System.out.println(jsonObject);
				Gson gson = new Gson();
				WeatherBean weatherBean = gson.fromJson(jsonObject.toString(), WeatherBean.class);
				String temp = weatherBean.getHeWeather6().get(0).getNow().getTmp();
				String cond = weatherBean.getHeWeather6().get(0).getNow().getCond_txt();
				weatherTemp.setText(temp);
				weatherWea.setText(cond);

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				System.out.println(volleyError);
			}
		});
		queue.add(request);
	}

	class mainMenuListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			openOptionsMenu();
		}
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		// show all blog list
		HashMap<String, String> blogParams = new HashMap<String, String>();
		blogParams.put("typeId", "0");
		blogParams.put("pageId", "0");
		this.doTaskAsync(C.task.blogList, C.api.blogList, blogParams);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);

		switch (taskId) {
			case C.task.blogList:
				try {
					@SuppressWarnings("unchecked")
					final ArrayList<Blog> blogList = (ArrayList<Blog>) message.getResultList("Blog");
					// load face image
					for (Blog blog : blogList) {
						loadImage(blog.getFace());
						blogSqlite.updateBlog(blog);
					}
					// show text
					blogListView = (ListView) this.findViewById(R.id.app_index_list_view);
					blogListAdapter = new BlogList(this, blogList);
					blogListView.setAdapter(blogListAdapter);
					blogListView.setOnItemClickListener(new OnItemClickListener(){
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
							Bundle params = new Bundle();
							params.putString("blogId", blogList.get(pos).getId());
							overlay(UiBlog.class, params);
						}
					});
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
		toast(C.err.network);
		switch (taskId) {
			case C.task.blogList:
				try {
					final ArrayList<Blog> blogList = blogSqlite.getAllBlogs();
					// load face image
					for (Blog blog : blogList) {
						loadImage(blog.getFace());
						blogSqlite.updateBlog(blog);
					}
					// show text
					blogListView = (ListView) this.findViewById(R.id.app_index_list_view);
					blogListAdapter = new BlogList(this, blogList);
					blogListView.setAdapter(blogListAdapter);
					blogListView.setOnItemClickListener(new OnItemClickListener(){
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
							Bundle params = new Bundle();
							params.putString("blogId", blogList.get(pos).getId());
							overlay(UiBlog.class, params);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				break;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// other methods
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			AlertDialog.Builder isExit=new AlertDialog.Builder(this);
			isExit.setTitle("提醒");
			isExit.setMessage("确定要退出吗？");
			isExit.setPositiveButton("确定",diaListener);
			isExit.setNegativeButton("取消",diaListener);
			isExit.show();

		}
		return super.onKeyDown(keyCode, event);
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

	class weatherTopListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent weatherIntent = new Intent();
			weatherIntent.setClass(UiIndex.this,WeatherDetail.class);
			startActivity(weatherIntent);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// inner classes
	
	private class IndexHandler extends BaseHandler {
		public IndexHandler(BaseUi ui) {
			super(ui);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				switch (msg.what) {
					case BaseTask.LOAD_IMAGE:
						blogListAdapter.notifyDataSetChanged();
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ui.toast(e.getMessage());
			}
		}
	}
}