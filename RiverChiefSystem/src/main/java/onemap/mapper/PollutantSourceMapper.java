package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface PollutantSourceMapper {
	
	public List<Map<Object, Object>> findPollutantSourceAll(Map<Object, Object> parameter);
	public Map<Object, Object> findPollutantSourceById(int pollutantSourceId);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findPollutantSourceByPage(Map<Object, Object> parameter);
	public int insertPollutantSource(Map<Object, Object> parameter);
	public int updatePollutantSource(Map<Object, Object> parameter);
	public int deletePollutantSourceById(int id);
	public int findMaxId();
	
}
