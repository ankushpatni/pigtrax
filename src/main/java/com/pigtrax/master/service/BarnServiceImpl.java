/**
 * 
 */
package com.pigtrax.master.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.dao.interfaces.BarnDao;
import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.pigevents.dto.BarnDto;
import com.pigtrax.master.dto.BarnBuilder;

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
	
	
}
