package quartzJob.mapper;

import java.util.List;

import quartzJob.JobSchedule;



public interface JobScheduleMapper {
     public List<JobSchedule> findAllList();
}
