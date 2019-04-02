package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.OnemapEventMapper;
import onemap.service.OnemapEventService;

@Service
public class OnemapEventServiceImpl implements OnemapEventService{

	@Autowired
	private OnemapEventMapper onemapEventMapper;
	
	//查询地图坐标点
	public Map<Object, Object> findEventAll(Map<Object, Object> parameter) {
		String code = (String) parameter.get("code");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = onemapEventMapper.findEventAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = onemapEventMapper.findEventAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);
		
		return map;
	}
	
	public Map<Object, Object> findEventPoint(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<String> eventIdList = (List<String>) parameter.get("eventIdList");
		List<Map<Object, Object>> result= null;
		if(eventIdList.size() == 0) {
			result = new ArrayList<>();
		}else {
			result = onemapEventMapper.findEventPoint(parameter);
		}
		map.put("items", result);
		
		return map;
	}

	//根据id获取详细信息
	public Map<Object, Object> findEventById(int id) {
		Map<Object, Object> result=onemapEventMapper.findEventById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("Event", result);
		
		return map;
	}
	
	//事件统计，统计各行政区事件数据量
	public Map<Object, Object> getEventStatisticsInformation(Map<Object, Object> parameter){
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> result=onemapEventMapper.getEventStatisticsInformation(parameter);
		map.put("items", result);
		
		return map;
	}


}
