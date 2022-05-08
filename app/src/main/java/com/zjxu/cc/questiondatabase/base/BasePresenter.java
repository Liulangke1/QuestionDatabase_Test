package com.zjxu.cc.questiondatabase.base;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BasePresenter <T extends BaseView>{

    public BasePresenter(){}

    private T mView;

    public void setView(T mvpView){
        this.mView=mvpView;
    }

    public T getView(){
        return mView;
    }

    public boolean isViewAttached(){
        return mView!=null;
    }

    public void detachView(){
        if(mView!=null){
            this.mView=null;
        }
    }
}
