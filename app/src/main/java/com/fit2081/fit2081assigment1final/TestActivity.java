package com.fit2081.fit2081assigment1final;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // 创建 GestureDetector 实例，并将当前 Activity 设置为监听器
        gestureDetector = new GestureDetector(this, this);
        gestureDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将触摸事件传递给 GestureDetector 处理
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    // 实现 OnGestureListener 接口的回调方法

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // 根据滑动距离判断左滑和右滑
        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            if (distanceX > 0) {
                Toast.makeText(this, "右滑", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "左滑", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Toast.makeText(this, "长按", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    // 实现 OnDoubleTapListener 接口的回调方法

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Toast.makeText(this, "双击", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
