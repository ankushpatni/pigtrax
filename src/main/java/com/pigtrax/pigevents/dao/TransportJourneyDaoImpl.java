package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.dao.interfaces.TransportJourneyDao;

@Repository
@Transactional
public class TransportJourneyDaoImpl implements TransportJourneyDao{

	private static final Logger logger = Logger.getLogger(TransportJourneyDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	
	
	@Override
	public TransportJourney getTransportJourneyById(final int id) throws SQLException {
		String qry = "select \"id\", \"trailerFunction\", \"journeyStartTime\", \"journeyEndTime\", \"id_TransportDestination\", "
		   		+ " \"id_TransportTruck\", \"id_TransportTrailer\", \"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"TransportJourney\" where \"id\" = ? ";
			
			List<TransportJourney> transportJourneyList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, id);
				}}, new TransportJourneyMapper());

			if(transportJourneyList != null && transportJourneyList.size() > 0){
				return transportJourneyList.get(0);
			}
			return null;
	}

	@Override
	public int addTransportJourney(final TransportJourney transportJourney)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"TransportJourney\"(\"trailerFunction\", \"journeyStartTime\", \"journeyEndTime\","
			+" \"id_TransportDestination\", \"id_TransportTruck\", \"id_TransportTrailer\", \"lastUpdated\", \"userUpdated\") "
				+ "values(?,?,?,?,?,?,current_timestamp,?)";
			
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});	    	           
	    	            ps.setString(1, transportJourney.getTrailerFunction());
	    	            if(transportJourney.getJourneyStartTime() != null )
	    	            	ps.setDate(2, new java.sql.Date(transportJourney.getJourneyStartTime().getTime()));
	    	            else
	    	            	ps.setNull(2, java.sql.Types.DATE);
	    	            if(transportJourney.getJourneyEndTime() != null )
	    	            	ps.setDate(3, new java.sql.Date(transportJourney.getJourneyEndTime().getTime()));
	    	            else
	    	            	ps.setNull(3, java.sql.Types.DATE);
	    	            if(transportJourney.getTransportDestinationId() != null )
	    	            	ps.setInt(4, transportJourney.getTransportDestinationId());
	    	            else 
	    	            	ps.setNull(4, java.sql.Types.INTEGER);
	    	            
	    	            if(transportJourney.getTransportTruckId() != null )
	    	            	ps.setInt(5, transportJourney.getTransportTruckId());
	    	            else 
	    	            	ps.setNull(5, java.sql.Types.INTEGER);
	    	            if(transportJourney.getTransportTrailerId() != null )
	    	            	ps.setInt(6, transportJourney.getTransportTrailerId());
	    	            else 
	    	            	ps.setNull(6, java.sql.Types.INTEGER);
	    	          
	    	            ps.setString(7, transportJourney.getUserUpdated());
	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}

	@Override
	public int updateTransportJourney(final TransportJourney transportJourney)
			throws SQLException {
		String Qry = "update pigtrax.\"TransportJourney\" set \"trailerFunction\" = ?, \"journeyStartTime\" = ?, \"journeyEndTime\" = ?, \"id_TransportDestination\"= ?, \"id_TransportTruck\"= ?, \"id_TransportTrailer\" = ?,"
				+ "  \"lastUpdated\" = current_timestamp, \"userUpdated\" = ? where \"id\" = ? ";

		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setString(1, transportJourney.getTrailerFunction());
				if (transportJourney.getJourneyStartTime() != null)
					ps.setDate(2, new java.sql.Date(transportJourney
							.getJourneyStartTime().getTime()));
				else
					ps.setNull(2, java.sql.Types.DATE);
				if (transportJourney.getJourneyEndTime() != null)
					ps.setDate(3, new java.sql.Date(transportJourney
							.getJourneyEndTime().getTime()));
				else
					ps.setNull(3, java.sql.Types.DATE);
				if (transportJourney.getTransportDestinationId() != null)
					ps.setInt(4, transportJourney.getTransportDestinationId());
				else
					ps.setNull(4, java.sql.Types.INTEGER);

				if (transportJourney.getTransportTruckId() != null)
					ps.setInt(5, transportJourney.getTransportTruckId());
				else
					ps.setNull(5, java.sql.Types.INTEGER);
				if (transportJourney.getTransportTrailerId() != null)
					ps.setInt(6, transportJourney.getTransportTrailerId());
				else
					ps.setNull(6, java.sql.Types.INTEGER);

				ps.setString(7, transportJourney.getUserUpdated());
				ps.setInt(8, transportJourney.getId());
			}
		});
	}
	
	private static final class TransportJourneyMapper implements RowMapper<TransportJourney> {
		public TransportJourney mapRow(ResultSet rs, int rowNum) throws SQLException {
			TransportJourney transportJourney = new TransportJourney();
			transportJourney.setId(rs.getInt("id"));
			transportJourney.setTrailerFunction(rs.getString("trailerFunction"));
			transportJourney.setJourneyStartTime(rs.getDate("journeyStartTime"));
			transportJourney.setJourneyEndTime(rs.getDate("journeyEndTime"));
			transportJourney.setTransportDestinationId(rs.getInt("id_TransportDestination"));
			transportJourney.setTransportTruckId(rs.getInt("id_TransportTruck"));
			transportJourney.setTransportTrailerId(rs.getInt("id_TransportTrailer"));
			transportJourney.setLastUpdated(rs.getDate("lastUpdated"));
			transportJourney.setUserUpdated(rs.getString("userUpdated"));
			return transportJourney;
		}
	}
}
