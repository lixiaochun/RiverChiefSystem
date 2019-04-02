package assessment.service;

import common.model.Region;

import java.util.List;

public interface RegionService {
    List<Region> selectAll();

    List<String> selectByParentId(String ParentId);

    String selectByName(String name);

    Region selectByPrimeryKey(String regionId);

    List<Region> getListByParent(String parentId);

    List<Region> getTree(String parentId) throws Exception;

}
