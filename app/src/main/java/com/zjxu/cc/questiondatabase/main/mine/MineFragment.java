package com.zjxu.cc.questiondatabase.main.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.zjxu.cc.questiondatabase.CollectionActivity;
import com.zjxu.cc.questiondatabase.R;
import com.zjxu.cc.questiondatabase.add.HandleActivity;
import com.zjxu.cc.questiondatabase.base.BaseActivity;
import com.zjxu.cc.questiondatabase.base.BaseFragment;


import javax.inject.Inject;

public class MineFragment extends BaseFragment<MineView,MinePresenter> implements MineView, View.OnClickListener {

    private ImageButton excel_btn;
    private ImageButton database_btn;
    private ImageButton word_btn;
    private ImageButton collection_btn;
    private ImageButton handle_btn;

    @Inject
    MinePresenter minePresenter;

    @Override
    protected MinePresenter createPresenter(){
        return  minePresenter;
    }

    @Override
    protected void injectDependencies() {
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_mine,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        excel_btn=getActivity().findViewById(R.id.excel_imagebutton);
        database_btn=getActivity().findViewById(R.id.database_imagebutton);
        word_btn=getActivity().findViewById(R.id.word_imagebutton);
        collection_btn=getActivity().findViewById(R.id.Collection_imagebutton);
        handle_btn = getActivity().findViewById(R.id.handle_imagebutton);

        excel_btn.setOnClickListener(this::onClick);
        database_btn.setOnClickListener(this::onClick);
        word_btn.setOnClickListener(this::onClick);
        collection_btn.setOnClickListener(this::onClick);
        handle_btn.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.excel_imagebutton:
//                Toast.makeText(mContext,"使用Excel表导入题库",Toast.LENGTH_SHORT).show();
//
                break;
            case R.id.database_imagebutton:
//                Toast.makeText(mContext,"使用SQLite数据库导入题库",Toast.LENGTH_SHORT).show();
                break;
            case R.id.word_imagebutton:
//                Toast.makeText(mContext,"使用Word表导入题库",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Collection_imagebutton:
                startActivity(new Intent(getActivity(), CollectionActivity.class));
                break;

            case R.id.handle_imagebutton:
                startActivity(new Intent(getActivity(), HandleActivity.class));

        }
    }
}
