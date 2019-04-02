package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.WaterSourceMapper;
import onemap.service.WaterSourceService;

@Service
public class WaterSourceServiceImpl implements WaterSourceService {

	@Autowired
	public WaterSourceMapper waterSourceMapper;

	public Map<Object, Object> findWaterSourceAll(Map<Object, Object> parameter) {		
		String code = (String) parameter.get("code");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = waterSourceMapper.findWaterSourceAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = waterSourceMapper.findWaterSourceAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);

		return map;
	}
	

	public Map<Object, Object> findWaterSourceById(int waterSourceId) {

		Map<Object, Object> result = waterSourceMapper.findWaterSourceById(waterSourceId);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("AllWaterSource", result);

		return map;
	}
	
	public Map<Object, Object> insertWaterSource(Map<Object, Object> parameter) {
		int maxid=waterSourceMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=waterSourceMapper.insertWaterSource(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateWaterSource(Map<Object, Object> parameter) {
		int result=waterSourceMapper.updateWaterSource(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> deleteWaterSourceById(int id) {
		int result=waterSourceMapper.deleteWaterSourceById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findWaterSourceByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = waterSourceMapper.findWaterSourceByPage(parameter);
		Map<Object, Object> pageInformation=waterSourceMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

}
