package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.InventoryStatusBean;
import com.pigtrax.report.dao.InventoryStatusDao;
import com.pigtrax.util.DateUtil;

@Repository
public class InventoryStatusReportService {

	@Autowired
	InventoryStatusDao inventoryStatusDao;

	@Autowired
	MessageSource messageSource;
	
	private static final String seprater = ",";

	public List<String> getInventoryStatusList(Integer premiseId, Locale locale) {
		List<InventoryStatusBean> inventoryList = inventoryStatusDao.getInventoryList(premiseId);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";

		if (inventoryList != null && inventoryList.size() > 0) {

			StringBuffer rowBuffer = null;
			//returnRows.add(messageSource.getMessage("label.premise.sowSource", null, "", locale)+","+messageSource.getMessage("label.premise.barn", null, "", locale)+","
			returnRows.add(messageSource.getMessage("label.premise.barn", null, "", locale)+","
					+messageSource.getMessage("label.barn.phaseType", null, "", locale)+","+messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","
					+messageSource.getMessage("label.groupReport.search.animal", null, "", locale)+","
					+messageSource.getMessage("label.reports.inventorystatus.head", null, "", locale)+","+messageSource.getMessage("label.reports.inventorystatus.dof", null, "", locale)
					+"\n");
			
			for (InventoryStatusBean inventoryStatusBean : inventoryList) {
				rowBuffer = new StringBuffer();
				
					//rowBuffer.append(inventoryStatusBean.getSowSource() + seprater);
					rowBuffer.append(inventoryStatusBean.getBarnId() + seprater);
					rowBuffer.append(inventoryStatusBean.getPhaseType() + seprater);
					rowBuffer.append(inventoryStatusBean.getGroupId()+seprater);
					rowBuffer.append(inventoryStatusBean.getAnimalType()+seprater);
					rowBuffer.append(inventoryStatusBean.getHead()+seprater);
					if(inventoryStatusBean.getDateOfFeed() != null)
						rowBuffer.append(inventoryStatusBean.getDateOfFeed());
					else
						rowBuffer.append(" ");
					
					/*try {
						dateStr = DateUtil.convertToFormatString(inventoryStatusBean.getDateOfFeed(), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr);
						else
							rowBuffer.append(" ");
					} catch (ParseException e) {
						rowBuffer.append(" ");
					}*/
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}

}
