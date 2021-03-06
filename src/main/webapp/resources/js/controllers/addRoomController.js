pigTrax.controller('addRoomCtrl', function($scope, $http, $window, $modalInstance,roomData) {	
	$scope.premisesData = roomData;
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.city;
	$scope.add.barnId = roomData.barnId;
	$scope.generatedBarnId = roomData.generatedBarnId;
	
	console.log(roomData);
	if(roomData != null && roomData.roomId !=null )
	{
		$scope.edit = true;
		$scope.add.barnId = roomData.barnId;
		$scope.add.location = roomData.location;
		$scope.add.active = roomData.active;		
		$scope.add.id = roomData.id;
		$scope.add.roomIdEdit = roomData.roomId;
		$scope.add.roomId = 1;
		$scope.add.roomPositionId = roomData.roomPositionId;
    	
	}
	
	$scope.addRoom = function() {
		if($scope.roomAddForm.$valid)
			{
			var postParam;
				if( roomData != null && roomData.roomId !=null )
				{
					postParam = {
							"roomId" : roomData.roomId,
							"location" : $scope.add.location,
							"active" : roomData.active,
							"id" : $scope.add.id,
							"barnId" : roomData.generatedBarnId,
							"floorTypeId" : $scope.add.floorTypeId,
							"roomPositionId" : $scope.add.roomPositionId,
							"pigSpaces" : $scope.add.pigSpaces
					};
				}
				else
				{
					postParam = {
							"roomId" : roomData.barnId +''+$scope.add.roomId,
							"location" : $scope.add.location,
							"active" : true,
							"barnId" : roomData.generatedBarnId	,
							"roomPositionId" : $scope.add.roomPositionId,
							"floorTypeId" : $scope.add.floorTypeId,
							"pigSpaces" : $scope.add.pigSpaces
						};
				}
				console.log(postParam);
				var res = $http.post('rest/room/insertRoomRecord', postParam);
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

