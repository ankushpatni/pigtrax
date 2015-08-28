pigTrax.controller('moveToAnotherGroupCtrl', function($scope, $http, $window, $modalInstance,moveToAnotherGroup,restServices) {	
	$scope.moveToAnotherGroup = moveToAnotherGroup;
	$scope.phaseOfProductionType = moveToAnotherGroup.phaseOfProductionType;
	$scope.dateFormat = 'yyyy-mm-dd';
	
	
	$scope.addMoveGroupEvent = function() {
	
		var groupStartDateTimeAnother = document.getElementById("groupStartDateTimeAnother").value;
		console.log(groupStartDateTimeAnother);
		
		if($scope.moveGroupeventAddForm.$valid)
		{
			if( groupStartDateTimeAnother === "")
			{
				$scope.groupdaterequiredMove = true;
				console.log($scope.groupdaterequiredMove);
				return;
			}
			if( groupStartDateTimeAnother < $scope.moveToAnotherGroup.groupStartDateTime)
			{
				$scope.groupdaterequiredMove = true;
				console.log($scope.groupdaterequiredMove);
				return;
			}
			if($scope.moveGroupevent.currentInventory > $scope.moveToAnotherGroup.pigCount)
			{
				console.log($scope.moveGroupevent.currentInventory );
				$scope.noOfPigsCanBeTransfered = true;
				return;
			}
			
			var postParam = {
			
					"groupId" : $scope.moveGroupevent.groupId,
					"companyId" : $scope.companyId,
					"groupStartDateTime" : document.getElementById("groupStartDateTimeAnother").value,
					"currentInventory" : $scope.moveGroupevent.currentInventory,					
					"remarks" : $scope.moveGroupevent.remarks,
					"phaseOfProductionTypeId" : $scope.moveGroupevent.phaseOfProductionTypeId,
					"fromMove" : true,
					"previousGroupId" : $scope.moveToAnotherGroup.previousGroupId,
					"weightInKgs" : $scope.moveGroupevent.weightInKgs,
					
				};				
			
			restServices.saveGroupEventInformation(postParam, function(data){
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
	
	$scope.open = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.opened = true;
  };
	
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
});
