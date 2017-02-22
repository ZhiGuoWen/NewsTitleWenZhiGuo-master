package com.wenzhiguo.newstitlewenzhiguo.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.television.Television;
import com.wenzhiguo.newstitlewenzhiguo.fragment.title.HomeTitleFrament;

import java.util.ArrayList;
import java.util.List;

public class Home_fragment extends Fragment {

    private String[] titles = new String[]{"精选", "足球", "娱乐", "体育", "财经", "科技", "电影",
            "NBA", "教育", "论坛", "情感", "时尚", "电台", "彩票", "汽车", "游戏", "笑话"};
    String[] id = new String[]{"T1370583240249", "T1399700447917", "T1348648517839",
            "T1348649079062", "T1348648756099", "T1348649580692", "T1348648650048",
            "T1348649145984", "T1348654225495", "T1349837670307", "T1348650839000",
            "T1348650593803", "T1379038288239", "T1356600029035", "T1348654060988",
            "T1348654151579", "T1350383429665"};
    List<Fragment> list;
    private View view;
    private TabLayout tab;
    private ViewPager viewpager;

    public Home_fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        //找控件
        initView();
        return view;
    }

    private void initView() {
        //频道图片监听
        ImageView television  = (ImageView) view.findViewById(R.id.add_image);
        television.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Television.class);
                startActivity(intent);
            }
        });
        tab = (TabLayout) view.findViewById(R.id.tablayout);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        //list添加fragment
        list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            HomeTitleFrament hometitle = new HomeTitleFrament();
            //传值
            Bundle bundle = new Bundle();
            bundle.putString("titles",id[i]);
            hometitle.setArguments(bundle);

            list.add(hometitle);
        }
        //适配器
        viewpager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
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
                return titles[position];
            }
        });
        //不仅加载当前视图，追加后面视图
        viewpager.setOffscreenPageLimit(3);
        //第一个参数是未选中颜色，第二参数为选中颜色
        tab.setTabTextColors(Color.BLACK,Color.RED);
        //设置下划线颜色：
        tab.setSelectedTabIndicatorColor(Color.GRAY);
        //设置下划线高度，宽度跟随tab的宽度:
        tab.setSelectedTabIndicatorHeight(5);
        //设置横向模式，默认是纵向
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setupWithViewPager(viewpager);

    }

}
