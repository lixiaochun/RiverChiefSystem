package filemanage.service.impl;

import common.model.Directory;
import filemanage.mapper.DirectoryMapper;
import filemanage.service.DirectoryMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DirectoryMapperServiceImpl implements DirectoryMapperService {
    @Autowired
    private DirectoryMapper directoryMapper;

    @Override
    public Directory selectByPrimaryKey(String directoryId) {
        return directoryMapper.selectByPrimaryKey(directoryId);
    }

    @Override
    public List<Directory> selectAllWithBeginer(String directoryId) {
        return directoryMapper.selectAllWithBeginer(directoryId);
    }

    @Override
    public int sortAndCount(String directoryId) {
        return directoryMapper.sortAndCount(directoryId);
    }

    @Override
    public boolean insert(String id, String name, String letter) {
        Directory directory = new Directory();
        directory.setDirectoryId(id);
        directory.setName(name);
        directory.setLetter(letter);
        directory.setTime(new Date());

        return directoryMapper.insert(directory);
    }
}
