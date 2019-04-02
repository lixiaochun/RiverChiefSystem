package statisticalform.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.Statistics;
import statisticalform.mapper.StatisticalPatrolMapper;
import statisticalform.service.statisticalPatrolService;

@Service("statisticalPatrolService")
@Transactional(readOnly = true)
public class statisticalPatrolServiceImpl implements statisticalPatrolService{

	@Autowired
	private StatisticalPatrolMapper statisticalPatrolMapper;

	public Map<Object, Object> statisticsRiver(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			int river = statisticalPatrolMapper.statisticsRiver(map);// 所有河道
			map.put("roleType", 2);
			int riverChief = statisticalPatrolMapper.statisticsRoleType(map);// 所有河长
			map.put("roleType", 4);
			int riverChiefOffice = statisticalPatrolMapper.statisticsRoleType(map);// 所有河长办
			map.put("roleType", 5);
			int riverCharge = statisticalPatrolMapper.statisticsRoleType(map);// 所有河道专管
			result.put("result", 1);
			result.put("river", river);	
			result.put("riverChief", riverChief);	
			result.put("riverChiefOffice", riverChiefOffice);
			result.put("riverCharge", riverCharge);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}
		return result;
	}
	
	public Map<Object, Object> statisticsLogin(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Statistics> list = statisticalPatrolMapper.statisticsLogin(map);
			if(list!=null&&list.size()>0) {
				result.put("login", list);
				result.put("result", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> statisticsNotLogin(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Statistics> list = statisticalPatrolMapper.statisticsNotLogin(map);
			if(list!=null&&list.size()>0) {
				result.put("login", list);
				result.put("result", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> statisticsPatrol(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
//			List<Statistics> login = statisticalPatrolMapper.statisticsLogin(map);// 统计河长app活跃度
//			List<Statistics> notLogin = statisticalPatrolMapper.statisticsNotLogin(map);// 统计app使用情况
//			List<Statistics> patrolCount = statisticalPatrolMapper.statisticsPatrolCount(map);// 统计巡查频次达标情况
			List<Statistics> patrolRecord = statisticalPatrolMapper.statisticsPatrolRecord(map);// 日志执行达标情况统计
			result.put("result", 1);
//			result.put("login", login);
//			result.put("notLogin", notLogin);
//			result.put("patrolCount", patrolCount);
			result.put("patrolRecord", patrolRecord);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	@Override
	public Map<Object, Object> statisticsPatrolRecord(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			if(map.get("regionId")!=null&&!(map.get("regionId").equals(""))) {
				list = statisticalPatrolMapper.statisticsPatrolRecordByRegionId(map);
			}
			if(map.get("riverIds")!=null) {
				List<String> riverIds = (List<String>) map.get("riverIds");
				for(String riverId :riverIds) {
					map.put("riverId", riverId);
					List<Map<String, Object>> maplist = new ArrayList<>();
					maplist = statisticalPatrolMapper.statisticsPatrolRecordByRiverId(map);
					list.addAll(maplist);
				}
			}
			result.put("result", 1);
			result.put("patrolRecord", list);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	@Override
	public Map<Object, Object> statisticsPublicsigns(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			if(map.get("regionId")!=null&&!(map.get("regionId").equals(""))) {
				list = statisticalPatrolMapper.statisticsPublicsignsByRegionId(map);
			}
			if(map.get("riverIds")!=null) {
				List<String> riverIds = (List<String>) map.get("riverIds");
				for(String riverId :riverIds) {
					map.put("riverId", riverId);
					List<Map<String, Object>> maplist = new ArrayList<>();
					maplist = statisticalPatrolMapper.statisticsPublicsignsByRiverId(map);
					list.addAll(maplist);
				}
			}
			result.put("result", 1);
			result.put("publicsigns", list);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

}
