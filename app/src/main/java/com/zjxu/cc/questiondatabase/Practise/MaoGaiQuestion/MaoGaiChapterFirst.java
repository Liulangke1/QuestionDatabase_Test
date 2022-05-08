package com.zjxu.cc.questiondatabase.Practise.MaoGaiQuestion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import androidx.annotation.Nullable;


import com.zjxu.cc.questiondatabase.CommonActivity;
import com.zjxu.cc.questiondatabase.Question;
import com.zjxu.cc.questiondatabase.R;
import com.zjxu.cc.questiondatabase.bean.DBService;

import java.util.ArrayList;
import java.util.List;

public class MaoGaiChapterFirst extends Activity implements SeekBar.OnSeekBarChangeListener {

    public final String TAG = "Answer";
    private int count;
    private int questionNumber = 1;
    private int current=0;
    private int mode = 0; // 单选0 多选1 判断2
    private boolean wrongMode;//标志变量，判断是否进入错题模式

    private int progress=0;     //当前进度条的值

    public TextView tv_maogai_question;
    public String maogai_nowAnswer = "";

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
                test_seekbar.setProgress(current);
            }
            if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                queryCallback_Previous(current);
                test_seekbar.setProgress(current);
            }
            return false;
        }
    };

    //控件
    public RadioButton[] maogai_radioButtons = new RadioButton[4];
    public CheckBox maogai_checkBox1;
    public CheckBox maogai_checkBox2;
    public CheckBox maogai_checkBox3;
    public CheckBox maogai_checkBox4;
    private boolean[] maogai_checkedArray = new boolean[]{false, false, false, false};

    private ImageButton btn_maogai_answer;
    private ImageButton btn_maogai_collection;

    private TextView questionNum;
    private TextView tv_maogai_explaination;
    private TextView user_maogai_answer;
    private RadioGroup radioGroup_maogai;

    private SeekBar test_seekbar;

    CommonActivity commonActivity=new CommonActivity();

    DBService dbService_maogai = new DBService("question.db");
    final List<Question> maogai_list=dbService_maogai.getQuestion("MaiGai");
    Question question_maogai = maogai_list.get(0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practises_common);
        mGestureDetector=new GestureDetector(mySimpleOnGestureListener);
        mOnTouchListener= (arg0, arg1) -> {
            if (mGestureDetector.onTouchEvent(arg1)) {
                return true;
            }
            return false;
        };

        count = maogai_list.size();
        mode = 0;
        wrongMode = false;//默认情况

        tv_maogai_question = findViewById(R.id.Question);
        questionNum = findViewById(R.id.QuestionNum);
        maogai_radioButtons[0] = findViewById(R.id.answerA);
        maogai_radioButtons[1] = findViewById(R.id.answerB);
        maogai_radioButtons[2] = findViewById(R.id.answerC);
        maogai_radioButtons[3] = findViewById(R.id.answerD);
        maogai_checkBox1 = findViewById(R.id.c1);
        maogai_checkBox2 = findViewById(R.id.c2);
        maogai_checkBox3 = findViewById(R.id.c3);
        maogai_checkBox4 = findViewById(R.id.c4);

        btn_maogai_answer = findViewById(R.id.btn_answer);
        btn_maogai_collection=findViewById(R.id.btn_Collection);

        tv_maogai_explaination = findViewById(R.id.explaination);
        user_maogai_answer = findViewById(R.id.text_answer);
        radioGroup_maogai = findViewById(R.id.radioGroup);

        test_seekbar=findViewById(R.id.test_seekbar);
        test_seekbar.setMax(maogai_list.size());
        test_seekbar.setOnSeekBarChangeListener(this);

        maogai_nowAnswer = question_maogai.explaination;
        questionNum.setText("当前第1道题");
        tv_maogai_question.setText(question_maogai.question);

        maogai_radioButtons[0].setText(question_maogai.answerA);
        maogai_radioButtons[1].setText(question_maogai.answerB);
        maogai_radioButtons[2].setText(question_maogai.answerC);
        maogai_radioButtons[3].setText(question_maogai.answerD);
        maogai_checkBox1.setVisibility(View.GONE);
        maogai_checkBox2.setVisibility(View.GONE);
        maogai_checkBox3.setVisibility(View.GONE);
        maogai_checkBox4.setVisibility(View.GONE);


        //查看答案
        btn_maogai_answer.setOnClickListener(view -> {
            tv_maogai_explaination.setText(maogai_nowAnswer);
            tv_maogai_explaination.setVisibility(View.VISIBLE);
            Log.i(TAG, "BTN_Answer : " + maogai_nowAnswer);
        });

        //添加收藏
        btn_maogai_collection.setOnClickListener(v -> {
            Question collect_question = maogai_list.get(current);
            collect_question.iscollect_question=1;
            Log.i(TAG, "BTN_Collection : " + collect_question.iscollect_question);
        });


        //选择选项时更新选择
        radioGroup_maogai.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            for (int i = 0; i < 4; i++) {
                if ( maogai_radioButtons[i].isChecked() == true) {
                    maogai_list.get(current).selectedAnswer = i;
//                    if(maogai_list.get(current).selectedAnswer!=maogai_list.get(current).answer){
//                        Toast.makeText(this, "抱歉，你答错了噢", Toast.LENGTH_SHORT).show();

//                    }
                    tv_maogai_explaination.setText(maogai_list.get(current).explaination);
                    tv_maogai_explaination.setVisibility(View.VISIBLE);
                    break;
                }
            }


            Log.i("Test", "checkAnswer: " + maogai_list.get(current).question + " " + maogai_list.get(current).selectedAnswer+"     "+maogai_list.get(current).answer);

