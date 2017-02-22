package com.wenzhiguo.newstitlewenzhiguo.fragment.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wenzhiguo.newstitlewenzhiguo.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@ContentView(R.layout.activity_login__deng_lu)
public class Login_DengLu extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout line;
    private EditText verification;
    private EditText phone;
    private TextView send;
    private Button enter;
    private TextView account_password;
    private TextView prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //setContentView(R.layout.activity_login__deng_lu);
        initView();
    }

    private void initView() {
        //大控件
        line = (LinearLayout) findViewById(R.id.activity_login__deng_lu);
        //动画效果
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.start_pingyi);
        line.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                line.clearAnimation();
            }
        });
        //图片关闭控件
        ImageView close = (ImageView) findViewById(R.id.dialog_close);
        close.setOnClickListener(this);
        //输入框
        verification = (EditText) findViewById(R.id.verification);
        phone = (EditText) findViewById(R.id.phone);
        send = (TextView) findViewById(R.id.send);
        send.setOnClickListener(this);
        //进入头条
        enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(this);
        //账号密码登录
        account_password = (TextView) findViewById(R.id.account_password);
        account_password.setOnClickListener(this);
        //提示
        prompt = (TextView) findViewById(R.id.prompt);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.dialog_close:
                Animation anim1 = AnimationUtils.loadAnimation(Login_DengLu.this, R.anim.end_pingyi);
                line.startAnimation(anim1);
                finish();
                break;
            case R.id.send:
                //得到输入框的值
                String str = verification.getText().toString().trim();
                if (!isMobileNO(str)) {
                    Toast.makeText(this, "输入正确", Toast.LENGTH_SHORT).show();
                } else {
                    prompt.setText("手机号输入有误");
                }
                break;
            case R.id.enter:
                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.account_password:
                Intent intent = new Intent(this, Account_Password_DengLu.class);
                startActivity(intent);
                break;
        }
    }

    //判断手机号
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }
}
