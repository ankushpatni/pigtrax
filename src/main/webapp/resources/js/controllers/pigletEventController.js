var pigletEventController = pigTrax.controller('PigletEventController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.pigletEvent = {};
	$scope.confirmClick = false;
	$scope.farrowEventList = [];
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
		$scope.farrowEventValidation_ErrCode_1 = false;
		$scope.pigletsAdded = false;
		$scope.malePigIdentified = false;		
		$scope.invalidLitterId = false;
	};
	
	
	$scope.loadPremises = function()
	{
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId);
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	//set the company id in root scope on page load
	$scope.loadPage = function(companyId)
	{
		$rootScope.companyId = companyId;
		$scope.loadPremises();
	};
	
	/**
     * Search for the farrow events
     */
    $scope.searchFarrowEvent = function()
	{	$scope.clearAllMessages();
		if($scope.pigletEvent.pigId != undefined && $scope.pigletEvent.pigId != "")
			{
			    var pigInfo = {
						searchText : $scope.pigletEvent.pigId,
						searchOption : "pigId",
						companyId : $rootScope.companyId
				};
				restServices.getPigInformation(pigInfo, function(data) {
					if(data.error)
					{
						$scope.clearAllMessages();
						$scope.inValidPigIdFromServer = true;		
						$scope.pigletStatusEvent = {};
					}	
					else
					{
						$scope.inValidPigIdFromServer = false;
						var pigInfo = data.payload;
						if(pigInfo.sexTypeId == 1)
						{
							$scope.clearAllMessages();
							$scope.malePigIdentified = true;			
						}
						else
						{
							$scope.clearAllMessages();
							$scope.malePigIdentified = false;		
						}
					
					}
				});
			}
		else
			{
			$scope.clearAllMessages();
			$scope.requiredPigIdMessage = true;
			
			}
		
	}; 
	
	
	
	$scope.selectFarrowEvent = function()
	{
		$scope.clearAllMessages(); 
		$scope.pigletEvent.farrowEventId = $scope.pigletEvent.farrowEventDto.id;
		$('#searchFarrowEvents').modal('hide');		
	}
	
	
    /**
     * Search for the Piglet events
     */
    $scope.getPigletEventInformation = function()
	{		
    	var option = "";
		if(document.getElementById("pigId").checked)
			 option = document.getElementById("pigId").value;
		 else if(document.getElementById("pigletTattooId").checked)
			 option = document.getElementById("pigletTattooId").value;
    	
		if($scope.searchText == undefined || $scope.searchText == "" || option == "")
		{
			   $scope.clearAllMessages();
			   $scope.searchErrorMessage = true;
		}
		else
		{
			var searchPigletEvent = {
					searchText : $scope.searchText,
					searchOption : option,
					companyId : $rootScope.companyId
					
			};				
			restServices.getPigletEventInformation(searchPigletEvent, function(data){
				$scope.clearAllMessages();
				if(!data.error){
					$scope.pigletEvent = {};
					$scope.pigletEventList = data.payload;						
				}
				else
				{
					$scope.pigletEvent = {};
					$scope.clearAllMessages(); 
					$scope.searchDataErrorMessage = true;
					$scope.pigletEventList = [];
					
				}
			});
		}
	};
	
	
	$scope.getPigletEventDetails = function(pigletEventObj)
	{
		//alert("came here : "+JSON.stringify(pregnancyEventObj));
		$scope.pigletEvent["pigletId"] = pigletEventObj["pigletId"]; 
		$scope.pigletEvent["farrowEventDto"] = pigletEventObj["farrowEventDto"];
		$scope.pigletEvent["farrowEventId"] = pigletEventObj.farrowEventDto.id;
		$scope.pigletEvent["tattooId"] = pigletEventObj["tattooId"];
		$scope.pigletEvent["pigId"] = pigletEventObj["pigId"];
		$scope.pigletEvent["weightAtBirth"] = pigletEventObj["weightAtBirth"];
		$scope.pigletEvent["weightAtWeaning"] = pigletEventObj["weightAtWeaning"];
		$scope.pigletEvent["premiseId"] = pigletEventObj["premiseId"];
		$scope.pigletEvent["litterId"] = pigletEventObj["litterId"];
	}
	
	/**
	 * Delete a pregnancy event by id
	 */
	$scope.deletePigletEvent = function()
	{
		restServices.deletePigletEvent($scope.pigletEvent.pigletId, function(data){
			if(!data.error)
				{
				$scope.clearAllMessages();
				$scope.entryEventDeleteMessage = true;
				$scope.pigletEvent = {};
				$scope.updateSearchResults();
				$window.scrollTo(0, 5);
				}
		});
	}
	
	
	$scope.updateSearchResults = function(id)
	{
		var pigletEvents = $scope.pigletEventList;
		for (var i = 0; i < pigletEvents.length; i++) { 
			if(pigletEvents[i].id == id)
				$scope.pigletEventList.splice(i, 1); 
		}
	}
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.farrowEvent = {};
	} 
	
	/**
	 * To add a farrow event
	 */
	$scope.addPigletEvent = function()
    {
		if($scope.pigleteventform.$valid)
		{
			
			$scope.pigletEvent["companyId"] = $rootScope.companyId;			
			
			restServices.savePigletEventInformation($scope.pigletEvent, function(data){
				if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.entryEventSuccessMessage = true;
						$scope.pigletEvent = {};
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
    };
    
    
    $scope.checkForLitterId = function()
    {
    	$scope.pigletEvent["companyId"] = $rootScope.companyId;
    	restServices.checkForLitterId($scope.pigletEvent, function(data){
			if(data.error)
				{
					$scope.invalidLitterId = true;
				}
			else
				{
					$scope.invalidLitterId = false;
				}
		});
    }
	
});


$(document).ready(function () { 
	$('input[class="icheck pigletevent"]').on('ifClicked', function (event) {
		angular.element("#PigletEventControllerId").scope().clearAllMessages();
		angular.element("#PigletEventControllerId").scope().$apply();
	});		
});