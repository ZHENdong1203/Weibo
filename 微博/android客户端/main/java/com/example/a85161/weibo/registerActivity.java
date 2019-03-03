package com.example.a85161.weibo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a85161.weibo.model.Image;

import java.nio.InvalidMarkException;
import java.util.HashMap;

public class registerActivity extends Activity{

    private ImageView reNameClear;
    private ImageView rePassClear;
    private ImageView reSignClear;
    private ImageView reFaceClear;
    private Button reBack;
    private EditText reName;
    private EditText rePass;
    private EditText reSign;
    private EditText reFace;
    private Button registerSubmit;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        reName=(EditText)findViewById(R.id.app_register_edit_name);
        rePass=(EditText)findViewById(R.id.app_register_edit_pass);
        reSign=(EditText)findViewById(R.id.app_register_sign_pass);
        reFace=(EditText)findViewById(R.id.app_register_face_pass);


        reBack=(Button)findViewById(R.id.register_back_btn);
        reBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reNameClear=(ImageView)findViewById(R.id.iv_register_username_del);
        reNameClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reName.setText("");
            }
        });

        rePassClear=(ImageView)findViewById(R.id.iv_register_pwd_del);
        rePassClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rePass.setText("");
            }
        });

        reSignClear=(ImageView)findViewById(R.id.iv_register_sign_del);
        reSignClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSign.setText("");
            }
        });

        reFaceClear=(ImageView)findViewById(R.id.iv_register_face_del);
        reFaceClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reFace.setText("");
            }
        });

        registerSubmit=(Button)findViewById(R.id.app_register_btn_submit);
        registerSubmitListener registerSubmitListener=new registerSubmitListener();
        registerSubmit.setOnClickListener(registerSubmitListener);


    }

    class registerSubmitListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            hideKeyBoard();
            final String name=reName.getText().toString();
            final String password=rePass.getText().toString();
            final String sign=reSign.getText().toString();
            final String face=reFace.getText().toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    HashMap<String, String> parameter = new HashMap<>();
                    parameter.put("name", name);
                    parameter.put("pass",password);
                    parameter.put("sign",sign);
                    parameter.put("face",face);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = RegisterHttpUtil.getResult("http://10.0.2.2:8001/", parameter, "/customer/customerCreate?sid=23rb4ql35duhvtdibsn7q8m7op3iatpv");
                    Toast.makeText(registerActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }).start();
        }
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                InputMethodManager.HIDE_NOT_ALWAYS);


    }






    }
