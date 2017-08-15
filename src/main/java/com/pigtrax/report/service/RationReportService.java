package com.pigtrax.report.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.report.bean.RationReportBean;
import com.pigtrax.report.bean.RationReportFeedCostBean;
import com.pigtrax.report.dao.RationReportDao;
import com.pigtrax.util.PrimitiveDataUtilAndManipulation;

@Repository
public class RationReportService {

	@Autowired
	RationReportDao rationReportDao;
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Autowired
	MessageSource messageSource;

	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	private static final String seprater = ",";

	public List<String> getRationReportList(String premise, Integer companyId, Integer premiseId, Date startDate, Date endDate, Integer groupId, Locale locale) { 
		List<RationReportBean> rationReportList = rationReportDao.getRationReportList(premiseId, startDate, endDate, groupId);
		List<RationReportFeedCostBean> feedCostList = rationReportDao.getFeedCostAndWeight(groupId); 
		List<Map<String, Date>> startAndDateList = rationReportDao.getStartEndDates(groupId);
		Date pigEntryDate = startAndDateList.get(0).get("startDate");
		Date pigEndDate = startAndDateList.get(0).get("endDate");
		
		GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(groupId, companyId);

		ArrayList<String> returnRows = new ArrayList<String>();
		if (rationReportList != null && rationReportList.size() > 0) {

			StringBuffer rowBuffer = null;			
			
			returnRows.add(
					messageSource.getMessage("label.piginfo.groupEventForm.groupId", null,"",locale)+
					","+messageSource.getMessage("label.piginfo.feedEventForm.batchId", null, "", locale)
					+",Inventory,"
					+"DOF"
					+","+messageSource.getMessage("label.reports.rationreport.actualtonsused", null, "", locale)
					+","+messageSource.getMessage("label.reports.rationreport.targettonsused", null, "", locale)
					+","+messageSource.getMessage("label.reports.rationreport.deviationtonsused", null, "", locale)+","
					+messageSource.getMessage("label.reports.rationreport.actualkgperpig", null, "", locale)+","
					+messageSource.getMessage("label.reports.rationreport.targetkgperpig", null, "", locale)+","
					+messageSource.getMessage("label.reports.rationreport.deviationkgperpig", null, "", locale)+","
					+"$/kg"+","
					+messageSource.getMessage("label.reports.rationreport.actualcostperpig", null, "", locale)+","
					+messageSource.getMessage("label.reports.rationreport.targetcostperpig", null, "", locale)+","
					+messageSource.getMessage("label.reports.rationreport.deviationcostperpig", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.groupperformancereport.totalfeedcost", null, "", locale)+","
					+messageSource.getMessage("label.reports.salesreport.weightperpig", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.feedEventForm.ticketNumber", null, "", locale)+",\n");
			int count = 0;
			for (RationReportBean rationReportBean : rationReportList) {
				rowBuffer = new StringBuffer();
				
				final Date feedDate = rationReportBean.getFeedEvenDate();
				Date nextFeedDate = null;
				Date currentFeedDate = feedDate;
				if (count == 0){
					currentFeedDate = pigEntryDate;
//					currentFeedDate = startDate;
				}
				if (count == rationReportList.size()-1){
					nextFeedDate = pigEndDate;
//					nextFeedDate = endDate;
				} else{
					nextFeedDate = rationReportList.get(count+1).getFeedEvenDate();

				}
				

				String sql = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num from pigtrax.\"GroupEventDetails\" GED "
						+ "where GED.\"id_GroupEvent\" = "+groupId+" and GED.\"dateOfEntry\" <= ?";
				
				@SuppressWarnings("unchecked")
				Integer sowCount  = (Integer)jdbcTemplate.query(sql,new PreparedStatementSetter() {
					@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setDate(1, new java.sql.Date(feedDate.getTime()));
						}
					},
			        new ResultSetExtractor() {
			          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return 0;
			          }
			        });
				if(sowCount == 0 )
				{
					Map<String, Object> detailsMap = groupEventDao.getStartWtAndHead(groupId);
					if(detailsMap != null && detailsMap.get("StartHd") != null)
					{
						sowCount = ((Long)detailsMap.get("StartHd")).intValue();
					}
				}
				
				int duration = getDOF(currentFeedDate,nextFeedDate);
				
					
					rowBuffer.append(groupEvent.getGroupId() + seprater);
					rowBuffer.append(rationReportBean.getRationId() + seprater);
					rowBuffer.append(sowCount + seprater);
					rowBuffer.append(duration + seprater);
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(rationReportBean.getActualTonsUsed(),3) + seprater);
					double targetTons = rationReportBean.getTargetTonsUsed()*sowCount*rationReportBean.getTargetKg()/1000 ; 
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(targetTons,3) + seprater);
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(rationReportBean.getActualTonsUsed()-targetTons,3) + seprater);
					double adfi = rationReportBean.getActualTonsUsed()*1000/duration/sowCount ;
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(adfi,3)+ seprater);
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(rationReportBean.getTargetTonsUsed(),3) + seprater);
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(adfi-rationReportBean.getTargetTonsUsed(),3)  + seprater);
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(feedCostList.get(count).getWtdAvg(),3) + seprater);
					double feedCostHD= feedCostList.get(count).getWtdAvg()*adfi*duration;
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(feedCostHD,3) + seprater);
					double feedCostHDTarget = feedCostList.get(count).getWtdAvg()*rationReportBean.getTargetTonsUsed()*duration	;
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(feedCostHDTarget,3) + seprater);
					rowBuffer.append(PrimitiveDataUtilAndManipulation.getRoundtoNPlace(feedCostHD - feedCostHDTarget,3)+ seprater);
					rowBuffer.append(sowCount*PrimitiveDataUtilAndManipulation.getRoundtoNPlace(feedCostHD,3)+ seprater);
					rowBuffer.append(rationReportBean.getTotalWeight()+ seprater);
					rowBuffer.append((rationReportBean.getTicketNum1() != null?rationReportBean.getTicketNum1()+":": "")+(rationReportBean.getTicketNum2() != null?rationReportBean.getTicketNum2()+":": "")+(rationReportBean.getTicketNum3() != null?rationReportBean.getTicketNum3()+":": "") +seprater);
					
					
					returnRows.add(rowBuffer.toString()+"\n");
					count++;
			}
		}
		return returnRows;
	}

	private int getDOF(Date startDate, Date feedDate) {
		int duration = 0;
		if(startDate != null && feedDate != null)
		{
			DateTime start = new DateTime(startDate.getTime());
			DateTime end = new DateTime(feedDate.getTime());
			duration  = Days.daysBetween(start, end).getDays();
		}

		// TODO Auto-generated method stub
		return duration;
	}

}
