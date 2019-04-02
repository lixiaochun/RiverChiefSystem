package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.OutfallMapper;
import onemap.service.OutfallService;

@Service
public class OutfallServiceImpl implements OutfallService {

	@Autowired
	private OutfallMapper outfallMapper;

	public Map<Object, Object> findOutfallAll(Map<Object, Object> parameter) {
		// TODO Auto-generated method stub
		String code = (String) parameter.get("code");
		List<Map<Object, Object>> result=new ArrayList<Map<Object,Object>>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		if(parameter.containsKey("regionIdArray")){
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = outfallMapper
						.findOutfallAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}
		}else if(parameter.containsKey("riverIdArray")){
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = outfallMapper
						.findOutfallAll(parameter);
				for(int j=0;j<list.size();j++)
					result.add(list.get(j));
			}	
		}
		
		map.put("items", result);
		return map;
	}

	public Map<Object, Object> findOutfallById(int id) {
		Map<Object, Object> outfall = outfallMapper.findOutfallById(id);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("Outfall", outfall);
		return map;
	}
	
	public Map<Object, Object> insertOutfall(Map<Object, Object> parameter) {
		int maxid=outfallMapper.findMaxId();
		parameter.put("id", maxid+1);
		int result=outfallMapper.insertOutfall(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}
	
	public Map<Object, Object> updateOutfall(Map<Object, Object> parameter) {
		int result=outfallMapper.updateOutfall(parameter);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> deleteOutfallById(int id) {
		int result=outfallMapper.deleteOutfallById(id);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("result", result);
		
		return map;
	}

	public Map<Object, Object> findOutfallByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = outfallMapper.findOutfallByPage(parameter);
		Map<Object, Object> pageInformation=outfallMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);
		
		return map;
	}

}
