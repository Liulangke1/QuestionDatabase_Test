package com.zjxu.cc.questiondatabase.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zjxu.cc.questiondatabase.Question;

public class AddDatabaseHelper extends SQLiteOpenHelper {

    String Single_table_name;
    String Judge_table_name;
    String Multi_table_name;

    public AddDatabaseHelper(@Nullable Context context,String table_name,int mode) {
        super(context, Constant.DATABASE_NAME_ADD, null, Constant.DATABASE_VERSION);

        switch (mode){
            case 0:
                this.Single_table_name=table_name;
                break;
            case 1:
                this.Judge_table_name=table_name;
                break;
            case 2:
                this.Multi_table_name=table_name;
                break;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Constant constant=new Constant();

        if(this.Single_table_name!=null){
            try{
                db.execSQL(constant.Create_Single_Table_sql(this.Single_table_name));
                Log.i("cc","---------创建添加单选表成功-----------");
            }catch (SQLException e){
                Log.i("cc","---------创建添加单选表失败----------");
                e.printStackTrace();
            }
        }
        else if(this.Judge_table_name!=null){
            try {
                db.execSQL(constant.Create_Judge_Table_sql(this.Judge_table_name));
                Log.i("cc","---------创建添加判断表成功-----------");
            }
            catch (SQLException e){
                Log.i("cc","---------创建添加判断表失败----------");
                e.printStackTrace();
            }
        }
        else if(this.Multi_table_name!=null){
            try {
                db.execSQL(constant.Create_Multi_Table_sql(this.Multi_table_name));
                Log.i("cc","---------创建添加多选表成功----------");
            }
            catch (SQLException e){
                Log.i("cc","---------创建添加多选表失败----------");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //添加收藏数据到数据库
    public long addData(Question question, int mode, String table_name ){
        ContentValues values = new ContentValues();
        ContentValues values1=new ContentValues();
        ContentValues values2=new ContentValues();

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        String sql=" select * from  "+table_name;

        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        question.ID=cursor.getCount()+1;

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
                Log.i("cc", question.ID + question.question);
                test = sqLiteDatabase.insertOrThrow(table_name, null, values);
                Log.i("cc", "---------插入单选表成功----------");
                sqLiteDatabase.close();
                return test;
            }else if (mode==1) {
                values1.put("ID",question.ID);
                values1.put("question",question.question);
                values1.put("answerA",question.answerA);
                values1.put("answerB",question.answerB);
                values1.put("answer",question.answer);
                values1.put("explainations",question.explaination);
                Log.i("cc",question.ID+question.question);
                test1= sqLiteDatabase.insertOrThrow(table_name,null,values1);
                Log.i("cc","---------插入判断表成功----------");
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
                Log.i("cc",question.ID+question.question);
                test2= sqLiteDatabase.insertOrThrow(table_name,null,values2);
                Log.i("cc","---------插入多选表成功----------");
                sqLiteDatabase.close();
                return test2;
            }
        }catch (SQLException e){
            Log.i("cc","---------插入失败----------");
        }
        cursor.close();

        return -1;
    }

    //删除数据库中收藏的数据
    public void delete(Question question,int mode,String table_name){
        String sql1;
        String sql2;
        String sql3;
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        if(mode==0){
            sql1="delete from "+table_name+"  where id="+question.ID;
            sqLiteDatabase.execSQL(sql1);
            sqLiteDatabase.close();
            Log.i("cc","---------删除单选表数据成功----------");
        }else if(mode==1){
            sql2="delete from "+table_name+"  where id="+question.ID;
            sqLiteDatabase.execSQL(sql2);
            sqLiteDatabase.close();
            Log.i("cc","---------删除判断表成功----------");
        }else if(mode==2){
            sql3="delete from "+table_name+"  where id="+question.ID;
            sqLiteDatabase.execSQL(sql3);
            sqLiteDatabase.close();
            Log.i("cc","---------删除多选表成功----------");
        }
    }


    public void SelectTableName(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        String str="select name from sqlite_master where type='table' order by name";

        Cursor cursor= sqLiteDatabase.rawQuery(str,null);

        while(cursor.moveToNext()){
                        //遍历出表名
            String name = cursor.getString(0);
            Log.i("cc", name);

        }
    }
}
