pigTrax.controller('editSowMovementCtrl', function($scope, $http, $window, $modalInstance,sowMovementData, restServices) {	
	$scope.sowMovementDataEdit = sowMovementData;
	$scope.premisesMap = sowMovementData.premisesMap;
	//$scope.roomMap = sowMovementData.roomMap;
	
	$scope.premiseId = sowMovementData.premiseId;
	$scope.roomId = sowMovementData.roomId;
	console.log(sowMovementData);
		
	$scope.getRooms = function()
		{
			restServices.getRoomsForPremise($scope.premiseId, function(data){
				if(!data.error){
					
					$scope.roomMap = data.payload;					
				}
			});
		}
	$scope.getRooms();
	
	$scope.editSowMovement = function() {
		if($scope.editSowMovementForm.$valid)
			{
				if($scope.sowMovementDataEdit.premiseId === $scope.premiseId && 
				$scope.sowMovementDataEdit.roomId === $scope.roomId)
				{
					$modalInstance.close();	
					return false;					
				}
				else{
			var postParam;
					postParam = {
							"roomId" : $scope.roomId,
							"premiseId" : $scope.premiseId,
							"pigInfoId" : $scope.sowMovementDataEdit.pigInfo
				};
				console.log(postParam);
				var res = $http.post('rest/sowMomentEvent/addSowEvent', postParam);
				res.success(function(data, status, headers, config) {
					console.log(data);
					if(data.statusMessage==="Success")
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
			}
			};
	}
	
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
});

