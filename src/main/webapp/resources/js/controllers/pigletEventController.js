var pigletEventController = pigTrax.controller('PigletEventController', function($scope,$rootScope, $http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.pigletEvent = {};
	$scope.confirmClick = false;
	$scope.farrowEventList = [];
	
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
	};
	
	//set the company id in root scope on page load
	$scope.loadPage = function(companyId)
	{
		$rootScope.companyId = companyId;	
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
						$('#searchFarrowEvents').modal('show');
						
						$scope.clearAllMessages();
						var searchFarrowEvent = {
								searchText : $scope.pigletEvent.pigId,
								searchOption : "PigId", 
								companyId : $rootScope.companyId
								
						};				
						restServices.getFarrowEventInformation(searchFarrowEvent, function(data){
							$scope.clearAllMessages();
							if(!data.error){
								$scope.farrowEvent = {};
								$scope.farrowEventList = data.payload;						
								
							}
							else
							{
								$scope.clearAllMessages();
								$scope.farrowEventList = [];
								
							}
						});
						
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
	
});


$(document).ready(function () { 
	$('input[class="icheck pigletevent"]').on('ifClicked', function (event) {
		angular.element("#PigletEventControllerId").scope().clearAllMessages();
		angular.element("#PigletEventControllerId").scope().$apply();
	});		
});