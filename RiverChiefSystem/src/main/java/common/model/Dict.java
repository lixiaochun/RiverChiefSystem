package common.model;

import java.util.List;

public class Dict {

	private String table;
	
	private String field;
	
	private String code;
	
	private String name;
	
	private List<Dict> dictList;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Dict> getDictList() {
		return dictList;
	}

	public void setDictList(List<Dict> dictList) {
		this.dictList = dictList;
	}
}
