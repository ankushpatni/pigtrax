package com.pigtrax.report.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pigtrax.report.bean.RemovalReportBean;
import com.pigtrax.report.bean.SaleReportBean;


@Repository
@Transactional
public class SaleReportDao {
	
	private static final Logger logger = Logger.getLogger(SaleReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
public List<SaleReportBean> getSaleList(final int premisesId, final int barnId, final int groupId, final Date start, final Date end, String ticketNumber, int pigId) {
	
	String query1 = "select P.\"name\",GE.\"pigId\", '' as \"groupId\", RES.\"salesDateTime\", RES.\"salesTypes\",RES.\"numberOfPigs\", RES.\"soldTo\", "
			 +" RES.\"weightInKgs\",RES.\"ticketNumber\",RES.\"invoiceId\",RES.\"revenueUsd\",RES.\"id_TransportJourney\",RES.\"remarks\", "
			 +" TT.\"trailerId\", TAT.\"truckId\",'' as \"roomId\",'' as \"barnId\" "
			 +" from pigtrax.\"SalesEventDetails\" RES "
			 
			 +" join pigtrax.\"PigInfo\" GE ON RES.\"id_PigInfo\" = GE.\"id\" "
			 +" left join pigtrax.\"Premise\" P  ON GE.\"id_Premise\" = P.\"id\"     "
			 +" left JOIN pigtrax.\"TransportJourney\" TJ ON RES.\"id_TransportJourney\" = TJ.\"id\" "
			 +" left JOIN pigtrax.\"TransportTrailer\" TT ON TJ.\"id_TransportTrailer\" = TT.\"id\" "
			 +" left JOIN pigtrax.\"TransportTruck\" TAT ON TJ.\"id_TransportTruck\" = TAT.\"id\" where  GE.\"id_Premise\" = ?" ;
	/* +" left join (select GEPC.\"id_GroupEvent\", GER.\"id_Room\" from pigtrax.\"GroupEventRoom\" GER  "
			 +" left JOIN pigtrax.\"GroupEventPhaseChange\" GEPC ON GER.\"id_GroupEventPhaseChange\" = GEPC.\"id\" "
			 +" where GEPC.\"phaseEndDate\" is null) GR_ROOM ON RES.\"id_GroupEvent\" = GR_ROOM.\"id_GroupEvent\" "
			 +" left join pigtrax.\"Room\" RGR ON GR_ROOM.\"id_Room\" =  RGR.\"id\" "
			 +" left join (SELECT \"barnserialid\" as \"id\",\"roomserrialid\" as \"id_Room\" from pigtrax.\"CompPremBarnRoomPenVw\" where \"barnId\" != '') BARN_ID ON  GR_ROOM.\"id_Room\" = BARN_ID.\"id_Room\" "
			 +" left join pigtrax.\"Barn\" BA ON BARN_ID.\"id\" = BA.\"id\" where  GE.\"id_Premise\" = ? ";		*/
	
	
	if(pigId != 0)
		query1 = query1 +"  and RES.\"id_PigInfo\" =  "+pigId ;
	
	if(ticketNumber != null && !StringUtils.isEmpty(ticketNumber))
		query1 = query1 +"  and RES.\"ticketNumber\" =  '"+ticketNumber+"'" ;
	
		
	if(start != null)
		query1 = query1 +"  and RES.\"salesDateTime\" >  '" + start+"'";
	
	if(end != null)
		query1 = query1 + " and RES.\"salesDateTime\" < '" + end+"'";
		
	query1 = query1 + " order by GE.\"pigId\", RES.\"salesDateTime\" ";
	
		
		String query = "select P.\"name\",'' as \"pigId\",GE.\"groupId\", RES.\"salesDateTime\", RES.\"salesTypes\",RES.\"numberOfPigs\", RES.\"soldTo\", "
				 +" RES.\"weightInKgs\",RES.\"ticketNumber\",RES.\"invoiceId\",RES.\"revenueUsd\",RES.\"id_TransportJourney\",RES.\"remarks\", "
				 +" TT.\"trailerId\", TAT.\"truckId\",RGR.\"roomId\",BA.\"barnId\" "
				 +" from pigtrax.\"SalesEventDetails\" RES "
				 
				 +" join pigtrax.\"GroupEvent\" GE ON RES.\"id_GroupEvent\" = GE.\"id\" "
				 +" left join pigtrax.\"Premise\" P  ON GE.\"id_Premise\" = P.\"id\"     "
				 +" left JOIN pigtrax.\"TransportJourney\" TJ ON RES.\"id_TransportJourney\" = TJ.\"id\" "
				 +" left JOIN pigtrax.\"TransportTrailer\" TT ON TJ.\"id_TransportTrailer\" = TT.\"id\" "
				 +" left JOIN pigtrax.\"TransportTruck\" TAT ON TJ.\"id_TransportTruck\" = TAT.\"id\" "
				 +" left join (select GEPC.\"id_GroupEvent\", GER.\"id_Room\" from pigtrax.\"GroupEventRoom\" GER  "
				 +" left JOIN pigtrax.\"GroupEventPhaseChange\" GEPC ON GER.\"id_GroupEventPhaseChange\" = GEPC.\"id\" "
				 +" where GEPC.\"phaseEndDate\" is null) GR_ROOM ON RES.\"id_GroupEvent\" = GR_ROOM.\"id_GroupEvent\" "
				 +" left join pigtrax.\"Room\" RGR ON GR_ROOM.\"id_Room\" =  RGR.\"id\" "
				 +" left join (SELECT \"barnserialid\" as \"id\",\"roomserrialid\" as \"id_Room\" from pigtrax.\"CompPremBarnRoomPenVw\" where \"barnId\" != '') BARN_ID ON  GR_ROOM.\"id_Room\" = BARN_ID.\"id_Room\" "
				 +" left join pigtrax.\"Barn\" BA ON BARN_ID.\"id\" = BA.\"id\" where  GE.\"id_Premise\" = ? ";		
		
		
		if(groupId != 0)
			query = query +"  and RES.\"id_GroupEvent\" =  "+groupId ;
		
		if(ticketNumber != null && !StringUtils.isEmpty(ticketNumber))
			query = query +"  and RES.\"ticketNumber\" =  '"+ticketNumber+"'" ;
		
		if(barnId != 0)
			query = query +"  and BA.\"id\" =  "+barnId ;
		
		if(start != null)
			query = query +"  and RES.\"salesDateTime\" > '" + start +"'";
		
		if(end != null)
			query = query + " and RES.\"salesDateTime\" < '" + end+"'";
			
		query = query + " order by GE.\"groupId\", RES.\"salesDateTime\" ";
		
		String finalQuery =  "(" +query1 + ") UNION (" +query+" ) ";
		
		List<SaleReportBean> saleReportBeanList = jdbcTemplate.query(finalQuery, new PreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premisesId);
				ps.setInt(2, premisesId);
				
			}}, new SaleReportMapper());
		
		return saleReportBeanList;
	
	}



