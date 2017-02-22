package com.wenzhiguo.newstitlewenzhiguo;

import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.wenzhiguo.newstitlewenzhiguo.utils.Utils;

import org.xutils.x;

/**
 * Created by dell on 2017/2/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        Utils.init(this);
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("aaa", "onSuccess: "+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d("aaa", "onFailure: "+s+"--------------"+s1);
            }
        });
    }
}
