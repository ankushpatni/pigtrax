package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.pigevents.beans.ChangedPigId;

public interface ChangedPigIdDao {
	
   int storeChangedPigId(ChangedPigId changedPigId)  throws SQLException;
}
