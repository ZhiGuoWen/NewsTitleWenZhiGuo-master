package com.wenzhiguo.newstitlewenzhiguo.fragment.title;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wenzhiguo.newstitlewenzhiguo.Bean.NewsSummary;
import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.WebViewActivity;
import com.wenzhiguo.newstitlewenzhiguo.adapter.MyAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HomeTitleFrament extends Fragment implements PullToRefreshBase.OnRefreshListener2 {

    private MyAdapter adapter;
    private View view;
    private String mId;
    private PullToRefreshListView mPullRefreshListView;
    int index = 0;
    List<NewsSummary> al;

    public HomeTitleFrament() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_title, container, false);
        //拿到传值
        mId = getArguments().get("titles").toString();
        //找控件
        initView();
        initData();
        //请求数据
        setHttp(index);
        return view;
    }

    private void initData() {
        adapter = new MyAdapter(getActivity(), al);
        mPullRefreshListView.setAdapter(adapter);
        mPullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", al.get(position - 1).url_3w);
                startActivity(intent);
            }
        });
    }

    private void setHttp(int a) {
        String url = String.format("http://c.m.163.com/nc/article/headline/%1$s/%2$s-20.html", mId, a);
        RequestParams request = new RequestParams(url);
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map<String, List<NewsSummary>> map = new Gson().fromJson(result, new TypeToken<Map<String, List<NewsSummary>>>() {
                }.getType());
                List<NewsSummary> list = map.get(mId);
                al.addAll(list);
                adapter.notifyDataSetChanged();
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initView() {
        al = new ArrayList<>();
        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.xlistview);
        //设置支持刷新和加载模式
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshListView.setOnRefreshListener(this);
        //时间格式
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  hh-mm-ss");
        String str = format.format(date);
        //设置刷新
        ILoadingLayout startLabels = mPullRefreshListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...\n" + str);// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...\n" + str);// 刷新时
        startLabels.setReleaseLabel("放开刷新...\n" + str);// 下来达到一定距离时，显示的提示
        //设置加载
        ILoadingLayout endLabels = mPullRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...\n" + str);// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...\n" + str);// 刷新时
        endLabels.setReleaseLabel("放开刷新...\n" + str);// 下来达到一定距离时，显示的提示

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        index = 0;
        al.clear();
        setHttp(index);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        index = index + 20;
        setHttp(index);
    }

}
