package com.pigtrax.report.bean;

public class RationReportFeedCostBean {
	private Double wtdAvg;
	public Double getWtdAvg() {
		return wtdAvg;
	}
	public String getRationId() {
		return rationId;
	}
	public void setWtdAvg(Double wtdAvg) {
		this.wtdAvg = wtdAvg;
	}
	public void setRationId(String rationId) {
		this.rationId = rationId;
	}
	private String rationId;

}
