package statisticalform.service;


import common.model.StatisticsWaterQuality;

import java.util.List;

public interface StatisticalWaterQualityService {

    List<StatisticsWaterQuality> countByType(String regionId);

    int countByRegion(String regionId);

    int countByWarn(String regionId);
    int countPollSrcByType(String type, String regionId);
}
