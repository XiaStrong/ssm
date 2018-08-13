package com.x.entity;

/**
 * Created by Xia_Q on 2018/3/24.
 */

//这是用spring直接注入的
public class MyJob {

    public MyJob() {
        System.out.println("myjob实例化…………");
    }

    //需要执行的方法
    public void printMessage() {
        System.out.println("Hello Quartz！！！MyJob的------------------");
    }
}
