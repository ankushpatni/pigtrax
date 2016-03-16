pigTrax.controller('addFeedEventDetailCtrl', function($scope, $rootScope, $http, $window,restServices,$modalInstance,feedEventDetailData, DateUtils) {
	
	$scope.siloList=feedEventDetailData.siloList;
	$scope.feedEventType=feedEventDetailData.feedEventType;
	$scope.feedEventDetail={};
	$scope.feedEventDetail.feedEventId = feedEventDetailData.feedId;
	$scope.feedEventId = feedEventDetailData.feedEventId;
	$scope.ticketNumber = feedEventDetailData.ticketNumber;
	$scope.companyId = feedEventDetailData.companyId;
	$scope.groupEvent = [];
	$scope.premiseId = feedEventDetailData.premiseId;
	
	console.log(feedEventDetailData);
	
	$scope.getActiveGroupsInPremise = function()
	{
		restServices.getActiveGroupEventsInPremise($scope.premiseId, function(data){
			if(!data.error)
				{
					$scope.groupEvent = data.payload;
				}
		})
	}
	$scope.getActiveGroupsInPremise();
	
	
	$scope.getSiloListForPremise = function()
	{
		restServices.getSiloListForPremise($scope.premiseId, function(data){
			if(!data.error)
				{
					$scope.siloList = data.payload;
				}
		})
	}
	$scope.getSiloListForPremise();
	
	
	
	if(feedEventDetailData.id)
	{
		
		var res1 = $http.get('rest/feedEvent/getFeedEventDetailInformation?id='+feedEventDetailData.id);
		res1.success(function(data, status, headers, config) {
			console.log(data);
			$scope.feedEventDetail = data.payload;			
		});
		res1.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		
	}
	
	$scope.init= function()
    {
		
	}
	
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		 //  var  dateObj = Date.parse(dateVal);	
		   var  dateObj = DateUtils.parse(dateVal,"dd/MM/yyyy");
		   if(dateObj == null)
			{
			   if(fieldName == "feedEventDate")
				{
					   $scope.feedEventDateFlag = true;
					   $scope.feedEventDetail["feedEventDate"]= null;
				}			  
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "feedEventDate")
				{
				   $scope.feedEventDateFlag = false;
				   $scope.feedEventDetail["feedEventDate"] = DateUtils.convertLocaleDateToServer(dateObj);
				}			  
			}
		}
		else
		{
			if(fieldName == "feedEventDate")
			{
				$scope.feedEventDateFlag = true;
				$scope.feedEventDetail["feedEventDate"]= null;
			}
		}
	  }
	}
	
	
	$scope.gotoFeedEvent = function()
	{

		document.getElementById("feedEventTicketNumber").value = $scope.ticketNumber;
		document.getElementById("selectedCompany").value = $scope.companyId;		
		document.forms['feedEventDetailAddForm'].action = 'feedEvent';
		document.forms['feedEventDetailAddForm'].submit();
	}
	
	$scope.addFeedEventDetail = function() {
		
		if($scope.feedEventDetail["feedEventDate"] == null || $scope.feedEventDetail["feedEventDate"] == undefined || $scope.feedEventDetail["feedEventDate"] == ""  )
		{
			$scope.feedEventDateFlag = true;
		}
		else
			$scope.feedEventDateFlag = false;
		
		if($scope.feedEventDetailAddForm.$valid)
			{
				
				console.log($scope.feedEventDetail);
				if($scope.feedEventDetail.groupEventId)
				{
					var groupStartDate = $scope.groupEvent[$scope.feedEventDetail.groupEventId].groupStartDateTime;
					var groupEndDate = $scope.groupEvent[$scope.feedEventDetail.groupEventId].groupCloseDateTime;
					
					console.log(groupStartDate);
					console.log(groupEndDate);
					  if(new Date($scope.feedEventDetail.feedEventDate)<new Date(groupStartDate))
					  {
						$scope.groupStartDateErrorMessage = true;
						return;
					  }
					  if(groupEndDate && new Date($scope.feedEventDetail.feedEventDate>groupEndDate))
					  {
						$scope.groupEndDateErrorMessage = true;
						return;
					  }
				}
				$scope.feedEventDetail.feedEventId = feedEventDetailData.feedId;
				
				restServices.addFeedEventDetail($scope.feedEventDetail, function(data){
				console.log(data);
					if(!data.error)
						{
							$modalInstance.close(data);					
							return data;
						}
					else
						{
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
	
	$scope.clearForm = function ()
	{
		$scope.entryEventErrorMessage = false;
		$scope.feedEventDetail={};			
	}
	
	$scope.cancel = function()
	{
		$modalInstance.dismiss('add');
	}
	
	$scope.open = function($event) 
	{
		$event.preventDefault();
		$event.stopPropagation();
		$scope.opened = true;
	};
});
