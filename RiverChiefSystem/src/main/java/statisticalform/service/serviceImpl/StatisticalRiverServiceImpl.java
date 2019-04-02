package statisticalform.service.serviceImpl;

import common.model.StatisticsRiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statisticalform.mapper.StatisticalRiverMapper;
import statisticalform.service.StatisticalRiverService;

import java.util.List;

@Service
public class StatisticalRiverServiceImpl implements StatisticalRiverService {
    @Autowired
    private StatisticalRiverMapper statisticalRiverMapper;

    @Override
    public List<StatisticsRiver> countByLevel(String regionId) {
        return statisticalRiverMapper.countByLevel(regionId);
    }

    @Override
    public int countRiverByParent(String regionId) {
        return statisticalRiverMapper.countRiverByParent(regionId);
    }

    @Override
    public int countUserByParent(String regionId) {
        return statisticalRiverMapper.countUserByParent(regionId);
    }
}
