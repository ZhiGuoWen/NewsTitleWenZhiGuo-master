package com.wenzhiguo.newstitlewenzhiguo.fragment.title;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wenzhiguo.newstitlewenzhiguo.Bean.VideoBean;
import com.wenzhiguo.newstitlewenzhiguo.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoTitleFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2 {
    private List<VideoBean> videoList;
    int index = 10;
    private View view;
    private String mId;
    private JCVideoPlayerStandard jicao;
    private TextView name;
    private ImageView imageview;
    private TextView count;
    private ImageView savour;
    private PullToRefreshListView xListView;
    private MyVideoAdapter adapter;
    private DisplayImageOptions options;
    private PopupWindow mCurPopupWindow;

    public VideoTitleFragment() {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video_title, container, false);
        mId = getArguments().get("mId").toString();
        //找控件
        initView();
        //解析数据
        setHttp(index);
        return view;
    }

    private void initView() {
        videoList = new ArrayList<>();
        xListView = (PullToRefreshListView) view.findViewById(R.id.video_shiping);
        //设置支持刷新和加载模式
        xListView.setMode(PullToRefreshBase.Mode.BOTH);
        xListView.setOnRefreshListener(this);
        //时间格式
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  hh-mm-ss");
        String str = format.format(date);
        //设置刷新
        ILoadingLayout startLabels = xListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...\n" + str);// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...\n" + str);// 刷新时
        startLabels.setReleaseLabel("放开刷新...\n" + str);// 下来达到一定距离时，显示的提示
        //设置加载
        ILoadingLayout endLabels = xListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...\n" + str);// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...\n" + str);// 刷新时
        endLabels.setReleaseLabel("放开刷新...\n" + str);// 下来达到一定距离时，显示的提示
    }

    private void setHttp(int a) {
        String url = String.format("http://c.3g.163.com/nc/video/list/%1$s/n/%2$s-10.html", mId, a);
        RequestParams request = new RequestParams(url);
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map<String, List<VideoBean>> map = new Gson().fromJson(result, new TypeToken<Map<String, List<VideoBean>>>() {
                }.getType());

                List<VideoBean> list = map.get(mId);
                videoList.addAll(list);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapter = new MyVideoAdapter();
                xListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                xListView.onRefreshComplete();
            }
        });
    }

    class MyVideoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return videoList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null){
                vh=new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.video_item, null);
                vh.jc = (JCVideoPlayerStandard) convertView.findViewById(R.id.jc_Video);
                vh.imageView = (ImageView) convertView.findViewById(R.id.image_Video);
                vh.image = (ImageView) convertView.findViewById(R.id.savour_Video);
                vh.name = (TextView) convertView.findViewById(R.id.name_Video);
                vh.count = (TextView) convertView.findViewById(R.id.count_Video);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder)convertView.getTag();
            }
            //设置视频和标头
            boolean setUp = vh.jc.setUp(videoList.get(position).getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST, videoList.get(position).getTitle());
            if (setUp) {
                Glide.with(getActivity()).load(videoList.get(position).getCover()).into(vh.jc.thumbImageView);
            }
            //图片加载
            ImageLoader.getInstance().displayImage(videoList.get(position).getTopicImg(), vh.imageView, options);
            //名字
            vh.name.setText(videoList.get(position).getTopicName());
            //次数
            vh.count.setText(videoList.get(position).getPlayCount());
            //不感兴趣监听
            vh.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurPopupWindow = showTipPopupWindow(v, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            videoList.remove(position);
                            MyVideoAdapter.this.notifyDataSetChanged();
                        }
                    });
                }
            });
            return convertView;
        }
        class ViewHolder{
            JCVideoPlayerStandard jc;
            ImageView imageView,image;
            TextView name,count;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        index = 0;
        videoList.clear();
        setHttp(index);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        index +=10;
        setHttp(index);
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
