package quartzJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import common.util.SpringContextHolder;
import quartzJob.service.EvaluationMonthlyService;
import quartzJob.service.SensorDailyService;

/**
 * 定时任务运行工厂类
 * 
 * @author 李玮勤
 * @date 2017/10/18
 */
public class JobFactory implements Job {
		@Autowired
		private SensorDailyService sensorDailyService = SpringContextHolder.getBean(SensorDailyService.class);
		@Autowired
		private EvaluationMonthlyService evaluationMonthlyService = SpringContextHolder.getBean(EvaluationMonthlyService.class);
		
		
	  public void execute(JobExecutionContext context) throws JobExecutionException {
	    System.out.println("+++++++++++++++++++++++++++++");
	    JobSchedule scheduleJob = (JobSchedule)context.getMergedJobDataMap().get("scheduleJob");
	    
	    if("每日任务求和插入".equals(scheduleJob.getJobName())){
	    	System.out.println("任务名称 = [" + scheduleJob.getJobName()+ "]");
	    	sensorDailyService.insertDailyData();
	    }else if("每日任务每天1点删除超过1个月数据".equals(scheduleJob.getJobName())){
	    	System.out.println("任务名称 = [" + scheduleJob.getJobName()+ "]");
	    	sensorDailyService.deleteOutdatedData();
	    }else if("每月1号凌晨统计考评分数".equals(scheduleJob.getJobName())){
	    	System.out.println("任务名称 = [" + scheduleJob.getJobName()+ "]");
	    	evaluationMonthlyService.insertMonthlyData();
	    }
	    
	  }
	}