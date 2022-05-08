package com.zjxu.cc.questiondatabase.Exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.zjxu.cc.questiondatabase.R;
import com.zjxu.cc.questiondatabase.bean.Save;

import java.util.Map;

public class ExamMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exammain);

        Button btn = findViewById(R.id.startButton);

        EditText time_text = findViewById(R.id.time_text);
        EditText single_text = findViewById(R.id.single_text);
        EditText judge_text = findViewById(R.id.judge_text);
        EditText mutichoice_text = findViewById(R.id.mutichoice_text);

        Intent intent_Choose=getIntent();
        final String dbname=intent_Choose.getStringExtra("Question");
        Log.i("Question","ExamMainActivity的Question是："+dbname);

        Map<String, String> userInfo = Save.getUserInfo(this);
        if (userInfo != null) {
            time_text.setText(userInfo.get("time"));
            single_text.setText(userInfo.get("single"));
            judge_text.setText(userInfo.get("judge"));
            mutichoice_text.setText(userInfo.get("mutichoice"));
        }

        btn.setOnClickListener(v -> {
            String time = time_text.getText().toString();
            String single = single_text.getText().toString();
            String judge = judge_text.getText().toString();
            String mutichoice = mutichoice_text.getText().toString();

            int timeNum = Integer.parseInt(time);
            int singleNum = Integer.parseInt(single);
            int judgeNum = Integer.parseInt(judge);
            int mutichoiceNum = Integer.parseInt(mutichoice);

            Save.saveUserInfo(this, time, single, judge, mutichoice,dbname);

            Intent intent = new Intent(ExamMainActivity.this, ExamActivity.class);

            intent.putExtra("time", timeNum);
            intent.putExtra("single", singleNum);
            intent.putExtra("judge", judgeNum);
            intent.putExtra("mutichoice", mutichoiceNum);
            intent.putExtra("Question",dbname);
            startActivity(intent);
        });
    }
}
