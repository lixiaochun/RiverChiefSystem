package conferenceManagement.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ConferenceFileService {

	public Map<Object, Object> uploadFile(Map<Object, Object> map);

	public Map<Object, Object> queryFile(Map<Object, Object> map);

	public Map<Object, Object> deleteFile(Map<Object, Object> map);

	public Map<Object, Object> downloadFile(Map<Object, Object> map, HttpServletRequest request, HttpServletResponse reponse);
}
