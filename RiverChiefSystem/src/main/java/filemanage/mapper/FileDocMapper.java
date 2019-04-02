package filemanage.mapper;

import common.model.FileCount;
import common.model.FileDoc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDocMapper {
    boolean deleteByPrimaryKey(Integer fileId);

    boolean insert(FileDoc record);

    FileDoc selectByPrimaryKey(Integer fileId);

    boolean updateByPrimaryKey(FileDoc record);

    List<FileDoc> selectByNameAndDir(@Param("fileName") String fileName, @Param("directoryId") String directoryId);

    FileDoc selectByNameWithMaxVer(@Param("fileNum") String fileNum, @Param("directoryId") String directoryId);

    List<FileDoc> selectByNum(String fileNum);

    String queryMaxVer(@Param("fileNum") String fileNum, @Param("directoryId") String directoryId);

    List<FileCount> selectByNameAndDirectoryFuzzy(@Param("directoryId") String directoryId, @Param("fileName") String fileName);

    String countFileByDirectoryId(@Param("directoryId") String directoryId);

    int countFileInAWeek();

    List<FileDoc> selectByNameAndDirectoryAndTimeFuzzy(@Param("directoryId") String directoryId, @Param("fileName") String fileName, @Param("startTime") String startTime, @Param("endTime") String endTime);

    //xxx:只显示最大版本文件列表
    List<FileDoc> selectByNameAndDirectoryWithMaxVer(@Param("directoryId") String directoryId, @Param("fileName") String fileName, @Param("startTime") String startTime, @Param("endTime") String endTime);


    List<FileDoc> selectByRiver(@Param("riverid") String riverid);
}