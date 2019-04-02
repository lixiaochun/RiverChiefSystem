package filemanage.service;

import common.model.Briefing;
import common.model.User;

import java.util.List;

public interface BriefingService {
    boolean deleteByPrimaryKey(Integer briefingId);

    boolean insert(String title, String body, String summary, String regionid, String struserId, String sendto);

    Briefing selectByPrimaryKey(Integer briefingId);

    boolean updateByPrimaryKeyWithBLOBs(String id, String title, String body, String summary, String struserId, String regionid, String sendto);

    List<Briefing> selectByUser(String startTime, String endTime, int userId, String status, String title);

    List<Briefing> selectByRegion(String startTime, String endTime, String regionId, String status, String title);

    List<Briefing> selectSelf(String startTime, String endTime, int userId, String status, String title);

    List<User> findLeader(int userId);

    User findOrganization(int userId);

    Integer findUnderling(int userId);

    boolean updateStatus(Briefing record);

    boolean updateScore(double score, Integer briefingId);

    List<Briefing> findListForPublic(String page, String size);
}
