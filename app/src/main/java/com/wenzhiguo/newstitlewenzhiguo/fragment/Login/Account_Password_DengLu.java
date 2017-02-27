package com.wenzhiguo.newstitlewenzhiguo.fragment.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wenzhiguo.newstitlewenzhiguo.R;

public class Account_Password_DengLu extends AppCompatActivity implements View.OnClickListener {
    private TextView yanzheng;
    private TextView found;
    private Button jinru;
    private ImageView houtui;
    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_password_denglu);
        initView();
    }

    private void initView() {
        yanzheng = (TextView) findViewById(R.id.text_yanzheng);
        found = (TextView) findViewById(R.id.found_password);
        jinru = (Button) findViewById(R.id.button_jinru);
        houtui = (ImageView) findViewById(R.id.houtui);
        close = (ImageView) findViewById(R.id.image_close);

        yanzheng.setOnClickListener(this);
        found.setOnClickListener(this);
        jinru.setOnClickListener(this);
        houtui.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.text_yanzheng:
                finish();
                break;
            case R.id.button_jinru:
                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.houtui:
                finish();
                break;
            case R.id.image_close:
                finish();
                break;
            case R.id.found_password:
                Intent intent = new Intent(this, Found_password.class);
                startActivity(intent);
                break;
        }
    }
}
