package onemap.service;

import java.util.Map;

public interface WaterQualityService {
	
	public Map<Object, Object> findWaterQualityAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterQualityById(int waterQualityId);
	public Map<Object, Object> findWaterQualityByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertWaterQuality(Map<Object, Object> parameter);
	public Map<Object, Object> updateWaterQuality(Map<Object, Object> parameter);
	public Map<Object, Object> deleteWaterQualityById(int id);
	public Map<Object, Object> getStatisticsInformation(Map<Object, Object> parameter);
	
}
