pigTrax.controller('sowReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	
	$scope.loadPremises = function(comapnyId)
	{
		$scope.companyId = comapnyId
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$scope.companyId+'&premisesType=null');
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
        
    $scope.searchPigInfo = function()
    {
			$scope.pigInfo = {};
			var searchPigInfo = {
					searchText : $scope.searchText,
					searchOption : 'pigId',
					companyId : $scope.companyId,
					selectedPremise : $scope.selectedPremise
			};
			restServices.getPigInformationWithOutStatus(searchPigInfo, function(data)
			{
				if(!data.error){
					$scope.searchDataErrorMessage = false;					
					document.forms['generateReportSow'].submit();
				}
				else
				{
					$scope.searchDataErrorMessage = true;
					
				}
			});
    }
	
	
});