package conferenceManagement.service;

import java.util.Map;

public interface ConfereeService {

	public Map<Object, Object> queryConferee(Map<Object, Object> map);

	public Map<Object, Object> insertConferee(Map<Object, Object> map);

	public Map<Object, Object> updateConferee(Map<Object, Object> map);

	public Map<Object, Object> deleteConferee(Map<Object, Object> map);


}
