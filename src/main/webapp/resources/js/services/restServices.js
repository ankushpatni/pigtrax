pigTrax.factory("restServices", function($resource) {
    return $resource("", null,
    {
    	"getEmployeeList"   	   :  {method:"GET", url:"rest/employee/getEmployeeList"}
    });
});