package workmanage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import common.model.Dict;
import common.model.Event;
import common.model.Statistics;
import common.model.WorkflowLog;

public interface EventMapper {

	public void addEven(Event event);
	
	public void updateEvent(Map<Object, Object> map);
	
	public List<Event> queryEvent(Map<Object, Object> map);
	
	public int countEvent(Map<Object, Object> map);
	
	public List<Event> queryEventByUserId(Map<Object, Object> map);
	
	public int countEventByUserId(Map<Object, Object> map);
	
	public List<WorkflowLog> queryWorkflowLog(Map<Object, Object> map);
	
	public int addWorkflowLog(Map<Object, Object> map);
	
	public void updateWorkflowLog(Map<Object, Object> map);
	
	public List<Dict> queryEventDict(Map<Object, Object> map);
	
	public List<Integer> queryEventId(Map<Object, Object> map);
	
	public List<Event> queryEventByPatrol(Map<Object, Object> map);
}
