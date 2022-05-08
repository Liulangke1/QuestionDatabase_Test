package com.zjxu.cc.questiondatabase.injection;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import javax.inject.Scope;

@Scope//如果使用了@Scope注解了该类，注入器会缓存第一次创建的实例，然后每次重复注入缓存的实例，而不会再创建新的实例 单例
@Retention(RUNTIME)//注解@Retention可以用来修饰注解，是注解的注解，称为元注解。
// 按生命周期来划分可分为3类：
//1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
//2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
//3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
//这3个生命周期分别对应于：Java源文件(.java文件) ---> .class文件 ---> 内存中的字节码。
public @interface PerActivity {
}


















