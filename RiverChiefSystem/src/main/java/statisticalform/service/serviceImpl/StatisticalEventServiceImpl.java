package statisticalform.service.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.Region;
import statisticalform.mapper.StatisticalEventMapper;
import statisticalform.mapper.StatisticalPatrolMapper;
import statisticalform.service.StatisticalEventService;
import usermanage.mapper.DateInfoMapper;
import workmanage.mapper.EventMapper;
import workmanage.service.WorkflowService;

@Service("StatisticalEventService")
@Transactional(readOnly = true)
public class StatisticalEventServiceImpl implements StatisticalEventService {

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private DateInfoMapper dateInfoMapper;

	@Autowired
	private StatisticalPatrolMapper statisticalPatrolMapper;

	@Autowired
	private StatisticalEventMapper statisticalEventMapper;

	public Map<Object, Object> statisticsEvent(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			String[] strregionIds = (map.get("regionIds").toString()).split(",");
			List<String> regionIds = Arrays.asList(strregionIds);
			List<String> eventIdList = new ArrayList<String>();
			map.put("regionIds", regionIds);
			int all = eventMapper.countEvent(map);// 所有事件

			eventIdList = workflowService.findFinishTask(map);// 结办任务
			if (eventIdList == null || eventIdList.size() == 0) {
				eventIdList.add("0");
			}
			map.put("eventIds", eventIdList);
			map.put("updateTime", new Date());
			int solve = eventMapper.countEvent(map);// 今日结办
			// eventIdList.clear();
			// eventIdList = workflowService.findOnTask(map);//在办任务
			// if(eventIdList!=null&&eventIdList.size()>0) {
			// map.put("eventIds", eventIdList);
			// }
			// int toSolve = eventMapper.countEvent(map);//今日在办
			eventIdList.clear();
			map.remove("updateTime");
			eventIdList = workflowService.findTodoTask(map);// 待办任务
			if (eventIdList == null || eventIdList.size() == 0) {
				eventIdList.add("0");
			}
			map.put("eventIds", eventIdList);
			int toSolve = eventMapper.countEvent(map);// 今日待办
			map.remove("eventIdList");
			map.put("eventTime", new Date());
			int add = eventMapper.countEvent(map);// 今日投诉
			result.put("result", 1);
			result.put("all", all);// 所有事件
			result.put("solve", solve);// 今日处理
			result.put("add", add);// 今日投诉
			result.put("toSolve", toSolve);// 今日待办
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	public Map<Object, Object> statisticsProblemType(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Map<String, Object>> list = statisticalEventMapper.statisticsProblemType(map);// 事件类型
			result.put("result", 1);
			result.put("problemTypeList", list);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	public Map<Object, Object> statisticalReportType(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Map<String, Object>> list = statisticalEventMapper.statisticalReportType(map);// 上报方式统计
			result.put("result", 1);
			result.put("reportTypeList", list);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	public Map<Object, Object> everyEventCount(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Map<String, Object>> list = statisticalEventMapper.everyEventCount(map);// 这个月事件统计
			List<Map<String, Object>> list1 = statisticalEventMapper.beforeEventCount(map);// 上个月事件统计
			result.put("result", 1);
			result.put("everyEventCount", list);
			result.put("beforeEventCount", list1);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	public Map<Object, Object> statisticalRegion(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Region> region = dateInfoMapper.queryRegionId(map);
			result.put("region", region);
			map.put("eventType", 2);// 不受理事件
			int solve = eventMapper.countEvent(map);// 今日处理
			map.put("eventType", 4);// 已完成事件
			solve += eventMapper.countEvent(map);
			// 待办事件
			map.put("eventType", 1);// 待办事项
			int toSolve = eventMapper.countEvent(map);// 今日待办
			map.put("eventType", 3);// 待审核
			toSolve += eventMapper.countEvent(map);
			map.put("eventType", 5);// 退回事件
			toSolve += eventMapper.countEvent(map);
			map.put("eventType", 6);// 回访
			toSolve += eventMapper.countEvent(map);
			map.put("eventType", 7);// 请求协调
			toSolve += eventMapper.countEvent(map);
			result.put("toSolve", toSolve);// 待办事件
			result.put("solve", solve);// 已办事件
			double strpercent = (double) solve / (solve + toSolve);
			String percent = strpercent + "%";
			result.put("percent", percent);// 事件完成率
			List<Map<String, Object>> list = statisticalEventMapper.statisticsProblemType(map);
			result.put("problemTypelist", list);// 事件类型
			result.put("result", 1);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	public Map<Object, Object> publicComplaints(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Map<Object, Object> search = new HashMap<Object, Object>();
			search.put("parentId", map.get("regionId"));
			List<Region> regionlist = dateInfoMapper.queryRegion(search);
			if (regionlist.size() > 0) {
				Region region = regionlist.get(0);
				if (region.getLevel() >= 3) {
					map.put("regionId", region.getRegionId().substring(0, 4) + "00000000");
				}
				List<Map<String, Object>> list = statisticalEventMapper.publicComplaints(map);
				if (list != null && list.size() > 0) {
					result.put("result", 1);
					result.put("publicComplaints", list);
				} else {
					result.put("result", 0);
					result.put("message", "查无行政区");
				}
			} else {
				result.put("result", 0);
				result.put("message", "查无行政区");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	public Map<Object, Object> statisticsEventCount(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Map<String, Object>> list = statisticalEventMapper.statisticsEventCount(map);
			result.put("result", 1);
			result.put("eventCount", list);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	public Map<Object, Object> statisticsProblemTypes(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		List<String> eventIdList = new ArrayList<String>();
		List<String> eventIdListAll = new ArrayList<String>();
		try {
			if (map.get("userId") != null) {
				search.put("userId", map.get("userId"));
				search.put("type", 1);
				eventIdList = workflowService.queryTask(search);

				// 结办任务
				// eventIdList = workflowService.findFinishTask(search);
				if (eventIdList == null || eventIdList.size() == 0) {
					eventIdList.add("0");
				}
				eventIdListAll.addAll(eventIdList);

				search.put("type", 2);
				eventIdList = workflowService.queryTask(search);

				// 在办任务
				// eventIdList = workflowService.findOnTask(search);
				if (eventIdList == null || eventIdList.size() == 0) {
					eventIdList.add("0");
				}
				eventIdListAll.addAll(eventIdList);

				search.put("type", 3);
				eventIdList = workflowService.queryTask(search);

				// 待办任务
				// eventIdList = workflowService.findTodoTask(search);
				if (eventIdList == null || eventIdList.size() == 0) {
					eventIdList.add("0");
				}
				eventIdListAll.addAll(eventIdList);
				map.put("eventIds", eventIdListAll);
			}
			List<Map<String, Object>> list = statisticalEventMapper.statisticsProblemTypes(map);// 事件类型
			result.put("result", 1);
			result.put("problemTypeList", list);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	public Map<Object, Object> statisticsEventType(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Map<String, Object>> list = statisticalEventMapper.statisticsEventType(map);// 事件类型
			result.put("result", 1);
			result.put("eventTypeList", list);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

	@Override
	public Map<Object, Object> statisticsHome(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			map.put("orderby", 1);
			List<Map<String, Object>> list = statisticalEventMapper.statisticsProblemType(map);// 事件类型
			List<Map<String, Object>> list1 = new ArrayList<>();
			if (map.get("regionId") != null && !(map.get("regionId").equals(""))) {
				list1 = statisticalPatrolMapper.statisticsPatrolRecordByRegionId(map);
			}
			if (map.get("riverIds") != null) {
				List<String> riverIds = (List<String>) map.get("riverIds");
				for (String riverId : riverIds) {
					map.put("riverId", riverId);
					List<Map<String, Object>> maplist = new ArrayList<>();
					maplist = statisticalPatrolMapper.statisticsPatrolRecordByRiverId(map);
					list1.addAll(maplist);
				}
			}
			result.put("patrolRecord", list1);
			result.put("result", 1);
			result.put("problemTypeList", list);
			result.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询失败");
		}
		return result;
	}

}
