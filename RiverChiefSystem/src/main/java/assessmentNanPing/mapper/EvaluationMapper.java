package assessmentNanPing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import common.model.EvaluationCriteria;
import common.model.EvaluationPieCharts;
import common.model.UserRank;

public interface EvaluationMapper {

	public int queryUserPatrolDays(@Param("userId")int userId, @Param("dateStr")String dateStr);

	public int queryUserPatrolTime(@Param("userId")int userId, @Param("dateStr")String dateStr);

	public double queryUserPatrolMileage(@Param("userId")int userId, @Param("dateStr")String dateStr);

	public int queryUserEventNum(@Param("userId")int userId, @Param("dateStr")String dateStr);

	public List<EvaluationPieCharts> queryUserPieChart(@Param("userId")int userId, @Param("dateStr")String dateSubOneMonth);

	public List<UserRank> queryUserRank(@Param("dateStr")String dateStr);

	public List<EvaluationCriteria> queryCurrentCriteria(@Param("currentDay")String currentDay);

	public void updateCurrentCriteria(@Param("list")List<EvaluationCriteria> eaList);

}
