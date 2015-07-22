package com.pigtrax.master.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.usermanagement.dto.EmployeeDto;

public interface EmployeeGroupDao {
    List<EmployeeGroupDto> getEmployeeGroups(Integer companyId) throws SQLException;
    
    List<EmployeeDto> getEmployeeList(Integer companyId, String jobFunction) throws SQLException;
    
    void addEmployeeGroup(EmployeeGroupDto employeeGroupDto) throws SQLException, DuplicateKeyException;
}
