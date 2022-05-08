package com.zjxu.cc.questiondatabase.Practise.MaYuanQuestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zjxu.cc.questiondatabase.R;


public class MayuanMainActivity extends Activity implements View.OnClickListener {

    private Button mMaYuanChapterFirst_btn;
    private Button mMaYuanChapterSecond_btn;
    private Button mMaYuanChapterThird_btn;
    private Button mMaYuanChapterFourth_btn;
    private Button mMaYuanChapterFifth_btn;
    private Button mMaYuanChapterSixth_btn;
    private Button mMaYuanChapterSeventh_btn;
    private Button mMaYuanSummary_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mayuan_question_main);

        mMaYuanChapterFirst_btn=findViewById(R.id.MaYuanChapterFirst_btn);
        mMaYuanChapterSecond_btn=findViewById(R.id.MaYuanChapterSecond_btn);
        mMaYuanChapterThird_btn=findViewById(R.id.MaYuanChapterThird_btn);
        mMaYuanChapterFourth_btn=findViewById(R.id.MaYuanChapterFourth_btn);
        mMaYuanChapterFifth_btn=findViewById(R.id.MaYuanChapterFifth_btn);
        mMaYuanChapterSixth_btn=findViewById(R.id.MaYuanChapterSixth_btn);
        mMaYuanChapterSeventh_btn=findViewById(R.id.MaYuanChapterSeventh_btn);
        mMaYuanSummary_btn=findViewById(R.id.MaYuanSummary_btn);

        mMaYuanChapterFirst_btn.setOnClickListener(this::onClick);
        mMaYuanChapterSecond_btn.setOnClickListener(this::onClick);
        mMaYuanChapterThird_btn.setOnClickListener(this::onClick);
        mMaYuanChapterFourth_btn.setOnClickListener(this::onClick);
        mMaYuanChapterFifth_btn.setOnClickListener(this::onClick);
        mMaYuanChapterSixth_btn.setOnClickListener(this::onClick);
        mMaYuanChapterSeventh_btn.setOnClickListener(this::onClick);
        mMaYuanSummary_btn.setOnClickListener(this::onClick);

}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.MaYuanChapterFirst_btn:
//                Intent intent_first=new Intent(MayuanMainActivity.this, MaYuanChapterFirst.class);
//                startActivity(intent_first);
                Toast.makeText(this,"第一章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaYuanChapterSecond_btn:
//                Intent intent_second=new Intent(MayuanMainActivity.this,MaYuanChapterSecond.class);
//                startActivity(intent_second);
                Toast.makeText(this,"第二章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaYuanChapterThird_btn:
//                Intent intent_third=new Intent(MayuanMainActivity.this,MaYuanChapterThird.class);
//                startActivity(intent_third);
                Toast.makeText(this,"第三章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaYuanChapterFourth_btn:
//                Intent intent_fourth=new Intent(MayuanMainActivity.this,MaYuanChapterFourth.class);
//                startActivity(intent_fourth);

                Toast.makeText(this,"第四章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.MaYuanChapterFifth_btn:
//                Intent intent_fifth=new Intent(MayuanMainActivity.this,MaYuanChapterFifth.class);
//                startActivity(intent_fifth);

                Toast.makeText(this,"第五章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.MaYuanChapterSixth_btn:
//                Intent intent_sixth=new Intent(MayuanMainActivity.this,MaYuanChapterSixth.class);
//                startActivity(intent_sixth);

                Toast.makeText(this,"第六章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.MaYuanChapterSeventh_btn:
//                Intent intent_seventh=new Intent(MayuanMainActivity.this,MaYuanChapterSeventh.class);
//                startActivity(intent_seventh);

                Toast.makeText(this,"第七章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.MaYuanSummary_btn:
                Intent intent_summary=new Intent(MayuanMainActivity.this,MaYuanSummary.class);
                startActivity(intent_summary);
                break;
        }
    }
}
