package com.zjxu.cc.questiondatabase.Practise.ComputerQuestionDataBase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zjxu.cc.questiondatabase.R;


public class ComputerQuestionMainActivity extends Activity implements View.OnClickListener {

    private Button mCbtn;
    private Button mMSOfficebtn;
    private Button mPythonbtn;
    private Button mVBbtn;
    private Button mJavabtn;
    private Button mAccessbtn;
    private Button mC__btn;
    private Button mMySQlbtn;
    private Button mWebbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_question_main);

        mCbtn=findViewById(R.id.Question_C);
        mMSOfficebtn=findViewById(R.id.Question_MS_Office);
        mPythonbtn=findViewById(R.id.Question_python);
        mVBbtn=findViewById(R.id.Question_VB);
        mJavabtn=findViewById(R.id.Question_Java);
        mAccessbtn=findViewById(R.id.Question_Access);
        mC__btn=findViewById(R.id.Question_C__);
        mMySQlbtn=findViewById(R.id.Question_MySQL);
        mWebbtn=findViewById(R.id.Question_Web);

        //设置监听器
        mCbtn.setOnClickListener(this::onClick);
        mMSOfficebtn.setOnClickListener(this::onClick);
        mPythonbtn.setOnClickListener(this::onClick);
        mVBbtn.setOnClickListener(this::onClick);
        mJavabtn.setOnClickListener(this::onClick);
        mAccessbtn.setOnClickListener(this::onClick);
        mC__btn.setOnClickListener(this::onClick);
        mMySQlbtn.setOnClickListener(this::onClick);
        mWebbtn.setOnClickListener(this::onClick);
    }

    //设置点击事件
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.Question_C:
//                Intent intent_C=new Intent(ComputerQuestionMainActivity.this, QuestionC.class);
//                intent_C.putExtra("DBName","QuestionC.db");
//                startActivity(intent_C);
                Toast.makeText(this,"点击了C语言题库",Toast.LENGTH_SHORT).show();
                break;

            case R.id.Question_MS_Office:
//                Intent intent_MS=new Intent(ComputerQuestionMainActivity.this, QuestionMSOffice.class);
//                intent_MS.putExtra("DBName","QuestionMS.db");
//                startActivity(intent_MS);
                Toast.makeText(this,"点击了Office题库",Toast.LENGTH_SHORT).show();

                break;

            case R.id.Question_python:
//                Intent intent_Python=new Intent(ComputerQuestionMainActivity.this, QuestionPython.class);
//                intent_Python.putExtra("DBName","QuestionPython.db");
//                startActivity(intent_Python);
                Toast.makeText(this,"点击了python题库",Toast.LENGTH_SHORT).show();

                break;

            case R.id.Question_VB:
//                Intent intent_VB=new Intent(ComputerQuestionMainActivity.this, QuestionVB.class);
//                intent_VB.putExtra("DBName","QuestionVB.db");
//                startActivity(intent_VB);
                Toast.makeText(this,"点击了VB题库",Toast.LENGTH_SHORT).show();

                break;

            case R.id.Question_Java:
//                Intent intent_Java=new Intent(ComputerQuestionMainActivity.this, QuestionJava.class);
//                intent_Java.putExtra("DBName","QuestionJava.db");
//                startActivity(intent_Java);
                Toast.makeText(this,"点击了Java题库",Toast.LENGTH_SHORT).show();

                break;

            case R.id.Question_Access:
//                Intent intent_Access=new Intent(ComputerQuestionMainActivity.this, QuestionAccess.class);
//                intent_Access.putExtra("DBName","QuestionAccess.db");
//                startActivity(intent_Access);
                Toast.makeText(this,"点击了Access数据库题库",Toast.LENGTH_SHORT).show();

                break;

            case R.id.Question_C__:
//                Intent intent_C__=new Intent(ComputerQuestionMainActivity.this, QuestionC__.class);
//                intent_C__.putExtra("DBName","QuestionC__.db");
//                startActivity(intent_C__);
                Toast.makeText(this,"点击了C++题库",Toast.LENGTH_SHORT).show();

                break;

            case R.id.Question_MySQL:
//                Intent intent_MySQL=new Intent(ComputerQuestionMainActivity.this, QuestionMySQL.class);
//                intent_MySQL.putExtra("DBName","QuestionMySQL.db");
//                startActivity(intent_MySQL);
                Toast.makeText(this,"点击了MySQL题库",Toast.LENGTH_SHORT).show();

                break;

            case R.id.Question_Web:
//                Intent intent_Web=new Intent(ComputerQuestionMainActivity.this, QuestionWeb.class);
//                intent_Web.putExtra("DBName","QuestionWeb.db");
//                startActivity(intent_Web);
                Toast.makeText(this,"点击了Web题库",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}