package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface WaterQualityMapper {

	public List<Map<Object, Object>> findWaterQualityAll(Map<Object, Object> parameter);
	public Map<Object, Object> findWaterQualityById(int waterQualityId);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findWaterQualityByPage(Map<Object, Object> parameter);
	public int insertWaterQuality(Map<Object, Object> parameter);
	public int updateWaterQuality(Map<Object, Object> parameter);
	public int deleteWaterQualityById(int id);
	public int findMaxId();
	public List<Map<Object, Object>> getStatisticsByRegionId();
	public List<Map<Object, Object>> getStatisticsByType(Map<Object, Object> parameter);
	
}
