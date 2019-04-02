package filemanage.mapper;


import common.model.Directory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectoryMapper {
    boolean deleteByPrimaryKey(String directoryId);

    boolean insert(Directory record);

    boolean insertSelective(Directory record);

    Directory selectByPrimaryKey(String directoryId);

    boolean updateByPrimaryKeySelective(Directory record);

    boolean updateByPrimaryKey(Directory record);

    List<Directory> selectAllWithBeginer(@Param("directoryId") String directoryId);

    int sortAndCount(@Param("directoryId") String directoryId);
}