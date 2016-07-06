pigTrax.controller('CompanyCleanupController', function($scope, $http, $window,$modal, restServices) {	
	 
	$scope.criteriaMessage = false;
	$scope.successMessage = false;
	
    $scope.getCompanyList = function(){
    	
		restServices.getCompanyList(function(data){
			 if(!data.error)
			 { 
				$scope.companyMapList = data.payload;
			 }
		});
	};
	
	
	
	$scope.cleanUp = function()
	{
		if($scope.cleanUpCompanyDataForm.$valid)
		{
			if($scope.selectedCompany == null || $scope.selectedCompany == undefined)
				$scope.criteriaMessage = true;
			else
			{
			  $scope.criteriaMessage = false;		  
			  restServices.cleanUpCompanyData($scope.selectedCompany, function(data){			  
				  $scope.successMessage = true;
			  });	 
			}
		}
	}
});