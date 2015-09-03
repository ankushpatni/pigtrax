var feedEventController = pigTrax.controller('FeedEventController', function($scope,$rootScope,$modal,$http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.feedEvent = {};
	$scope.transportDestination;
	$scope.transportTruck;
	$scope.transportTrailer;
	$scope.feedEventDetailList={};
	$scope.siloList={};
	$scope.feedEventType={};	
	
	
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
					if(!data.error)
						{
							console.log(data);
							$scope.entryEventSuccessMessage = true;
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
	}
	
	$scope.getFeedEvent = function ()
	{
		console.log('Group ID is '+ $scope.searchText);
		var postParam = {
				
				"ticketNumber" : $scope.searchText,
			};
		restServices.getFeedEventInformation(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.clearAllMessages();
					$scope.feedEvent = data.payload[0];
					$scope.feedEventDetailList	= data.payload[1];	
					$scope.feedEvent.transportJourney = data.payload[2];
					$window.scrollTo(0,550);
							
				}
			else
				{
					$scope.feedEvent = {};
					$scope.feedEventDetailList={};
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
	
	$scope.addFeedEventDetail = function()
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
					return feedEventDetailData;
				}
			}
		});
		
		modalInstance.result.then( function(res) { 
			$scope.entryEventDetailSuccessMessage = true;
			$scope.getFeedEvent();
		});
	}
	
});