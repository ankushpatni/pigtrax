package com.pigtrax.pigevents.service.interfaces;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.SowMovement;

public interface SowMovementService {

	int addSowMovement(SowMovement sowMovement) throws PigTraxException;

	int updateSowMovement(SowMovement sowMovement) throws PigTraxException;

}
