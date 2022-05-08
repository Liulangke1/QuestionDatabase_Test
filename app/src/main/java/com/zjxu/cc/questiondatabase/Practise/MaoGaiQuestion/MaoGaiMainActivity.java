package com.zjxu.cc.questiondatabase.Practise.MaoGaiQuestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zjxu.cc.questiondatabase.R;

public class MaoGaiMainActivity extends Activity implements View.OnClickListener{

    private Button mMaoGaiChapterFirst_btn;
    private Button mMaoGaiChapterSecond_btn;
    private Button mMaoGaiChapterThird_btn;
    private Button mMaoGaiChapterFourth_btn;
    private Button mMaoGaiChapterFifth_btn;
    private Button mMaoGaiChapterSixth_btn;
    private Button mMaoGaiChapterSeventh_btn;
    private Button mMaoGaiSummary_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maogai_question_main);

        mMaoGaiChapterFirst_btn=findViewById(R.id.MaoGaiChapterFirst_btn);
        mMaoGaiChapterSecond_btn=findViewById(R.id.MaoGaiChapterSecond_btn);
        mMaoGaiChapterThird_btn=findViewById(R.id.MaoGaiChapterThird_btn);
        mMaoGaiChapterFourth_btn=findViewById(R.id.MaoGaiChapterFourth_btn);
        mMaoGaiChapterFifth_btn=findViewById(R.id.MaoGaiChapterFifth_btn);
        mMaoGaiChapterSixth_btn=findViewById(R.id.MaoGaiChapterSixth_btn);
        mMaoGaiChapterSeventh_btn=findViewById(R.id.MaoGaiChapterSeventh_btn);
        mMaoGaiSummary_btn=findViewById(R.id.MaoGaiSummary_btn);

        mMaoGaiChapterFirst_btn.setOnClickListener(this::onClick);
        mMaoGaiChapterSecond_btn.setOnClickListener(this::onClick);
        mMaoGaiChapterThird_btn.setOnClickListener(this::onClick);
        mMaoGaiChapterFourth_btn.setOnClickListener(this::onClick);
        mMaoGaiChapterFifth_btn.setOnClickListener(this::onClick);
        mMaoGaiChapterSixth_btn.setOnClickListener(this::onClick);
        mMaoGaiChapterSeventh_btn.setOnClickListener(this::onClick);
        mMaoGaiSummary_btn.setOnClickListener(this::onClick);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.MaoGaiChapterFirst_btn:
//                Intent intent_first=new Intent(MaoGaiMainActivity.this, MaoGaiChapterFirst.class);
//                startActivity(intent_first);
                Toast.makeText(this,"第一章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaoGaiChapterSecond_btn:
//                Intent intent_second=new Intent(MaoGaiMainActivity.this, MaoGaiChapterSecond.class);
//                startActivity(intent_second);
                Toast.makeText(this,"第二章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaoGaiChapterThird_btn:
//                Intent intent_third=new Intent(MaoGaiMainActivity.this, MaoGaiChapterThird.class);
//                startActivity(intent_third);
                Toast.makeText(this,"第三章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaoGaiChapterFourth_btn:
//                Intent intent_fourth=new Intent(MaoGaiMainActivity.this, MaoGaiChapterFourth.class);
//                startActivity(intent_fourth);
                Toast.makeText(this,"第四章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaoGaiChapterFifth_btn:
//                Intent intent_fifth=new Intent(MaoGaiMainActivity.this, MaoGaiChapterFifth.class);
//                startActivity(intent_fifth);
                Toast.makeText(this,"第五章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaoGaiChapterSixth_btn:
//                Intent intent_sixth=new Intent(MaoGaiMainActivity.this, MaoGaiChapterSixth.class);
//                startActivity(intent_sixth);
                Toast.makeText(this,"第六章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaoGaiChapterSeventh_btn:
//                Intent intent_seventh=new Intent(MaoGaiMainActivity.this, MaoGaiChapterSeventh.class);
//                startActivity(intent_seventh);
                Toast.makeText(this,"第七章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.MaoGaiSummary_btn:
                Intent intent_summary=new Intent(MaoGaiMainActivity.this, MaoGaiSummary.class);
                startActivity(intent_summary);
                break;
        }
    }
}
