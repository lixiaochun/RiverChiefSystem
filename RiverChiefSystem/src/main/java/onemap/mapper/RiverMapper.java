package onemap.mapper;

import java.util.List;
import java.util.Map;

import common.model.River;
import org.springframework.stereotype.Repository;

@Repository
public interface RiverMapper {

	public List<River> findRiverAll();
	public River findRiverById(int riverId);
	public int insertRiver(Map<Object, Object> parameter);
	public int updateRiver(Map<Object, Object> parameter);
	public int deleteRiverById(int riverId);




    List<String> selectByParent(String parentId);

    List<String> selectByRegion(String regionId);

    List<String> selectByOwner(String ownerId);

    River selectByPrimaryKey(String riverId);

    List<River> getListByParent(String parentId);
}
