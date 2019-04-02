package assessmentFuDing.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import common.model.UserBasisInfo;
import common.model.UserPatrolInfo;

public interface AssessmentUserMapper {

	List<UserBasisInfo> querySelectedRegion(@Param("list")String[] regionIds);

	List<UserPatrolInfo> queryUsersPatrolInfo(@Param("list")String[] userIds,@Param("startTime")Date startTime,@Param("endTime")Date endTime);

}
