var feedEventController = pigTrax.controller('RemovalEventController',function($scope,$rootScope,$modal,$http,$window,restServices,$confirm, DateUtils) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.removalEvent = {};
	$scope.transportDestination;
	$scope.transportTruck;
	$scope.transportTrailer;
	$scope.premiseList={};
	$scope.removalEventType={};
	$scope.groupEvent={};
	$scope.removalExceptSalesList={};
	$scope.transferList={};
	$scope.exceptSalesFlag= false;
	$scope.salesEventFlag = false;
	$scope.transferEventFlag = false;
	$scope.salesEventList={};
	$scope.DateUtils = DateUtils;
	$scope.premiseNameMap={};
	$scope.dataLoaded = true;
	
	
	$scope.setCompanyId = function(companyId,removalId,fromExcept, actionResult)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		
		var res2 = $http.get('rest/util/getRemovalExceptSalesMasterData?companyId='+$rootScope.companyId);
		res2.success(function(data, status, headers, config) {
			console.log(data);
			
			var removalEventTypeMap = data.payload[0];
			$scope.removalEventTypeKeys = removalEventTypeMap['RemovalEventKey'];			
			$scope.removalEventType = removalEventTypeMap['RemovalEventValue'];
			//$scope.removalEventType = data.payload[0];
			$scope.pigInfoList = data.payload[1];
			//$scope.premiseList = data.payload[2];
			$scope.groupEventList = data.payload[3];
			$scope.premiseNameMap = data.payload[5];
		});
		res2.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	

var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId+'&premisesType=null');
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		if( removalId)
		{
			//$scope.searchText = ticketNumber;
			console.log("fromExcept "+fromExcept);
			
			if(fromExcept === 'true')
			{
				$scope.getRemovalEvent(removalId,false,true);
			}
			else
			{	
				$scope.getRemovalEvent(removalId,true,false);
			}
			
		}	
			if(actionResult == 'true')
			{
			  $scope.actionResult = true;
			}
		else
			{
			 $scope.actionResult = false;
			}		
	};
	
	$scope.loadPigAndGroupInfo = function()
	{
		var res = $http.get('rest/entryEvent/getPigInfoList?companyId='+$rootScope.companyId+'&premiseId='+$scope.premiseId);
		res.success(function(data, status, headers, config) {
		console.log(data.payload);
			$scope.pigInfoListSearch = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		var res = $http.get('rest/groupEvent/getGroupEventByPremiseWithoutStatus?premiseId='+$scope.premiseId);
		res.success(function(data, status, headers, config) {
			$scope.groupEventListSearch = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.removalEvent = {};
		$scope.actionResult = false;
	}
	
	$scope.clearAllMessages = function()
	{
		$scope.entryEventSuccessMessage = false;
		$scope.entrySalesDetailSuccessMessage = false;
		$scope.entryExceptSalesDetailsSuccessMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.removalEventDuplicateErrorMessage = false;
	};
	
		
	$scope.addRemovalEvent = function() 
	{
		if($scope.removalEventForm.$valid)
			{
				$scope.clearAllMessages();
				
				restServices.addRemovalEvent($scope.removalEvent, function(data){
				console.log(data);
					if(!data.error)
						{
							
							$scope.entryEventSuccessMessage = true;
							$scope.getRemovalEvent($scope.removalEvent.removalId,true);
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
	
	$scope.editRemovalData = function (removalEventData) {
		console.log("removalEventData "+removalEventData);
		var modalInstance = $modal.open ({
			templateUrl: 'editRemoval',
			controller: 'editRemovalCtrlr',
			backdrop:true,
			windowClass : 'cp-model-window',
			resolve:{
				premisesData : function(){						
					return removalEventData;
				}
			}
		});
		
		modalInstance.result.then( function(res) {    			
			if(res.statusMessage==="Success")
			{
				$scope.searchRemovalEvent();
//				$scope.getPremisesList($scope.companyId,$scope.generatedCompanyId);
			}
		});
}
	
	$scope.getRemovalEvent = function (removalId,flag,flag1)
	{
		
		console.log(removalId);
	
	if(!removalId || removalId===undefined)
	{
		removalId = $scope.searchText;
	}
	
		var postParam = {
				
				"removalId" : removalId,
			};
			
		restServices.getRemovalEventInformation(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.removalEvent = data.payload[0];
					$scope.removalExceptSalesList = data.payload[1];
					$scope.clearAllMessages();
					if(flag)
					{
						$scope.entryEventSuccessMessage = true;
					}
					if(flag1)
					{
						$scope.entryExceptSalesDetailsSuccessMessage = true;
					}
					$window.scrollTo(0,550);
					if($scope.removalEvent.removalTypeId != 3)
					{
						$scope.exceptSalesFlag = true;
						$scope.salesEventFlag = false;
					}
					else if($scope.removalEvent.removalTypeId == 3) 
					{
						$scope.exceptSalesFlag = false;
						$scope.salesEventFlag = true;
					}
					
				}
			else
				{
					$scope.resetForm();
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
	
		
	$scope.addDifferentRemovalEvent = function(removalExceptId)
	{
		console.log($scope.removalEvent);
		
		document.getElementById("companyId").value = $scope.companyId;
		
		if(document.getElementById("radAdd1").checked)
		{
			document.forms['removalEventForm'].action = 'addRemovalEventExceptSalesDetails';
			document.getElementById("removalTypeId1").value = 1;
		}			
		else if(document.getElementById("radAdd2").checked)
		{
			document.forms['removalEventForm'].action = 'addRemovalEventExceptSalesDetails';
			document.getElementById("removalTypeId1").value = 9;
		}			
		else
		{
			document.forms['removalEventForm'].action = 'addSalesEventDetails';
			document.getElementById("removalTypeId1").value = 3;
		}
		console.log(document.getElementById("companyId").value);
		document.forms['removalEventForm'].submit();
	}
	
	$scope.deleteRemovalExceptSalesData = function(removalRow,message)
	{
		if(removalRow.groupEventId==0)
		{
			 $confirm({text: message}).then(function() {
				 $scope.deleteRemovalExceptSales(removalRow);
	        });
		}
		if(removalRow.pigInfoId==0)
		{
			var groupevent = $scope.groupEventList[removalRow.groupEventId];
			if(!groupevent.active)
			{
				 $confirm({text: message}).then(function() {
					 $scope.deleteRemovalExceptSales(removalRow);
		        });
			}
			else
			{
				$scope.deleteRemovalExceptSales(removalRow);
			}
		}
	}
	
	$scope.deleteRemovalExceptSales = function(removalRow)
	{
	console.log(removalRow);
	removalRow.companyId = $scope.companyId;
		restServices.deleteRemovalExceptSales(removalRow, function(data){
			console.log(data);
				if(!data.error)
					{
						
						$scope.entryEventSuccessMessage = true;
						$scope.searchRemovalEvent();
						//$scope.getRemovalEvent($scope.removalEvent.removalId,false,true);
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
	
	$scope.searchRemovalEvent = function ()
	{
		
		if(document.getElementById("rad1").checked)
		{
			groupId = $scope.searchText;
			var postParam = {
					"groupId" : $scope.searchText,
					"companyId" : $rootScope.companyId,
					"premisesId" : $scope.premiseId
				};
			$scope.getSearchRemovalEvent(postParam);
		}			
		else if(document.getElementById("rad2").checked)
		{
			pigId = $scope.searchText;
			var postParam = {
					"pigId" : $scope.searchText,
					"companyId" : $rootScope.companyId,
					"premisesId" : $scope.premiseId
			};
			$scope.getSearchRemovalEvent(postParam);
		}			
		/*else
		{
			removalId = $scope.searchText;
			$scope.getRemovalEvent(removalId);
		}*/
	}
	
	$scope.getSearchRemovalEvent = function (postParam)
	{
		$scope.dataLoaded = false;
		console.log(postParam);
		restServices.getRemovalEventInformationList(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
				var flag;
				
				if(data.payload[0] != null && data.payload[0].length>0)
				{
					$scope.removalExceptSalesList=data.payload[0];
					
					$scope.exceptSalesFlag= true;
				}
				else
				{
					$scope.exceptSalesFlag= false;
				}
				
				if(data.payload[1] != null && data.payload[1].length>0)
				{
					$scope.salesEventList=data.payload[1];
					$scope.salesEventFlag = true;
				}
				else
				{
					$scope.salesEventFlag = false;
				}
				
				if(data.payload[2] != null && data.payload[2].length>0)
				{
					$scope.transferList=data.payload[2];
					$scope.transferEventFlag = true;
				}
				else
				{
					$scope.transferEventFlag = false;
				}
				
				if(!($scope.salesEventFlag || $scope.exceptSalesFlag))
				{
					$scope.searchDataErrorMessage = true;
				}
				else
				{
					$scope.searchDataErrorMessage = false;
				}
				$scope.actionResult = false;
				
				
				}
			else
				{
					$scope.resetForm();
				}
			$scope.dataLoaded = true;
		});
	}
	
	$scope.deleteSalesEventDetailsData = function(removalRow,message)
	{
		if(removalRow.groupEventId==0)
		{
			 $confirm({text: message}).then(function() {
				 $scope.deleteSalesEventDetails(removalRow);
	        });
		}
		if(removalRow.pigInfoId==0)
		{
			var groupevent = $scope.groupEventList[removalRow.groupEventId];
			if(!groupevent.active)
			{
				 $confirm({text: message}).then(function() {
					 $scope.deleteSalesEventDetails(removalRow);
		        });
			}
			else
			{
				$scope.deleteSalesEventDetails(removalRow);
			}
		}
	}
	
	$scope.deleteSalesEventDetails = function(removalRow)
	{
	console.log(removalRow);
	removalRow.companyId = $scope.companyId;
		restServices.deleteSalesEventDetails(removalRow, function(data){
			console.log(data);
				if(!data.error)
					{
						
						$scope.entryEventSuccessMessage = true;
						$scope.searchRemovalEvent();
						//$scope.getRemovalEvent($scope.removalEvent.removalId,false,true);
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
	
});