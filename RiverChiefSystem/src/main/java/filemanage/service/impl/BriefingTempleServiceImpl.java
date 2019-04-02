package filemanage.service.impl;

import common.model.BriefingTemple;
import filemanage.mapper.BriefingTempleMapper;
import filemanage.service.BriefingTempleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BriefingTempleServiceImpl implements BriefingTempleService {
    @Autowired
    private BriefingTempleMapper briefingTempleMapper;

    @Override
    public boolean deleteByPrimaryKey(Integer briefingId) {
        return briefingTempleMapper.deleteByPrimaryKey(briefingId);
    }

    @Override
    public boolean insert(String name, String title, String body, String summary, String regionid) {
        BriefingTemple briefing = new BriefingTemple();
        briefing.setBriefingName(name);
        briefing.setBriefingTitle(title);
        briefing.setBriefingBody(body);
        briefing.setBriefingSummary(summary);
        briefing.setRegionId(regionid);
        Date now = new Date();
        briefing.setSubmitTime(now);
        return briefingTempleMapper.insert(briefing);
    }

    @Override
    public BriefingTemple selectByPrimaryKey(Integer briefingId) {
        return briefingTempleMapper.selectByPrimaryKey(briefingId);
    }

    @Override
    public boolean updateByPrimaryKeyWithBLOBs(String id, String name, String title, String body, String summary, String regionid) {
        BriefingTemple briefing = briefingTempleMapper.selectByPrimaryKey(Integer.valueOf(id));
        briefing.setBriefingTitle(title);
        briefing.setBriefingName(name);
        briefing.setBriefingBody(body);
        briefing.setBriefingSummary(summary);
        Date now = new Date();
        briefing.setSubmitTime(now);
        briefing.setRegionId(regionid);
        return briefingTempleMapper.updateByPrimaryKeyWithBLOBs(briefing);
    }

    @Override
    public List<BriefingTemple> selectTitleByRegion(String regionId) {
        return briefingTempleMapper.selectTitleByRegion(regionId);
    }
}
