
pigTrax.controller('openSelectBoxCtrl', function($scope, $http, $window, $modalInstance,openSelectBoxData) {	
	$scope.removalElementList = openSelectBoxData.data;
	
	console.log(openSelectBoxData);
	
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
});

