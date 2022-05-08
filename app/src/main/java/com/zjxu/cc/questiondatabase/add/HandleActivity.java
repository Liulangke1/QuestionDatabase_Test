package com.zjxu.cc.questiondatabase.add;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.zjxu.cc.questiondatabase.Question;
import com.zjxu.cc.questiondatabase.R;
import com.zjxu.cc.questiondatabase.bean.AddDatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandleActivity extends AppCompatActivity {

    @BindView(R.id.question_table_et) TextInputEditText question_table;
    @BindView(R.id.question_title_et) TextInputEditText question_title;
    @BindView(R.id.answerA_et)         TextInputEditText question_answerA;
    @BindView(R.id.answerB_et)         TextInputEditText question_answerB;
    @BindView(R.id.answerC_et)         TextInputEditText question_answerC;
    @BindView(R.id.answerD_et)         TextInputEditText question_answerD;
    @BindView(R.id.answer_et)          TextInputEditText question_answer;
    @BindView(R.id.explaination_et)    TextInputEditText question_explaination;


    @BindView(R.id.choose_question_type) RadioGroup radio_type;
    @BindView(R.id.single_question)      RadioButton single_type;
    @BindView(R.id.judge_question)       RadioButton judge_type;
    @BindView(R.id.multi_question)       RadioButton multi_type;

    @BindView(R.id.add_question)         Button add_btn;

    int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle);
        ButterKnife.bind(this);

        radio_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.single_question:
                        mode=0;
                        Log.i("cc","单选题");
                        break;
                    case R.id.judge_question:
                        mode=1;
                        Log.i("cc","判断题");
                        break;
                    case R.id.multi_question:
                        mode=2;
                        Log.i("cc","多选题");
                        break;
                }
            }
        });

    }

    @OnClick(R.id.add_question)
    public void StartAdd(){
        String question_table_str=question_table.getText().toString().trim();
        String question_title_str=question_title.getText().toString().trim();
        String question_answerA_str=question_answerA.getText().toString().trim();
        String question_answerB_str=question_answerB.getText().toString().trim();
        String question_answerC_str=question_answerC.getText().toString().trim();
        String question_answerD_str=question_answerD.getText().toString().trim();
        String question_answer_str=question_answer.getText().toString().trim();
        String question_explaination_str=question_explaination.getText().toString().trim();

        Question question =new Question();
        question.question=question_title_str;
        question.answerA=question_answerA_str;
        question.answerB=question_answerB_str;
        question.answerC=question_answerC_str;
        question.answerD=question_answerD_str;
        question.answer= Integer.parseInt(question_answer_str);
        question.explaination=question_explaination_str;
        question.mode=mode;

        AddDatabaseHelper addDatabaseHelper=new AddDatabaseHelper(this,question_table_str,question.mode);
        addDatabaseHelper.onUpgrade(addDatabaseHelper.getWritableDatabase(),1,2);
        addDatabaseHelper.addData(question,mode,question_table_str);
        Log.i("cc","---------插入成功----------");
        Toast.makeText(this,"插入成功",Toast.LENGTH_SHORT).show();

        addDatabaseHelper.SelectTableName();

        question_title.getText().clear();
        question_answerA.getText().clear();
        question_answerB.getText().clear();
        question_answerC.getText().clear();
        question_answerD.getText().clear();
        question_answer.getText().clear();
        question_explaination.getText().clear();

    }
}