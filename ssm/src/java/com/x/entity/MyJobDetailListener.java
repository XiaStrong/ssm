package com.x.entity;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Created by Xia_Q on 2018/3/24.
 */

public class MyJobDetailListener implements JobListener {

	public String getName() {
		return "MyJobListener";
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		System.out.println(context.getJobDetail().getKey()+"任务将要被执行");
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println(context.getJobDetail().getKey()+"任务已经在执行");
	}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		System.out.println(context.getJobDetail().getKey()+"任务执行完毕");
	}

}
