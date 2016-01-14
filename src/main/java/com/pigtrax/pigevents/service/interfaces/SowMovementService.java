package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.SowMovement;

public interface SowMovementService {

	int addSowMovement(SowMovement sowMovement) throws PigTraxException;

	int updateSowMovement(SowMovement sowMovement) throws PigTraxException;

	int deleteSawMoment(Integer id) throws Exception;

	List<SowMovement> getSowMomwntsListByCompany(int companyId)
			throws Exception;

	List<SowMovement> getSowMovementListByPigInfoId(String pigInfoId,
			int companyId, int premisesId) throws Exception;

}
