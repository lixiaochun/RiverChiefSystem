package quartzJob.service.serviceImpl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.util.DateHelper;
import quartzJob.mapper.EvaluationMonthlyMapper;
import quartzJob.service.EvaluationMonthlyService;


@Service("EvaluationMonthlyService")
public class EvaluationServiceImpl implements EvaluationMonthlyService{
    @Autowired
	private EvaluationMonthlyMapper evaluationMonthlyMapper;
	
	public void insertMonthlyData() {

		Date date = new Date();
		String currentMonth = DateHelper.formatDateByFormat(date, "yyyy-MM");
		String currentMonthFirst = currentMonth+"-01";
		String lastMonthLike = DateHelper.getPreviousMonth(currentMonth)+"%";
		String lastMonthFirst = lastMonthLike +"-01";
		
		evaluationMonthlyMapper.insertPatrolDaysMonthly(lastMonthLike,lastMonthFirst);//插入上一个月每个用户的巡河完成率得分情况
		evaluationMonthlyMapper.insertPatrolTimeMonthly(lastMonthLike,lastMonthFirst);//插入上一个月每个用户的巡河次数得分情况
		evaluationMonthlyMapper.insertPatrolMileageLowerMonthly(lastMonthLike,lastMonthFirst);//插入上一个月每个用户的巡河里程得0分的用户
		evaluationMonthlyMapper.insertPatrolMileageNoZeroMonthly(lastMonthLike,lastMonthFirst);
		evaluationMonthlyMapper.insertEventNumMonthly(lastMonthLike,lastMonthFirst);//插入上一个月每个用户的上报事件数得分情况
		
		evaluationMonthlyMapper.insertLastMonthCriteria(lastMonthFirst,currentMonthFirst);//  插入上一个月的评分规则作为当前月的默认规则
		
	}
    

	

}
