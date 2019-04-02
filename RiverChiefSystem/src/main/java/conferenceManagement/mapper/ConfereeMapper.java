package conferenceManagement.mapper;

import java.util.List;
import java.util.Map;

import common.model.Conferee;

public interface ConfereeMapper {

	public List<Conferee> queryConferee(Map<Object, Object> map);

	public int countConferee(Map<Object, Object> map);

	public int insertConferee(Conferee conferee);

	public void updateConferee(Map<Object, Object> map);

	public void deleteConferee(Map<Object, Object> map);

}
