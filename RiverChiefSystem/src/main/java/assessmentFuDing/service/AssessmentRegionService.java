package assessmentFuDing.service;

import common.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface AssessmentRegionService {
    List<AssessRegionDateStencil> findRegionDataMonthAvg(String regionId, String[] time);

    List<AssessRegionDateStencil> findRegionDataMonthSum(String regionId, String[] time);

    List<RegionPatrolInfo> queryRegionContrastSUM(List<String> regionIdsLike, Date startTime, Date endTime);
    
	public List<RegionPatrolInfo> queryRegionName(String[] regionIds);

	public ArrayList<Integer> queryRegionPeopleNum(String[] regionIds);

	
}
