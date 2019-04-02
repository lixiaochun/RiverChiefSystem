package assessmentFuDing.service.Impl;

import assessmentFuDing.mapper.AssessmentRegionMapper;
import assessmentFuDing.service.AssessmentRegionService;
import common.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AssessmentRegionServiceImpl implements AssessmentRegionService {

    @Autowired
    private AssessmentRegionMapper assessmentRegionMapper;

    @Override
    public List<AssessRegionDateStencil> findRegionDataMonthAvg(String regionId, String[] time) {

        List<AssessRegionDateStencil> assessRegionDateStencils = assessmentRegionMapper.queryMonthContrast(regionId, time);

        for (int i = 0; i < time.length; i++) {
            String[] strArray = time[i].split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, Integer.parseInt(strArray[0]));
            //year年
            calendar.set(Calendar.MONTH, Integer.parseInt(strArray[1]) - 1);
            //Calendar对象默认一月为0,month月
            int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数

            assessRegionDateStencils.get(i).setDate(time[i]);
            assessRegionDateStencils.get(i).setRegionId(regionId);
            assessRegionDateStencils.get(i).setEventNum(new BigDecimal(assessRegionDateStencils.get(i).getEventNum() / day).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            assessRegionDateStencils.get(i).setPatrolDays(new BigDecimal(assessRegionDateStencils.get(i).getPatrolDays() / day).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            assessRegionDateStencils.get(i).setPatrolMileage(new BigDecimal(assessRegionDateStencils.get(i).getPatrolMileage() / day /1000).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            assessRegionDateStencils.get(i).setPatrolTime(new BigDecimal(assessRegionDateStencils.get(i).getPatrolTime() / day).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        return assessRegionDateStencils;
    }

    @Override
    public List<AssessRegionDateStencil> findRegionDataMonthSum(String regionId, String[] time) {
        List<AssessRegionDateStencil> assessRegionDateStencils = assessmentRegionMapper.queryMonthContrast(regionId, time);

        for (int i = 0; i < time.length; i++) {
            assessRegionDateStencils.get(i).setDate(time[i]);
            assessRegionDateStencils.get(i).setRegionId(regionId);
            assessRegionDateStencils.get(i).setPatrolMileage(new BigDecimal(assessRegionDateStencils.get(i).getPatrolMileage() /1000).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        return assessRegionDateStencils;
    }

    public List<RegionPatrolInfo> queryRegionContrastSUM(List<String> regionIds, Date startTime, Date endTime) {
        List<RegionPatrolInfo> rpList = assessmentRegionMapper.queryRegionContrastSUM(regionIds, startTime, endTime);
        return rpList;
    }

    public List<RegionPatrolInfo> queryRegionName(String[] regionIds) {
        List<RegionPatrolInfo> rpList = assessmentRegionMapper.queryRegionName(regionIds);
        return rpList;
    }

    public ArrayList<Integer> queryRegionPeopleNum(String[] regionIds) {
        ArrayList<Integer> peopleNum = assessmentRegionMapper.queryRegionPeopleNum(regionIds);
        return peopleNum;
    }

}
