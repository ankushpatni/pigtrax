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
		$scope.breedingEventValidation_WarnCode_1 = false;
		$scope.breedingEventValidation_WarnCode_2 = false;
		$scope.breedingEventValidation_ErrCode_1 = false;
		$scope.breedingEventValidation_ErrCode_2 = false;
		$scope.breedingEventValidation_ErrCode_3 = false;
		$scope.breedingEventValidation_ErrCode_BirthDate = false;
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
			//alert(JSON.stringify($scope.breedingEvent)); 
			restServices.saveBreedingEventInformation($scope.breedingEvent, function(data){
				if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.entryEventSuccessMessage = true;
						$scope.breedingEvent = {};
						$scope.changeText();
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
					     if(statusCode == "SUCCESS-00")
					     {
					    	   $scope.breedingEvent["gestationRecord"] = true; 
					    	   $scope.confirmAddBreedingEvent();
					    }
					     else
					    {
					    	 if(statusCode == "ERR_BIRTHDATE_NOT_MATCHING")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_BirthDate = true;
					    	 }
					    	 else if(statusCode == "WARN-01")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_WarnCode_1 = true;
						    	 $scope.breedingEvent["gestationRecord"] = false; 
						    	 $scope.confirmClick = true;
					    	 }
						     else if(statusCode == "WARN-02")
						     {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_WarnCode_2 = true;
						    	 $scope.breedingEvent["gestationRecord"] = true; 
						    	 $scope.confirmClick = true;
						     }
						     else if(statusCode == "ERR-01")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_1 = true;
						    	 }
						     else if(statusCode == "ERR-02")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_2 = true;
						    	 }
						     else if(statusCode == "ERR-03")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_3 = true;
						    	 }
						     else if(statusCode == "ERR-04")
					    	 {
					    	 $scope.clearAllMessages();
					    	 $scope.breedingEventValidation_ErrCode_4 = true;
					    	 }
						     else if(statusCode == "ERR_GENERAL")
					    	 {
						    	 $scope.clearAllMessages();
					    	 $scope.entryEventErrorMessage = true;
					    	 }
					    	 
					    	 
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
		$scope.changeText();
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
				restServices.getGestationRecord(pigInfo.id, function(data){
					if(!data.error)
						{
						   var gestationRecord  = data.payload;
						   $scope.breedingEvent["gestationRecordDate"] = gestationRecord.breedingDate;
						}
				});
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