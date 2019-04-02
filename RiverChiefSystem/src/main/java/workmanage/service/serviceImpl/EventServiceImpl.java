package workmanage.service.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.Event;
import common.model.EventFile;
import common.model.WorkflowLog;
import common.util.LogToDocx;
import usermanage.service.UserService;
import workmanage.mapper.EventFileMapper;
import workmanage.mapper.EventMapper;
import workmanage.service.EventService;
import workmanage.service.PatrolRecordService;
import workmanage.service.WorkflowService;

@Service("EventService")
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private EventFileMapper eventFileMapper;

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private UserService userService;

	@Autowired
	private PatrolRecordService patrolRecordService;

	private LogToDocx logToDocx;

	@Transactional(readOnly = false)
	public Map<Object, Object> addEven(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Map<Object, Object> search = new HashMap<Object, Object>();
			Map<Object, Object> searchmap = new HashMap<Object, Object>();
			
			if(map.get("solve").equals("0")) {
				search.put("roleId", 4);
				search.put("regionId", map.get("regionId"));
				// 查询下一级处理人
				List<Integer> list = userService.queryUserRoleId(search);
				if (list.size() > 0) {
					map.put("nextUserId", list.get(0));
				}else {
					result.put("result", 0);
					result.put("message", "查无下一级处理人");
					return result;
				}
			}
				// 插入event
				Event event = new Event();
				if (map.get("patrolRecordId") != null && !(map.get("patrolRecordId").toString().equals(""))) {
					event.setPatrolRecordId(Integer.parseInt(map.get("patrolRecordId").toString()));
				}
				event.setReportType(map.get("reportType").toString());
				event.setEventName(map.get("eventName").toString());
				event.setEventCode(map.get("eventCode").toString());
				event.setEventLevel(map.get("eventLevel").toString());
				event.setEventContent(map.get("eventContent").toString());
				event.setEventPoint(map.get("eventPoint").toString());
				event.setAddress(map.get("address").toString());
				event.setRegionId(map.get("regionId").toString());
				event.setRiverId(map.get("riverId").toString());
				event.setProblemType(map.get("problemType").toString());
				event.setUpdateTime(new Timestamp(new Date().getTime()));
				event.setRemark(map.get("remark").toString());
				if (map.get("limitTime") != null) {
					event.setLimitTime(Timestamp.valueOf(map.get("limitTime").toString()));
				}
				event.setRectification(map.get("rectification").toString());
				event.setUserId(Integer.parseInt(map.get("userId").toString()));
				event.setEventTime(Timestamp.valueOf(map.get("eventTime").toString()));
				event.setEventType(map.get("solve").toString());
				event.setEventStatus(Integer.parseInt(map.get("eventStatus").toString()));
				event.setIsAccountability(0);
				eventMapper.addEven(event);
				int count = event.getEventId();
				if (count != 0) {
					String processDefinitionKey = "event";
					map.put("processDefinitionKey", processDefinitionKey);
					map.put("businessKey", Integer.toString(count));
					// 启动工作流程
					searchmap = workflowService.saveStartProcess(map);
					if (searchmap.get("processInstanceId") != null) {
						searchmap.put("eventId", count);
						searchmap = workflowService.findTask(searchmap);
						if (searchmap.get("taskId") != null) {
							map.put("type", 2);
							map.put("acceptTime", new Date());
							map.put("logStatus", "00");
							map.put("eventId", count);
							map.put("taskId", searchmap.get("taskId"));
							
							if(map.get("solve").equals("0")) {
								map.put("userId", map.get("nextUserId"));
								map.remove("nextUserId");
							}else {
								map.put("userId", map.get("userId"));
							}
							eventMapper.addWorkflowLog(map);
							result.put("result", 1);
							result.put("eventId", count);
							result.put("message", "操作成功");
						}
					} else {
						result.put("result", 0);
						result.put("message", "事件流程启动失败");
					}
				} else {
					result.put("result", 0);
					result.put("message", "事件新增失败");
				}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "新增事件异常");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> updateEventByImg(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			int eventId = Integer.parseInt(map.get("eventId").toString());
			search.put("eventId", eventId);
			search.put("eventStatus", map.get("eventStatus"));
			List<Event> list = eventMapper.queryEvent(search);
			search.clear();
			int workflowLogId = Integer.parseInt(map.get("workflowLogId").toString());
			search.put("workflowLogId", workflowLogId);
			List<WorkflowLog> wflList = eventMapper.queryWorkflowLog(search);
			if ((list != null && list.size() > 0) || (wflList != null && wflList.size() > 0)) {
				List<EventFile> eventFileList = new ArrayList<EventFile>();
				if (map.get("fileinfo") != null && ((List<Map<Object, Object>>) map.get("fileinfo")).size() > 0) {
					List<Map<Object, Object>> fileinfo = (List<Map<Object, Object>>) map.get("fileinfo");
					for (Map<Object, Object> fileinfomap : fileinfo) {
						EventFile eventFile = new EventFile();
						eventFile.setEventId(eventId);
						eventFile.setWorkflowLogId(workflowLogId);
						eventFile.setFileName(fileinfomap.get("filename").toString());
						eventFile.setFileUrl(fileinfomap.get("url").toString());
						eventFile.setType(fileinfomap.get("type").toString());
						if (fileinfomap.get("type").toString().equals("image")) {
							eventFile.setSmallUrl(fileinfomap.get("smallurl").toString());
						}
						eventFile.setStatus(1);
						eventFileList.add(eventFile);
						eventFile.setSubmitTime(new Timestamp(new Date().getTime()));
						if (map.get("imageTime") != null) {
							eventFile.setImageTime(Timestamp.valueOf(map.get("imageTime").toString()));
						}
					}
					if (eventFileList != null && eventFileList.size() > 0) {
						int count = eventFileMapper.addEventFile(eventFileList);
						result.put("result", 1);
						result.put("message", "上传成功");
					} else {
						result.put("result", 0);
						result.put("message", "无相应文件");
					}
				} else {
					result.put("result", 0);
					result.put("message", "无相应文件");
				}
			} else {
				result.put("result", 0);
				result.put("message", "无该事件数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "上传异常");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> updateEvent(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			int eventId = Integer.parseInt(map.get("eventId").toString());
			search.put("eventId", eventId);
			List<Event> list = eventMapper.queryEvent(search);
			if (list != null && list.size() > 0) {
				eventMapper.updateEvent(map);
				result.put("result", 1);
				result.put("message", "操作成功");
			} else {
				result.put("result", 0);
				result.put("message", "不存在该事件");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作异常");
		}
		return result;
	}

	public Map<Object, Object> queryEvent(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Event> list = new ArrayList<Event>();
			List<Event> list2 = new ArrayList<Event>();
			int count = 0;
			int count2 = 0;
			if (map.get("type") != null) {
				String type = map.get("type").toString();
				List<String> eventIdList = new ArrayList<String>();
				if (type.equals("0")) {// 所有事件
					map.remove("userId");
					// map.remove("regionId");
				} else if (type.equals("1")) {
					map.remove("regionId");
					eventIdList = workflowService.queryTask(map);
					// eventIdList = workflowService.findFinishTask(map);// 结办任务
					if (eventIdList == null || eventIdList.size() == 0) {
						eventIdList.add("0");
					}
					map.put("eventIds", eventIdList);
					map.put("eventTypes", "2,4");
					// list2 = eventMapper.queryEventByUserId(map);// 查询用户新建的结办事件
					// count2 = eventMapper.countEventByUserId(map);
				} else if (type.equals("2")) {
					map.remove("regionId");
					eventIdList = workflowService.queryTask(map);
					// eventIdList = workflowService.findOnTask(map);// 在办任务
					if (eventIdList == null || eventIdList.size() == 0) {
						eventIdList.add("0");
					}
					// List<Integer> eventlist = eventMapper.queryEventId(map);
					// if (eventlist.size() > 0) {
					// for (Integer i : eventlist) {
					// eventIdList.add(Integer.toString(i));
					// }
					// }
					map.put("eventIds", eventIdList);
					map.put("eventTypes", "0,1,3,5,6,7");
					// list2 = eventMapper.queryEventByUserId(map);
					// count2 = eventMapper.countEventByUserId(map);
				} else if (type.equals("3")) {
					map.remove("regionId");
					eventIdList = workflowService.queryTask(map);
					// eventIdList = workflowService.findTodoTask(map);// 待办任务
					if (eventIdList == null || eventIdList.size() == 0) {
						eventIdList.add("0");
					}
					map.put("eventIds", eventIdList);
				}
			}
			list = eventMapper.queryEvent(map);
			count = eventMapper.countEvent(map);
			// list.addAll(list2);
			// count += count2;
			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("countEvent", count);
			result.put("eventList", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> addWorkflowLog(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			map.put("operaTime", new Date());
			int count = eventMapper.addWorkflowLog(map);
			if (count != 0) {
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> queryWorkflowLog(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<WorkflowLog> list = eventMapper.queryWorkflowLog(map);
			if (list != null && list.size() > 0) {
				result.put("workflowLog", list);
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> queryEventDetail(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Event> list = eventMapper.queryEvent(map);
			int count = eventMapper.countEvent(map);
			if (list != null && list.size() > 0) {
				String point = list.get(0).getEventPoint();
				List<List<String>> points = new ArrayList<List<String>>();
				if (!(point.equals(""))) {
					points = patrolRecordService.stringToStringArray(point);
				}
				List<WorkflowLog> workflowLogList = eventMapper.queryWorkflowLog(map);
				result.put("workflowLog", workflowLogList);
				result.put("result", 1);
				result.put("points", points);
				result.put("countEvent", count);
				result.put("eventList", list);
				result.put("message", "操作成功");
			} else {
				result.put("result", 0);
				result.put("message", "无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作异常");
		}
		return result;
	}

	public Map<Object, Object> claimEvent(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			List<Event> list = eventMapper.queryEvent(map);
			if (list != null && list.size() > 0) {
				// map.put("processInstanceId", list.get(0).getProcessInstanceId());
				// 拾取任务
				result = workflowService.claimTask(map);
				if (Integer.parseInt(result.get("result").toString()) == 1) {
					result.put("result", 1);
					result.put("message", "操作成功");
				}
			} else {
				result.put("result", 0);
				result.put("message", "无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作异常");
		}
		return result;
	}

	public Map<Object, Object> solveEvent(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Map<Object, Object> search = new HashMap<Object, Object>();
			search.put("eventId", map.get("eventId").toString());
			if(map.get("solve").equals("40")) {
				search.put("eventStatus", map.get("eventStatus").toString());
				map.put("solve", map.get("solve").toString().subSequence(0, 1));
			}
			
			List<Event> list = eventMapper.queryEvent(search);
			if (list != null && list.size() > 0) {

				// 不受理--->退回至发起人
				if (map.get("solve").equals("2")) {
					map.put("nextUserId", list.get(0).getUserId());
				}

				// 查询任务是否是该人的！
				result = workflowService.findTask(map);
				if (result.get("taskId") != null) {
					map.put("taskId", result.get("taskId"));
					// 完成任务
					result = workflowService.completeTask(map);
					if (Integer.parseInt(result.get("result").toString()) == 1) {
						search.put("eventId", map.get("eventId").toString());
						search.put("eventType", map.get("solve"));
						search.put("updateTime", new Date());
						eventMapper.updateEvent(search);
						List<WorkflowLog> workflowList = eventMapper.queryWorkflowLog(map);
						if (workflowList != null && workflowList.size() > 0) {
							map.put("type", 2);
							map.put("operaTime", new Date());
							// map.put("logStatus", map.get("solve"));
							map.put("logStatus", "1" + map.get("solve"));
							map.put("nextUserId", map.get("nextUserId"));
							eventMapper.updateWorkflowLog(map);
							result = workflowService.findTask(search);
							if (result.get("taskId") != null) {
								search.put("type", 2);
								search.put("acceptTime", new Date());
								search.put("logStatus", "0" + map.get("solve"));
								// search.put("logStatus", map.get("solve"));
								search.put("taskId", result.get("taskId"));
								search.put("userId", map.get("nextUserId"));
								search.put("eventId", map.get("eventId"));
								eventMapper.addWorkflowLog(search);
								result.put("result", 1);
								result.put("message", "操作成功");
							} else {
								result.put("result", 1);
								result.put("message", "操作成功");
								result.put("message", "流程执行结束");
							}
							result.put("workflowLogId", workflowList.get(0).getWorkflowLogId());
						} else {
							result.put("result", 0);
							result.put("message", "操作失败，查无对应的日志");
						}

					}
				}
			} else {
				result.put("result", 0);
				result.put("message", "此事件不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作异常");
		}
		return result;
	}

	public Map<Object, Object> countEvent(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Map<Object, Object> search = new HashMap<Object, Object>();
			Map<Object, Object> searchmap = new HashMap<Object, Object>();
			List<String> eventIdList = new ArrayList<String>();
			List<String> eventIdListAll = new ArrayList<String>();
			String regionId = (String) map.get("regionId");
			search.put("regionId", regionId);
			search.put("type", 0);
			int all = eventMapper.countEvent(search);// 所有事件
			search.clear();
			search.put("userId", map.get("userId").toString());

			search.put("type", 1);
			eventIdList = workflowService.queryTask(search);

			// 结办任务
			// eventIdList = workflowService.findFinishTask(search);
			if (eventIdList == null || eventIdList.size() == 0) {
				eventIdList.add("0");
			}
			eventIdListAll.addAll(eventIdList);
			searchmap.put("eventIds", eventIdList);
			int solve = eventMapper.countEvent(searchmap);// 我的结办
			// searchmap.put("eventTypes", "2,4");
			// searchmap.put("userId", map.get("userId").toString());
			// int count2 = eventMapper.countEventByUserId(searchmap);
			// solve = solve + count2;
			searchmap.remove("userId");
			searchmap.remove("eventTypes");
			search.clear();
			eventIdList.clear();
			search.put("userId", map.get("userId").toString());

			search.put("type", 2);
			eventIdList = workflowService.queryTask(search);

			// 在办任务
			// eventIdList = workflowService.findOnTask(search);
			if (eventIdList == null || eventIdList.size() == 0) {
				eventIdList.add("0");
			}
			// List<Integer> eventlist = eventMapper.queryEventId(map);
			// if (eventlist.size() > 0) {
			// for (Integer i : eventlist) {
			// eventIdList.add(Integer.toString(i));
			// }
			// }
			eventIdListAll.addAll(eventIdList);

			searchmap.put("eventIds", eventIdList);
			int toSolve = eventMapper.countEvent(searchmap);
			// searchmap.put("eventTypes", "0,1,3,5,6,7");
			// searchmap.put("userId", map.get("userId").toString());
			// count2 = eventMapper.countEventByUserId(searchmap);
			// toSolve = toSolve + count2;
			searchmap.remove("userId");
			searchmap.remove("eventTypes");
			search.clear();
			eventIdList.clear();
			search.put("userId", map.get("userId").toString());

			search.put("type", 3);
			eventIdList = workflowService.queryTask(search);

			// 待办任务
			// eventIdList = workflowService.findTodoTask(search);
			if (eventIdList == null || eventIdList.size() == 0) {
				eventIdList.add("0");
			}
			eventIdListAll.addAll(eventIdList);
			searchmap.put("eventIds", eventIdList);
			int todo = eventMapper.countEvent(searchmap);// 我的待办
			searchmap.put("eventIds", eventIdListAll);
			int myevent = eventMapper.countEvent(searchmap);// 我的事件

			// searchmap.put("userId", map.get("userId").toString());
			// count2 = eventMapper.countEventByUserId(searchmap);
			// myevent = myevent + count2;

			// search.put("eventType", Integer.toString(2));
			// int solve = eventMapper.countEvent(search);
			// search.put("eventType", Integer.toString(4));
			// solve += eventMapper.countEvent(search);//我的结办
			// search.put("eventType", Integer.toString(0));//投诉
			// int toSolve = eventMapper.countEvent(search);//我的在办
			// search.put("eventType", Integer.toString(1));//待办事项
			// toSolve += eventMapper.countEvent(search);
			// search.put("eventType", Integer.toString(3));//待审核
			// toSolve += eventMapper.countEvent(search);
			// search.put("eventType", Integer.toString(5));//退回事件
			// toSolve += eventMapper.countEvent(search);
			// search.put("eventType", Integer.toString(6));//回访
			// toSolve += eventMapper.countEvent(search);
			// search.put("eventType", Integer.toString(7));//请求协调
			// toSolve += eventMapper.countEvent(search);
			// search.clear();
			// search.put("nowUserId", map.get("userId").toString());
			// int todo = eventMapper.countEvent(search);//我的待办
			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("all", all);// 所有事件
			result.put("solve", solve);// 我的结办
			result.put("toSolve", toSolve);// 我的在办
			result.put("todo", todo);// 我的待办
			result.put("myevent", myevent);// 我的事件
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<Object, Object> newEven(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			// 查询下一级处理人
			Event eventDraft = new Event();
			if (map.get("patrolRecordId") != null && !(map.get("patrolRecordId").toString().equals(""))) {
				eventDraft.setPatrolRecordId(Integer.parseInt(map.get("patrolRecordId").toString()));
			}
			eventDraft.setReportType(map.get("reportType").toString());
			eventDraft.setEventName(map.get("eventName").toString());
			eventDraft.setEventCode(map.get("eventCode").toString());
			eventDraft.setEventLevel(map.get("eventLevel").toString());
			eventDraft.setEventContent(map.get("eventContent").toString());
			eventDraft.setEventPoint(map.get("eventPoint").toString());
			eventDraft.setAddress(map.get("address").toString());
			eventDraft.setRegionId(map.get("regionId").toString());
			eventDraft.setRiverId(map.get("riverId").toString());
			eventDraft.setProblemType(map.get("problemType").toString());
			eventDraft.setLimitTime(Timestamp.valueOf(map.get("limitTime").toString()));
			eventDraft.setRectification(map.get("rectification").toString());
			eventDraft.setUserId(Integer.parseInt(map.get("userId").toString()));
			eventDraft.setEventTime(Timestamp.valueOf(map.get("eventTime").toString()));
			eventDraft.setEventType("4");
			eventDraft.setEventStatus(1);
			eventDraft.setIsAccountability(0);
			// eventDraft.setSanType(Integer.parseInt(map.get("sanType").toString()));
			eventMapper.addEven(eventDraft);
			int count = eventDraft.getEventId();
			result.put("result", 1);
			result.put("eventId", eventDraft.getEventId());
			result.put("message", "事件新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "新增事件");
		}
		return result;
	}

	@Override
	public Map<Object, Object> deleteEventByImg(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		List<Event> list = eventMapper.queryEvent(map);
		if (list != null && list.size() > 0) {
			search.put("eventId", list.get(0).getEventId());
			eventFileMapper.deleteEventByImg(search);
			result.put("result", 1);
			result.put("message", "操作成功");
		} else {
			result.put("result", 0);
			result.put("message", "查无该事件");
		}
		return result;
	}

	@Override
	public Map<Object, Object> exportEvent(Map<Object, Object> map, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<String, String> mappath = new HashMap<String, String>();
		map.put("recordStatus", "1");
		List<Event> list = eventMapper.queryEvent(map);
		if (list != null && list.size() > 0) {
			try {
				String filename = "问题事件" + new Date().getTime() + ".docx";
				String path = map.get("path").toString() + filename;
				mappath.put("filename", filename);
				mappath.put("path", path);
				logToDocx.getWordByEvent(mappath, list, response);
				result.put("Event", list);
				result.put("result", 1);
				result.put("message", "操作成功");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.put("result", 0);
				result.put("message", "操作异常");
			}
		} else {
			result.put("result", 0);
			result.put("message", "查无数据");
		}
		return result;
	}
}
