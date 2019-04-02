package filemanage.mapper;

import common.model.BriefingTemple;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BriefingTempleMapper {
    boolean deleteByPrimaryKey(Integer briefingId);

    boolean insert(BriefingTemple record);

    BriefingTemple selectByPrimaryKey(Integer briefingId);

    boolean updateByPrimaryKeyWithBLOBs(BriefingTemple record);

    List<BriefingTemple> selectTitleByRegion(@Param("regionId") String regionId);
}