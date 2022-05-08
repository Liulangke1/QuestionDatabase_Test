package com.zjxu.cc.questiondatabase.Exam;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zjxu.cc.questiondatabase.R;


public class ChooseExamMainActivity extends Activity implements View.OnClickListener {

    private Button mLvYouExam_btn;
    private Button mHistoryExam_btn;
    private Button mThinkingVirtue_btn;
    private Button mMaYuanExam_btn;
    private Button mMaoGaiExam_btn;
    private Button mNetworkSecurityExam_btn;
    private Button mComputerExam_btn;
    private Button mTmpExam_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exam_main);

        mLvYouExam_btn=findViewById(R.id.LvYou_exam_button);
        mHistoryExam_btn=findViewById(R.id.History_exam_button);
        mThinkingVirtue_btn=findViewById(R.id.ThinkingVirtue_exam_button);
        mMaYuanExam_btn=findViewById(R.id.MaYuan_exam_button);
        mMaoGaiExam_btn=findViewById(R.id.MaoGai_exam_button);
        mNetworkSecurityExam_btn=findViewById(R.id.NetworkSecurity_exam_button);
        mComputerExam_btn=findViewById(R.id.Computer_second_exam_buttont);
        mTmpExam_btn=findViewById(R.id.test3_btn);


        mLvYouExam_btn.setOnClickListener(this::onClick);
        mHistoryExam_btn.setOnClickListener(this::onClick);
        mThinkingVirtue_btn.setOnClickListener(this::onClick);
        mMaYuanExam_btn.setOnClickListener(this::onClick);
        mMaoGaiExam_btn.setOnClickListener(this::onClick);
        mNetworkSecurityExam_btn.setOnClickListener(this::onClick);
        mComputerExam_btn.setOnClickListener(this::onClick);
        mTmpExam_btn.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {

        Intent intent_Choose=new Intent(ChooseExamMainActivity.this, ExamMainActivity.class);
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
//                intent_Choose.putExtra("Question","ComputerSecond");
//                startActivity(intent_Choose);
                Toast.makeText(this,"点击了计算机等级考试题库",Toast.LENGTH_SHORT);
                break;

            case R.id.test3_btn:
//                intent_Choose.putExtra("Question","TMP");
//                startActivity(intent_Choose);
                Toast.makeText(this,"点击了计算机等级考试Web程序设计题库",Toast.LENGTH_SHORT);
                break;
        }
    }
}