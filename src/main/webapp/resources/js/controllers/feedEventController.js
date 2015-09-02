
transportJourneyService = pigTrax.service('transportJourneyService', function() {
    var transportJourney = "hello";
});

feedEventService = pigTrax.service('feedEventService', function() {
    var feedEvent = "hello";
	var feedEventData={};
});

var feedEventController = pigTrax.controller('FeedEventController', function($scope,$rootScope, $http,$window,$modal,restServices,transportJourneyService,feedEventService) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.feedEvent = {};
	$scope.transportDestination;
	$scope.transportTruck;
	$scope.transportTrailer;
	$rootScope.selectedTrailerDetails = {};
	$scope.feedEventDetailList={};
	
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;		
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
	};
	
	$scope.addFeedEvent = function() 
	{
		if($scope.feedEventForm.$valid)
			{
				if(document.getElementById("initialFeedEntryDateTime").value != "")
					$scope.feedEvent.initialFeedEntryDateTime =  document.getElementById("initialFeedEntryDateTime").value;
				$scope.feedEvent.transportJourneyId = 1;
				$scope.feedEvent.transportJourney = transportJourneyService.transportJourney;
				console.log($scope.feedEvent.transportJourney);
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
					//feedEventService.feedEventData.feedEventId = $scope.feedEvent.id;
					//feedEventService.feedEventData.feedContentId = $scope.feedEvent.feedContentId;
					$rootScope.feedEventId = $scope.feedEvent.id;
					$rootScope.feedContentId = $scope.feedEvent.feedContentId;
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
	
});