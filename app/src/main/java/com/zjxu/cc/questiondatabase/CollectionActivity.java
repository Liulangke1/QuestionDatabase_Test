package com.zjxu.cc.questiondatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zjxu.cc.questiondatabase.Exam.ChooseExamMainActivity;
import com.zjxu.cc.questiondatabase.Practise.ComputerQuestionDataBase.ComputerQuestionMainActivity;
import com.zjxu.cc.questiondatabase.Practise.HistoryQuestion.HistoryCollectionActivity;
import com.zjxu.cc.questiondatabase.Practise.LvYouQuestion.LvYouCollectionActivity;
import com.zjxu.cc.questiondatabase.Practise.MaYuanQuestion.MaYuanCollectionActivity;
import com.zjxu.cc.questiondatabase.Practise.MaoGaiQuestion.MaoGaiCollectionActivity;
import com.zjxu.cc.questiondatabase.Practise.Network_Security.NetworkSecurityCollectionActivity;
import com.zjxu.cc.questiondatabase.Practise.ThinkingVirtue.ThinkingVirtueCollectionActivity;

public class CollectionActivity extends Activity implements View.OnClickListener {

    private Button mComputer_Second_btn;
    private Button mMaogai_btn;
    private Button mMayuan_btn;
    private Button mLuyou_btn;
    private Button mHistory_btn;
    private Button mThinkingVirtue_btn;
    private Button mNetworkSecurity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_init);

        mComputer_Second_btn = findViewById(R.id.Computer_second_button);
        mMaogai_btn = findViewById(R.id.MaoGai_button);
        mMayuan_btn = findViewById(R.id.MaYuan_button);
        mLuyou_btn = findViewById(R.id.LuYou_button);
        mHistory_btn = findViewById(R.id.History_button);
        mThinkingVirtue_btn=findViewById(R.id.ThinkingVirtue_button);
        mNetworkSecurity=findViewById(R.id.NetworkSecurity_button);

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
                Intent intent_Computer = new Intent(CollectionActivity.this, ComputerQuestionMainActivity.class);
                startActivity(intent_Computer);
                break;
            case R.id.MaoGai_button:
                Intent intent_MaoGai = new Intent(CollectionActivity.this, MaoGaiCollectionActivity.class);
                startActivity(intent_MaoGai);
                break;

            case R.id.MaYuan_button:
                Intent intent_MaYuan = new Intent(CollectionActivity.this, MaYuanCollectionActivity.class);
                startActivity(intent_MaYuan);
                break;

            case R.id.LuYou_button:
                Intent intent_LvYou = new Intent(CollectionActivity.this, LvYouCollectionActivity.class);
                startActivity(intent_LvYou);
                break;

            case R.id.History_button:
                Intent intent_History = new Intent(CollectionActivity.this, HistoryCollectionActivity.class);
                startActivity(intent_History);
                break;

            case R.id.ThinkingVirtue_button:
                Intent intent_ThinkingVirtue=new Intent(CollectionActivity.this, ThinkingVirtueCollectionActivity.class);
                startActivity(intent_ThinkingVirtue);
                break;

            case R.id.NetworkSecurity_button:
                Intent intent_NetworkSecurity=new Intent(CollectionActivity.this, NetworkSecurityCollectionActivity.class);
                startActivity(intent_NetworkSecurity);
                break;

        }
    }
}


