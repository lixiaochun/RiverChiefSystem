package onemap.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.CollectionRiverMapper;
import onemap.service.CollectionRiverService;

@Service
public class CollectionRiverServiceImpl implements CollectionRiverService{

	@Autowired
	private CollectionRiverMapper collectionRiverMapper;
	
	/**
	 *查询收藏河流信息
	 */
	public Map<Object, Object> findCollectionRiverByUserId(int userId) {
		Map<Object, Object> map=new HashMap<Object, Object>();

		List<Map<Object, Object>> list=collectionRiverMapper.findCollectionRiverByUserId(userId);
		map.put("items", list);
		if(list.size()>0) {
			map.put("result", 1);
			map.put("message", "操作成功");
		}else {
			map.put("result", 2);
			map.put("message", "查无数据");
		}
	
		return map;
	}
	/**
	 * 新增收藏河流
	 */
	public Map<Object, Object> insertCollectionRiver(Map<Object, Object> parameter) {
		Map<Object, Object> map=new HashMap<Object, Object>();
		int result=collectionRiverMapper.insertCollectionRiver(parameter);
		map.put("result", result);
		if(result==1) {
			map.put("message", "添加成功");
		}else {
			map.put("message", "添加失败");
		}
		
		return map;
	}

	/**
	 * 删除收藏河流
	 */
	public Map<Object, Object> deleteCollectionRiverById(Map<Object, Object> parameter) {
		Map<Object, Object> map=new HashMap<Object, Object>();
		int result=collectionRiverMapper.deleteCollectionRiverById(parameter);
		map.put("result", result);
		if(result==1) {
			map.put("message", "删除成功");
		}else {
			map.put("message", "删除失败");
		}
		
		return map;
	}

}
