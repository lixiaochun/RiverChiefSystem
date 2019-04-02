package assessmentNanPing.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.model.EvaluationCriteria;
import common.model.EvaluationPieCharts;

public interface EvaluationService {

	public int queryUserPatrolDays(int userId, String dateStr);

	public int queryUserPatrolTime(int userId, String dateStr);

	public double queryUserPatrolMileage(int userId, String dateStr);

	public int queryUserEventNum(int userId, String dateStr);

	public List<EvaluationPieCharts> queryUserPieChart(int userId, String dateSubOneMonth);

	public String downLoadUserRank(HttpServletRequest request, HttpServletResponse response, String dateStr);

	public List<EvaluationCriteria> queryCurrentCriteria();

	public void updateCurrentCriteria(List<EvaluationCriteria> eaList);

}
