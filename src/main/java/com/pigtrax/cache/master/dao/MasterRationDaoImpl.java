package com.pigtrax.master.dao;

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

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.beans.MasterRation;
import com.pigtrax.master.dao.interfaces.MasterRationDao;
import com.pigtrax.master.dto.MasterRationDto;
@Repository
@Transactional
public class MasterRationDaoImpl implements MasterRationDao {
	
	private static final Logger logger = Logger.getLogger(MasterRationDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	   @Override
	public List<MasterRationDto> getRationList() {
		// TODO Auto-generated method stub
		return null;
	}
/*
   @Override
	public boolean checkIfExists(final String name) {
	   if(name !=null && name.trim().length() > 0)
	   {
		   String query = "select count(\"id\") from pigtrax.\"Origin\" where lower(\"name\") = ?";
		
		   @SuppressWarnings("unchecked")
			Integer cnt  = (Integer)jdbcTemplate.query(query,new PreparedStatementSetter() {
				@Override
					public void setValues(PreparedStatement ps) throws SQLException {					
						ps.setString(1, name.toLowerCase());
					}
				},
			        new ResultSetExtractor() {
			          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return null;
			          }
			        });
			
			return cnt > 0? true : false;
	   }
	   else
		   return false;
		
	}*/
   
   @Override
	public Integer saveRation(final MasterRation rationObj) {
	   String query = "INSERT INTO pigtrax.\"MasterRation\"(  \"rationValue\",\"id_FeedEventType\", \"lastUpdated\",\"userUpdated\",\"rationDescription\", \"id_RationType\")"+
				 "VALUES ( ?, ?,current_timestamp, ?,?,?)";
	
	return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, rationObj.getRationValue());
			ps.setObject(2, rationObj.getFeedTypeId(), java.sql.Types.INTEGER);
			ps.setString(3, rationObj.getUserUpdated());
			ps.setString(4, rationObj.getRationDescription());
			ps.setObject(5, rationObj.getRationTypeId(), java.sql.Types.INTEGER);
		}
	});
		
	}
   
   @Override
	public Integer deleteRation(final Integer rationId) {
	   String query = "delete from pigtrax.\"MasterRation\" where \"id\" = ?";
	
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, rationId);
			}
		});
		
	}
	@Override
	public List<MasterRationDto> getRationList(final String language) {
		String query = "SELECT \"id\",\"rationValue\", \"id_FeedEventType\", \"lastUpdated\",\"userUpdated\",\"rationDescription\",\"id_RationType\" from pigtrax.\"MasterRation\" order by \"rationValue\" ";

		   List<MasterRationDto> rationDtoList =  jdbcTemplate.query(query, new MasterRationMapper());
		   
		   if(rationDtoList != null && 0<rationDtoList.size())
		   {
			   for(MasterRationDto rationDto : rationDtoList)
			   {
				   rationDto.setFeedType(refDataCache.getFeedEventTypeMap(language).get(rationDto.getFeedTypeId()));
				   
				   rationDto.setRationType(refDataCache.getMasterRationTypeMap(language).get(rationDto.getRationTypeId())); 
			   }
		   }
		   
		   return rationDtoList;
	}   
	
	private static final class MasterRationMapper implements RowMapper<MasterRationDto> {
		public MasterRationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			MasterRationDto masterRationDto = new MasterRationDto();
			masterRationDto.setId(rs.getInt("id"));
			masterRationDto.setRationValue(rs.getString("rationValue"));
			masterRationDto.setFeedTypeId(rs.getInt("id_FeedEventType"));
			masterRationDto.setLastUpdated(rs.getDate("lastUpdated"));
			masterRationDto.setUserUpdated(rs.getString("userUpdated"));
			masterRationDto.setRationDescription(rs.getString("rationDescription"));
			masterRationDto.setRationTypeId(rs.getInt("id_RationType"));
			return masterRationDto;
		}
	}
	
	@Override
	public MasterRationDto findRationById(final Integer rationId) {
		String query = "SELECT \"id\",\"rationValue\", \"id_FeedEventType\", \"lastUpdated\",\"userUpdated\",\"rationDescription\",\"id_RationType\" "
				+ "from pigtrax.\"MasterRation\" where \"id\" = ?"
				+ " order by \"rationValue\" ";
		
		   List<MasterRationDto> rationDtoList = jdbcTemplate.query(query, new PreparedStatementSetter(){
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, rationId);
				}}, new MasterRationMapper());
		   
		   if(rationDtoList != null && 0<rationDtoList.size())
		   {
			   return rationDtoList.get(0);
		   }
		   return null;
	}
  
}
