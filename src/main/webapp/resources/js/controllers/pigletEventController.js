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
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId+'&premisesType=null');
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
						companyId : $rootScope.companyId,
						selectedPremise : $scope.pigletEvent.premiseId
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
			   if(fieldName == "date1")
				{
					   $scope.pigletEvent["date1"] = null;
				}
			   else if(fieldName == "date2")
			   {
				   $scope.pigletEvent["date2"] = null; 
			   }  
			   else if(fieldName == "date3")
			   {
				   $scope.pigletEvent["date3"] = null; 
			   } 
			   else if(fieldName == "date4")
			   {
				   $scope.pigletEvent["date4"] = null; 
			   } 
			   else if(fieldName == "date5")
			   {
				   $scope.pigletEvent["date5"] = null; 
			   } 
			   else if(fieldName == "date6")
			   {
				   $scope.pigletEvent["date6"] = null; 
			   } 
			   else if(fieldName == "date7")
			   {
				   $scope.pigletEvent["date7"] = null; 
			   } 
			   else if(fieldName == "date8")
			   {
				   $scope.pigletEvent["date8"] = null; 
			   } 
			}
		   else
			{
			  
			   if(fieldName == "date1")
				{
				   $scope.pigletEvent["date1"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "date2")
				{
				   $scope.pigletEvent["date2"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "date3")
				{
				   $scope.pigletEvent["date3"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "date4")
				{
				   $scope.pigletEvent["date4"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "date5")
				{
				   $scope.pigletEvent["date5"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "date6")
				{
				   $scope.pigletEvent["date6"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "date7")
				{
				   $scope.pigletEvent["date7"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "date8")
				{
				   $scope.pigletEvent["date8"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			}
		}
		else
		{
			if(fieldName == "entryDate")
			{
				   $scope.entryDateRequired = true;
				   $scope.pigInfo["entryDate"] = null;
			}
		   else if(fieldName == "birthDate")
		   {
			   $scope.pigInfo["birthDate"] = null;
			   $scope.birthDateRequired = true;	   
		   }
		}
	  }
	}
	
	
	
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
					companyId : $rootScope.companyId,
					selectedPremise : $scope.selectedPremise
					
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
		$scope.pigletEvent["tattooId"] = pigletEventObj["tattooId"];
		$scope.pigletEvent["pigId"] = pigletEventObj["pigId"];
		$scope.pigletEvent["groupId"] = pigletEventObj["groupId"];
		$scope.pigletEvent["weightAtBirth"] = pigletEventObj["weightAtBirth"];
		$scope.pigletEvent["weightAtWeaning"] = pigletEventObj["weightAtWeaning"];
		$scope.pigletEvent["premiseId"] = pigletEventObj["premiseId"];
		$scope.pigletEvent["litterId"] = pigletEventObj["litterId"];
		$scope.pigletEvent["weight1"] = pigletEventObj["weight1"];
		$scope.pigletEvent["weight2"] = pigletEventObj["weight2"];
		$scope.pigletEvent["weight3"] = pigletEventObj["weight3"];
		$scope.pigletEvent["weight4"] = pigletEventObj["weight4"];
		$scope.pigletEvent["weight5"] = pigletEventObj["weight5"];
		$scope.pigletEvent["weight6"] = pigletEventObj["weight6"];
		$scope.pigletEvent["weight7"] = pigletEventObj["weight7"];
		$scope.pigletEvent["weight8"] = pigletEventObj["weight8"];
		$scope.pigletEvent["dateStr1"] = pigletEventObj["dateStr1"];
		$scope.pigletEvent["dateStr2"] = pigletEventObj["dateStr2"];
		$scope.pigletEvent["dateStr3"] = pigletEventObj["dateStr3"];
		$scope.pigletEvent["dateStr4"] = pigletEventObj["dateStr4"];
		$scope.pigletEvent["dateStr5"] = pigletEventObj["dateStr5"];
		$scope.pigletEvent["dateStr6"] = pigletEventObj["dateStr6"];
		$scope.pigletEvent["dateStr7"] = pigletEventObj["dateStr7"];
		$scope.pigletEvent["dateStr8"] = pigletEventObj["dateStr8"];
		$scope.pigletEvent["date1"] = pigletEventObj["date1"];
		$scope.pigletEvent["date2"] = pigletEventObj["date2"];
		$scope.pigletEvent["date3"] = pigletEventObj["date3"];
		$scope.pigletEvent["date4"] = pigletEventObj["date4"];
		$scope.pigletEvent["date5"] = pigletEventObj["date5"];
		$scope.pigletEvent["date6"] = pigletEventObj["date6"];
		$scope.pigletEvent["date7"] = pigletEventObj["date7"];
		$scope.pigletEvent["date8"] = pigletEventObj["date8"];
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
			
			var dateObj = null;
			if($scope.pigletEvent["date1"] != null)
			{
				dateObj = new Date($scope.pigletEvent["date1"]);
				$scope.pigletEvent["date1"] = DateUtils.convertLocaleDateToServer(dateObj);
			}
			if($scope.pigletEvent["date2"] != null)
			{
				dateObj = new Date($scope.pigletEvent["date2"]);
				$scope.pigletEvent["date2"] = DateUtils.convertLocaleDateToServer(dateObj);
			}
			if($scope.pigletEvent["date3"] != null)
			{
				dateObj = new Date($scope.pigletEvent["date3"]);
				$scope.pigletEvent["date3"] = DateUtils.convertLocaleDateToServer(dateObj);
			}
			if($scope.pigletEvent["date4"] != null)
			{
				dateObj = new Date($scope.pigletEvent["date4"]);
				$scope.pigletEvent["date4"] = DateUtils.convertLocaleDateToServer(dateObj);
			}
			if($scope.pigletEvent["date5"] != null)
			{
				dateObj = new Date($scope.pigletEvent["date5"]);
				$scope.pigletEvent["date5"] = DateUtils.convertLocaleDateToServer(dateObj);
			}
			if($scope.pigletEvent["date6"] != null)
			{
				dateObj = new Date($scope.pigletEvent["date6"]);
				$scope.pigletEvent["date6"] = DateUtils.convertLocaleDateToServer(dateObj);
			}
			if($scope.pigletEvent["date7"] != null)
			{
				dateObj = new Date($scope.pigletEvent["date7"]);
				$scope.pigletEvent["date7"] = DateUtils.convertLocaleDateToServer(dateObj);
			}
			if($scope.pigletEvent["date8"] != null)
			{
				dateObj = new Date($scope.pigletEvent["date8"]);
				$scope.pigletEvent["date8"] = DateUtils.convertLocaleDateToServer(dateObj);
			}
			
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