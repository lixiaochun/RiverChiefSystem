package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.WaterNumberMapper;
import onemap.service.WaterNumberService;

@Service
public class WaterNumberServiceImpl implements WaterNumberService{

	@Autowired
	private WaterNumberMapper waterNumberMapper;
	
	public Map<Object, Object> findWaterNumberAll(Map<Object, Object> parameter) {
		// TODO Auto-generated method stub
		String code = (String) parameter.get("code");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = waterNumberMapper.findWaterNumberAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = waterNumberMapper.findWaterNumberAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);
		return map;
	}

	
	public Map<Object, Object> findWaterNumberById(int waternumberid) {
		Map<Object, Object> result=waterNumberMapper.findWaterNumberById(waternumberid);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("WaterNumber", result);
		return map;
	}
	
	public Map<Object, Object> insertWaterNumber(Map<Object, Object> parameter) {
		int maxid=waterNumberMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=waterNumberMapper.insertWaterNumber(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateWaterNumber(Map<Object, Object> parameter) {
		int result=waterNumberMapper.updateWaterNumber(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> deleteWaterNumberById(int id) {
		int result=waterNumberMapper.deleteWaterNumberById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findWaterNumberByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = waterNumberMapper.findWaterNumberByPage(parameter);
		Map<Object, Object> pageInformation=waterNumberMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

}
