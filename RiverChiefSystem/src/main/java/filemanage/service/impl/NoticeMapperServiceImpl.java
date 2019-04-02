package filemanage.service.impl;

import common.model.Notice;
import filemanage.mapper.NoticeMapper;
import filemanage.service.NoticeMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeMapperServiceImpl implements NoticeMapperService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public boolean deleteByPrimaryKey(Integer noticeId) {
        return noticeMapper.deleteByPrimaryKey(noticeId);
    }

    @Override
    public boolean insert(String title, String body, String directoryId, String struserId, String level, String weblink, String fountImgUrl, String summary, String userOpen) {
        Notice notice = new Notice();
        Date now = new Date();
        notice.setSubmitTime(now);
        notice.setNoticeTitle(title);
        notice.setNoticeBody(body);
        notice.setDirectoryId(directoryId);
        notice.setUserId(Integer.valueOf(struserId));
        notice.setNoticeLevel(level);
        notice.setOutLink(weblink);
        notice.setFountImgUrl(fountImgUrl);
        notice.setSummary(summary);
        if (userOpen != null) {
            notice.setUserOpen(Integer.parseInt(userOpen));
        }
        return noticeMapper.insert(notice);
    }

    @Override
    public Notice selectByPrimaryKey(Integer noticeId) {
        return noticeMapper.selectByPrimaryKey(noticeId);
    }

    @Override
    public boolean updateByPrimaryKeyWithBLOBs(String id, String title, String body, String directoryId, String struserId, String level, String weblink, String fountImgUrl, String summary, String userOpen) {
        Notice notice = noticeMapper.selectByPrimaryKey(Integer.valueOf(id));
        Date now = new Date();
        notice.setSubmitTime(now);
        notice.setNoticeTitle(title);
        notice.setNoticeBody(body);
        notice.setDirectoryId(directoryId);
        notice.setUserId(Integer.valueOf(struserId));
        notice.setNoticeLevel(level);
        notice.setOutLink(weblink);
        notice.setFountImgUrl(fountImgUrl);
        notice.setSummary(summary);
        if (userOpen != null) {
            notice.setUserOpen(Integer.parseInt(userOpen));
        }
        return noticeMapper.updateByPrimaryKeyWithBLOBs(notice);
    }

    @Override
    public List<Notice> selectByDirectoryAndTitleWithTime(String directoryId, String noticeTitle, String startTime, String endTime, String noticeLevel) {
        return noticeMapper.selectByDirectoryAndTitleWithTime(directoryId, noticeTitle, startTime, endTime, noticeLevel);
    }

    @Override
    public int countByDirectory(String directoryId) {
        String v = noticeMapper.countByDirectory(directoryId);
        if (v == null) {
            return 0;
        } else {
            return Integer.parseInt(v);
        }
    }

    @Override
    public int countInAWeek(String directoryId) {
        String v = noticeMapper.countInAWeek(directoryId);
        if (v == null) {
            return 0;
        } else {
            return Integer.parseInt(v);
        }
    }

    @Override
    public List<Notice> selectByLevel(String level, String directoryId) throws NullPointerException {
        if (level != null && directoryId != null) {
            return noticeMapper.selectByLevel(level, directoryId);
        } else {
            throw new NullPointerException("level or directoryId is null");
        }

    }

    @Override
    public List<Notice> selectByTimeDesc() {
        return noticeMapper.selectByTimeDesc();
    }

    @Override
    public List<Notice> selectNewsForPublic(String page, String size) {
        int Page, Size;
        if (page == null) {
            Page = 1;
        } else {
            Page = Integer.parseInt(page);
        }
        if (size == null) {
            Size = 10;
        } else {
            Size = Integer.parseInt(size);
        }
        return noticeMapper.selectNewsForPublic((Page - 1) * Size, Size);
    }

    @Override
    public Notice selectByPKForPublic(Integer noticeId) {
        return noticeMapper.selectByPKForPublic(noticeId);
    }

    @Override
    public List<Notice> selectWithPicForPublic() {
        return noticeMapper.selectWithPicForPublic();
    }

}
