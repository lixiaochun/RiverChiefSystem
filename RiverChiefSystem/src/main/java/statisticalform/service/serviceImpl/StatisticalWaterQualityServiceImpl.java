package statisticalform.service.serviceImpl;

import common.model.StatisticsWaterQuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statisticalform.mapper.StatisticalWaterQualityMapper;
import statisticalform.service.StatisticalWaterQualityService;

import java.util.List;

@Service
public class StatisticalWaterQualityServiceImpl implements StatisticalWaterQualityService {

    @Autowired
    private StatisticalWaterQualityMapper statisticalWaterQualityMapper;


    @Override
    public List<StatisticsWaterQuality> countByType(String regionId) {
        return statisticalWaterQualityMapper.countByType(regionId);
    }

    @Override
    public int countByRegion(String regionId) {
        return statisticalWaterQualityMapper.countByRegion(regionId);
    }

    @Override
    public int countByWarn(String regionId) {
        return statisticalWaterQualityMapper.countByWarn(regionId);
    }

    @Override
    public int countPollSrcByType(String type, String regionId) {
        return statisticalWaterQualityMapper.countPollSrcByType(type, regionId);
    }


}
