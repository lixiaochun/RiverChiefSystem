package quartzJob.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quartzJob.JobSchedule;
import quartzJob.mapper.JobScheduleMapper;
import quartzJob.service.JobScheduleService;


@Service("JobScheduleService")
public class JobScheduleServiceImpl implements JobScheduleService{
    
	@Autowired
	private JobScheduleMapper jobScheduleMapper;
	
	public List<JobSchedule> findAllList() {
		List<JobSchedule> list = jobScheduleMapper.findAllList();
		return list;
	}

}
