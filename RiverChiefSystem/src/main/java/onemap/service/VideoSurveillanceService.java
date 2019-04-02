package onemap.service;

import java.util.Map;

public interface VideoSurveillanceService {
	
	public Map<Object, Object> findVideoSurveillanceAll(Map<Object, Object> parameter);
	public Map<Object, Object> findVideoSurveillanceById(int id);
	public Map<Object, Object> findVideoSurveillanceByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertVideoSurveillance(Map<Object, Object> parameter);
	public Map<Object, Object> updateVideoSurveillance(Map<Object, Object> parameter);
	public Map<Object, Object> deleteVideoSurveillanceById(int id);
	public Map<Object, Object> getDetailInformationAllData(Map<Object, Object> parameter);

}
