package com.wenzhiguo.newstitlewenzhiguo;

import android.widget.LinearLayout;

/**
 * Created by dell on 2017/2/23.
 */

public class EvenBus {
    private  LinearLayout night;
    public EvenBus(LinearLayout night){
        this.night=night;
    }

    public LinearLayout getNight() {
        return night;
    }
}
