package com.wenzhiguo.newstitlewenzhiguo;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by dell on 2017/2/23.
 */

public class EvenBus {
    private  LinearLayout night;
    private ImageView image;
    private TextView text;
    public EvenBus(LinearLayout night, ImageView image, TextView text){
        this.image=image;
        this.night=night;
        this.text=text;
    }

    public LinearLayout getNight() {
        return night;
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getText() {
        return text;
    }
}