//            commonActivity.JudgeAnswer(maogai_list,current,tv_maogai_explaination);
        });
        maogai_checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            maogai_checkedArray[0] = isChecked;

            maogai_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( maogai_checkedArray);
            Log.i(TAG, "C4: T" + " " + maogai_checkedArray[0] + " " +  maogai_list.get(current).selectedAnswer);
        });
        maogai_checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            maogai_checkedArray[1] = isChecked;

            maogai_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( maogai_checkedArray);
            Log.i(TAG, "C4: T" + " " + maogai_checkedArray[1] + " " +  maogai_list.get(current).selectedAnswer);
        });
        maogai_checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            maogai_checkedArray[2] = isChecked;

            maogai_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( maogai_checkedArray);
            Log.i(TAG, "C4: T" + " " + maogai_checkedArray[2] + " " +  maogai_list.get(current).selectedAnswer);
        });
        maogai_checkBox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            maogai_checkedArray[3] = isChecked;
            maogai_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( maogai_checkedArray);
            Log.i(TAG, "C4: T" + " " + maogai_checkedArray[3] + " " +  maogai_list.get(current).selectedAnswer);
        });

        Log.i(TAG, "Judge: " + commonActivity.JudgeCheckButton(maogai_checkedArray));

    }

    public void queryCallback_Next(int number) {
//            flagAnswer=false;
        current=number;
        tv_maogai_explaination.setVisibility(View.GONE);
        if (current < count - 1) {//若当前题目不为最后一题，点击next按钮跳转到下一题；否则不响应
            current++;
            questionNumber = current + 1;
            Question question = maogai_list.get(current);
            mode = question.mode;

            maogai_nowAnswer = question.explaination;

            if (mode == 0) {

                commonActivity.UpdateSimpleQuestion(questionNum,questionNumber,tv_maogai_question,maogai_radioButtons,
                        maogai_checkBox1,maogai_checkBox2,maogai_checkBox3,maogai_checkBox4,
                        question,tv_maogai_explaination,user_maogai_answer,wrongMode);

                // 若之前已经选择过，则应记录选择
                radioGroup_maogai.clearCheck();
                if (question.selectedAnswer != -1) {
                    maogai_radioButtons[question.selectedAnswer].setChecked(true);
                }

            } else if (mode == 1) {

                commonActivity.UpdateJudgeQuestion(tv_maogai_question,questionNum,tv_maogai_explaination,user_maogai_answer,maogai_radioButtons,
                        maogai_checkBox1,maogai_checkBox2,maogai_checkBox3,maogai_checkBox4,
                        question,questionNumber,wrongMode);
                //若之前已经选择过，则应记录选择
                radioGroup_maogai.clearCheck();
                if (question.selectedAnswer != -1) {
                    maogai_radioButtons[question.selectedAnswer].setChecked(true);
                }
            } else if (mode == 2) {

                commonActivity.UpdateMutiChoiceQuestion(tv_maogai_question,questionNum,tv_maogai_explaination,user_maogai_answer,
                        maogai_radioButtons,maogai_checkBox1,maogai_checkBox2,maogai_checkBox3,maogai_checkBox4,
                        question,questionNumber,wrongMode,maogai_checkedArray);

                //若之前已经选择过，则应记录选择
                maogai_checkBox1.setChecked(false);
                maogai_checkBox2.setChecked(false);
                maogai_checkBox3.setChecked(false);
                maogai_checkBox4.setChecked(false);
                if (question.selectedAnswer != -1) {
                    commonActivity.SetCheckBox(question.selectedAnswer,maogai_checkBox1,maogai_checkBox2,maogai_checkBox3,maogai_checkBox4);
                }
            }
        }

        //错题模式的最后一题
        else if (current == count - 1 && wrongMode == true) {
            new AlertDialog.Builder(MaoGaiChapterFirst.this)
                    .setTitle("提示")
                    .setMessage("已经到达最后一题，是否退出？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MaoGaiChapterFirst.this.finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

        } else {
            //当前题目为最后一题时，告知用户作答正确的数量和作答错误的数量，并询问用户是否要查看错题
            final List<Integer> wrongList = commonActivity.checkAnswer(maogai_list);

            //收藏的题目


            //作对所有题目
            if (wrongList.size() == 0) {
                new AlertDialog.Builder(MaoGaiChapterFirst.this)
                        .setTitle("提示")
                        .setMessage("恭喜你全部回答正确！")
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            MaoGaiChapterFirst.this.finish();
                        }).show();

            } else
                new AlertDialog.Builder(MaoGaiChapterFirst.this)
                        .setTitle("提示")
                        .setMessage("您答对了" + (maogai_list.size() - wrongList.size()) +
                                "道题目；答错了" + wrongList.size() + "道题目。是否查看错题？")
                        .setPositiveButton("确定", (dialogInterface, which) -> {
                            //判断进入错题模式
                            questionNum.setText("当前第" + 1 + "道题");
                            wrongMode = true;
                            List<Question> newList = new ArrayList<>();
                            //将错误题目复制到newList中
                            for (int i = 0; i < wrongList.size(); i++) {
                                newList.add(maogai_list.get(wrongList.get(i)));
                            }
                            //将原来的list清空
                            maogai_list.clear();
                            //将错误题目添加到原来的list中
                            for (int i = 0; i < newList.size(); i++) {
                                maogai_list.add(newList.get(i));
                            }
                            int wrong_current = 0;
                            count = maogai_list.size();
                            //更新显示时的内容
                            Question q12 = maogai_list.get(wrong_current);
                            tv_maogai_explaination.setText(q12.explaination);
                            //显示解析
                            tv_maogai_explaination.setVisibility(View.VISIBLE);
                            if (q12.mode == 0) {
                                Log.i(TAG, "Mode: 0");
                                user_maogai_answer.setText(commonActivity.getUserAnswer(q12.selectedAnswer));
                                user_maogai_answer.setVisibility(View.VISIBLE);
                                tv_maogai_question.setText(q12.question);
                                maogai_radioButtons[0].setText(q12.answerA);
                                maogai_radioButtons[1].setText(q12.answerB);
                                maogai_radioButtons[2].setText(q12.answerC);
                                maogai_radioButtons[3].setText(q12.answerD);
                                maogai_radioButtons[0].setVisibility(View.VISIBLE);
                                maogai_radioButtons[1].setVisibility(View.VISIBLE);
                                maogai_radioButtons[2].setVisibility(View.VISIBLE);
                                maogai_radioButtons[3].setVisibility(View.VISIBLE);

                                maogai_checkBox1.setVisibility(View.GONE);
                                maogai_checkBox2.setVisibility(View.GONE);
                                maogai_checkBox3.setVisibility(View.GONE);
                                maogai_checkBox4.setVisibility(View.GONE);

                            } else if (q12.mode == 1) {
                                user_maogai_answer.setText(commonActivity.getUserAnswer(q12.selectedAnswer));
                                user_maogai_answer.setVisibility(View.VISIBLE);
                                Log.i(TAG, "Mode: 1");
                                tv_maogai_question.setText(q12.question);
                                maogai_radioButtons[0].setText(q12.answerA);
                                maogai_radioButtons[1].setText(q12.answerB);
                                maogai_radioButtons[0].setVisibility(View.VISIBLE);
                                maogai_radioButtons[1].setVisibility(View.VISIBLE);
                                maogai_radioButtons[2].setVisibility(View.GONE);
                                maogai_radioButtons[3].setVisibility(View.GONE);
                                maogai_checkBox1.setVisibility(View.GONE);
                                maogai_checkBox2.setVisibility(View.GONE);
                                maogai_checkBox3.setVisibility(View.GONE);
                                maogai_checkBox4.setVisibility(View.GONE);
                            } else if (mode == 2) {
                                questionNum.setText("当前第" + questionNumber + "道题");
                                tv_maogai_question.setText(q12.question);
                                user_maogai_answer.setVisibility(View.GONE);
                                maogai_radioButtons[0].setVisibility(View.GONE);
                                maogai_radioButtons[1].setVisibility(View.GONE);
                                maogai_radioButtons[2].setVisibility(View.GONE);
                                maogai_radioButtons[3].setVisibility(View.GONE);

                                maogai_checkBox1.setText(q12.answerA);
                                maogai_checkBox2.setText(q12.answerB);
                                maogai_checkBox3.setText(q12.answerC);
                                maogai_checkBox4.setText(q12.answerD);

                                maogai_checkBox1.setVisibility(View.VISIBLE);
                                maogai_checkBox2.setVisibility(View.VISIBLE);
                                maogai_checkBox3.setVisibility(View.VISIBLE);
                                maogai_checkBox4.setVisibility(View.VISIBLE);

                            }

                        })
                        .setNegativeButton("取消", (dialogInterface, which) -> {
                            //点击取消时，关闭当前activity
                            MaoGaiChapterFirst.this.finish();
                        }).show();
        }
    }

    public void queryCallback_Previous(int number){
        current=number;
        tv_maogai_explaination.setVisibility(View.GONE);
        if (current > 0)//若当前题目不为第一题，点击previous按钮跳转到上一题；否则不响应
        {
            current--;
            questionNumber = current + 1;
            Question q1 = maogai_list.get(current);
            tv_maogai_question.setText(q1.question);
            questionNum.setText("当前第" + questionNumber + "道题");

            maogai_nowAnswer = q1.explaination;

            if (q1.mode == 0) {
                Log.i(TAG, "LastMode: 0");
                maogai_radioButtons[0].setText(q1.answerA);
                maogai_radioButtons[1].setText(q1.answerB);
                maogai_radioButtons[2].setText(q1.answerC);
                maogai_radioButtons[3].setText(q1.answerD);
                maogai_radioButtons[0].setVisibility(View.VISIBLE);
                maogai_radioButtons[1].setVisibility(View.VISIBLE);
                maogai_radioButtons[2].setVisibility(View.VISIBLE);
                maogai_radioButtons[3].setVisibility(View.VISIBLE);
                maogai_checkBox1.setVisibility(View.GONE);
                maogai_checkBox2.setVisibility(View.GONE);
                maogai_checkBox3.setVisibility(View.GONE);
                maogai_checkBox4.setVisibility(View.GONE);
                if (wrongMode) {
                    tv_maogai_explaination.setText(q1.explaination);
                    tv_maogai_explaination.setVisibility(View.VISIBLE);
                    user_maogai_answer.setText(commonActivity.getUserAnswer(q1.selectedAnswer));
                    user_maogai_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "last" + " " +question_maogai.explaination);
                }

                //若之前已经选择过，则应记录选择
                radioGroup_maogai.clearCheck();
                if (q1.selectedAnswer != -1) {
                    maogai_radioButtons[q1.selectedAnswer].setChecked(true);
                }

            } else if (q1.mode == 1) {
                Log.i(TAG, "LastMode: 1");
                maogai_radioButtons[0].setText(q1.answerA);
                maogai_radioButtons[1].setText(q1.answerB);
                maogai_radioButtons[2].setText(q1.answerB);
                maogai_radioButtons[3].setText(q1.answerB);
                maogai_radioButtons[0].setVisibility(View.VISIBLE);
                maogai_radioButtons[1].setVisibility(View.VISIBLE);
                maogai_radioButtons[2].setVisibility(View.GONE);
                maogai_radioButtons[3].setVisibility(View.GONE);
                maogai_checkBox1.setVisibility(View.GONE);
                maogai_checkBox2.setVisibility(View.GONE);
                maogai_checkBox3.setVisibility(View.GONE);
                maogai_checkBox4.setVisibility(View.GONE);
                if (wrongMode) {
                    tv_maogai_explaination.setText(q1.explaination);
                    tv_maogai_explaination.setVisibility(View.VISIBLE);
                    user_maogai_answer.setText(commonActivity.getUserAnswer(q1.selectedAnswer));
                    user_maogai_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "last" + " " + question_maogai.explaination);
                }

                //若之前已经选择过，则应记录选择
                radioGroup_maogai.clearCheck();
                if (q1.selectedAnswer != -1) {
                    maogai_radioButtons[q1.selectedAnswer].setChecked(true);
                }
            } else if (mode == 2) {
                Log.i(TAG, "Judge: " + commonActivity.JudgeCheckButton(maogai_checkedArray));
                questionNum.setText("当前第" + questionNumber + "道题");
                tv_maogai_question.setText(q1.question);
                user_maogai_answer.setVisibility(View.GONE);
                maogai_radioButtons[0].setVisibility(View.GONE);
                maogai_radioButtons[1].setVisibility(View.GONE);
                maogai_radioButtons[2].setVisibility(View.GONE);
                maogai_radioButtons[3].setVisibility(View.GONE);

                maogai_checkBox1.setText(q1.answerA);
                maogai_checkBox2.setText(q1.answerB);
                maogai_checkBox3.setText(q1.answerC);
                maogai_checkBox4.setText(q1.answerD);

                maogai_checkBox1.setVisibility(View.VISIBLE);
                maogai_checkBox2.setVisibility(View.VISIBLE);
                maogai_checkBox3.setVisibility(View.VISIBLE);
                maogai_checkBox4.setVisibility(View.VISIBLE);
                if (wrongMode) {
                    tv_maogai_explaination.setText(q1.explaination);
                    tv_maogai_explaination.setVisibility(View.VISIBLE);
                    user_maogai_answer.setText(commonActivity.getUserAnswer(q1.selectedAnswer));
                    user_maogai_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "next" + " " + q1.explaination);
                }

                if (q1.selectedAnswer != -1) {
                    maogai_checkBox1.setChecked(false);
                    maogai_checkBox2.setChecked(false);
                    maogai_checkBox3.setChecked(false);
                    maogai_checkBox4.setChecked(false);
                    commonActivity.SetCheckBox(q1.selectedAnswer,maogai_checkBox1,maogai_checkBox2,maogai_checkBox3,maogai_checkBox4);

                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        this.progress=progress;
//            test_tv.setText(progress);
        queryCallback_Next(progress-1);
//        test_tv.setText(String.valueOf(current));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
//            this.progress=current;
//        test_tv.setText(String.valueOf(current));
//        test_tv.setVisibility(View.VISIBLE);

        test_seekbar.setProgress(current);

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        current=progress;
//        test_tv.setText(String.valueOf(progress));

        Log.i(TAG,"当前页为："+progress);
    }



}
