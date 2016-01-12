pigTrax.factory("restServices", function($resource) {
    return $resource("", null,
    {
    	"getEmployeeList"      :  {method:"GET", url:"rest/employee/getEmployeeList"},
    	"getCompanyList"   	   :  {method:"GET", url:"rest/company/getCompanyList"},
    	"updateCompanyStatus"  :  {method:"POST", url:"rest/company/updateCompanyStatus"},
    	"getPremisesList"      :  {method:"GET", url:"rest/premises/getPremisesList"},
    	"getPremisesListBySowSource"      :  {method:"GET", url:"rest/premises/getPremisesListBySowSource"},
    	"updatePremisesStatus"  :  {method:"POST", url:"rest/premises/updatePremisesStatus"},
    	"getBarns"			   :  {method:"POST", url:"rest/entryEvent/getBarns"},
    	"getPenList"		   :  {method:"POST", url:"rest/entryEvent/getPenList"},
    	"saveEntryEventInformation" : {method:"POST", url:"rest/entryEvent/saveEntryEventInformation"},
    	"getPigInformation" : {method:"POST", url:"rest/entryEvent/getPigInformation"},
    	"getInactivePigInformation" : {method:"POST", url:"rest/entryEvent/getInactivePigInformation"},
    	"deletePigInfo" : {method:"POST", url:"rest/entryEvent/deletePigInfo"},
    	"getBreedingServiceType" : {method:"GET", url:"rest/util/getBreedingServiceType"},
    	"saveBreedingEventInformation" : {method:"POST", url:"rest/breedingEvent/saveBreedingEventInformation"},
    	"validateBreedingEvent" : {method:"POST", url:"rest/breedingEvent/validateBreedingEvent"},
    	"deleteBreedingEventInfo" : {method:"POST", url:"rest/breedingEvent/deleteBreedingEventInfo"},
    	"deleteEmployeeGroup" : {method:"POST", url:"rest/employeeGroup/deleteEmployeeGroup"},
    	"getBreedingEventInformation" : {method:"POST", url:"rest/breedingEvent/getBreedingEventInformation"}, 
    	"getBreedingEventDetails" : {method:"POST", url:"rest/breedingEvent/getBreedingEventDetails"},
    	"getActiveBreedingServices" : {method:"POST", url:"rest/breedingEvent/getActiveBreedingServices"},
    	"getPregnantBreedingServices" : {method:"POST", url:"rest/breedingEvent/getPregnantBreedingServices"},
    	"saveEmployeeGroup" : {method:"POST", url:"rest/employeeGroup/saveEmployeeGroup"},
    	"getEmployeeGroups" : {method:"POST", url:"rest/employeeGroup/getEmployeeGroups"},
    	"getEmployeeList" : {method:"POST", url:"rest/employeeGroup/getEmployeeList"},
    	"getPregnancyEventTypes" : {method:"GET", url:"rest/util/getPregnancyEventTypes"}, 
    	"getPregnancyExamResultTypes" : {method:"GET", url:"rest/util/getPregnancyExamResultTypes"},
    	"checkForBreedingServiceId" : {method:"POST", url:"rest/pregnancyEvent/checkForBreedingServiceId"},
    	"savePregnancyEventInformation" : {method:"POST", url:"rest/pregnancyEvent/savePregnancyEventInformation"},
    	"getPregnancyEventInformation" : {method:"POST", url:"rest/pregnancyEvent/getPregnancyEventInformation"},  
    	"getPregnancyEventDetailsByBreedingId" : {method:"POST", url:"rest/pregnancyEvent/getPregnancyEventDetailsByBreedingId"},
    	"getPregnancyEventDetailsById" : {method:"POST", url:"rest/pregnancyEvent/getPregnancyEventDetailsById"},  
    	"deletePregnancyEvent" : {method:"POST", url:"rest/pregnancyEvent/deletePregnancyEvent"},
    	"validatePregnancyEvent" : {method:"POST", url:"rest/pregnancyEvent/validatePregnancyEvent"},
    	"getPenListForCompany"		   :  {method:"POST", url:"rest/farrowEvent/getPenListForCompany"},
    	"getPenListForPremise"		   :  {method:"POST", url:"rest/farrowEvent/getPenListForPremise"},
    	"validateFarrowEvent" : {method:"POST", url:"rest/farrowEvent/validateFarrowEvent"},
    	"saveFarrowEventInformation" : {method:"POST", url:"rest/farrowEvent/saveFarrowEventInformation"},
    	"getFarrowEventInformation" : {method:"POST", url:"rest/farrowEvent/getFarrowEventInformation"},
    	"getFarrowEventDetailsById" : {method:"POST", url:"rest/farrowEvent/getFarrowEventDetailsById"},
    	"deleteFarrowEvent" : {method:"POST", url:"rest/farrowEvent/deleteFarrowEvent"},
    	"saveGroupEventInformation" : {method:"POST", url:"rest/groupEvent/addGroupEvent"},
		"getGroupEventInformation" : {method:"POST", url:"rest/groupEvent/getGroupEventInformation"},
		"getGroupEventInformationForTransfer" : {method:"POST", url:"rest/groupEvent/getGroupEventInformationForTransfer"},
    	"getPigletEventInformation" : {method:"POST", url:"rest/pigletEvent/getPigletEventInformation"},
    	"getFarrowEventDetails": {method:"POST", url:"rest/farrowEvent/getFarrowEventDetails"}  ,
    	"savePigletEventInformation" : {method:"POST", url:"rest/pigletEvent/savePigletEventInformation"},
    	"deletePigletEvent" : {method:"POST", url:"rest/pigletEvent/deletePigletEvent"},
    	"savePigletStatusEventInformation" : {method:"POST", url:"rest/pigletStatusEvent/savePigletStatusEventInformation"},
    	"getPigletStatusEventInformation" : {method:"POST", url:"rest/pigletStatusEvent/getPigletStatusEventInformation"},
    	"validatePigletStatusEvent" : {method:"POST", url:"rest/pigletStatusEvent/validatePigletStatusEvent"},    	
    	"deletePigletStatusEvent" : {method:"POST", url:"rest/pigletStatusEvent/deletePigletStatusEventInformation"},
    	"getAllFosterPigs" : {method:"POST", url:"rest/entryEvent/getAllFosterPigs"},    	
    	"getFosterInRecords" : {method:"POST", url:"rest/pigletStatusEvent/getFosterInRecords"},
    	"getPigletStatusEventsByFarrowEventId" : {method:"POST", url:"rest/pigletStatusEvent/getPigletStatusEventsByFarrowEventId"},
    	"getPhaseOfProductionType" :{method:"GET", url:"rest/util/getPhaseOfProductionType"},
		"addGroupEventDetail" : {method:"POST", url:"rest/groupEvent/addGroupEventDetail"},
		"getGroupEventDetail" : {method:"POST", url:"rest/groupEvent/getGroupEventDetail"},
		"addTransportJourney" : {method:"POST", url:"rest/transportJourney/addTransportJourney"},
		"updateGroupEventInformation" : {method:"POST", url:"rest/groupEvent/updateGroupEventInformation"},
		"addFeedEvent" : {method:"POST",url:"rest/feedEvent/addFeedEvent"},
		"getFeedEventInformation" : {method:"POST",url:"rest/feedEvent/getFeedEventInformation"},
		"addFeedEventDetail" : {method:"POST",url:"rest/feedEvent/addFeedEventDetail"},
		"getGestationRecord" : {method:"POST",url:"rest/breedingEvent/getGestationRecord"},
		"getCityCountryList" : {method:"GET",url:"rest/util/getCityCountryList"},
		"addRemovalEvent" : {method:"POST",url:"rest/removalEvent/addRemovalEvent"},
		"getRemovalEventInformation" : {method:"POST",url:"rest/removalEvent/getRemovalEventInformation"},
		"addRemovalExceptSales" : {method:"POST",url:"rest/removalEvent/addRemovalExceptSales"},
		"getRemovalExceptSales" : {method:"POST",url:"rest/removalEvent/getRemovalExceptSales"},
		"deleteRemovalExceptSales": {method:"POST",url:"rest/removalEvent/deleteRemovalExceptSales"},
		"getGfunctionTypes" : {method:"GET", url:"rest/util/getGfunctionTypes"}, 
		"getMortalityReasonTypes" : {method:"GET", url:"rest/util/getMortalityReasonTypes"},
		"getRemovalEventInformationList" : {method:"POST",url:"rest/removalEvent/getRemovalEventInformationList"},
		"saveMatingDetails" : {method:"POST",url:"rest/matingDetails/saveMatingDetails"},
		"validateMatingDetails" : {method:"POST", url:"rest/breedingEvent/validateMatingDetails"},
		"deleteMatingDetails" : {method:"POST", url:"rest/matingDetails/deleteMatingDetails"},
		"getCompanyTargets" : {method:"POST", url:"rest/companyTarget/getCompanyTargets"},
		"saveCompanyTarget" : {method:"POST", url:"rest/companyTarget/saveCompanyTarget"},
		"getTargetTypes" : {method:"GET", url:"rest/util/getTargetTypes"},
		"deleteTargetDetails" : {method:"POST", url:"rest/companyTarget/deleteTargetDetails"},
		"addSalesEventDetails": {method:"POST",url:"rest/removalEvent/addSalesEventDetails"},
		"getSalesEventDetails": {method:"POST",url:"rest/removalEvent/getSalesEventDetails"},
		"deleteSalesEventDetails": {method:"POST",url:"rest/removalEvent/deleteSalesEventDetails"},
		"getPigInformationForChangeId" : {method:"POST", url:"rest/entryEvent/getPigInformationForChangeId"},
		"changePigId" : {method:"POST", url:"rest/changePigId"},
		"productionLog" : {method:"POST", url:"rest/productionLog"},
		"getProductionLogList" : {method:"POST", url:"rest/productionLog/getProductionLogList"},
		"deleteProductionLog" : {method:"POST", url:"rest/productionLog/deleteProductionLog"},
		"getAvailablePigIds":	{method:"GET",		url:"rest/entryEvent/getAvailablePigIds/:companyId", params:{companyId: "@companyId"} },
		"getEmployeeListIndi"      :  {method:"GET", url:"rest/employee/getEmployeeListIndi"},
		"getRoleTypes"      :  {method:"GET", url:"rest/util/getRoleTypes"},
		"getEmployeeDetails"      :  {method:"POST", url:"rest/employee/getEmployeeDetails"},
		"forgotPassword"      :  {method:"POST", url:"rest/employee/forgotPassword"},
		"validateOneTimePassword" : {method:"POST", url:"rest/employee/validateOneTimePassword"},
		"changePassword"      :  {method:"POST", url:"rest/employee/changePassword"},
		"getGcompanyTypes" : {method:"GET", url:"rest/util/getGcompanyTypes"},
		"getGlineTypes" : {method:"GET", url:"rest/util/getGlineTypes"},
		"deletePremise" : {method:"POST", url:"rest/premises/deletePremise"},
		"getRoomsForCompany" : {method:"POST", url:"rest/room/getRoomsForCompany"},
		"getRoomsForPremise" : {method:"POST", url:"rest/room/getRoomsForPremise"},
		"getLogEventTypes" : {method:"GET", url:"rest/util/getLogEventTypes"},
		"getRemovalPremise" : {method:"POST", url:"rest/removalEvent/getRemovalPremise"},
		"getOriginList" : {method:"GET", url:"rest/origin/getOriginList"},
		"saveOrigin" : {method:"POST", url:"rest/origin/saveOrigin"},
		"deleteOrigin" : {method:"POST", url:"rest/origin/deleteOrigin"},
		"saveRation" : {method:"POST", url:"rest/ration/saveRation"},
		"deleteRation" : {method:"POST", url:"rest/ration/deleteRation"},
		"getFeedEventTypes" : {method:"GET", url:"rest/util/getFeedEventTypes"},
		"getRationList" : {method:"GET", url:"rest/ration/getRationList"},
		"getPigletConditions" : {method:"GET", url:"rest/util/getPigletConditions"},
		"getBarnDetailsByRoom" : {method:"POST", url:"rest/barn/getBarnDetailsByRoom"},
		"getSaleTypes" : {method:"GET", url:"rest/util/getSaleTypes"},
		"getSaleReasons" : {method:"GET", url:"rest/util/getSaleReasons"},
		"getPremiseTypes" : {method:"GET", url:"rest/util/getPremiseTypes"},
		"getBarnLocations" : {method:"GET", url:"rest/util/getBarnLocations"},
		"getWaterTypes" : {method:"GET", url:"rest/util/getWaterTypes"},
		"getBarnPositions" : {method:"GET", url:"rest/util/getBarnPositions"},
		"getFeederTypes" : {method:"GET", url:"rest/util/getFeederTypes"},
		"getFunctionTypes" : {method:"GET", url:"rest/util/getFunctionTypes"},
		"gtJobFunctionRoles" : {method:"GET", url:"rest/util/getJobFunctionRoles"},
		"getEmployeeRoles" : {method:"POST", url:"rest/employee/getEmployeeRoles"},
		"getTrailerFunctions" : {method:"GET", url:"rest/util/getTrailerFunctions"},
		"getMarketTypes" : {method:"GET", url:"rest/util/getMarketTypes"},
		"getRationTypes" : {method:"GET", url:"rest/util/getRationTypes"},
		"checkForLitterId" : {method:"POST", url:"rest/pigletEvent/checkForLitterId"},
		"moveBackToNursery" : {method:"POST", url:"rest/groupEvent/moveBackToNursery"},
		"promoteToFinish" : {method:"POST", url:"rest/groupEvent/promoteToFinish"},
		"transferToGroup" : {method:"POST", url:"rest/groupEvent/transferToGroup"},
		"getSowMovement" : {method:"POST", url:"rest/sowMomentEvent/getSowMomentList"},
		"deleteSowMovement" : {method:"POST", url:"rest/sowMomentEvent/deleteSowMovement"}
    });
});
