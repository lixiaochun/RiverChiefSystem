package assessmentNanPing.mapper;

import common.model.AssessCompleteScore;
import common.model.AssessTypeAndScore;
import common.model.StringAndDouble;
import common.model.StringAndInt;
import common.model.UserBasisInfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AssessRegionMapper {
    List<AssessTypeAndScore> findScore(@Param("regionId") String regionId, @Param("time") String time);

    List<AssessCompleteScore> findCompleteLastYear(@Param("start") String start, @Param("end") String end, @Param("regionId") String regionId);

    Integer queryRegionPatrolTime(@Param("regionId") String regionId, @Param("date") String date);

    Double queryRegionPatrolMileage(@Param("regionId") String regionId, @Param("date") String date);

    Integer queryRegionEventNum(@Param("regionId") String regionId, @Param("date") String date);

    Integer queryUserSum(@Param("regionId") String regionId);

    Integer queryRegionPatrolDays(@Param("regionId") String regionId, @Param("date") String date);

    List<StringAndInt> queryRegionPatrolTimeEach(@Param("regionId") String regionId, @Param("date") String date);

    List<StringAndDouble> queryRegionPatrolMileageEach(@Param("regionId") String regionId, @Param("date") String date);

    List<StringAndInt> queryRegionEventNumEach(@Param("regionId") String regionId, @Param("date") String date);

    List<StringAndInt> queryRegionPatrolDaysEach(@Param("regionId") String regionId, @Param("date") String date);
    
}
