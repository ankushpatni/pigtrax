pigTrax.controller('DataIntegrityReportController', function($scope, $http, $window,$modal, restServices) {	
	
	$scope.startDate;
	$scope.endDate;
	$scope.searchDataErrorMessage = false;
	
	$scope.getCompanyList = function(){
		restServices.getCompanyList(function(data){
			 if(!data.error)
			 {
				$scope.companyMapList = data.payload;
			 }
		});
	};
	
	
	$scope.loadPremises = function(companyId, dataStatus)
	{
		var localCompany ;
		if(companyId != null && companyId != undefined)
			localCompany = companyId;
		else
			localCompany = $scope.selectedCompany;
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+localCompany+'&premisesType=null');
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		$scope.companyId = localCompany;
		if(dataStatus == "true")
			$scope.searchDataErrorMessage = true;
		else
			$scope.searchDataErrorMessage = false;
	}    
	
	
	$scope.load = function(companyId, dataStatus)
	{
		$scope.getCompanyList();
		$scope.loadPremises(companyId, dataStatus);
		if(dataStatus == "true")
			$scope.searchDataErrorMessage = true;
		else
			$scope.searchDataErrorMessage = false;
	}    
  
    
    $scope.generate = function()
    {	
    	
    	if($scope.startDate !=  null &&  $scope.endDate != null)
    	{
    		$scope.criteriaMessage = false;	
			document.forms['generateDataIntegrityReportForm'].submit();
    	}
    	else
    	{
    		$scope.criteriaMessage = true;
    	}
    	
			
    }
    
    
});