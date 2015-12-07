pigTrax.controller('addFeedEventDetailCtrl', function($scope, $rootScope, $http, $window,restServices,$modalInstance,feedEventDetailData) {
	
	$scope.siloList=feedEventDetailData.siloList;
	$scope.feedEventType=feedEventDetailData.feedEventType;
	$scope.feedEventDetail={};
	$scope.feedEventDetail.feedEventId = feedEventDetailData.feedId;
	$scope.feedEventId = feedEventDetailData.feedEventId;
	$scope.ticketNumber = feedEventDetailData.ticketNumber;
	$scope.companyId = feedEventDetailData.companyId;
	$scope.groupEvent = [];
	console.log(feedEventDetailData);
	
	$scope.groupEventOriginalList = feedEventDetailData.groupEvent;
	for( var x in $scope.groupEventOriginalList)
	{
		if( $scope.groupEventOriginalList[x].active )
			{		
				$scope.groupEvent[x] = $scope.groupEventOriginalList[x];
			}
	}
	
	
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
	
	$scope.gotoFeedEvent = function()
	{

		document.getElementById("feedEventTicketNumber").value = $scope.ticketNumber;
		document.getElementById("selectedCompany").value = $scope.companyId;		
		document.forms['feedEventDetailAddForm'].action = 'feedEvent';
		document.forms['feedEventDetailAddForm'].submit();
	}
	
	$scope.addFeedEventDetail = function() {
		if($scope.feedEventDetailAddForm.$valid)
			{
				
				console.log($scope.feedEventDetail);
				if($scope.feedEventDetail.groupEventId)
				{
					var groupStartDate = $scope.groupEvent[$scope.feedEventDetail.groupEventId].groupStartDateTime;
					var groupEndDate = $scope.groupEvent[$scope.feedEventDetail.groupEventId].groupCloseDateTime;
					
					console.log(groupStartDate);
					console.log(groupEndDate);
					  if($scope.feedEventDetail.feedEventDate<groupStartDate)
					  {
						$scope.groupStartDateErrorMessage = true;
						return;
					  }
					  if(groupEndDate && $scope.feedEventDetail.feedEventDate>groupEndDate)
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
