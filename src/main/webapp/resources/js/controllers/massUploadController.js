pigTrax.controller('MassUploadController', function($scope,$rootScope, $http,$window,restServices) {
	$scope.uploadStatus = "";
	
	$scope.setStatus = function(statusMessage)
	{
		$scope.uploadStatus = statusMessage;
	}
});
