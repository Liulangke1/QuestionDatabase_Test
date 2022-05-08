package com.zjxu.cc.questiondatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SplashActivity extends AppCompatActivity {

    private static final  long LOADING_TIME=3000;
    private Timer mTimer;
    private Unbinder mUnbinder;

    @BindView(R.id.splash_loading_item)
    ImageView animIv;

    @OnClick(R.id.skip_button)
    void skip(){
        skipToMain();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        mUnbinder= ButterKnife.bind(this);
        startAnim();
        keepLoading();
    }


    //添加时间任务
    private void keepLoading() {
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        skipToMain();
                    }
                });
            }
        },LOADING_TIME);
    }

    //动画的开启
    private void startAnim() {
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_loading_anim);

        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.INFINITE);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {            }
            @Override
            public void onAnimationEnd(Animation animation) {            }

            @Override
            public void onAnimationRepeat(Animation animation) {            }
        });
        animIv.startAnimation(animation);
    }

    //跳转到主界面
    private void skipToMain() {
        mTimer.cancel();
        animIv.clearAnimation();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}