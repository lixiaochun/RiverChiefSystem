package onemap.service;

import common.model.River;

import java.util.List;
import java.util.Map;

public interface RiverService {
	
	public Map<Object, Object> findRiverAll();
	public Map<Object, Object> findRiverById(int id);



    List<String> selectByParent(String parentId);

    List<String> selectByRegion(String regionId);

    List<String> selectByOwner(String ownerId);

    River selectByPrimaryKey(String riverId);

    List<River> getListByParent(String parentId);
}