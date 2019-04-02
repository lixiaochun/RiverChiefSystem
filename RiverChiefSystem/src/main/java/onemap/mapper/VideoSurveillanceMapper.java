package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface VideoSurveillanceMapper {
	
	public List<Map<Object, Object>> findVideoSurveillanceAll(Map<Object, Object> map);
	public Map<Object, Object> findVideoSurveillanceById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findVideoSurveillanceByPage(Map<Object, Object> parameter);
	public List<Map<Object, Object>> getDetailInformationAllData(Map<Object, Object> parameter);
	public int insertVideoSurveillance(Map<Object, Object> map);
	public int updateVideoSurveillance(Map<Object, Object> map);
	public int deleteVideoSurveillanceById(int id);
	public int findMaxId();
	public void updateVideoSurveillanceByStatus(Map<Object, Object> map);
}
