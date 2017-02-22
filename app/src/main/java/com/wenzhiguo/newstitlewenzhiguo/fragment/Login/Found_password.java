package com.wenzhiguo.newstitlewenzhiguo.fragment.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wenzhiguo.newstitlewenzhiguo.R;

public class Found_password extends AppCompatActivity implements View.OnClickListener {

    private ImageView houtui;
    private ImageView close;
    private Button xiayi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_password);
        initView();
    }

    private void initView() {
        houtui = (ImageView) findViewById(R.id.houtui1);
        close = (ImageView) findViewById(R.id.image1_close);
        xiayi = (Button) findViewById(R.id.xiayibu);
        houtui.setOnClickListener(this);
        close.setOnClickListener(this);
        xiayi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.houtui1:
                finish();
                break;
            case R.id.image1_close:
                finish();
                break;
            case R.id.xiayibu:
                Toast.makeText(this, "判断手机号", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
