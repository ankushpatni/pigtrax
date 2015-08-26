pigTrax.controller('moveToAnotherGroupCtrl', function($scope, $http, $window, $modalInstance,moveToAnotherGroup,restServices) {	
	$scope.moveToAnotherGroup = moveToAnotherGroup;
	$scope.phaseOfProductionType = moveToAnotherGroup.phaseOfProductionType;
	
	
	$scope.addMoveGroupEvent = function() {
		if(document.getElementById("groupStartDateTime").value === "")
		{
			$scope.groupdaterequired = true;
			console.log($scope.groupdaterequired);
			return;
		}
		if($scope.moveGroupeventAddForm.$valid)
		{
			var postParam = {
			
					"groupId" : $scope.moveGroupevent.groupId,
					"companyId" : $scope.companyId,
					"groupStartDateTime" : document.getElementById("groupStartDateTime").value,
					"currentInventory" : $scope.moveGroupevent.currentInventory,					
					"remarks" : $scope.moveGroupevent.remarks,
					"phaseOfProductionTypeId" : $scope.moveGroupevent.phaseOfProductionTypeId,
					"fromMove" : true,
					"previousGroupId" : $scope.moveToAnotherGroup.previousGroupId,
					"weightInKgs" : $scope.moveGroupevent.weightInKgs,
					
				};				
			
			restServices.saveGroupEventInformation(postParam, function(data){
			console.log('Ankush');
			console.log(data);
				if(!data.error)
					{
					$modalInstance.close(data);					
					return data;
					}
				else
					{
						if(data.duplicateRecord)
						{
							$scope.groupEventDuplicateErrorMessage = true;
						}
						else
						{
						$scope.entryEventErrorMessage = true;
						}
					}
			});
		}
	}
	
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
});
