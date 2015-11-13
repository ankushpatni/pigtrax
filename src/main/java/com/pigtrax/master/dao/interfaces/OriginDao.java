package com.pigtrax.master.dao.interfaces;

import java.util.List;

import com.pigtrax.master.beans.Origin;
import com.pigtrax.master.dto.OriginDto;

public interface OriginDao {
	List<OriginDto> getOriginList();
    Integer saveOrigin(Origin origin);
    Integer deleteOrigin(Integer originId);
    boolean checkIfExists(String name) ;
}
