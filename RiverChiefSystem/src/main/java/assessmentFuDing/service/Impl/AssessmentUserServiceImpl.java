package assessmentFuDing.service.Impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import assessmentFuDing.mapper.AssessmentUserMapper;
import assessmentFuDing.service.AssessmentUserService;
import common.model.UserBasisInfo;
import common.model.UserPatrolInfo;

@Service("AssessmentUserService")
public class AssessmentUserServiceImpl implements AssessmentUserService{
	
	@Autowired
	private AssessmentUserMapper assessmentUserMapper;
	
	public List<UserBasisInfo> querySelectedRegion(String[] regionIds) {
		List<UserBasisInfo> ubiList = assessmentUserMapper.querySelectedRegion(regionIds);
		return ubiList;
	}

	public List<UserPatrolInfo> queryUsersPatrolInfo(String[] userIds, Date startTime, Date endTime) {
		List<UserPatrolInfo> upiList = assessmentUserMapper.queryUsersPatrolInfo(userIds,startTime,endTime);
		return upiList;
	}


	

}
