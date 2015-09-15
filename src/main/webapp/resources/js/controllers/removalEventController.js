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
	
	
	$scope.setCompanyId = function(companyId,removalId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		/*var res1 = $http.get('rest/transportJourney/getTransportJourneyMasterData?generatedCompanyId='+$scope.companyId);
		res1.success(function(data, status, headers, config) {
			console.log(data);
			$scope.transportDestination = data.payload[0];	
			$scope.transportTruck = data.payload[1];
			$scope.transportTrailer = data.payload[2];
			
		});
		res1.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	*/
		
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
			$scope.getRemovalEvent(removalId);
			$scope.entryEventDetailSuccessMessage = true;
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
	
	console.log(removalId);
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
						$scope.entryEventDetailSuccessMessage = true;
					}
					$window.scrollTo(0,550);
					if($scope.removalEvent.removalTypeId ==1  || $scope.removalEvent.removalTypeId ==2)
					{
						$scope.exceptSalesFlag = true;
					}
					else
					{
						$scope.exceptSalesFlag = false;
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
	
	/*$scope.addTransportJourney = function()
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
					transportJourneyMasterData.transportJourney = $scope.feedEvent.transportJourney;					
					return transportJourneyMasterData;
				}
			}
		});
		
		modalInstance.result.then( function(res) { 
			console.log(res);
			$scope.feedEvent.transportJourney = res;
		});
	}*/
	
	/*$scope.addFeedEventDetail = function(id)
	{
		var modalInstance = $modal.open ({
			templateUrl: 'addFeedEventDetail',
			controller: 'addFeedEventDetailCtrl',
			backdrop:true,
			windowClass : 'cp-model-window',
			resolve:{
				feedEventDetailData : function(){
					var feedEventDetailData={};
					feedEventDetailData.siloList = $scope.siloList;	
					feedEventDetailData.feedEventType = $scope.feedEventType ;
					feedEventDetailData.feedEventId = $scope.feedEvent.feedContentId;
					feedEventDetailData.ticketNumber = $scope.feedEvent.ticketNumber;
					feedEventDetailData.companyId = $scope.companyId;
					feedEventDetailData.feedId = $scope.feedEvent.id;
					feedEventDetailData.id = id;
					feedEventDetailData.groupEvent = $scope.groupEvent;
					return feedEventDetailData;
				}
			}
		});
		
		modalInstance.result.then( function(res) { 
			$scope.entryEventDetailSuccessMessage = true;
			$scope.getFeedEvent($scope.feedEvent.ticketNumber,false,true);
		});
	}*/
	
	$scope.addRemovalExceptSalesData = function(removalExceptId)
	{
	console.log($scope.removalEvent);
		document.getElementById("removalIdEntered").value = $scope.removalEvent.removalId;
		document.getElementById("removalGeneratedId").value = $scope.removalEvent.id;
		document.getElementById("removalExceptSalesId").value = removalExceptId;
		document.getElementById("companyId").value = $scope.companyId;
		
		document.forms['removalExceptSalesDisplayForm'].action = 'addRemovalEventExceptSalesDetails';
		document.forms['removalExceptSalesDisplayForm'].submit();
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
						$scope.getRemovalEvent($scope.removalEvent.removalId,false,true);
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