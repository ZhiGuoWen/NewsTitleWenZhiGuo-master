package com.wenzhiguo.newstitlewenzhiguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wenzhiguo.newstitlewenzhiguo.Bean.NewsSummary;
import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.utils.ConvertUtils;
import com.wenzhiguo.newstitlewenzhiguo.utils.ScreenUtils;

import java.util.List;

/**
 * Created by liqingyi on 2017/1/10.
 */

public class AdAdapter extends BaseAdapter {

    List<NewsSummary.AdsBean> list;
    Context activity;
    LayoutInflater inflater;
    private DisplayImageOptions options;

    public AdAdapter(Context activity, List<NewsSummary.AdsBean> list) {
        this.activity = activity;
        this.list = list;
        this.inflater = LayoutInflater.from(activity);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public NewsSummary.AdsBean getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_image, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsSummary.AdsBean bean = getItem(position);
        ImageLoader.getInstance().displayImage(bean.imgsrc, holder.imageView, options);

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.image);
            int width = ScreenUtils.getScreenWidth();
            int padding = ConvertUtils.dp2px(32);
            int spacing = ConvertUtils.dp2px(2);
            int size = getCount();
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            if (size == 1) {
                params.width = width - padding;
                params.height = params.width / 2;
            } else if (size == 2) {
                params.width = (width - padding - spacing) / 2;
                params.height = (width - padding) / 2;
            } else if (size >= 3) {
                params.width = (width - padding - spacing) / 3;
                params.height = params.width;
            }
            imageView.setLayoutParams(params);
        }
    }
}
