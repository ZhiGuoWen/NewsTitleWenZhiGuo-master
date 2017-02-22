package com.wenzhiguo.newstitlewenzhiguo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wenzhiguo.newstitlewenzhiguo.R;

public class Care_fragment extends Fragment implements View.OnClickListener{

    private View view;
    private ImageView loveadd;
    private LinearLayout love_ll;
    private ImageView love_image;
    public Care_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_care, container, false);
        //初始化
        initView(view);
        //监听
        setonListener();
        return view;
    }

    private void setonListener() {
        loveadd.setOnClickListener(this);
        love_image.setOnClickListener(this);
        love_ll.setOnClickListener(this);
    }

    private void initView(View view) {
        loveadd = (ImageView) view.findViewById(R.id.love_add);
        love_image = (ImageView) view.findViewById(R.id.love_addimg);
        love_ll = (LinearLayout) view.findViewById(R.id.love_ll);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.love_add:
                Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.love_addimg:
                Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.love_ll:
                Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

