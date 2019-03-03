package com.example.a85161.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import com.example.a85161.weibo.ui.*;

public class frontActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView view = new ImageView(this);
        view.setBackgroundResource(R.drawable.front);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(frontActivity.this,UiLogin.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 2000);
    }
}
