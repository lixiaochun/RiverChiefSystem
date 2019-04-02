package quartzJob;

import java.util.List;

import javax.servlet.ServletContext;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import quartzJob.service.JobScheduleService;

public  class JobStart implements ServletContextAware{
	
	@Autowired
	private  JobScheduleService jobScheduleService;
	
	public void setServletContext(ServletContext arg0) {
		Scheduler scheduler = null;
			try {
				scheduler = StdSchedulerFactory.getDefaultScheduler();
			} catch (SchedulerException e1) {
				e1.printStackTrace();
			}
		//这里获取所有任务信息数据
		List<JobSchedule> jobList = jobScheduleService.findAllList();
		 
		for (JobSchedule job : jobList) {
		 
		  TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		 
		  //获取trigger，相当于spring配置文件中定义的 bean id="myTrigger"
		  CronTrigger trigger = null;
		  
		try {
			trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		 
		  //不存在，创建一个触发器
		  if (null == trigger) {
		    JobDetail jobDetail = JobBuilder.newJob(JobFactory.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
		    jobDetail.getJobDataMap().put("scheduleJob", job);
		 
		    //表达式调度构建器
		    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
		 
		    //按新的cronExpression表达式构建一个新的trigger
		    trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
		 
		    try {
				scheduler.scheduleJob(jobDetail, trigger);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		    
		  } else {
		    // Trigger已存在，那么更新相应的定时设置
		    //表达式调度构建器
		    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
		 
		    //按新的cronExpression表达式重新构建trigger
		    trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		 
		    //按新的trigger重新设置job执行
		    try {
				scheduler.rescheduleJob(triggerKey, trigger);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		  }
		}
		
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
}
