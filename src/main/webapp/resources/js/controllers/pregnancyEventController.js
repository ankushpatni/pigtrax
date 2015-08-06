var pregnancyEventController = pigTrax.controller('PregnancyEventController', function($scope,$rootScope, $http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.pregnancyEvent = {};
	$scope.confirmClick = false;
	
	$scope.clearAllMessages = function()
	{
		$scope.searchDataErrorMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventDeleteMessage = false;
		$scope.searchErrorMessage = false;
		$scope.inValidPigIdFromServer = false;
		$scope.confirmClick = false;
	};
	
	$scope.loadPage = function(companyId)
	{
		$scope.setCompanyId(companyId);
		
		$scope.getPregnancyEventTypes();
		
		$scope.getPregnancyExamResultTypes();
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
				$scope.pregnancyEventTypes = data.payload;
			}
		});
	}
	
	
	
	$scope.getPregnancyExamResultTypes = function()
	{
		restServices.getPregnancyExamResultTypes(function(data)
		{
			if(!data.error){
				$scope.pregnancyExamResultTypes = data.payload;
			}
		});
	}
	
	/**
	 * check the validity of pigId
	 */
	$scope.checkForPigId = function()
	{
	    var pigInfo = {
				searchText : $scope.pregnancyEvent.pigId,
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
			}
				
		});
	};
		
	
	/**
	 * To add a pregnancy event
	 */
	$scope.addPregnancyEvent = function()
    {
		if($scope.pregnancyeventform.$valid)
		{
			var examDate = document.getElementById("examDate").value;
			var resultDate = document.getElementById("resultDate").value;
			$scope.pregnancyEvent["examDate"] = examDate;
			$scope.pregnancyEvent["resultDate"] = resultDate;
			$scope.pregnancyEvent["companyId"] = $rootScope.companyId;
			//alert(JSON.stringify($scope.pregnancyEvent));
			restServices.savePregnancyEventInformation($scope.pregnancyEvent, function(data){
				if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.entryEventSuccessMessage = true;
						$scope.pregnancyEvent = {};
					}
				else
					{
						$scope.clearAllMessages();
						$scope.entryEventErrorMessage = true;
					}
					$window.scrollTo(0, 5);
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
    	
		if($scope.searchText == undefined || $scope.searchText == "" || option == "")
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
					companyId : $scope.companyId
					
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
	}
	
	
});