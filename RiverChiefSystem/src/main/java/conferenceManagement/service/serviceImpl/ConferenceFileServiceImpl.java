package conferenceManagement.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.model.Conference;
import common.model.ConferenceFile;
import common.util.DownloadFile;
import conferenceManagement.mapper.ConferenceFileMapper;
import conferenceManagement.mapper.ConferenceMapper;
import conferenceManagement.service.ConferenceFileService;

@Service("ConferenceFileService")
public class ConferenceFileServiceImpl implements ConferenceFileService {

	@Autowired
	private ConferenceMapper conferenceMapper;

	@Autowired
	private ConferenceFileMapper conferenceFileMapper;

	DownloadFile downloadFile = new DownloadFile();

	@Transactional(readOnly = false)
	public Map<Object, Object> uploadFile(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			int conferenceId = Integer.parseInt(map.get("conferenceId").toString());
			search.put("conferenceId", conferenceId);
			List<Conference> list = conferenceMapper.queryConference(search);
			if (list != null && list.size() > 0) {
				List<Map<Object, Object>> fileinfo = (List<Map<Object, Object>>) map.get("fileinfo");
				List<ConferenceFile> conferenceFilelist = new ArrayList<ConferenceFile>();
				if (fileinfo != null && fileinfo.size() > 0) {
					for (Map<Object, Object> filemap : fileinfo) {
						ConferenceFile conferenceFile = new ConferenceFile();
						String type = filemap.get("type").toString();
						conferenceFile.setType(type);
						conferenceFile.setConferenceId(conferenceId);
						if (type.equals("image")) {
							String url = filemap.get("url").toString();
							String smallurl = filemap.get("smallurl").toString();
							conferenceFile.setFileUrl(url);
							conferenceFile.setSmallUrl(smallurl);
						}
						if (type.equals("file")) {
							String url = filemap.get("url").toString();
							conferenceFile.setFileUrl(url);
						}
						conferenceFile.setStatus(1);
						conferenceFile.setFileName(filemap.get("filename").toString() + "." + filemap.get("tail").toString());
						conferenceFilelist.add(conferenceFile);
					}
					if (conferenceFilelist.size() > 0) {
						int count = conferenceFileMapper.insertConferenceFile(conferenceFilelist);
						result.put("result", 1);
						result.put("message", "操作成功");
						result.put("conferenceFilelist", conferenceFilelist);
					} else {
						result.put("result", 0);
						result.put("message", "无改文件");
					}
				} else {
					result.put("result", 0);
					result.put("message", "无文件数据");
				}
			} else {
				result.put("result", 0);
				result.put("message", "无该数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作异常");
		}
		return result;
	}

	public Map<Object, Object> queryFile(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			search.put("conferenceId", map.get("conferenceId"));
			List<Conference> list = conferenceMapper.queryConference(search);
			if (list != null && list.size() > 0) {
				map.put("conferenceId", list.get(0).getConferenceId());
				List<ConferenceFile> conferenceFilelist = conferenceFileMapper.queryFile(map);
				result.put("conferenceFilelist", conferenceFilelist);
				result.put("result", 1);
				result.put("message", "查询成功");
			} else {
				result.put("result", 0);
				result.put("message", "查无该会议");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	public Map<Object, Object> deleteFile(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			search.put("conferenceFileId", map.get("conferenceFileId"));
			List<ConferenceFile> list = conferenceFileMapper.queryFile(map);
			if (list != null && list.size() > 0) {
				map.put("conferenceFileId", list.get(0).getConferenceFileId());
				map.put("status", 0);
				conferenceFileMapper.updateConferenceFile(map);
				result.put("result", 1);
				result.put("message", "修改成功");
			} else {
				result.put("result", 0);
				result.put("message", "查无该会议");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "修改异常");
		}
		return result;
	}

	public Map<Object, Object> downloadFile(Map<Object, Object> map, HttpServletRequest request, HttpServletResponse reponse) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> search = new HashMap<Object, Object>();
		try {
			search.put("conferenceFileId", map.get("conferenceFileId"));
			List<ConferenceFile> list = conferenceFileMapper.queryFile(map);
			if (list != null && list.size() > 0) {
				map.put("conferenceFileId", list.get(0).getConferenceFileId());
				String savePath = request.getSession().getServletContext().getRealPath(list.get(0).getFileUrl());
				map.put("savePath", savePath);
				map.put("filename", list.get(0).getFileName());
				result = downloadFile.download(map, reponse);
			} else {
				result.put("result", 0);
				result.put("message", "查无该会议");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "修改异常");
		}
		return result;
	}

}
