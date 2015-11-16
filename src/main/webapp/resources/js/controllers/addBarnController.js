pigTrax.controller('addBarnCtrl', function($scope, $http, $window, $modalInstance,barnData, restServices) {	
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
	
	$scope.getBarnLocations = function()
	{
		restServices.getBarnLocations(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.barnLocationKeys = responseList[0];
				$scope.barnLocationKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getBarnLocations();
	
	$scope.getWaterTypes = function()
	{
		restServices.getWaterTypes(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.waterTypeKeys = responseList[0];
				$scope.waterTypeKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getWaterTypes();
	
	
	$scope.getBarnPositions = function()
	{
		restServices.getBarnPositions(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.barnPositionKeys = responseList[0];
				$scope.barnPositionKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getBarnPositions();
	
	
	$scope.getFeederTypes = function()
	{
		restServices.getFeederTypes(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.feederTypeKeys = responseList[0];
				$scope.feederTypeKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getFeederTypes();
	
	
	
	console.log(barnData);
	if(barnData != null && barnData.barnId !=null )
	{
		$scope.edit = true;
		$scope.add.barnIdEdit = barnData.barnId;
		console.log($scope.add.barnId);
    	$scope.add.phaseTypeId = barnData.phaseTypeId;
    	$scope.add.location = barnData.location;
		$scope.add.area = barnData.area;
		$scope.add.feederCount = barnData.feederCount;       
		$scope.add.waterAccessCount = barnData.waterAccessCount;
		$scope.add.active = barnData.active;		
		$scope.add.ventilationTypeId = barnData.ventilationTypeId;
		$scope.add.id = barnData.id
		$scope.add.barnId = 1;
		$scope.add.barnOrientationId = barnData.barnOrientationId;
		
		$scope.add.barnLocationId = barnData.barnLocationId;
		$scope.add.waterTypeId = barnData.waterTypeId;
		$scope.add.barnPositionId = barnData.barnPositionId;
		$scope.add.feederTypeId = barnData.feederTypeId;
		$scope.add.holesPerFeeder = barnData.holesPerFeeder;
		$scope.add.remarks = barnData.remarks;
    	
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
							"barnId" : barnData.barnId,
							"phaseTypeId" : $scope.add.phaseTypeId,
							"location" : $scope.add.location,
							"area" : $scope.add.area,
							"feederCount" : $scope.add.feederCount,
							"waterAccessCount" : $scope.add.waterAccessCount,
							"active" : barnData.active,
							"id" : $scope.add.id,
							"premiseId" : barnData.generatedPremisesId,
							"ventilationTypeId" : $scope.add.ventilationTypeId,
							"barnOrientationId" : $scope.add.barnOrientationId,
							"barnLocationId" : $scope.add.barnLocationId,
							"barnPositionId" : $scope.add.barnPositionId,
							"waterTypeId" : $scope.add.waterTypeId,
							"feederTypeId" : $scope.add.feederTypeId,
							"holesPerFeeder" : $scope.add.holesPerFeeder,
							"remarks" : $scope.add.remarks
						
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
							"ventilationTypeId" : $scope.add.ventilationTypeId,
							"barnOrientationId" : $scope.add.barnOrientationId,
							"barnLocationId" : $scope.add.barnLocationId,
							"barnPositionId" : $scope.add.barnPositionId,
							"waterTypeId" : $scope.add.waterTypeId,
							"feederTypeId" : $scope.add.feederTypeId,
							"holesPerFeeder" : $scope.add.holesPerFeeder,
							"remarks" : $scope.add.remarks
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

