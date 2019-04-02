package onemap.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onemap.mapper.PublicsignsMapper;
import onemap.service.PublicsignsService;
import onemap.util.LocationUtils;


@Service
public class PublicsignsServiceImpl implements PublicsignsService {

	@Autowired
	private PublicsignsMapper publicSignsMapper;

	/**
	 * 根据行政区查询点
	 */
	public Map<Object, Object> findPublicsignsAll(Map<Object, Object> parameter) {
		String code = (String) parameter.get("code");
		List<Map<Object, Object>> result = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		if (parameter.containsKey("regionIdArray")) {
			String regionIdArray[] = (String[]) parameter.get("regionIdArray");
			for (int i = 0; i < regionIdArray.length; i++) {
				parameter.put("regionId", regionIdArray[i]);
				List<Map<Object, Object>> list = publicSignsMapper.findPublicsignsAll(parameter);
				for (int j = 0; j < list.size(); j++)
					result.add(list.get(j));
			}
		} else if (parameter.containsKey("riverIdArray")) {
			String riverIdArray[] = (String[]) parameter.get("riverIdArray");
			for (int i = 0; i < riverIdArray.length; i++) {
				parameter.put("riverId", riverIdArray[i]);
				List<Map<Object, Object>> list = publicSignsMapper.findPublicsignsAll(parameter);
				for (int j = 0; j < list.size(); j++)
					result.add(list.get(j));
			}
		}
		map.put("items", result);
		return map;
	}

	/**
	 * 查询详情
	 */
	public Map<Object, Object> findPublicsignsById(int id) {
		Map<Object, Object> result = publicSignsMapper.findPublicsignsById(id);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("result", 1);
		map.put("PublicSigns", result);
		return map;
	}

	public Map<Object, Object> insertPublicsigns(Map<Object, Object> parameter) {
		int maxid = publicSignsMapper.findMaxId();
		parameter.put("id", maxid + 1);
		int result = publicSignsMapper.insertPublicsigns(parameter);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("result", result);

		return map;
	}

	public Map<Object, Object> updatePublicsigns(Map<Object, Object> parameter) {
		int result = publicSignsMapper.updatePublicsigns(parameter);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("result", result);

		return map;
	}

	public Map<Object, Object> deletePublicsignsById(int id) {
		int result = publicSignsMapper.deletePublicsignsById(id);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("result", result);

		return map;
	}

	/**
	 * 分页查询
	 */
	public Map<Object, Object> findPublicsignsByPage(Map<Object, Object> parameter) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<Map<Object, Object>> list = publicSignsMapper.findPublicsignsByPage(parameter);
		Map<Object, Object> pageInformation = publicSignsMapper.getPagingInformation(parameter);
		pageInformation.put("resultCount", list.size());
		map.put("pageInformation", pageInformation);
		map.put("items", list);

		return map;
	}

	/**
	 * 获取巡河轨迹附近的公示牌河流
	 */
	public Map<Object, Object> getRiverName(Map<Object, Object> parameter){
		String point=(String) parameter.get("point");
		String riverId=(String) parameter.get("riverId");
		System.out.println("riverId:"+riverId);
		System.out.println("point:"+point);
		//查询所有公示牌
		Map<Object, Object> allParameter=new HashMap<>();
		allParameter.put("regionId", null);
		allParameter.put("riverId", null);
		allParameter.put("keyword", null);
		List<Map<Object, Object>> list = publicSignsMapper.findPublicsignsAll(allParameter);

		String points[]=point.split(",");
		Set<Integer> idSet=new HashSet<Integer>();
		for(int i=0;i<points.length;i+=2) {
			double longitute=Double.valueOf(points[i]);
			double latitude=Double.valueOf(points[i+1]);
			int id=getMinDistancePublicsings(list,longitute,latitude);
			idSet.add(id);
		}
		Iterator<Integer> iterator = idSet.iterator();
		Set<Map<Object, Object>> riverNameSet=new HashSet<Map<Object, Object>>();
		while(iterator.hasNext()){  
		   int id=iterator.next();
		   Map<Object, Object> map = publicSignsMapper.getRiverName(id);
		   if(map==null)
			   continue;
		   if(!map.get("river_id").equals(riverId)) {
			   riverNameSet.add(map);
		   }
		}
		Map<Object, Object> map=new HashMap<>();
		map.put("items", riverNameSet);
		return map;
	};
	//获取一个点最近的公示牌id
	private int getMinDistancePublicsings(List<Map<Object, Object>> list, double longitute, double latitude) {
		int id = -1;
		double length = -1;
		for (int i = 0; i < list.size(); i++) {
			Map<Object, Object> point = list.get(i);
			if(point.get("x") != null && point.get("y")!=null) {
				double distance = LocationUtils.getDistance(longitute, latitude, (double) point.get("x"),
						(double) point.get("y"));
				if (distance < length || length < 0) {
					length = distance;
					id = (int) point.get("id");
				}
			}
		}
		return id;
	}

}
