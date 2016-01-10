	var sowMovementController = pigTrax.controller('SowMovementController', function($scope,$rootScope, $modal, $http,$window,restServices, DateUtils) {
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.roomList={};
	$scope.sowMovementList = {};
	
	$scope.loadPremises = function()
	{
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId);
		res.success(function(data, status, headers, config) {
			$scope.farmList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
	};
	
	
	
	$scope.clearAllMessages = function()
	{
		$scope.searchDataErrorMessage = false;	
		$scope.sowMovementSuccessMessage = false;
	};
	
	$scope.getSowMovement = function ()
	{
		var postParam = {
				
				"pigInfo" : searchGroupEvent,
				"companyId" : $scope.companyId
			};
		
		restServices.getSowMovement(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.clearAllMessages();
					$window.scrollTo(0,550);
					$scope.sowMovementList = {};
					$scope.sowMovementSuccessMessage = true;
				}
			else
				{
					$scope.sowMovementList = {};
					$scope.sowMovementSuccessMessage = false;
					$scope.searchDataErrorMessage = true;
				}
		});
	}

});