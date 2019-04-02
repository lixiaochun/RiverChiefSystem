package workmanage.mapper;

import java.util.List;
import java.util.Map;

import common.model.EventFile;

public interface EventFileMapper {

	public int addEventFile(List<EventFile> list);

	public void deleteEventByImg(Map<Object, Object> map);
}
