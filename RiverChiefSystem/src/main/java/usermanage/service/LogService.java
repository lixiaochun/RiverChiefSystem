package usermanage.service;

import java.util.Map;

public interface LogService {
	
	/**
	 * 
	 * @param userId
	 * @param operInterface
	 * @param operName
	 * @param operType "select,update,delete,insert"
	 * @param logType "info,debug,warning,error,trace"
	 */
	public void addLog(String userId,String operInterface, String operName, String operType,String logType,String logDetail);
	
	/**
	 * 
	 * @param operInterface
	 * @param operName
	 * @param operType
	 * @param logType
	 * @param logDetail
	 */
	public void addLog(String operInterface, String operName, String operType,String logType,String logDetail);
	
	public Map<Object, Object> queryLog(Map<Object, Object> map);
}
