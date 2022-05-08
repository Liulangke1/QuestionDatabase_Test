package com.zjxu.cc.questiondatabase.bean;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.zjxu.cc.questiondatabase.Question;
import java.util.ArrayList;
import java.util.List;


public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

    private String DB_TABLE1;
    private String DB_TABLE2;
    private String DB_TABLE3;

    public MyDatabaseOpenHelper(Context context){
        super(context,Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Constant constant=new Constant();
        try{
            db.execSQL(constant.Create_Single_Table_sql(Constant.HISTORY_SINGLE_NAME));
            db.execSQL(constant.Create_Single_Table_sql(Constant.LvYou_SINGLE_NAME));
            db.execSQL(constant.Create_Single_Table_sql(Constant.ThinkingVirtue_SINGLE_NAME));
            db.execSQL(constant.Create_Single_Table_sql(Constant.MaoGai_SINGLE_NAME));
            db.execSQL(constant.Create_Single_Table_sql(Constant.NetworkSecurity_SINGLE_NAME));
            db.execSQL(constant.Create_Single_Table_sql(Constant.MaYuan_SINGLE_NAME));
            db.execSQL(constant.Create_Single_Table_sql(Constant.Computer_SINGLE_NAME));
            Log.i("Log","---------创建收藏单选表成功-----------");
        }catch (SQLException e){
            Log.i("Log","---------创建收藏单选表失败----------");
            e.printStackTrace();
        }

        try {
            db.execSQL(constant.Create_Judge_Table_sql(Constant.HISTORY_Judge_NAME));
            db.execSQL(constant.Create_Judge_Table_sql(Constant.LvYou_Judge_NAME));
            db.execSQL(constant.Create_Judge_Table_sql(Constant.ThinkingVirtue_Judge_NAME));
            db.execSQL(constant.Create_Judge_Table_sql(Constant.MaoGai_Judge_NAME));
            db.execSQL(constant.Create_Judge_Table_sql(Constant.NetworkSecurity_Judge_NAME));
            db.execSQL(constant.Create_Judge_Table_sql(Constant.MaYuan_Judge_NAME));
            db.execSQL(constant.Create_Judge_Table_sql(Constant.Computer_Judge_NAME));
            Log.i("Log","---------创建收藏判断表成功-----------");
        }
        catch (SQLException e){
            Log.i("Log","---------创建收藏判断表失败----------");
            e.printStackTrace();
        }

        try {
            db.execSQL(constant.Create_Multi_Table_sql(Constant.HISTORY_Multi_NAME));
            db.execSQL(constant.Create_Multi_Table_sql(Constant.LvYou_Multi_NAME));
            db.execSQL(constant.Create_Multi_Table_sql(Constant.ThinkingVirtue_Multi_NAME));
            db.execSQL(constant.Create_Multi_Table_sql(Constant.MaoGai_Multi_NAME));
            db.execSQL(constant.Create_Multi_Table_sql(Constant.NetworkSecurity_Multi_NAME));
            db.execSQL(constant.Create_Multi_Table_sql(Constant.MaYuan_Multi_NAME));
            db.execSQL(constant.Create_Multi_Table_sql(Constant.Computer_Multi_NAME));

            Log.i("Log","---------创建收藏多选表成功----------");
        }
        catch (SQLException e){
            Log.i("Log","---------创建收藏多选表失败----------");
            e.printStackTrace();
        }
        Log.i("Log","---------onCreate()-----------");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void ChooseTable(String type_question){
        switch (type_question){
            case "History":
                DB_TABLE1=Constant.HISTORY_SINGLE_NAME;
                DB_TABLE2=Constant.HISTORY_Judge_NAME;
                DB_TABLE3=Constant.HISTORY_Multi_NAME;
                break;

            case "MaYuan":
                DB_TABLE1=Constant.MaYuan_SINGLE_NAME;
                DB_TABLE2=Constant.MaYuan_Judge_NAME;
                DB_TABLE3=Constant.MaYuan_Multi_NAME;
                break;

            case  "LvYou":
                DB_TABLE1=Constant.LvYou_SINGLE_NAME;
                DB_TABLE2=Constant.LvYou_Judge_NAME;
                DB_TABLE3=Constant.LvYou_Multi_NAME;
                break;

            case "MaoGai":
                DB_TABLE1=Constant.MaoGai_SINGLE_NAME;
                DB_TABLE2=Constant.MaoGai_Judge_NAME;
                DB_TABLE3=Constant.MaoGai_Multi_NAME;
                break;

            case "ThinkingVirtue":
                DB_TABLE1=Constant.ThinkingVirtue_SINGLE_NAME;
                DB_TABLE2=Constant.ThinkingVirtue_Judge_NAME;
                DB_TABLE3=Constant.ThinkingVirtue_Multi_NAME;
                break;

            case "NetworkSecurity":
                DB_TABLE1=Constant.NetworkSecurity_SINGLE_NAME;
                DB_TABLE2=Constant.NetworkSecurity_Judge_NAME;
                DB_TABLE3=Constant.NetworkSecurity_Multi_NAME;
                break;

            case "ComputerQuestion":
                DB_TABLE1=Constant.Computer_SINGLE_NAME;
                DB_TABLE2=Constant.Computer_Judge_NAME;
                DB_TABLE3=Constant.Computer_Multi_NAME;
                break;
        }
    }

    @SuppressLint("Range")
    public List<Question> getCollectionQuestion(String title,Context context) {

        List<Question> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=SQLiteDatabase.openDatabase("/data/data/com.zjxu.cc.questiondatabase/databases/Collection.db",null,SQLiteDatabase.OPEN_READWRITE);

//        Log.i("Log","context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath()---------------"+ context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath());
        Log.i("Log","getRootDirectory()---------------"+ Environment.getRootDirectory());
        Log.i("Log","getDataDirectory()---------------"+ Environment.getDataDirectory());
        Log.i("Log","getExternalStoragePublicDirectory()---------------"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
        Log.i("Log","getDownloadCacheDirectory()---------------"+ Environment.getDownloadCacheDirectory());
        Log.i("Log","getExternalStorageState()---------------"+ Environment.getExternalStorageState());
        Log.i("Log","getExternalStorageDirectory()---------------"+ Environment.getExternalStorageDirectory());

        ChooseTable(title);

        String sql1=" select * from  "+DB_TABLE1;
        String sql2=" select * from  "+DB_TABLE2;
        String sql3=" select * from  "+DB_TABLE3;

        try{
            Cursor cursor = sqLiteDatabase.rawQuery(sql1, null);
            Cursor cursor2 = sqLiteDatabase.rawQuery(sql2, null);
            Cursor cursor3 = sqLiteDatabase.rawQuery(sql3, null);
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
        sqLiteDatabase.close();
        return list;
    }

    //添加收藏数据到数据库
    public long addData(Question question, int mode,String table_name ){
        ContentValues values = new ContentValues();
        ContentValues values1=new ContentValues();
        ContentValues values2=new ContentValues();
        ChooseTable(table_name);

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        long test;
        long test1;
        long test2;
        try {
            if (mode==0) {
                values.put("ID", question.ID);
                values.put("question", question.question);
                values.put("answerA", question.answerA);
                values.put("answerB", question.answerB);
                values.put("answerC", question.answerC);
                values.put("answerD", question.answerD);
                values.put("answer", question.answer);
                values.put("explainations", question.explaination);
                Log.i("Log", question.ID + question.question);
                test = sqLiteDatabase.insertOrThrow(DB_TABLE1, null, values);
                Log.i("Log", "---------插入成功----------");
                sqLiteDatabase.close();
                return test;
            }else if (mode==1) {
                values1.put("ID",question.ID);
                values1.put("question",question.question);
                values1.put("answerA",question.answerA);
                values1.put("answerB",question.answerB);
                values1.put("answer",question.answer);
                values1.put("explainations",question.explaination);
                Log.i("Log",question.ID+question.question);
                test1= sqLiteDatabase.insertOrThrow(DB_TABLE2,null,values1);
                Log.i("Log","---------插入成功----------");
                sqLiteDatabase.close();
                return test1;
            }
            else if (mode==2) {
                values2.put("ID",question.ID);
                values2.put("question",question.question);
                values2.put("answerA",question.answerA);
                values2.put("answerB",question.answerB);
                values2.put("answerC",question.answerC);
                values2.put("answerD",question.answerD);
                values2.put("answer",question.answer);
                values2.put("explainations",question.explaination);
                Log.i("Log",question.ID+question.question);
                test2= sqLiteDatabase.insertOrThrow(DB_TABLE3,null,values2);
                Log.i("Log","---------插入成功----------");
                sqLiteDatabase.close();
                return test2;
            }
        }catch (SQLException e){
            Log.i("Log","---------插入失败----------");
        }
        return -1;
    }

    //删除数据库中收藏的数据
    public void delete(Question question,int mode,String table_name){
        String sql1;
        String sql2;
        String sql3;
        ChooseTable(table_name);
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        if(mode==0){
            sql1="delete from "+DB_TABLE1+" where id="+question.ID;
            sqLiteDatabase.execSQL(sql1);
            sqLiteDatabase.close();
            Log.i("Log","---------删除成功----------");
        }else if(mode==1){
            sql2="delete from "+DB_TABLE2+"  where id="+question.ID;
            sqLiteDatabase.execSQL(sql2);
            sqLiteDatabase.close();
            Log.i("Log","---------删除成功----------");
        }else if(mode==2){
            sql3="delete from "+DB_TABLE3+"  where id="+question.ID;
            sqLiteDatabase.execSQL(sql3);
            sqLiteDatabase.close();
            Log.i("Log","---------删除成功----------");
        }
    }
}
