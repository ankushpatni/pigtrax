package com.pigtrax.master.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.master.beans.Origin;
import com.pigtrax.master.dao.interfaces.OriginDao;
import com.pigtrax.master.dto.OriginDto;
@Repository
@Transactional
public class OriginDaoImpl implements OriginDao {
	
	private static final Logger logger = Logger.getLogger(OriginDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
   @Override
	public List<OriginDto> getOriginList() {
	   String query = "SELECT \"id\",\"name\", \"lastUpdated\",\"userUpdated\" from pigtrax.\"Origin\" order by \"name\" ";

	   return jdbcTemplate.query(query, new OriginMapper());
	}
   
   
   

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
		
	}
   
   @Override
	public Integer saveOrigin(final Origin origin) {
	   String query = "INSERT INTO pigtrax.\"Origin\"(  \"name\", \"lastUpdated\",\"userUpdated\")"+
				 "VALUES ( ?, current_timestamp, ?)";
	
	return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, origin.getName());
			ps.setString(2, origin.getUserUpdated());
		}
	});
		
	}
   
   @Override
	public Integer deleteOrigin(final Integer originId) {
	   String query = "delete from pigtrax.\"Origin\" where \"id\" = ?";
	
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, originId);
			}
		});
		
	}
   
   
   private static final class OriginMapper implements RowMapper<OriginDto> {
		public OriginDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			OriginDto origin = new OriginDto();
			origin.setId(rs.getInt("id"));
			origin.setName(rs.getString("name"));
			origin.setLastUpdated(rs.getDate("lastUpdated"));
			origin.setUserUpdated(rs.getString("userUpdated"));
			
			return origin;
		}
	}
}
