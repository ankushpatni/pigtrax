pigTrax.controller('OriginController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	$scope.companyId = "";
	$scope.origin = {};
	$scope.itemsByPage=10;
	$scope.totalPages;
	
	
	$scope.getOriginList = function()
	{
		restServices.getOriginList( function(data){
			if(!data.error)
			{
				  $scope.originList = data.payload;
				  $scope.totalPages = Math.ceil($scope.originList.length/$scope.itemsByPage);
			}
		})
	};
	
	
	$scope.clearAllMessages = function()
	{
		 $scope.originRequired = false;	
		 $scope.duplicateOrigin = false;
		 $scope.originValueDeleted = false;
	}
	
	
	
	$scope.saveOrigin = function()
	{
		if($scope.origin["name"] == null ||$scope.origin["name"] == "")
		{
		  $scope.originRequired = true;	
		}
		else 
			{
			  $scope.clearAllMessages();
			  restServices.saveOrigin($scope.origin, function(data){
				  if(!data.error)
				  {
					  	$scope.clearAllMessages();
					    $scope.originSaved = true;
					    $scope.origin = {};
					    $scope.getOriginList();
					    
				   }
				  else
					  {
					    (data.duplicateRecord == true)
					    {
					    	$scope.duplicateOrigin = true;
					    }
					  }
				  $window.scrollTo(0, 0);
			  });
			}
	};
	
	
	
	
	$scope.deleteOrigin = function(originId)
	{
		restServices.deleteOrigin(originId, function(data) {
			if(!data.error)
			{
				$scope.clearAllMessages();
				$scope.origin = {};
				$scope.originValueDeleted = true;
				$scope.getOriginList();
			}
			$window.scrollTo(0, 0);
		});
	}
	
	
	
});
