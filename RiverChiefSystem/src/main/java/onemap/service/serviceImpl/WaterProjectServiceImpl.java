package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.WaterProjectMapper;
import onemap.service.WaterProjectService;

@Service
public class WaterProjectServiceImpl implements WaterProjectService{

	@Autowired
	private WaterProjectMapper waterProjectMapper;
	
	
	public Map<Object, Object> findWaterProjectAll(Map<Object, Object> parameter) {
		// TODO Auto-generated method stub
		String code = (String) parameter.get("code");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();

		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = waterProjectMapper.findWaterProjectAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = waterProjectMapper.findWaterProjectAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		map.put("items", result);
		return map;
	}

	public Map<Object, Object> findWaterProjectById(int id) {
		
		Map<Object, Object> result=waterProjectMapper.findWaterProjectById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("WaterProject", result);
		
		return map;
	}
	
	public Map<Object, Object> insertWaterProject(Map<Object, Object> parameter) {
		int maxid=waterProjectMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=waterProjectMapper.insertWaterProject(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateWaterProject(Map<Object, Object> parameter) {
		int result=waterProjectMapper.updateWaterProject(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> deleteWaterProjectById(int id) {
		int result=waterProjectMapper.deleteWaterProjectById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	@Override
	public Map<Object, Object> findWaterProjectByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = waterProjectMapper.findWaterProjectByPage(parameter);
		Map<Object, Object> pageInformation=waterProjectMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

}
