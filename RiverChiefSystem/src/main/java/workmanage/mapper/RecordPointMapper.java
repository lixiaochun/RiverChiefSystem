package workmanage.mapper;

import java.util.List;
import common.model.RecordPoint;

public interface RecordPointMapper {
	
	public List<RecordPoint> queryRecordPointByPatrolRecordId(int patrolRecordId);

}
