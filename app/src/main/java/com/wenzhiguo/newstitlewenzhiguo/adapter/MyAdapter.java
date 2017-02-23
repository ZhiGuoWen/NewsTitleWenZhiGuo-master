package com.wenzhiguo.newstitlewenzhiguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wenzhiguo.newstitlewenzhiguo.Bean.NewsSummary;
import com.wenzhiguo.newstitlewenzhiguo.ImageViewActivity;
import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.utils.ConvertUtils;
import com.wenzhiguo.newstitlewenzhiguo.utils.FullGridView;
import com.wenzhiguo.newstitlewenzhiguo.utils.ScreenUtils;

import java.util.List;

/**
 * Created by dell on 2017/2/16.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<NewsSummary> list;
    DisplayImageOptions options;
    LayoutInflater inflater;
    ImageView popup;
    private PopupWindow mCurPopupWindow;

    public MyAdapter(Context context, List<NewsSummary> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NewsSummary getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int width = ScreenUtils.getScreenWidth();
        final int padding = ConvertUtils.dp2px(32);
        int spacing = ConvertUtils.dp2px(2);

        NewsSummary summary = getItem(position);

        holder.digest.setText(summary.digest);
        holder.ltitle.setText(summary.title);
        holder.source.setText(summary.source);
        holder.ptime.setText(summary.ptime);

        if (summary.getImgextra() == null) {
            ViewGroup.LayoutParams params = holder.image.getLayoutParams();
            params.width = params.height = width / 4;
            holder.image.setLayoutParams(params);
            ImageLoader.getInstance().displayImage(summary.imgsrc, holder.image, options);
            holder.image.setVisibility(View.VISIBLE);
        } else {
            holder.image.setVisibility(View.GONE);
        }

        int size = summary.getImgextra() == null ? 0 : summary.getImgextra().size();
        //图片的监听事件
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ImageViewActivity.class);
                String imgsrc = getItem(position).getImgextra().get(position).imgsrc;
                intent.putExtra("imageurl",imgsrc);
                //图片放大
                context.startActivity(intent);
                Toast.makeText(context, imgsrc, Toast.LENGTH_SHORT).show();
            }
        });

        if (size > 0) {
            AdAdapter adapter = new AdAdapter(context, summary.getImgextra());
            holder.gridView.setAdapter(adapter);
            holder.gridView.setVisibility(View.VISIBLE);
            if (size == 3) {
                holder.gridView.setNumColumns(3);
            } else if (size == 1) {
                holder.gridView.setNumColumns(1);
            } else if (size == 2) {
                holder.gridView.setNumColumns(2);
            } else if (size == 4) {
                holder.gridView.setNumColumns(2);
            } else {
                holder.gridView.setNumColumns(3);
            }

            if (size == 1) {
                holder.gridView.setColumnWidth(width - padding);
            } else if (size % 2 == 0) {
                holder.gridView.setColumnWidth((width - padding - spacing) / 2);
            } else if (size % 3 == 0) {
                holder.gridView.setColumnWidth((width - padding - spacing * 2) / 3);
            }
        } else {
            holder.gridView.setVisibility(View.GONE);
        }
        //不敢兴趣   将信息移除
        popup = holder.pop;
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurPopupWindow = showTipPopupWindow(v, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.remove(position);
                        MyAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView ltitle;
        TextView digest;
        TextView ptime;
        TextView source;
        //防止图片的Grid
        FullGridView gridView;
        ImageView pop;

        public ViewHolder(View convertView) {
            image = (ImageView) convertView.findViewById(R.id.list_image);
            ltitle = (TextView) convertView.findViewById(R.id.list_title);
            digest = (TextView) convertView.findViewById(R.id.list_content);
            ptime = (TextView) convertView.findViewById(R.id.ptime);
            source = (TextView) convertView.findViewById(R.id.source);
            gridView = (FullGridView) convertView.findViewById(R.id.fgv);
            pop = (ImageView) convertView.findViewById(R.id.pop);
        }
    }

    private void autoAdjustArrowPos(PopupWindow popupWindow, View contentView, View anchorView) {

        View upArrow = contentView.findViewById(R.id.up_arrow);
        View downArrow = contentView.findViewById(R.id.down_arrow);

        //初始化位置数组
        int pos[] = new int[2];//0：x,1:y
        contentView.getLocationOnScreen(pos);//数组初始化，获得位置信息
        Log.d("contentView", "(" + pos[0] + "," + pos[1] + ")");
        int popLeftPos = pos[0];
        Log.d("popLeftPos", "(" + popLeftPos + ")");

        anchorView.getLocationOnScreen(pos);

        int anchorLeftPos = pos[0];
        Log.d("anchorLeftPos", "(" + anchorLeftPos + ")");
        Log.d("anchorView", "(" + pos[0] + "," + pos[1] + ")");
        Log.d("anchorView", "(" + anchorView.getWidth() + "," + upArrow.getWidth() + ")");

        //箭头的X标,可以动态调整
        int arrowLeftMargin = anchorLeftPos - popLeftPos + anchorView.getWidth() / 2 - upArrow.getWidth() / 2;

        Log.d("arrowLeftMargin", "(" + arrowLeftMargin + ")");

        upArrow.setVisibility(popupWindow.isAboveAnchor() ? View.INVISIBLE : View.VISIBLE);
        downArrow.setVisibility(popupWindow.isAboveAnchor() ? View.VISIBLE : View.INVISIBLE);

        RelativeLayout.LayoutParams upArrowParams = (RelativeLayout.LayoutParams) upArrow.getLayoutParams();
        upArrowParams.leftMargin = arrowLeftMargin;
        RelativeLayout.LayoutParams downArrowParams = (RelativeLayout.LayoutParams) downArrow.getLayoutParams();
        downArrowParams.leftMargin = arrowLeftMargin;
    }

    public PopupWindow showTipPopupWindow(final View anchorView, final View.OnClickListener onClickListener) {

        final View contentView = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.pop, null);

        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        // 创建PopupWindow时候指定高宽时showAsDropDown能够自适应
        // 如果设置为wrap_content,showAsDropDown会认为下面空间一直很充足（我以认为这个Google的bug）
        // 备注如果PopupWindow里面有ListView,ScrollView时，一定要动态设置PopupWindow的大小

        final PopupWindow popupWindow = new PopupWindow(contentView,
                contentView.getMeasuredWidth(), contentView.getMeasuredHeight(), false);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                onClickListener.onClick(v);
            }
        });

        //调整箭头位置，看的时候注意一下
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 自动调整箭头的位置
                autoAdjustArrowPos(popupWindow, contentView, anchorView);
                contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        popupWindow.setOutsideTouchable(true);

        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        popupWindow.setTouchable(true);

        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        popupWindow.setFocusable(false);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });

        // 如果希望showAsDropDown方法能够在下面空间不足时自动在anchorView的上面弹出
        // 必须在创建PopupWindow的时候指定高度，不能用wrap_content
        popupWindow.showAsDropDown(anchorView);

        return popupWindow;
    }
}
