var groupEventController = pigTrax.controller('GroupEventController', function($scope,$rootScope, $modal, $http,$window,restServices, DateUtils) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.groupEvent = {};
	$scope.groupEventDetailList = [];
	$scope.confirmClick = false;
	$scope.phaseOfProductionType = {};
	$scope.phaseOfProductionTypeForNewAdd = {};
	$scope.roomList={};
	$scope.transportDestination;
	$scope.transportTruck;
	$scope.transportTrailer;
	$scope.followedGroupIdString;
	$scope.barnList={};
	$scope.entryEventStatusChangeSuccessMessage = false;
	$scope.editGroupEventInventory = false;
	$scope.DateUtils = DateUtils;
	$scope.roomValues = [];	
	$scope.updatedRooms = [];
	$scope.allRoomValues = [];
	$scope.groupEvent["roomIds"] = [];
	$scope.promoteToFinishMessage = false;
	$scope.transferToGroupSearchError = false;
	$scope.transferToGroupSearchDataError = false;
	$scope.transferGroupEventData = {};
	$scope.invalidRoomSelection = false;
	$scope.updatedRooms = [];
	$scope.transferPhase = null;
	$scope.transferPhaseId = null;
	
	$scope.multiselectdropdownsettings = {
    scrollableHeight: '200px',
    scrollable: true
};
	
	$scope.loadPremises = function()
	{
		var res = $http.get('rest/premises/getPremisesListNotIn?generatedCompanyId='+$rootScope.companyId+'&premisesType=1');
		res.success(function(data, status, headers, config) {
			$scope.farmList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.loadGroupEvents = function()
	{
		var res = $http.get('rest/groupEvent/getGroupEventByPremiseWithoutStatus?premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
			$scope.groupEventFromPremisesList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.setCompanyId = function(companyId,searchedGroupid, searchPremiseId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
				
		var res2 = $http.get('rest/util/getPhaseOfProductionType?companyId='+$scope.companyId);
			res2.success(function(data, status, headers, config) {
				$scope.phaseOfProductionType = data.payload[0];	
				$scope.roomList = data.payload[1];
				$scope.barnList = data.payload[2]
				//$scope.phaseOfProductionTypeForNewAdd = data.payload[0];
				for( var x in $scope.phaseOfProductionType) {
					if( x == 1 || x == 3 )
						{		
						var obje = $scope.phaseOfProductionType[x];
						$scope.phaseOfProductionTypeForNewAdd[x] = $scope.phaseOfProductionType[x];
						}
					}
					console.log($scope.phaseOfProductionTypeForNewAdd);
			});
			
			res2.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});
			
		if( searchedGroupid)
		{
			$scope.searchText = searchedGroupid
			$scope.selectedPremise = searchPremiseId;
			$scope.getGroupEventInformation(searchedGroupid,searchPremiseId, true);
			$scope.entryEventSuccessMessage = true;
		}
		
		var res1 = $http.get('rest/transportJourney/getTransportJourneyMasterData?generatedCompanyId='+$scope.companyId);
			res1.success(function(data, status, headers, config) {
				
				$scope.transportDestination = data.payload[0];	
				$scope.transportTruck = data.payload[1];
				$scope.transportTrailer = data.payload[2];
				
			});
			res1.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});
			
			$scope.loadPremises();
		
	};
	
	
	$scope.getRooms = function(isOnChange)
	{		
		if(isOnChange)
			$scope.groupEvent["roomIds"] = [];
		var res = $http.post('rest/room/getRoomsForPremise?barnType=groupevent', $scope.groupEvent["premiseId"]);
		res.success(function(data, status, headers, config) {
			if(!data.error)
			{
				$scope.roomType = data.payload;
				$scope.roomValues = [];
				angular.forEach($scope.roomType, function(key, value){					
                   var itemObj = {"id" : value, "label":key}  
				   $scope.roomValues.push(itemObj);
               })		
			}				
		});
	}
	
	$scope.getBarnDetailsByRoom = function()
	{
		restServices.getBarnDetailsByRoom($scope.groupEvent.roomId, function(data){
			if(!data.error){
				var barn = data.payload;
				$scope.groupEvent["barnId"] = barn.id;
				$scope.groupEvent["barnIdValue"] = barn.barnId;
			}
		});
	}
	
	
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{			 			 
		   var  dateObj = DateUtils.parse(dateVal,"dd/MM/yyyy");
		   if(dateObj == null)
			{
			   if(fieldName == "groupStartDate")
				{
					   $scope.groupdaterequired = true;
					   $scope.groupEvent["groupStartDateTime"] = null;
				}	
			   else if(fieldName == "groupCloseDate")
			   {				  
				   $scope.groupEvent["groupCloseDateTime"] = null;
			   }
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "groupStartDate")
				{
				   $scope.groupdaterequired = false;
				   $scope.groupEvent["groupStartDateTime"]  = DateUtils.convertLocaleDateToServer(dateObj);
				}		
			   else if(fieldName == "groupCloseDate")
			   {				  
				   $scope.groupEvent["groupCloseDateTime"] = DateUtils.convertLocaleDateToServer(dateObj);
			   }
			}
		}
		else
		{
			if(fieldName == "groupStartDate")
			{
				   $scope.groupdaterequired = true;
				   $scope.groupEvent["groupStartDateTime"] = null;
			}	
			else if(fieldName == "groupCloseDate")
			{				  
				   $scope.groupEvent["groupCloseDateTime"] = null;
			}
		}
	  }
	}
	
	
	
	
	$scope.clearAllMessages = function()
	{
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.groupdaterequired = false;
		$scope.groupEventDuplicateErrorMessage = false;
		$scope.searchDataErrorMessage = false;
		$scope.moveEntryEventSuccessMessage = false;
		$scope.groupStartEndDateError = false;
		$scope.groupenddaterequired = false;
		$scope.entryEventStatusChangeSuccessMessage = false;
		//$scope.searchText='';
		$scope.editGroupEventInventory = false;
		$scope.inventoryAdjustmentError = false;
	};
	
	$scope.resetForm = function()
	{
		$scope.editGroupEventInventory = false;
		$scope.inventoryAdjustmentError = false;
		$scope.groupEventDetailList = [];
		$scope.groupEvent = {};
		$scope.clearAllMessages();
		//document.getElementById("groupStartDateTime").value = "";
		//document.getElementById("groupCloseDateTime").value = "";
		$scope.changeText();
	}
	
	$scope.moveToAnotherGroup = function()
	{
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
					moveToAnotherGroup.groupGeneratedIdSeq = $scope.groupEvent.id;
					moveToAnotherGroup.previousGroupId = $scope.groupEvent.groupId;
					moveToAnotherGroup.pigCount = $scope.groupEvent.currentInventory;
					moveToAnotherGroup.groupStartDateTime = $scope.groupEvent.groupStartDateTime;
					return moveToAnotherGroup;
				}
			}
		});
		
		modalInstance.result.then( function(res) {   
			if(res.statusMessage==="Success")
			{
				$scope.getGroupEventInformation($scope.groupEvent.groupId,$scope.groupEvent.premiseId,false,true);
			}
		});
	}
	
	$scope.addGroupEvent = function()
	{
		if($scope.groupEvent.groupStartDateTime == null || $scope.groupEvent.groupStartDateTime === "")
		{
			$scope.groupdaterequired = true;
			console.log($scope.groupdaterequired);
			return;
		}
		
		if($scope.groupEvent.inventoryAdjustment != 0  && $scope.groupEvent.inventoryAdjustment > $scope.groupEvent.currentInventory)
		{
			$scope.inventoryAdjustmentError = true;
			return false;
		}
		if($scope.groupEventForm.$valid)
		{
			var startDate = new Date($scope.groupEvent["groupStartDateTime"]);
			$scope.groupEvent["groupStartDateTime"] = DateUtils.convertLocaleDateToServer(startDate);
			
			if($scope.groupEvent["groupCloseDateTime"] != null)
			{
			var endDate = new Date($scope.groupEvent["groupCloseDateTime"]);
			$scope.groupEvent["groupCloseDateTime"] = DateUtils.convertLocaleDateToServer(endDate);
			}
			
			var postParam = {
			
					"groupId" : $scope.groupEvent.groupId,
					"companyId" : $rootScope.companyId,
					"groupStartDateTime" : $scope.groupEvent.groupStartDateTime,
					"groupCloseDateTime" : $scope.groupEvent.groupCloseDateTime,					
					"remarks" : $scope.groupEvent.remarks,
					"phaseOfProductionTypeId" : $scope.groupEvent.phaseOfProductionTypeId,
					"fromMove" : false,
					"previousGroupId" : $scope.groupEvent.previousGroupId,
					"currentInventory" : $scope.groupEvent.currentInventory,
					"inventoryAdjustment" : $scope.groupEvent.inventoryAdjustment,
					"premiseId" : $scope.groupEvent.premiseId,
					"roomIds" : $scope.groupEvent.roomIds
					
				};
				if($scope.groupEvent.id != undefined && $scope.groupEvent.id >0)
				{
					postParam.id = $scope.groupEvent.id;
					postParam.active = $scope.groupEvent.active;
				}
				
				
			restServices.saveGroupEventInformation(postParam, function(data){
				if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.entryEventSuccessMessage = true;
						$scope.groupEvent.active = true;
						//$scope.groupEvent = postParam;
						$scope.editGroupEventInventory = false;
						$scope.getGroupEventInformation($scope.groupEvent.groupId,$scope.groupEvent.premiseId,true);
					}
				else
					{
						$scope.clearAllMessages();
						console.log(data.duplicateRecord);
						if(data.duplicateRecord)
						{
							$scope.groupEventDuplicateErrorMessage = true;
						}
						else
						{
						$scope.entryEventErrorMessage = true;
						}
					}
					$window.scrollTo(0,5);  
			});
		}
	}
	
	$scope.changeGroupEventStatus = function(flag)
	{
		$scope.clearAllMessages();
		if(!flag)
		{
			if($scope.groupEvent.groupCloseDateTime == null || $scope.groupEvent.groupCloseDateTime == "")
			{
				$scope.groupenddaterequired = true;
				return;
			}
			else
			{
				$scope.groupenddaterequired = false; 
				if($scope.groupEvent.groupStartDateTime > $scope.groupEvent.groupCloseDateTime)
				{
					$scope.groupStartEndDateError = true;
					return;
				}
				else
				{
					$scope.groupStartEndDateError = false;
				}
			}
		}
		console.log('change group event status');
			var postParam = {
					
					"id" : $scope.groupEvent.id,
					"companyId" : $rootScope.companyId,
					"active" : flag,
					"groupCloseDateTime" : $scope.groupEvent.groupCloseDateTime,
					
				};
			
			restServices.updateGroupEventInformation(postParam, function(data){
				if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.entryEventStatusChangeSuccessMessage = true;
						$scope.groupEvent.active = flag;
					}
				else
					{
						$scope.clearAllMessages();
						console.log(data.duplicateRecord);
						if(data.duplicateRecord)
						{
							$scope.groupEventDuplicateErrorMessage = true;
						}
						else
						{
						$scope.entryEventErrorMessage = true;
						}
					}
					$window.scrollTo(0,5);  
			});
		
	}
	
	$scope.getGroupEventInformation = function (searchGroupEvent,selectedPremise, flag,flag1)
	{
		console.log('Group ID is '+ $scope.searchText+"/"+selectedPremise);
		var postParam = {
				
				"groupId" : searchGroupEvent,
				"companyId" : $scope.companyId,
				"premiseId" : selectedPremise
			};
		
		restServices.getGroupEventInformation(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.editGroupEventInventory = false;
					$scope.groupEvent = data.payload[0];
					$scope.groupEventDetailList	= data.payload[1];	
					$scope.followedGroupIdString = data.payload[2];		
					$scope.getRooms(false);
					$window.scrollTo(0,550);
					if(flag)					
						$scope.entryEventSuccessMessage = true;		
					if(flag1)	
						$scope.moveEntryEventSuccessMessage = true;
				}
			else
				{
					$scope.groupEvent = {};
					$scope.groupEventDetailList=[];
					$scope.clearAllMessages();
					if(data.recordNotPresent)
					{
						$scope.searchDataErrorMessage = true;
					}
					else
					{
						$scope.entryEventErrorMessage = true;
					}
				}
		});
	}
	
	
	$scope.getTransferToGroupEventInformation = function (transferToGroupEvent,transferToPremise)
	{
		$scope.transferToGroupSearchError = false;
		$scope.transferToGroupSearchDataError = false;
		
		if($scope.groupEvent.premiseId == transferToPremise && $scope.groupEvent. groupId == transferToGroupEvent)
		{
			$scope.transferToGroupSearchError = true;
		}
		else
		{
			console.log('Group ID is '+ transferToGroupEvent+"/"+transferToPremise);
			var postParam = {
					
					"groupId" : transferToGroupEvent,
					"companyId" : $scope.companyId,
					"premiseId" : transferToPremise,
					"phaseOfProductionTypeId" : $scope.transferPhaseId
					
				};
			
			restServices.getGroupEventInformationForTransfer(postParam, function(data){
				console.log(data);
				if(!data.error)
					{	
						$scope.transferGroupEventData = data.payload[0];
					}
				else
					{
						$scope.transferGroupEventData = {};						
						$scope.transferToGroupSearchDataError = true;
					}
			});
		}
	}
	
	
	
	$scope.addGroupEventDetailData = function(groupEventId)
	{
		document.getElementById("searchedGroupid").value = $scope.groupEvent.groupId;
		document.getElementById("groupEventId").value = groupEventId;
		document.getElementById("searchPremiseId").value  = $scope.groupEvent.premiseId;
		document.getElementById("groupGeneratedIdSeq").value = $scope.groupEvent.id;
		document.getElementById("companyId").value = $scope.companyId;
		document.getElementById("groupStartDateTimeAdd").value = $scope.groupEvent.groupStartDateTime;
		
		document.forms['groupEventFormAdd'].action = 'addGroupEventDetail';
		document.forms['groupEventFormAdd'].submit();
	}
	
	$scope.editGroupEventInventoryAmount = function()
	{
		$scope.editGroupEventInventory = true;
	}
	
	
	$scope.promoteToFinish = function()
	{
		$scope.groupEvent.phaseOfProductionTypeId = 2;
		restServices.promoteToFinish($scope.groupEvent, function(data) {
			if(!data.error)
			{
				$scope.promoteToFinishMessage = true;
				$scope.getGroupEventInformation($scope.groupEvent.groupId,$scope.groupEvent.premiseId, true);
			}
		});
	}
	
	
	
	$scope.moveBackToNursery = function()
	{
		$scope.groupEvent.phaseOfProductionTypeId = 1;
		restServices.moveBackToNursery($scope.groupEvent, function(data) {
			if(!data.error)
			{
				$scope.promoteToFinishMessage = true;
				$scope.getGroupEventInformation($scope.groupEvent.groupId,$scope.groupEvent.premiseId, true);
			}
		});
	}
	
	$scope.transferToGroup = function()
	{
		var maxNum = $scope.groupEvent.currentInventory
		$scope.invalidTransferPigNum = false;
		if($scope.transferGroupEventData.transferredPigNum == 0 || $scope.transferGroupEventData.transferredPigNum > maxNum)
			$scope.invalidTransferPigNum = true;
		else
		{
			$scope.groupEvent.transferredToGroupId = $scope.transferGroupEventData.id;
			$scope.groupEvent.transferredPigNum = $scope.transferGroupEventData.transferredPigNum;
			$scope.groupEvent.transferredPigWt = $scope.transferGroupEventData.transferredPigWt;
			
			restServices.transferToGroup($scope.groupEvent, function(data) {
				if(!data.error)
				{
					$scope.promoteToFinishMessage = true;					
					$scope.getGroupEventInformation($scope.groupEvent.groupId,$scope.groupEvent.premiseId, true);
				}
			});
		}
	}
	
	$scope.transferToGroupInit = function(phaseOfProductionTypeId)
	{
		$scope.transferGroupEventData = {};
		$scope.transferPhaseId = phaseOfProductionTypeId;
		$scope.transferPhase = $scope.phaseOfProductionType[phaseOfProductionTypeId];
		$scope.transferToPremise = null;
		$scope.transferToGroupTxt = "";
	}
	
	$scope.promoteToPhase2 = function()
	{
		$scope.updatedRooms = [];
		restServices.getRoomsForPremise($scope.groupEvent["premiseId"], function(data){
			if(!data.error){
				$scope.roomType = data.payload;
				$scope.allRoomValues = [];
				angular.forEach($scope.roomType, function(key, value){					
                   var itemObj = {"id" : value, "label":key}  
				   $scope.allRoomValues.push(itemObj);
               })			   
			}
		});
	}
	
	$scope.confirmPromoteToPhase2 = function()
	{
		$scope.invalidRoomSelection = false;
		$scope.confirmPromoteToPhase2Status = true;
		if($scope.updatedRooms != null && 0 < $scope.updatedRooms.length)
		{
		  $scope.invalidRoomSelection = false;
		  $scope.groupEvent.roomIds = $scope.updatedRooms;
		  $scope.groupEvent.phaseOfProductionTypeId = 4;
		  restServices.weanToFinishPhase2($scope.groupEvent, function(data)
		  {
			  if(!data.error)
			  {
				  $('#phase2GroupModal').modal('hide');
				  $('body').removeClass('modal-open');
				  $('.modal-backdrop').remove();
				  $scope.confirmPromoteToPhase2Status = true;
				  $scope.getGroupEventInformation($scope.groupEvent.groupId,$scope.groupEvent.premiseId, true);
			  }
			  else
			 {
			 	  $scope.confirmPromoteToPhase2Status = false;
			  }
		  });
		}
		else
		{
		  $scope.invalidRoomSelection = true;
		}
	}
	
	
	$scope.undoWeanToFinishPhase2 = function()
	{
		restServices.undoWeanToFinishPhase2($scope.groupEvent, function(data){
			if(!data.error)
			{
				$scope.getGroupEventInformation($scope.groupEvent.groupId,$scope.groupEvent.premiseId, true);
			}
		});
	}
	
	$scope.transferToFarm = function()
	{
		document.getElementById("fromGroup").value=$scope.groupEvent.groupId;
		document.getElementById("fromGroupId").value=$scope.groupEvent.id;
		document.getElementById("transferToEntry").submit();
	}

});