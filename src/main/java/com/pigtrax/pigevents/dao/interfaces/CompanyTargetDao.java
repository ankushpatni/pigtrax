package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.CompanyTarget;

public interface CompanyTargetDao {
	
   List<CompanyTarget> getCompanyTargets(Integer companyId) throws SQLException;
   
   int addCompanyTarget(CompanyTarget companyTarget) throws SQLException;
   
   int updateCompanyTarget(CompanyTarget companyTarget) throws SQLException;
   
   int deleteTargetDetails(Integer companyTargetId) throws SQLException;

   CompanyTarget getCompanyTargetsByPremises(Integer premisesId) throws SQLException;
}
