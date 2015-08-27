var PigletStatusEventController = pigTrax.controller('PigletStatusEventController', function($scope,$rootScope, $http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.pigletStatusEvent = {};
	$scope.confirmClick = false;
	$scope.editBtnclicked = false;
	
	
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
		$scope.eventDateTimerequired = false;
		$scope.inValidServiceIdFromServer = false;
		$scope.fosterToPigIdrequired = false;
		$scope.pigletstatusEventValidation_ErrCode_1 = false;
		$scope.pigletstatusEventValidation_ErrCode_2 = false;
		$scope.pigletstatusEventValidation_ErrCode_3 = false;
		$scope.pigletstatuseventform.$setUntouched();
	};
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.pigletStatusEvent = {};
		$scope.changeText();
		
	}
	
	$scope.loadPage = function(companyId)
	{
		$scope.setCompanyId(companyId);		
	};
	
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		
	};
		
	$scope.setPigletStatusDetails = function(pigletStatusObj)
	{
		$scope.pigletStatusEvent = pigletStatusObj; 
		$scope.editBtnclicked = true;
	}	
	
	$scope.deletePigletEvent = function()
	{
		restServices.deletePigletEvent($scope.pigletStatusEvent, function(data){
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.entryEventDeleteMessage = true;
					$scope.pigletStatusEvent = {};
					$scope.searchPigletStatusEvents();
					$window.scrollTo(0, 5);
				}
		});
	}
    
    /**
     * Search for the farrow events
     */
    $scope.searchFarrowEvent = function()
	{	   
		if($scope.pigletStatusEvent.pigId != undefined && $scope.pigletStatusEvent.pigId != "")
			{
			    var pigInfo = {
						searchText : $scope.pigletStatusEvent.pigId,
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
						$('#searchFarrowEvents').modal('show');
						
						$scope.clearAllMessages();
						var searchFarrowEvent = {
								searchText : $scope.pigletStatusEvent.pigId,
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
		$scope.pigletStatusEvent.farrowEventId = $scope.pigletStatusEvent.farrowEventDto.id;
		$('#searchFarrowEvents').modal('hide');
		$scope.getFosterInRecords();
	}
	
	
	$scope.getFosterInRecords = function()
	{
		if($scope.pigletStatusEvent.pigId != null)
			{  
				$scope.pigletStatusEvent["companyId"] = $rootScope.companyId;
			    
				restServices.getFosterInRecords($scope.pigletStatusEvent, function(data){				
					if(!data.error)
					{
					   $scope.fosterInRecords = data.payload;
					}
				});
			}
	}
	
	/**
     * retrieve all foster pigs
     */
    $scope.getAllFosterPigs = function()
	{	  
		if($scope.pigletStatusEvent.pigId != undefined && $scope.pigletStatusEvent.pigId != "")
			{
			    var pigInfo = {
						searchText : $scope.pigletStatusEvent.pigId,
						searchOption : "pigId",
						companyId : $rootScope.companyId
				};
			    //alert(JSON.stringify(pigInfo));
				restServices.getAllFosterPigs(pigInfo, function(data) {
					if(data.error)
					{
						$scope.clearAllMessages();
						$scope.inValidPigIdFromServer = true;		
						
					}
					else
					{
						$('#searchFosters').modal('show');
						$scope.fosterPigList = data.payload;
						//alert(JSON.stringify($scope.fosterPigList));
					}
						
				});
			}
		else
			{
			$scope.clearAllMessages();
			$scope.requiredPigIdMessage = true;
			
			}
		
	};
	
	
	$scope.selectFoster = function()
	{
		
		$scope.pigletStatusEvent.fosterToPigId = $scope.pigletStatusEvent.fosterDto.pigId  + " ["+$scope.pigletStatusEvent.fosterDto.currentFarrowEventDate+"]";
		$scope.pigletStatusEvent.fosterTo = $scope.pigletStatusEvent.fosterDto.id;
		$scope.pigletStatusEvent.fosterFarrowId = $scope.pigletStatusEvent.fosterDto.farrowEventId;
		$scope.pigletStatusEvent.fosterToDateTime = $scope.pigletStatusEvent.fosterDto.currentFarrowEventDate;
		$('#searchFosters').modal('hide');
	}	
	
	
	
	/**
	 * To add a farrow event
	 */
	$scope.addPigletStatusEvent = function()
    {
		if($scope.pigletstatuseventform.$valid)
		{
			$scope.pigletStatusEvent["eventDateTime"] = document.getElementById("eventDateTime").value;
			if($scope.pigletStatusEvent["eventDateTime"] === "")
			{
				$scope.eventDateTimerequired = true;
				return;
			}	
			if(($scope.pigletStatusEvent["fosterPigNum"] != undefined)){
				if($scope.pigletStatusEvent["fosterPigNum"] != "" 
					&& ($scope.pigletStatusEvent["fosterToPigId"] === undefined || $scope.pigletStatusEvent["fosterToPigId"] === "")){
					$scope.fosterToPigIdrequired = true;
					return;
				}
			}
			$scope.pigletStatusEvent["companyId"] = $rootScope.companyId;
			delete $scope.pigletStatusEvent.fosterDto;
			restServices.validatePigletStatusEvent($scope.pigletStatusEvent, function(data){
		   		if(!data.error)
			   {
		   			var statusCode = data.payload;
				     
				     if(statusCode == 0)
				     {
				    		$scope.confirmAddPigletStatusEvent();
							$scope.clearAllMessages();
							
				    }
				     else
				    {
				    	 //alert(statusCode);
				    	 if(statusCode == 1)
					    	 $scope.pigletstatusEventValidation_ErrCode_1 = true;
					     else if(statusCode == 2)
					    	 $scope.pigletstatusEventValidation_ErrCode_2 = true;
					     else if(statusCode == 3)
					    	 $scope.pigletstatusEventValidation_ErrCode_3 = true;
				    	 $scope.confirmClick = true;
				    	$window.scrollTo(0, 5);
				   	 }
			   }
			});
			
		}
    };
    
	$scope.confirmAddPigletStatusEvent = function()
	{
		//alert(JSON.stringify($scope.pigletStatusEvent));
		
		restServices.savePigletStatusEventInformation($scope.pigletStatusEvent, function(data){
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.entryEventSuccessMessage = true;
					$scope.pigletStatusEvent = {};
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
	
	};
    
   //method to search the piglet events on pig id or farrow id. Farrow it will give one set and pig id may return multiple sets. 
    $scope.searchPigletStatusEvents = function()
    {
    	var option = "";
		if(document.getElementById("rad1").checked)
			 option = document.getElementById("rad1").value;
		 else if(document.getElementById("rad2").checked)
			 option = document.getElementById("rad2").value;
    	
		if($scope.searchText == undefined || $scope.searchText == "" || option == "")
		{
			   $scope.clearAllMessages();
			   $scope.searchErrorMessage = true;
		}
		else
		{
			var searchPigletStatusEvent = {
					searchText : $scope.searchText,
					searchOption : option,
					pigId : $scope.searchText, 
					companyId : $rootScope.companyId
					
			};				
			restServices.getPigletStatusEventInformation(searchPigletStatusEvent, function(data){
				$scope.clearAllMessages();
				if(!data.error){
					$scope.pigletStatusEvent = {};
					$scope.pigletStatusEventList = data.payload;						
				}
				else
				{
					$scope.pigletStatusEvent = {};
					$scope.clearAllMessages(); 
					$scope.searchDataErrorMessage = true;		
					$scope.pigletStatusEventList = [];
				}
			});
		}
    };
	
   
	
});
