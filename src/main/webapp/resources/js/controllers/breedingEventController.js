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
		$scope.entryEventDuplicateErrorMessage = false;
		$scope.confirmClick = false;
		$scope.malePigIdentified = false;
		$scope.AddMatingDetailsForm = false;
		$scope.matingDetailsDeleteErrorMessage = false;
		$scope.entryEventDeleteErrorMessage = false;
		$scope.breedingEventValidation_ErrCode_DuplicateMatingDate = false;
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
		$scope.getPenList();
		
		//$scope.setupFormElements();
	};
		
	$scope.getPenList = function(){
		restServices.getPenListForCompany($rootScope.companyId, function(data){
			 if(!data.error)
			 {
				 $scope.penInfo = data.payload;
			 }
		});
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
	
	 
	$scope.getBreedingEventDetails = function()
	{
		
		if($scope.breedingEvent["id"] != null)
			{
			  restServices.getBreedingEventDetails($scope.breedingEvent["id"], function(data){
				  if(!data.error)
					  {
					    $scope.clearAllMessages();
					    $scope.breedingEvent = data.payload;
					  }
			  });
			}
	}
	
	
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
						companyId : $scope.companyId
						
				};
				restServices.getBreedingEventInformation(searchBreedEvent, function(data){
					$scope.clearAllMessages();
					if(!data.error){
						$scope.breedingEventList = data.payload;
					}
					else
					{
						$scope.breedingEventList = [];
						$scope.breedingEvent = {};						
						$scope.clearAllMessages();
						$scope.searchDataErrorMessage = true;
						$scope.AddMatingDetailsForm = false;
					}
				});
			}
	};
	
	
	$scope.confirmAddBreedingEvent = function()
	{
		if($scope.breedingeventform.$valid)
		{
			
			$scope.breedingEvent["companyId"] = $rootScope.companyId;
			//alert(JSON.stringify($scope.breedingEvent)); 
			restServices.saveBreedingEventInformation($scope.breedingEvent, function(data){
				if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.entryEventSuccessMessage = true;						
						//$scope.setupFormElements();
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
	
	
	$scope.confirmAddMatingDetails = function()
	{
		var matingDate = document.getElementById("matingDate").value;
		$scope.matingDetails["breedingEventId"] = $scope.breedingEvent["id"];
		$scope.matingDetails["matingDate"] =matingDate;
		
		//alert(JSON.stringify($scope.breedingEvent)); 
		restServices.saveMatingDetails($scope.matingDetails, function(data){
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.entryEventSuccessMessage = true;						
					$scope.AddMatingDetailsForm = false;
					$scope.getBreedingEventDetails();
				}
			else
				{
					$scope.clearAllMessages();
					if(data.duplicateRecord)
						$scope.entryEventDuplicateErrorMessage = true;
					else
						$scope.entryEventErrorMessage = true;
					
					$scope.AddMatingDetailsForm = true;
				}
				$window.scrollTo(0, 5);
		});
	}
	
	
	$scope.saveMatingDetails = function()
	{	
		
		if($scope.matingdetailsform.$valid)
		{
			if($scope.confirmClick)
			{
				$scope.confirmAddMatingDetails();
			}
			else
			{	
				var matingDate = document.getElementById("matingDate").value;
				$scope.matingDetails["breedingEventId"] = $scope.breedingEvent["id"];
				$scope.matingDetails["matingDate"] = matingDate;
				
				restServices.validateMatingDetails($scope.matingDetails, function(data){
			   		if(!data.error)
				   {
			   			var statusCode = data.payload;
					     $scope.clearAllMessages();
					     if(statusCode == "SUCCESS-00")
					     {
					    	     $scope.confirmAddMatingDetails();
					    }
					     else
					    {
					    	 if(statusCode == "ERR_BIRTHDATE_NOT_MATCHING")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_BirthDate = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 else if(statusCode == "ERR_ENTRYDATE_NOT_MATCHING")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_EntryDate = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 else if(statusCode == "ERR_CODE_DUPLICATE_DATE")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_DuplicateMatingDate = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 else if(statusCode == "WARN-01")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_WarnCode_1 = true;
						    	 $scope.confirmClick = true;						    	 
					    	 }
						     else if(statusCode == "WARN-02")
						     {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_WarnCode_2 = true;
						    	 $scope.confirmClick = true;
						     }
						     else if(statusCode == "ERR-01")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_1 = true;
						    	 $scope.confirmClick = false;	
						    	 }
						     else if(statusCode == "ERR-02")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_2 = true;
						    	 $scope.confirmClick = false;
						    	 }
						     else if(statusCode == "ERR-03")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_3 = true;
						    	 $scope.confirmClick = false;
						    	 }
						     else if(statusCode == "ERR-04")
					    	 {
					    	 $scope.clearAllMessages();
					    	 $scope.breedingEventValidation_ErrCode_4 = true;
					    	 $scope.confirmClick = false;
					    	 }
						     else if(statusCode == "ERR_GENERAL")
					    	 {
						    	 $scope.clearAllMessages();
					    	 $scope.entryEventErrorMessage = true;
					    	 $scope.confirmClick = false;
					    	 }
					    	 
					    	 $scope.AddMatingDetailsForm = true;
					    //	 $scope.setupFormElements(); 
					    	$window.scrollTo(0, 5);
					   	 }
				   }
				});
			
			 }
		}
	}
	
	
	
	$scope.addBreedingEvent = function(){
		$scope.confirmClick = true;
		if($scope.breedingeventform.$valid)
		{
			if($scope.confirmClick)
				{
				$scope.confirmAddBreedingEvent();
				} 
			else
			{
				
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
						    	 $scope.confirmClick = false;
					    	 }
					    	 if(statusCode == "ERR_ENTRYDATE_NOT_MATCHING")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_EntryDate = true;
						    	 $scope.confirmClick = false;
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
						    	 $scope.confirmClick = false;	
						    	 }
						     else if(statusCode == "ERR-02")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_2 = true;
						    	 $scope.confirmClick = false;
						    	 }
						     else if(statusCode == "ERR-03")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_3 = true;
						    	 $scope.confirmClick = false;
						    	 }
						     else if(statusCode == "ERR-04")
					    	 {
					    	 $scope.clearAllMessages();
					    	 $scope.breedingEventValidation_ErrCode_4 = true;
					    	 $scope.confirmClick = false;
					    	 }
						     else if(statusCode == "ERR_GENERAL")
					    	 {
						    	 $scope.clearAllMessages();
					    	 $scope.entryEventErrorMessage = true;
					    	 $scope.confirmClick = false;
					    	 }
					    	 
					    //	 $scope.setupFormElements(); 
					    	$window.scrollTo(0, 5);
					   	 }
				   }
				});
			
			 }
		}
	};
	
	
	/*$scope.setupFormElements = function()
	{
		if($scope.confirmClick)
			{
		$("#serviceId").attr("disabled","");
		$("#employeeGroupId").attr("disabled","");
		$("#pigInfoId").attr("disabled","");
		$("#pigInfoId").attr("disabled","");
		$("#sexType").attr("disabled","");
		$("#breedingGroupId").attr("disabled","");
		$("#popupcal").attr("disabled","");
		$("#semenId").attr("disabled","");
		$("#mateQuality").attr("disabled","");
		$("#remarks").attr("disabled","");
		$("#sowCondition").attr("disabled","");
			}
		else
			{
			$("#serviceId").attr("disabled",false);
			$("#employeeGroupId").attr("disabled",false);
			$("#pigInfoId").attr("disabled",false);
			$("#pigInfoId").attr("disabled",false);
			$("#sexType").attr("disabled",false);
			$("#breedingGroupId").attr("disabled",false);
			$("#popupcal").attr("disabled",false);
			$("#semenId").attr("disabled",false);
			$("#mateQuality").attr("disabled",false);
			$("#remarks").attr("disabled",false);
			$("#sowCondition").attr("disabled",false);
			}
	}*/
	
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
			
			if(data.error)
			{
				if(data.statusMessage == "ERROR : PREGCHECK-TRUE")
				{
					$scope.AddMatingDetailsForm = false;
					$scope.clearAllMessages();
					$scope.entryEventDeleteErrorMessage = true; 
				}
			}
			else
			{
				$scope.clearAllMessages();
				$scope.entryEventDeleteMessage = true;
				$scope.breedingEvent = {};			
				$scope.getBreedingEventInformation();
				$window.scrollTo(0, 5);
			}
			
		});
			
	};
	
	$scope.deleteMatingDetails = function(matingDetailsObj)
	{	
		restServices.deleteMatingDetails(matingDetailsObj, function(data){
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.entryEventDeleteMessage = true; 
					$scope.AddMatingDetailsForm = false;					
					$scope.getBreedingEventDetails(); 
					$window.scrollTo(0, 5);
				}
			else
				{
					if(data.statusMessage == "ERROR : PREGCHECK-TRUE")
					{
						$scope.AddMatingDetailsForm = false;
						$scope.clearAllMessages();
						$scope.matingDetailsDeleteErrorMessage = true; 
					}
				}
		});
			
	};
	
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.breedingEvent = {};
		$scope.changeText();
		//$scope.setupFormElements();
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
				if(pigInfo.sexTypeId == 2)
					{
						$scope.breedingEvent["pigBirthDate"] = pigInfo.birthDate;						
					}
				else
					{
						$scope.clearAllMessages();
						$scope.malePigIdentified = true;
					}
			}
				
		});
	};
	
	
	$scope.validateBreedingDate = function()
	{
		var birthDate = $scope.breedingEvent.pigBirthDate;
		var breedingDate = $scope.breedingEvent.breedingDate;
		
	}
	
	$scope.getBreedingEventDetailsObj = function(breedingEventObj) 
	{
		$scope.AddMatingDetailsForm = false;
		$scope.breedingEvent = breedingEventObj;
	}
	
	$scope.addMatingDetailData = function()
	{
		$scope.clearAllMessages();
		$scope.AddMatingDetailsForm = true;
		$scope.matingDetails = {};
		$scope.matingDetails["breedingEventId"] = $scope.breedingEvent["id"];
	}
	
	
}); 

$(document).ready(function () { 
	$('input[class="icheck breedingevent"]').on('ifClicked', function (event) {
		angular.element("#BreedingEventControllerId").scope().clearAllMessages();
		angular.element("#BreedingEventControllerId").scope().$apply();
	});		
});