package onemap.service;

import java.util.Map;

public interface WaterSourceService {
	
	public Map<Object, Object> findWaterSourceAll(Map<Object, Object> parameter);	
	public Map<Object, Object> findWaterSourceById(int waterLineId);
	public Map<Object, Object> findWaterSourceByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertWaterSource(Map<Object, Object> parameter);
	public Map<Object, Object> updateWaterSource(Map<Object, Object> parameter);
	public Map<Object, Object> deleteWaterSourceById(int id);
	
}
