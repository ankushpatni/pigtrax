package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.ActionListReportBean;
import com.pigtrax.report.dao.ActionListReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class ActionListReportService {

	@Autowired
	ActionListReportDao actionListReportDao;
	
	@Autowired
	MessageSource messageSource;

	private static final String separator = ",";

	public List<String> getActionList(Integer premiseId, Locale locale) {
		List<ActionListReportBean> actionList = actionListReportDao
				.getActionList(premiseId);

		Map<String, String> actionListTargets = getActionListTargets(premiseId);
		if(actionListTargets == null) actionListTargets = new HashMap<String, String>();
		
		
		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";

		if (actionList != null && actionList.size() > 0) {

			StringBuffer rowBuffer = null;			
			returnRows.add(messageSource.getMessage("label.reports.sowhistory.sowid", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.parity", null, "", locale)+","
					+messageSource.getMessage("label.reports.actionlist.age", null, "", locale)+","+messageSource.getMessage("label.reports.actionlist.servicegroup", null, "", locale)+","
					+messageSource.getMessage("label.reports.actionlist.sowphasedate", null, "", locale)+messageSource.getMessage("label.piginfo.input.dateformat", null, "", locale)+","
					+messageSource.getMessage("label.reports.actionlist.sowphase", null, "", locale)+","+messageSource.getMessage("label.barn.room", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.entryeventform.pen", null, "", locale)+","+messageSource.getMessage("label.reports.actionlist.servno", null, "", locale)+","
					+messageSource.getMessage("label.reports.actionlist.duedtanticipated", null, "", locale)+messageSource.getMessage("label.piginfo.input.dateformat", null, "", locale)+","
					+messageSource.getMessage("label.reports.actionlist.overdue", null, "", locale)+","+messageSource.getMessage("label.companytargetform.targetvalue", null, "", locale)+","
					+messageSource.getMessage("label.reports.actionlist.daysovertarget", null, "", locale)+"\n");
		
			int parityInt = 0;
			for (ActionListReportBean actionBean : actionList) {
				rowBuffer = new StringBuffer();
				if (actionBean.getPigId() != null) {
					rowBuffer.append(actionBean.getPigId() + separator);
					rowBuffer.append(actionBean.getParity() + separator);
					rowBuffer.append((actionBean.getAge() != 0 ?actionBean.getAge()+"":"" ) + separator);
					rowBuffer.append(actionBean.getServiceGroupId()+separator);
					try {
						dateStr = DateUtil.convertToFormatString(actionBean.getSowPhaseDate(), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr+separator);
						else
							rowBuffer.append(" "+separator);
					} catch (ParseException e) {
						rowBuffer.append(" "+separator);
					}
					rowBuffer.append(actionBean.getSowPhase() + separator);
					rowBuffer.append(actionBean.getRoomId() + separator);
					rowBuffer.append(actionBean.getPenId() + separator);
					rowBuffer.append(actionBean.getServNum()+separator);
					try {
						dateStr = DateUtil.convertToFormatString(actionBean.getDueDateAnticipated(), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr+separator);
						else
							rowBuffer.append(" "+separator);
					} catch (ParseException e) {
						rowBuffer.append(" "+separator);
					}
					rowBuffer.append(actionBean.getOverDue()+separator);
					
					Integer target = 0;
					try{
						target = Integer.parseInt(actionListTargets.get(actionBean.getSowPhase()));
					}
					catch(Exception e)
					{
						target = 0;
					}
					
					rowBuffer.append(target+separator);
					rowBuffer.append((actionBean.getOverDue() - target)+separator);
					returnRows.add(rowBuffer.toString()+"\n");
				}

			}
		}
		return returnRows;
	}
	
	
	
	private Map<String, String> getActionListTargets(Integer premiseId)
	{
		return actionListReportDao.getActionListTargets(premiseId);
	}

}
