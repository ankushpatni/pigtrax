pigTrax.controller('MassUploadController', function($scope,$rootScope, $http,$window,restServices) {
	$scope.uploadStatus = "";	
	$scope.eventType = "";
	$scope.companyList = [];
	$scope.upload = {};
	
	$scope.setStatus = function(statusMessage)
	{
		$scope.uploadStatus = statusMessage;
	}
	
	$scope.getCompanyList = function()
	{
		restServices.getCompanyList(function(data){
			
			if(!data.error)
			{
				$scope.companyList = data.payload;
			}
			
		});
	}
	$scope.getCompanyList();
	
	
	$scope.getPremises = function()
	{
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$scope.upload.companyId);
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	
});
