package assessment.service.impl;

import assessment.mapper.AssessmentMapper;
import assessment.service.AssessmentService;
import common.model.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService {
    @Autowired
    private AssessmentMapper assessmentMapper ;

    @Override
    public boolean insert(Assessment record) {
        return assessmentMapper.insert(record);
    }

    @Override
    public boolean updateByPrimaryKey(Assessment record) {
        return assessmentMapper.updateByPrimaryKey(record);
    }

    @Override
    public Assessment selectExistByRiverId(Integer year, Integer month, String riverId, String regionId) {
        return assessmentMapper.selectExistByRiverId(year, month, riverId, regionId);
    }

    @Override
    public Assessment avgByRiverInSeason(Integer year, Integer season, String riverId, String regionId) {
        return assessmentMapper.avgByRiverInSeason(year, season, riverId, regionId);
    }

    @Override
    public Assessment avgByRiverInYear(Integer year, String riverId, String regionId) {
        return assessmentMapper.avgByRiverInYear(year, riverId, regionId);
    }

    @Override
    public List<Assessment> avgByRiverListInMonth(Integer year, Integer month, List<String> riverList) {
        return assessmentMapper.avgByRiverListInMonth(year, month, riverList);
    }

    @Override
    public List<Assessment> avgByRiverListInSeason(Integer year, Integer season, List<String> riverList) {
        return assessmentMapper.avgByRiverListInSeason(year, season, riverList);
    }

    @Override
    public List<Assessment> avgByRiverListInYear(Integer year, List<String> riverList) {
        return assessmentMapper.avgByRiverListInYear(year, riverList);
    }

    @Override
    public boolean deleteByPrimaryKey(String pkid) {
        return assessmentMapper.deleteByPrimaryKey(pkid);
    }

    @Override
    public Assessment selectByPrimaryKey(String pkid) {
        return assessmentMapper.selectByPrimaryKey(pkid);
    }
}
