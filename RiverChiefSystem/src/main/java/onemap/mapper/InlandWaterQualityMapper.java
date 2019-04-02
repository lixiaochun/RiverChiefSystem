package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface InlandWaterQualityMapper {
	
	public List<Map<Object, Object>> findInlandWaterQualityAll(Map<Object, Object> parameter);
	public Map<Object, Object> findInlandWaterQualityById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findInlandWaterQualityByPage(Map<Object, Object> parameter);
	public int insertInlandWaterQuality(Map<Object, Object> parameter);
	public int updateInlandWaterQuality(Map<Object, Object> parameter);
	public int deleteInlandWaterQualityById(int id);
	public int findMaxId();
	
	
	
}