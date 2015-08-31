pigTrax.controller('addTransportJourneyCtrl', function($scope, $http, $window,restServices) {	
	var transportJourneyData={};
	
	$scope.init= function()
    {
		//alert("init");
		transportJourneyData.transportDestination = $scope.transportDestination;
		transportJourneyData.transportTruck = $scope.transportTruck;
		transportJourneyData.transportTrailer = $scope.transportTrailer;
		alert(JSON.stringify(transportJourneyData));
		//return transportJourneyData;
    }
	
	$scope.alertVisible = false;
	$scope.transportDestination=transportJourneyData.transportDestination;
	$scope.transportTruck=transportJourneyData.transportTruck;
	$scope.transportTrailer=transportJourneyData.transportTrailer;
	
	$scope.getPenList($scope.roomId,$scope.generatedRoomId);		
	
	//$scope.getPenList($scope.roomId,$scope.generatedRoomId);		
	
	$scope.transportJourney;
	
	$scope.addTransportJourney = function() {
	
		if($scope.transportJourneyForm.$valid)
			{
			if(document.getElementById("journeyStartTime").value != "")
				$scope.transportJourney.journeyStartTime =  document.getElementById("journeyStartTime").value;
			if(document.getElementById("journeyEndTime").value != "")
				$scope.transportJourney.journeyEndTime = document.getElementById("journeyEndTime").value;
			
			restServices.addTransportJourney($scope.transportJourney, function(data){
				if(!data.error)
					{
						console.log(data);
					}
				else
					{
						
					} 
			});
	}
	}
	
	$scope.cancel = function(){
		//$modalInstance.dismiss('add');
	}
});
