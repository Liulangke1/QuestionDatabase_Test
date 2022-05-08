package com.zjxu.cc.questiondatabase;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.WhiteToastStyle;
import com.zjxu.cc.questiondatabase.Permission.PermissionInterceptor;
import com.zjxu.cc.questiondatabase.base.BaseActivity;
import com.zjxu.cc.questiondatabase.main.MainFragmentPagerAdapter;
import com.zjxu.cc.questiondatabase.main.MainPresenter;
import com.zjxu.cc.questiondatabase.main.MainView;
import com.zjxu.cc.questiondatabase.view.NoSlideViewPager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainView, MainPresenter>
        implements MainView{
    @Inject
    MainPresenter mainPresenter;
    private NoSlideViewPager viewPager;
    private RadioGroup main_radio_group;
    private RadioButton main_radio_practise;
    private RadioButton main_radio_exam;
    private RadioButton main_radio_mine;

    @Override
    protected MainPresenter createPresenter() {
        return mainPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);

        //获取权限
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 初始化吐司工具类
//                ToastUtils.init(new AppApplication(), new WhiteToastStyle());

                // 设置权限申请拦截器（全局设置）
                XXPermissions.setInterceptor(new PermissionInterceptor());
                XXPermissions.with(MainActivity.this)
                        // 不适配 Android 11 可以这样写
                        //.permission(Permission.Group.STORAGE)
                        // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                        .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                        .request(new OnPermissionCallback() {
                            @Override
                            public void onGranted(List<String> permissions, boolean all) {
                                if (all) {
//                                    toast("获取存储权限成功");
                                }
                            }
                            @Override
                            public void onDenied(List<String> permissions, boolean never) {
                                if (never) {
//                                    toast("被永久拒绝授权，请手动授予存储权限");
                                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                    XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                                } else {
//                                    toast("获取存储权限失败");
                                }
                            }
                        });

            }
        });
        thread.start();

        viewPager=findViewById(R.id.main_vp_select);
        main_radio_group=findViewById(R.id.main_radio_group);
        main_radio_practise=findViewById(R.id.main_radio_practise);
        main_radio_exam=findViewById(R.id.main_radio_exam);
        main_radio_mine=findViewById(R.id.main_radio_mine);

        String DB_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/databases/";
        String DB_NAME = "question.db";
        //应用启动时，判断数据库是否存在，不存在则将提前打包好的数据库文件复制到数据库目录下
        //数据库目录不存在时，创建数据库目录
        if ((new File(DB_PATH + DB_NAME).exists()) == false) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
        }

        //定义输入输出流，用于复制文件
        try {
            InputStream is = getBaseContext().getAssets().open(DB_NAME);
            OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            //刷新输出流，关闭输入输出流
            os.flush();
            os.close();
            is.close();
            Log.i("Log","---------写入成功----------");

        } catch (IOException e) {
            Log.i("Log","---------写入失败----------");
            e.printStackTrace();
        }

        MainFragmentPagerAdapter pagerAdapter=new MainFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setSlidable(true);
        main_radio_practise.setChecked(true);
        main_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_radio_practise:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.main_radio_exam:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.main_radio_mine:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    @Override
    protected void injectDependencies() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showToast(String s) {}
}