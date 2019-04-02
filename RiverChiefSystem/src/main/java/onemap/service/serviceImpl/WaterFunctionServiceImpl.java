package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.WaterFunctionMapper;
import onemap.service.WaterFunctionService;

@Service
public class WaterFunctionServiceImpl implements WaterFunctionService{
	
	@Autowired
	private WaterFunctionMapper waterFunctionMapper;

	
	public Map<Object, Object> findWaterFunctionAll(Map<Object, Object> parameter) {
		// TODO Auto-generated method stub
		String code = (String) parameter.get("code");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = waterFunctionMapper.findWaterFunctionAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = waterFunctionMapper.findWaterFunctionAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);
		return map;
	}

	
	public Map<Object, Object> findWaterFunctionById(int id) {

		Map<Object, Object> result=waterFunctionMapper.findWaterFunctionById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("WaterFunction", result);
		
		return map;
	}
	
	public Map<Object, Object> insertWaterFunction(Map<Object, Object> parameter) {
		int maxid=waterFunctionMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=waterFunctionMapper.insertWaterFunction(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateWaterFunction(Map<Object, Object> parameter) {
		int result=waterFunctionMapper.updateWaterFunction(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> deleteWaterFunctionById(int id) {
		int result=waterFunctionMapper.deleteWaterFunctionById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findWaterFunctionByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = waterFunctionMapper.findWaterFunctionByPage(parameter);
		Map<Object, Object> pageInformation=waterFunctionMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}


}
