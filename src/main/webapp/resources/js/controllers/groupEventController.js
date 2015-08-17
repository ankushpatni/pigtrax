var breedingEventController = pigTrax.controller('GroupEventController', function($scope,$rootScope, $http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.groupEvent = {};
	$scope.confirmClick = false;
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		
	};
	
	$scope.clearAllMessages = function()
	{
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.groupdaterequired = false;
		$scope.groupEventDuplicateErrorMessage = false;		
	};
	
	$scope.addGroupEvent = function()
	{
	console.log(document.getElementById("groupStartDateTime").value);
		if(document.getElementById("groupStartDateTime").value === "")
		{
			$scope.groupdaterequired = true;
			console.log($scope.groupdaterequired);
			return;
		}
		if($scope.groupEventForm.$valid)
		{
		
			var postParam = {
			
					"groupId" : $scope.groupEvent.groupId,
					"companyId" : $rootScope.companyId,
					"groupStartDateTime" : document.getElementById("groupStartDateTime").value,
					"groupCloseDateTime" : document.getElementById("groupCloseDateTime").value,					
					"remarks" : $scope.groupEvent.remark,
				};
			
			restServices.saveGroupEventInformation(postParam, function(data){
				if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.entryEventSuccessMessage = true;
						$scope.groupEvent = {};
					}
				else
					{
						$scope.clearAllMessages();
						console.log(data.duplicateRecord);
						if(data.duplicateRecord)
						{
							$scope.groupEventDuplicateErrorMessage = true;
						}
						else
						{
						$scope.entryEventErrorMessage = true;
						}
					}
					$window.scrollTo(0, 5);  
			});
		}
	}
	
	$scope.changeGroupEventStatus = function()
	{
		console.log('change group event status');
	}
	
	$scope.getGroupEventInformation = function ()
	{
		console.log('Group ID is '+ $scope.searchText);
		
		restServices.getGroupEventInformation($scope.searchText, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.clearAllMessages();					
				}
			else
				{
					if(data.recordNotPresent)
					{
						$scope.searchDataErrorMessage = true;
					}
					else
					{
						$scope.entryEventErrorMessage = true;
					}
				}
				$window.scrollTo(0, 5);  
		});
	}

});