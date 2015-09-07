pigTrax.controller('addCompanyCtrl', function($scope, $http, $window, $modalInstance,companyData) {	
	$scope.companyData = companyData;
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.country=[{"name":"US","value":"US"},{"name":"UK","value":"UK"},{"name":"Germany","value":"Germany"}];
	$scope.cityJSON={"US":[{"name":"BOSTON","value":"BOS"}],"UK":[{"name":"LONDON","value":"LON"}],"Germany":[{"name":"Moscow","value":"mOS"}]};
	$scope.city;
	
	if(companyData != null)
	{
		$scope.edit = true;
		$scope.add.companyId = companyData.companyId,
    	$scope.add.name = companyData.name,
    	$scope.add.address = companyData.address,
		//$scope.add.city = $scope.cityJSON[companyData.city],
    	//$scope.add.city = companyData.country + "-"+companyData.city,
		$scope.add.city = companyData.city,
    	$scope.add.registrationNumber = companyData.registrationNumber,
    	$scope.add.email = companyData.email,
    	$scope.add.phone = companyData.phone,
    	$scope.add.contactName = companyData.contactName,
    	$scope.add.payment = companyData.payment,
    	$scope.add.paymentDate = companyData.paymentDate, 
    	$scope.add.active = companyData.active,
    	$scope.add.id = companyData.id;
	}
	
	$scope.addCompany = function() {
	$scope.paymentDateFlag = false;
		if($scope.companyAddForm.$valid)
			{
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
						"paymentDate" : document.getElementById("paymentDate").value, 
						"active" : $scope.add.active,
						"id" : $scope.add.id
				};
				
				if($scope.add.payment)
				{
					if(document.getElementById("paymentDate").value === "")
					{
						$scope.paymentDateFlag = true;
						return false;
					}
				}
				
				var res = $http.post('rest/company/insertCompanyRecord', postParam);
				res.success(function(data, status, headers, config) {
					if(data.statusMessage==="SUCCESS")
					{
						$modalInstance.close(data);					
						return data;
					}
					else
					{
						$scope.alertMessage = data.payload;
						$scope.alertVisible = true;
					}
				});
				res.error(function(data, status, headers, config) {
					console.log( data);
					$scope.alertMessage = data.statusMessage;
					$scope.alertVisible = true;
				});
			};
	}
	
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
	
	$scope.changeCity = function(){	
		$scope.city = $scope.cityJSON[$scope.add.country];
	}
	
	$scope.open = function($event) 
	{
		$event.preventDefault();
		$event.stopPropagation();
		$scope.opened = true;
	};
});

