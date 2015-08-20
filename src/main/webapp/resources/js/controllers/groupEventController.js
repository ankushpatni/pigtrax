var groupEventController = pigTrax.controller('GroupEventController', function($scope,$rootScope, $http,$window,$modal,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.groupEvent = {};
	$scope.groupEventDetailList = [];
	$scope.confirmClick = false;
	$scope.phaseOfProductionType = {};
	$scope.roomList={};
	
	$scope.setCompanyId = function(companyId,searchedGroupid)
	{
	console.log(companyId);
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		restServices.getPhaseOfProductionType("", function(data){
			if(!data.error)
				{
					$scope.phaseOfProductionType = data.payload[0];	
					$scope.groupEvent = data.payload[0];
					$scope.groupEventDetailList	= data.payload[1];
									
				}
			else
				{
					console.log( "failure message: " + {data: data});
				} 
		});
		
		if( searchedGroupid)
		{
			$scope.searchText = searchedGroupid
			$scope.getGroupEventInformation(searchedGroupid,true);
			$scope.entryEventSuccessMessage = true;
		}
		
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
		$scope.clearAllMessages();
		$scope.groupEvent = {};
	}
	
	$scope.addGroupEvent = function()
	{
	console.log(document.getElementById("groupStartDateTime").value);
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
		
		document.forms['groupEventFormAdd'].action = 'addGroupEventDetail';
		document.forms['groupEventFormAdd'].submit();
	}

});