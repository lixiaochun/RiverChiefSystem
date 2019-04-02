package assessmentNanPing.service;

import common.model.*;

import java.util.List;
import java.util.Map;

public interface AssessRegionService {
    AssessRegion findByTimeAndRegion(String regionId, String time) throws Exception;

    List<AssessCompleteScore> findCompleteLastYear(String start, String end, String regionId) throws Exception;

    List<AssessTypeAndScore> findRegionData(String regionId, String time);

    List<StringAndInt> queryRegionPatrolTimeEach(String regionId, String date);

    List<StringAndDouble> queryRegionPatrolMileageEach(String regionId, String date);

    List<StringAndInt> queryRegionEventNumEach(String regionId, String date);

    List<StringAndInt> queryRegionPatrolDaysEach(String regionId, String date);
}
