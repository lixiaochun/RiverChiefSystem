package filemanage.service;



import common.model.Directory;

import java.util.Date;
import java.util.List;

public interface DirectoryMapperService {
    Directory selectByPrimaryKey(String directoryId);
    List<Directory> selectAllWithBeginer(String directoryId);
    int sortAndCount (String directoryId);

    boolean insert(String id, String name, String letter);
}
