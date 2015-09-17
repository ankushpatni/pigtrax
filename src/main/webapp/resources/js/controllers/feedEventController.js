var feedEventController = pigTrax.controller('FeedEventController', function($scope,$rootScope,$modal,$http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.feedEvent = {};
	$scope.transportDestination;
	$scope.transportTruck;
	$scope.transportTrailer;
	$scope.feedEventDetailList=[];
	$scope.siloList={};
	$scope.feedEventType={};
	$scope.groupEvent={};
	
	
	$scope.setCompanyId = function(companyId,ticketNumber)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
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
		
		var res2 = $http.get('rest/util/getFeedEventDetailMasterData?companyId='+$rootScope.companyId);
		res2.success(function(data, status, headers, config) {
			console.log(data);
			$scope.siloList = data.payload[1];	
			$scope.feedEventType = data.payload[0];
			$scope.groupEvent = data.payload[2]
		});
		res2.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		if( ticketNumber)
		{
			$scope.searchText = ticketNumber;
			$scope.getFeedEvent();
			$scope.entryEventDetailSuccessMessage = true;
		}
	};
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.feedEvent = {};
		$scope.changeText();
	}
	
	$scope.clearAllMessages = function()
	{
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.groupEventDuplicateErrorMessage = false;
		$scope.searchDataErrorMessage = false;
		$scope.entryEventDetailSuccessMessage = false;
	};
	
	$scope.addFeedEvent = function() 
	{
		if($scope.feedEventForm.$valid)
			{
				if(document.getElementById("initialFeedEntryDateTime").value != "")
				{
					$scope.feedEvent.initialFeedEntryDateTime =  document.getElementById("initialFeedEntryDateTime").value;
				}
				$scope.clearAllMessages();
				
				restServices.addFeedEvent($scope.feedEvent, function(data){
				console.log(data);
					if(!data.error)
						{
							
							$scope.entryEventSuccessMessage = true;
							$scope.getFeedEvent($scope.feedEvent.ticketNumber,true);
						}
					else
						{
							$scope.clearAllMessages();
							if(data.duplicateRecord)
							{
								$scope.groupEventDuplicateErrorMessage = true;
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
	
	$scope.getFeedEvent = function (ticketNumber,flag,flag1)
	{
		console.log('Group ID is '+ $scope.searchText);
		var postParam = {
				
				"ticketNumber" : ticketNumber,
			};
			
			console.log();
		restServices.getFeedEventInformation(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
					
					$scope.feedEvent = data.payload[0];
					$scope.feedEventDetailList	= data.payload[1];	
					$scope.clearAllMessages();
					$scope.feedEvent.transportJourney = data.payload[2];
					if(flag)
					{
						$scope.entryEventSuccessMessage = true;
					}
					if(flag1)
					{
						$scope.entryEventDetailSuccessMessage = true;
					}
					$window.scrollTo(0,550);
							
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
					transportJourneyMasterData.transportJourney = $scope.feedEvent.transportJourney;					
					return transportJourneyMasterData;
				}
			}
		});
		
		modalInstance.result.then( function(res) { 
			console.log(res);
			$scope.feedEvent.transportJourney = res;
		});
	}
	
	$scope.addFeedEventDetail = function(id)
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
	}
	
});