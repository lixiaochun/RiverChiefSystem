package filemanage.service;


import common.model.FileCount;
import common.model.FileDoc;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FileDocMapperService {
    boolean deleteByPrimaryKey(Integer fileId);

    boolean insert(String directory_id, String name, String type, int struserId, String file_num, String url, int version, Date date, String riverid);

    FileDoc selectByPrimaryKey(Integer fileId);

    boolean updateByPrimaryKey(FileDoc record);

    List<FileDoc> selectByNameAndDir(String fileName, String directoryId);

    FileDoc selectByNameWithMaxVer(String fileNum, String directoryId);

    List<FileDoc> selectByNum(String fileNum);

    int queryMaxVer(String fileNum, String directoryId);

    List<FileCount> selectByNameAndDirectoryFuzzy(String directoryId, String fileName);

    int countFileByDirectoryId(String directoryId);

    int countFileInAWeek();

    List<FileDoc> selectByNameAndDirectoryAndTimeFuzzy(String directoryId, String fileName, String startTime, String endTime);

    List<FileDoc> selectByNameAndDirectoryWithMaxVer(String directoryId, String fileName, String startTime, String endTime);

    Map<Object, Object> countDirectory();

    Map<Object, Object> cutDocPage(String page, String size, List<FileDoc> list);

    boolean recoverByPrimaryKey(Integer fileId);

    //
    Map<Object, Object> selectByRiver(String riverid);

}
