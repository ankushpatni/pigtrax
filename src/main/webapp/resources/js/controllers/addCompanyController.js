pigTrax.controller('addCompanyCtrl', function($scope, $http, $window, $modalInstance,companyData) {	
	$scope.companyData = companyData;
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	//$scope.country=[{"name":"US","value":"US"},{"name":"UK","value":"UK"},{"name":"Germany","value":"Germany"}];
	//$scope.cityJSON={"US":[{"name":"BOSTON","value":"BOS"}],"UK":[{"name":"LONDON","value":"LON"}],"Germany":[{"name":"Moscow","value":"mOS"}]};
	$scope.country=companyData.countryList;
	$scope.cityJSON=companyData.cityJSON;
	$scope.city;
	$scope.showDetail = companyData.showDetail;
	console.log(companyData);
	
	
	if(companyData != null && companyData.id !=null)
	{
		$scope.edit = true;
		$scope.add.companyId = companyData.companyId,
    	$scope.add.name = companyData.name,
    	$scope.add.address = companyData.address,
		$scope.add.country = companyData.country,
    	$scope.city = companyData.cityJSON[companyData.country],
		$scope.add.city = companyData.city,
    	$scope.add.registrationNumber = companyData.registrationNumber,
    	$scope.add.email = companyData.email,
    	$scope.add.phone = companyData.phone,
    	$scope.add.contactName = companyData.contactName,
    	$scope.add.payment = companyData.payment,
    	$scope.add.paymentDate = companyData.paymentDate, 
    	$scope.add.active = companyData.active,
    	$scope.add.id = companyData.id;
		$scope.add.otherCity = companyData.otherCity;
		
//		for (i = 0; i < $scope.city.length; i++) { 
//			if($scope.city[i].value === companyData.city)
//			{
//				$scope.cityValue = $scope.city[i].name;
//				break;
//			}
//		}
		
		if($scope.add.city.toUpperCase()==="OTHERS")
		{
		   $scope.premiseOtherCityBox = true;
		}
		else
		{
		  $scope.premiseOtherCityBox = false
		  $scope.add.otherCity = "";
		}
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
						"country" : $scope.add.country,
						"registrationNumber" : $scope.add.registrationNumber,
						"email" : $scope.add.email,
						"phone" : $scope.add.phone,
						"contactName" : $scope.add.contactName,
						"payment" : $scope.add.payment,
						"paymentDate" : null, 
						"active" : $scope.add.active,
						"id" : $scope.add.id,
						"otherCity" : $scope.add.otherCity,
				};
				
				/*if($scope.add.payment)
				{
					if(document.getElementById("paymentDate").value === "")
					{
						$scope.paymentDateFlag = true;
						return false;
					}
				}*/
				
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
	
	$scope.selectOtherCity = function(){	
		if($scope.add.city.toUpperCase()==="OTHERS")
		{
		   $scope.premiseOtherCityBox = true;
		}
		else
		{
		  $scope.premiseOtherCityBox = false
		  $scope.add.otherCity = "";
		}
	}
});

