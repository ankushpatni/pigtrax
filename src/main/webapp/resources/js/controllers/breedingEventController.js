pigTrax.controller('BreedingEventController', function($scope,$rootScope, $http,$window,restServices) {
	
	$scope.companyId = "";
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	
	$scope.clearAllMessages = function()
	{
		$scope.searchDataErrorMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventDeleteMessage = false;
		$scope.searchErrorMessage = false;
	};
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		
	};
	
	$scope.$watch('selectedEmployeeGroup', function() {
	       alert("group selected");
	       $scope.breedingEvent.employeeGroupId = $rootScope.selectedEmployeeGroup.id;
	   });

	
	$scope.getBreedingServiceType = function()
	{
		restServices.getBreedingServiceType(function(data){
			if(!data.error){
				$scope.breedingServiceTypes = data.payload;
			}
		});
	}
	$scope.getBreedingServiceType();
	
	$scope.getBreedingEventInformation = function()
	{
		
		if($scope.searchText == undefined || $scope.searchText == "")
		{
			   $scope.clearAllMessages();
			   $scope.searchErrorMessage = true;
		}
		else
			{
				var searchBreedEvent = {
						serviceId : $scope.searchText,
						companyId : $scope.companyId
						
				};
				
				restServices.getBreedingEventInformation(searchBreedEvent, function(data){
					
					if(!data.error){
						$scope.breedingEvent = data.payload;						
					}
					else
					{
						$scope.breedingEvent = {};
						$scope.clearAllMessages();
						$scope.searchDataErrorMessage = true;
						
					}
				});
			}
	};
	
	
	$scope.addBreedingEvent = function(){
		if($scope.breedingeventform.$valid)
		{
			var breedingDate = document.getElementById("breedingDate").value;
			$scope.breedingEvent["breedingDate"] = breedingDate;
			restServices.saveBreedingEventInformation($scope.breedingEvent, function(data){
				if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.entryEventSuccessMessage = true;
					}
				else
					{
						$scope.clearAllMessages();
						$scope.entryEventErrorMessage = true;
					}
			});
		}
	};
	
	$scope.getEmployeeGroups = function()
	{
		restServices.getEmployeeGroups($scope.companyId, function(data) {
			if(!data.error)
				{
				   $scope.employeeGroups = data.payload;
				}
		});
	};
	
	$scope.deleteBreedingEventInfo = function()
	{	
		restServices.deleteBreedingEventInfo($scope.breedingEvent.id, function(data){
			$scope.clearAllMessages();
			$scope.entryEventDeleteMessage = true;
			$scope.breedingEvent = {};
		});
			
	};
	
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.breedingEvent = {};
	}
	
	$scope.viewEmployeeGroup = function()
	{
		$rootScope.viewAddForm = false;
		alert($rootScope.viewAddForm);
	};
});