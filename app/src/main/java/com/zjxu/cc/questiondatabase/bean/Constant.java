package com.zjxu.cc.questiondatabase.bean;

public class Constant {

    public static final String DATABASE_NAME="Collection.db";
    public static final int DATABASE_VERSION=1;

    public static final String DATABASE_NAME_ADD="AddQuestion.db";


    public static final String HISTORY_SINGLE_NAME="History_Single_Collection";
    public static final String LvYou_SINGLE_NAME="LvYou_Single_Collection";
    public static final String ThinkingVirtue_SINGLE_NAME="ThinkingVirtue_Single_Collection";
    public static final String MaoGai_SINGLE_NAME="MaoGai_Single_Collection";
    public static final String NetworkSecurity_SINGLE_NAME="NetworkSecurity_Single_Collection";
    public static final String MaYuan_SINGLE_NAME="MaYuan_Single_Collection";
    public static final String Computer_SINGLE_NAME="Computer_Single_Collection";

    public static final String HISTORY_Multi_NAME="History_Multi_Collection";
    public static final String LvYou_Multi_NAME="LvYou_Multi_Collection";
    public static final String ThinkingVirtue_Multi_NAME="ThinkingVirtue_Multi_Collection";
    public static final String MaoGai_Multi_NAME="MaoGai_Multi_Collection";
    public static final String NetworkSecurity_Multi_NAME="NetworkSecurity_Multi_Collection";
    public static final String MaYuan_Multi_NAME="MaYuan_Multi_Collection";
    public static final String Computer_Multi_NAME="Computer_Multi_Collection";

    public static final String HISTORY_Judge_NAME="History_Judge_Collection";
    public static final String LvYou_Judge_NAME="LvYou_Judge_Collection";
    public static final String ThinkingVirtue_Judge_NAME="ThinkingVirtue_Judge_Collection";
    public static final String MaoGai_Judge_NAME="MaoGai_Judge_Collection";
    public static final String NetworkSecurity_Judge_NAME="NetworkSecurity_Judge_Collection";
    public static final String MaYuan_Judge_NAME="MaYuan_Judge_Collection";
    public static final String Computer_Judge_NAME="Computer_Judge_Collection";

    public String question;
    public String answerA;
    public String answerB;
    public String answerC;
    public String answerD;
    public int answer;
    public String explaination;


    public String Create_Single_Table_sql(String Single_table_name){
        return "CREATE TABLE  "+Single_table_name+"(" +
                "  ID INTEGER PRIMARY KEY, " +
                "  question TEXT , " +
                "  answerA TEXT, " +
                "  answerB TEXT, " +
                "  answerC TEXT, " +
                "  answerD TEXT, " +
                "  answer INTEGER, " +
                "  explainations TEXT)";
    }

    public  String Create_Multi_Table_sql(String Multi_table_name){
        return "CREATE TABLE  "+Multi_table_name+"("+
                "  ID INTEGER PRIMARY KEY, " +
                "  question TEXT , " +
                "  answerA TEXT, " +
                "  answerB TEXT, " +
                "  answerC TEXT, " +
                "  answerD TEXT, " +
                "  answer INTEGER, " +
                "  explainations TEXT)";
    }

    public  String Create_Judge_Table_sql(String Judge_table_name){
        return "CREATE TABLE  "+Judge_table_name+"("+
                "  ID INTEGER PRIMARY KEY, " +
                "  question TEXT , " +
                "  answerA TEXT, " +
                "  answerB TEXT, " +
                "  answer INTEGER, " +
                "  explainations TEXT)";
    }

    public String ChangeTable_sql(String table_old_name,String new_table_name){
        return "ALTER  TABLE  "+table_old_name+" RENAME TO "+new_table_name;
    }



}
