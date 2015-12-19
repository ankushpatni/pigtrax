var feedEventController = pigTrax.controller('FeedEventController', function($scope,$rootScope,$modal,$http,$window,restServices, DateUtils) {
	
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
	$scope.rationType={};
	$scope.DateUtils = DateUtils;
	
	
	$scope.loadPremises = function()
	{
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId);
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		   var  dateObj = Date.parse(dateVal);		   
		   if(dateObj == null)
			{
			   if(fieldName == "feedDate")
				{
					   $scope.initialFeedEntryDateTimerequired = true;
					   $scope.feedEvent["initialFeedEntryDateTime"] = null;
				}			  
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "feedDate")
				{
				   $scope.initialFeedEntryDateTimerequired = false;
				   $scope.feedEvent["initialFeedEntryDateTime"] = DateUtils.convertLocaleDateToServer(dateObj);
				}			  
			}
		}
		else
		{
			if(fieldName == "feedDate")
			{
				   $scope.initialFeedEntryDateTimerequired = true;
				   $scope.feedEvent["initialFeedEntryDateTime"] = null;
			}		   
		}
	  }
	}
	
	
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
			$scope.groupEvent = data.payload[2];
			$scope.rationType = data.payload[3];
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
		$scope.loadPremises();		
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
		if($scope.feedEvent["initialFeedEntryDateTime"] == null || $scope.feedEvent["initialFeedEntryDateTime"] == undefined || $scope.feedEvent["initialFeedEntryDateTime"] == ""  )
		{
			$scope.initialFeedEntryDateTimerequired = true;
		}
		else
			$scope.initialFeedEntryDateTimerequired = false;
		if($scope.feedEventForm.$valid)
			{
				if($scope.feedEvent["initialFeedEntryDateTime"] != "")
				{					
				
					$scope.clearAllMessages();
					
					restServices.addFeedEvent($scope.feedEvent, function(data){
					console.log(data);
						if(!data.error)
							{
								
								$scope.entryEventSuccessMessage = true;
								$scope.getFeedEvent($scope.feedEvent.ticketNumber,$scope.feedEvent.premiseId);
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
		
		$window.scrollTo(0,5);
	}
	
	$scope.getFeedEvent = function (ticketNumber,selectedPremise, flag,flag1)
	{
		console.log('Group ID is '+ $scope.searchText);
		if(ticketNumber != null && ticketNumber != "" && selectedPremise != null && selectedPremise != "")
			{
		var postParam = {
				
				"ticketNumber" : ticketNumber,
				"selectedPremise" : selectedPremise
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
			$scope.getFeedEvent($scope.feedEvent.ticketNumber,$scope.feedEvent.premiseId, false,true);
		});
	}
	
});