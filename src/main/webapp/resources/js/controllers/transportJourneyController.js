pigTrax.controller('addTransportJourneyCtrl', function($scope, $rootScope, $http, $window, $modalInstance,restServices, transportJourneyMasterData) {
	
	$scope.transportDestination = transportJourneyMasterData.transportDestination;
	$scope.transportTruck = transportJourneyMasterData.transportTruck ;
	$scope.transportTrailer = transportJourneyMasterData.transportTrailer;
	
	$scope.datepickers = {
        firstdate: false,
        secondDate: false
      }
	
	if(null != transportJourneyMasterData.transportJourney && transportJourneyMasterData.transportJourney != undefined)
	{
		$scope.transportJourney = transportJourneyMasterData.transportJourney;
	}
	
	$scope.init= function()
    {
		
	}
	
	$scope.addTransportJourney = function()
	{		
		if ($scope.transportJourneyForm.$valid)
		{
			if (document.getElementById("journeyStartTime").value != "")
				$scope.transportJourney.journeyStartTime = document
						.getElementById("journeyStartTime").value;
			if (document.getElementById("journeyEndTime").value != "")
				$scope.transportJourney.journeyEndTime = document
						.getElementById("journeyEndTime").value;
			//$scope.feedEvent.transportJourney.transportTrailerId = $scope.transportJourney.transportTrailerId;
			//$scope.feedEvent.transportJourney.transportTruckId = $scope.transportJourney.transportTruckId;

			$modalInstance.close($scope.transportJourney);
		}
	}
	
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
	
	$scope.open = function($event,index) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.datepickers[index] = true;
  };
});
