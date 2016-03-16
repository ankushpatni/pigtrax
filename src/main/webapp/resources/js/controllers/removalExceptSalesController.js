var feedEventController = pigTrax.controller('RemovalExceptSalesController', function($scope,$rootScope,$modal,$http,$window,restServices,DateUtils) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.transportDestination;
	$scope.transportTruck;
	$scope.transportTrailer;
	$scope.premiseList={};
	$scope.removalEventType={};
	$scope.groupEventList={};
	$scope.groupEventOriginalList={};
	$scope.pigInfoList={};
	$scope.pigInfoOriginalList={};
	$scope.removalExceptSales={};
	$scope.sourceAndDestinationPremisesSameError = false;
	$scope.mortalityReasonType={};
$scope.removalEventTypeKeysValues = {};
	
	
	$scope.setCompanyId = function(companyId,removalIdEntered,removalGeneratedId,removalExceptSalesId,removalTypeId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		$scope.removalTypeId = removalTypeId;
		$scope.removalExceptSales.removalEventId = removalTypeId;
		console.log($scope.removalTypeId);
		
		var res1 = $http.get('rest/transportJourney/getTransportJourneyMasterData?generatedCompanyId='+$scope.companyId);
		res1.success(function(data, status, headers, config) {
			console.log(data);
			$scope.transportDestination = data.payload[0];	
			$scope.transportTruck = data.payload[1];
			$scope.transportTrailer = data.payload[2];
			
		});
		res1.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		var res2 = $http.get('rest/util/getRemovalExceptSalesMasterData?companyId='+$rootScope.companyId);
		res2.success(function(data, status, headers, config) {
			console.log(data);
			
			
			var removalEventTypeMap = data.payload[0];
			$scope.removalEventTypeKeys = removalEventTypeMap['RemovalEventKey'];
			$scope.removalEventTypeOriginal = removalEventTypeMap['RemovalEventValue'];
			
			//$scope.removalEventTypeOriginal = data.payload[0];
			$scope.pigInfoOriginalList = data.payload[1];
			$scope.premiseList = data.payload[2];
			$scope.groupEventOriginalList = data.payload[3];
			
			
			var mortalityReasonKeyValueMap = data.payload[4];
			$scope.mortalityReasonKeys = mortalityReasonKeyValueMap['MortalityReasonKey'];
			$scope.mortalityReasonType = mortalityReasonKeyValueMap['MortalityReasonValue'];
			$scope.premisesMap = data.payload[5];
			
			//$scope.mortalityReasonType = data.payload[4];
			
			for( var x in $scope.groupEventOriginalList)
			{
				if( $scope.groupEventOriginalList[x].active && $scope.groupEventOriginalList[x].currentInventory > 0)
					{		
						$scope.groupEventList[x] = $scope.groupEventOriginalList[x];
					}
			}
			for( var x in $scope.pigInfoOriginalList)
			{
				if( $scope.pigInfoOriginalList[x].active )
					{		
						$scope.pigInfoList[x] = $scope.pigInfoOriginalList[x];
					}
			}
			for( var x in $scope.removalEventTypeOriginal)
			{				
				if( x!=3)
					{
						$scope.removalEventType[x] = $scope.removalEventTypeOriginal[x];
					}
			}
			for( var x in $scope.removalEventTypeKeys)
			{				
				if($scope.removalEventTypeKeys[x] == 2 || $scope.removalEventTypeKeys[x] == 7 || $scope.removalEventTypeKeys[x] == 8)
					{
						$scope.removalEventTypeKeysValues[x] = $scope.removalEventTypeKeys[x];
					}
			}

		});
		res2.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		/*if( removalExceptSalesId && removalExceptSalesId!= undefined)
		{
			$scope.getRemovalExceptSales(removalExceptSalesId);
		}*/
	};
	

	$scope.getRemovalExceptSales = function(removalExceptSalesId)
    {
		if(removalExceptSalesId && removalExceptSalesId != undefined )
		{
			$scope.removalExceptSales.id = removalExceptSalesId;
		}
		restServices.getRemovalExceptSales($scope.removalExceptSales, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.removalExceptSales = data.payload;
				}
			else
				{
					
				}
		});
    }
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.removalExceptSales = {};
		//document.getElementById("removalDateTime").value = "";
		$scope.changeText();
	}
	
	$scope.clearAllMessages = function()
	{
		$scope.eventErrorMessage = false;
		$scope.eventDuplicateErrorMessage = false;
		$scope.noOfPigsrequired  = false;
		$scope.noOfPigWrongCount = false;
		$scope.errorRemovalDateTime = false;
		$scope.destinationPremisesSameError = false;
		$scope.roomSelectionForGroupTransferError = false;
	};
	
	$scope.$watch("removalExceptSales.destPremiseId", function(newValue, oldValue) {
		if (newValue != null && newValue != undefined && newValue !=0 && newValue === $scope.removalExceptSales.premiseId) {
			$scope.sourceAndDestinationPremisesSameError = true;
		}
		else
		{
			$scope.sourceAndDestinationPremisesSameError = false;
		}
	});
	
	/*$scope.decideGroupPremises = function()
	{
		var option = "";
		if(document.getElementById("rad1").checked)
		{
			 option = document.getElementById("rad1").value;
		}
		 else if(document.getElementById("rad2").checked)
		{
			 option = document.getElementById("rad2").value;
		}
	}*/
	
	$scope.addTransportJourney = function()
	{
		var modalInstance = $modal.open ({
			templateUrl: 'transportJourney',
			controller: 'addTransportJourneyCtrl',
			backdrop:true,
			windowClass : 'cp-model-window',
			resolve:{
				transportJourneyMasterData : function(){
					var transportJourneyMasterData={};
					transportJourneyMasterData.transportDestination = $scope.transportDestination;
					transportJourneyMasterData.transportTruck = $scope.transportTruck;
					transportJourneyMasterData.transportTrailer = $scope.transportTrailer;
					transportJourneyMasterData.transportJourney = $scope.removalExceptSales.transportJourney;					
					return transportJourneyMasterData;
				}
			}
		});
		
		modalInstance.result.then( function(res) { 
			console.log(res);
			$scope.removalExceptSales.transportJourney = res;
		});
	}
	
	$scope.addRemovalExceptSales = function() 
	{
		$scope.clearAllMessages();
		if($scope.removalExceptSales["removalDateTime"] === "")
		{
			$scope.removalDateTimerequired = true;
			return;
		}
		if($scope.sourceAndDestinationPremisesSameError == true)
		{
			return false;
		}
		console.log($scope.removalExceptSales.removalEventId);
		console.log($scope.removalExceptSales.destPremiseId);
		if($scope.removalExceptSales.removalEventId==9 &&  
		$scope.removalExceptSales.destPremiseId === undefined)
		{
			$scope.destinationPremisesSameError = true;
			return false;
		}
		if ($scope.removalExceptSales.removalEventId==9 &&  
		$scope.removalExceptSales.roomId === undefined && $scope.selectGroup==='group')
		{
			$scope.roomSelectionForGroupTransferError = true;
			return false;
		}
		if($scope.removalExceptSales.id === 'undefined' )
		{
			$scope.removalExceptSales.id = 0;
		}
		
		if(document.getElementById("rad1").checked)
		{
			$scope.removalExceptSales.pigInfoId = 0;
			if($scope.removalExceptSales.numberOfPigs ==0)
			{
				$scope.noOfPigsrequired = true;	
				return false;
			}
			var groupevent = $scope.groupEventList[$scope.removalExceptSales.groupEventId];
			if(groupevent.currentInventory < $scope.removalExceptSales.numberOfPigs)
			{
				$scope.noOfPigWrongCount  = true;
				return false;
			}			
			
			if($scope.removalExceptSales.removalDateTime < groupevent.groupStartDateTime)
			{
				$scope.errorRemovalDateTime = true;
				return false;
			}
		}
		 else if(document.getElementById("rad2").checked)
		{
			 $scope.removalExceptSales.groupEventId = 0;
			 $scope.removalExceptSales.numberOfPigs = 1;
		}
		
		
		if($scope.removalExceptFormSales.$valid)
			{
				$scope.clearAllMessages();
				$scope.removalExceptSales.companyId = $scope.companyId;
				console.log($scope.removalExceptSales);
				restServices.addRemovalExceptSales($scope.removalExceptSales, function(data){
				console.log(data);
					if(!data.error)
						{
							
							/*$scope.entryEventSuccessMessage = true;
							$scope.getRemovalEvent($scope.removalEventForm.ticketNumber,true);*/
						$scope.gotoRemovalEvent();
						}
					else
						{
							$scope.clearAllMessages();
							if(data.duplicateRecord)
							{
								$scope.removalEventDuplicateErrorMessage = true;
							}
							else
							{
								$scope.entryEventErrorMessage = true;
							}
						} 
				});
			}
		
		$window.scrollTo(0,5);
	}
	
	$scope.getPremise = function()
	{	
		if($scope.selectGroup=='pigInfo')
		{
			restServices.getPigInformationById($scope.removalExceptSales.pigInfoId, function(data){
				if(!data.error){
					var pigInfo = data.payload;
					$scope.removalExceptSales["premiseId"] = pigInfo["premiseId"];
				}
			});
		}
		else
		{		
			var grpEvent = {
					"id" : $scope.removalExceptSales.groupEventId,
					"companyId" : $rootScope.companyId
			}
			restServices.getGroupEventInformationById(grpEvent, function(data){
				if(!data.error)
					{
					  var groupInfo = data.payload;
					  $scope.removalExceptSales["premiseId"] = groupInfo["premiseId"];
					  $scope.removalExceptSales["numberOfPigs"] = groupInfo.currentInventory;
					  $scope.removalExceptSales["weightInKgs"]=0;
					}
			});
		}
		
	}
	
	 $scope.gotoRemovalEvent = function()
	    {
	    	document.getElementById("selectedCompany").value = $scope.companyId;		
			document.getElementById("fromExcept").value = true;
			document.forms['removalExceptFormSales'].action = 'removalEvent';
			document.forms['removalExceptFormSales'].submit();
	    }
		
	$scope.dateCheck = function(dateVal)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		   //var  dateObj = Date.parse(dateVal);
		   var  dateObj = DateUtils.parse(dateVal,"dd/MM/yyyy");
		   if(dateObj == null)
			{
			  	   $scope.removalExceptSales["removalDateTime"] = "";
				   $scope.birthDateRequired = true;
			}
		   else
			{
			   $scope.dateError = false;
			    $scope.birthDateRequired = false;	 
				$scope.removalExceptSales.removalDateTime = DateUtils.convertLocaleDateToServer(dateObj);
			}
		}
		else
		{
			   $scope.removalExceptSales["removalDateTime"] = "";
			   $scope.birthDateRequired = true;	
		}
	  }
	}
	 
	 
	 

		$scope.moveToAnotherGroup = function()
		{
			var  groupEventObj  = $scope.groupEventList[$scope.removalExceptSales.groupEventId];
		console.log($scope.phaseOfProductionType);
			var modalInstance = $modal.open ({
				templateUrl: 'moveToAnotherGroup',
				controller: 'moveToAnotherGroupCtrl',
				backdrop:true,
				windowClass : 'cp-model-window',
				resolve:{
					moveToAnotherGroup : function(){
						var moveToAnotherGroup={};
						moveToAnotherGroup.phaseOfProductionType = $scope.phaseOfProductionType;
						moveToAnotherGroup.companyId = $rootScope.companyId;
						moveToAnotherGroup.groupGeneratedIdSeq = $scope.removalExceptSales.groupEventId;						
						moveToAnotherGroup.previousGroupId = groupEventObj.groupId;
						moveToAnotherGroup.pigCount = groupEventObj.currentInventory;
						moveToAnotherGroup.groupStartDateTime = groupEventObj.groupStartDateTime;
						moveToAnotherGroup.premisesMap = $scope.premisesMap;
						return moveToAnotherGroup;
					}
				}
			});
			
			modalInstance.result.then( function(res) {   
				if(res.statusMessage==="Success")
				{
					$scope.getGroupEventInformation(groupEventObj.groupId,false,true);
				}
			});
		}
		
		$scope.getRooms = function()
		{
			restServices.getRoomsForPremise($scope.removalExceptSales.destPremiseId, function(data){
				if(!data.error){
					
					$scope.roomMap = data.payload;					
				}
			});
		}
	
});