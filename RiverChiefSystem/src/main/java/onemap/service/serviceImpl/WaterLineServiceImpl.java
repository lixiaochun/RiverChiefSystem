package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.WaterLineMapper;
import onemap.service.WaterLineService;

@Service
public class WaterLineServiceImpl implements WaterLineService {

	@Autowired
	public WaterLineMapper waterLineMapper;

	public Map<Object, Object> findWaterLineAll(Map<Object, Object> parameter) {		
		String code = (String) parameter.get("code");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = waterLineMapper.findWaterLineAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = waterLineMapper.findWaterLineAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);

		return map;
	}
	

	public Map<Object, Object> findWaterLineById(int waterLineId) {
		// TODO Auto-generated method stub
		Map<Object, Object> result = waterLineMapper.findWaterLineById(waterLineId);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("AllWaterLine", result);

		return map;
	}
	
	public Map<Object, Object> insertWaterLine(Map<Object, Object> parameter) {
		int maxid=waterLineMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=waterLineMapper.insertWaterLine(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateWaterLine(Map<Object, Object> parameter) {
		int result=waterLineMapper.updateWaterLine(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> deleteWaterLineById(int id) {
		int result=waterLineMapper.deleteWaterLineById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findWaterLineByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = waterLineMapper.findWaterLineByPage(parameter);
		Map<Object, Object> pageInformation=waterLineMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

}
