package conferenceManagement.mapper;

import java.util.List;
import java.util.Map;

import common.model.Conference;

public interface ConferenceMapper {

	public List<Conference> queryConference(Map<Object, Object> map);

	public int countConference(Map<Object, Object> map);

	public int insertConference(Conference conference);

	public void updateConference(Map<Object, Object> map);

	public void deleteConference(Map<Object, Object> map);

}
