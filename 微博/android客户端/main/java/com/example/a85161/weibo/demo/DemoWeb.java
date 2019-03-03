package com.example.a85161.weibo.demo;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.example.a85161.weibo.R;
import com.example.a85161.weibo.base.BaseUiWeb;
import com.example.a85161.weibo.base.C;
import com.example.a85161.weibo.ui.UiIndex;

public class DemoWeb extends BaseUiWeb {
	
	private WebView mWebView;
	private ImageButton webBackBtn;
	
	@SuppressLint("JavascriptInterface")
	@Override
	public void onStart() {
		super.onStart();
		
		// start loading webview
		setContentView(R.layout.demo_web);
		mWebView = (WebView) findViewById(R.id.web_form);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(C.web.index);

		webBackBtn=(ImageButton)findViewById(R.id.web_back);
		webBackListener webBackListener=new webBackListener();
		webBackBtn.setOnClickListener(webBackListener);
		
		// add js interface
		mWebView.addJavascriptInterface(new DemoJs(), "demo");
		
		this.setWebView(mWebView);
		this.startWebView();
	}

	class webBackListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			forward(UiIndex.class);
		}
	}
	
	protected class DemoJs {
		public void testCallBack(String testParam) {
			Log.w("DemoJs", testParam);
		}
	}
}