package onemap.service;

import java.util.Map;

public interface WaterLineService {
	
	public Map<Object, Object> findWaterLineAll(Map<Object, Object> parameter);	
	public Map<Object, Object> findWaterLineById(int waterLineId);
	public Map<Object, Object> findWaterLineByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertWaterLine(Map<Object, Object> parameter);
	public Map<Object, Object> updateWaterLine(Map<Object, Object> parameter);
	public Map<Object, Object> deleteWaterLineById(int id);
}
