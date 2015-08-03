pigTrax.controller('addSiloCtrl', function($scope, $http, $window, $modalInstance,siloData) {	
	$scope.premisesData = siloData;
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.city;
	$scope.add.barnId = siloData.barnId;
	$scope.generatedBarnId = siloData.generatedBarnId;
	$scope.siloType = siloData.siloType;
	
	console.log(siloData);
	if(siloData != null && siloData.siloId !=null )
	{
		$scope.edit = true;
		$scope.add.barnId = siloData.barnId;
		$scope.add.location = siloData.location;
		$scope.add.id = siloData.id;
		$scope.add.siloTypeId = siloData.siloTypeId;
		$scope.add.siloIdEdit = siloData.siloId;
		$scope.add.siloId = 1;
    	
	}
	
	$scope.addSilo = function() {
		if($scope.siloAddForm.$valid)
			{
			var postParam;
				if( siloData != null && siloData.siloId !=null )
				{
					postParam = {
							"siloId" : siloData.siloId,
							"location" : $scope.add.location,
							"id" : $scope.add.id,
							"barnId" : siloData.generatedBarnId,
							"siloTypeId" : $scope.add.siloTypeId
					};
				}
				else
				{
					postParam = {
							"siloId" : siloData.barnId +''+$scope.add.siloId,
							"location" : $scope.add.location,
							"barnId" : siloData.generatedBarnId,
							"siloTypeId" : $scope.add.siloTypeId
						};
				}
				console.log(postParam);
				var res = $http.post('rest/silo/insertSiloRecord', postParam);
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

