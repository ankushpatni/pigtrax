pigTrax.controller('addPenCtrl', function($scope, $http, $window, $modalInstance,penData) {	
	$scope.premisesData = penData;
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.city;
	$scope.add.roomId = penData.roomId;
	$scope.generatedRoomId = penData.generatedRoomId;
	
	console.log(penData);
	if(penData != null && penData.penId !=null )
	{
		$scope.edit = true;
		$scope.add.roomId = penData.roomId;
		$scope.add.location = penData.location;
		$scope.add.active = penData.active;		
		$scope.add.id = penData.id;
		$scope.add.penId = penData.penId
    	
	}
	
	$scope.addPen = function() {
	console.log(penData);
		if($scope.penAddForm.$valid)
			{
			var postParam;
				if( penData != null && penData.penId !=null )
				{
					postParam = {
							"penId" : $scope.add.penId,
							"location" : $scope.add.location,
							"active" : penData.active,
							"id" : $scope.add.id,
							"roomId" : penData.generatedRoomId
					};
				}
				else
				{
					postParam = {
							"penId" : penData.roomId +''+$scope.add.penId,
							"location" : $scope.add.location,
							"active" : false,
							"roomId" : penData.generatedRoomId							
						};
				}
				console.log(postParam);
				var res = $http.post('rest/pen/insertPenRecord', postParam);
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

