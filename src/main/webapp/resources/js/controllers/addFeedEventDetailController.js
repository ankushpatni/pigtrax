pigTrax.controller('addFeedEventDetailCtrl', function($scope, $rootScope, $http, $window,restServices,$modalInstance,feedEventDetailData) {
	
	$scope.siloList=feedEventDetailData.siloList;
	$scope.feedEventType=feedEventDetailData.feedEventType;
	$scope.feedEventDetail={};
	$scope.feedEventDetail.feedEventId = feedEventDetailData.feedId;
	$scope.feedEventId = feedEventDetailData.feedEventId;
	$scope.ticketNumber = feedEventDetailData.ticketNumber;
	$scope.companyId = feedEventDetailData.companyId;
	console.log(feedEventDetailData);
	
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
				if(document.getElementById("feedEventDate").value != "")
				{
					$scope.feedEventDetail.feedEventDate =  document.getElementById("feedEventDate").value;
				}
				$scope.feedEventDetail.feedEventId = feedEventDetailData.feedId;
				console.log($scope.feedEventDetail);
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
