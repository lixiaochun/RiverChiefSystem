package statisticalform.service;

import common.model.StatisticsRiver;

import java.util.List;

public interface StatisticalRiverService {
    List<StatisticsRiver> countByLevel(String regionId);

    int countRiverByParent(String regionId);

    int countUserByParent(String regionId);
}
