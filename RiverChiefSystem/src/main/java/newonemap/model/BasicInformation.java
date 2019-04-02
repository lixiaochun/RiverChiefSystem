package newonemap.model;

/**
 * 基础设施基本信息
 * @author wangwch
 *
 */
public class BasicInformation {
	
	//基础设施id
	String id;
	//基础设施名称
	String name;
	//经度
	String x;
	//纬度
	String y;
	//基础设施子类别
	String type;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


}
