pigTrax.controller('EmployeeController', function($scope, $http, $window, restServices) {	
	$scope.getEmployeeList = function(){
		restServices.getEmployeeList(function(data){
			 if(!data.error)
			 {
				    $scope.employeeList = data.payload;
			 }
		});
	};
});	

