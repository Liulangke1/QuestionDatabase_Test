package com.zjxu.cc.questiondatabase;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class Gesture_control  extends Activity {

    //手势控制
    private static final int FLING_MIN_DISTANCE = 120;
    private static final int FLING_MIN_VELOCITY = 200;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;

    private GestureDetector.SimpleOnGestureListener mySimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // TODO Auto-generated method stub
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            }
            if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            }
            return false;
        }
    };

    public Gesture_control(){
        gestureDetector = new GestureDetector(mySimpleOnGestureListener);
        gestureListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                if (gestureDetector.onTouchEvent(arg1)) {
                    return true;
                }
                return false;
            }
        };
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }
}
