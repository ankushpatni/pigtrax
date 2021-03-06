package com.pigtrax.master.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.dao.interfaces.SiloDao;
import com.pigtrax.master.dto.Room;
import com.pigtrax.master.dto.Silo;
import com.pigtrax.master.service.interfaces.SiloService;

/**
 * @author Ankush
 *
 */
@Repository
public class SiloServiceImpl implements SiloService{

	@Autowired
	private SiloDao siloDao;

	@Override
	public List<Silo> getSiloList(int generatedBarnId) {
		return siloDao.getSiloList( generatedBarnId );
	}

	@Override
	public int updateSiloStatus(String siloID, Boolean siloStatus)
			throws SQLException {
		return siloDao.updateSiloStatus(siloID, siloStatus);
	}
	
	@Override
	public int insertSiloRecord(Silo silo) throws SQLException
	{
		return siloDao.insertSiloRecord(silo);
	}
	
	@Override
	public int updateSiloRecord(Silo silo) throws SQLException
	{
		return siloDao.updateSiloRecord(silo);
	}
	
	public Silo findBySiloByAutoGeneratedId(final int generatedSiloId) throws SQLException
	{
		return siloDao.findBySiloByAutoGeneratedId(generatedSiloId);
	}
	
	public Map<Integer,String> getSiloListBasedOnCompanyId( int generatedCompanyId )
	{
		Map<Integer,String> siloIdMap = new LinkedHashMap<Integer,String>();
		try
		{
		List<Silo> siloList =  siloDao.getSiloListBasedOnCompanyId(generatedCompanyId);
			if(null != siloList && siloList.size()>0)
			{
				for(Silo silo : siloList)
				{
					siloIdMap.put(silo.getId(),silo.getSiloId());
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return siloIdMap;
	}
	
	
	public Map<Integer,String> getSiloListBasedOnPremiseId( Integer premiseId )
	{
		Map<Integer,String> siloIdMap = new LinkedHashMap<Integer,String>();
		try
		{
		List<Silo> siloList =  siloDao.getSiloListBasedOnPremiseId(premiseId);
			if(null != siloList && siloList.size()>0)
			{
				for(Silo silo : siloList)
				{
					siloIdMap.put(silo.getId(),silo.getSiloId());
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return siloIdMap;
	}

	@Override
	public Integer deleteSilo(Integer siloId) {
		return siloDao.deleteSilo(siloId);
	}
}
