package quartzJob.service.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quartzJob.mapper.SensorDailyMapper;
import quartzJob.service.SensorDailyService;


@Service("SensorDailyService")
public class SensorDailyServiceImpl implements SensorDailyService{
    
	@Autowired
	private SensorDailyMapper sensorDailyMapper;

	@Override
	public void insertDailyData() {
		sensorDailyMapper.insertDailyData();
	}

	@Override
	public void deleteOutdatedData() {
		sensorDailyMapper.deleteOutdatedData();
	}
	

}
