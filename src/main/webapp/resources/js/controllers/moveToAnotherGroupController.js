pigTrax.controller('moveToAnotherGroupCtrl', function($scope, $http, $window, $modalInstance,moveToAnotherGroup,restServices) {	
	$scope.moveToAnotherGroup = moveToAnotherGroup;
	$scope.phaseOfProductionType = moveToAnotherGroup.phaseOfProductionType;
	$scope.dateFormat = 'yyyy-mm-dd';
	$scope.moveGroupevent={};
	$scope.previousGroupId = moveToAnotherGroup.previousGroupId;
	$scope.groupDateError = false;
	$scope.groupdaterequiredMove = false;
	
	$scope.init = function(){
	var res2 = $http.get('rest/util/getPhaseOfProductionType?companyId='+$scope.companyId);
	res2.success(function(data, status, headers, config) {
		$scope.phaseOfProductionType = data.payload[0];	
		$scope.roomList = data.payload[1];
		$scope.barnList = data.payload[2]
		//$scope.phaseOfProductionTypeForNewAdd = data.payload[0];
		for( var x in $scope.phaseOfProductionType) {
			if( x == 1 || x == 3 )
				{		
				var obje = $scope.phaseOfProductionType[x];
				$scope.phaseOfProductionTypeForNewAdd[x] = $scope.phaseOfProductionType[x];
				}
			}
			console.log($scope.phaseOfProductionTypeForNewAdd);
	});
	
	res2.error(function(data, status, headers, config) {
		console.log( "failure message: " + {data: data});
	});
	
	
	}
	
	$scope.init();
	
	
	$scope.$watch("moveGroupevent.groupId", function(newValue, oldValue) {
		if (newValue === $scope.previousGroupId) {
			$scope.groupIdMatches = true;
		}
		else
		{
			$scope.groupIdMatches = false;
		}
	});
	
	
	$scope.addMoveGroupEvent = function() {
	
		var groupStartDateTimeAnother = document.getElementById("groupStartDateTimeAnother").value;
		console.log(groupStartDateTimeAnother);
		
		if($scope.groupIdMatches == true)
		{
			return false;
		}
		
		if($scope.closeGroupParentError)
		{
			return false;
		}
		
		if($scope.moveGroupeventAddForm.$valid)
		{
			if( groupStartDateTimeAnother === "")
			{
				$scope.groupdaterequiredMove = true;
				console.log($scope.groupdaterequiredMove);
				return;
			}
			else
			{
				$scope.groupdaterequiredMove = false;
			}
			if( groupStartDateTimeAnother < $scope.moveToAnotherGroup.groupStartDateTime)
			{
				$scope.groupDateError = true;
				console.log($scope.groupdaterequiredMove);
				return;
			}
			{
				$scope.groupDateError = false;
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
					"id" : $scope.moveGroupevent.id,
					
				};				
			
			restServices.saveGroupEventInformation(postParam, function(data){
				if(!data.error)
					{
					$modalInstance.close(data);		
					//alert("removalEvent?selectedCompany="+$scope.companyId);
					$window.location = "removalEvent?selectedCompany="+$scope.companyId;
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
	
	$scope.getGroupEventInformation = function (searchGroupEvent)
	{
		console.log('Group ID is '+ $scope.searchText);
		var postParam = {
				
				"groupId" : searchGroupEvent,
				"companyId" : $scope.companyId,
			};
		
		restServices.getGroupEventInformation(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
					//$scope.clearAllMessages();
					$scope.moveGroupevent = data.payload[0];
					$scope.moveGroupevent.groupStartDateTimeAnother = $scope.moveGroupevent.groupStartDateTime;
					$scope.moveGroupevent.currentInventory	= 0;	
					$scope.moveGroupevent.weightInKgs = 0;						
					$scope.moveGroupevent.remarks = '';
					$scope.closeGroupParentError = !$scope.moveGroupevent.active;
				}
			else
				{
					if(data.recordNotPresent)
					{
					$scope.moveGroupevent ={};
						$scope.searchDataErrorMessage = true;
					}
					
				}
		});
	}
	
	$scope.clearForm = function()
	{
		if(!$scope.moveGroupevent.searchExisting)
		{
			$scope.moveGroupevent ={};
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
