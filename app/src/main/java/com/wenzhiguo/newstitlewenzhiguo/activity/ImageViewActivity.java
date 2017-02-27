package com.wenzhiguo.newstitlewenzhiguo.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.wenzhiguo.newstitlewenzhiguo.R;
import com.wenzhiguo.newstitlewenzhiguo.Signal;

import org.xutils.image.ImageOptions;
import org.xutils.x;

public class ImageViewActivity extends Signal {
    private final int MODE_DRAG = 0;
    private final int MODE_ZOOM = 1;
    private final int MODE_NONE = 3;
    private int currentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ImageOptions imageOptions = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.MATRIX).build();
        //接收图片路径
        Intent intent = getIntent();
        String imageurl = intent.getStringExtra("imageurl");
        final ImageView imageView = (ImageView) findViewById(R.id.Activity_image);
        x.image().bind(imageView,imageurl,imageOptions);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private double beginSpacing;
            private float y;
            private float x;
            Matrix matrix = new Matrix();
            Matrix newMatrix = new Matrix();
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        matrix.set(imageView.getImageMatrix());
                        currentMode = MODE_DRAG;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (spacing(motionEvent) > 10) {
                            currentMode = MODE_ZOOM;
                            beginSpacing = spacing(motionEvent);
                            matrix.set(imageView.getImageMatrix());
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (currentMode == MODE_DRAG) {
                            Log.e("myMessage","ACTION_MOVE");
                            newMatrix.set(matrix);
                            float newX = motionEvent.getX();
                            float newY = motionEvent.getY();
                            float lastX = newX - x;
                            float lastY = newY - y;
                            newMatrix.postTranslate(lastX, lastY);
                        } else if (currentMode == MODE_ZOOM) {
                            //计算缩放比例
                            if (spacing(motionEvent) > 10) {
                                newMatrix.set(matrix);
                                double newSpacing = spacing(motionEvent);
                                float scale = (float) newSpacing / (float) beginSpacing;
                                PointF pointF = midPoint(motionEvent);
                                newMatrix.postScale(scale, scale, pointF.x, pointF.y);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        currentMode = MODE_NONE;
                        break;
                }
                imageView.setImageMatrix(newMatrix);
                return true;
            }
        });
    }

    //计算两点之间的距离
    private double spacing(MotionEvent event) {
        float x = 0;
        float y = 0;
        try {
            x = event.getX(0) - event.getX(1);
            y = event.getY(0) - event.getY(1);
        } catch (IllegalArgumentException e) {}
        return Math.sqrt(x * x + y * y);
    }

    //计算中点位置
    private PointF midPoint(MotionEvent event) {
        PointF pointF = new PointF();
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        pointF.set(x / 2, y / 2);
        return pointF;
    }

}