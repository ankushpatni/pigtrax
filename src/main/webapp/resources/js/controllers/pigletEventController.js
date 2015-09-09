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
	 * check the validity of pigId
	 */
	$scope.checkForFarrowId = function()
	{
		if($scope.pigletEvent.farrowId != undefined && $scope.pigletEvent.farrowId != "")
			{
			    var farrowEventDetails = {
						searchText : $scope.pigletEvent.farrowId,
						searchOption : "farrowId",
						companyId : $rootScope.companyId
				};
				restServices.getFarrowEventDetails(farrowEventDetails, function(data) {
					if(data.error)
					{
						$scope.clearAllMessages();
						$scope.inValidPigIdFromServer = true;				
					}
					else 
					{ 
						var dto = data.payload;
						if(dto.pigletsAdded)
						{
							
							$scope.clearAllMessages();
							$scope.pigletsAdded = true;
							
						}  
						else
						{
							
							$scope.clearAllMessages();
						}
					}
						
				});
			}
	};
	
    /**
     * Search for the Piglet events
     */
    $scope.getPigletEventInformation = function()
	{		
    	var option = "";
		if(document.getElementById("farrowId").checked)
			 option = document.getElementById("farrowId").value;
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
		$scope.pigletEvent = pigletEventObj; 
		$scope.pigletEvent.farrowId = pigletEventObj.farrowEventDto.farrowId;
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