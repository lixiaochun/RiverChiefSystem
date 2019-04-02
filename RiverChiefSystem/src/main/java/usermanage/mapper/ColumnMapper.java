package usermanage.mapper;

import java.util.List;
import java.util.Map;

import common.model.Column;

public interface ColumnMapper {

	public List<Column> queryColumn(Map<Object, Object> map);
}
