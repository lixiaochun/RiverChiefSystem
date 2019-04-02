package usermanage.mapper;

import java.util.List;
import java.util.Map;

import common.model.Dict;
import common.model.FocusRiver;
import common.model.Region;
import common.model.River;

public interface DateInfoMapper {

	public List<River> queryRiver(Map<Object, Object> map);

	public List<River> queryRiverId(Map<Object, Object> map);

	public List<Region> queryRegion(Map<Object, Object> map);

	public List<Region> queryRegionId(Map<Object, Object> map);

	public List<FocusRiver> queryFocusRiver(Map<Object, Object> map);

	public List<Dict> queryDict(Map<Object, Object> map);

	public int insertRegion(List<Region> region);

	public int updateRegion(List<Region> region);

	public int insertRiver(List<River> river);

	public int updateRiver(List<River> river);

	public List<Dict> queryProblemType(Map<Object, Object> map);
	
	public Dict queryProblemTypeByProblemType(Map<Object, Object> map);
}
