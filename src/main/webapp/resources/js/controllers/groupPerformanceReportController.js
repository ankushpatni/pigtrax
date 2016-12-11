pigTrax.controller('GroupPerformanceReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;

	
	$scope.loadPremises = function(comapnyId)
	{
		var localCompany ;
		if(comapnyId === undefined )
		{
			localCompany = $scope.selectedCompany;
		}
		else
		{
			localCompany  = comapnyId;
		}
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+localCompany);
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		$scope.companyId = localCompany;
	}
	
        
    $scope.searchGroupInfo = function()
    {
			$scope.searchDataErrorMessage = false;
			document.getElementById("companyId1").value	= $scope.companyId;		
			document.forms['generateGroupPerformanceReport'].submit();
			
    }
    
    
    $scope.getCompanyList = function(){
    	
    		restServices.getCompanyList(function(data){
    			 if(!data.error)
    			 {
    				$scope.companyMapList = data.payload;
    			 }
    		});
    	};
    	
	$scope.getCompanyList();
	
});