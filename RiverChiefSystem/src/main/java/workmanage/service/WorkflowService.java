package workmanage.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WorkflowService {

	public Map<Object, Object> saveNewDeploye(File file, String filename);

	public Map<Object, Object> saveStartProcess(Map<Object, Object> map);

	public Map<Object, Object> completeTask(Map<Object, Object> map);

	public Map<Object, Object> claimTask(Map<Object, Object> map);

	public Map<Object, Object> findTask(Map<Object, Object> map);

	public Map<Object, Object> queryImage(Map<Object, Object> map, HttpServletRequest request, HttpServletResponse response);

	public List<String> findTodoTask(Map<Object, Object> map);

	public List<String> findOnTask(Map<Object, Object> map);

	public List<String> findFinishTask(Map<Object, Object> map);

	public List<String> queryTask(Map<Object, Object> map);

	public void generateImage(String processInstanceId);
}
