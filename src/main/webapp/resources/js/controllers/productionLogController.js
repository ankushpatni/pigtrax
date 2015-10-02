pigTrax.controller('ProductionLogController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	
	$scope.productionLog = {};
	
	$scope.getProductionLogList = function(){
		var productionLog = {
				"companyId" : $rootScope.companyId,
				"startDate" : null,
				"endDate" : null
		};
		 restServices.getProductionLogList(productionLog, function(data){
				 if(!data.error)
				 {
					$scope.rowCollection = data.payload;
					$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
				 }
			});
		};
	
	$scope.setCompanyId = function(companyId)
	{
		$rootScope.companyId = companyId;
		$scope.getProductionLogList();
	};
	
	
	$scope.clearAllMessages = function()
	{
		$scope.observationRequired = false;
		$scope.productionLogSaved = false;
		$scope.productionLogError = false;
	}
	
	
	
	
	
	
	$scope.saveProductionLog = function()
	{	
		if($scope.productionLog["observation"] == null)
		{
			$scope.observationRequired = true;
			valid = false;
		}
		else
		{
			$scope.observationRequired = false;			
		}
		if(!$scope.observationRequired)
		{	
			$scope.productionLog["companyId"] = $rootScope.companyId
		   restServices.productionLog($scope.productionLog, function(data){
			   if(!data.error){
				   $scope.clearAllMessages();
				   $scope.productionLogSaved = true;	
				   $scope.productionLog = {};
				   $scope.getProductionLogList();				   
			   }
			   else
			   {
				   $scope.productionLogError = true;	
			   }
			   $window.scrollTo(0, 0);
		   });	
		}
	};
	
	
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.productionLog = {};
	}
});
