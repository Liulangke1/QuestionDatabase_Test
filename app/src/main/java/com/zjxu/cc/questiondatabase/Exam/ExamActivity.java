package com.zjxu.cc.questiondatabase.Exam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import androidx.core.app.NavUtils;

import com.zjxu.cc.questiondatabase.CommonActivity;
import com.zjxu.cc.questiondatabase.Question;
import com.zjxu.cc.questiondatabase.R;
import com.zjxu.cc.questiondatabase.bean.DBService;
import com.zjxu.cc.questiondatabase.bean.MyDatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class ExamActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    public final String TAG = "Answer";
    private int count;
    private int questionNumber = 1;
    private int current;
    private int mode = 0; // 单选0 多选1 判断2
    private boolean wrongMode;//标志变量，判断是否进入错题模式
    private boolean flagAnswer;

    private  String TABLE_NAME;

    private int progress=0;     //当前进度条的值

    //倒计时
    private int timeNum;
    private int singleNum;
    private int judgeNum;
    private int mutichoiceNum;
    private String ExamQuestion;

    public TextView tv_exam_question;
    public TextView tv_exam_remaining_time;
    public TextView user_exam_answer;
    public String exam_nowAnswer = "";


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
            Log.i("Gesture","向左");
            exam_seekbar.setProgress(current);
        }
        if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            queryCallback_Previous(current);
            Log.i("Gesture","向右");
            exam_seekbar.setProgress(current);
        }
        return false;
        }
    };
    public RadioButton[] exam_radioButtons = new RadioButton[4];
    public CheckBox exam_checkBox1;
    public CheckBox exam_checkBox2;
    public CheckBox exam_checkBox3;
    public CheckBox exam_checkBox4;
    private boolean[] exam_checkedArray = new boolean[]{false, false, false, false};

//    private ImageButton btn_exam_answer;

    private TextView exam_questionNum;
    private TextView tv_exam_explaination;
    private RadioGroup exam_radioGroup;
    private SeekBar exam_seekbar;
    private List<Question> list;
    private Question q;

    CommonActivity commonActivity=new CommonActivity();
    DBService dbService = new DBService("question.db");
    CountDownTimer timer;

    private MyDatabaseOpenHelper mMyDatabaseOpenHelper =new MyDatabaseOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        tv_exam_remaining_time = findViewById(R.id.tv_remaining_time);
        Intent intent = getIntent();
        timeNum = intent.getIntExtra("time", 3600);
        singleNum = intent.getIntExtra("single", 90);
        judgeNum = intent.getIntExtra("judge", 65);
        mutichoiceNum = intent.getIntExtra("mutichoice", 45);
        ExamQuestion=intent.getStringExtra("Question");

        Log.i("Question","点击的数据库："+ExamQuestion);
        list = dbService.getRandomQuestion(singleNum, judgeNum, mutichoiceNum,ExamQuestion);
        q = list.get(0);

        timer = new CountDownTimer(timeNum * 1000, 1000) {
            /**
             * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
             * @param millisUntilFinished
             */
            @Override
            public void onTick(long millisUntilFinished) {
                tv_exam_remaining_time.setText("剩余时间：" + formatTime(millisUntilFinished));
            }

            /**
             * 倒计时完成时被调用
             */
            @Override
            public void onFinish() {
                tv_exam_remaining_time.setText("时间到：00:00");
                new AlertDialog.Builder(ExamActivity.this)
                        .setTitle("提示")
                        .setMessage("时间到！ 请点击 “下一题” 查看错题")
                        .setPositiveButton("确定", (dialogInterface, which) -> {
                            current = count - 1;
                        }).show();
            }
        };
        timer.start();

        mGestureDetector=new GestureDetector(mySimpleOnGestureListener);
        mOnTouchListener= (arg0, arg1) -> {
            if (mGestureDetector.onTouchEvent(arg1)) {
                return true;
            }
            return false;
        };

        count = list.size();
        current = 0;
        mode = 0;
        wrongMode = false;//默认情况
        flagAnswer = false;//默认情况
        Log.i(TAG, "onCreate: " + "Time: " + timeNum);

        tv_exam_question = findViewById(R.id.question);
        exam_questionNum = findViewById(R.id.questionNum);
        exam_radioButtons[0] = findViewById(R.id.answerA);
        exam_radioButtons[1] = findViewById(R.id.answerB);
        exam_radioButtons[2] = findViewById(R.id.answerC);
        exam_radioButtons[3] = findViewById(R.id.answerD);
        exam_checkBox1 = findViewById(R.id.c1);
        exam_checkBox2 = findViewById(R.id.c2);
        exam_checkBox3 = findViewById(R.id.c3);
        exam_checkBox4 = findViewById(R.id.c4);

