package basemanage.mapper;

import java.util.List;
import java.util.Map;

import basemanage.model.OperationManualFile;

public interface OperationManualMapper {
	
	public List<OperationManualFile> selectFileList(Map<Object, Object> parameter);


}
