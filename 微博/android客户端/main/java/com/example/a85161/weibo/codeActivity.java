package com.example.a85161.weibo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a85161.weibo.base.BaseMessage;
import com.example.a85161.weibo.base.BaseUiAuth;
import com.example.a85161.weibo.base.C;
import com.example.a85161.weibo.model.Customer;
import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.encode.CodeCreator;

public class codeActivity extends Activity implements View.OnClickListener{


    private EditText name;

    private Button encodeBtn;
    private ImageView contentIv;
    private Button back;
    /**
     * 生成带logo的二维码
     */
    private Button encodeBtnWithLogo;
    private ImageView contentIvWithLogo;
    private String contentEtString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_code);

        /*生成按钮*/
        encodeBtn = findViewById(R.id.encodeBtn);
        encodeBtn.setOnClickListener(this);
        /*生成的图片*/
        contentIv = findViewById(R.id.contentIv);
        encodeBtnWithLogo = (Button) findViewById(R.id.encodeBtnWithLogo);
        encodeBtnWithLogo.setOnClickListener(this);
        contentIvWithLogo = (ImageView) findViewById(R.id.contentIvWithLogo);
        encodeBtn = (Button) findViewById(R.id.encodeBtn);
        contentIv = (ImageView) findViewById(R.id.contentIv);
        name=(EditText)findViewById(R.id.code_name);

        back=(Button)findViewById(R.id.code_back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {

        Bitmap bitmap = null;
        switch (v.getId()) {

            case R.id.encodeBtn:
                hideKeyBoard();
                contentEtString = name.getText().toString().trim();
                if (TextUtils.isEmpty(contentEtString)) {
                    Toast.makeText(codeActivity.this, "请输入要生成二维码图片的字符串", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, null);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    contentIv.setImageBitmap(bitmap);
                }

                break;

            case R.id.encodeBtnWithLogo:

                contentEtString = name.getText().toString().trim();
                if (TextUtils.isEmpty(contentEtString)) {
                    Toast.makeText(codeActivity.this, "请输入要生成二维码图片的字符串", Toast.LENGTH_SHORT).show();
                    return;
                }

                bitmap = null;
                try {
                    Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.aboutlogo);
                    bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, logo);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    contentIvWithLogo.setImageBitmap(bitmap);
                }

                break;

            default:
        }
    }


    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);


    }

}
