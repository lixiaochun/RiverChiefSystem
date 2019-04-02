package workmanage.service.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.model.Event;
import common.model.Patrol;
import common.util.ValidateUtil;
import workmanage.mapper.EventMapper;
import workmanage.mapper.PatrolMapper;
import workmanage.service.PatrolService;

@Service("PatrolService")
public class PatrolServiceImpl implements PatrolService {
	@Autowired
	private PatrolMapper patrolMapper;

	@Autowired
	private EventMapper eventMapper;

	public Map<Object, Object> queryPatrol(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Patrol> list = patrolMapper.queryPatrol(map);
			List<Patrol> removelist = new ArrayList<Patrol>();
			int count = patrolMapper.countPatrol(map);
			if (map.get("appType") != null && map.get("appType").toString().equals("1")) {
				if (list != null && list.size() > 0) {
					for (Patrol patrol : list) {
						if (patrol.getPatrolNum() <= patrol.getCompleteNum()) {
							removelist.add(patrol);
							count--;
						}
					}
					list.removeAll(removelist);
				}
			}
			result.put("result", 1);
			result.put("Patrol", list);
			result.put("countPatrol", count);
			result.put("message", "查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作异常");
		}
		return result;
	}

	public Map<Object, Object> insertPatrol(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Patrol patrol = new Patrol();
			patrol.setTaskName(map.get("taskName").toString());
			patrol.setUserId(Integer.parseInt(map.get("patrolUserId").toString()));
			// patrol.setRegionId(map.get("regionId").toString());
			patrol.setRiverId(map.get("riverId").toString());

			if (map.get("patrolPeriod") != null) {
				patrol.setPatrolPeriod(map.get("patrolPeriod").toString());
			}
			if (map.get("point") != null) {
				patrol.setPoint(map.get("point").toString());
			}
			patrol.setPatrolType(map.get("patrolType").toString());
			if (map.get("patrolType").equals("0")) {
				patrol.setPatrolNum(1);
			} else {
				patrol.setPatrolNum(Integer.parseInt(map.get("patrolNum").toString()));
			}
			patrol.setOpuserId(Integer.parseInt(map.get("userId").toString()));
			patrol.setPublishTime(new Timestamp(new Date().getTime()));
			patrol.setPatrolStatus(1);
			List<String> msg = ValidateUtil.BeanValidate(patrol);
			if (msg.size() > 0) {
				result.put("result", 0);
				result.put("message", msg);
				return result;
			}
			patrolMapper.insertPatrol(patrol);
			result.put("result", 1);
			result.put("message", "操作成功");
		} catch (Exception e) {
			result.put("result", 0);
			result.put("message", "操作异常");
			e.printStackTrace();
		}
		return result;
	}

	public Map<Object, Object> updatePatrol(Map<Object, Object> map) {
		Map<Object, Object> search = new HashMap<Object, Object>();
		Map<Object, Object> query = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		int userId = Integer.parseInt(map.get("userId").toString());
		int patrolStatus = Integer.parseInt(map.get("patrolStatus").toString());
		int patrolId = Integer.parseInt(map.get("patrolId").toString());
		String point = map.get("point").toString();
		search.put("userId", userId);
		search.put("patrolStatus", patrolStatus);
		search.put("patrolId", patrolId);
		search.put("point", point);
		patrolMapper.updatePatrol(search);
		query.put("patrolId", patrolId);
		List<Patrol> list = patrolMapper.queryPatrolByStatus(query);
		result.put("result", 0);
		if (map.get("userId") != null) {
			if (list.get(0).getUserId() == userId) {
				result.put("result", 1);
			}
		}
		if (map.get("patrolStatus") != null) {
			if (list.get(0).getPatrolStatus() == patrolStatus) {
				result.put("result", 1);
			}
		}
		if (map.get("point") != null) {
			if (list.get(0).getPoint() == point) {
				result.put("result", 1);
			}
		}
		return result;

	}

	public Map<Object, Object> deletePatrol(int patrolId) {
		Map<Object, Object> result = new HashMap<Object, Object>();

		Map<Object, Object> query = new HashMap<Object, Object>();
		try {
			query.put("patrolId", patrolId);
			List<Patrol> list = patrolMapper.queryPatrolByStatus(query);
			if (list != null && list.size() > 0) {
				patrolMapper.deletePatrol(patrolId);
				result.put("result", 1);
			} else {
				result.put("result", 0);
				result.put("message", "查无该任务");
			}
		} catch (Exception e) {
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> queryPointFromPatrol(int patrolId) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("patrolId", patrolId);
		List<Patrol> list = patrolMapper.queryPatrolByStatus(map);
		if (list != null && list.size() > 0) {
			result.put("result", 1);
			result.put("Patrol", list);
		} else {
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> queryEventByPatrol(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Event> eventlist = eventMapper.queryEventByPatrol(map);
			result.put("eventlist", eventlist);
			result.put("result", 1);
			result.put("message", "查询成功");
		} catch (Exception e) {
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	public Map<Object, Object> countPatrol(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			map.put("todo", 1);
			List<Patrol> list = patrolMapper.queryPatrol(map);
			int count = patrolMapper.countPatrol(map);
			result.put("result", 1);
			result.put("Patrol", list);
			result.put("countPatrol", count);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}
}
