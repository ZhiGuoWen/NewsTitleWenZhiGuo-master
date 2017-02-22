package com.wenzhiguo.newstitlewenzhiguo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.fragment.title.VideoTitleFragment;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;

public class Video_fragment extends Fragment {
    private String[] title = {"热点视频", "娱乐视频", "搞笑视频", "精品视频"};
    private String[] mId = {"V9LG4B3A0","V9LG4CHOR","V9LG4E6VR","00850FRB"};
    List<Fragment> list;
    private View view;

    public Video_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);
        //找控件
        initView();
        return view;
    }

    private void initView() {
        TabLayout tab = (TabLayout) view.findViewById(R.id.video_tablayout);
        ViewPager vp = (ViewPager) view.findViewById(R.id.video_viewpager);
        list = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            VideoTitleFragment titleFragment = new VideoTitleFragment();
            Bundle bundle = new Bundle();
            bundle.putString("mId",mId[i]);
            titleFragment.setArguments(bundle);
            list.add(titleFragment);
        }
        //适配器
        vp.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        });
        //第一个参数是未选中颜色，第二参数为选中颜色
        tab.setTabTextColors(Color.BLACK, Color.RED);
        //设置下划线颜色：
        tab.setSelectedTabIndicatorColor(Color.GRAY);
        //设置下划线高度，宽度跟随tab的宽度:
        tab.setSelectedTabIndicatorHeight(5);
        //设置横向模式，默认是纵向
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setupWithViewPager(vp);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            JCMediaManager.instance().mediaPlayer.pause();
        }else {
            JCMediaManager.instance().mediaPlayer.start();
        }
    }
}
