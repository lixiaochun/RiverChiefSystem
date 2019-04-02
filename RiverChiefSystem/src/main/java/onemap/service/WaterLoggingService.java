package onemap.service;

import java.util.Map;

public interface WaterLoggingService {

	public Map<Object, Object> findWaterloggingAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterloggingById(int id);
	public Map<Object, Object> findWaterloggingByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertWaterlogging(Map<Object, Object> parameter);
	public Map<Object, Object> updateWaterlogging(Map<Object, Object> parameter);
	public Map<Object, Object> deleteWaterloggingById(int id);
	
}
