package filemanage.service.impl;

import common.model.FileCount;
import common.model.FileDoc;
import filemanage.mapper.DirectoryMapper;
import filemanage.mapper.FileDocMapper;
import filemanage.service.FileDocMapperService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileDocMapperServiceImpl implements FileDocMapperService {

    @Autowired
    private FileDocMapper fileDocMapper;
    @Autowired
    private DirectoryMapper directoryMapper;

    @Override
    public boolean deleteByPrimaryKey(Integer fileId) {
        FileDoc fileDoc = fileDocMapper.selectByPrimaryKey(fileId);
        fileDoc.setTf(0);
        //作为代替修改状态
        return fileDocMapper.updateByPrimaryKey(fileDoc);
    }

    @Override
    public boolean recoverByPrimaryKey(Integer fileId) {
        FileDoc fileDoc = fileDocMapper.selectByPrimaryKey(fileId);
        fileDoc.setTf(1);
        return fileDocMapper.updateByPrimaryKey(fileDoc);
    }

    @Override
    public Map<Object, Object> selectByRiver(String riverid) {
        Map<Object, Object> map = new HashMap<>();
        List<FileDoc> fileDocs = fileDocMapper.selectByRiver(riverid);
        List<FileDoc> d1 = new ArrayList<>();
        List<FileDoc> d2 = new ArrayList<>();
        List<FileDoc> d3 = new ArrayList<>();
        List<FileDoc> d4 = new ArrayList<>();
        for (FileDoc fileDoc : fileDocs) {
            String firstword = fileDoc.getDirectoryId().substring(0, 1);
            if ("1".equals(firstword)) {
                d1.add(fileDoc);
            } else if ("2".equals(firstword)) {
                d2.add(fileDoc);
            } else if ("3".equals(firstword)) {
                d3.add(fileDoc);
            } else if ("4".equals(firstword)) {
                d4.add(fileDoc);
            }
        }
        map.put("1", d1);
        map.put("2", d2);
        map.put("3", d3);
        map.put("4", d4);
        map.put("result", 1);
        return map;
    }

    @Override
    public boolean insert(String directory_id, String name, String type, int struserId, String file_num, String url, int version, Date date, String riverid) {
        FileDoc fileDoc = new FileDoc();
        fileDoc.setDirectoryId(directory_id);
        fileDoc.setDownloadNum(0);
        fileDoc.setFileName(name);
        fileDoc.setFileType(type);
        fileDoc.setVersion(version);
        fileDoc.setSubmitTime(date);
        fileDoc.setUserId(struserId);
        fileDoc.setFileNum(file_num);
        fileDoc.setUrl(url);
        fileDoc.setTf(1);
        fileDoc.setRiverId(riverid);
        return fileDocMapper.insert(fileDoc);
    }

    @Override
    public FileDoc selectByPrimaryKey(Integer fileId) {
        return fileDocMapper.selectByPrimaryKey(fileId);
    }

    @Override
    public boolean updateByPrimaryKey(FileDoc record) {
        return fileDocMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<FileDoc> selectByNameAndDir(String fileName, String directoryId) {
        return fileDocMapper.selectByNameAndDir(fileName, directoryId);
    }

    @Override
    public FileDoc selectByNameWithMaxVer(String fileNum, String directoryId) {
        return fileDocMapper.selectByNameWithMaxVer(fileNum, directoryId);
    }

    @Override
    public List<FileDoc> selectByNum(String fileNum) {
        return fileDocMapper.selectByNum(fileNum);
    }

    @Override
    public int queryMaxVer(String fileNum, String directoryId) {
        String var = fileDocMapper.queryMaxVer(fileNum, directoryId);
        if (var == null)
            return 0;
        else {
            return Integer.parseInt(var);
        }
    }

    @Override
    public List<FileCount> selectByNameAndDirectoryFuzzy(String directoryId, String fileName) {
//        if (Integer.parseInt(directoryId)==0)
//            return fileDocMapper.selectANDgourpByNameFuzzy(fileName);
//        else
        return fileDocMapper.selectByNameAndDirectoryFuzzy(directoryId, fileName);
    }

    @Override
    public int countFileByDirectoryId(String directoryId) {
        String var = fileDocMapper.countFileByDirectoryId(directoryId);
        if (var == null)
            return 0;
        else {
            return Integer.parseInt(var);
        }
    }

    @Override
    public int countFileInAWeek() {
        return fileDocMapper.countFileInAWeek();
    }

    @Override
    public List<FileDoc> selectByNameAndDirectoryAndTimeFuzzy(String directoryId, String fileName, String startTime, String endTime) {
//        if (Integer.parseInt(directoryId)==0)
//            return fileDocMapper.selectANDgourpByNameAndDirectoryFuzzyINtime(fileName,startTime,endTime);
//        else
        return fileDocMapper.selectByNameAndDirectoryAndTimeFuzzy(directoryId, fileName, startTime, endTime);
    }

    @Override
    public List<FileDoc> selectByNameAndDirectoryWithMaxVer(String directoryId, String fileName, String startTime, String endTime) {
        return fileDocMapper.selectByNameAndDirectoryWithMaxVer(directoryId, fileName, startTime, endTime);
    }

    @Override
    public Map<Object, Object> countDirectory() {
        Map<Object, Object> map = new HashMap<>();
        int i;
        List<JSONObject> countList = new ArrayList<JSONObject>();
        for (i = 1; i <= 4; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", i);
            jsonObject.put("totalNum", fileDocMapper.countFileByDirectoryId(String.valueOf(i)));
            jsonObject.put("sortationNum", directoryMapper.sortAndCount(String.valueOf(i)));
            countList.add(jsonObject);
        }
        map.put("countList", countList);
        map.put("result", 1);
        return map;
    }

    @Override
    public Map<Object, Object> cutDocPage(String page, String size, List<FileDoc> list) {
        Map<Object, Object> map = new HashMap<>();
        int pagenum;
        int pagesize;
        if (page == null)
            pagenum = 1;
        else
            pagenum = Integer.parseInt(page);
        if (size == null)
            pagesize = 10;
        else
            pagesize = Integer.parseInt(size);
        int i;
        List<FileDoc> fileDocs = list;
        if (fileDocs.size() % pagesize == 0) {
            map.put("totalPage", fileDocs.size() / pagesize);
        } else {
            map.put("totalPage", fileDocs.size() / pagesize + 1);
        }
        if ((pagenum - 1) * pagesize < fileDocs.size()) {
            List<FileDoc> fileDocs1 = new ArrayList<>();
            for (i = 0 + (pagenum - 1) * pagesize; i < fileDocs.size() && i < pagenum * pagesize; i++) {
                fileDocs1.add(fileDocs.get(i));
            }
            map.put("fileList", fileDocs1);
            map.put("result", 1);
            map.put("totalLength", fileDocs.size());
        } else {
            map.put("result", -5); //超页
        }
        return map;
    }


}
