var breedingEventController = pigTrax.controller('BreedingEventController', function($scope,$rootScope, $http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.breedingEvent = {};
	$scope.confirmClick = false;
	
	$scope.clearAllMessages = function()
	{
		$scope.searchDataErrorMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventDeleteMessage = false;
		$scope.searchErrorMessage = false;
		$scope.inValidPigIdFromServer = false;
		$scope.breedingEventValidation_Success = false;
		$scope.breedingEventValidation_ErrCode_1 = false;
		$scope.breedingEventValidation_ErrCode_2 = false;
		$scope.breedingEventValidation_ErrCode_3 = false;
		$scope.breedingEventValidation_ErrCode_4 = false;
		$scope.breedingEventValidation_ErrCode_5 = false;
		$scope.confirmClick = false;
	};
	
	
	$scope.$watch("selectedEmployeeGroup", function(newValue, oldValue) {
		
		if (newValue != null && newValue != undefined) {
			$scope.selectedEmployeeGroup = newValue;
			
		}
	});
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		
	};
		
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
		var option = "";
		if(document.getElementById("rad1").checked)
			 option = document.getElementById("rad1").value;
		 else if(document.getElementById("rad2").checked)
			 option = document.getElementById("rad2").value;
		 else if(document.getElementById("rad3").checked)
			 option = document.getElementById("rad3").value;
		
		if($scope.searchText == undefined || $scope.searchText == ""|| option == "")
		{
			   $scope.clearAllMessages();
			   $scope.searchErrorMessage = true;
		}
		else
			{
				var searchBreedEvent = {
						searchText : $scope.searchText,
						searchOption : option,
						serviceId : $scope.searchText,
						companyId : $scope.companyId
						
				};
				restServices.getBreedingEventInformation(searchBreedEvent, function(data){
					$scope.clearAllMessages();
					if(!data.error){
						$scope.breedingEventList = data.payload;	
						document.getElementById("breedingDate").value = $scope.breedingEvent.breedingDate;
					}
					else
					{
						$scope.breedingEventList = [];
						$scope.breedingEvent = {};						
						$scope.clearAllMessages();
						$scope.searchDataErrorMessage = true;
						
					}
				});
			}
	};
	
	
	$scope.confirmAddBreedingEvent = function()
	{
		if($scope.breedingeventform.$valid)
		{
			var breedingDate = document.getElementById("breedingDate").value;
			$scope.breedingEvent["breedingDate"] = breedingDate;
			$scope.breedingEvent["companyId"] = $rootScope.companyId;
			restServices.saveBreedingEventInformation($scope.breedingEvent, function(data){
				if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.entryEventSuccessMessage = true;
						$scope.breedingEvent = {};
						if($scope.breedingEventList != null && $scope.breedingEventList.length > 0)
							$scope.getBreedingEventInformation();
					}
				else
					{
						$scope.clearAllMessages();
						if(data.duplicateRecord)
							$scope.entryEventDuplicateErrorMessage = true;
						else
							$scope.entryEventErrorMessage = true;
					}
					$window.scrollTo(0, 5);
			});
		}
	}
	
	
	$scope.addBreedingEvent = function(){
		if($scope.breedingeventform.$valid)
		{
			if($scope.confirmClick)
				{
				$scope.confirmAddBreedingEvent();
				} 
			else
			{
				var breedingDate = document.getElementById("breedingDate").value;
				$scope.breedingEvent["breedingDate"] = breedingDate;
				$scope.breedingEvent["companyId"] = $rootScope.companyId;
				restServices.validateBreedingEvent($scope.breedingEvent, function(data){
			   		if(!data.error)
				   {
			   			var statusCode = data.payload;
					     $scope.clearAllMessages();
					     if(statusCode == 0)
					     {
					    		$scope.confirmAddBreedingEvent();
					    }
					     else
					    {
					    	 if(statusCode == 1)
						    	 $scope.breedingEventValidation_ErrCode_1 = true;
						     else if(statusCode == 2)
						    	 $scope.breedingEventValidation_ErrCode_2 = true;
						     else if(statusCode == 3)
						    	 $scope.breedingEventValidation_ErrCode_3 = true;
						     else if(statusCode == 4)
						    	 $scope.breedingEventValidation_ErrCode_4 = true;
						     else if(statusCode == 5)
						    	 $scope.breedingEventValidation_ErrCode_5 = true;
					    	 $scope.confirmClick = true;
					    	$window.scrollTo(0, 5);
					   	 }
				   }
				});
			
			 }
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
			$scope.getBreedingEventInformation();
			$window.scrollTo(0, 5);
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
		
	};
	
	/**
	 * check the validity of pigId
	 */
	$scope.checkForPigId = function()
	{
	    var pigInfo = {
				searchText : $scope.breedingEvent.pigInfoId,
				searchOption : "pigId",
				companyId : $rootScope.companyId
		};
		restServices.getPigInformation(pigInfo, function(data) {
			if(data.error)
			{
				$scope.clearAllMessages();
				$scope.inValidPigIdFromServer = true;
			}
			else
			{
				$scope.inValidPigIdFromServer = false;
			  var pigInfo = data.payload;
			  $scope.breedingEvent["pigBirthDate"] = pigInfo.birthDate;
			}
				
		});
	};
	
	
	$scope.validateBreedingDate = function()
	{
		var birthDate = $scope.breedingEvent.pigBirthDate;
		var breedingDate = $scope.breedingEvent.breedingDate;
		
	}
	
	$scope.getBreedingEventDetails = function(breedingEventObj) 
	{
		$scope.breedingEvent = breedingEventObj;
	}
	
}); 