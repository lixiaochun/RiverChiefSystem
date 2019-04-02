package onemap.mapper;

import java.util.List;
import java.util.Map;

public interface PublicsignsMapper {

	public List<Map<Object, Object>> findPublicsignsAll(Map<Object, Object> parameter);
	public Map<Object, Object> findPublicsignsById(int id);
	public Map<Object, Object> getPagingInformation(Map<Object, Object> parameter);
	public List<Map<Object, Object>> findPublicsignsByPage(Map<Object, Object> parameter);
	public int insertPublicsigns(Map<Object, Object> parameter);
	public int updatePublicsigns(Map<Object, Object> parameter);
	public int deletePublicsignsById(int id);
	public int findMaxId();
	public Map<Object, Object> getRiverName(int id);
	public List<Map<Object, Object>>  findPublicsignsByLevel(Map<Object, Object> parameter);
}
