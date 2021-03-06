var pregnancyEventController = pigTrax.controller('PregnancyEventController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.pregnancyEvent = {};
	$scope.confirmClick = false;
	$scope.breedingEventList = [];
	$scope.DateUtils = DateUtils;
	
	$scope.clearAllMessages = function()
	{
		$scope.searchDataErrorMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventDeleteMessage = false;
		$scope.searchErrorMessage = false;
		$scope.inValidPigIdFromServer = false;
		$scope.confirmClick = false;
		$scope.requiredPigIdMessage = false;
		$scope.inValidServiceIdFromServer = false;
		$scope.pregnancyEventValidation_ErrCode_1 = false;
		$scope.pregnancyEventValidation_ErrCode_2 = false;
		$scope.pregnancyEventValidation_ErrCode_3 = false;
		$scope.pregnancyEventValidation_ErrCode_4 = false;
		$scope.pregnancyEventValidation_ErrCode_5 = false;
		$scope.malePigIdentified = false;
		$scope.resultDateRequired = false;
		$scope.invalidResultDate = false;
		$scope.breedingEventIdRequired = false;
		$scope.matchingServiceRecordNotFound = false;
		$scope.pigIdRequired = false;
	};
	
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		   //var  dateObj = Date.parse(dateVal);
			 var  dateObj = DateUtils.parse(dateVal,"dd/MM/yyyy");
		   if(dateObj == null)
			{
			   if(fieldName == "resultDate")
				{
					   $scope.resultDateRequired = true;
					   $scope.pregnancyEvent["resultDate"] = null;
				}			   
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "resultDate")
				{
				   $scope.resultDateRequired = false;
				   $scope.pregnancyEvent["resultDate"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			  
			}
		}
		else
		{
			if(fieldName == "resultDate")
			{
				 $scope.resultDateRequired = true;
				   $scope.pregnancyEvent["resultDate"] = null;
			}
		}
	  }
	}
	
	
	
	
	
	$scope.loadPremises = function()
	{
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId+'&premisesType=1,6,8');
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.loadPigInfo = function()
	{
		var res = $http.get('rest/entryEvent/getPigInfoList?companyId='+$rootScope.companyId+'&premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
		console.log(data.payload);
			$scope.pigInfoListSearch = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.loadPage = function(companyId, selectedPregnancyEventId)
	{
		$scope.setCompanyId(companyId);
		
		$scope.getPregnancyEventTypes();
		
		$scope.getPregnancyExamResultTypes();
		
		if(selectedPregnancyEventId != null && selectedPregnancyEventId != undefined && selectedPregnancyEventId != "")
		{
			restServices.getPregnancyEventDetailsById(selectedPregnancyEventId, function(data){
				  if(!data.error)
				  {
				    $scope.clearAllMessages();
				    $scope.pregnancyEvent = data.payload;
				    
				  }
			  });
		}
		$scope.loadPremises();
	};
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		
	};
	
	$scope.getPregnancyEventTypes = function()
	{
		restServices.getPregnancyEventTypes(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.pregnancyEventTypeKeys = responseList[0];
				$scope.pregnancyEventTypes = responseList[1];
			}
		});
	}
	
	
	
	$scope.getPregnancyExamResultTypes = function()
	{
		restServices.getPregnancyExamResultTypes(function(data)
		{
			if(!data.error){
				
				var responseList = data.payload;
				$scope.pregnancyExamResultTypeKeys = responseList[0];
				$scope.pregnancyExamResultTypes = responseList[1];
			}
		});
	}
	
	/**
	 * check the validity of pigId
	 */
	$scope.checkForPigId = function()
	{
		if($scope.pregnancyEvent.pigId != undefined && $scope.pregnancyEvent.pigId != "" && $scope.pregnancyEvent.premiseId != "" && $scope.pregnancyEvent.premiseId != undefined)
			{
			    var pigInfo = {
						searchText : $scope.pregnancyEvent.pigId,
						searchOption : "pigId",
						companyId : $rootScope.companyId,
						selectedPremise : $scope.pregnancyEvent.premiseId
				};
				restServices.getPigInformation(pigInfo, function(data) {
					if(data.error)
					{
						$scope.clearAllMessages();
						$scope.inValidPigIdFromServer = true;		
						return false;
						
					}
					else
					{
						$scope.inValidPigIdFromServer = false;
						$scope.requiredPigIdMessage = false;
						var pigInfo = data.payload;
						if(pigInfo.sexTypeId == 2)
						{
							$scope.clearAllMessages();
							$scope.malePigIdentified = false;
							return true;
						}
						else
						{
							$scope.clearAllMessages();
							$scope.malePigIdentified = true;
							return false
						}
					}
						
				});
			}
	};
	
	
	$scope.$watch('inValidPigIdFromServer', function(newVal, oldVal) {
		
        if(newVal)
		{
        	$('#searchBreedingService').modal('hide');
		}
    });
    
	
	
		
	
	/**
	 * To add a pregnancy event
	 */
	$scope.addPregnancyEvent = function()
    {
		var examDate = $scope.pregnancyEvent["resultDate"];
		
		var resultDate = $scope.pregnancyEvent["resultDate"];
		
		if($scope.pregnancyEvent["pigId"] == undefined  || $scope.pregnancyEvent["pigId"] == "")		{					
			$scope.requiredPigIdMessage = true;	
			
		}	
		else{
			$scope.requiredPigIdMessage = false;	
			
		}
		
		if(resultDate == null || resultDate == undefined || resultDate == "")
		{
			$scope.resultDateRequired = true;
		}	
		else if(examDate != null && examDate >  resultDate)
		{
		   $scope.invalidResultDate = true;	
		}	
		if($scope.pregnancyeventform.$valid && !$scope.breedingEventIdRequired && !$scope.resultDateRequired && !$scope.invalidResultDate && !$scope.requiredPigIdMessage)
		{
			
			
			var examDateVal = new Date(examDate);
			examDate = DateUtils.convertLocaleDateToServer(examDateVal);
			
			var resultDateVal = new Date(resultDate);
			resultDate = DateUtils.convertLocaleDateToServer(resultDateVal);
			
			$scope.pregnancyEvent["examDate"] = examDate;
			$scope.pregnancyEvent["resultDate"] = resultDate;
			$scope.pregnancyEvent["companyId"] = $rootScope.companyId;
			
			restServices.validatePregnancyEvent($scope.pregnancyEvent, function(data){
				if(!data.error)
				{
				   $scope.clearAllMessages();
				   var statusCode = data.payload;
				   if(statusCode == 0)
				   {   
						//alert(JSON.stringify($scope.pregnancyEvent));
						restServices.savePregnancyEventInformation($scope.pregnancyEvent, function(data){
							if(!data.error)
								{
									$scope.clearAllMessages();
									$scope.entryEventSuccessMessage = true;
									$scope.pregnancyEvent = {};
									$scope.changeText();
								}
							else
								{
									$scope.clearAllMessages();
									if(data.statusMessage == "ERR:INVALID-SERVICE")
										$scope.matchingServiceRecordNotFound = true;
									else										
										$scope.entryEventErrorMessage = true;
								}
								$window.scrollTo(0, 5);  
						});
				   }
				   else if(statusCode == 1)
					   $scope.pregnancyEventValidation_ErrCode_1 = true;
				   else if(statusCode == 2)
					   $scope.pregnancyEventValidation_ErrCode_2 = true;
				   else if(statusCode == 3)
					   $scope.pregnancyEventValidation_ErrCode_3 = true;
				   else if(statusCode == 4)
					   $scope.pregnancyEventValidation_ErrCode_4 = true;
				   else if(statusCode == 5)
					   $scope.pregnancyEventValidation_ErrCode_5 = true;
				   
				   $window.scrollTo(0, 5);  
				}
			});
			
			
		}
    };
    
    
    /**
     * Search for the pregnancy events
     */
    $scope.getPregnancyEventInformation = function()
	{		
    	var option = "";
		if(document.getElementById("rad1").checked)
			 option = document.getElementById("rad1").value;
		 else if(document.getElementById("rad2").checked)
			 option = document.getElementById("rad2").value;
    	
		if($scope.searchText == undefined || $scope.searchText == "" || option == "" || $scope.selectedPremise == null || $scope.selectedPremise == "")
		{
			   $scope.clearAllMessages();
			   $scope.searchErrorMessage = true;
		}
		else
		{
			var searchPregnancyEvent = {
					searchText : $scope.searchText,
					searchOption : option,
					pigId : $scope.searchText, 
					companyId : $scope.companyId,
					selectedPremise : $scope.selectedPremise
					
			};				
			restServices.getPregnancyEventInformation(searchPregnancyEvent, function(data){
				$scope.clearAllMessages();
				if(!data.error){
					$scope.pregnancyEvent = {};
					$scope.pregnancyEventList = data.payload;						
				}
				else
				{
					$scope.pregnancyEvent = {};
					$scope.clearAllMessages(); 
					$scope.searchDataErrorMessage = true;
					$scope.pregnancyEventList = [];
					
				}
			});
		}
	};
	
	
	$scope.getPregnancyEventDetails = function(pregnancyEventObj)
	{
		//alert("came here : "+JSON.stringify(pregnancyEventObj));
		$scope.pregnancyEvent = pregnancyEventObj;
	}
	
	/**
	 * Delete a pregnancy event by id
	 */
	$scope.deletePregnancyEvent = function()
	{
		restServices.deletePregnancyEvent($scope.pregnancyEvent.id, function(data){
			if(!data.error)
				{
				$scope.clearAllMessages();
				$scope.entryEventDeleteMessage = true;
				$scope.pregnancyEvent = {};
				$scope.getPregnancyEventInformation();
				$window.scrollTo(0, 5);
				}
		});
	}
	
	
	$scope.updateSearchResults = function(id)
	{
		var pregnancyEvents = $scope.pregnancyEventList;
		for (var i = 0; i < pregnancyEvents.length; i++) { 
			if(pregnancyEvents[i].id == id)
				$scope.pregnancyEventList.splice(i, 1); 
		}
	}
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.pregnancyEvent = {};
		$scope.changeText();
	}
	
	
	$scope.searchBreedingService = function(pigId, selectedCompanyId)
	{		
		if(pigId == undefined  || pigId == "")
		{		
			$scope.clearAllMessages();
			$scope.requiredPigIdMessage = true;
			$('#searchBreedingService').modal('hide');
		}		
		else if(pigId != undefined && pigId != "" && $scope.pregnancyEvent.premiseId != undefined && $scope.pregnancyEvent.premiseId != "")
		{
		    var pigInfo = {
					searchText : $scope.pregnancyEvent.pigId,
					searchOption : "pigId",
					companyId : $rootScope.companyId,
					selectedPremise : $scope.pregnancyEvent.premiseId
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
					$scope.requiredPigIdMessage = false;
					var pigInfo = data.payload;
					if(pigInfo.sexTypeId == 2)
					{
						$scope.clearAllMessages();
						/*$("#searchBreedingService").modal("show");
						
						var searchBreedEvent = {
								searchText : pigId,
								searchOption : "pigId",
								companyId : $rootScope.companyId
								
						};
						
						restServices.getActiveBreedingServices(searchBreedEvent, function(data){
							$scope.clearAllMessages();
							 $("#searchBreedingService").modal("show");
							if(!data.error){
								$scope.breedingEventList = data.payload;
							}
							else
							{
								$scope.breedingEventList = [];  
							}
						});*/
					}
					else
					{
						$scope.clearAllMessages();
						$scope.malePigIdentified = true;
					}
				}
					
			});
		}
	};
	
	$scope.selectBreedingEventService = function()
	{
		$scope.pregnancyEvent.breedingServiceId = $scope.pregnancyEvent.breedingEventDto.serviceId;
		$scope.pregnancyEvent.breedingEventId = $scope.pregnancyEvent.breedingEventDto.id;
		$('#searchBreedingService').modal('hide');
	}
	
	
	$scope.changePregnancyEventType = function()
	{		
		if($scope.pregnancyEvent.pregnancyEventTypeId != 1)
		{
			$scope.pregnancyEvent["examDate"] = null;
			$scope.pregnancyEvent["pregnancyExamResultTypeId"] = null; 
		}
		
	}
	
	
	$scope.goToBreedingEvent = function()
	{
		document.getElementById("selectedCompany").value = $rootScope.companyId;
		document.getElementById("selectedBreedingEventId").value = $scope.pregnancyEvent.breedingEventId;
		document.getElementById("prevBreedingEventForm").submit();
	}
	
	
});


$(document).ready(function () { 
	$('input[class="icheck pregnancyevent"]').on('ifClicked', function (event) {
		angular.element("#PregnancyEventControllerId").scope().clearAllMessages();
		angular.element("#PregnancyEventControllerId").scope().$apply();
	});		
});