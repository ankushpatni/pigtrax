pigTrax.controller('addTransportDestinationCtrl', function($scope, $http, $window, $modalInstance,transportDestinationData, restServices) {	
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.generatedCompanyId = transportDestinationData.generatedCompanyId;
	
	$scope.getMarketTypes = function()
	{
		restServices.getMarketTypes(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.marketTypeKeys = responseList[0];
				$scope.marketTypeKeyValues =responseList[1];
			}
		});
	}
	
	$scope.getMarketTypes();
	
	$scope.addTransportDestination = function() {
		if($scope.transportDestinationAddForm.$valid)
			{
			var postParam = {
							"name" : $scope.add.name,
							"address" : $scope.add.address,
							"city" : $scope.add.city,
							"companyId" : transportDestinationData.generatedCompanyId,
							"state" : $scope.add.state,
							"marketTypeId" : $scope.add.marketTypeId
						};
				
				console.log(postParam);
				var res = $http.post('rest/transportDestination/insertTransportDestination', postParam);
				res.success(function(data, status, headers, config) {
					if(data.statusMessage==="SUCCESS")
					{
						$modalInstance.close(data);					
						return data;
					}
					else
					{
						$scope.alertMessage = data.payload;
						$scope.alertVisible = true;
					}
				});
				res.error(function(data, status, headers, config) {
					console.log( data);
					$scope.alertMessage = data.statusMessage;
					$scope.alertVisible = true;
				});
			};
	}
		
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
});

