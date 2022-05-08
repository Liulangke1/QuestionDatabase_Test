package com.zjxu.cc.questiondatabase.main.practise;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjxu.cc.questiondatabase.Exam.ChooseExamMainActivity;
import com.zjxu.cc.questiondatabase.Practise.ComputerQuestionDataBase.ComputerQuestionMainActivity;
import com.zjxu.cc.questiondatabase.Practise.HistoryQuestion.HistoryMainActivity;
import com.zjxu.cc.questiondatabase.Practise.LvYouQuestion.LvYouMainActivity;
import com.zjxu.cc.questiondatabase.Practise.MaYuanQuestion.MayuanMainActivity;
import com.zjxu.cc.questiondatabase.Practise.MaoGaiQuestion.MaoGaiMainActivity;
import com.zjxu.cc.questiondatabase.Practise.Network_Security.NetworkSecurityMainActivity;
import com.zjxu.cc.questiondatabase.Practise.ThinkingVirtue.ThinkingVirtueMainActivity;
import com.zjxu.cc.questiondatabase.R;
import com.zjxu.cc.questiondatabase.base.BaseFragment;

import javax.inject.Inject;

public class PractiseFragment extends BaseFragment<PractiseView, PractisePresenter>
    implements PractiseView, View.OnClickListener {

    private Button mComputer_Second_btn;
    private Button mMaogai_btn;
    private Button mMayuan_btn;
    private Button mLuyou_btn;
    private Button mHistory_btn;
    private Button mThinkingVirtue_btn;
    private Button mNetworkSecurity;

    @Inject
    PractisePresenter mPractisePresenter;

    @Override
    protected PractisePresenter createPresenter(){
        return mPractisePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_practise,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mComputer_Second_btn = getActivity().findViewById(R.id.Computer_second_button);
        mMaogai_btn = getActivity().findViewById(R.id.MaoGai_button);
        mMayuan_btn = getActivity().findViewById(R.id.MaYuan_button);
        mLuyou_btn = getActivity().findViewById(R.id.LuYou_button);
        mHistory_btn = getActivity().findViewById(R.id.History_button);
        mThinkingVirtue_btn=getActivity().findViewById(R.id.ThinkingVirtue_button);
        mNetworkSecurity=getActivity().findViewById(R.id.NetworkSecurity_button);

        mComputer_Second_btn.setOnClickListener(this::onClick);
        mMaogai_btn.setOnClickListener(this::onClick);
        mMayuan_btn.setOnClickListener(this::onClick);
        mLuyou_btn.setOnClickListener(this::onClick);
        mHistory_btn.setOnClickListener(this::onClick);
        mThinkingVirtue_btn.setOnClickListener(this::onClick);
        mNetworkSecurity.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Computer_second_button:
                startActivity(new Intent(getActivity(), ComputerQuestionMainActivity.class));
                break;
            case R.id.MaoGai_button:
                startActivity(new Intent(getActivity(), MaoGaiMainActivity.class));
                break;

            case R.id.MaYuan_button:
                startActivity(new Intent(getActivity(), MayuanMainActivity.class));
                break;

            case R.id.LuYou_button:
                startActivity(new Intent(getActivity(), LvYouMainActivity.class));
                break;

            case R.id.History_button:
                startActivity(new Intent(getActivity(), HistoryMainActivity.class));
                break;

            case R.id.ThinkingVirtue_button:
                startActivity(new Intent(getActivity(), ThinkingVirtueMainActivity.class));
                break;

            case R.id.NetworkSecurity_button:
                startActivity(new Intent(getActivity(), NetworkSecurityMainActivity.class));
                break;

//            case R.id.Exam_button:
//                startActivity(new Intent(getActivity(), ChooseExamMainActivity.class));
//                break;
        }

    }
}
