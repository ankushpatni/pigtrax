package com.pigtrax.report.bean;

import java.util.Date;

public class TargetReportBean implements Comparable<TargetReportBean>{
	
	private String parameter;
	private String targetValue;
	private Date startDate;
	private String remark;
	private String rationId;
	
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRationId() {
		return rationId;
	}
	public void setRationId(String rationId) {
		this.rationId = rationId;
	}
	public int compareTo(TargetReportBean targetReportBean) {
		int compare = 0;
		compare = this.getParameter().compareTo(targetReportBean.getParameter());
		if (compare ==0){
			compare = this.getRationId().compareTo(targetReportBean.getRationId());
		}
		if (compare == 0){
			compare = this.getStartDate().compareTo(targetReportBean.getStartDate());
		}
		
		return compare;
	}	

}
