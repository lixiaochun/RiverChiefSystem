package statisticalform.mapper;

import common.model.StatisticsWaterQuality;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticalWaterQualityMapper {
    List<StatisticsWaterQuality> countByType(@Param("regionId") String regionId);

    int countByRegion(@Param("regionId") String regionId);

    int countByWarn(@Param("regionId") String regionId);
    int countPollSrcByType(@Param("type") String type, @Param("regionId") String regionId);
}
