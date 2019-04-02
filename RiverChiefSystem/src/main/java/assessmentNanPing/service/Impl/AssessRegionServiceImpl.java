package assessmentNanPing.service.Impl;

import assessment.mapper.RegionMapper;
import assessmentNanPing.mapper.AssessRegionMapper;
import assessmentNanPing.service.AssessRegionService;
import common.model.*;
import filemanage.service.RegionCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssessRegionServiceImpl implements AssessRegionService {
    @Autowired
    private AssessRegionMapper assessRegionMapper;
    @Autowired
    private RegionCut regionCut;
    @Autowired
    private RegionMapper regionMapper;

    @Override
    public AssessRegion findByTimeAndRegion(String regionId, String time) throws Exception {
        AssessRegion assessRegion = new AssessRegion();
        String region = regionCut.cut(regionId);
        List<AssessTypeAndScore> scores = assessRegionMapper.findScore(region, time);
        try {
            assessRegion.setRegionId(regionId);
            assessRegion.setDate(time);
            for (AssessTypeAndScore a : scores) {
                switch (a.getType()) {
                    case "1":
                        a.setType("巡河完成率");
                        break;
                    case "2":
                        a.setType("巡河次数");
                        break;
                    case "3":
                        a.setType("巡河里程数");
                        break;
                    case "4":
                        a.setType("巡河事件数");
                        break;
                }
            }
            assessRegion.setList(scores);
        } catch (Exception e) {
            throw new Exception("data error");
        }
        return assessRegion;
    }

    @Override
    public List<AssessCompleteScore> findCompleteLastYear(String start, String end, String regionId) throws Exception {
        String region ="";
		try {
			region = regionCut.cut(regionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        List<AssessCompleteScore> scores = assessRegionMapper.findCompleteLastYear(start, end, region);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        for (AssessCompleteScore a : scores) {
            a.setDate(format.format(a.getDateTime()));
        }
        return scores;
    }

    @Override
    public List<AssessTypeAndScore> findRegionData(String regionId, String time) {
        List<AssessTypeAndScore> assessTypeAndScores = new ArrayList<>();
        Integer user = assessRegionMapper.queryUserSum(regionId);
        AssessTypeAndScore d = new AssessTypeAndScore();
        d.setType("巡河天数");
        Integer data4 = assessRegionMapper.queryRegionPatrolDays(regionId, time);
        d.setDataSum(data4);
        d.setDataAvg(((float) data4 / user));
        assessTypeAndScores.add(d);

        AssessTypeAndScore a = new AssessTypeAndScore();

        a.setType("巡河次数");
        Integer data1 = assessRegionMapper.queryRegionPatrolTime(regionId, time);
        a.setDataSum(data1);
        a.setDataAvg(((float) data1 / user));
        assessTypeAndScores.add(a);

        AssessTypeAndScore b = new AssessTypeAndScore();
        b.setType("巡河里程数");
        Double data2 = assessRegionMapper.queryRegionPatrolMileage(regionId, time);
        b.setDataSum(data2);
        b.setDataAvg((data2 / user));
        assessTypeAndScores.add(b);

        AssessTypeAndScore c = new AssessTypeAndScore();
        c.setType("巡河事件数");
        Integer data3 = assessRegionMapper.queryRegionEventNum(regionId, time);
        c.setDataSum(data3);
        c.setDataAvg(((float) data3 / user));
        assessTypeAndScores.add(c);

        return assessTypeAndScores;
    }

    @Override
    public List<StringAndInt> queryRegionPatrolTimeEach(String regionId, String date) {
        return assessRegionMapper.queryRegionPatrolTimeEach(regionId, date);
    }

    @Override
    public List<StringAndDouble> queryRegionPatrolMileageEach(String regionId, String date) {
        return assessRegionMapper.queryRegionPatrolMileageEach(regionId, date);
    }

    @Override
    public List<StringAndInt> queryRegionEventNumEach(String regionId, String date) {
        return assessRegionMapper.queryRegionEventNumEach(regionId, date);
    }

    @Override
    public List<StringAndInt> queryRegionPatrolDaysEach(String regionId, String date) {
        return assessRegionMapper.queryRegionPatrolDaysEach(regionId, date);
    }
}
