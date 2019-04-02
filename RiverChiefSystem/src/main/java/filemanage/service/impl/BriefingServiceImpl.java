package filemanage.service.impl;

import common.model.Briefing;
import common.model.BriefingTemple;
import common.model.User;
import filemanage.mapper.BriefingMapper;
import filemanage.service.BriefingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BriefingServiceImpl implements BriefingService {
    @Autowired
    private BriefingMapper briefingMapper;

    @Override
    public boolean deleteByPrimaryKey(Integer briefingId) {
        return briefingMapper.deleteByPrimaryKey(briefingId);
    }

    @Override
    public boolean insert(String title, String body, String summary, String regionid, String struserId, String sendto) {
        Briefing briefing = new Briefing();
        briefing.setBriefingTitle(title);
        briefing.setBriefingBody(body);
        briefing.setBriefingSummary(summary);
        briefing.setBriefingUserId(Integer.valueOf(struserId));
        briefing.setRegionId(regionid);
        Date now = new Date();
        briefing.setSubmitTime(now);
        briefing.setBriefingStatus("1");
        briefing.setBriefingSendtoUserId(Integer.parseInt(sendto));
        return briefingMapper.insert(briefing);
    }

    @Override
    public Briefing selectByPrimaryKey(Integer briefingId) {
        return briefingMapper.selectByPrimaryKey(briefingId);
    }

    @Override
    public boolean updateByPrimaryKeyWithBLOBs(String id, String title, String body, String summary, String struserId, String regionid, String sendto) {
        Briefing briefing = briefingMapper.selectByPrimaryKey(Integer.valueOf(id));
        briefing.setBriefingTitle(title);
        briefing.setBriefingBody(body);
        briefing.setBriefingSummary(summary);
        briefing.setBriefingUserId(Integer.valueOf(struserId));
        Date now = new Date();
        briefing.setSubmitTime(now);
        briefing.setRegionId(regionid);
        briefing.setBriefingStatus("1");
        briefing.setBriefingSendtoUserId(Integer.parseInt(sendto));
        return briefingMapper.updateByPrimaryKeyWithBLOBs(briefing);
    }

    @Override
    public List<Briefing> selectByUser(String startTime, String endTime, int userId, String status, String title) {
        return briefingMapper.selectByUser(startTime, endTime, userId, status, title);
    }

    @Override
    public List<Briefing> selectByRegion(String startTime, String endTime, String regionId, String status, String title) {
        return briefingMapper.selectByRegion(startTime, endTime, regionId, status, title);
    }

    @Override
    public List<Briefing> selectSelf(String startTime, String endTime, int userId, String status, String title) {
        return briefingMapper.selectSelf(startTime, endTime, userId, status, title);
    }

    @Override
    public List<User> findLeader(int userId) {
        return briefingMapper.findLeader(userId);
    }

    @Override
    public User findOrganization(int userId) {
        return briefingMapper.findOrganization(userId);
    }

    @Override
    public Integer findUnderling(int userId) {
        return briefingMapper.findUnderling(userId);
    }

    @Override
    public boolean updateStatus(Briefing record) {
        return briefingMapper.updateStatus(record);
    }

    @Override
    public boolean updateScore(double score, Integer briefingId) {
        return briefingMapper.updateScore(score, briefingId);
    }

    @Override
    public List<Briefing> findListForPublic(String page, String size) {
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
        return briefingMapper.selectListForPublic((Page - 1) * Size, Size);
    }

}
