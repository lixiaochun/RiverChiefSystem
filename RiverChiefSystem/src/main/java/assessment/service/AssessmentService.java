package assessment.service;

import common.model.Assessment;

import java.util.List;

public interface AssessmentService {
    boolean insert(Assessment record);

    boolean updateByPrimaryKey(Assessment record);

    Assessment selectExistByRiverId(Integer year, Integer month, String riverId, String regionId);

    Assessment avgByRiverInSeason(Integer year, Integer season, String riverId, String regionId);

    Assessment avgByRiverInYear(Integer year, String riverId, String regionId);

    List<Assessment> avgByRiverListInMonth(Integer year, Integer month, List<String> regionList);

    List<Assessment> avgByRiverListInSeason(Integer year, Integer season, List<String> regionList);

    List<Assessment> avgByRiverListInYear(Integer year, List<String> regionList);

    boolean deleteByPrimaryKey(String pkid);

    Assessment selectByPrimaryKey(String pkid);

}
