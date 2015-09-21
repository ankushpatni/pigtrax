var groupEventController = pigTrax.controller('GroupEventController', function($scope,$rootScope, $http,$window,$modal,restServices) {
	
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
		
	
	$scope.setCompanyId = function(companyId,searchedGroupid)
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
			$scope.getGroupEventInformation(searchedGroupid,true);
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
		
	};
	
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
		$scope.searchText='';
	};
	
	$scope.resetForm = function()
	{
		$scope.groupEventDetailList = [];
		$scope.groupEvent = {};
		$scope.clearAllMessages();
		document.getElementById("groupStartDateTime").value = "";
		document.getElementById("groupCloseDateTime").value = "";
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
					moveToAnotherGroup.groupStartDateTime = document.getElementById("groupStartDateTime").value;
					return moveToAnotherGroup;
				}
			}
		});
		
		modalInstance.result.then( function(res) {   
			if(res.statusMessage==="Success")
			{
				$scope.getGroupEventInformation($scope.groupEvent.groupId,false,true);
			}
		});
	}
	
	$scope.addGroupEvent = function()
	{
		if(document.getElementById("groupStartDateTime").value === "")
		{
			$scope.groupdaterequired = true;
			console.log($scope.groupdaterequired);
			return;
		}
		if($scope.groupEventForm.$valid)
		{
		
			var postParam = {
			
					"groupId" : $scope.groupEvent.groupId,
					"companyId" : $rootScope.companyId,
					"groupStartDateTime" : document.getElementById("groupStartDateTime").value,
					"groupCloseDateTime" : document.getElementById("groupCloseDateTime").value,					
					"remarks" : $scope.groupEvent.remarks,
					"phaseOfProductionTypeId" : $scope.groupEvent.phaseOfProductionTypeId,
					"fromMove" : false,
					"previousGroupId" : $scope.groupEvent.previousGroupId,
					"currentInventory" : $scope.groupEvent.currentInventory,
					
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
			if(document.getElementById("groupCloseDateTime").value === "")
			{
				$scope.groupenddaterequired = true;
				return;
			}
			else
			{
				$scope.groupenddaterequired = false; 
				if($scope.groupEvent.groupStartDateTime > document.getElementById("groupCloseDateTime").value)
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
					"groupCloseDateTime" : document.getElementById("groupCloseDateTime").value,
					
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
	
	$scope.getGroupEventInformation = function (searchGroupEvent,flag,flag1)
	{
		console.log('Group ID is '+ $scope.searchText);
		var postParam = {
				
				"groupId" : searchGroupEvent,
				"companyId" : $scope.companyId,
			};
		
		restServices.getGroupEventInformation(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.groupEvent = data.payload[0];
					$scope.groupEventDetailList	= data.payload[1];	
					$scope.followedGroupIdString = data.payload[2];						
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
	
	$scope.addGroupEventDetailData = function(groupEventId)
	{
		document.getElementById("searchedGroupid").value = $scope.groupEvent.groupId;
		document.getElementById("groupEventId").value = groupEventId;
		document.getElementById("groupGeneratedIdSeq").value = $scope.groupEvent.id;
		document.getElementById("companyId").value = $scope.companyId;
		document.getElementById("groupStartDateTimeAdd").value = document.getElementById("groupStartDateTime").value;
		
		document.forms['groupEventFormAdd'].action = 'addGroupEventDetail';
		document.forms['groupEventFormAdd'].submit();
	}

});