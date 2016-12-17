var PigletStatusEventController = pigTrax.controller('PigletStatusEventController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.pigletStatusEvent = {};
	$scope.confirmClick = false;
	$scope.editBtnclicked = false;
	$scope.DateUtils = DateUtils;
	$scope.eventSection = null;
	
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
		$scope.pigletStatusEventAlreadyAdded = false;
		$scope.invalidPigletNumbers = false;
		$scope.pigletNumbersRequired = false;
		$scope.invalidGroupEventId = false;
		$scope.matchingFarrowRecordNotFound = false;
	};
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.pigletStatusEvent = {};
		$scope.eventSection = null;
		$scope.changeText();
		
	}
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		  // var  dateObj = Date.parse(dateVal);	
		   var  dateObj = DateUtils.parse(dateVal,"dd/MM/yyyy");
		   if(dateObj == null)
			{
			   if(fieldName == "deathEventDate")
				{
					   $scope.eventDateTimerequired = true;
					   $scope.pigletStatusEvent["deathEventDateTime"] = null;
				}
			   else if(fieldName == "fosterEventDate")
			   {
				   $scope.pigletStatusEvent["fosterEventDateTime"] = null;
				   $scope.eventDateTimerequired = true;	   
			   }   
			   else if(fieldName == "weanEventDate")
			   {
				   $scope.pigletStatusEvent["weanEventDateTime"] = null;
				   $scope.eventDateTimerequired = true;	   
			   }   
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "deathEventDate")
				{
				   $scope.eventDateTimerequired = false;
				   $scope.pigletStatusEvent["deathEventDateTime"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "fosterEventDate")
				{
				   $scope.eventDateTimerequired = false;	 
				   $scope.pigletStatusEvent["fosterEventDateTime"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "weanEventDate")
				{
				   $scope.eventDateTimerequired = false;	 
				   $scope.pigletStatusEvent["weanEventDateTime"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			}
		}
		else
		{
			if(fieldName == "deathEventDate")
			{
				   $scope.eventDateTimerequired = true;
				   $scope.pigletStatusEvent["deathEventDateTime"] = null;
			}
		   else if(fieldName == "fosterEventDate")
		   {
			   $scope.pigletStatusEvent["fosterEventDateTime"] = null;
			   $scope.eventDateTimerequired = true;	   
		   }   
		   else if(fieldName == "weanEventDate")
		   {
			   $scope.pigletStatusEvent["weanEventDateTime"] = null;
			   $scope.eventDateTimerequired = true;	   
		   }   
		}
	  }
	}
	
	
	$scope.changeEvent = function()
	{
		if($scope.pigletStatusEvent.pigletStatusEventTypeId == 2)
			$scope.eventSection = 'transfer';
		else if($scope.pigletStatusEvent.pigletStatusEventTypeId == 3)
			$scope.eventSection = 'wean';
		else if($scope.pigletStatusEvent.pigletStatusEventTypeId == 4)
			$scope.eventSection = 'death';
		
		/*if($scope.pigletStatusEvent["pigId"] != null)
			{
				restServices.getPigletStatusEventsByFarrowEventId($scope.pigletStatusEvent, function(data){
					$scope.fosterInRecords = [];
					if(!data.error)
					{
						//$('#searchFarrowEvents').modal('hide');				
						$scope.pigletStatusEventAlreadyAdded = false;
						$scope.getFosterInRecords();
					}
					else
					{
						//$('#searchFarrowEvents').modal('hide');
						$scope.clearAllMessages();
						$scope.pigletStatusEventAlreadyAdded = true;
						$scope.pigletStatusEvent = {};
					}
				});
			}*/
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
			$scope.pigInfoList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.loadPage = function(companyId)
	{
		$scope.setCompanyId(companyId);	
		$scope.loadMortalityReasonTypes();		
		$scope.loadPremises();
	};
	
	
	$scope.getPenList = function(){
		restServices.getPenListForPremise($scope.pigletStatusEvent["premiseId"], function(data){
			 if(!data.error)
			 {
				 $scope.penInfo = data.payload;
			 }
		});
	};
	
	$scope.loadMortalityReasonTypes = function()	
	{
		restServices.getMortalityReasonTypes(function(data)
		{
			if(!data.error){
				
				var responseList = data.payload;
				$scope.mortalityReasonTypeKeys = responseList[0];
				$scope.mortalityReasonTypes = responseList[1];
			}
		});
	}
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		
	};
		
	$scope.setPigletStatusDetails = function(pigletStatusObj)
	{
		$scope.clearAllMessages();
		$scope.pigletStatusEvent = pigletStatusObj;
		if($scope.pigletStatusEvent.pigletStatusEventTypeId == 2)
			$scope.eventSection = 'transfer';
		else if($scope.pigletStatusEvent.pigletStatusEventTypeId == 3)
			$scope.eventSection = 'wean';
		else if($scope.pigletStatusEvent.pigletStatusEventTypeId == 4)
			$scope.eventSection = 'death';
		$scope.getPenList();
		$scope.getFosterInRecords();
		$scope.editBtnclicked = true;
	}	
	
	
	$scope.checkGroupEventId = function()
	{
		if($scope.pigletStatusEvent.groupId != undefined && $scope.pigletStatusEvent.groupId != ""){
			var searchGroupEvent = {
				"groupId" : $scope.pigletStatusEvent.groupId,
				"companyId" : $scope.companyId,
			};
		
			restServices.getGroupEventInformation(searchGroupEvent, function(data){
				if(data.error)
				{	
					
					$scope.invalidGroupEventId = true;	
				}
				else
				{
					var responsePayload = data.payload;
					
					var groupEvent = responsePayload[0];
					if(groupEvent.groupStartDateTime >  $scope.pigletStatusEvent.weanEventDateTime)
					{
						$scope.invalidGroupEventDate = true;
					}
					else
					{
						$scope.invalidGroupEventDate = false;
						$scope.invalidGroupEventId = false;
						var groupEvent = data.payload[0];
						$scope.pigletStatusEvent["groupEventId"] = groupEvent.id;
					}
				}
			});
		}
	}
	
	$scope.deletePigletStatusEvent = function() 
	{
		$scope.clearAllMessages();
		restServices.deletePigletStatusEvent($scope.pigletStatusEvent, function(data){
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.entryEventDeleteMessage = true;
					$scope.pigletStatusEvent = {};
					$scope.fosterInRecords = [];
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
    	$scope.getPenList();
    	$scope.clearAllMessages();
		if($scope.pigletStatusEvent.pigId != undefined && $scope.pigletStatusEvent.pigId != "")
			{
			    var pigInfo = {
						searchText : $scope.pigletStatusEvent.pigId,
						searchOption : "pigId",
						companyId : $rootScope.companyId,
						selectedPremise : $scope.pigletStatusEvent.premiseId
				};
				restServices.getPigInformation(pigInfo, function(data) {
					if(data.error)
					{
						$scope.clearAllMessages();
						$scope.inValidPigIdFromServer = true;		
						$scope.pigletStatusEvent = {};
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
		$scope.pigletStatusEvent.farrowEventId = $scope.pigletStatusEvent.farrowEventDto.id;
		$scope.clearPigletInformation();
		$scope.getFosterInRecords();	
		$('#searchFarrowEvents').modal('hide');
	}
	
	
	$scope.clearPigletInformation = function()
	{
		$scope.pigletStatusEvent["fosterFrom"] = null;
		$scope.pigletStatusEvent["fosterTo"] = null;
		$scope.pigletStatusEvent["fosterToDateTime"] = null;
		$scope.pigletStatusEvent["weanEventDateTime"] = null;
		$scope.pigletStatusEvent["fosterEventDateTime"] = null;
		$scope.pigletStatusEvent["deathEventDateTime"] = null;
		$scope.pigletStatusEvent["eventReason"] = null;
		$scope.pigletStatusEvent["remarks"] = null;
		$scope.pigletStatusEvent["sowCondition"] = null;
		$scope.pigletStatusEvent["weanGroupId"] = null;
		
		$scope.pigletStatusEvent["weanPigNum"] = null;
		$scope.pigletStatusEvent["weanPigWt"] = null;
		$scope.pigletStatusEvent["fosterPigNum"] = null;
		$scope.pigletStatusEvent["fosterPigWt"] = null;
		$scope.pigletStatusEvent["fosterToPigId"] = null;
		$scope.pigletStatusEvent["fosterFarrowEventId"] = null;
		$scope.pigletStatusEvent["deathPigNum"] = null;
		$scope.pigletStatusEvent["deathPigWt"] = null;
		$scope.fosterInRecords = [];
	}
	
	
	
	$scope.getFosterInRecords = function()
	{		
		$scope.clearAllMessages(); 
		if($scope.pigletStatusEvent.pigId != null)
			{  
				$scope.pigletStatusEvent["companyId"] = $rootScope.companyId;
			    
				restServices.getFosterInRecords($scope.pigletStatusEvent, function(data){				
					if(!data.error)
					{
					   $scope.fosterInRecords = data.payload;
					   var records = $scope.fosterInRecords;
					   var totalfoserInCnt = 0;
					   if(records != null && records.length > 0)
						   {
						    for(var i=0; i<records.length; i++)
						    	{
						    	totalfoserInCnt += records[i].numberOfPigs;
						     	}
						   }
					   $scope.pigletStatusEvent["totalfoserInCnt"] = totalfoserInCnt;
					   
					}
				});
			}
	}
	
	/**
     * retrieve all foster pigs
     */
    $scope.getAllFosterPigs = function()
	{	$scope.clearAllMessages(); 
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
		$scope.clearAllMessages(); 
		$scope.pigletStatusEvent.fosterToPigId = $scope.pigletStatusEvent.fosterDto.pigId  + " ["+$scope.pigletStatusEvent.fosterDto.currentFarrowEventDate+"]";
		$scope.pigletStatusEvent.fosterTo = $scope.pigletStatusEvent.fosterDto.id;
		$scope.pigletStatusEvent.fosterFarrowEventId = $scope.pigletStatusEvent.fosterDto.farrowEventId;
		$scope.pigletStatusEvent.fosterToDateTime = $scope.pigletStatusEvent.fosterDto.currentFarrowEventDate;
		$('#searchFosters').modal('hide');
	}	
	
	
	
	/**
	 * To add a farrow event
	 */
	$scope.addPigletStatusEvent = function()
    {
		$scope.clearAllMessages();
		
		/*var weanEventDateTime = document.getElementById("weanEventDateTime").value;
		var weanEventDateVal = new Date(weanEventDateTime);
		weanEventDateTime = DateUtils.convertLocaleDateToServer(weanEventDateVal);
		$scope.pigletStatusEvent["weanEventDateTime"] = weanEventDateTime;
		
		var fosterEventDateTime = document.getElementById("fosterEventDateTime").value;
		var fosterEventDateVal = new Date(fosterEventDateTime);
		fosterEventDateTime = DateUtils.convertLocaleDateToServer(fosterEventDateVal);
		$scope.pigletStatusEvent["fosterEventDateTime"] = fosterEventDateTime;
		
		var deathEventDateTime = document.getElementById("deathEventDateTime").value;
		var deathEventDateVal = new Date(deathEventDateTime);
		deathEventDateTime = DateUtils.convertLocaleDateToServer(deathEventDateVal);   
		$scope.pigletStatusEvent["deathEventDateTime"] = deathEventDateTime;*/
					
		
		var weanPigNum = $scope.pigletStatusEvent["weanPigNum"];
		if(weanPigNum == null || weanPigNum == undefined)
		{
			weanPigNum = 0;
			$scope.pigletStatusEvent["weanPigNum"] = weanPigNum;
		}
		var fosterPigNum = $scope.pigletStatusEvent["fosterPigNum"];
		if(fosterPigNum == null || fosterPigNum == undefined)
		{
			fosterPigNum = 0;
			$scope.pigletStatusEvent["fosterPigNum"] = fosterPigNum;
		}
		var deathPigNum = $scope.pigletStatusEvent["deathPigNum"];
		if(deathPigNum == null || deathPigNum == undefined)
		{
			deathPigNum = 0;
			$scope.pigletStatusEvent["deathPigNum"] = deathPigNum;
		}
		
		if($scope.pigletStatusEvent["pigId"] == null || $scope.pigletStatusEvent["pigId"] == undefined || $scope.pigletStatusEvent["pigId"] == "")
		{
			$scope.requiredPigIdMessage = true;
			return;
		}
		
		if(parseInt(weanPigNum) != weanPigNum || parseInt(fosterPigNum) != fosterPigNum || parseInt(deathPigNum) != deathPigNum)
		{
				$scope.clearAllMessages();
				$scope.invalidPigletNumbers = true;
		}
		else if(weanPigNum == 0 && fosterPigNum == 0 && deathPigNum == 0)
		{		
			   $scope.clearAllMessages();
				$scope.pigletNumbersRequired = true;				
				
				return;
		}
		else if($scope.pigletStatusEvent["pigletStatusEventTypeId"] == 3 && $scope.pigletStatusEvent["weanPigNum"] != undefined && $scope.pigletStatusEvent["weanPigNum"] != null && $scope.pigletStatusEvent["weanPigNum"] != 0
				&& $scope.pigletStatusEvent["weanPigNum"] != "" && ( $scope.pigletStatusEvent["weanEventDateTime"] === undefined || $scope.pigletStatusEvent["weanEventDateTime"] === null || 
				$scope.pigletStatusEvent["weanEventDateTime"] == "" || $scope.pigletStatusEvent["weanEventDateTime"] == "Invalid Date") )
		{
			
			$scope.clearAllMessages();
			$scope.eventDateTimerequired = true;			
			return;
		}
		
		else if($scope.pigletStatusEvent["pigletStatusEventTypeId"] == 2 && $scope.pigletStatusEvent["fosterPigNum"] != undefined && $scope.pigletStatusEvent["fosterPigNum"] != null && $scope.pigletStatusEvent["fosterPigNum"] != 0
				&& $scope.pigletStatusEvent["fosterPigNum"] != "" && ( $scope.pigletStatusEvent["fosterEventDateTime"] === undefined || $scope.pigletStatusEvent["fosterEventDateTime"] === null ||
				$scope.pigletStatusEvent["fosterEventDateTime"] == ""  || $scope.pigletStatusEvent["fosterEventDateTime"] == "Invalid Date"))
		{
			$scope.clearAllMessages();
			$scope.eventDateTimerequired = true;
			return;
		}
		
		else if($scope.pigletStatusEvent["pigletStatusEventTypeId"] == 4 && $scope.pigletStatusEvent["deathPigNum"] != undefined && $scope.pigletStatusEvent["deathPigNum"] != null && $scope.pigletStatusEvent["deathPigNum"] != 0
				&& $scope.pigletStatusEvent["deathPigNum"] != "" && ( $scope.pigletStatusEvent["deathEventDateTime"] === undefined || $scope.pigletStatusEvent["deathEventDateTime"] === null || 
				$scope.pigletStatusEvent["deathEventDateTime"] === null || $scope.pigletStatusEvent["deathEventDateTime"] == "Invalid Date")) 
		{
			$scope.clearAllMessages();
			$scope.eventDateTimerequired = true;
			return;
		}
		 
		else if($scope.pigletStatusEvent["fosterPigNum"] != undefined && $scope.pigletStatusEvent["fosterPigNum"] != null && $scope.pigletStatusEvent["fosterPigNum"] != 0
				&& ($scope.pigletStatusEvent["fosterToPigId"] === undefined || $scope.pigletStatusEvent["fosterToPigId"] === null)){				
				$scope.clearAllMessages();
				$scope.fosterToPigIdrequired = true;
				
				return;				
		}
		else
			{
				$scope.clearAllMessages();
				//alert("fosterToPigId"+$scope.pigletStatusEvent["fosterToPigId"]);
				if($scope.pigletstatuseventform.$valid)
				{
					
					$scope.clearAllMessages();
					$scope.pigletStatusEvent["companyId"] = $rootScope.companyId;
					delete $scope.pigletStatusEvent.fosterDto;
					//alert($scope.pigletStatusEvent.farrowEventId);
					/*restServices.validatePigletStatusEvent($scope.pigletStatusEvent, function(data){
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
					});*/
					$scope.confirmAddPigletStatusEvent();
					
				}
			}
    };
    
	$scope.confirmAddPigletStatusEvent = function()
	{
		//alert(JSON.stringify($scope.pigletStatusEvent));
		
		
		if($scope.pigletStatusEvent["weanEventDateTime"] != null){
			var weanEventDateVal = new Date($scope.pigletStatusEvent["weanEventDateTime"]);
			$scope.pigletStatusEvent["weanEventDateTime"] = DateUtils.convertLocaleDateToServer(weanEventDateVal);
		}
		
		if($scope.pigletStatusEvent["fosterEventDateTime"] != null){
			var fosterEventDateVal = new Date($scope.pigletStatusEvent["fosterEventDateTime"]);
			$scope.pigletStatusEvent["fosterEventDateTime"] = DateUtils.convertLocaleDateToServer(fosterEventDateVal);
		}
		
		if($scope.pigletStatusEvent["deathEventDateTime"] != null){
			var deathEventDateVal = new Date($scope.pigletStatusEvent["deathEventDateTime"]);
			$scope.pigletStatusEvent["deathEventDateTime"] = DateUtils.convertLocaleDateToServer(deathEventDateVal);
		}
		
		restServices.savePigletStatusEventInformation($scope.pigletStatusEvent, function(data){
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.entryEventSuccessMessage = true;
					$scope.pigletStatusEvent = {};
					$scope.fosterInRecords = [];
				}
			else
				{ 
					$scope.clearAllMessages();					
					if(data.duplicateRecord)
						$scope.entryEventDuplicateErrorMessage = true;
					else if(data.statusMessage == "ERR:INVALID-FARROW")
						$scope.matchingFarrowRecordNotFound = true;
					else
						$scope.entryEventErrorMessage = true;
				}
				$window.scrollTo(0, 5);  
		});		
	
	};
    
   //method to search the piglet events on pig id or farrow id. Farrow it will give one set and pig id may return multiple sets. 
    $scope.searchPigletStatusEvents = function()
    {
    	$scope.clearAllMessages(); 
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
			var searchPigletStatusEvent = {
					searchText : $scope.searchText,
					searchOption : option,
					pigId : $scope.searchText, 
					companyId : $rootScope.companyId,
					selectedPremise : $scope.selectedPremise
					
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
	
    
    $scope.$watch('pigletStatusEvent["weanPigNum"]', function(newVal, oldVal) {
		
        if(oldVal != null && oldVal != "" && (newVal == "" || newVal == null) && $scope.pigletStatusEvent["groupEventId"] != null)
		{
			if(confirm("Please confirm if you want to remove the group id association?"))
			{
				$scope.pigletStatusEvent["groupEventId"] = null;
				$scope.pigletStatusEvent["groupId"] = null;
			}
			else
			{
				$scope.pigletStatusEvent["weanPigNum"] = oldVal;
			}
		}
    });
    
    $scope.goToFarrowEvent = function()
	{
		document.getElementById("selectedCompany").value = $rootScope.companyId;
		document.getElementById("selectedFarrowEventId").value = $scope.pigletStatusEvent.farrowEventDto.id;
		document.getElementById("prevFarrowEventForm").submit();
	}
    
	
});


$(document).ready(function () { 
	$('input[class="icheck pigletstatus"]').on('ifClicked', function (event) {
		angular.element("#PigletStatusEventControllerId").scope().clearAllMessages();
		angular.element("#PigletStatusEventControllerId").scope().$apply();
	});		
});