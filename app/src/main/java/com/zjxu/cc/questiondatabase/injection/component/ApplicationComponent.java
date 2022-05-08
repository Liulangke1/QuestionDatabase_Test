package com.zjxu.cc.questiondatabase.injection.component;

import android.app.Application;
import android.content.Context;


import com.zjxu.cc.questiondatabase.App;
import com.zjxu.cc.questiondatabase.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton//单例
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(App application);
    Application application();//将Application开放给其他Component使用
    Context context();//将Context开放给其他Component使用
}















