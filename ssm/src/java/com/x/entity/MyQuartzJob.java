package com.x.entity;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Created by Xia_Q on 2018/3/24.
 */
//这是spring整合中需要用到的
public class MyQuartzJob extends QuartzJobBean{
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("hello  Quartz！！！"+jobExecutionContext.getJobDetail().getKey()+"::"+new Date());
    }
}
