pigTrax.controller('addCompanyCtrl', function($scope, $http, $window, $modalInstance) {	
	
	$scope.addCompany = function() {
		var postParam = {
    			"companyId" : $scope.add.companyId,
    			"name" : $scope.add.name,
    			"address" : $scope.add.address,
    			"city" : $scope.add.city,
    			"registrationNumber" : $scope.add.registrationNumber,
    			"email" : $scope.add.email,
    			"phone" : $scope.add.phone,
    			"contactName" : $scope.add.contactName,
    			"payment" : $scope.add.payment,
    			"paymentDate" : $scope.add.paymentDate, 
    			"active" : $scope.add.active
    	};
		
		var res = $http.post('rest/company/insertCompanyRecord', postParam);
		res.success(function(data, status, headers, config) {
			$modalInstance.close('cancel');
			console.log(data);
			return true;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
			return false;
		});
	};
	
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
});

