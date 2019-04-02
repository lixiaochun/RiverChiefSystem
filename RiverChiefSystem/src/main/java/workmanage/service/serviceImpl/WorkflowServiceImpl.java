package workmanage.service.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import workmanage.mapper.WorkflowMapper;
import workmanage.service.WorkflowService;

@Service("WorkflowService")
public class WorkflowServiceImpl implements WorkflowService {

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private WorkflowMapper workflowMapper;

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public Map<Object, Object> saveNewDeploye(File file, String filename) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			InputStream in = new FileInputStream(file);
			ZipInputStream zipInputStream = new ZipInputStream(in);
			Deployment deployment = repositoryService.createDeployment().name(filename).addZipInputStream(zipInputStream).deploy();
			result.put("deploymentId", deployment.getId());
			result.put("deployment", deployment);
			result.put("message", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "操作异常");
		}
		return result;
	}

	public Map<Object, Object> saveStartProcess(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			String processDefinitionKey = (String) map.get("processDefinitionKey");
			String businessKey = map.get("businessKey").toString();
			Map<String, Object> variables = new HashMap<String, Object>();
			// 设置参数
			variables.put("regionId", map.get("regionId").toString());
			String userId = map.get("userId").toString();
			variables.put("userId", Integer.parseInt(userId));
			// 启动流程实例
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);

			// 查找启动人
			List<Task> list = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskAssignee(userId).list();
			if (list != null && list.size() > 0) {
				Task task = list.get(0);
				if(map.get("solve").equals("0")) {
					if (map.get("nextUserId") != null && map.get("nextUserId").toString() != "") {
						variables.put("userId", Integer.parseInt(map.get("nextUserId").toString()));
					}
					variables.put("solve", 0);
					// 完成事件启动
					taskService.complete(task.getId(), variables);
					String userId2 = map.get("nextUserId").toString();
					List<Task> list1 = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskAssignee(userId2).list();
					map.put("taskId", list1.get(0).getId());
				}else {
					result.put("taskId", task.getId());
				}
				result.put("result", 1);
				result.put("processInstanceId", processInstance.getId());
			} else {
				result.put("result", 0);
				result.put("message", "查无任务");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
		}
		return result;
	}

	public Map<Object, Object> completeTask(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<String, Object> variables = new HashMap<String, Object>();
		try {
			String taskId = (String) map.get("taskId");
			List<Task> list = taskService.createTaskQuery().taskId(taskId).list();
			if (list != null && list.size() > 0) {
				Task task = list.get(0);
				variables.put("solve", Integer.parseInt(map.get("solve").toString()));
				if (map.get("nextUserId") != null && map.get("nextUserId").toString() != "") {
					variables.put("userId", Integer.parseInt(map.get("nextUserId").toString()));
				}
				taskService.complete(task.getId(), variables);
				result.put("result", 1);
				result.put("taskId", task.getId());
			} else {
				result.put("result", 0);
				result.put("message", "该事件不属于该人");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "工作流异常");
		}
		return result;
	}

	public Map<Object, Object> claimTask(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			String processInstanceId = (String) map.get("processInstanceId");
			String userId = Integer.toString((Integer) map.get("userId"));
			List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).taskCandidateUser(userId).list();
			if (list != null && list.size() > 0) {
				Task task = list.get(0);
				taskService.claim(task.getId(), userId);
				result.put("result", 1);
			} else {
				result.put("result", 0);
				result.put("message", "该事件不属于该人");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "事件异常");
		}
		return result;
	}

	public Map<Object, Object> findTask(Map<Object, Object> map) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		String businessKey = map.get("eventId").toString();
		List<Task> list = new ArrayList<Task>();
		if (map.get("userId") != null) {
			String userId = map.get("userId").toString();
			list = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskAssignee(userId).list();
		} else {
			list = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
		}
		if (list != null && list.size() > 0) {
			result.put("result", 1);
			result.put("taskId", list.get(0).getId());
		} else {
			result.put("result", 0);
			result.put("message", "查无可执行的事件");
		}
		return result;
	}

	public Map<Object, Object> queryImage(Map<Object, Object> map, HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			String businessKey = map.get("eventId").toString();
			// 获取历史流程实例
			HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey)
					// .processInstanceId(processInstanceId)
					.singleResult();
			// 获取流程图
			BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
			ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
			Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

			ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
			ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

			List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).list();
			// 高亮环节id集合
			List<String> highLightedActivitis = new ArrayList<String>();

			// 高亮线路id集合
			List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitList);

			for (HistoricActivityInstance tempActivity : highLightedActivitList) {
				String activityId = tempActivity.getActivityId();
				highLightedActivitis.add(activityId);
			}

			// 中文显示的是口口口，设置字体就好了
			InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "宋体", "宋体", null, null, 1.0);
			// 单独返回流程图，不高亮显示
			// InputStream imageStream = diagramGenerator.generatePngDiagram(bpmnModel);
			// 输出资源内容到相应对象
			// BufferedImage image = ImageIO.read(imageStream);
			// ByteArrayOutputStream bs = new ByteArrayOutputStream();
			// // 写入字符流
			// ImageIO.write(image, "jpg", bs);

			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
			int rc = 0;
			while ((rc = imageStream.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] in_b = swapStream.toByteArray();

			Base64.Encoder base64 = Base64.getEncoder();
			// 转码成字符串
			String imgsrc = "data:image/jpeg;base64," + base64.encodeToString(in_b);

			result.put("result", 1);
			result.put("message", "查询成功");
			result.put("imgsrc", imgsrc);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", 0);
			result.put("message", "查询异常");
		}
		return result;
	}

	/**
	 * 获取需要高亮的线
	 * 
	 * @param processDefinitionEntity
	 * @param historicActivityInstances
	 * @return
	 */
	private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances) {

		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
		for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
			ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());// 得到节点定义的详细信息
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
			ActivityImpl sameActivityImpl1 = processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId());
			// 将后面第一个节点放在时间相同节点的集合里
			sameStartTimeNodes.add(sameActivityImpl1);
			for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
				HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
				HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点
				if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
					// 如果第一个节点和第二个节点开始时间相同保存
					ActivityImpl sameActivityImpl2 = processDefinitionEntity.findActivity(activityImpl2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				} else {
					// 有不相同跳出循环
					break;
				}
			}
			List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
			for (PvmTransition pvmTransition : pvmTransitions) {
				// 对所有的线进行遍历
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
				// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
				if (sameStartTimeNodes.contains(pvmActivityImpl)) {
					highFlows.add(pvmTransition.getId());
				}
			}
		}
		return highFlows;
	}

	public static byte[] steamToByte(InputStream input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = input.read(b, 0, b.length)) != -1) {
			baos.write(b, 0, len);
		}
		byte[] buffer = baos.toByteArray();
		return buffer;
	}

	public List<String> findTodoTask(Map<Object, Object> map) {
		// Map<Object, Object> result = new HashMap<Object, Object>();
		List<String> result = new ArrayList<String>();
		List<Task> list = new ArrayList<Task>();
		if (map.get("userId") != null) {
			String userId = map.get("userId").toString();
			list = taskService.createTaskQuery().taskAssignee(userId).list();
		} else {
			list = taskService.createTaskQuery().list();
		}
		for (Task task : list) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
			String businessKey = processInstance.getBusinessKey();
			if (businessKey != null && !(businessKey.equals(""))) {
				result.add(businessKey);
			}
		}
		return result;
	}

	public List<String> findOnTask(Map<Object, Object> map) {
		List<String> result = new ArrayList<String>();
		List<HistoricTaskInstance> list = new ArrayList<HistoricTaskInstance>();
		if (map.get("userId") != null) {
			String userId = map.get("userId").toString();
			list = processEngine.getHistoryService() // 历史任务Service
					.createHistoricTaskInstanceQuery() // 创建历史任务实例查询
					.taskAssignee(userId) // 指定办理人
					.finished() // 查询已经完成的任务
					.list();
		} else {
			list = processEngine.getHistoryService() // 历史任务Service
					.createHistoricTaskInstanceQuery() // 创建历史任务实例查询
					.finished() // 查询已经完成的任务
					.list();
		}
		for (HistoricTaskInstance task : list) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
			if (processInstance != null) {
				String businessKey = processInstance.getBusinessKey();
				if (businessKey != null && !(businessKey.equals(""))) {
					result.add(businessKey);
				}
			}
		}
		return result;
	}

	public List<String> findFinishTask(Map<Object, Object> map) {
		List<String> result = new ArrayList<String>();
		List<HistoricTaskInstance> list = new ArrayList<HistoricTaskInstance>();
		if (map.get("userId") != null) {
			String userId = map.get("userId").toString();
			list = processEngine.getHistoryService() // 历史任务Service
					.createHistoricTaskInstanceQuery() // 创建历史任务实例查询
					.taskAssignee(userId) // 指定办理人
					.finished() // 查询已经完成的任务
					.list();
		} else {
			list = processEngine.getHistoryService() // 历史任务Service
					.createHistoricTaskInstanceQuery() // 创建历史任务实例查询
					.finished() // 查询已经完成的任务
					.list();
		}
		for (HistoricTaskInstance task : list) {
			String processInstanceId = task.getProcessInstanceId();

			ProcessInstance rpi = processEngine.getRuntimeService()//
					.createProcessInstanceQuery()// 创建流程实例查询对象
					.processInstanceId(processInstanceId).singleResult();
			if (rpi == null) {
				HistoricProcessInstance processInstance = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
				String businessKey = processInstance.getBusinessKey();
				if (businessKey != null && !(businessKey.equals(""))) {
					result.add(businessKey);
				}
			}
		}
		return result;
	}

	@Override
	public List<String> queryTask(Map<Object, Object> map) {
		List<String> result = new ArrayList<String>();
		if (map.get("type") != null && map.get("type").toString().equals("1")) {
			List<HistoricProcessInstance> list = workflowMapper.selectHistoricProcessInstances(map);
			for (HistoricProcessInstance processInstance : list) {
				String businessKey = processInstance.getBusinessKey();
				if (businessKey != null && !(businessKey.equals(""))) {
					result.add(businessKey);
				}
			}
		} else {
			List<ProcessInstance> list = workflowMapper.selectProcessInstances(map);
			for (ProcessInstance processInstance : list) {
				String businessKey = processInstance.getBusinessKey();
				if (businessKey != null && !(businessKey.equals(""))) {
					result.add(businessKey);
				}
			}
		}

		return result;
	}

	public void generateImage(String processInstanceId) {
		// 1.创建核心引擎流程对象processEngine
		// ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		// 流程定义
		BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(task.getProcessDefinitionId());

		// 正在活动节点
		List<String> activeActivityIds = processEngine.getRuntimeService().getActiveActivityIds(task.getExecutionId());

		ProcessDiagramGenerator pdg = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
		// 生成流图片
		InputStream inputStream = pdg.generateDiagram(bpmnModel, "png", activeActivityIds, activeActivityIds, "宋体", "宋体", processEngine.getProcessEngineConfiguration().getActivityFontName(),
				processEngine.getProcessEngineConfiguration().getProcessEngineConfiguration().getClassLoader(), 1.0);
		try {
			// 生成本地图片

			FileOutputStream out = new FileOutputStream("D:/test.png");
			byte buffer[] = new byte[1024];
			int len = 0;

			while ((len = inputStream.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			inputStream.close();
			out.close();
		} catch (Exception e) {
			throw new RuntimeException("生成流程图异常！", e);
		}
	}
}
