var feedEventController = pigTrax.controller('RemovalExceptSalesController', function($scope,$rootScope,$modal,$http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.transportDestination;
	$scope.transportTruck;
	$scope.transportTrailer;
	$scope.barnList={};
	$scope.removalEventType={};
	$scope.groupEvent={};
	$scope.pigInfoList={};
	$scope.removalExceptSales={};
	
	
	$scope.setCompanyId = function(companyId,removalId,removalGeneratedId,removalExceptSalesId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		$scope.removalId = removalId;
		$scope.removalGeneratedId = removalGeneratedId;
		$scope.removalExceptSalesId = removalExceptSalesId;
		
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
		
		var res2 = $http.get('rest/util/getRemovalExceptSalesMasterData?companyId='+$rootScope.companyId);
		res2.success(function(data, status, headers, config) {
			console.log(data);
			
			$scope.removalEventType = data.payload[0];
			$scope.siloList = data.payload[1];	
			$scope.groupEvent = data.payload[2]
		});
		res2.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		/*if( ticketNumber)
		{
			$scope.searchText = ticketNumber;
			$scope.getFeedEvent();
			$scope.entryEventDetailSuccessMessage = true;
		}*/
	};
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.feedEvent = {};
		$scope.changeText();
	}
	
	$scope.clearAllMessages = function()
	{
		$scope.eventErrorMessage = false;
		$scope.eventDuplicateErrorMessage = false;
	};
	
});