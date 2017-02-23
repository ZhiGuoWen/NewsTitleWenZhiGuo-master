package com.wenzhiguo.newstitlewenzhiguo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        //接收图片路径
        Intent intent = getIntent();
        String imageurl = intent.getStringExtra("imageurl");
        //找控件
        image = (ImageView) findViewById(R.id.Activity_image);

    }
}
