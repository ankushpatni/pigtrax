pigTrax.controller('TargetReportController', function($scope, $http, $window,$modal, restServices) {	

	$scope.startDate;
	
    $scope.generateTargetReport = function()
    {		
		document.forms['generateTargetReportForm'].submit();
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