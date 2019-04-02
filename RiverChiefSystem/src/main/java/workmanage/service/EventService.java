package workmanage.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface EventService {

	public Map<Object, Object> addEven(Map<Object, Object> map);

	// 手动录入
	public Map<Object, Object> newEven(Map<Object, Object> map);

	public Map<Object, Object> updateEvent(Map<Object, Object> map);

	public Map<Object, Object> updateEventByImg(Map<Object, Object> map);

	public Map<Object, Object> queryEvent(Map<Object, Object> map);

	public Map<Object, Object> queryEventDetail(Map<Object, Object> map);

	public Map<Object, Object> queryWorkflowLog(Map<Object, Object> map);

	public Map<Object, Object> addWorkflowLog(Map<Object, Object> map);

	public Map<Object, Object> claimEvent(Map<Object, Object> map);

	public Map<Object, Object> solveEvent(Map<Object, Object> map);

	public Map<Object, Object> countEvent(Map<Object, Object> map);

	public Map<Object, Object> deleteEventByImg(Map<Object, Object> map);

	public Map<Object, Object> exportEvent(Map<Object, Object> map, HttpServletResponse response);
}
