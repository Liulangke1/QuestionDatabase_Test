package com.zjxu.cc.questiondatabase.main.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjxu.cc.questiondatabase.Exam.ExamMainActivity;
import com.zjxu.cc.questiondatabase.R;
import com.zjxu.cc.questiondatabase.base.BaseActivity;
import com.zjxu.cc.questiondatabase.base.BaseFragment;

import javax.inject.Inject;

public  class ExamFragment extends
        BaseFragment<ExamView, ExamPresenter>
        implements ExamView, View.OnClickListener {

    private Button mLvYouExam_btn;
    private Button mHistoryExam_btn;
    private Button mThinkingVirtue_btn;
    private Button mMaYuanExam_btn;
    private Button mMaoGaiExam_btn;
    private Button mNetworkSecurityExam_btn;
    private Button mComputerExam_btn;

    @Inject
    ExamPresenter mExamPresenter;

    @Override
    protected ExamPresenter createPresenter(){
        return mExamPresenter;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_exam,container,false);
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLvYouExam_btn=getActivity().findViewById(R.id.LvYou_exam_button);
        mHistoryExam_btn=getActivity().findViewById(R.id.History_exam_button);
        mThinkingVirtue_btn=getActivity().findViewById(R.id.ThinkingVirtue_exam_button);
        mMaYuanExam_btn=getActivity().findViewById(R.id.MaYuan_exam_button);
        mMaoGaiExam_btn=getActivity().findViewById(R.id.MaoGai_exam_button);
        mNetworkSecurityExam_btn=getActivity().findViewById(R.id.NetworkSecurity_exam_button);
        mComputerExam_btn=getActivity().findViewById(R.id.Computer_second_exam_buttont);

        mLvYouExam_btn.setOnClickListener(this::onClick);
        mHistoryExam_btn.setOnClickListener(this::onClick);
        mThinkingVirtue_btn.setOnClickListener(this::onClick);
        mMaYuanExam_btn.setOnClickListener(this::onClick);
        mMaoGaiExam_btn.setOnClickListener(this::onClick);
        mNetworkSecurityExam_btn.setOnClickListener(this::onClick);
        mComputerExam_btn.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        Intent intent_Choose=new Intent(getActivity(), ExamMainActivity.class);
        switch (v.getId()){
            case R.id.LvYou_exam_button:
                intent_Choose.putExtra("Question","LvYou");
                startActivity(intent_Choose);
                break;

            case R.id.History_exam_button:
                intent_Choose.putExtra("Question","History");
                startActivity(intent_Choose);
                break;

            case R.id.ThinkingVirtue_exam_button:
                intent_Choose.putExtra("Question","ThinkingVirtue");
                startActivity(intent_Choose);
                break;

            case R.id.MaYuan_exam_button:
                intent_Choose.putExtra("Question","MaYuan");
                startActivity(intent_Choose);
                break;

            case R.id.MaoGai_exam_button:
                intent_Choose.putExtra("Question","MaoGai");
                startActivity(intent_Choose);
                break;

            case R.id.NetworkSecurity_exam_button:
                intent_Choose.putExtra("Question","NetworkSecurity");
                startActivity(intent_Choose);
                break;

            case R.id.Computer_second_exam_buttont:
                intent_Choose.putExtra("Question","ComputerSecond");
                startActivity(intent_Choose);
                break;
        }
    }
}
