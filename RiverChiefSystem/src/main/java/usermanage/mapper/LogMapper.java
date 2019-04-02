package usermanage.mapper;

import java.util.List;
import java.util.Map;

import common.model.SystemLog;


public interface LogMapper {

	public void addLog(Map<Object, Object> map);
	
	public void addLog1(Map<Object, Object> map);
	
	public List<SystemLog> queryLog(Map<Object, Object> map);
	
	public int countLog(Map<Object, Object> map);
	
	public void addWorkflowLog(Map<Object, Object> map);
	
	public List<SystemLog> queryEventLog(Map<Object, Object> map);
}
