package com.zjxu.cc.questiondatabase;

import android.app.Application;

import com.zjxu.cc.questiondatabase.bean.MyDatabaseOpenHelper;
import com.zjxu.cc.questiondatabase.injection.component.ApplicationComponent;
import com.zjxu.cc.questiondatabase.injection.component.DaggerApplicationComponent;
import com.zjxu.cc.questiondatabase.injection.module.ApplicationModule;


public class App extends Application {

    private ApplicationComponent component;
    public MyDatabaseOpenHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }
    private void initInjector() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        component.inject(this);
    }
    public ApplicationComponent component() {
        return component;
    }
    public MyDatabaseOpenHelper getDbHelper(){
        if (dbHelper == null){
            dbHelper = new MyDatabaseOpenHelper(this);
        }
        return dbHelper;
    }
}
