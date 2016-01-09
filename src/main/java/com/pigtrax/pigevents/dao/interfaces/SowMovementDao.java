package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.SowMovement;

public interface SowMovementDao {

	int addSowMovement(SowMovement sowMovement) throws SQLException;

	void deleteSowMovement(Integer id) throws SQLException;

	int updateSowMovement(SowMovement sowMovement) throws SQLException;

	List<SowMovement> getSowMovementListByCompanyId(Integer companyId)
			throws SQLException;

}
