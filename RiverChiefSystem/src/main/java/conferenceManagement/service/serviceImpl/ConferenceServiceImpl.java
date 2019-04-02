package conferenceManagement.service.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.model.Conference;
import conferenceManagement.mapper.ConferenceMapper;
import conferenceManagement.service.ConferenceService;

@Service("ConferenceService")
public class ConferenceServiceImpl implements ConferenceService {

	@Autowired
	private ConferenceMapper conferenceMapper;

	public Map<Object, Object> queryConference(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		List<Conference> list = conferenceMapper.queryConference(map);
		int count = conferenceMapper.countConference(map);
		if (list != null && list.size() > 0) {
			result.put("result", 1);
			result.put("Conference", list);
			result.put("countConference", count);
		} else {
			result.put("result", 0);
		}

		return result;
	}

	public Map<Object, Object> insertConference(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Conference conference = new Conference();
			// set数据
			conference.setAdvertisingMap(map.get("advertisingMap").toString());
			conference.setAgenda(map.get("agenda").toString());
			conference.setAnnouncements(map.get("announcements").toString());
			conference.setArrangement(map.get("arrangement").toString());
			conference.setConferenceName(map.get("conferenceName").toString());
			conference.setContact(map.get("contact").toString());
			conference.setManual(map.get("manual").toString());
			conference.setNotice(map.get("notice").toString());
			conference.setRemark(map.get("remark").toString());
			conference.setSite(map.get("site").toString());
			conference.setStepStatus(map.get("stepStatus").toString());
			conference.setFollowThrough(map.get("followThrough").toString());
			conference.setTheme(map.get("theme").toString());
			Date time = (Date) map.get("time");
			conference.setTime(new Timestamp(time.getTime()));
			conference.setStatus(Integer.parseInt(map.get("status").toString()));
			conferenceMapper.insertConference(conference);
			if (conference.getConferenceId() != 0) {
				result.put("result", 1);
				result.put("conferenceId", conference.getConferenceId());
			}
		} catch (Exception e) {
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> updateConference(Map<Object, Object> map) {
		Map<Object, Object> query = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			// 从map里获取id
			int conferenceId = Integer.parseInt(map.get("conferenceId").toString());
			query.put("conferenceId", conferenceId);
			List<Conference> list = conferenceMapper.queryConference(query);
			if (list != null && list.size() > 0) {
				conferenceMapper.updateConference(map);
				result.put("result", 1);

			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> deleteConference(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> query = new HashMap<Object, Object>();
		try {
			// 从map里获取id
			int conferenceId = Integer.parseInt(map.get("conferenceId").toString());
			query.put("conferenceId", conferenceId);
			List<Conference> list = conferenceMapper.queryConference(query);
			if (list != null && list.size() > 0) {
				conferenceMapper.deleteConference(map);
				result.put("result", 1);

			}
		} catch (Exception e) {
			result.put("result", 0);
		}
		return result;
	}

}
