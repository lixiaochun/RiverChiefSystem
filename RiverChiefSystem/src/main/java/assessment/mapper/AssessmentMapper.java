package assessment.mapper;

import common.model.Assessment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentMapper {
    boolean insert(Assessment record);

    boolean updateByPrimaryKey(Assessment record);

    Assessment selectExistByRiverId(@Param("year") Integer year, @Param("month") Integer month, @Param("riverId") String riverId, @Param("regionId") String regionId);

    Assessment avgByRiverInSeason(@Param("year") Integer year, @Param("season") Integer season, @Param("riverId") String riverId, @Param("regionId") String regionId);

    Assessment avgByRiverInYear(@Param("year") Integer year, @Param("riverId") String riverId, @Param("regionId") String regionId);

    List<Assessment> avgByRiverListInMonth(@Param("year") Integer year, @Param("month") Integer month, @Param("regionList") List<String> regionList);

    List<Assessment> avgByRiverListInSeason(@Param("year") Integer year, @Param("season") Integer season, @Param("regionList") List<String> regionList);

    List<Assessment> avgByRiverListInYear(@Param("year") Integer year, @Param("regionList") List<String> regionList);

    boolean deleteByPrimaryKey(String pkid);

    Assessment selectByPrimaryKey(String pkid);
}