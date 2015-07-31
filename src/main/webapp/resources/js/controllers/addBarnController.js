pigTrax.controller('addBarnCtrl', function($scope, $http, $window, $modalInstance,barnData) {	
	$scope.premisesData = barnData;
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.city;
	$scope.add.premisesId = barnData.premisesId;
	$scope.generatedPremisesId = barnData.generatedPremisesId;
	$scope.phaseType = barnData.phaseType;
	$scope.validationType = barnData.validationType;
	
	console.log(barnData);
	if(barnData != null && barnData.barnId !=null )
	{
		$scope.edit = true;
		$scope.add.barnId = barnData.barnId;
		console.log($scope.add.barnId);
    	$scope.add.phaseTypeId = barnData.phaseTypeId;
    	$scope.add.location = barnData.location;
		$scope.add.area = barnData.area;
		$scope.add.feederCount = barnData.feederCount;       
		$scope.add.waterAccessCount = barnData.waterAccessCount;
		$scope.add.active = barnData.active;		
		$scope.add.ventilationTypeId = barnData.ventilationTypeId;
		$scope.add.id = barnData.id
    	
	}
	
	$scope.addBarn = function() {
	console.log($scope.add.barnId);
	console.log($scope.barnAddForm.$valid);
		if($scope.barnAddForm.$valid)
			{
			var postParam;
				if( barnData != null && barnData.barnId !=null )
				{
					postParam = {
							"barnId" : $scope.add.barnId,
							"phaseTypeId" : $scope.add.phaseTypeId,
							"location" : $scope.add.location,
							"area" : $scope.add.area,
							"feederCount" : $scope.add.feederCount,
							"waterAccessCount" : $scope.add.waterAccessCount,
							"active" : barnData.active,
							"id" : $scope.add.id,
							"premiseId" : barnData.generatedPremisesId,
							"ventilationTypeId" : $scope.add.ventilationTypeId
					};
				}
				else
				{
					postParam = {
							"barnId" : barnData.premisesId +''+$scope.add.barnId,
							"phaseTypeId" : $scope.add.phaseTypeId,
							"location" : $scope.add.location,
							"area" : $scope.add.area,
							"feederCount" : $scope.add.feederCount,
							"waterAccessCount" : $scope.add.waterAccessCount,
							"active" : true,
							"id" : $scope.add.id,
							"premiseId" : barnData.generatedPremisesId,
							"ventilationTypeId" : $scope.add.ventilationTypeId
					};
				}
				console.log(postParam);
				var res = $http.post('rest/barn/insertBarnRecord', postParam);
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
	
	$scope.changeCity = function(){	
		$scope.city = $scope.cityJSON[$scope.add.state];
	}
});

