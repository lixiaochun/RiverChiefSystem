package assessment.service.impl;

import assessment.mapper.RegionMapper;
import assessment.service.RegionService;
import common.model.Region;
import filemanage.service.RegionCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private RegionCut regionCut;

    public List<Region> selectAll() {
        return regionMapper.selectAll();
    }

    public List<String> selectByParentId(String ParentId) {
        return regionMapper.selectByParentId(ParentId);
    }

    public String selectByName(String name) {
        return regionMapper.selectByName(name);
    }

    public Region selectByPrimeryKey(String regionId) {
        return regionMapper.selectByPrimeryKey(regionId);
    }

    public List<Region> getListByParent(String parentId) {
        return regionMapper.getListByParent(parentId);
    }

    public List<Region> getTree(String parentId) throws Exception {
        String region="";
		try {
			region = regionCut.cut(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return regionMapper.getTree(region);
    }
}
