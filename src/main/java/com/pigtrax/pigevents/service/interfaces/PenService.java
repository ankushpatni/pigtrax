package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.dto.PenDto;

public interface PenService {
    
	List<PenDto> getPenList(Integer barnId) throws SQLException;
}
