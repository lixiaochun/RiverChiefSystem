package statisticalform.mapper;

import common.model.StatisticsRiver;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticalRiverMapper {
    List<StatisticsRiver> countByLevel(@Param("regionId") String regionId);

    int countRiverByParent(@Param("regionId") String regionId);

    int countUserByParent(@Param("regionId") String regionId);
}
