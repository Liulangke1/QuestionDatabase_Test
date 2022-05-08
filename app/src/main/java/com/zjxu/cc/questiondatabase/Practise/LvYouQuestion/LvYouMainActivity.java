package com.zjxu.cc.questiondatabase.Practise.LvYouQuestion;

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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zjxu.cc.questiondatabase.CommonActivity;
import com.zjxu.cc.questiondatabase.Question;
import com.zjxu.cc.questiondatabase.R;
import com.zjxu.cc.questiondatabase.bean.DBService;
import com.zjxu.cc.questiondatabase.bean.MyDatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LvYouMainActivity extends Activity   implements SeekBar.OnSeekBarChangeListener {


    public final String TAG = "Answer";
    private int count;
    private int questionNumber = 1;
    private int current=0;
    private int mode = 0; // 单选0 多选1 判断2
    private boolean wrongMode;//标志变量，判断是否进入错题模式

    private int progress=0;     //当前进度条的值

    public TextView tv_lvyou_question;
    public String lvyou_nowAnswer = "";

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
    public RadioButton[] lvyou_radioButtons = new RadioButton[4];
    public CheckBox lvyou_checkBox1;
    public CheckBox lvyou_checkBox2;
    public CheckBox lvyou_checkBox3;
    public CheckBox lvyou_checkBox4;
    private boolean[] lvyou_checkedArray = new boolean[]{false, false, false, false};


    private ImageButton btn_lvyou_answer;
    private ImageButton btn_lvyou_collection;

    private TextView questionNum;
    private TextView tv_lvyou_explaination;
    private TextView user_lvyou_answer;
    private RadioGroup radioGroup_lvyou;

    private SeekBar test_seekbar;

    CommonActivity commonActivity=new CommonActivity();
    DBService dbService_lvyou = new DBService("question.db");
    final List<Question> lvyou_list=dbService_lvyou.getQuestion("LvYou");
    Question question_lvyou = lvyou_list.get(0);
    private MyDatabaseOpenHelper mMyDatabaseOpenHelper;

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

        count = lvyou_list.size();
        mode = 0;
        wrongMode = false;//默认情况

        tv_lvyou_question = findViewById(R.id.Question);
        questionNum = findViewById(R.id.QuestionNum);
        lvyou_radioButtons[0] = findViewById(R.id.answerA);
        lvyou_radioButtons[1] = findViewById(R.id.answerB);
        lvyou_radioButtons[2] = findViewById(R.id.answerC);
        lvyou_radioButtons[3] = findViewById(R.id.answerD);
        lvyou_checkBox1 = findViewById(R.id.c1);
        lvyou_checkBox2 = findViewById(R.id.c2);
        lvyou_checkBox3 = findViewById(R.id.c3);
        lvyou_checkBox4 = findViewById(R.id.c4);

        btn_lvyou_answer = findViewById(R.id.btn_answer);
        btn_lvyou_answer.setVisibility(View.GONE);
        btn_lvyou_collection=findViewById(R.id.btn_Collection);

        tv_lvyou_explaination = findViewById(R.id.explaination);
        user_lvyou_answer = findViewById(R.id.text_answer);
        radioGroup_lvyou = findViewById(R.id.radioGroup);

        test_seekbar=findViewById(R.id.test_seekbar);
        test_seekbar.setMax(lvyou_list.size());
        test_seekbar.setOnSeekBarChangeListener(this);

        //为控件赋值
        lvyou_nowAnswer = question_lvyou.explaination;
        questionNum.setText("当前第1道题");
        tv_lvyou_question.setText(question_lvyou.question);

        lvyou_radioButtons[0].setText(question_lvyou.answerA);
        lvyou_radioButtons[1].setText(question_lvyou.answerB);
        lvyou_radioButtons[2].setText(question_lvyou.answerC);
        lvyou_radioButtons[3].setText(question_lvyou.answerD);
        lvyou_checkBox1.setVisibility(View.GONE);
        lvyou_checkBox2.setVisibility(View.GONE);
        lvyou_checkBox3.setVisibility(View.GONE);
        lvyou_checkBox4.setVisibility(View.GONE);

        //查看答案
        btn_lvyou_answer.setOnClickListener(view -> {
            tv_lvyou_explaination.setText(lvyou_nowAnswer);
            tv_lvyou_explaination.setVisibility(View.VISIBLE);
            Log.i(TAG, "BTN_Answer : " + lvyou_nowAnswer);
        });
        //添加收藏
        btn_lvyou_collection.setOnClickListener(v -> {
            mMyDatabaseOpenHelper=new MyDatabaseOpenHelper(this);
            Question collect_question = lvyou_list.get(current);
            if (collect_question.iscollect_question==0){
                collect_question.iscollect_question=1;
                long rowid=mMyDatabaseOpenHelper.addData(collect_question,collect_question.mode,"LvYou");
                if(rowid!=-1){
                    Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this,"收藏失败",Toast.LENGTH_SHORT).show();
                }
            }else if (collect_question.iscollect_question==1){
                collect_question.iscollect_question=0;
                mMyDatabaseOpenHelper.delete(collect_question,collect_question.mode,"LvYou");
                Log.i("Log","---------删除成功----------");
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
        //选择选项时更新选择
        radioGroup_lvyou.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            for (int i = 0; i < 4; i++) {
                if ( lvyou_radioButtons[i].isChecked() == true) {
                    lvyou_list.get(current).selectedAnswer = i;
                    if(lvyou_list.get(current).selectedAnswer!=lvyou_list.get(current).answer){
                        Toast.makeText(this, "抱歉，你答错了噢", Toast.LENGTH_SHORT).show();
                    }
                    tv_lvyou_explaination.setText(lvyou_list.get(current).explaination);
                    tv_lvyou_explaination.setVisibility(View.VISIBLE);
                    break;
                }
            }
            Log.i("Test", "checkAnswer: " + lvyou_list.get(current).question + " " + lvyou_list.get(current).selectedAnswer+"     "+lvyou_list.get(current).answer);
        });
        lvyou_checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lvyou_checkedArray[0] = isChecked;
            lvyou_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( lvyou_checkedArray);
            Log.i(TAG, "C4: T" + " " + lvyou_checkedArray[0] + " " +  lvyou_list.get(current).selectedAnswer);
        });
        lvyou_checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lvyou_checkedArray[1] = isChecked;
            lvyou_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( lvyou_checkedArray);
            Log.i(TAG, "C4: T" + " " + lvyou_checkedArray[1] + " " +  lvyou_list.get(current).selectedAnswer);
        });
        lvyou_checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lvyou_checkedArray[2] = isChecked;
            lvyou_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( lvyou_checkedArray);
            Log.i(TAG, "C4: T" + " " + lvyou_checkedArray[2] + " " +  lvyou_list.get(current).selectedAnswer);
        });
        lvyou_checkBox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lvyou_checkedArray[3] = isChecked;
            lvyou_list.get(current).selectedAnswer = commonActivity.JudgeCheckButton( lvyou_checkedArray);
            Log.i(TAG, "C4: T" + " " + lvyou_checkedArray[3] + " " +  lvyou_list.get(current).selectedAnswer);
        });
        Log.i(TAG, "Judge: " + commonActivity.JudgeCheckButton(lvyou_checkedArray));
    }

    public void queryCallback_Next(int number) {
        current=number;
        tv_lvyou_explaination.setVisibility(View.GONE);
        if (current < count - 1) {//若当前题目不为最后一题，点击next按钮跳转到下一题；否则不响应
            current++;
            questionNumber = current + 1;
            Question question = lvyou_list.get(current);
            mode = question.mode;

            lvyou_nowAnswer = question.explaination;
            if (mode == 0) {
                commonActivity.UpdateSimpleQuestion(questionNum,questionNumber,tv_lvyou_question,lvyou_radioButtons,
                        lvyou_checkBox1,lvyou_checkBox2,lvyou_checkBox3,lvyou_checkBox4,
                        question,tv_lvyou_explaination,user_lvyou_answer,wrongMode);

                // 若之前已经选择过，则应记录选择
                radioGroup_lvyou.clearCheck();
                if (question.selectedAnswer != -1) {
                    lvyou_radioButtons[question.selectedAnswer].setChecked(true);
                }

            } else if (mode == 1) {
                commonActivity.UpdateJudgeQuestion(tv_lvyou_question,questionNum,tv_lvyou_explaination,user_lvyou_answer,lvyou_radioButtons,
                        lvyou_checkBox1,lvyou_checkBox2,lvyou_checkBox3,lvyou_checkBox4,
                        question,questionNumber,wrongMode);
                //若之前已经选择过，则应记录选择
                radioGroup_lvyou.clearCheck();
                if (question.selectedAnswer != -1) {
                    lvyou_radioButtons[question.selectedAnswer].setChecked(true);
                }
            } else if (mode == 2) {
                btn_lvyou_answer.setVisibility(View.VISIBLE);
                commonActivity.UpdateMutiChoiceQuestion(tv_lvyou_question,questionNum,tv_lvyou_explaination,user_lvyou_answer,
                        lvyou_radioButtons,lvyou_checkBox1,lvyou_checkBox2,lvyou_checkBox3,lvyou_checkBox4,
                        question,questionNumber,wrongMode,lvyou_checkedArray);

                //若之前已经选择过，则应记录选择
                lvyou_checkBox1.setChecked(false);
                lvyou_checkBox2.setChecked(false);
                lvyou_checkBox3.setChecked(false);
                lvyou_checkBox4.setChecked(false);
                if (question.selectedAnswer != -1) {
                    commonActivity.SetCheckBox(question.selectedAnswer,lvyou_checkBox1,lvyou_checkBox2,lvyou_checkBox3,lvyou_checkBox4);
                }
            }
        }

        //错题模式的最后一题
        else if (current == count - 1 && wrongMode == true) {
            new AlertDialog.Builder(LvYouMainActivity.this)
                    .setTitle("提示")
                    .setMessage("已经到达最后一题，是否退出？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LvYouMainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

        } else {
            //当前题目为最后一题时，告知用户作答正确的数量和作答错误的数量，并询问用户是否要查看错题
            final List<Integer> wrongList = commonActivity.checkAnswer(lvyou_list);
            //作对所有题目
            if (wrongList.size() == 0) {
                new AlertDialog.Builder(LvYouMainActivity.this)
                        .setTitle("提示")
                        .setMessage("恭喜你全部回答正确！")
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            LvYouMainActivity.this.finish();
                        }).show();

            } else
                new AlertDialog.Builder(LvYouMainActivity.this)
                        .setTitle("提示")
                        .setMessage("您答对了" + (lvyou_list.size() - wrongList.size()) +
                                "道题目；答错了" + wrongList.size() + "道题目。是否查看错题？")
                        .setPositiveButton("确定", (dialogInterface, which) -> {
                            //判断进入错题模式
                            questionNum.setText("当前第" + 1 + "道题");
                            wrongMode = true;
                            List<Question> newList = new ArrayList<>();
                            //将错误题目复制到newList中
                            for (int i = 0; i < wrongList.size(); i++) {
                                newList.add(lvyou_list.get(wrongList.get(i)));
                            }
                            //将原来的list清空
                            lvyou_list.clear();
                            //将错误题目添加到原来的list中
                            for (int i = 0; i < newList.size(); i++) {
                                lvyou_list.add(newList.get(i));
                            }
                            int wrong_current = 0;
                            count = lvyou_list.size();
                            //更新显示时的内容
                            Question q12 = lvyou_list.get(wrong_current);
                            tv_lvyou_explaination.setText(q12.explaination);
                            //显示解析
                            tv_lvyou_explaination.setVisibility(View.VISIBLE);
                            if (q12.mode == 0) {
                                Log.i(TAG, "Mode: 0");
                                user_lvyou_answer.setText(commonActivity.getUserAnswer(q12.selectedAnswer));
                                user_lvyou_answer.setVisibility(View.VISIBLE);
                                tv_lvyou_question.setText(q12.question);
                                lvyou_radioButtons[0].setText(q12.answerA);
                                lvyou_radioButtons[1].setText(q12.answerB);
                                lvyou_radioButtons[2].setText(q12.answerC);
                                lvyou_radioButtons[3].setText(q12.answerD);
                                lvyou_radioButtons[0].setVisibility(View.VISIBLE);
                                lvyou_radioButtons[1].setVisibility(View.VISIBLE);
                                lvyou_radioButtons[2].setVisibility(View.VISIBLE);
                                lvyou_radioButtons[3].setVisibility(View.VISIBLE);

                                lvyou_checkBox1.setVisibility(View.GONE);
                                lvyou_checkBox2.setVisibility(View.GONE);
                                lvyou_checkBox3.setVisibility(View.GONE);
                                lvyou_checkBox4.setVisibility(View.GONE);

                            } else if (q12.mode == 1) {
                                user_lvyou_answer.setText(commonActivity.getUserAnswer(q12.selectedAnswer));
                                user_lvyou_answer.setVisibility(View.VISIBLE);
                                Log.i(TAG, "Mode: 1");
                                tv_lvyou_question.setText(q12.question);
                                lvyou_radioButtons[0].setText(q12.answerA);
                                lvyou_radioButtons[1].setText(q12.answerB);
                                lvyou_radioButtons[0].setVisibility(View.VISIBLE);
                                lvyou_radioButtons[1].setVisibility(View.VISIBLE);
                                lvyou_radioButtons[2].setVisibility(View.GONE);
                                lvyou_radioButtons[3].setVisibility(View.GONE);
                                lvyou_checkBox1.setVisibility(View.GONE);
                                lvyou_checkBox2.setVisibility(View.GONE);
                                lvyou_checkBox3.setVisibility(View.GONE);
                                lvyou_checkBox4.setVisibility(View.GONE);
                            } else if (mode == 2) {
                                questionNum.setText("当前第" + questionNumber + "道题");
                                tv_lvyou_question.setText(q12.question);
                                user_lvyou_answer.setVisibility(View.GONE);
                                lvyou_radioButtons[0].setVisibility(View.GONE);
                                lvyou_radioButtons[1].setVisibility(View.GONE);
                                lvyou_radioButtons[2].setVisibility(View.GONE);
                                lvyou_radioButtons[3].setVisibility(View.GONE);

                                lvyou_checkBox1.setText(q12.answerA);
                                lvyou_checkBox2.setText(q12.answerB);
                                lvyou_checkBox3.setText(q12.answerC);
                                lvyou_checkBox4.setText(q12.answerD);

                                lvyou_checkBox1.setVisibility(View.VISIBLE);
                                lvyou_checkBox2.setVisibility(View.VISIBLE);
                                lvyou_checkBox3.setVisibility(View.VISIBLE);
                                lvyou_checkBox4.setVisibility(View.VISIBLE);

                            }

                        })
                        .setNegativeButton("取消", (dialogInterface, which) -> {
                            //点击取消时，关闭当前activity
                            LvYouMainActivity.this.finish();
                        }).show();
        }
    }

    public void queryCallback_Previous(int number){
        current=number;
        tv_lvyou_explaination.setVisibility(View.GONE);
        if (current > 0)//若当前题目不为第一题，点击previous按钮跳转到上一题；否则不响应
        {
            current--;
            questionNumber = current + 1;
            Question q1 = lvyou_list.get(current);
            tv_lvyou_question.setText(q1.question);
            questionNum.setText("当前第" + questionNumber + "道题");

            lvyou_nowAnswer = q1.explaination;
            if (q1.mode == 0) {
                Log.i(TAG, "LastMode: 0");
                lvyou_radioButtons[0].setText(q1.answerA);
                lvyou_radioButtons[1].setText(q1.answerB);
                lvyou_radioButtons[2].setText(q1.answerC);
                lvyou_radioButtons[3].setText(q1.answerD);
                lvyou_radioButtons[0].setVisibility(View.VISIBLE);
                lvyou_radioButtons[1].setVisibility(View.VISIBLE);
                lvyou_radioButtons[2].setVisibility(View.VISIBLE);
                lvyou_radioButtons[3].setVisibility(View.VISIBLE);
                lvyou_checkBox1.setVisibility(View.GONE);
                lvyou_checkBox2.setVisibility(View.GONE);
                lvyou_checkBox3.setVisibility(View.GONE);
                lvyou_checkBox4.setVisibility(View.GONE);
                if (wrongMode) {
                    tv_lvyou_explaination.setText(q1.explaination);
                    tv_lvyou_explaination.setVisibility(View.VISIBLE);
                    user_lvyou_answer.setText(commonActivity.getUserAnswer(q1.selectedAnswer));
                    user_lvyou_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "last" + " " +question_lvyou.explaination);
                }
                //若之前已经选择过，则应记录选择
                radioGroup_lvyou.clearCheck();
                if (q1.selectedAnswer != -1) {
                    lvyou_radioButtons[q1.selectedAnswer].setChecked(true);
                }
            } else if (q1.mode == 1) {
                Log.i(TAG, "LastMode: 1");
                lvyou_radioButtons[0].setText(q1.answerA);
                lvyou_radioButtons[1].setText(q1.answerB);
                lvyou_radioButtons[2].setText(q1.answerB);
                lvyou_radioButtons[3].setText(q1.answerB);
                lvyou_radioButtons[0].setVisibility(View.VISIBLE);
                lvyou_radioButtons[1].setVisibility(View.VISIBLE);
                lvyou_radioButtons[2].setVisibility(View.GONE);
                lvyou_radioButtons[3].setVisibility(View.GONE);
                lvyou_checkBox1.setVisibility(View.GONE);
                lvyou_checkBox2.setVisibility(View.GONE);
                lvyou_checkBox3.setVisibility(View.GONE);
                lvyou_checkBox4.setVisibility(View.GONE);
                if (wrongMode) {
                    tv_lvyou_explaination.setText(q1.explaination);
                    tv_lvyou_explaination.setVisibility(View.VISIBLE);
                    user_lvyou_answer.setText(commonActivity.getUserAnswer(q1.selectedAnswer));
                    user_lvyou_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "last" + " " + question_lvyou.explaination);
                }
                //若之前已经选择过，则应记录选择
                radioGroup_lvyou.clearCheck();
                if (q1.selectedAnswer != -1) {
                    lvyou_radioButtons[q1.selectedAnswer].setChecked(true);
                }
            } else if (mode == 2) {
                Log.i(TAG, "Judge: " + commonActivity.JudgeCheckButton(lvyou_checkedArray));
                questionNum.setText("当前第" + questionNumber + "道题");
                tv_lvyou_question.setText(q1.question);
                user_lvyou_answer.setVisibility(View.GONE);
                lvyou_radioButtons[0].setVisibility(View.GONE);
                lvyou_radioButtons[1].setVisibility(View.GONE);
                lvyou_radioButtons[2].setVisibility(View.GONE);
                lvyou_radioButtons[3].setVisibility(View.GONE);

                lvyou_checkBox1.setText(q1.answerA);
                lvyou_checkBox2.setText(q1.answerB);
                lvyou_checkBox3.setText(q1.answerC);
                lvyou_checkBox4.setText(q1.answerD);

                lvyou_checkBox1.setVisibility(View.VISIBLE);
                lvyou_checkBox2.setVisibility(View.VISIBLE);
                lvyou_checkBox3.setVisibility(View.VISIBLE);
                lvyou_checkBox4.setVisibility(View.VISIBLE);
                if (wrongMode) {
                    tv_lvyou_explaination.setText(q1.explaination);
                    tv_lvyou_explaination.setVisibility(View.VISIBLE);
                    user_lvyou_answer.setText(commonActivity.getUserAnswer(q1.selectedAnswer));
                    user_lvyou_answer.setVisibility(View.VISIBLE);
                    Log.i(TAG, "next" + " " + q1.explaination);
                }
                if (q1.selectedAnswer != -1) {
                    lvyou_checkBox1.setChecked(false);
                    lvyou_checkBox2.setChecked(false);
                    lvyou_checkBox3.setChecked(false);
                    lvyou_checkBox4.setChecked(false);
                    commonActivity.SetCheckBox(q1.selectedAnswer,lvyou_checkBox1,lvyou_checkBox2,lvyou_checkBox3,lvyou_checkBox4);
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
        test_seekbar.setProgress(current);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        current=progress;
        Log.i(TAG,"当前页为："+progress);
    }
}
