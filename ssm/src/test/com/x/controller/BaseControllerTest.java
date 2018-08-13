package com.x.controller;

import com.x.entity.MyJob2;
import com.x.entity.MyJobDetailListener;
import com.x.entity.MyTriggerListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml",
        "classpath*:spring-mvc.xml",
        "classpath*:quartz.xml",
        "classpath*:mybatis-config.xml"
        })
public class BaseControllerTest {

    @Test
    public void say(){
        System.out.println("呼啦啦");
    }

    //    SimpleTrigger  实现的每隔5秒执行一次任务
    @Test
    public void test1() {

        try {
            //1.创建JobDetail  任务描述
            /// define the job and tie it to our MyJob class
            JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).
                    withIdentity("myJob1", "group1").build();
            //JobDetail jobDetail = newJob(MyJob.class).build();  静态导入了JobBuilder 类
            //2.创建一个Trigger
            //startAt 指定开始时间
            //withSchedule : 使用的调度器  指定重复时间
            SimpleTrigger trigger = TriggerBuilder.
                    newTrigger().startAt(new Date(System.currentTimeMillis() + 3000)).
                    withSchedule(SimpleScheduleBuilder.
                            simpleSchedule().withIntervalInSeconds(5)
                            .repeatForever()).
                    withIdentity("myTrigger", "group1").build();
            //3. 创建一个调度器
            //得到调度器工厂
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            //得到一个调度器
            Scheduler scheduler = schedulerFactory.getScheduler();

            //4.注册job 和 trigger 到调度器中
            scheduler.scheduleJob(jobDetail, trigger);

            //5.开始执行
            scheduler.start();

            //睡50秒
            Thread.sleep(1000 * 50);

            scheduler.shutdown();
        } catch (Exception e) {

        }

    }

    //    排除特殊日期 特殊日期不执行任务
    @Test
    public void test2() {
        try {
            //1,创建调度器工厂
            SchedulerFactory factory = new StdSchedulerFactory();
            //得到调度器
            Scheduler scheduler = factory.getScheduler();
            //创建一个表示 五一的Calendar对象
            Calendar calendar = new GregorianCalendar();//Date  Calendar
            calendar.set(Calendar.MONTH, 4);//0
            calendar.set(Calendar.DATE, 1);
            //创建一个表示十月一对象
            Calendar calendar1 = new GregorianCalendar();//Date  Calendar
            calendar1.set(Calendar.MONTH, 9);//0
            calendar1.set(Calendar.DATE, 1);

            //创建AnnualCalendar对象 排除 五一 十一
            AnnualCalendar annualCalendar = new AnnualCalendar();

            annualCalendar.setDayExcluded(calendar, true);//不包含
            annualCalendar.setDayExcluded(calendar1, true);


            //添加不触发触发器的日历对象
            scheduler.addCalendar("myCalendar", annualCalendar, false, false);

            //2,创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).
                    withIdentity("myJob").build();
            //3,创建触发器
            //cronExpression  corn表达式
            CronTrigger trigger = TriggerBuilder.
                    newTrigger().withSchedule
                    (CronScheduleBuilder.cronSchedule("0/5 * 11 * * ?"))
                    .withIdentity("myCronTrigger", "goup1").modifiedByCalendar("myCalendar").build();

            //4,注册任务和触发器
            scheduler.scheduleJob(jobDetail, trigger);

            //5.开始任务
            scheduler.start();
        } catch (Exception e) {

        }
    }

    //    CronTrigger  实现的每隔5秒执行一次任务
    @Test
    public void test3() {
        try {
            //1,创建调度器工厂
            SchedulerFactory factory = new StdSchedulerFactory();
            //得到调度器
            Scheduler scheduler = factory.getScheduler();

            //2,创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).
                    withIdentity("myJob").build();
            //3,创建触发器
            //cronExpression  corn表达式
            CronTrigger trigger = TriggerBuilder.
                    newTrigger().withSchedule
                    (CronScheduleBuilder.cronSchedule("0/5 * 10 * * ?"))
                    .withIdentity("myCronTrigger", "goup1").build();

            //4,注册任务和触发器
            scheduler.scheduleJob(jobDetail, trigger);

            //5.开始任务
            scheduler.start();
        }catch (Exception e){

        }

    }

    //CronTrigger 指定某些日期 或者 时间 不执行任务
    @Test
    public void test4() {
        try {
            //1,构建调度器工厂对象
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            //得到日程对象
            Scheduler scheduler = schedulerFactory.getScheduler();

            //2,创建CronCalendar对象  指定某些日期 或者 时间 不执行任务
            //国庆不执行
            CronCalendar cronCalendar = new CronCalendar("* * * 1-7 10 ?");

            //3.把不执行任务的Calendar对象 添加给 调度器

            scheduler.addCalendar("MyCronCalendar", cronCalendar, false, false);

            //4,创建JobDeatil
            JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).withIdentity("myJob","group1").build();
            //5,创建触发器对象

            CronTrigger trigger  = TriggerBuilder.newTrigger().
                    withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?")).modifiedByCalendar("MyCronCalendar").build();

            //6.注册触发器和任务
            scheduler.scheduleJob(jobDetail, trigger);

            //7.开始执行
            scheduler.start();
        }catch (Exception e){

        }

    }

    //CronTrigger 指定某些日期 或者 时间 不执行任务,按星期
    @Test
    public void test5() {
        try {
            //1,构建调度器工厂对象
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            //得到日程对象
            Scheduler scheduler = schedulerFactory.getScheduler();

            System.out.println(new GregorianCalendar().THURSDAY);//
            System.out.println(new GregorianCalendar().FRIDAY);//

            //周天 1
            //周四 5

            //创建一星期的日历
            WeeklyCalendar weeklyCalendar = new WeeklyCalendar();
            // weeklyCalendar.setDaysExcluded(new boolean[] {true,true,true,false,false,true,true,true});
            //参数是Calendar指定的星期数
            weeklyCalendar.setDayExcluded(6,true);

            scheduler.addCalendar("myWeeklyCalendar", weeklyCalendar, false, false);

            //创建一个触发器
            SimpleTrigger trigger = TriggerBuilder.newTrigger().withSchedule
                    (SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).

                            repeatForever()).modifiedByCalendar("myWeeklyCalendar").build();
            //创建JobDetail  任务

            JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).withIdentity("myJob","group1").build();

            //  注册job和trigger
            scheduler.scheduleJob(jobDetail,trigger);

            //执行任务
            scheduler.start();
        }catch (Exception e){

        }

    }

    //quartz监听器的使用
    @Test
    public void test6() {
        try {
            //1,构建调度器工厂对象
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            //得到日程对象
            Scheduler scheduler = schedulerFactory.getScheduler();


            //创建一个触发器
            SimpleTrigger trigger = TriggerBuilder.newTrigger().withSchedule
                    (SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).
                            repeatSecondlyForTotalCount(5)).withIdentity("myTrigger", "group1").build();
            //创建JobDetail  任务

            JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).withIdentity("myJob","group1").build();

            //根据name匹配trigger
            KeyMatcher<TriggerKey> triggerKey =   KeyMatcher.keyEquals(trigger.getKey());

            //根据name匹配作业
            KeyMatcher<JobKey> jonKey = KeyMatcher.keyEquals(jobDetail.getKey());

            //创建监听器
            MyJobDetailListener myJobDetailListener = new MyJobDetailListener();
            MyTriggerListener myTriggerListener = new MyTriggerListener();

            //把监听器注册到scheduler
            scheduler.getListenerManager().addJobListener(myJobDetailListener);;
            scheduler.getListenerManager().addTriggerListener(myTriggerListener);




            //  注册job和trigger
            scheduler.scheduleJob(jobDetail,trigger);

            //执行任务
            scheduler.start();
        }catch (Exception e){

        }

    }


}