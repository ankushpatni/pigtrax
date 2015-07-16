package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.pigevents.beans.Pen;
import com.pigtrax.pigevents.dao.interfaces.PenDao;
import com.pigtrax.pigevents.dto.PenBuilder;
import com.pigtrax.pigevents.dto.PenDto;
import com.pigtrax.pigevents.service.interfaces.PenService;

@Repository
public class PenServiceImpl implements PenService {
    @Autowired
    PenDao penDao;
    
    @Autowired
    PenBuilder penBuilder;
    
    public List<PenDto> getPenList(Integer barnId) throws SQLException {
    	List<Pen> penList = penDao.getPenList(barnId);
    	return penBuilder.convertToDtoList(penList);
    }
}
