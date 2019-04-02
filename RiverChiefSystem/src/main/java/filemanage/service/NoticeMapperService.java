package filemanage.service;

import common.model.Notice;

import java.util.List;

public interface NoticeMapperService {
    boolean deleteByPrimaryKey(Integer noticeId);

    boolean insert(String title, String body, String directoryId, String struserId, String level, String weblink, String fountImgUrl, String summary, String userOpen);

    Notice selectByPrimaryKey(Integer noticeId);

    boolean updateByPrimaryKeyWithBLOBs(String id, String title, String body, String directoryId, String struserId, String level, String weblink, String fountImgUrl, String summary, String userOpen);
    List<Notice> selectByDirectoryAndTitleWithTime(String directoryId, String noticeTitle, String startTime, String endTime,String noticeLevel);
    int countByDirectory(String directoryId);

    int countInAWeek(String directoryId);

    List<Notice> selectByLevel(String level, String directoryId);

    List<Notice> selectByTimeDesc();

    List<Notice> selectNewsForPublic(String page, String size);

    Notice selectByPKForPublic(Integer noticeId);

    List<Notice> selectWithPicForPublic();
}

