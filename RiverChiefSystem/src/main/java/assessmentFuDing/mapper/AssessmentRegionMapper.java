package assessmentFuDing.mapper;

import common.model.AssessRegionDateStencil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.model.RegionPatrolInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRegionMapper {
    List<AssessRegionDateStencil> queryMonthContrast(@Param("regionId") String regionId, @Param("date") String[] date);

    List<RegionPatrolInfo> queryRegionContrastSUM(@Param("list") List<String> regionIds, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

	List<RegionPatrolInfo> queryRegionName(@Param("list")String[] regionIds);

	ArrayList<Integer> queryRegionPeopleNum(@Param("list")String[] regionIds);
}


