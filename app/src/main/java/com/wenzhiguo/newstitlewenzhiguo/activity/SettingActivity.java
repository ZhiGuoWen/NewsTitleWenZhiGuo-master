package com.wenzhiguo.newstitlewenzhiguo.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.Signal;

public class SettingActivity extends Signal implements View.OnClickListener{

    ImageView ture_back2;
    RelativeLayout qchc_line;
    RelativeLayout end_ok;
    RelativeLayout ztdx_line;
    AlertDialog create;
    TextView huancun;
    TextView quxiao;
    View v2;
    private TextView word_little;
    private TextView word_mid;
    private TextView word_big;
    private TextView word_so_big;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int x = msg.what;
            if (x == 1) {
                //关闭"正在删除"对话框
                create.dismiss();
                //显示'删除成功'布局
                end_ok.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(2, 1500);
            } else if (x == 2) {
                //缓存清零
                huancun.setText(0 + " MB");
                //隐藏布局
                end_ok.setVisibility(View.GONE);
            }

        }
    };
    private TextView quxiao1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //找控件
        initView();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按钮监听
            case R.id.ture_back2:
                ture_back2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                break;
            //清除缓存监听
            case R.id.qchc_line:
                AlertDialog.Builder bu = new AlertDialog.Builder(this);
                bu.setTitle("提示");
                bu.setMessage("确定要删除所有的缓存吗?问答草稿,离线内容及图片均会被清除.");
                //取消按钮
                bu.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                //确定按钮
                bu.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder bu2 = new AlertDialog.Builder(SettingActivity.this);
                        create = bu2.create();
                        create.setTitle("提示");
                        create.setMessage("正在删除缓存");
                        create.show();
                        handler.sendEmptyMessageDelayed(1, 1500);
                    }
                });
                //显示对话框
                bu.show();
                break;
            //字体大小
            case R.id.ztdx_line:
                final AlertDialog.Builder bu3 = new AlertDialog.Builder(SettingActivity.this);
                AlertDialog create = bu3.create();
                create.setTitle("字体大小");
                create.setView(v2);
                create.show();
                break;

        }
    }

    public void initView() {
        //字体大小控件
        v2 = View.inflate(SettingActivity.this, R.layout.ztdx_item, null);
        quxiao = (TextView) v2.findViewById(R.id.quxiao);
        word_little = (TextView) v2.findViewById(R.id.word_little);
        word_mid = (TextView) v2.findViewById(R.id.word_mid);
        word_big = (TextView) v2.findViewById(R.id.word_big);
        word_so_big = (TextView) v2.findViewById(R.id.word_so_big);

        ture_back2 = (ImageView) findViewById(R.id.ture_back2);
        qchc_line = (RelativeLayout) findViewById(R.id.qchc_line);
        end_ok = (RelativeLayout) findViewById(R.id.end_ok);
        ztdx_line = (RelativeLayout) findViewById(R.id.ztdx_line);
        huancun = (TextView) findViewById(R.id.setting_huancun);

        ture_back2.setOnClickListener(this);
        qchc_line.setOnClickListener(this);
        ztdx_line.setOnClickListener(this);
    }
}