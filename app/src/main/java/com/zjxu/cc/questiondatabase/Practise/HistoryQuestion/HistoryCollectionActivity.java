package com.zjxu.cc.questiondatabase.Practise.HistoryQuestion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zjxu.cc.questiondatabase.CommonActivity;
import com.zjxu.cc.questiondatabase.Question;
import com.zjxu.cc.questiondatabase.R;
import com.zjxu.cc.questiondatabase.bean.MyDatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryCollectionActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    public final String TAG = "cc";
    private int count=0;
    private int questionNumber = 1;
    private int current=0;
    private int wrong_current = 0;

    private int mode = 0; // 单选0 多选1 判断2
    private boolean wrongMode;//标志变量，判断是否进入错题模式

    private boolean WrongQuestion=false;
    private int progress=0;     //当前进度条的值

    public TextView tv_history_question;
    public String history_nowAnswer = "";

    //手势控制
    private static final int FLING_MIN_DISTANCE = 120;
    private static final int FLING_MIN_VELOCITY = 200;
    private GestureDetector mGestureDetector;
    private View.OnTouchListener mOnTouchListener;
    private GestureDetector.SimpleOnGestureListener mySimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // TODO Auto-generated method stub
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            queryCallback_Next(current);
            history_seekbar.setProgress(current);
        }
        if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            queryCallback_Previous(current);
            history_seekbar.setProgress(current);
        }
        return false;
        }
    };

    //控件
    public RadioButton[] history_radioButtons = new RadioButton[4];
    public CheckBox history_checkBox1;
    public CheckBox history_checkBox2;
    public CheckBox history_checkBox3;
    public CheckBox history_checkBox4;
    private boolean[] history_checkedArray = new boolean[]{false, false, false, false};

    private ImageButton btn_history_checkanswer;
    private TextView questionNum;
    private TextView tv_history_explaination;
    private TextView user_history_answer;
    private RadioGroup radioGroup_history;

    //进度条
    private SeekBar history_seekbar;

    //调用辅助类
    CommonActivity commonActivity=new CommonActivity();
    MyDatabaseOpenHelper mMyDatabaseOpenHelper=new MyDatabaseOpenHelper(this);

    final List<Question> history_list=mMyDatabaseOpenHelper.getCollectionQuestion("History",this);
    Question question_history = history_list.get(0);
    private  MyDatabaseOpenHelper myDatabaseOpenHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practises_collection);

        //设置手势监听
        mGestureDetector=new GestureDetector(mySimpleOnGestureListener);
        mOnTouchListener= (arg0, arg1) -> {
            if (mGestureDetector.onTouchEvent(arg1)) {
                return true;
            }
            return false;
        };

        myDatabaseOpenHelper=new MyDatabaseOpenHelper(this);

        //初始化
        count = history_list.size();
        mode = 0;
        wrongMode = false;

        //绑定控件
        tv_history_question = findViewById(R.id.Question_Collection);
        questionNum = findViewById(R.id.QuestionNum_Collection);
        history_radioButtons[0] = findViewById(R.id.answerA_Collection);
        history_radioButtons[1] = findViewById(R.id.answerB_Collection);
        history_radioButtons[2] = findViewById(R.id.answerC_Collection);
        history_radioButtons[3] = findViewById(R.id.answerD_Collection);
        history_checkBox1 = findViewById(R.id.c1_Collection);
        history_checkBox2 = findViewById(R.id.c2_Collection);
        history_checkBox3 = findViewById(R.id.c3_Collection);
        history_checkBox4 = findViewById(R.id.c4_Collection);

        btn_history_checkanswer = findViewById(R.id.btn_answer_Collection);//查看答案按钮
        btn_history_checkanswer.setVisibility(View.GONE);

        tv_history_explaination = findViewById(R.id.explaination_Collection);//解析文本域
        user_history_answer = findViewById(R.id.text_answer_Collection);
        radioGroup_history = findViewById(R.id.radioGroup_Collection);

        history_seekbar=findViewById(R.id.test_seekbar_Collection);//进度条
        history_seekbar.setMax(history_list.size());
        history_seekbar.setOnSeekBarChangeListener(this);

        //为控件赋值
        history_nowAnswer = question_history.explaination;
        questionNum.setText("当前第1道题");
        tv_history_question.setText(question_history.question);
        history_radioButtons[0].setText(question_history.answerA);
        history_radioButtons[1].setText(question_history.answerB);
        history_radioButtons[2].setText(question_history.answerC);
        history_radioButtons[3].setText(question_history.answerD);
        history_checkBox1.setVisibility(View.GONE);
        history_checkBox2.setVisibility(View.GONE);
        history_checkBox3.setVisibility(View.GONE);
        history_checkBox4.setVisibility(View.GONE);

        btn_history_checkanswer.setOnClickListener(view -> {
            tv_history_explaination.setText(history_nowAnswer);
            tv_history_explaination.setVisibility(View.VISIBLE);

            Log.i(TAG, "BTN_Answer : " + history_nowAnswer);
        });

        radioGroup_history.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            for (int i = 0; i < 4; i++) {
                if ( history_radioButtons[i].isChecked() == true) {
                    history_list.get(current).selectedAnswer = i;
                    if(history_list.get(current).selectedAnswer!=history_list.get(current).answer){
                        Toast.makeText(this, "抱歉，你答错了噢", Toast.LENGTH_SHORT).show();
                    }
                    else
                        tv_history_explaination.setText(history_list.get(current).explaination);
                    tv_history_explaination.setVisibility(View.VISIBLE);
                    break;
                }
            }

            Log.i("Test", "checkAnswer: " + history_list.get(current).question + " " + history_list.get(current).selectedAnswer+"     "+history_list.get(current).answer);
        });

        history_checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            history_checkedArray[0] = isChecked;
            history_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( history_checkedArray);
            Log.i(TAG, "C4: T" + " " + history_checkedArray[0] + " " +  history_list.get(current).selectedAnswer);
        });
        history_checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            history_checkedArray[1] = isChecked;
            history_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( history_checkedArray);
            Log.i(TAG, "C4: T" + " " + history_checkedArray[1] + " " +  history_list.get(current).selectedAnswer);
        });
        history_checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            history_checkedArray[2] = isChecked;
            history_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( history_checkedArray);
            Log.i(TAG, "C4: T" + " " + history_checkedArray[2] + " " +  history_list.get(current).selectedAnswer);
        });
        history_checkBox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            history_checkedArray[3] = isChecked;
            history_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( history_checkedArray);
            Log.i(TAG, "C4: T" + " " + history_checkedArray[3] + " " +  history_list.get(current).selectedAnswer);
        });

        Log.i(TAG, "Judge: " + commonActivity.JudgeCheckButton(history_checkedArray));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    public void queryCallback_Next(int number) {
        current=number;
        wrong_current++;
        tv_history_explaination.setVisibility(View.GONE);
        if (current < count - 1) {//若当前题目不为最后一题，点击next按钮跳转到下一题；否则不响应
            current++;
            questionNumber = current + 1;
            Question question = history_list.get(current);
            mode = question.mode;
            history_nowAnswer = question.explaination;
            if (mode == 0) {
                commonActivity.UpdateSimpleQuestion(questionNum,questionNumber,tv_history_question,history_radioButtons,
                        history_checkBox1,history_checkBox2,history_checkBox3,history_checkBox4,
                        question,tv_history_explaination,user_history_answer,wrongMode);
                // 若之前已经选择过，则应记录选择
                radioGroup_history.clearCheck();
                if (question.selectedAnswer != -1) {
                    history_radioButtons[question.selectedAnswer].setChecked(true);
                }
            } else if (mode == 1) {
                commonActivity.UpdateJudgeQuestion(tv_history_question,questionNum,tv_history_explaination,user_history_answer,history_radioButtons,
                        history_checkBox1,history_checkBox2,history_checkBox3,history_checkBox4,
                        question,questionNumber,wrongMode);
                //若之前已经选择过，则应记录选择
                radioGroup_history.clearCheck();
                if (question.selectedAnswer != -1) {
                    history_radioButtons[question.selectedAnswer].setChecked(true);
                }
            } else if (mode == 2) {
                commonActivity.UpdateMutiChoiceQuestion(tv_history_question,questionNum,tv_history_explaination,user_history_answer,
                        history_radioButtons,history_checkBox1,history_checkBox2,history_checkBox3,history_checkBox4,
                        question,questionNumber,wrongMode,history_checkedArray);
                btn_history_checkanswer.setVisibility(View.VISIBLE);
                //若之前已经选择过，则应记录选择
                history_checkBox1.setChecked(false);
                history_checkBox2.setChecked(false);
                history_checkBox3.setChecked(false);
                history_checkBox4.setChecked(false);
                if (question.selectedAnswer != -1) {
                    commonActivity.SetCheckBox(question.selectedAnswer,history_checkBox1,history_checkBox2,history_checkBox3,history_checkBox4);
                }
            }
        }
        //错题模式的最后一题
        else if (current == count - 1 && wrongMode == true) {
            new AlertDialog.Builder(HistoryCollectionActivity.this)
                    .setTitle("提示")
                    .setMessage("已经到达最后一题，是否退出？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            HistoryCollectionActivity.this.finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            //当前题目为最后一题时，告知用户作答正确的数量和作答错误的数量，并询问用户是否要查看错题
            final List<Integer> wrongList = commonActivity.checkAnswer(history_list);
            //作对所有题目
            if (wrongList.size() == 0) {
                new AlertDialog.Builder(HistoryCollectionActivity.this)
                        .setTitle("提示")
                        .setMessage("恭喜你全部回答正确！")
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            HistoryCollectionActivity.this.finish();
                        }).show();
            } else                  //存在错题
                new AlertDialog.Builder(HistoryCollectionActivity.this)
                        .setTitle("提示")
                        .setMessage("您答对了" + (history_list.size() - wrongList.size()) +
                                "道题目；答错了" + wrongList.size() + "道题目。是否查看错题？")
                        .setPositiveButton("确定", (dialogInterface, which) -> {
                            //判断进入错题模式
                            questionNum.setText("当前第" + 1 + "道题");
                            wrongMode = true;
                            WrongQuestion=true;
                            List<Question> newList = new ArrayList<>();
                            //将错误题目复制到newList中
                            for (int i = 0; i < wrongList.size(); i++) {
                                newList.add(history_list.get(wrongList.get(i)));
                            }
                            //将原来的list清空
                            history_list.clear();
                            //将错误题目添加到原来的list中
                            for (int i = 0; i < newList.size(); i++) {
                                history_list.add(newList.get(i));
                            }
                            wrong_current = 0;
                            count = history_list.size();

                            //更新显示时的内容
                            current = 0;
                            Question question_wrong = history_list.get(wrong_current);
                            tv_history_explaination.setText(question_wrong.explaination);
                            //显示解析
                            tv_history_explaination.setVisibility(View.VISIBLE);
                            if (question_wrong.mode == 0) {
                                Log.i(TAG, "Mode: 0");
                                user_history_answer.setText(commonActivity.getUserAnswer(question_wrong.selectedAnswer));
                                user_history_answer.setVisibility(View.VISIBLE);
                                tv_history_question.setText(question_wrong.question);
                                history_radioButtons[0].setText(question_wrong.answerA);
                                history_radioButtons[1].setText(question_wrong.answerB);
                                history_radioButtons[2].setText(question_wrong.answerC);
                                history_radioButtons[3].setText(question_wrong.answerD);
                                history_radioButtons[0].setVisibility(View.VISIBLE);
                                history_radioButtons[1].setVisibility(View.VISIBLE);
                                history_radioButtons[2].setVisibility(View.VISIBLE);
                                history_radioButtons[3].setVisibility(View.VISIBLE);
                                history_checkBox1.setVisibility(View.GONE);
                                history_checkBox2.setVisibility(View.GONE);
                                history_checkBox3.setVisibility(View.GONE);
                                history_checkBox4.setVisibility(View.GONE);

                            } else if (question_wrong.mode == 1) {
                                user_history_answer.setText(commonActivity.getUserAnswer(question_wrong.selectedAnswer));
                                user_history_answer.setVisibility(View.VISIBLE);
                                Log.i(TAG, "Mode: 1");
                                tv_history_question.setText(question_wrong.question);
                                history_radioButtons[0].setText(question_wrong.answerA);
                                history_radioButtons[1].setText(question_wrong.answerB);
                                history_radioButtons[0].setVisibility(View.VISIBLE);
                                history_radioButtons[1].setVisibility(View.VISIBLE);
                                history_radioButtons[2].setVisibility(View.GONE);
                                history_radioButtons[3].setVisibility(View.GONE);
                                history_checkBox1.setVisibility(View.GONE);
                                history_checkBox2.setVisibility(View.GONE);
                                history_checkBox3.setVisibility(View.GONE);
                                history_checkBox4.setVisibility(View.GONE);

                            } else if (mode == 2) {
                                questionNum.setText("当前第" + questionNumber + "道题");
                                tv_history_question.setText(question_wrong.question);
                                user_history_answer.setVisibility(View.GONE);
                                history_radioButtons[0].setVisibility(View.GONE);
                                history_radioButtons[1].setVisibility(View.GONE);
                                history_radioButtons[2].setVisibility(View.GONE);
                                history_radioButtons[3].setVisibility(View.GONE);
                                history_checkBox1.setText(question_wrong.answerA);
                                history_checkBox2.setText(question_wrong.answerB);
                                history_checkBox3.setText(question_wrong.answerC);
                                history_checkBox4.setText(question_wrong.answerD);
                                history_checkBox1.setVisibility(View.VISIBLE);
                                history_checkBox2.setVisibility(View.VISIBLE);
                                history_checkBox3.setVisibility(View.VISIBLE);
                                history_checkBox4.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("取消", (dialogInterface, which) -> {
                            //点击取消时，关闭当前activity
                            HistoryCollectionActivity.this.finish();
                        }).show();
        }
    }
    public void queryCallback_Previous(int number){
        current=number;
        tv_history_explaination.setVisibility(View.GONE);
        if (current > 0)//若当前题目不为第一题，点击previous按钮跳转到上一题；否则不响应
        {
            current--;
            questionNumber = current + 1;
            Question q1 = history_list.get(current);
            tv_history_question.setText(q1.question);
            questionNum.setText("当前第" + questionNumber + "道题");

            history_nowAnswer = q1.explaination;
            if (q1.mode == 0) {
                Log.i(TAG, "LastMode: 0");
                history_radioButtons[0].setText(q1.answerA);
                history_radioButtons[1].setText(q1.answerB);
                history_radioButtons[2].setText(q1.answerC);
                history_radioButtons[3].setText(q1.answerD);
                history_radioButtons[0].setVisibility(View.VISIBLE);
                history_radioButtons[1].setVisibility(View.VISIBLE);
                history_radioButtons[2].setVisibility(View.VISIBLE);
                history_radioButtons[3].setVisibility(View.VISIBLE);
                history_checkBox1.setVisibility(View.GONE);
                history_checkBox2.setVisibility(View.GONE);
                history_checkBox3.setVisibility(View.GONE);
                history_checkBox4.setVisibility(View.GONE);
                if (wrongMode) {
                    tv_history_explaination.setText(q1.explaination);
                    tv_history_explaination.setVisibility(View.VISIBLE);
                    user_history_answer.setText(commonActivity.getUserAnswer(q1.selectedAnswer));
                    user_history_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "last" + " " +question_history.explaination);
                }
                //若之前已经选择过，则应记录选择
                radioGroup_history.clearCheck();
                if (q1.selectedAnswer != -1) {
                    history_radioButtons[q1.selectedAnswer].setChecked(true);
                }

            } else if (q1.mode == 1) {
                Log.i(TAG, "LastMode: 1");
                history_radioButtons[0].setText(q1.answerA);
                history_radioButtons[1].setText(q1.answerB);
                history_radioButtons[2].setText(q1.answerB);
                history_radioButtons[3].setText(q1.answerB);
                history_radioButtons[0].setVisibility(View.VISIBLE);
                history_radioButtons[1].setVisibility(View.VISIBLE);
                history_radioButtons[2].setVisibility(View.GONE);
                history_radioButtons[3].setVisibility(View.GONE);
                history_checkBox1.setVisibility(View.GONE);
                history_checkBox2.setVisibility(View.GONE);
                history_checkBox3.setVisibility(View.GONE);
                history_checkBox4.setVisibility(View.GONE);
                if (wrongMode) {
                    tv_history_explaination.setText(q1.explaination);
                    tv_history_explaination.setVisibility(View.VISIBLE);
                    user_history_answer.setText(commonActivity.getUserAnswer(q1.selectedAnswer));
                    user_history_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "last" + " " + question_history.explaination);
                }
                //若之前已经选择过，则应记录选择
                radioGroup_history.clearCheck();
                if (q1.selectedAnswer != -1) {
                    history_radioButtons[q1.selectedAnswer].setChecked(true);
                }
            } else if (mode == 2) {
                Log.i(TAG, "Judge: " + commonActivity.JudgeCheckButton(history_checkedArray));
                questionNum.setText("当前第" + questionNumber + "道题");
                tv_history_question.setText(q1.question);
                user_history_answer.setVisibility(View.GONE);
                history_radioButtons[0].setVisibility(View.GONE);
                history_radioButtons[1].setVisibility(View.GONE);
                history_radioButtons[2].setVisibility(View.GONE);
                history_radioButtons[3].setVisibility(View.GONE);

                history_checkBox1.setText(q1.answerA);
                history_checkBox2.setText(q1.answerB);
                history_checkBox3.setText(q1.answerC);
                history_checkBox4.setText(q1.answerD);

                history_checkBox1.setVisibility(View.VISIBLE);
                history_checkBox2.setVisibility(View.VISIBLE);
                history_checkBox3.setVisibility(View.VISIBLE);
                history_checkBox4.setVisibility(View.VISIBLE);
                if (wrongMode) {
                    tv_history_explaination.setText(q1.explaination);
                    tv_history_explaination.setVisibility(View.VISIBLE);
                    user_history_answer.setText(commonActivity.getUserAnswer(q1.selectedAnswer));
                    user_history_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "next" + " " + q1.explaination);
                }

                if (q1.selectedAnswer != -1) {
                    history_checkBox1.setChecked(false);
                    history_checkBox2.setChecked(false);
                    history_checkBox3.setChecked(false);
                    history_checkBox4.setChecked(false);
                    commonActivity.SetCheckBox(q1.selectedAnswer,history_checkBox1,history_checkBox2,history_checkBox3,history_checkBox4);
                }
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        this.progress=progress;
        queryCallback_Next(progress-1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if(!wrongMode){
            history_seekbar.setProgress(wrong_current);
        }
        else {
            history_seekbar.setProgress(current);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        current=progress;
        Log.i(TAG,"当前页为："+progress);
    }
}
