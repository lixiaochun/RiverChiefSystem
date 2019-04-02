package onemap.service;

import java.util.Map;

public interface PollutantSourceService {
	
	public Map<Object, Object> findPollutantsourceAll(Map<Object, Object> parameter);
	public Map<Object, Object> findPollutantsourceById(int id);
	public Map<Object, Object> findPollutantsourceByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertPollutantsource(Map<Object, Object> parameter);
	public Map<Object, Object> updatePollutantsource(Map<Object, Object> parameter);
	public Map<Object, Object> deletePollutantsourceById(int id);
}
