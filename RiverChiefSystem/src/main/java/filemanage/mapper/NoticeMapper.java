package filemanage.mapper;

import common.model.Notice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper {
    boolean deleteByPrimaryKey(Integer noticeId);

    boolean insert(Notice record);

    Notice selectByPrimaryKey(Integer noticeId);

    boolean updateByPrimaryKeyWithBLOBs(Notice record);

    List<Notice> selectByDirectoryAndTitleWithTime(@Param("directoryId")String directoryId, @Param("noticeTitle")String noticeTitle, @Param("startTime")String startTime, @Param("endTime")String endTime,@Param("noticeLevel")String noticeLevel);

    String countByDirectory(@Param("directoryId") String directoryId);

    String countInAWeek(@Param("directoryId") String directoryId);

    List<Notice> selectByLevel(@Param("level") String level, @Param("directoryId") String directoryId);

    List<Notice> selectByTimeDesc();

    List<Notice> selectNewsForPublic(@Param("numstart") int numstart, @Param("size") int size);

    Notice selectByPKForPublic(Integer noticeId);

    List<Notice> selectWithPicForPublic();
}