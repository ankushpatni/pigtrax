package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.InventoryStatusBean;
import com.pigtrax.report.dao.InventoryStatusDao;
import com.pigtrax.util.DateUtil;

@Repository
public class InventoryStatusReportService {

	@Autowired
	InventoryStatusDao inventoryStatusDao;

	private static final String seprater = ",";

	public List<String> getInventoryStatusList(Integer premiseId) {
		List<InventoryStatusBean> inventoryList = inventoryStatusDao.getInventoryList(premiseId);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";

		if (inventoryList != null && inventoryList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Sow Source, Barn, Phase Type, Group Id, Animal Type, Head, DOF(dd/MM/yyyy)");
			returnRows.add("\n");
			for (InventoryStatusBean inventoryStatusBean : inventoryList) {
				rowBuffer = new StringBuffer();
				
					rowBuffer.append(inventoryStatusBean.getSowSource() + seprater);
					rowBuffer.append(inventoryStatusBean.getBarnId() + seprater);
					rowBuffer.append(inventoryStatusBean.getPhaseType() + seprater);
					rowBuffer.append(inventoryStatusBean.getGroupId()+seprater);
					rowBuffer.append(inventoryStatusBean.getAnimalType()+seprater);
					rowBuffer.append(inventoryStatusBean.getHead()+seprater);
					try {
						dateStr = DateUtil.convertToFormatString(inventoryStatusBean.getDateOfFeed(), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr);
						else
							rowBuffer.append(" ");
					} catch (ParseException e) {
						rowBuffer.append(" ");
					}
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}

}
