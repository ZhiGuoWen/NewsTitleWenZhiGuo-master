package com.wenzhiguo.newstitlewenzhiguo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.fragment.Login.Login_DengLu;

import org.json.JSONException;
import org.json.JSONObject;

import static com.wenzhiguo.newstitlewenzhiguo.R.id.Voide_picture_qq;

public class Login_fragment extends Fragment implements View.OnClickListener {

    private String[] name = new String[]{"消息通知", "活动", "头条商城", "京东特供  新人领188元红包", "我要爆料", "用户反馈"};
    private View view;
    private ListView lv;
    private ImageView arrow;
    private ImageView phone;
    private ImageView weixin;
    private ImageView qq;
    private TextView m_text;
    private LinearLayout night;
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        //控件
        initView();

        return view;
    }

    private void initView() {
        //控件list
        lv = (ListView) view.findViewById(R.id.Login_lv);
        lv.setAdapter(new MyAdapter());
        //图片控件
        arrow = (ImageView) view.findViewById(R.id.Voide_picture_jiantou);
        phone = (ImageView) view.findViewById(R.id.Voide_picture_shouji);
        weixin = (ImageView) view.findViewById(R.id.Voide_picture_wexin);
        qq = (ImageView) view.findViewById(R.id.Voide_picture_qq);
        m_text = (TextView) view.findViewById(R.id.m_text);
        night = (LinearLayout) view.findViewById(R.id.night_bai);

        //监听
        arrow.setOnClickListener(this);
        phone.setOnClickListener(this);
        weixin.setOnClickListener(this);
        qq.setOnClickListener(this);

        night.setOnClickListener(this);

        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,getActivity().getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.Voide_picture_jiantou:
                Jump(new Login_DengLu());
                break;
            case R.id.Voide_picture_shouji:
                Jump(new Login_DengLu());
                break;
            case R.id.Voide_picture_wexin:

                break;
            case Voide_picture_qq:
                /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
                官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
                第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(getActivity(),"all", mIUiListener);
                break;
            case R.id.night_bai:

                break;
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getActivity().getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());


                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(), "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    //在调用Login的Activity或者Fragment中重写onActivityResult方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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
