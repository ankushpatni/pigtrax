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
	
	
	
	$scope.load = function( dataStatus)
	{
		$scope.getCompanyList();
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