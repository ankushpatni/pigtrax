package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.Pen;

public interface PenDao {
    public List<Pen> getPenList(Integer barnId) throws SQLException;
}
