package com.zjxu.cc.questiondatabase.Practise.Network_Security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zjxu.cc.questiondatabase.R;


public class NetworkSecurityMainActivity extends Activity implements View.OnClickListener {

    private Button mNetworkSecurityChapterFirst_btn;
    private Button mNetworkSecurityChapterSecond_btn;
    private Button mNetworkSecurityChapterThird_btn;
    private Button mNetworkSecurityChapterFourth_btn;
    private Button mNetworkSecurityChapterFifth_btn;
    private Button mNetworkSecurityChapterSixth_btn;
    private Button mNetworkSecurityChapterSeventh_btn;
    private Button mNetworkSecuritySummary_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_security_main);

        mNetworkSecurityChapterFirst_btn=findViewById(R.id.NetworkSecurityChapterFirst_btn);
        mNetworkSecurityChapterSecond_btn=findViewById(R.id.NetworkSecurityChapterSecond_btn);
        mNetworkSecurityChapterThird_btn=findViewById(R.id.NetworkSecurityChapterThird_btn);
        mNetworkSecurityChapterFourth_btn=findViewById(R.id.NetworkSecurityChapterFourth_btn);
        mNetworkSecurityChapterFifth_btn=findViewById(R.id.NetworkSecurityChapterFifth_btn);
        mNetworkSecurityChapterSixth_btn=findViewById(R.id.NetworkSecurityChapterSixth_btn);
        mNetworkSecurityChapterSeventh_btn=findViewById(R.id.NetworkSecurityChapterSeventh_btn);
        mNetworkSecuritySummary_btn=findViewById(R.id.NetworkSecuritySummary_btn);

        mNetworkSecurityChapterFirst_btn.setOnClickListener(this::onClick);
        mNetworkSecurityChapterSecond_btn.setOnClickListener(this::onClick);
        mNetworkSecurityChapterThird_btn.setOnClickListener(this::onClick);
        mNetworkSecurityChapterFourth_btn.setOnClickListener(this::onClick);
        mNetworkSecurityChapterFifth_btn.setOnClickListener(this::onClick);
        mNetworkSecurityChapterSixth_btn.setOnClickListener(this::onClick);
        mNetworkSecurityChapterSeventh_btn.setOnClickListener(this::onClick);
        mNetworkSecuritySummary_btn.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.NetworkSecurityChapterFirst_btn:
//                Intent intent_first=new Intent(NetworkSecurityMainActivity.this, NetworkSecurityChapterFirst.class);
//                startActivity(intent_first);
                Toast.makeText(this,"第一章",Toast.LENGTH_SHORT).show();

                break;
            case R.id.NetworkSecurityChapterSecond_btn:
//                Intent intent_second=new Intent(NetworkSecurityMainActivity.this, NetworkSecurityChapterSecond.class);
//                startActivity(intent_second);

                Toast.makeText(this,"第二章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.NetworkSecurityChapterThird_btn:
//                Intent intent_third=new Intent(NetworkSecurityMainActivity.this, NetworkSecurityChapterThird.class);
//                startActivity(intent_third);
                Toast.makeText(this,"第三章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.NetworkSecurityChapterFourth_btn:
//                Intent intent_fourth=new Intent(NetworkSecurityMainActivity.this, NetworkSecurityChapterFourth.class);
//                startActivity(intent_fourth);
                Toast.makeText(this,"第四章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.NetworkSecurityChapterFifth_btn:
//                Intent intent_fifth=new Intent(NetworkSecurityMainActivity.this, NetworkSecurityChapterFifth.class);
//                startActivity(intent_fifth);
                Toast.makeText(this,"第五章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.NetworkSecurityChapterSixth_btn:
//                Intent intent_sixth=new Intent(NetworkSecurityMainActivity.this, NetworkSecurityChapterSixth.class);
//                startActivity(intent_sixth);
                Toast.makeText(this,"第六章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.NetworkSecurityChapterSeventh_btn:
//                Intent intent_seventh=new Intent(NetworkSecurityMainActivity.this, NetworkSecurityChapterSeventh.class);
//                startActivity(intent_seventh);
                Toast.makeText(this,"第七章",Toast.LENGTH_SHORT).show();
                break;
            case R.id.NetworkSecuritySummary_btn:
                Intent intent_summary=new Intent(NetworkSecurityMainActivity.this, NetworkSecuritySummary.class);
                startActivity(intent_summary);
                break;
        }
    }
}