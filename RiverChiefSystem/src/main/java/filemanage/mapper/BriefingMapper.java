package filemanage.mapper;

import common.model.Briefing;
import common.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BriefingMapper {
    boolean deleteByPrimaryKey(Integer briefingId);

    boolean insert(Briefing record);

    Briefing selectByPrimaryKey(Integer briefingId);

    boolean updateByPrimaryKeyWithBLOBs(Briefing record);

    List<Briefing> selectByUser(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") int userId, @Param("status") String status, @Param("title") String title);

    List<Briefing> selectByRegion(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("regionId") String regionId, @Param("status") String status, @Param("title") String title);

    List<Briefing> selectSelf(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") int userId, @Param("status") String status, @Param("title") String title);

    List<User> findLeader(@Param("userId") int userId);

    User findOrganization(@Param("userId") int userId);

    Integer findUnderling(@Param("userId") int userId);

    boolean updateStatus(Briefing record);

    boolean updateScore(@Param("briefingScore") double briefingScore, @Param("briefingId") Integer briefingId);

    List<Briefing> selectListForPublic(@Param("numstart") int numstart, @Param("size") int size);
}