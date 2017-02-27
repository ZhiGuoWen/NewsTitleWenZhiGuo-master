package com.wenzhiguo.newstitlewenzhiguo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wenzhiguo.newstitlewenzhiguo.EvenBus;
import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.Xutils_video_image;
import com.wenzhiguo.newstitlewenzhiguo.activity.SettingActivity;
import com.wenzhiguo.newstitlewenzhiguo.fragment.Login.Login_DengLu;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

import static com.wenzhiguo.newstitlewenzhiguo.R.id.Voide_picture_qq;
import static com.wenzhiguo.newstitlewenzhiguo.R.id.Voide_picture_shouji;

public class Login_fragment extends Fragment implements View.OnClickListener {
    private IUiListener userInfoListener;
    private String[] name = new String[]{"消息通知", "活动", "头条商城", "京东特供  新人领188元红包", "我要爆料", "用户反馈"};
    private View view;
    private ListView lv;
    private ImageView arrow;
    private ImageView phone;
    private ImageView weixin;
    private ImageView qq;
    private TextView m_text;
    private LinearLayout night;
    private LinearLayout setting;
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1105602574";//官方获取的APPID
    //获取权限列表。所有权限为 all
    private static String SCOPE = "all";
    private Tencent mTencent;
    private IUiListener loginListener;
    private UserInfo mUserInfo;
    private TextView night_text;
    private ImageView night_image;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        //控件
        initView();
        //初始化QQ登录
        initQqLogin();
        return view;
    }

    private void initView() {
        //控件list
        lv = (ListView) view.findViewById(R.id.Login_lv);
        lv.setAdapter(new MyAdapter());
        //图片控件
        arrow = (ImageView) view.findViewById(R.id.Voide_picture_jiantou);
        phone = (ImageView) view.findViewById(Voide_picture_shouji);
        weixin = (ImageView) view.findViewById(R.id.Voide_picture_wexin);
        qq = (ImageView) view.findViewById(R.id.Voide_picture_qq);
        m_text = (TextView) view.findViewById(R.id.m_text);
        //夜间
        night = (LinearLayout) view.findViewById(R.id.night_bai);
        setting = (LinearLayout) view.findViewById(R.id.line_setting);
        night_image = (ImageView) view.findViewById(R.id.night_image);
        night_text = (TextView) view.findViewById(R.id.night_text);
        m_text = (TextView) view.findViewById(R.id.m_text);

        //监听
        setting.setOnClickListener(this);
        arrow.setOnClickListener(this);
        phone.setOnClickListener(this);
        weixin.setOnClickListener(this);
        qq.setOnClickListener(this);
        night.setOnClickListener(this);
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID, getActivity().getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.Voide_picture_jiantou:
                Jump(new Login_DengLu());
                break;
            case Voide_picture_shouji:
                Jump(new Login_DengLu());
                break;
            case R.id.Voide_picture_wexin:

                break;
            case Voide_picture_qq:
                mTencent.login(this, SCOPE, loginListener);
                break;
            case R.id.night_bai:
                //给activity传值
                EventBus.getDefault().post(new EvenBus(night,night_image,night_text));
                break;
            case R.id.line_setting:
                Jump(new SettingActivity());
                break;
        }
    }

    //初始化QQ登录分享的需要的资源
    private void initQqLogin() {
        try {
            mTencent = Tencent.createInstance(APP_ID, getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //创建QQ登录回调接口
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {

                //登录成功后回调该方法
                Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                SharedPreferences spf = getActivity().getSharedPreferences("myheading", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = spf.edit();
                edit.putBoolean("islogin", true);
                edit.commit();

                //设置openid，如果不设置这一步的话无法获取用户信息
                JSONObject jo = (JSONObject) o;
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
                Toast.makeText(getActivity(), "取消登录", Toast.LENGTH_SHORT).show();
            }
        };
        //获取用户信息的回调接口
        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject jo = (JSONObject) o;
                try {
                    JSONObject info = (JSONObject) o;
                    //获取用户具体信息
                    String nickName = info.getString("nickname");
                    String figureurl_qq_2 = info.getString("figureurl_qq_2");
                    //隐藏图片  gone与invisible的区别gone只是隐藏，invisible隐藏而且占位位置
                    qq.setVisibility(View.GONE);
                    arrow.setVisibility(View.GONE);
                    phone.setVisibility(View.INVISIBLE);
                    //phone.setVisibility(View.GONE);
                    //QQ回调信息赋值
                    Xutils_video_image.display(weixin, figureurl_qq_2);
                    m_text.setText(nickName);

                    SharedPreferences sp = getActivity().getSharedPreferences("myheading", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit1 = sp.edit();
                    edit1.putString("qqname", nickName);
                    edit1.putString("qqimg", figureurl_qq_2);
                    edit1.commit();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
    }

    //获取用户信息
    private void getUserInfo() {
        UserInfo info = new UserInfo(getActivity(), mTencent.getQQToken());
        info.getUserInfo(userInfoListener);
    }

    //在调用Login的Activity或者Fragment中重写onActivityResult方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);*/
        Tencent.handleResultData(data, loginListener);
        //获取用户信息
        getUserInfo();
    }
    /**
     * 跳转
     */
    private void Jump(Activity a) {
        Intent intent = new Intent(getActivity(), a.getClass());
        startActivity(intent);
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.login_listview, null);
                holder.login_name = (TextView) convertView.findViewById(R.id.login_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.login_name.setText(name[position]);
            return convertView;
        }

        class ViewHolder {
            TextView login_name;
        }
    }
}