//        btn_exam_answer = findViewById(R.id.btn_answer);
//        btn_exam_answer.setVisibility(View.GONE);

        tv_exam_explaination = findViewById(R.id.explaination);
        user_exam_answer = findViewById(R.id.text_answer);
        exam_radioGroup = findViewById(R.id.radioGroup);
        exam_seekbar=findViewById(R.id.Exam_seekbar);

        //为控件赋值
        exam_nowAnswer = q.explaination;
        exam_questionNum.setText("当前第1道题");
        tv_exam_question.setText(q.question);
        exam_radioButtons[0].setText(q.answerA);
        exam_radioButtons[1].setText(q.answerB);
        exam_radioButtons[2].setText(q.answerC);
        exam_radioButtons[3].setText(q.answerD);
        exam_checkBox1.setVisibility(View.GONE);
        exam_checkBox2.setVisibility(View.GONE);
        exam_checkBox3.setVisibility(View.GONE);
        exam_checkBox4.setVisibility(View.GONE);

        exam_seekbar.setMax(list.size());
        exam_seekbar.setOnSeekBarChangeListener(this);

//        btn_exam_answer.setOnClickListener(view -> {
//            tv_exam_explaination.setText(exam_nowAnswer);
//            tv_exam_explaination.setVisibility(View.VISIBLE);
//            Log.i(TAG, "BTN_Answer : " + exam_nowAnswer);
//        });

        exam_radioGroup.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            for (int i = 0; i < 4; i++) {
                if (exam_radioButtons[i].isChecked() == true) {
                    list.get(current).selectedAnswer = i;
                    break;
                }
            }
        });
        exam_checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            exam_checkedArray[0] = isChecked;
            list.get(current).selectedAnswer = JudgeCheckButton(exam_checkedArray);
            Log.i(TAG, "C4: T" + " " + exam_checkedArray[0] + " " + list.get(current).selectedAnswer);
        });
        exam_checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            exam_checkedArray[1] = isChecked;
            list.get(current).selectedAnswer = JudgeCheckButton(exam_checkedArray);
            Log.i(TAG, "C4: T" + " " + exam_checkedArray[1] + " " + list.get(current).selectedAnswer);
        });
        exam_checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            exam_checkedArray[2] = isChecked;
            list.get(current).selectedAnswer = JudgeCheckButton(exam_checkedArray);
            Log.i(TAG, "C4: T" + " " + exam_checkedArray[2] + " " + list.get(current).selectedAnswer);
        });
        exam_checkBox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            exam_checkedArray[3] = isChecked;
            list.get(current).selectedAnswer = JudgeCheckButton(exam_checkedArray);
            Log.i(TAG, "C4: T" + " " + exam_checkedArray[3] + " " + list.get(current).selectedAnswer);
        });
        Log.i(TAG, "Judge: " + JudgeCheckButton(exam_checkedArray));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public int JudgeCheckButton(boolean[] checkedArray) {
        int select = 0;
        if (checkedArray[0] && checkedArray[1] && !checkedArray[2] && !checkedArray[3]) { //AB
            select = 4;
        } else if (checkedArray[0] && checkedArray[2] && !checkedArray[1] && !checkedArray[3]) { //AC
            select = 5;
        } else if (checkedArray[0] && checkedArray[3] && !checkedArray[1] && !checkedArray[2]) { //AD
            select = 6;
        } else if (checkedArray[1] && checkedArray[2] && !checkedArray[0] && !checkedArray[3]) { //BC
            select = 7;
        } else if (checkedArray[1] && checkedArray[3] && !checkedArray[0] && !checkedArray[2]) { //BD
            select = 8;
        } else if (checkedArray[2] && checkedArray[3] && !checkedArray[0] && !checkedArray[1]) { //CD
            select = 9;
        } else if (checkedArray[0] && checkedArray[1] && checkedArray[2] && !checkedArray[3]) { //ABC
            select = 10;
        } else if (checkedArray[0] && checkedArray[1] && checkedArray[3] && !checkedArray[2]) { //ABD
            select = 11;
        } else if (checkedArray[0] && checkedArray[2] && checkedArray[3] && !checkedArray[1]) { //ACD
            select = 12;
        } else if (checkedArray[1] && checkedArray[2] && checkedArray[3] && !checkedArray[0]) { //BCD
            select = 13;
        } else if (checkedArray[0] && checkedArray[1] && checkedArray[2] && checkedArray[3]) { //BCD
            select = 14;
        }
        Log.i(TAG, "JudgeCheckButton: " + select);
        return select;
    }

    public String getUserAnswer(int answer) {
        if (answer == 0)
            return "你的答案： A";
        else if (answer == 1)
            return "你的答案： B";
        else if (answer == 2)
            return "你的答案： C";
        else if (answer == 3)
            return "你的答案： D";
        else if (answer == 4)
            return "你的答案： AB";
        else if (answer == 5)
            return "你的答案： AC";
        else if (answer == 6)
            return "你的答案： AD";
        else if (answer == 7)
            return "你的答案： BC";
        else if (answer == 8)
            return "你的答案： BD";
        else if (answer == 9)
            return "你的答案： CD";
        else if (answer == 10)
            return "你的答案： ABC";
        else if (answer == 11)
            return "你的答案： ABD";
        else if (answer == 12)
            return "你的答案： ACD";
        else if (answer == 13)
            return "你的答案： BCD";
        else if (answer == 14)
            return "你的答案： ABCD";
        return "你的答案：空";
    }

    /*判断用户作答是否正确，并将作答错误题目的下标生成list,返回给调用者*/
    public List<Integer> checkAnswer(List<Question> list) {
        List<Integer> wrongList = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).answer != list.get(i).selectedAnswer) {
                wrongList.add(i);
                Log.i(TAG, "checkAnswer: " + list.get(i).question + " " + list.get(i).selectedAnswer);
            }
        }
        return wrongList;
    }

    public void queryCallback_Next(int number) {
        current=number;
        tv_exam_explaination.setVisibility(View.GONE);
        if (current < count - 1) {//若当前题目不为最后一题，点击next按钮跳转到下一题；否则不响应
            current++;
            questionNumber = current + 1;
            Question question = list.get(current);
            mode = question.mode;
            exam_nowAnswer = question.explaination;

            if (mode == 0) {

                commonActivity.UpdateSimpleQuestion(exam_questionNum,questionNumber,tv_exam_question,exam_radioButtons,
                        exam_checkBox1,exam_checkBox2,exam_checkBox3,exam_checkBox4,
                        question,tv_exam_explaination,user_exam_answer,wrongMode);
                // 若之前已经选择过，则应记录选择
                exam_radioGroup.clearCheck();
                if (question.selectedAnswer != -1) {
                    exam_radioButtons[question.selectedAnswer].setChecked(true);
                }
            } else if (mode == 1) {
                commonActivity.UpdateJudgeQuestion(tv_exam_question,exam_questionNum,tv_exam_explaination,user_exam_answer,exam_radioButtons,
                        exam_checkBox1,exam_checkBox2,exam_checkBox3,exam_checkBox4,
                        question,questionNumber,wrongMode);
                //若之前已经选择过，则应记录选择
                exam_radioGroup.clearCheck();
                if (question.selectedAnswer != -1) {
                    exam_radioButtons[question.selectedAnswer].setChecked(true);
                }
            } else if (mode == 2) {

//                btn_exam_answer.setVisibility(View.VISIBLE);
                commonActivity.UpdateMutiChoiceQuestion(tv_exam_question,exam_questionNum,tv_exam_explaination,user_exam_answer,
                        exam_radioButtons,exam_checkBox1,exam_checkBox2,exam_checkBox3,exam_checkBox4,
                        question,questionNumber,wrongMode,exam_checkedArray);

                //若之前已经选择过，则应记录选择
                exam_checkBox1.setChecked(false);
                exam_checkBox2.setChecked(false);
                exam_checkBox3.setChecked(false);
                exam_checkBox4.setChecked(false);
                if (question.selectedAnswer != -1) {
                    commonActivity.SetCheckBox(question.selectedAnswer,exam_checkBox1,exam_checkBox2,exam_checkBox3,exam_checkBox4);
                }
            }
        }
        //错题模式的最后一题
        else if (current == count - 1 && wrongMode == true) {
            if(!ExamActivity.this.isFinishing()){
                new AlertDialog.Builder(ExamActivity.this)
                        .setTitle("提示")
                        .setMessage("已经到达最后一题，是否退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ExamActivity.this.finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        } else {
            //当前题目为最后一题时，告知用户作答正确的数量和作答错误的数量，并询问用户是否要查看错题
            final List<Integer> wrongList = checkAnswer(list);
            //作对所有题目
            if (wrongList.size() == 0) {
                new AlertDialog.Builder(ExamActivity.this)
                        .setTitle("提示")
                        .setMessage("恭喜你全部回答正确！")
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            ExamActivity.this.finish();
                        }).show();
            } else
                new AlertDialog.Builder(ExamActivity.this)
                        .setTitle("提示")
                        .setMessage("您答对了" + (list.size() - wrongList.size()) +
                                "道题目；答错了" + wrongList.size() + "道题目。是否查看错题？")
                        .setPositiveButton("确定", (dialogInterface, which) -> {
                            //判断进入错题模式
                            exam_questionNum.setText("当前第" + 1 + "道题");
                            wrongMode = true;
                            List<Question> newList = new ArrayList<>();
                            //将错误题目复制到newList中
                            for (int i = 0; i < wrongList.size(); i++) {
                                newList.add(list.get(wrongList.get(i)));
                            }
                            //将原来的list清空
                            list.clear();
                            //将错误题目添加到原来的list中
                            for (int i = 0; i < newList.size(); i++) {
                                list.add(newList.get(i));
                            }
                            int wrong_current = 0;
                            count = list.size();
                            //更新显示时的内容
                            Question q12 = list.get(wrong_current);
                            tv_exam_explaination.setText(q12.explaination);
                            //显示解析
                            tv_exam_explaination.setVisibility(View.VISIBLE);
                            if (q12.mode == 0) {
                                Log.i(TAG, "Mode: 0");
                                user_exam_answer.setText(getUserAnswer(q12.selectedAnswer));
                                user_exam_answer.setVisibility(View.VISIBLE);
                                tv_exam_question.setText(q12.question);
                                exam_radioButtons[0].setText(q12.answerA);
                                exam_radioButtons[1].setText(q12.answerB);
                                exam_radioButtons[2].setText(q12.answerC);
                                exam_radioButtons[3].setText(q12.answerD);
                                exam_radioButtons[0].setVisibility(View.VISIBLE);
                                exam_radioButtons[1].setVisibility(View.VISIBLE);
                                exam_radioButtons[2].setVisibility(View.VISIBLE);
                                exam_radioButtons[3].setVisibility(View.VISIBLE);

                                exam_checkBox1.setVisibility(View.GONE);
                                exam_checkBox2.setVisibility(View.GONE);
                                exam_checkBox3.setVisibility(View.GONE);
                                exam_checkBox4.setVisibility(View.GONE);

                            } else if (q12.mode == 1) {
                                user_exam_answer.setText(getUserAnswer(q12.selectedAnswer));
                                user_exam_answer.setVisibility(View.VISIBLE);
                                Log.i(TAG, "Mode: 1");
                                tv_exam_question.setText(q12.question);
                                exam_radioButtons[0].setText(q12.answerA);
                                exam_radioButtons[1].setText(q12.answerB);
                                exam_radioButtons[0].setVisibility(View.VISIBLE);
                                exam_radioButtons[1].setVisibility(View.VISIBLE);
                                exam_radioButtons[2].setVisibility(View.GONE);
                                exam_radioButtons[3].setVisibility(View.GONE);
                                exam_checkBox1.setVisibility(View.GONE);
                                exam_checkBox2.setVisibility(View.GONE);
                                exam_checkBox3.setVisibility(View.GONE);
                                exam_checkBox4.setVisibility(View.GONE);
                            } else if (mode == 2) {
                                exam_questionNum.setText("当前第" + questionNumber + "道题");
                                tv_exam_question.setText(q12.question);
                                user_exam_answer.setVisibility(View.GONE);
                                exam_radioButtons[0].setVisibility(View.GONE);
                                exam_radioButtons[1].setVisibility(View.GONE);
                                exam_radioButtons[2].setVisibility(View.GONE);
                                exam_radioButtons[3].setVisibility(View.GONE);

                                exam_checkBox1.setText(q12.answerA);
                                exam_checkBox2.setText(q12.answerB);
                                exam_checkBox3.setText(q12.answerC);
                                exam_checkBox4.setText(q12.answerD);

                                exam_checkBox1.setVisibility(View.VISIBLE);
                                exam_checkBox2.setVisibility(View.VISIBLE);
                                exam_checkBox3.setVisibility(View.VISIBLE);
                                exam_checkBox4.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("取消", (dialogInterface, which) -> {
                            //点击取消时，关闭当前activity
                            ExamActivity.this.finish();
                        }).show();
        }
    }

    public void queryCallback_Previous(int number){
        current=number;
        tv_exam_explaination.setVisibility(View.GONE);
        if (current > 0)//若当前题目不为第一题，点击previous按钮跳转到上一题；否则不响应
        {
            current--;
            questionNumber = current + 1;
            Question q1 = list.get(current);
            tv_exam_question.setText(q1.question);
            exam_questionNum.setText("当前第" + questionNumber + "道题");
            exam_nowAnswer = q1.explaination;
            if (q1.mode == 0) {
                Log.i(TAG, "LastMode: 0");
                exam_radioButtons[0].setText(q1.answerA);
                exam_radioButtons[1].setText(q1.answerB);
                exam_radioButtons[2].setText(q1.answerC);
                exam_radioButtons[3].setText(q1.answerD);
                exam_radioButtons[0].setVisibility(View.VISIBLE);
                exam_radioButtons[1].setVisibility(View.VISIBLE);
                exam_radioButtons[2].setVisibility(View.VISIBLE);
                exam_radioButtons[3].setVisibility(View.VISIBLE);
                exam_checkBox1.setVisibility(View.GONE);
                exam_checkBox2.setVisibility(View.GONE);
                exam_checkBox3.setVisibility(View.GONE);
                exam_checkBox4.setVisibility(View.GONE);
                if (wrongMode) {
                    tv_exam_explaination.setText(q1.explaination);
                    tv_exam_explaination.setVisibility(View.VISIBLE);
                    user_exam_answer.setText(getUserAnswer(q1.selectedAnswer));
                    user_exam_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "last" + " " +q.explaination);
                }

                //若之前已经选择过，则应记录选择
                exam_radioGroup.clearCheck();
                if (q1.selectedAnswer != -1) {
                    exam_radioButtons[q1.selectedAnswer].setChecked(true);
                }

            } else if (q1.mode == 1) {
                Log.i(TAG, "LastMode: 1");
                exam_radioButtons[0].setText(q1.answerA);
                exam_radioButtons[1].setText(q1.answerB);
                exam_radioButtons[2].setText(q1.answerB);
                exam_radioButtons[3].setText(q1.answerB);
                exam_radioButtons[0].setVisibility(View.VISIBLE);
                exam_radioButtons[1].setVisibility(View.VISIBLE);
                exam_radioButtons[2].setVisibility(View.GONE);
                exam_radioButtons[3].setVisibility(View.GONE);
                exam_checkBox1.setVisibility(View.GONE);
                exam_checkBox2.setVisibility(View.GONE);
                exam_checkBox3.setVisibility(View.GONE);
                exam_checkBox4.setVisibility(View.GONE);
                if (wrongMode) {
                    tv_exam_explaination.setText(q1.explaination);
                    tv_exam_explaination.setVisibility(View.VISIBLE);
                    user_exam_answer.setText(getUserAnswer(q1.selectedAnswer));
                    user_exam_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "last" + " " + q.explaination);
                }

                //若之前已经选择过，则应记录选择
                exam_radioGroup.clearCheck();
                if (q1.selectedAnswer != -1) {
                    exam_radioButtons[q1.selectedAnswer].setChecked(true);
                }
            } else if (mode == 2) {
                Log.i(TAG, "Judge: " +JudgeCheckButton(exam_checkedArray));
                exam_questionNum.setText("当前第" + questionNumber + "道题");
                tv_exam_question.setText(q1.question);
                user_exam_answer.setVisibility(View.GONE);
                exam_radioButtons[0].setVisibility(View.GONE);
                exam_radioButtons[1].setVisibility(View.GONE);
                exam_radioButtons[2].setVisibility(View.GONE);
                exam_radioButtons[3].setVisibility(View.GONE);

                exam_checkBox1.setText(q1.answerA);
                exam_checkBox2.setText(q1.answerB);
                exam_checkBox3.setText(q1.answerC);
                exam_checkBox4.setText(q1.answerD);

                exam_checkBox1.setVisibility(View.VISIBLE);
                exam_checkBox2.setVisibility(View.VISIBLE);
                exam_checkBox3.setVisibility(View.VISIBLE);
                exam_checkBox4.setVisibility(View.VISIBLE);
                if (wrongMode) {
                    tv_exam_explaination.setText(q1.explaination);
                    tv_exam_explaination.setVisibility(View.VISIBLE);
                    user_exam_answer.setText(getUserAnswer(q1.selectedAnswer));
                    user_exam_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "next" + " " + q1.explaination);
                }
                if (q1.selectedAnswer != -1) {
                    exam_checkBox1.setChecked(false);
                    exam_checkBox2.setChecked(false);
                    exam_checkBox3.setChecked(false);
                    exam_checkBox4.setChecked(false);
                    commonActivity.SetCheckBox(q1.selectedAnswer,exam_checkBox1,exam_checkBox2,exam_checkBox3,exam_checkBox4);
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
        queryCallback_Next(progress-1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        exam_seekbar.setProgress(current);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        current=progress;
        Log.i(TAG,"当前页为："+progress);
    }

    public String formatTime(long millisecond) {
        int minute;//分钟
        int second;//秒数
        minute = (int) ((millisecond / 1000) / 60);
        second = (int) ((millisecond / 1000) % 60);
        if (minute < 10) {
            if (second < 10) {
                return "0" + minute + ":" + "0" + second;
            } else {
                return "0" + minute + ":" + second;
            }
        } else {
            if (second < 10) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }
    public void onBackPressed() {
        NavUtils.navigateUpTo(this, new Intent(this, ExamMainActivity.class));
        timerCancel();
        finish();
    }
    public void timerCancel() {
        timer.cancel();
    }
}
