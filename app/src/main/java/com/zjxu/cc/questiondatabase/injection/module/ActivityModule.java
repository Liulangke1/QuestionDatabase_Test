package com.zjxu.cc.questiondatabase.injection.module;

import android.app.Activity;


import com.zjxu.cc.questiondatabase.injection.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;
    public ActivityModule(Activity activity) {
        this.activity = activity;
    }
    @Provides
    @PerActivity
    Activity provideActivity(){
        return this.activity;
    }

}













