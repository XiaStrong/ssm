package com.x.entity;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

/**
 * Created by Xia_Q on 2018/3/24.
 */

public class MyTriggerListener implements TriggerListener {

	public MyTriggerListener() {
		System.out.println("实例化TriggerListener");
	}
	
	
	
	public String getName() {
		return "MyTriggerListener";
	}

	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		System.out.println(trigger.getJobKey()+"被触发…………");
	}

	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		  System.out.println(trigger.getKey()+"将要执行");
		return false;
	}

	public void triggerMisfired(Trigger trigger) {
		  System.out.println(trigger.getKey()+"错过执行");
	}

	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		  System.out.println(trigger.getKey()+"触发完毕！！！");
	}

}
