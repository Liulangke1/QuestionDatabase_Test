package com.zjxu.cc.questiondatabase.bean;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.zjxu.cc.questiondatabase.Question;

import java.util.ArrayList;
import java.util.List;

public class DBService {
    private SQLiteDatabase db;
    private String title;
    private String ExamTitle;
    private String DB_TABLE1;
    private String DB_TABLE2;
    private String DB_TABLE3;
    private String DB_TABLE=null;
    public final String TAG = "Answer";

    public DBService(Context context,String dbname) {
        db = openOrCreateDatabase(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/databases/" +dbname+
                "", null);
    }

    //在构造函数中打开指定数据库，并把它的引用指向db
    public DBService(String dbname){
        db = openOrCreateDatabase(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/databases/" +dbname+
                "", null);

    }


    //按顺序获取数据库中的问题
    @SuppressLint("Range")
    public List<Question> getQuestion(String title) {
        List<Question> list = new ArrayList<Question>();
        this.title=title;
        /*
        Cursor是结果集游标，用于对结果集进行随机访问,其实Cursor与JDBC中的ResultSet作用很相似。
        rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，
        如果select语句没有使用占位符，该参数可以设置为null。*/
        // 单选 判断 多选     question表
        switch (this.title){
            case "History":
                DB_TABLE1="Single_History";
                DB_TABLE2="Judge_History";
                DB_TABLE3="MutiChoice_History";
                break;

            case "MaYuan":
                DB_TABLE1="Single_MaYuan";
                DB_TABLE2="Judge_MaYuan";
                DB_TABLE3="MutiChoice_MaYuan";
                break;

            case  "LvYou":
                DB_TABLE1="Single_LvYou";
                DB_TABLE2="Judge_LvYou";
                DB_TABLE3="MutiChoice_LvYou";
                break;

            case "MaoGai":
                DB_TABLE1="Single_MaoGai";
                DB_TABLE2="Judge_MaoGai";
                DB_TABLE3="MutiChoice_MaoGai";
                break;

            case "ThinkingVirtue":
                DB_TABLE1="Single_Thinking";
                DB_TABLE2="Judge_Thinking";
                DB_TABLE3="MutiChoice_Thinking";
                break;

            case "NetworkSecurity":
                DB_TABLE1="Single_NetworkSecurity";
                DB_TABLE2="Judge_NetworkSecurity";
                DB_TABLE3="MutiChoice_NetworkSecurity";
                break;

            case "ComputerQuestion":
                DB_TABLE1="Single_ComputerQuestion";
                DB_TABLE2="Judge_ComputerQuestion";
                DB_TABLE3="MutiChoice_ComputerQuestion";
                break;
        }

        String sql1=" select * from  "+DB_TABLE1;
        String sql2=" select * from  "+DB_TABLE2;
        String sql3=" select * from  "+DB_TABLE3;

        try{
            Cursor cursor = db.rawQuery(sql1, null);
            Cursor cursor2 = db.rawQuery(sql2, null);
            Cursor cursor3 = db.rawQuery(sql3, null);
            //单选
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();//将cursor移动到第一个光标上
                int count = cursor.getCount();
                //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
                for (int i = 0; i < count; i++) {
                    cursor.moveToPosition(i);
                    Question question = new Question();
                    question.ID = cursor.getInt(cursor.getColumnIndex("ID"));
                    question.mode = 0;
                    question.question = cursor.getString(cursor.getColumnIndex("question"));
                    question.answerA = cursor.getString(cursor.getColumnIndex("answerA"));
                    question.answerB = cursor.getString(cursor.getColumnIndex("answerB"));
                    question.answerC = cursor.getString(cursor.getColumnIndex("answerC"));
                    question.answerD = cursor.getString(cursor.getColumnIndex("answerD"));
                    question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                    question.explaination = cursor.getString(cursor.getColumnIndex("explainations"));
                    //表示没有选择任何选项
                    question.selectedAnswer = -1;
                    list.add(question);
                }
            }
            //判断
            if (cursor2.getCount() > 0) {
                cursor2.moveToFirst();
                int count = cursor2.getCount();

                for (int i = 0; i < count; i++) {
                    cursor2.moveToPosition(i);
                    Question question = new Question();
                    question.ID = cursor2.getInt(cursor2.getColumnIndex("ID"));
                    question.mode = 1;
                    question.question = cursor2.getString(cursor2.getColumnIndex("question"));
                    question.answerA = cursor2.getString(cursor2.getColumnIndex("answerA"));
                    question.answerB = cursor2.getString(cursor2.getColumnIndex("answerB"));
                    question.answer = cursor2.getInt(cursor2.getColumnIndex("answer"));
                    question.explaination = cursor2.getString(cursor2.getColumnIndex("explainations"));
                    //表示没有选择任何选项
                    question.selectedAnswer = -1;
                    list.add(question);
                }
            }
            //  多选
            if (cursor3.getCount() > 0) {
                cursor3.moveToFirst();
                int count = cursor3.getCount();
                for (int i = 0; i < count; i++) {
                    cursor3.moveToPosition(i);
                    Question question = new Question();
                    question.ID = cursor3.getInt(cursor3.getColumnIndex("ID"));
                    question.mode = 2;
                    question.question = cursor3.getString(cursor3.getColumnIndex("question"));
                    question.answerA = cursor3.getString(cursor3.getColumnIndex("answerA"));
                    question.answerB = cursor3.getString(cursor3.getColumnIndex("answerB"));
                    question.answerC = cursor3.getString(cursor3.getColumnIndex("answerC"));
                    question.answerD = cursor3.getString(cursor3.getColumnIndex("answerD"));
                    question.answer = cursor3.getInt(cursor3.getColumnIndex("answer"));
                    question.explaination = cursor3.getString(cursor3.getColumnIndex("explainations"));
                    //表示没有选择任何选项
                    question.selectedAnswer = -1;
                    list.add(question);
                }
            }
        } catch (Exception e) {
            Log.i("TAG","失败");
            e.printStackTrace();
        }
        db.close();
        return list;
    }

    // 生成随机数 查找数据库内容并添加
    @SuppressLint("Range")
    public List<Question> getRandomQuestion(int singleNum, int judgeNum, int mutichoiceNum, String ExamQuestion) {
        List<Question> list = new ArrayList<Question>();
        int flag = 0;
        int flag2 = 0;
        int flag3 = 0;

       ExamTitle=ExamQuestion;
       Log.i("Question","题库是："+ExamTitle);
       Log.i("Question","单选题是："+singleNum);

        switch (ExamTitle){
            case "History":
                DB_TABLE1="Single_History";
                DB_TABLE2="Judge_History";
                DB_TABLE3="MutiChoice_History";
                break;

            case "MaYuan":
                DB_TABLE1="Single_MaYuan";
                DB_TABLE2="Judge_MaYuan";
                DB_TABLE3="MutiChoice_MaYuan";
                break;

            case  "LvYou":
                DB_TABLE1="Single_LvYou";
                DB_TABLE2="Judge_LvYou";
                DB_TABLE3="MutiChoice_LvYou";
                break;

            case "MaoGai":
                DB_TABLE1="Single_MaoGai";
                DB_TABLE2="Judge_MaoGai";
                DB_TABLE3="MutiChoice_MaoGai";
                break;

            case "ThinkingVirtue":
                DB_TABLE1="Single_Thinking";
                DB_TABLE2="Judge_Thinking";
                DB_TABLE3="MutiChoice_Thinking";
                break;

            case "ComputerQuestion":
                DB_TABLE1="Single_ComputerQuestion";
                DB_TABLE2="Judge_ComputerQuestion";
                DB_TABLE3="MutiChoice_ComputerQuestion";
                break;

            case "NetworkSecurity":
                DB_TABLE1="Single_NetworkSecurity";
                DB_TABLE2="Judge_NetworkSecurity";
                DB_TABLE3="MutiChoice_NetworkSecurity";
                break;
        }

        String sql1=" select * from  "+DB_TABLE1;
        String sql2=" select * from  "+DB_TABLE2;
        String sql3=" select * from  "+DB_TABLE3;

        int[] array = new int[999];
        int[] array2 = new int[999];
        int[] array3 = new int[999];
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
            array2[i] = 0;
            array3[i] = 0;
        }
        // 单选
        for (int i = 0; flag < singleNum; i++) {
            int number = (int) (1 + Math.random() * (20));
            if (array[number] == 0) {
                array[number] = 1;
                Cursor cursor = db.rawQuery(sql1+" where ID= " + number, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();//将cursor移动到第一个光标上
                    int count = cursor.getCount();
                    //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
                    for (int j = 0; j < count; j++) {
                        cursor.moveToPosition(j);
                        Question question = new Question();
                        question.mode = 0;
                        question.question = cursor.getString(cursor.getColumnIndex("question"));
                        question.answerA = cursor.getString(cursor.getColumnIndex("answerA"));
                        question.answerB = cursor.getString(cursor.getColumnIndex("answerB"));
                        question.answerC = cursor.getString(cursor.getColumnIndex("answerC"));
                        question.answerD = cursor.getString(cursor.getColumnIndex("answerD"));
                        question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                        question.explaination = cursor.getString(cursor.getColumnIndex("explainations"));
                        //表示没有选择任何选项
                        question.selectedAnswer = -1;
                        list.add(question);
                    }
                }
                flag++;
            }
        }

        // 判断
        for (int i = 0; flag2 < judgeNum; i++) {
            int number = (int) (1 + Math.random() * (20));
            if (array2[number] == 0) {
                array2[number] = 1;
                Cursor cursor = db.rawQuery(sql2+" where ID= " + number, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int count = cursor.getCount();
                    for (int j = 0; j < count; j++) {
                        cursor.moveToPosition(j);
                        Question question = new Question();
                        question.mode = 1;
                        question.question = cursor.getString(cursor.getColumnIndex("question"));
                        question.answerA = cursor.getString(cursor.getColumnIndex("answerA"));
                        question.answerB = cursor.getString(cursor.getColumnIndex("answerB"));
                        question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                        question.explaination = cursor.getString(cursor.getColumnIndex("explainations"));
                        //表示没有选择任何选项
                        question.selectedAnswer = -1;
                        list.add(question);
                    }
                }
                flag2++;
            }
        }
        // 多选
        for (int i = 0; flag3 < mutichoiceNum; i++) {
            int number = (int) (1 + Math.random() * (20));
            if (array3[number] == 0) {
                array3[number] = 1;
                Cursor cursor = db.rawQuery(sql3+" where ID= " + number, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();//将cursor移动到第一个光标上
                    int count = cursor.getCount();
                    //将cursor中的每一条记录生成一个question对象，并将该question对象添加到list中
                    for (int j = 0; j < count; j++) {
                        cursor.moveToPosition(j);
                        Question question = new Question();
                        question.mode = 2;
                        question.question = cursor.getString(cursor.getColumnIndex("question"));
                        question.answerA = cursor.getString(cursor.getColumnIndex("answerA"));
                        question.answerB = cursor.getString(cursor.getColumnIndex("answerB"));
                        question.answerC = cursor.getString(cursor.getColumnIndex("answerC"));
                        question.answerD = cursor.getString(cursor.getColumnIndex("answerD"));
                        question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                        question.explaination = cursor.getString(cursor.getColumnIndex("explainations"));
                        //表示没有选择任何选项
                        question.selectedAnswer = -1;
                        list.add(question);
                    }
                }
                flag3++;
            }
        }
        db.close();
        return list;
    }


    public SQLiteDatabase getDb(){
        return  db;
    }

    //添加收藏数据到数据库
    public long addData(Question question, int mode,String tablename ){
        ContentValues values = new ContentValues();
        ContentValues values1=new ContentValues();
        ContentValues values2=new ContentValues();

        long test;
        long test1;
        long test2;

        try {
            if (mode==0) {
                switch (tablename) {
                    case "History":
                        values.put("ID", question.ID);
                        values.put("question", question.question);
                        values.put("answerA", question.answerA);
                        values.put("answerB", question.answerB);
                        values.put("answerC", question.answerC);
                        values.put("answerD", question.answerD);
                        values.put("answer", question.answer);
                        values.put("explainations", question.explaination);
                        Log.i("Log", question.ID + question.question);
                        test = db.insertOrThrow(Constant.HISTORY_SINGLE_NAME, null, values);
                        Log.i("Log", "---------插入成功----------");
                        db.close();
                        return test;

                    case "MaYuan":
                        values.put("ID", question.ID);
                        values.put("question", question.question);
                        values.put("answerA", question.answerA);
                        values.put("answerB", question.answerB);
                        values.put("answerC", question.answerC);
                        values.put("answerD", question.answerD);
                        values.put("answer", question.answer);
                        values.put("explainations", question.explaination);
                        Log.i("Log", question.ID + question.question);
                        test = db.insertOrThrow(Constant.MaYuan_SINGLE_NAME, null, values);
                        Log.i("Log", "---------插入成功----------");
                        db.close();
                        return test;

                    case "LvYou":
                        values.put("ID", question.ID);
                        values.put("question", question.question);
                        values.put("answerA", question.answerA);
                        values.put("answerB", question.answerB);
                        values.put("answerC", question.answerC);
                        values.put("answerD", question.answerD);
                        values.put("answer", question.answer);
                        values.put("explainations", question.explaination);
                        Log.i("Log", question.ID + question.question);
                        test = db.insertOrThrow(Constant.LvYou_SINGLE_NAME, null, values);
                        Log.i("Log", "---------插入成功----------");
                        db.close();
                        return test;

                    case "MaoGai":
                        values.put("ID", question.ID);
                        values.put("question", question.question);
                        values.put("answerA", question.answerA);
                        values.put("answerB", question.answerB);
                        values.put("answerC", question.answerC);
                        values.put("answerD", question.answerD);
                        values.put("answer", question.answer);
                        values.put("explainations", question.explaination);
                        Log.i("Log", question.ID + question.question);
                        test = db.insertOrThrow(Constant.MaoGai_SINGLE_NAME, null, values);
                        Log.i("Log", "---------插入成功----------");
                        db.close();
                        return test;

                    case "ThinkingVirtue":
                        values.put("ID", question.ID);
                        values.put("question", question.question);
                        values.put("answerA", question.answerA);
                        values.put("answerB", question.answerB);
                        values.put("answerC", question.answerC);
                        values.put("answerD", question.answerD);
                        values.put("answer", question.answer);
                        values.put("explainations", question.explaination);
                        Log.i("Log", question.ID + question.question);
                        test = db.insertOrThrow(Constant.ThinkingVirtue_SINGLE_NAME, null, values);
                        Log.i("Log", "---------插入成功----------");
                        db.close();
                        return test;

                    case "NetworkSecurity":
                        values.put("ID", question.ID);
                        values.put("question", question.question);
                        values.put("answerA", question.answerA);
                        values.put("answerB", question.answerB);
                        values.put("answerC", question.answerC);
                        values.put("answerD", question.answerD);
                        values.put("answer", question.answer);
                        values.put("explainations", question.explaination);
                        Log.i("Log", question.ID + question.question);
                        test = db.insertOrThrow(Constant.NetworkSecurity_SINGLE_NAME, null, values);
                        Log.i("Log", "---------插入成功----------");
                        db.close();
                        return test;

                    case "ComputerQuestion":
                        values.put("ID", question.ID);
                        values.put("question", question.question);
                        values.put("answerA", question.answerA);
                        values.put("answerB", question.answerB);
                        values.put("answerC", question.answerC);
                        values.put("answerD", question.answerD);
                        values.put("answer", question.answer);
                        values.put("explainations", question.explaination);
                        Log.i("Log", question.ID + question.question);
                        test = db.insertOrThrow(Constant.Computer_SINGLE_NAME, null, values);
                        Log.i("Log", "---------插入成功----------");
                        db.close();
                        return test;
                }
            }else if (mode==1) {
                switch (tablename) {
                    case "History":
                        values1.put("ID",question.ID);
                        values1.put("question",question.question);
                        values1.put("answerA",question.answerA);
                        values1.put("answerB",question.answerB);
                        values1.put("answer",question.answer);
                        values1.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test1= db.insertOrThrow(Constant.HISTORY_SINGLE_NAME,null,values1);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test1;

                    case "MaYuan":
                        values1.put("ID",question.ID);
                        values1.put("question",question.question);
                        values1.put("answerA",question.answerA);
                        values1.put("answerB",question.answerB);
                        values1.put("answer",question.answer);
                        values1.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test1= db.insertOrThrow(Constant.MaYuan_SINGLE_NAME,null,values1);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test1;

                    case "LvYou":
                        values1.put("ID",question.ID);
                        values1.put("question",question.question);
                        values1.put("answerA",question.answerA);
                        values1.put("answerB",question.answerB);
                        values1.put("answer",question.answer);
                        values1.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test1= db.insertOrThrow(Constant.LvYou_SINGLE_NAME,null,values1);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test1;

                    case "MaoGai":
                        values1.put("ID",question.ID);
                        values1.put("question",question.question);
                        values1.put("answerA",question.answerA);
                        values1.put("answerB",question.answerB);
                        values1.put("answer",question.answer);
                        values1.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test1= db.insertOrThrow(Constant.MaoGai_SINGLE_NAME,null,values1);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test1;

                    case "ThinkingVirtue":
                        values1.put("ID",question.ID);
                        values1.put("question",question.question);
                        values1.put("answerA",question.answerA);
                        values1.put("answerB",question.answerB);
                        values1.put("answer",question.answer);
                        values1.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test1= db.insertOrThrow(Constant.ThinkingVirtue_SINGLE_NAME,null,values1);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test1;

                    case "NetworkSecurity":
                        values1.put("ID",question.ID);
                        values1.put("question",question.question);
                        values1.put("answerA",question.answerA);
                        values1.put("answerB",question.answerB);
                        values1.put("answer",question.answer);
                        values1.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test1= db.insertOrThrow(Constant.NetworkSecurity_SINGLE_NAME,null,values1);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test1;

                    case "ComputerQuestion":
                        values1.put("ID",question.ID);
                        values1.put("question",question.question);
                        values1.put("answerA",question.answerA);
                        values1.put("answerB",question.answerB);
                        values1.put("answer",question.answer);
                        values1.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test1= db.insertOrThrow(Constant.Computer_SINGLE_NAME,null,values1);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test1;
                }
            }
            else if (mode==2) {
                switch (tablename) {
                    case "History":
                        values2.put("ID",question.ID);
                        values2.put("question",question.question);
                        values2.put("answerA",question.answerA);
                        values2.put("answerB",question.answerB);
                        values2.put("answerC",question.answerC);
                        values2.put("answerD",question.answerD);
                        values2.put("answer",question.answer);
                        values2.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test2= db.insertOrThrow(Constant.HISTORY_SINGLE_NAME,null,values2);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test2;

                    case "MaYuan":
                        values2.put("ID",question.ID);
                        values2.put("question",question.question);
                        values2.put("answerA",question.answerA);
                        values2.put("answerB",question.answerB);
                        values2.put("answerC",question.answerC);
                        values2.put("answerD",question.answerD);
                        values2.put("answer",question.answer);
                        values2.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test2= db.insertOrThrow(Constant.MaYuan_SINGLE_NAME,null,values2);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test2;

                    case "LvYou":
                        values2.put("ID",question.ID);
                        values2.put("question",question.question);
                        values2.put("answerA",question.answerA);
                        values2.put("answerB",question.answerB);
                        values2.put("answerC",question.answerC);
                        values2.put("answerD",question.answerD);
                        values2.put("answer",question.answer);
                        values2.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test2= db.insertOrThrow(Constant.LvYou_SINGLE_NAME,null,values2);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test2;

                    case "MaoGai":
                        values2.put("ID",question.ID);
                        values2.put("question",question.question);
                        values2.put("answerA",question.answerA);
                        values2.put("answerB",question.answerB);
                        values2.put("answerC",question.answerC);
                        values2.put("answerD",question.answerD);
                        values2.put("answer",question.answer);
                        values2.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test2= db.insertOrThrow(Constant.MaoGai_SINGLE_NAME,null,values2);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test2;

                    case "ThinkingVirtue":
                        values2.put("ID",question.ID);
                        values2.put("question",question.question);
                        values2.put("answerA",question.answerA);
                        values2.put("answerB",question.answerB);
                        values2.put("answerC",question.answerC);
                        values2.put("answerD",question.answerD);
                        values2.put("answer",question.answer);
                        values2.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test2= db.insertOrThrow(Constant.ThinkingVirtue_SINGLE_NAME,null,values2);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test2;

                    case "NetworkSecurity":
                        values2.put("ID",question.ID);
                        values2.put("question",question.question);
                        values2.put("answerA",question.answerA);
                        values2.put("answerB",question.answerB);
                        values2.put("answerC",question.answerC);
                        values2.put("answerD",question.answerD);
                        values2.put("answer",question.answer);
                        values2.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test2= db.insertOrThrow(Constant.NetworkSecurity_SINGLE_NAME,null,values2);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test2;

                    case "ComputerQuestion":
                        values2.put("ID",question.ID);
                        values2.put("question",question.question);
                        values2.put("answerA",question.answerA);
                        values2.put("answerB",question.answerB);
                        values2.put("answerC",question.answerC);
                        values2.put("answerD",question.answerD);
                        values2.put("answer",question.answer);
                        values2.put("explainations",question.explaination);
                        Log.i("Log",question.ID+question.question);
                        test2= db.insertOrThrow(Constant.Computer_SINGLE_NAME,null,values2);
                        Log.i("Log","---------插入成功----------");
                        db.close();
                        return test2;
                }
            }
        }catch (SQLException e){
            Log.i("Log","---------插入失败----------");
        }
        return -1;
    }

    //删除数据库中收藏的数据
    public void delete(Question question,int mode,String tablename){
        String sql1;
        String sql2;
        String sql3;

        if(mode==0){
            switch (tablename){
                case "History":
                    sql1="delete from "+Constant.HISTORY_SINGLE_NAME+" where id="+question.ID;
                    db.execSQL(sql1);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;
                case "MaYuan":
                    sql1="delete from "+Constant.MaYuan_SINGLE_NAME+" where id="+question.ID;
                    db.execSQL(sql1);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case  "LvYou":
                    sql1="delete from "+Constant.LvYou_SINGLE_NAME+" where id="+question.ID;
                    db.execSQL(sql1);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "MaoGai":
                    sql1="delete from "+Constant.MaoGai_SINGLE_NAME+" where id="+question.ID;
                    db.execSQL(sql1);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "ThinkingVirtue":
                    sql1="delete from "+Constant.ThinkingVirtue_SINGLE_NAME+" where id="+question.ID;
                    db.execSQL(sql1);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "NetworkSecurity":
                    sql1="delete from "+Constant.NetworkSecurity_SINGLE_NAME+" where id="+question.ID;
                    db.execSQL(sql1);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "ComputerQuestion":
                    sql1="delete from "+Constant.Computer_SINGLE_NAME+" where id="+question.ID;
                    db.execSQL(sql1);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;
            }


        }else if(mode==1){
            switch (tablename){
                case "History":
                    sql2="delete from "+Constant.HISTORY_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql2);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;
                case "MaYuan":
                    sql2="delete from "+Constant.MaYuan_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql2);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case  "LvYou":
                    sql2="delete from "+Constant.LvYou_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql2);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "MaoGai":
                    sql2="delete from "+Constant.MaoGai_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql2);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "ThinkingVirtue":
                    sql2="delete from "+Constant.ThinkingVirtue_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql2);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "NetworkSecurity":
                    sql2="delete from "+Constant.NetworkSecurity_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql2);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "ComputerQuestion":
                    sql2="delete from "+Constant.Computer_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql2);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;
            }

        }else if(mode==2){
            switch (tablename){
                case "History":
                    sql3="delete from "+Constant.HISTORY_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql3);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;
                case "MaYuan":
                    sql3="delete from "+Constant.MaYuan_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql3);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;
                case  "LvYou":
                    sql3="delete from "+Constant.LvYou_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql3);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "MaoGai":
                    sql3="delete from "+Constant.MaoGai_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql3);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "ThinkingVirtue":
                    sql3="delete from "+Constant.ThinkingVirtue_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql3);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "NetworkSecurity":
                    sql3="delete from "+Constant.NetworkSecurity_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql3);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;

                case "ComputerQuestion":
                    sql3="delete from "+Constant.Computer_SINGLE_NAME+"  where id="+question.ID;
                    db.execSQL(sql3);
                    db.close();
                    Log.i("Log","---------删除成功----------");
                    break;
            }
        }
    }
}
