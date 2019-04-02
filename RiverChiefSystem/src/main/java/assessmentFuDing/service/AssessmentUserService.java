package assessmentFuDing.service;

import java.util.Date;
import java.util.List;

import common.model.UserBasisInfo;
import common.model.UserPatrolInfo;

public interface AssessmentUserService {
	List<UserBasisInfo> querySelectedRegion(String[] regionIds);
	List<UserPatrolInfo> queryUsersPatrolInfo(String[] userIds, Date startTime, Date endTime);
}
