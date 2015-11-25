pigTrax.controller('addTruckTrailorCtrl', function($scope, $http, $window, $modalInstance,truckTrailorData, restServices) {	
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.generatedCompanyId = truckTrailorData.generatedCompanyId;
	$scope.transportTrailerType = truckTrailorData.transportTrailerType;
	$scope.truck = truckTrailorData.truck;

	var currDate = new Date();
	var currentYear = currDate.getFullYear();
	$scope.purchaseYearArr = [];
	
	var yearVal = currentYear;
	for(i=0; i<20; i++)
	{
		$scope.purchaseYearArr[i] = yearVal;
		yearVal--;
	}
	
	$scope.getTrailerFunctions = function()
	{
		restServices.getTrailerFunctions(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.trailerFunctionKeys = responseList[0];
				$scope.trailerFunctionKeyTypes =responseList[1];
			}
		});
	}
    
    $scope.getTrailerFunctions();
	
	
	$scope.addTransportTruck = function() {
		if($scope.truckAddForm.$valid)
			{
			var postParam = {
							"transportTruckId" : $scope.add.transportTruckId,
							"companyId" : truckTrailorData.generatedCompanyId,
							"purchaseYear" : $scope.add.purchaseYear,
							"make" : $scope.add.make,
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
						if(data.duplicateRecord)
						{
							$scope.duplicateErrorMessage = true;
						}
						else
						{
							$scope.alertMessage = data.payload;
							$scope.alertVisible = true;
						}
					}
				});
				res.error(function(data, status, headers, config) {
					console.log( data);
					$scope.alertMessage = data.statusMessage;
					$scope.alertVisible = true;
				});
			};
	}
	
	$scope.addTransportTrailer = function() {
	console.log($scope.trailerAddForm.$valid);
		if($scope.trailerAddForm.$valid)
			{
			var postParam = {
							"transportTrailerId" : $scope.add.transportTrailerId,
							"companyId" : truckTrailorData.generatedCompanyId,
							"trailerTypeId" : $scope.add.trailerTypeId,
							"trailerFunctionId" : $scope.add.trailerFunctionId,
							"trailerYear" : $scope.add.trailerYear,
							"trailerMake" : $scope.add.trailerMake,
							
						};
				
				console.log(postParam);
				var res = $http.post('rest/transportTrailerTruck/insertTransportTrailorRecord', postParam);
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
