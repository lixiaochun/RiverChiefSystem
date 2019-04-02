package conferenceManagement.service;

import java.util.Map;

public interface ConferenceService {

	public Map<Object, Object> queryConference(Map<Object, Object> map);

	public Map<Object, Object> insertConference(Map<Object, Object> map);

	public Map<Object, Object> updateConference(Map<Object, Object> map);

	public Map<Object, Object> deleteConference(Map<Object, Object> map);
}
