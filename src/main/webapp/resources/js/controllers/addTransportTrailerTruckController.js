pigTrax.controller('addTruckTrailorCtrl', function($scope, $http, $window, $modalInstance,truckTrailorData) {	
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.generatedCompanyId = truckTrailorData.generatedCompanyId;
	$scope.transportTrailerType = truckTrailorData.transportTrailerType;
		
	$scope.addTransportTruck = function() {
		if($scope.truckAddForm.$valid)
			{
			var postParam = {
							"transportTruckId" : $scope.add.transportTruckId,
							"companyId" : truckTrailorData.generatedCompanyId							
						};
				
				console.log(postParam);
				var res = $http.post('rest/transportTrailerTruck/insertTransportTruckRecord', postParam);
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

