package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.dto.Barn;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.SalesEventDetails;
import com.pigtrax.report.bean.ActionListReportBean;
import com.pigtrax.report.bean.GroupReportBean;
import com.pigtrax.report.dao.ActionListReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class ActionListReportService {

	@Autowired
	ActionListReportDao actionListReportDao;

	private static final String seprater = ",";

	public List<String> getActionList(Integer premiseId) {
		List<ActionListReportBean> actionList = actionListReportDao
				.getActionList(premiseId);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";

		if (actionList != null && actionList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Sow Id, Parity, Age, Service Group, Sow Phase Date(mm/dd/yyyy), Sow Phase, Room, Pen, Serv No, Due Date Anticipated(mm/dd/yyyy), Overdue, Average Gestation Length, Lactating Days");
			returnRows.add("\n");
			int parityInt = 0;
			for (ActionListReportBean actionBean : actionList) {
				rowBuffer = new StringBuffer();
				if (actionBean.getPigId() != null) {
					rowBuffer.append(actionBean.getPigId() + seprater);
					rowBuffer.append(actionBean.getParity() + seprater);
					rowBuffer.append(actionBean.getAge() + seprater);
					rowBuffer.append(actionBean.getServiceGroupId()+seprater);
					try {
						dateStr = DateUtil.convertToFormatString(actionBean.getSowPhaseDate(), "MM/dd/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr+seprater);
						else
							rowBuffer.append(" "+seprater);
					} catch (ParseException e) {
						rowBuffer.append(" "+seprater);
					}
					rowBuffer.append(actionBean.getSowPhase() + seprater);
					rowBuffer.append(actionBean.getRoomId() + seprater);
					rowBuffer.append(actionBean.getPenId() + seprater);
					rowBuffer.append(actionBean.getServNum()+seprater);
					try {
						dateStr = DateUtil.convertToFormatString(actionBean.getDueDateAnticipated(), "MM/dd/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr+seprater);
						else
							rowBuffer.append(" "+seprater);
					} catch (ParseException e) {
						rowBuffer.append(" "+seprater);
					}
					rowBuffer.append(actionBean.getOverDue()+seprater);
					rowBuffer.append(actionBean.getGestationLength()+seprater);
					rowBuffer.append(actionBean.getLactatingDays());
					returnRows.add(rowBuffer.toString()+"\n");
				}

			}
		}
		return returnRows;
	}

}
