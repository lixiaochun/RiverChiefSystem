package workmanage.mapper;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;

public interface WorkflowMapper {

	public List<ProcessInstance> selectProcessInstances(Map<Object, Object> map);

	public List<HistoricProcessInstance> selectHistoricProcessInstances(Map<Object, Object> map);

	public int selectTaskAssignee(Map<Object, Object> map);
}
