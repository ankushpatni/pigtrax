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
		
	
	$scope.setCompanyId = function(companyId,searchedGroupid)
	{
	console.log(companyId);
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		/*restServices.getPhaseOfProductionType("", function(data){
			if(!data.error)
				{
					$scope.phaseOfProductionType = data.payload[0];	
				}
			else
				{
					console.log( "failure message: " + {data: data});
				} 
		});*/
		
		var res2 = $http.get('rest/util/getPhaseOfProductionType?companyId='+$scope.companyId);
			res2.success(function(data, status, headers, config) {
				console.log(data);
				$scope.phaseOfProductionType = data.payload[0];	
				$scope.roomList = data.payload[1];
				$scope.phaseOfProductionTypeForNewAdd = data.payload[0];
				console.log($scope.phaseOfProductionTypeForNewAdd );
				for( var x in $scope.phaseOfProductionTypeForNewAdd) {
					if( x == 2 || x == 4 || x == 5)
						{		
						delete $scope.phaseOfProductionTypeForNewAdd[x];
						}
					}
				
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
				
				console.log(data);
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
	};
	
	$scope.resetForm = function()
	{
		/*$scope.clearAllMessages();
		$scope.groupEvent = {};
		$scope.changeText();*/
		
		var modalInstance = $modal.open ({
			templateUrl: 'transportJourney',
			controller: 'addTransportJourneyCtrl',
			backdrop:true,
			windowClass : 'cp-model-window',
			resolve:{
				transportJourneyData : function(){
					var transportJourneyData={};
					transportJourneyData.transportDestination = $scope.transportDestination;
					transportJourneyData.transportTruck = $scope.transportTruck;
					transportJourneyData.transportTrailer = $scope.transportTrailer;
					return transportJourneyData;
				}
			}
		});
		
		modalInstance.result.then( function(res) {    			
			if(res.statusMessage==="SUCCESS")
			{
				$scope.getPenList($scope.roomId,$scope.generatedRoomId);				
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
	
	$scope.changeGroupEventStatus = function()
	{
		console.log('change group event status');
	}
	
	$scope.getGroupEventInformation = function (searchGroupEvent,flag)
	{
		console.log('Group ID is '+ $scope.searchText);
		var postParam = {
				
				"groupId" : searchGroupEvent,
				"companyId" : $rootScope.companyId,
			};
		
		restServices.getGroupEventInformation(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.groupEvent = data.payload[0];
					$scope.groupEventDetailList	= data.payload[1];				
					$window.scrollTo(0,550);
					if(flag)					
					$scope.entryEventSuccessMessage = true;					
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