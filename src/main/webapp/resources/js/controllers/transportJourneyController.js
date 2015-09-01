pigTrax.controller('addTransportJourneyCtrl', function($scope, $rootScope, $http, $window,restServices) {	
	var transportJourneyData={};
	
	$scope.init= function()
    {
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
	}
	
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
					//$rootScope.selectedTrailerDetails.transportJourneyId = data.payLoad
					$rootScope.selectedTrailerDetails.transportDestination = $scope.transportDestination[$scope.transportJourneyForm.transportDestinationId];
					$rootScope.selectedTrailerDetails.transportTruck = $scope.transportTruck[$scope.transportJourneyForm.transportTruckId];
					$rootScope.selectedTrailerDetails.transportTrailer = $scope.transportTrailer[$scope.transportJourneyForm.transportTrailerId];
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
