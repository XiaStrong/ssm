package com.x.entity;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Xia_Q on 2018/3/24.
 */

//若是代码直接操作，需要实现jop
public class MyJob2 implements Job{

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello World!!! MyJob2de ================");
    }
}
