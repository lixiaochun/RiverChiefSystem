package onemap.service;

import java.util.Map;

public interface PublicsignsService {
	
	public Map<Object, Object> findPublicsignsAll(Map<Object, Object> parameter);
	public Map<Object, Object> findPublicsignsById(int id);
	public Map<Object, Object> findPublicsignsByPage(Map<Object, Object> parameter);
	public Map<Object, Object> insertPublicsigns(Map<Object, Object> parameter);
	public Map<Object, Object> updatePublicsigns(Map<Object, Object> parameter);
	public Map<Object, Object> deletePublicsignsById(int id);
	public Map<Object, Object> getRiverName(Map<Object, Object> parameter);
	
}
