pigTrax.controller('addCompanyCtrl', function($scope, $http, $window, $modalInstance,companyData) {	
	$scope.companyData = companyData;
	$scope.edit = false;
	$scope.add ={};
	
	if(companyData != null)
	{
		$scope.edit = true;
		$scope.add.companyId = companyData.companyId,
    	$scope.add.name = companyData.name,
    	$scope.add.address = companyData.address,
    	$scope.add.city = companyData.city,
    	$scope.add.registrationNumber = companyData.registrationNumber,
    	$scope.add.email = companyData.email,
    	$scope.add.phone = companyData.phone,
    	$scope.add.contactName = companyData.contactName,
    	$scope.add.payment = companyData.payment,
    	$scope.add.paymentDate = companyData.paymentDate, 
    	$scope.add.active = companyData.active,
    	$scope.add.id = companyData.id
	}
	
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
    			"active" : $scope.add.active,
    			"id" : $scope.add.id
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

