package onemap.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.model.River;

import onemap.mapper.RiverMapper;
import onemap.service.RiverService;

@Service
public class RiverServiceImpl implements RiverService{

	@Autowired
	private RiverMapper riverMapper;
	
	public Map<Object, Object> findRiverAll() {
		// TODO Auto-generated method stub
		List<River> list=riverMapper.findRiverAll();
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (list.size() > 0) {
			map.put("result", 1);
			map.put("AllRiver", list);
		} else {
			map.put("result", 0);
			map.put("AllRiver", null);
		}

		return map;
		
	}

	public Map<Object, Object> findRiverById(int id) {
		// TODO Auto-generated method stub
		return null;
	}


    public List<String> selectByParent(String parentId) {
        return riverMapper.selectByParent(parentId);
    }

    public List<String> selectByRegion(String regionId) {
        return riverMapper.selectByRegion(regionId);
    }

    public List<String> selectByOwner(String ownerId) {
        return riverMapper.selectByOwner(ownerId);
    }

    public River selectByPrimaryKey(String riverId) {
        return riverMapper.selectByPrimaryKey(riverId);
    }

    public List<River> getListByParent(String parentId) {
        return riverMapper.getListByParent(parentId);
    }
}
