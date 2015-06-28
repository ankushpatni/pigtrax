pigTrax.factory("restServices", function($resource) {
    return $resource("", null,
    {
    	"getEmployeeList"      :  {method:"GET", url:"rest/employee/getEmployeeList"},
    	"getCompanyList"   	   :  {method:"GET", url:"rest/company/getCompanyList"},
    	"updateCompanyStatus"  :  {method:"POST", url:"rest/company/updateCompanyStatus"}
    });
});