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
	private Double transferNetPct;
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
	private Double mofPct;
	private Double netTransferWeight;
	private Integer netTransferHead;
	private Double netTransferWeightPerHead;
	private Double budgetVariancePct;
	
	
	
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
		if(startDaysVariance == null) startDaysVariance = 0;
		return startDaysVariance;
	}

	@XmlElement
	public void setStartDaysVariance(Integer startDaysVariance) {
		this.startDaysVariance = startDaysVariance;
	}

	public Integer getEndDaysVariance() {
		if(endDaysVariance == null) endDaysVariance = 0;
		return endDaysVariance;
	}

	@XmlElement
	public void setEndDaysVariance(Integer endDaysVariance) {
		this.endDaysVariance = endDaysVariance;
	}

	public Integer getStartHd() {
		if(startHd == null) startHd = 0;
		return startHd;
	}

	@XmlElement
	public void setStartHd(Integer startHd) {
		this.startHd = startHd;
	}

	public Integer getEndHd() {
		if(endHd == null) endHd = 0;
		return endHd;
	}

	@XmlElement
	public void setEndHd(Integer endHd) {
		this.endHd = endHd;
	}

	public Integer getPigDeaths() {
		if(pigDeaths == null) pigDeaths = 0;
		return pigDeaths;
	}

	@XmlElement
	public void setPigDeaths(Integer pigDeaths) {
		this.pigDeaths = pigDeaths;
	}

	public Integer getPigsEuth() {
		if(pigsEuth == null) pigsEuth = 0;
		return pigsEuth;
	}

	@XmlElement
	public void setPigsEuth(Integer pigsEuth) {
		this.pigsEuth = pigsEuth;
	}

	public Integer getPigAdjust() {
		if(pigAdjust == null) pigAdjust = 0;
		return pigAdjust;
	}

	@XmlElement
	public void setPigAdjust(Integer pigAdjust) {
		this.pigAdjust = pigAdjust;
	}

	public Integer getTransferIn() {
		if(transferIn == null) transferIn = 0;
		return transferIn;
	}
	
	@XmlElement
	public void setTransferIn(Integer transferIn) {
		this.transferIn = transferIn;
	}

	public Integer getTransferOut() {
		if(transferOut == null) transferOut = 0;
		return transferOut;
	}

	@XmlElement
	public void setTransferOut(Integer transferOut) {
		this.transferOut = transferOut;
	}

	public Integer getTransferNet() {
		if(transferNet == null) transferNet = 0;
		return transferNet;
	}

	@XmlElement
	public void setTransferNet(Integer transferNet) {
		this.transferNet = transferNet;
	}

	public Integer getWeanSales() {
		if(weanSales == null) weanSales = 0;
		return weanSales;
	}

	@XmlElement
	public void setWeanSales(Integer weanSales) {
		this.weanSales = weanSales;
	}

	public Integer getFeederSales() {
		if(feederSales == null) feederSales = 0;
		return feederSales;
	}

	@XmlElement
	public void setFeederSales(Integer feederSales) {
		this.feederSales = feederSales;
	}

	public Integer getPreMarketSales() {
		if(preMarketSales == null) preMarketSales = 0;
		return preMarketSales;
	}

	@XmlElement
	public void setPreMarketSales(Integer preMarketSales) {
		this.preMarketSales = preMarketSales;
	}

	public Integer getMarketSales() {
		if(marketSales == null) marketSales = 0;
		return marketSales;
	}

	@XmlElement
	public void setMarketSales(Integer marketSales) {
		this.marketSales = marketSales;
	}

	public Double getStartWtTotal() {
		if(startWtTotal == null) startWtTotal = 0D;
		return startWtTotal;
	}

	@XmlElement
	public void setStartWtTotal(Double startWtTotal) {
		this.startWtTotal = startWtTotal;
	}

	public Double getStartWt() {
		if(startWt == null) startWt = 0D;
		return startWt;
	}

	@XmlElement
	public void setStartWt(Double startWt) {
		this.startWt = startWt;
	}

	public Double getStartWtHdVary() {
		if(startWtHdVary == null) startWtHdVary = 0D;
		return startWtHdVary;
	}

	@XmlElement
	public void setStartWtHdVary(Double startWtHdVary) {
		this.startWtHdVary = startWtHdVary;
	}

	public Double getEndWtTotal() {
		if(endWtTotal == null) endWtTotal = 0D;
		return endWtTotal;
	}

	@XmlElement
	public void setEndWtTotal(Double endWtTotal) {
		this.endWtTotal = endWtTotal;
	}

	public Double getEndWt() {
		if(endWt == null) endWt = 0D;
		return endWt;
	}

	@XmlElement
	public void setEndWt(Double endWt) {
		this.endWt = endWt;
	}

	public Double getEndWtHdVary() {
		if(endWtHdVary == null) endWtHdVary = 0D;
		return endWtHdVary;
	}

	@XmlElement
	public void setEndWtHdVary(Double endWtHdVary) {
		this.endWtHdVary = endWtHdVary;
	}

	public Double getTransferInWtTotal() {
		if(transferInWtTotal == null) transferInWtTotal = 0D;
		return transferInWtTotal;
	}

	@XmlElement
	public void setTransferInWtTotal(Double transferInWtTotal) {
		this.transferInWtTotal = transferInWtTotal;
	}

	public Double getTransferInWtHd() {
		if(transferInWtHd == null) transferInWtHd = 0D;
		return transferInWtHd;
	}

	@XmlElement
	public void setTransferInWtHd(Double transferInWtHd) {
		this.transferInWtHd = transferInWtHd;
	}

	public Double getTransferOutWtTotal() {
		if(transferOutWtTotal == null) transferOutWtTotal = 0D;
		return transferOutWtTotal;
	}

	@XmlElement
	public void setTransferOutWtTotal(Double transferOutWtTotal) {
		this.transferOutWtTotal = transferOutWtTotal;
	}

	public Double getTransferOutWtHd() {
		if(transferOutWtHd == null) transferOutWtHd = 0D;
		return transferOutWtHd;
	}
	
	@XmlElement
	public void setTransferOutWtHd(Double transferOutWtHd) {
		this.transferOutWtHd = transferOutWtHd;
	}

	public Double getTotalGainWt() {
		if(totalGainWt == null) totalGainWt = 0D;
		return totalGainWt;
	}

	@XmlElement
	public void setTotalGainWt(Double totalGainWt) {
		this.totalGainWt = totalGainWt;
	}

	public Double getTotalGainWtPerTranfer() {
		if(totalGainWtPerTranfer == null) totalGainWtPerTranfer = 0D;
		return totalGainWtPerTranfer;
	}

	@XmlElement
	public void setTotalGainWtPerTranfer(Double totalGainWtPerTranfer) {
		this.totalGainWtPerTranfer = totalGainWtPerTranfer;
	}

	public Double getGainHd() {
		if(gainHd == null) gainHd = 0D;
		return gainHd;
	}

	@XmlElement
	public void setGainHd(Double gainHd) {
		this.gainHd = gainHd;
	}

	public Double getGainHdPerTransfer() {
		if(gainHdPerTransfer == null) gainHdPerTransfer = 0D;
		return gainHdPerTransfer;
	}

	@XmlElement
	public void setGainHdPerTransfer(Double gainHdPerTransfer) {
		this.gainHdPerTransfer = gainHdPerTransfer;
	}

	public Double getMortalityPct() {
		if(mortalityPct == null) mortalityPct = 0D;
		return mortalityPct;
	}

	@XmlElement
	public void setMortalityPct(Double mortalityPct) {
		this.mortalityPct = mortalityPct;
	}

	public Double getPreMarketPct() {
		if(preMarketPct == null) preMarketPct = 0D;
		return preMarketPct;
	}

	@XmlElement
	public void setPreMarketPct(Double preMarketPct) {
		this.preMarketPct = preMarketPct;
	}

	public Integer getPigDays() {
		if(pigDays == null) pigDays = 0;
		return pigDays;
	}

	@XmlElement
	public void setPigDays(Integer pigDays) {
		this.pigDays = pigDays;
	}

	public Integer getDof() {
		if(dof == null) dof = 0;
		return dof;
	}

	@XmlElement
	public void setDof(Integer dof) {
		this.dof = dof;
	}

	public Double getAdg() {
		if(adg == null) adg = 0D;
		return adg;
	}
	

	@XmlElement
	public void setAdg(Double adg) {
		this.adg = adg;
	}

	public Double getTotalFeedUsed() {
		if(totalFeedUsed == null) totalFeedUsed = 0D;
		return totalFeedUsed;
	}

	@XmlElement
	public void setTotalFeedUsed(Double totalFeedUsed) {
		this.totalFeedUsed = totalFeedUsed;
	}

	public Double getTotalFeedBudget() {
		if(totalFeedBudget == null) totalFeedBudget = 0D;
		return totalFeedBudget;
	}

	@XmlElement
	public void setTotalFeedBudget(Double totalFeedBudget) {
		this.totalFeedBudget = totalFeedBudget;
	}

	public Double getBudgetVariance() {
		if(budgetVariance == null) budgetVariance = 0D;
		return budgetVariance;
	}

	@XmlElement
	public void setBudgetVariance(Double budgetVariance) {
		this.budgetVariance = budgetVariance;
	}

	public Double getAdfi() {
		if(adfi == null) adfi = 0D;
		return adfi;
	}

	@XmlElement
	public void setAdfi(Double adfi) {
		this.adfi = adfi;
	}

	public Double getFeedHd() {
		if(feedHd == null) feedHd = 0D;
		return feedHd;
	}

	@XmlElement
	public void setFeedHd(Double feedHd) {
		this.feedHd = feedHd;
	}

	public Double getFeedEfficiency() {
		if(feedEfficiency == null) feedEfficiency = 0D;
		return feedEfficiency;
	}

	@XmlElement
	public void setFeedEfficiency(Double feedEfficiency) {
		this.feedEfficiency = feedEfficiency;
	}

	public Double getGainEfficiency() {
		if(gainEfficiency == null) gainEfficiency = 0D;
		return gainEfficiency;
	}

	@XmlElement
	public void setGainEfficiency(Double gainEfficiency) {
		this.gainEfficiency = gainEfficiency;
	}

	public Double getTotalFeedCost() {
		if(totalFeedCost == null) totalFeedCost = 0D;
		return totalFeedCost;
	}

	@XmlElement
	public void setTotalFeedCost(Double totalFeedCost) {
		this.totalFeedCost = totalFeedCost;
	}

	public Double getTfcBudgeted() {
		if(tfcBudgeted == null) tfcBudgeted = 0D;
		return tfcBudgeted;
	}

	@XmlElement
	public void setTfcBudgeted(Double tfcBudgeted) {
		this.tfcBudgeted = tfcBudgeted;
	}

	public Double getTfcVariance() {
		if(tfcVariance == null) tfcVariance = 0D;
		return tfcVariance;
	}

	@XmlElement
	public void setTfcVariance(Double tfcVariance) {
		this.tfcVariance = tfcVariance;
	}

	public Double getTfcHd() {
		if(tfcHd == null) tfcHd = 0D;
		return tfcHd;
	}

	@XmlElement
	public void setTfcHd(Double tfcHd) {
		this.tfcHd = tfcHd;
	}

	public Double getTfcBudgetedHd() {
		if(tfcBudgetedHd == null) tfcBudgetedHd = 0D;
		return tfcBudgetedHd;
	}

	@XmlElement
	public void setTfcBudgetedHd(Double tfcBudgetedHd) {
		this.tfcBudgetedHd = tfcBudgetedHd;
	}

	public Double getTfcVarianceHd() {
		if(tfcVarianceHd == null) tfcVarianceHd = 0D;
		return tfcVarianceHd;
	}
	
	@XmlElement
	public void setTfcVarianceHd(Double tfcVarianceHd) {
		this.tfcVarianceHd = tfcVarianceHd;
	}

	public Double getTfcGain() {
		if(tfcGain == null) tfcGain = 0D;
		return tfcGain;
	}

	@XmlElement
	public void setTfcGain(Double tfcGain) {
		this.tfcGain = tfcGain;
	}

	public Double getMof() {
		if(mof == null) mof = 0D;
		return mof;
	}

	@XmlElement
	public void setMof(Double mof) {
		this.mof = mof;
	}

	public Double getMofHd() {
		if(mofHd == null) mofHd = 0D;
		return mofHd;
	}

	@XmlElement
	public void setMofHd(Double mofHd) {
		this.mofHd = mofHd;
	}

	public Double getNetTransferWeight() {
		if(netTransferWeight == null) netTransferWeight = 0D;
		return netTransferWeight;
	}

	@XmlElement
	public void setNetTransferWeight(Double netTransferWeight) {
		this.netTransferWeight = netTransferWeight;
	}

	public Integer getNetTransferHead() {
		if(netTransferHead == null) netTransferHead = 0;
		return netTransferHead;
	}

	@XmlElement
	public void setNetTransferHead(Integer netTransferHead) {
		this.netTransferHead = netTransferHead;
	}

	public Double getNetTransferWeightPerHead() {
		if(netTransferWeightPerHead == null) netTransferWeightPerHead = 0D;
		return netTransferWeightPerHead;
	}

	@XmlElement
	public void setNetTransferWeightPerHead(Double netTransferWeightPerHead) {
		this.netTransferWeightPerHead = netTransferWeightPerHead;
	}

	public Double getBudgetVariancePct() {
		if(budgetVariancePct == null) budgetVariancePct = 0D;
		return budgetVariancePct;
	}

	@XmlElement
	public void setBudgetVariancePct(Double budgetVariancePct) {
		this.budgetVariancePct = budgetVariancePct;
	}

	public Double getMofPct() {
		if(mofPct == null) mofPct = 0D;
		return mofPct;
	}

	@XmlElement
	public void setMofPct(Double mofPct) {
		this.mofPct = mofPct;
	}

	public Double getTransferNetPct() {
		return transferNetPct;
	}

	@XmlElement
	public void setTransferNetPct(Double transferNetPct) {
		this.transferNetPct = transferNetPct;
	}
}
