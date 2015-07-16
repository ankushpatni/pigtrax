pigTrax.factory("restServices", function($resource) {
    return $resource("", null,
    {
    	"getEmployeeList"      :  {method:"GET", url:"rest/employee/getEmployeeList"},
    	"getCompanyList"   	   :  {method:"GET", url:"rest/company/getCompanyList"},
    	"updateCompanyStatus"  :  {method:"POST", url:"rest/company/updateCompanyStatus"},
    	"getPremisesList"      :  {method:"GET", url:"rest/premises/getPremisesList"},
    	"updatePremisesStatus"  :  {method:"POST", url:"rest/premises/updatePremisesStatus"},
    	"getBarns"			   :  {method:"GET", url:"rest/entryEvent/getBarns"},
    	"getPenList"		   :  {method:"POST", url:"rest/entryEvent/getPenList"},
    	"saveEntryEventInformation" : {method:"POST", url:"rest/entryEvent/saveEntryEventInformation"},
    	"getPigInformation" : {method:"POST", url:"rest/entryEvent/getPigInformation"}
    });
});