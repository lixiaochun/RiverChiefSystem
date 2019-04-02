package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface WaterLoggingMapper {

	public List<Map<Object, Object>> findWaterLoggingAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterLoggingById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findWaterLoggingByPage(Map<Object, Object> parameter);
	public int insertWaterLogging(Map<Object, Object> parameter);
	public int updateWaterLogging(Map<Object, Object> parameter);
	public int deleteWaterLoggingById(int id);
	public int findMaxId();
	
}