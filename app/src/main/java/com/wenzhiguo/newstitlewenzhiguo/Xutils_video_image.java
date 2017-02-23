package com.wenzhiguo.newstitlewenzhiguo;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by ASUS on 2017/1/10.
 */

public class Xutils_video_image {
    public static void display(ImageView image, String url) {
        ImageOptions options = new ImageOptions.Builder()
                .setSize((DensityUtil.dip2px(120)), DensityUtil.dip2px(120))//设置宽高
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)//设置缩放模式
                .setUseMemCache(true)//支持缓存
                .setCircular(true)
                .setConfig(Bitmap.Config.RGB_565)
                .setLoadingDrawableId(R.drawable.picture_tipic_details)//设置加载中的图片
                .setFailureDrawableId(R.drawable.not_found_loading)//设置加载失败后的图片
                .setIgnoreGif(false) //忽略Gif图片
                .build();
        x.image().bind(image, url, options);
    }
}