private static final class SaleReportMapper implements RowMapper<SaleReportBean> {
	public SaleReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		SaleReportBean saleReportBean = new SaleReportBean();
		
		saleReportBean.setPremisesId(rs.getString("name"));
		saleReportBean.setGroupID(rs.getString("groupId"));
		saleReportBean.setSalesEventDate(rs.getDate("salesDateTime"));
		saleReportBean.setNumberOfPigsSold(rs.getInt("numberOfPigs"));
		saleReportBean.setWeightPerPig(rs.getInt("weightInKgs")/7);
		saleReportBean.setTotalWeight(rs.getInt("weightInKgs"));
		saleReportBean.setSalesType(rs.getString("salesTypes"));
		saleReportBean.setDestination(rs.getString("soldTo"));
		saleReportBean.setTicketNumber(rs.getString("ticketNumber"));
		saleReportBean.setInvoiceNumber(rs.getString("invoiceId"));
		saleReportBean.setRevenue(rs.getFloat("revenueUsd"));
		saleReportBean.setRemarks(rs.getString("remarks"));
		saleReportBean.setTrailer(rs.getString("trailerId"));
		saleReportBean.setTruck(rs.getString("truckId"));
		saleReportBean.setBarnID(rs.getString("barnId"));
		saleReportBean.setRoomID(rs.getString("roomId"));
		saleReportBean.setPigID(rs.getString("pigId"));
		
		return saleReportBean;
	}
}

	

}
