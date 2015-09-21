package com.pigtrax.pigevents.dao.interfaces;

import java.util.List;

import com.pigtrax.pigevents.beans.MatingDetails;

public interface MatingDetailsDao {

	List<MatingDetails> getMatingDetails(Integer breedingEventId);
	
	int addMatingDetails(MatingDetails matingDetails);
	
	int deleteMatingDetails(Integer matingDetailsId);
	
	int deleteMatingDetailsForBreedingEvent(Integer breedingEventId);
}
