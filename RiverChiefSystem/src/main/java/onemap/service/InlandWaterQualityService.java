package onemap.service;

import java.util.Map;

public interface InlandWaterQualityService {
	
	public Map<Object, Object> findInlandWaterQualityAll(Map<Object, Object> parameter);
	public Map<Object, Object> findInlandWaterQualityById(int id);
	public Map<Object, Object> findInlandWaterQualityByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertInlandWaterQuality(Map<Object, Object> parameter);
	public Map<Object, Object> updateInlandWaterQuality(Map<Object, Object> parameter);
	public Map<Object, Object> deleteInlandWaterQualityById(int id);
	
}