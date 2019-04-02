package quartzJob.mapper;

import org.apache.ibatis.annotations.Param;

public interface EvaluationMonthlyMapper {

	public void insertPatrolDaysMonthly(@Param("lastMonthLike") String lastMonthLike,@Param("lastMonthFirst") String lastMonthFirst);

	public void insertPatrolTimeMonthly(@Param("lastMonthLike") String lastMonthLike,@Param("lastMonthFirst") String lastMonthFirst);

	public void insertEventNumMonthly(@Param("lastMonthLike") String lastMonthLike,@Param("lastMonthFirst") String lastMonthFirst);

	public void insertPatrolMileageLowerMonthly(@Param("lastMonthLike") String lastMonthLike,@Param("lastMonthFirst") String lastMonthFirst);

	public void insertPatrolMileageNoZeroMonthly(@Param("lastMonthLike")String lastMonthLike,@Param("lastMonthFirst") String lastMonthFirst);

	public void insertLastMonthCriteria(@Param("lastMonthFirst")String lastMonthFirst, @Param("currentMonthFirst")String currentMonthFirst);

}
 