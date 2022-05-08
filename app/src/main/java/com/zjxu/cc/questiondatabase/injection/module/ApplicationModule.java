package com.zjxu.cc.questiondatabase.injection.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.zjxu.cc.questiondatabase.App;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class ApplicationModule {
    private final App application;

    //Application不能new，通过构造函数传递
    public ApplicationModule(App application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    //提供OkHttpClient
    @Provides
    @Singleton//单例
    OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    //提供Gson
    @Provides
    @Singleton//单例
    Gson provideGson() {
        return new Gson();
    }
}























