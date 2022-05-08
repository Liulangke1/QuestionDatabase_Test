package com.zjxu.cc.questiondatabase.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zjxu.cc.questiondatabase.main.exam.ExamFragment;
import com.zjxu.cc.questiondatabase.main.mine.MineFragment;
import com.zjxu.cc.questiondatabase.main.practise.PractiseFragment;


public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    public MainFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i){
        Fragment fragment=null;
        switch (i){
            case 0:
                fragment=new PractiseFragment();
                break;
            case 1:
                fragment=new ExamFragment();
                break;
            case 2:
                fragment=new MineFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount(){
        return 3;
    }
}
