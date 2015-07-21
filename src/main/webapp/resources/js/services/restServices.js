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
    	"getBreedingEventInformation" : {method:"POST", url:"rest/breedingEvent/getBreedingEventInformation"},
    	"getEmployeeGroups" : {method:"POST", url:"rest/employeeGroup/getEmployeeGroups"}
    });
});