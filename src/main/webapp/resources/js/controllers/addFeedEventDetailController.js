pigTrax.controller('AddFeedEventDetailController', function($scope, $rootScope, $http, $window,restServices,feedEventService) {	
	$scope.siloList={};
	$scope.feedEventType={};
	$scope.feedEventDetail={};
	
	$scope.init= function()
    {
		var res1 = $http.get('rest/util/getFeedEventDetailMasterData?companyId='+$rootScope.companyId);
			res1.success(function(data, status, headers, config) {
				console.log(data);
				$scope.siloList = data.payload[1];	
				$scope.feedEventType = data.payload[0];				
			});
			res1.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});	
	
	//$scope.feedEventId = feedEventService.feedEventData.feedEventId;
	//$scope.feedContentId = feedEventService.feedEventData.feedContentId;
	
	}
	
	$scope.addFeedEventDetail = function() {
		if($scope.feedEventDetailAddForm.$valid)
			{
			if(document.getElementById("feedEventDate").value != "")
				$scope.feedEventDetail.feedEventDate =  document.getElementById("feedEventDate").value;
			$scope.feedEventDetail.feedEventId = $rootScope.feedEventId;
			console.log($scope.feedEventDetail);

			restServices.addFeedEventDetail($scope.feedEventDetail, function(data){
			console.log(data);
				if(!data.error)
					{
						console.log(data);
					}
				else
					{
						console.log(data);
					} 
			});
	}
	}
	
	$scope.cancel = function(){
		//$modalInstance.dismiss('add');
	}
});
