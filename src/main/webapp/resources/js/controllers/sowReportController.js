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
	
    $scope.checkPigInfo = function()
	{
		document.getElementById("generatedSiloId").value = row.id;
		document.forms['siloForm'].action = $scope.differentPages[index].value;
		document.forms['siloForm'].submit();
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
			restServices.getPigInformation(searchPigInfo, function(data)
			{
				if(!data.error){
					$scope.clearAllMessages();
					$scope.pigInfo = data.payload;
					$scope.getRooms();
				}
				else
				{
					$scope.pigInfo = {};
					$scope.clearAllMessages();
					$scope.searchDataErrorMessage = true;
					
				}
			});
    }
	
	
});