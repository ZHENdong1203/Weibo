package com.example.a85161.weibo.test;

import com.example.a85161.weibo.R;
import com.example.a85161.weibo.base.BaseUi;
import com.example.a85161.weibo.ui.UiIndex;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class TestUi extends BaseUi {
	
	final static int testArrayTask = 1;
	final static int testArrayListTask = 2;
	
	private Button btnTestArray = null;
	private Button btnArrayListTask = null;
	private ImageButton testBackBtn;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_test);
		
		btnTestArray = (Button) this.findViewById(R.id.app_test_btn_test_array);
		btnTestArray.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doTaskAsync(testArrayTask, 0);
			}
		});
		
		btnArrayListTask = (Button) this.findViewById(R.id.app_test_btn_test_array_list);
		btnArrayListTask.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doTaskAsync(testArrayListTask, 0);
			}
		});

		testBackBtn=(ImageButton)findViewById(R.id.test_back);
		testBackListener testBackListener=new testBackListener();
		testBackBtn.setOnClickListener(testBackListener);
	}

	class testBackListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			forward(UiIndex.class);
		}
	}
	
	public void onTaskComplete (int taskId) {
		super.onTaskComplete(taskId);
		switch (taskId) {
			case testArrayTask:
				try {
					TestDemo td = (TestDemo) TestProxy.init(new TestDemoImpl());
					td.testArray();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case testArrayListTask:
				try {
					TestDemo td = (TestDemo) TestProxy.init(new TestDemoImpl());
					td.testArrayList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
		}
	}
}