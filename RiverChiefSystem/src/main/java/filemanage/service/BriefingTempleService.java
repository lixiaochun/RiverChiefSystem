package filemanage.service;

import common.model.BriefingTemple;

import java.util.List;

public interface BriefingTempleService {
    boolean deleteByPrimaryKey(Integer briefingId);

    boolean insert(String name, String title, String body, String summary, String regionid);

    BriefingTemple selectByPrimaryKey(Integer briefingId);

    boolean updateByPrimaryKeyWithBLOBs(String id, String name, String title, String body, String summary, String regionid);

    List<BriefingTemple> selectTitleByRegion(String regionId);
}
