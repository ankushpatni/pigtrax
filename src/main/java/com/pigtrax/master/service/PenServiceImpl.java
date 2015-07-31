package com.pigtrax.master.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.dao.interfaces.PenDao;
import com.pigtrax.master.dto.Pen;
import com.pigtrax.master.service.interfaces.PenService;
import com.pigtrax.pigevents.dto.PenBuilder;

/**
 * @author Ankush
 *
 */
@Repository
public class PenServiceImpl implements PenService {

	@Autowired
	private PenDao penDao;
	
	@Autowired
	PenBuilder penBuilder;

	@Override
	public List<Pen> getPenList(int generatedRoomId) {
		return penDao.getPenList(generatedRoomId);
	}

	@Override
	public int updatePenStatus(String penID, Boolean penStatus)
			throws SQLException {
		return penDao.updatePenStatus(penID, penStatus);
	}

	@Override
	public int insertPenRecord(Pen pen) throws SQLException {
		return penDao.insertPenRecord(pen);
	}

	@Override
	public int updatePenRecord(Pen pen) throws SQLException {
		return penDao.updatePenRecord(pen);
	}

	public List<Pen> getPenListByBarnId(Integer barnId) throws SQLException {
		List<Pen> penList = penDao.getPenListByBarnId(barnId);
		return penList;
	}

}
