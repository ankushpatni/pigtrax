pigTrax.controller('addTransportJourneyCtrl', function($scope, $http, $window, $modalInstance,transportJourneyData,restServices) {	
	
	$scope.alertVisible = false;
	$scope.transportDestination=transportJourneyData.transportDestination;
	$scope.transportTruck=transportJourneyData.transportTruck;
	$scope.transportTrailer=transportJourneyData.transportTrailer;
	
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
		$modalInstance.dismiss('add');
	}
});
