package com.pigtrax.jobs.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupPerformanceAttribute {

	private String startDate;
	private String endDate;
	private Integer startDaysVariance;
	private Integer endDaysVariance;
	private Integer startHd;
	private Integer endHd;
	private Integer pigDeaths;
	private Integer pigsEuth;
	private Integer pigAdjust;
	private Integer transferIn;
	private Integer transferOut;
	private Integer transferNet;
	private Integer weanSales;
	private Integer feederSales;
	private Integer preMarketSales;
	private Integer marketSales;
	private Double startWtTotal;
	private Double startWt;
	private Double startWtHdVary;
	private Double endWtTotal;
	private Double endWt;
	private Double endWtHdVary;
	private Double transferInWtTotal;
	private Double transferInWtHd;
	private Double transferOutWtTotal;
	private Double transferOutWtHd;
	private Double totalGainWt;
	private Double totalGainWtPerTranfer;
	private Double gainHd;
	private Double gainHdPerTransfer;
	private Double mortalityPct;
	private Double preMarketPct;
	private Integer pigDays;
	private Integer dof;
	private Double adg;
	private Double totalFeedUsed;
	private Double totalFeedBudget;
	private Double budgetVariance;
	private Double adfi;
	private Double feedHd;
	private Double feedEfficiency;
	private Double gainEfficiency;
	private Double totalFeedCost;
	private Double tfcBudgeted;
	private Double tfcVariance;
	private Double tfcHd;
	private Double tfcBudgetedHd;
	private Double tfcVarianceHd;
	private Double tfcGain;
	private Double mof;
	private Double mofHd;

	public String getStartDate() {
		return startDate;
	}

	@XmlElement
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	@XmlElement
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getStartDaysVariance() {
		return startDaysVariance;
	}

	@XmlElement
	public void setStartDaysVariance(Integer startDaysVariance) {
		this.startDaysVariance = startDaysVariance;
	}

	public Integer getEndDaysVariance() {
		return endDaysVariance;
	}

	@XmlElement
	public void setEndDaysVariance(Integer endDaysVariance) {
		this.endDaysVariance = endDaysVariance;
	}

	public Integer getStartHd() {
		return startHd;
	}

	@XmlElement
	public void setStartHd(Integer startHd) {
		this.startHd = startHd;
	}

	public Integer getEndHd() {
		return endHd;
	}

	@XmlElement
	public void setEndHd(Integer endHd) {
		this.endHd = endHd;
	}

	public Integer getPigDeaths() {
		return pigDeaths;
	}

	@XmlElement
	public void setPigDeaths(Integer pigDeaths) {
		this.pigDeaths = pigDeaths;
	}

	public Integer getPigsEuth() {
		return pigsEuth;
	}

	@XmlElement
	public void setPigsEuth(Integer pigsEuth) {
		this.pigsEuth = pigsEuth;
	}

	public Integer getPigAdjust() {
		return pigAdjust;
	}

	@XmlElement
	public void setPigAdjust(Integer pigAdjust) {
		this.pigAdjust = pigAdjust;
	}

	public Integer getTransferIn() {
		return transferIn;
	}
	
	@XmlElement
	public void setTransferIn(Integer transferIn) {
		this.transferIn = transferIn;
	}

	public Integer getTransferOut() {
		return transferOut;
	}

	@XmlElement
	public void setTransferOut(Integer transferOut) {
		this.transferOut = transferOut;
	}

	public Integer getTransferNet() {
		return transferNet;
	}

	@XmlElement
	public void setTransferNet(Integer transferNet) {
		this.transferNet = transferNet;
	}

	public Integer getWeanSales() {
		return weanSales;
	}

	@XmlElement
	public void setWeanSales(Integer weanSales) {
		this.weanSales = weanSales;
	}

	public Integer getFeederSales() {
		return feederSales;
	}

	@XmlElement
	public void setFeederSales(Integer feederSales) {
		this.feederSales = feederSales;
	}

	public Integer getPreMarketSales() {
		return preMarketSales;
	}

	@XmlElement
	public void setPreMarketSales(Integer preMarketSales) {
		this.preMarketSales = preMarketSales;
	}

	public Integer getMarketSales() {
		return marketSales;
	}

	@XmlElement
	public void setMarketSales(Integer marketSales) {
		this.marketSales = marketSales;
	}

	public Double getStartWtTotal() {
		return startWtTotal;
	}

	@XmlElement
	public void setStartWtTotal(Double startWtTotal) {
		this.startWtTotal = startWtTotal;
	}

	public Double getStartWt() {
		return startWt;
	}

	@XmlElement
	public void setStartWt(Double startWt) {
		this.startWt = startWt;
	}

	public Double getStartWtHdVary() {
		return startWtHdVary;
	}

	@XmlElement
	public void setStartWtHdVary(Double startWtHdVary) {
		this.startWtHdVary = startWtHdVary;
	}

	public Double getEndWtTotal() {
		return endWtTotal;
	}

	@XmlElement
	public void setEndWtTotal(Double endWtTotal) {
		this.endWtTotal = endWtTotal;
	}

	public Double getEndWt() {
		return endWt;
	}

	@XmlElement
	public void setEndWt(Double endWt) {
		this.endWt = endWt;
	}

	public Double getEndWtHdVary() {
		return endWtHdVary;
	}

	@XmlElement
	public void setEndWtHdVary(Double endWtHdVary) {
		this.endWtHdVary = endWtHdVary;
	}

	public Double getTransferInWtTotal() {
		return transferInWtTotal;
	}

	@XmlElement
	public void setTransferInWtTotal(Double transferInWtTotal) {
		this.transferInWtTotal = transferInWtTotal;
	}

	public Double getTransferInWtHd() {
		return transferInWtHd;
	}

	@XmlElement
	public void setTransferInWtHd(Double transferInWtHd) {
		this.transferInWtHd = transferInWtHd;
	}

	public Double getTransferOutWtTotal() {
		return transferOutWtTotal;
	}

	@XmlElement
	public void setTransferOutWtTotal(Double transferOutWtTotal) {
		this.transferOutWtTotal = transferOutWtTotal;
	}

	public Double getTransferOutWtHd() {
		return transferOutWtHd;
	}
	
	@XmlElement
	public void setTransferOutWtHd(Double transferOutWtHd) {
		this.transferOutWtHd = transferOutWtHd;
	}

	public Double getTotalGainWt() {
		return totalGainWt;
	}

	@XmlElement
	public void setTotalGainWt(Double totalGainWt) {
		this.totalGainWt = totalGainWt;
	}

	public Double getTotalGainWtPerTranfer() {
		return totalGainWtPerTranfer;
	}

	@XmlElement
	public void setTotalGainWtPerTranfer(Double totalGainWtPerTranfer) {
		this.totalGainWtPerTranfer = totalGainWtPerTranfer;
	}

	public Double getGainHd() {
		return gainHd;
	}

	@XmlElement
	public void setGainHd(Double gainHd) {
		this.gainHd = gainHd;
	}

	public Double getGainHdPerTransfer() {
		return gainHdPerTransfer;
	}

	@XmlElement
	public void setGainHdPerTransfer(Double gainHdPerTransfer) {
		this.gainHdPerTransfer = gainHdPerTransfer;
	}

	public Double getMortalityPct() {
		return mortalityPct;
	}

	@XmlElement
	public void setMortalityPct(Double mortalityPct) {
		this.mortalityPct = mortalityPct;
	}

	public Double getPreMarketPct() {
		return preMarketPct;
	}

	@XmlElement
	public void setPreMarketPct(Double preMarketPct) {
		this.preMarketPct = preMarketPct;
	}

	public Integer getPigDays() {
		return pigDays;
	}

	@XmlElement
	public void setPigDays(Integer pigDays) {
		this.pigDays = pigDays;
	}

	public Integer getDof() {
		return dof;
	}

	@XmlElement
	public void setDof(Integer dof) {
		this.dof = dof;
	}

	public Double getAdg() {
		return adg;
	}
	

	@XmlElement
	public void setAdg(Double adg) {
		this.adg = adg;
	}

	public Double getTotalFeedUsed() {
		return totalFeedUsed;
	}

	@XmlElement
	public void setTotalFeedUsed(Double totalFeedUsed) {
		this.totalFeedUsed = totalFeedUsed;
	}

	public Double getTotalFeedBudget() {
		return totalFeedBudget;
	}

	@XmlElement
	public void setTotalFeedBudget(Double totalFeedBudget) {
		this.totalFeedBudget = totalFeedBudget;
	}

	public Double getBudgetVariance() {
		return budgetVariance;
	}

	@XmlElement
	public void setBudgetVariance(Double budgetVariance) {
		this.budgetVariance = budgetVariance;
	}

	public Double getAdfi() {
		return adfi;
	}

	@XmlElement
	public void setAdfi(Double adfi) {
		this.adfi = adfi;
	}

	public Double getFeedHd() {
		return feedHd;
	}

	@XmlElement
	public void setFeedHd(Double feedHd) {
		this.feedHd = feedHd;
	}

	public Double getFeedEfficiency() {
		return feedEfficiency;
	}

	@XmlElement
	public void setFeedEfficiency(Double feedEfficiency) {
		this.feedEfficiency = feedEfficiency;
	}

	public Double getGainEfficiency() {
		return gainEfficiency;
	}

	@XmlElement
	public void setGainEfficiency(Double gainEfficiency) {
		this.gainEfficiency = gainEfficiency;
	}

	public Double getTotalFeedCost() {
		return totalFeedCost;
	}

	@XmlElement
	public void setTotalFeedCost(Double totalFeedCost) {
		this.totalFeedCost = totalFeedCost;
	}

	public Double getTfcBudgeted() {
		return tfcBudgeted;
	}

	@XmlElement
	public void setTfcBudgeted(Double tfcBudgeted) {
		this.tfcBudgeted = tfcBudgeted;
	}

	public Double getTfcVariance() {
		return tfcVariance;
	}

	@XmlElement
	public void setTfcVariance(Double tfcVariance) {
		this.tfcVariance = tfcVariance;
	}

	public Double getTfcHd() {
		return tfcHd;
	}

	@XmlElement
	public void setTfcHd(Double tfcHd) {
		this.tfcHd = tfcHd;
	}

	public Double getTfcBudgetedHd() {
		return tfcBudgetedHd;
	}

	@XmlElement
	public void setTfcBudgetedHd(Double tfcBudgetedHd) {
		this.tfcBudgetedHd = tfcBudgetedHd;
	}

	public Double getTfcVarianceHd() {
		return tfcVarianceHd;
	}
	
	@XmlElement
	public void setTfcVarianceHd(Double tfcVarianceHd) {
		this.tfcVarianceHd = tfcVarianceHd;
	}

	public Double getTfcGain() {
		return tfcGain;
	}

	@XmlElement
	public void setTfcGain(Double tfcGain) {
		this.tfcGain = tfcGain;
	}

	public Double getMof() {
		return mof;
	}

	@XmlElement
	public void setMof(Double mof) {
		this.mof = mof;
	}

	public Double getMofHd() {
		return mofHd;
	}

	@XmlElement
	public void setMofHd(Double mofHd) {
		this.mofHd = mofHd;
	}

}
