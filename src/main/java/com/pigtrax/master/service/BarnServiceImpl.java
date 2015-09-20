/**
 * 
 */
package com.pigtrax.master.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.dao.interfaces.BarnDao;
import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.dto.BarnBuilder;
import com.pigtrax.master.dto.Silo;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.pigevents.dto.BarnDto;

/**
 * @author Ankush
 *
 */
@Repository
public class BarnServiceImpl implements BarnService{

	@Autowired
	private BarnDao barnDao;
	
	@Autowired
	private BarnBuilder builder;

	@Override
	public List<Barn> getBarnList(int generatedPremisesId) {
		return barnDao.getBarnList( generatedPremisesId );
	}

	@Override
	public int updateBarnStatus(String barnID, Boolean barnStatus)
			throws SQLException {
		return barnDao.updateBarnStatus(barnID, barnStatus);
	}
	
	@Override
	public int insertBarnRecord(Barn barn) throws SQLException
	{
		return barnDao.insertBarnRecord(barn);
	}
	
	@Override
	public int updateBarnRecord(Barn barn) throws SQLException
	{
		return barnDao.updateBarnRecord(barn);
	}
	

	/**
	 * Get List of Barns for a given Company Id
	 * @param companyId
	 * @return List of BarnDto
	 * @throws SQLException
	 */
	public List<BarnDto> getBarns(Integer companyId) throws SQLException {
		List<Barn> barnList = barnDao.getBarns(companyId);
		return builder.convertToDtoList(barnList);
	}
	
	@Override
	public Barn findByBarnByAutoGeneratedId(final int generatedBarnId) throws SQLException
	{
		return barnDao.findByBarnByAutoGeneratedId(generatedBarnId);
	}

	@Override
	public Map<Integer,String> getBarnListBasedOnCompanyId(int generatedCompanyId) {
		Map<Integer,String> barnIdMap = new LinkedHashMap<Integer,String>();
		try
		{
		List<Barn> barnList =  barnDao.getBarnListBasedOnCompanyId(generatedCompanyId);
			if(null != barnList && barnList.size()>0)
			{
				for(Barn barn : barnList)
				{
					barnIdMap.put(barn.getId(),barn.getBarnId());
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return barnIdMap;
	}
	
	
}
