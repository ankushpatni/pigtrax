pigTrax.controller('RationController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	$scope.companyId = "";
	$scope.masterRation = {};
	
	
	
	
	$scope.getFeedTypes = function()
	{
		restServices.getFeedEventTypes( function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.feedTypeKeys = responseList[0];
				$scope.feedTypeKeyValues =responseList[1];
				
				$scope.getRationList();
			}
		})
	};
	
	
	$scope.getRationList = function()
	{
		restServices.getRationList(function(data) {
			if(!data.error)
				{
				   $scope.rationList = data.payload;
				}
		});
	};
	
	$scope.clearAllMessages = function()
	{
		$scope.rationSaved = false;
		 $scope.rationRequired = false;	
		 $scope.duplicateRation = false;
		 $scope.rationValueDeleted = false;
	}
	
	
	
	$scope.saveRation = function()
	{
		if($scope.masterRation["rationValue"] == null ||$scope.masterRation["rationValue"] == "")
		{
		  $scope.rationRequired = true;	
		}
		else 
			{
			  $scope.clearAllMessages();
			  restServices.saveRation($scope.masterRation, function(data){
				  if(!data.error)
				  {
					  	$scope.clearAllMessages();
					    $scope.rationSaved = true;
					    $scope.getRationList();
					   
				   }				  
				  $window.scrollTo(0, 0);
			  });
			}
	};
	
	
	
	
	$scope.deleteRation = function(rationId)
	{
		restServices.deleteRation(rationId, function(data) {
			if(!data.error)
			{
				$scope.clearAllMessages();
				$scope.masterRation = {};
				$scope.rationValueDeleted = true;
				$scope.getRationList();
			}
			$window.scrollTo(0, 0);
		});
	}
	
	
	
});
