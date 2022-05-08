package com.zjxu.cc.questiondatabase;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CommonActivity extends Activity {
    private static final String TAG = "Answer";

    //单选题
    public void UpdateSimpleQuestion(TextView questionNum,int questionNumber,TextView questionView,RadioButton[] radioButton,
                               CheckBox checkBox1,CheckBox checkBox2,CheckBox checkBox3,CheckBox checkBox4,
                               Question question,TextView explaination,TextView answer, boolean wrongMode){

        questionNum.setText("当前第"+ questionNumber+"道题");
        questionView.setText(question.question);
        radioButton[0].setText(question.answerA);
        radioButton[1].setText(question.answerB);
        radioButton[2].setText(question.answerC);
        radioButton[3].setText(question.answerD);

        if(question.answerC==null&&question.answerC.length()==0){
            radioButton[2].setVisibility(View.GONE);
            if(question.answerD==null&&question.answerD.length()==0)
                radioButton[3].setVisibility(View.GONE);
        }
        checkBox1.setVisibility(View.GONE);
        checkBox2.setVisibility(View.GONE);
        checkBox3.setVisibility(View.GONE);
        checkBox4.setVisibility(View.GONE);

        if (wrongMode) {
            explaination.setText(question.explaination);
            explaination.setVisibility(View.VISIBLE);
            answer.setText(getUserAnswer(question.selectedAnswer));
            answer.setVisibility(View.VISIBLE);
            Log.i(TAG, "next" + " " + question.explaination);
        }
    }

    //判断题
    public void UpdateJudgeQuestion(TextView questionView,TextView questionNum,TextView explaination,TextView answer,RadioButton[] radioButton,
                                    CheckBox checkBox1,CheckBox checkBox2,CheckBox checkBox3,CheckBox checkBox4,
                                    Question question, int questionNumber,boolean wrongMode){

        questionNum.setText("当前第" + questionNumber + "道题");
        questionView.setText(question.question);
        radioButton[0].setText(question.answerA);
        radioButton[1].setText(question.answerB);
        radioButton[2].setVisibility(View.GONE);
        radioButton[3].setVisibility(View.GONE);
        checkBox1.setVisibility(View.GONE);
        checkBox2.setVisibility(View.GONE);
        checkBox3.setVisibility(View.GONE);
        checkBox4.setVisibility(View.GONE);

        if (wrongMode) {
            explaination.setText(question.explaination);
            explaination.setVisibility(View.VISIBLE);
            answer.setText(getUserAnswer(question.selectedAnswer));
            answer.setVisibility(View.VISIBLE);
            Log.i(TAG, "next" + " " + question.explaination);
        }
    }

    //多选题
    public void UpdateMutiChoiceQuestion(TextView questionView,TextView questionNum,TextView explaination,TextView answer,RadioButton[] radioButton,
                                         CheckBox checkBox1,CheckBox checkBox2,CheckBox checkBox3,CheckBox checkBox4,
                                         Question question, int questionNumber,boolean wrongMode,boolean[] checkedArray){

        Log.i(TAG, "Judge: " + JudgeCheckButton(checkedArray));
        questionNum.setText("当前第" + questionNumber + "道题");
        questionView.setText(question.question);
        answer.setVisibility(View.GONE);
        radioButton[0].setVisibility(View.GONE);
        radioButton[1].setVisibility(View.GONE);
        radioButton[2].setVisibility(View.GONE);
        radioButton[3].setVisibility(View.GONE);

        checkBox1.setText(question.answerA);
        checkBox2.setText(question.answerB);
        checkBox3.setText(question.answerC);
        checkBox4.setText(question.answerD);

        checkBox1.setVisibility(View.VISIBLE);
        checkBox2.setVisibility(View.VISIBLE);
        checkBox3.setVisibility(View.VISIBLE);
        checkBox4.setVisibility(View.VISIBLE);
        if (wrongMode) {
            explaination.setText(question.explaination);
            explaination.setVisibility(View.VISIBLE);
            answer.setText(getUserAnswer(question.selectedAnswer));
            answer.setVisibility(View.VISIBLE);
            Log.i(TAG, "next" + " " + question.explaination);
        }
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

    //多选题选择结果
    public void SetCheckBox(int selectAnswer,CheckBox checkBox1,CheckBox checkBox2,CheckBox checkBox3,CheckBox checkBox4){
        switch (selectAnswer){
            case 4:
                checkBox1.setChecked(true);
                checkBox2.setChecked(true);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                break;
            case 5:
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                checkBox3.setChecked(true);
                checkBox4.setChecked(false);
                break;
            case 6:
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(true);
                break;
            case 7:
                checkBox1.setChecked(false);
                checkBox2.setChecked(true);
                checkBox3.setChecked(true);
                checkBox4.setChecked(false);
                break;
            case 8:
                checkBox1.setChecked(false);
                checkBox2.setChecked(true);
                checkBox3.setChecked(false);
                checkBox4.setChecked(true);
                break;
            case 9:
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(true);
                checkBox4.setChecked(true);
                break;
            case 10:
                checkBox1.setChecked(true);
                checkBox2.setChecked(true);
                checkBox3.setChecked(true);
                checkBox4.setChecked(false);
                break;
            case 11:
                checkBox1.setChecked(true);
                checkBox2.setChecked(true);
                checkBox3.setChecked(false);
                checkBox4.setChecked(true);
                break;
            case 12:
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                checkBox3.setChecked(true);
                checkBox4.setChecked(true);
                break;
            case 13:
                checkBox1.setChecked(false);
                checkBox2.setChecked(true);
                checkBox3.setChecked(true);
                checkBox4.setChecked(true);
                break;
            case 14:
                checkBox1.setChecked(true);
                checkBox2.setChecked(true);
                checkBox3.setChecked(true);
                checkBox4.setChecked(true);
                break;
            default:
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                break;
        }
    }

    //错题模式
    public void  ErrorMode(){

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

    /*判断用户是否选择收藏该题目*/
    public List<Integer> checkCollection(List<Question> list){
        return null;
    }
}
