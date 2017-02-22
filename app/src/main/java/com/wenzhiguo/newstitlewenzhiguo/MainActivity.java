package com.wenzhiguo.newstitlewenzhiguo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wenzhiguo.newstitlewenzhiguo.fragment.Care_fragment;
import com.wenzhiguo.newstitlewenzhiguo.fragment.Home_fragment;
import com.wenzhiguo.newstitlewenzhiguo.fragment.Login_fragment;
import com.wenzhiguo.newstitlewenzhiguo.fragment.Video_fragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //LinearLayout
    private FrameLayout framelayout;
    private LinearLayout home;
    private LinearLayout video;
    private LinearLayout care;
    private LinearLayout login;
    //ImageView
    private ImageView home_image;
    private ImageView video_image;
    private ImageView care_image;
    private ImageView login_image;
    //TextView
    private TextView home_text;
    private TextView video_text;
    private TextView care_text;
    private TextView login_text;
    //数组
    int[] bai = {R.drawable.b_newhome_tabbar, R.drawable.b_newvideo_tabbar, R.drawable.b_newcare_tabbar, R.drawable.b_newnologin_tabbar};
    int[] hong = {R.drawable.b_newhome_tabbar_press, R.drawable.b_newvideo_tabbar_press, R.drawable.b_newcare_tabbar_press, R.drawable.b_newnologin_tabbar_press};
    ImageView[] imageview;
    TextView[] textview;

    //Fragment
    Home_fragment homeFragment;
    Care_fragment careFragment;
    Login_fragment loginFragment;
    Video_fragment videoFragment;
    Fragment fragment;
    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        initView();
        //加载视图
        if (homeFragment == null) {
            homeFragment = new Home_fragment();
        }
        setFragment(homeFragment);
    }

    private void initView() {
        framelayout = (FrameLayout) findViewById(R.id.framelayout);
        //LinearLayout
        home = (LinearLayout) findViewById(R.id.home_line);
        video = (LinearLayout) findViewById(R.id.video_line);
        care = (LinearLayout) findViewById(R.id.care_line);
        login = (LinearLayout) findViewById(R.id.login_line);
        //ImageView
        home_image = (ImageView) findViewById(R.id.home_image);
        video_image = (ImageView) findViewById(R.id.video_image);
        care_image = (ImageView) findViewById(R.id.care_image);
        login_image = (ImageView) findViewById(R.id.login_image);
        //TextView
        home_text = (TextView) findViewById(R.id.home_text);
        video_text = (TextView) findViewById(R.id.video_text);
        care_text = (TextView) findViewById(R.id.care_text);
        login_text = (TextView) findViewById(R.id.login_text);
        //监听
        home.setOnClickListener(this);
        video.setOnClickListener(this);
        care.setOnClickListener(this);
        login.setOnClickListener(this);
        //数组
        imageview = new ImageView[]{home_image, video_image, care_image, login_image};
        textview = new TextView[]{home_text, video_text, care_text, login_text};
        //侧滑
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidingment_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_line:
                setBackgroup(0);
                if (homeFragment == null) {
                    homeFragment = new Home_fragment();
                }
                setFragment(homeFragment);
                break;
            case R.id.video_line:
                setBackgroup(1);
                if (videoFragment == null) {
                    videoFragment = new Video_fragment();
                }
                setFragment(videoFragment);
                break;
            case R.id.care_line:
                setBackgroup(2);
                if (careFragment == null) {
                    careFragment = new Care_fragment();
                }
                setFragment(careFragment);
                break;
            case R.id.login_line:
                setBackgroup(3);
                if (loginFragment == null) {
                    loginFragment = new Login_fragment();
                }
                setFragment(loginFragment);
                break;
        }
    }

    private void setBackgroup(int a) {
        for (int i = 0; i < bai.length; i++) {
            if (a == i) {
                imageview[a].setImageResource(hong[a]);
                textview[a].setTextColor(Color.RED);
            } else {
                imageview[i].setImageResource(bai[i]);
                textview[i].setTextColor(Color.BLACK);
            }
        }
    }

    private void setFragment(Fragment f) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            transaction.hide(fragment);
        }
        if (!f.isAdded()) {
            transaction.add(R.id.framelayout, f);
        }
        transaction.show(f);
        transaction.commit();
        fragment = f;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}