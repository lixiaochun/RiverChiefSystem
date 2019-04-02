package conferenceManagement.mapper;

import java.util.List;
import java.util.Map;

import common.model.ConferenceFile;

public interface ConferenceFileMapper {

	public int insertConferenceFile(List<ConferenceFile> eventFilelist);

	public List<ConferenceFile> queryFile(Map<Object, Object> map);

	public void updateConferenceFile(Map<Object, Object> map);
}
