pigTrax.controller('GestationReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	$scope.startDate;
	$scope.endDate;
	
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
//		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+localCompany+'&premisesType=1,4,6,8,');
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+localCompany+'&premisesType=1,4,6,8');
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});		
		$scope.companyId = localCompany;
	}    
  
    
    $scope.generateProdEventLog = function()
    {	
    	if($scope.generateGestationReportForm.$valid)
    	{
			document.getElementById("companyId1").value	= $scope.companyId;		
			document.getElementById("selectedPremise").value	= $scope.selectedPremise;		
			document.forms['generateGestationReportForm'].submit();
    	}	
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