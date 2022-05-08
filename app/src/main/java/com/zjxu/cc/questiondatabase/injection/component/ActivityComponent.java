package com.zjxu.cc.questiondatabase.injection.component;


import com.zjxu.cc.questiondatabase.MainActivity;
import com.zjxu.cc.questiondatabase.injection.PerActivity;
import com.zjxu.cc.questiondatabase.injection.module.ActivityModule;
import com.zjxu.cc.questiondatabase.main.exam.ExamFragment;
import com.zjxu.cc.questiondatabase.main.mine.MineFragment;
import com.zjxu.cc.questiondatabase.main.practise.PractiseFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(PractiseFragment practiseFragment);

    void inject(ExamFragment examFragment);

    void inject(MineFragment mineFragment);
}























