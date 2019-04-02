package common.model;

import java.util.Date;

public class ExcelAssess {

	private int id;

	private String createBy;

	private Date createTime;

	private String updateBy;

	private Date updateTime;

	private String delFlag;

	private String excelId;

	private String assessContent;

	private String assessNorm;

	private String scoreExplain;

	private String description;
	
	private String name;
	
	private String tempFlag;
	
	private int min;//分页信息 从第几条开始
	private int pageSize;//页码大小
	
	
	

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExcelId() {
		return excelId;
	}

	public void setExcelId(String excelId) {
		this.excelId = excelId;
	}

	public String getAssessContent() {
		return assessContent;
	}

	public void setAssessContent(String assessContent) {
		this.assessContent = assessContent;
	}

	public String getAssessNorm() {
		return assessNorm;
	}

	public void setAssessNorm(String assessNorm) {
		this.assessNorm = assessNorm;
	}

	public String getScoreExplain() {
		return scoreExplain;
	}

	public void setScoreExplain(String scoreExplain) {
		this.scoreExplain = scoreExplain;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}
