package com.pigtrax.master.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.EmployeeGroupDto;

public interface EmployeeGroupDao {
    List<EmployeeGroupDto> getEmployeeGroups(Integer companyId) throws SQLException;
}
