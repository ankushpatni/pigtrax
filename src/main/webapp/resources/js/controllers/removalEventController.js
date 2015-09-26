var feedEventController = pigTrax.controller('RemovalEventController',function($scope,$rootScope,$modal,$http,$window,restServices,$confirm) {
	
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
	$scope.exceptSalesFlag= false;
	$scope.salesEventFlag = false;
	$scope.salesEventList={};
	
	
	$scope.setCompanyId = function(companyId,removalId,fromExcept)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		
		var res2 = $http.get('rest/util/getRemovalExceptSalesMasterData?companyId='+$rootScope.companyId);
		res2.success(function(data, status, headers, config) {
			console.log(data);
			
			$scope.removalEventType = data.payload[0];
			$scope.pigInfoList = data.payload[1];
			$scope.premiseList = data.payload[2];
			$scope.groupEventList = data.payload[3]
		});
		res2.error(function(data, status, headers, config) {
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
	};
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.removalEvent = {};
		$scope.changeText();
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
					if($scope.removalEvent.removalTypeId ==1  || $scope.removalEvent.removalTypeId ==2 || $scope.removalEvent.removalTypeId ==3)
					{
						$scope.exceptSalesFlag = true;
						$scope.salesEventFlag = false;
					}
					else
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
			document.getElementById("removalTypeId1").value = 3;
		}			
		else
		{
			document.forms['removalEventForm'].action = 'addSalesEventDetails';
			document.getElementById("removalTypeId1").value = 4;
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
				};
			$scope.getSearchRemovalEvent(postParam);
		}			
		else if(document.getElementById("rad2").checked)
		{
			pigId = $scope.searchText;
			var postParam = {
					"pigId" : $scope.searchText,
					"companyId" : $rootScope.companyId,
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
	console.log(postParam);
		restServices.getRemovalEventInformationList(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
				if(data.payload[0] != null && data.payload[0].length>0)
				{
					$scope.exceptSalesFlag= true;
				}
				else
				{
					$scope.exceptSalesFlag= false;
				}
				
				if(data.payload[1] != null && data.payload[0].length>0)
				{
					$scope.salesEventFlag = true;
				}
				else
				{
					$scope.salesEventFlag = false;
				}
				
				$scope.removalExceptSalesList=data.payload[0];
				$scope.salesEventList=data.payload[1];
				
				
			
					/*var modalInstance = $modal.open ({
						templateUrl: 'openSelectBox',
						controller: 'openSelectBoxCtrl',
						backdrop:true,
						windowClass : 'cp-model-window',
						resolve:{
							openSelectBoxData : function(){
								var openSelectBox={};
								openSelectBox.data = data.payload;				
								return openSelectBox;
							}
						}
					});
		
					modalInstance.result.then( function(res) { 
						console.log(res);
						$scope.removalExceptSales.transportJourney = res;
					});*/
		}
			else
				{
					$scope.resetForm();
				}
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