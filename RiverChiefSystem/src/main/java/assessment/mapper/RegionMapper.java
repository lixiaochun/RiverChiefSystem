package assessment.mapper;

import common.model.Region;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionMapper {
    List<Region> selectAll();

    List<String> selectByParentId(@Param("ParentId") String parentId);

    String selectByName(@Param("name") String name);

    Region selectByPrimeryKey(@Param("regionId") String regionId);

    List<Region> getListByParent(String parentId);

    List<Region> getTree(@Param("parentId") String parentId);
}
