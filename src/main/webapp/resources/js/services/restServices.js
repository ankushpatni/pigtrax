pigTrax.factory("restServices", function($resource) {
    return $resource("", null,
    {
    	"getEmployeeList"      :  {method:"GET", url:"rest/employee/getEmployeeList"},
    	"getCompanyList"   	   :  {method:"GET", url:"rest/company/getCompanyList"},
    	"updateCompanyStatus"  :  {method:"POST", url:"rest/company/updateCompanyStatus"},
    	"getPremisesList"      :  {method:"GET", url:"rest/premises/getPremisesList"},
    	"updatePremisesStatus"  :  {method:"POST", url:"rest/premises/updatePremisesStatus"},
    	"getBarns"			   :  {method:"POST", url:"rest/entryEvent/getBarns"},
    	"getPenList"		   :  {method:"POST", url:"rest/entryEvent/getPenList"},
    	"saveEntryEventInformation" : {method:"POST", url:"rest/entryEvent/saveEntryEventInformation"},
    	"getPigInformation" : {method:"POST", url:"rest/entryEvent/getPigInformation"},
    	"deletePigInfo" : {method:"POST", url:"rest/entryEvent/deletePigInfo"},
    	"getBreedingServiceType" : {method:"GET", url:"rest/util/getBreedingServiceType"},
    	"saveBreedingEventInformation" : {method:"POST", url:"rest/breedingEvent/saveBreedingEventInformation"},
    	"validateBreedingEvent" : {method:"POST", url:"rest/breedingEvent/validateBreedingEvent"},
    	"deleteBreedingEventInfo" : {method:"POST", url:"rest/breedingEvent/deleteBreedingEventInfo"},
    	"deleteEmployeeGroup" : {method:"POST", url:"rest/employeeGroup/deleteEmployeeGroup"},
    	"getBreedingEventInformation" : {method:"POST", url:"rest/breedingEvent/getBreedingEventInformation"},    	
    	"saveEmployeeGroup" : {method:"POST", url:"rest/employeeGroup/saveEmployeeGroup"},
    	"getEmployeeGroups" : {method:"POST", url:"rest/employeeGroup/getEmployeeGroups"},
    	"getEmployeeList" : {method:"POST", url:"rest/employeeGroup/getEmployeeList"},
    	"getPregnancyEventTypes" : {method:"GET", url:"rest/util/getPregnancyEventTypes"}, 
    	"getPregnancyExamResultTypes" : {method:"GET", url:"rest/util/getPregnancyExamResultTypes"},
    	"checkForBreedingServiceId" : {method:"POST", url:"rest/pregnancyEvent/checkForBreedingServiceId"},
    	"savePregnancyEventInformation" : {method:"POST", url:"rest/pregnancyEvent/savePregnancyEventInformation"},
    	"getPregnancyEventInformation" : {method:"POST", url:"rest/pregnancyEvent/getPregnancyEventInformation"},  
    	"deletePregnancyEvent" : {method:"POST", url:"rest/pregnancyEvent/deletePregnancyEvent"},
    	"validatePregnancyEvent" : {method:"POST", url:"rest/pregnancyEvent/validatePregnancyEvent"},
    	"getPenListForCompany"		   :  {method:"POST", url:"rest/farrowEvent/getPenListForCompany"},
    	"validateFarrowEvent" : {method:"POST", url:"rest/farrowEvent/validateFarrowEvent"},
    	"saveFarrowEventInformation" : {method:"POST", url:"rest/farrowEvent/saveFarrowEventInformation"},
    	"getFarrowEventInformation" : {method:"POST", url:"rest/farrowEvent/getFarrowEventInformation"},
    	"deleteFarrowEvent" : {method:"POST", url:"rest/farrowEvent/deleteFarrowEvent"},
    	"saveGroupEventInformation" : {method:"POST", url:"rest/groupEvent/addGroupEvent"},
		"getGroupEventInformation" : {method:"POST", url:"rest/groupEvent/getGroupEventInformation"},
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
    });
});
