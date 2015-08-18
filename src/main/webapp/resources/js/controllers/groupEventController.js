var groupEventController = pigTrax.controller('GroupEventController', function($scope,$rootScope, $http,$window,$modal,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.groupEvent = {};
	$scope.groupEventDetailList = [];
	$scope.confirmClick = false;
	$scope.phaseOfProductionType = {};
	
	$scope.setCompanyId = function(companyId,searchedGroupid)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		restServices.getPhaseOfProductionType("", function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.phaseOfProductionType = data.payload[0];	
									
				}
			else
				{
					console.log( "failure message: " + {data: data});
				} 
		});
		
		if( searchedGroupid)
		{
			$scope.searchText = searchedGroupid
			$scope.getGroupEventInformation(searchedGroupid);
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
	
	$scope.getGroupEventInformation = function (searchGroupEvent)
	{
		console.log('Group ID is '+ $scope.searchText);
		
		restServices.getGroupEventInformation(searchGroupEvent, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.groupEvent = data.payload;	
					$window.scrollTo(0,550);					
				}
			else
				{
					$scope.groupEvent = {};
					if(data.recordNotPresent)
					{
						$scope.searchDataErrorMessage = true;
					}
					else
					{
						$scope.entryEventErrorMessage = true;
					}
				}
				//$window.scrollTo(0, 5);  
		});
	}
	
/*	$scope.addGroupEventDetailData = function () {
			var modalInstance = $modal.open ({
    			templateUrl: 'addGroupEventDetail',
    			controller: 'addGroupEventDetailCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
					addGroupEventDetailData : function(){
						var addGroupEventDetailData={};
						if($scope.searchText != undefined || $scope.searchText !== "")
						{
							addGroupEventDetailData.groupId= $scope.searchText;
						}
						else
						{
							addGroupEventDetailData.groupId = $scope.groupEvent.groupId;
						}
						addGroupEventDetailData.phaseOfProductionType = $scope.phaseOfProductionType;
						addGroupEventDetailData.companyId = $scope.companyId;
    					return addGroupEventDetailData;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
					//$scope.getBarnList($scope.premisesId,$scope.generatedPremisesId);				
				}
    		});
    }*/
	
	$scope.addGroupEventDetailData = function(groupId,groupDetailId)
	{
		document.getElementById("searchedGroupid").value = $scope.groupEvent.groupId;
		document.getElementById("groupEventId").value = groupEventId;
		document.getElementById("groupGeneratedIdSeq").value = $scope.groupEvent.id;
		document.getElementById("companyId").value = $scope.companyId;
		
		document.forms['groupEventFormAdd'].action = 'addGroupEventDetail';
		document.forms['groupEventFormAdd'].submit();
	}

});