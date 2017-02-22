package com.wenzhiguo.newstitlewenzhiguo.fragment.title;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null){
                vh=new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.video_item, null);
                vh.jc = (JCVideoPlayerStandard) convertView.findViewById(R.id.jc_Video);
                vh.imageView = (ImageView) convertView.findViewById(R.id.image_Video);
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
            return convertView;
        }
        class ViewHolder{
            JCVideoPlayerStandard jc;
            ImageView imageView;
            TextView name,count;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //super.onHiddenChanged(hidden);
        Log.e("myMessage","hidden "+hidden);
        if (hidden) {
            JCMediaManager.instance().mediaPlayer.pause();
        }
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



}
