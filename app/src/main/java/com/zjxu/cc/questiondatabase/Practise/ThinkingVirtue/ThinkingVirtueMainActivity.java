package com.zjxu.cc.questiondatabase.Practise.ThinkingVirtue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zjxu.cc.questiondatabase.R;


public class ThinkingVirtueMainActivity extends Activity implements View.OnClickListener{

    private Button mThinkingVirtueChapterFirst_btn;
    private Button mThinkingVirtueChapterSecond_btn;
    private Button mThinkingVirtueChapterThird_btn;
    private Button mThinkingVirtueChapterFourth_btn;
    private Button mThinkingVirtueChapterFifth_btn;
    private Button mThinkingVirtueChapterSixth_btn;
    private Button mThinkingVirtueChapterSeventh_btn;
    private Button mThinkingVirtueSummary_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thinking_virtue_main);

        mThinkingVirtueChapterFirst_btn=findViewById(R.id.ThinkingVirtueChapterFirst_btn);
        mThinkingVirtueChapterSecond_btn=findViewById(R.id.ThinkingVirtueChapterSecond_btn);
        mThinkingVirtueChapterThird_btn=findViewById(R.id.ThinkingVirtueChapterThird_btn);
        mThinkingVirtueChapterFourth_btn=findViewById(R.id.ThinkingVirtueChapterFourth_btn);
        mThinkingVirtueChapterFifth_btn=findViewById(R.id.ThinkingVirtueChapterFifth_btn);
        mThinkingVirtueChapterSixth_btn=findViewById(R.id.ThinkingVirtueChapterSixth_btn);
        mThinkingVirtueChapterSeventh_btn=findViewById(R.id.ThinkingVirtueChapterSeventh_btn);
        mThinkingVirtueSummary_btn=findViewById(R.id.ThinkingVirtueSummary_btn);

        mThinkingVirtueChapterFirst_btn.setOnClickListener(this::onClick);
        mThinkingVirtueChapterSecond_btn.setOnClickListener(this::onClick);
        mThinkingVirtueChapterThird_btn.setOnClickListener(this::onClick);
        mThinkingVirtueChapterFourth_btn.setOnClickListener(this::onClick);
        mThinkingVirtueChapterFifth_btn.setOnClickListener(this::onClick);
        mThinkingVirtueChapterSixth_btn.setOnClickListener(this::onClick);
        mThinkingVirtueChapterSeventh_btn.setOnClickListener(this::onClick);
        mThinkingVirtueSummary_btn.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ThinkingVirtueChapterFirst_btn:
//                Intent intent_first=new Intent(ThinkingVirtueMainActivity.this, ThinkingVirtueChapterFirst.class);
//                startActivity(intent_first);
                Toast.makeText(this,"第一章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.ThinkingVirtueChapterSecond_btn:
//                Intent intent_second=new Intent(ThinkingVirtueMainActivity.this, ThinkingVirtueChapterSecond.class);
//                startActivity(intent_second);
                Toast.makeText(this,"第二章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ThinkingVirtueChapterThird_btn:
//                Intent intent_third=new Intent(ThinkingVirtueMainActivity.this, ThinkingVirtueChapterThird.class);
//                startActivity(intent_third);
                Toast.makeText(this,"第三章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ThinkingVirtueChapterFourth_btn:
//                Intent intent_fourth=new Intent(ThinkingVirtueMainActivity.this, ThinkingVirtueChapterFourth.class);
//                startActivity(intent_fourth);
                Toast.makeText(this,"第四章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ThinkingVirtueChapterFifth_btn:
//                Intent intent_fifth=new Intent(ThinkingVirtueMainActivity.this, ThinkingVirtueChapterFifth.class);
//                startActivity(intent_fifth);
                Toast.makeText(this,"第五章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ThinkingVirtueChapterSixth_btn:
//                Intent intent_sixth=new Intent(ThinkingVirtueMainActivity.this, ThinkingVirtueChapterSixth.class);
//                startActivity(intent_sixth);
                Toast.makeText(this,"第六章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ThinkingVirtueChapterSeventh_btn:
//                Intent intent_seventh=new Intent(ThinkingVirtueMainActivity.this, ThinkingVirtueChapterSeventh.class);
//                startActivity(intent_seventh);
                Toast.makeText(this,"第七章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ThinkingVirtueSummary_btn:
                Intent intent_summary=new Intent(ThinkingVirtueMainActivity.this, ThinkingVirtueSummary.class);
                startActivity(intent_summary);
                break;
        }
    }
}