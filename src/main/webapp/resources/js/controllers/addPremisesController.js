pigTrax.controller('addPremisesCtrl', function($scope, $http, $window, $modalInstance,premisesData) {	
	$scope.premisesData = premisesData;
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.country=[{"name":"US","value":"US"},{"name":"UK","value":"UK"},{"name":"Germany","value":"Germany"}];
	$scope.cityJSON={"US":[{"name":"BOSTON","value":"BOS"}],"UK":[{"name":"LONDON","value":"LON"}],"Germany":[{"name":"Moscow","value":"mOS"}]};
	$scope.city;
	$scope.add.companyId = premisesData.companyId;
	$scope.generatedCompanyId = premisesData.generatedCompanyId;
	console.log(premisesData);
	if(premisesData != null && premisesData.permiseId !=null )
	{
		$scope.edit = true;
		$scope.add.permiseIdEdit = premisesData.permiseId,
		$scope.add.permiseId = 1,
    	$scope.add.name = premisesData.name,
    	$scope.add.address = premisesData.address,
		$scope.add.state = premisesData.state;
		$scope.add.zipcode = premisesData.zipcode;
       	$scope.add.active = premisesData.active;		
		$scope.city = $scope.cityJSON[$scope.add.state];
		$scope.add.city = premisesData.city;
		$scope.add.id = premisesData.id
    	
	}
	
	$scope.addPremise = function() {
	console.log($scope.premisesAddForm	.$valid);
		if($scope.premisesAddForm.$valid)
			{
			var postParam;
				if( premisesData != null && premisesData.permiseId !=null )
				{
				console.log();
					postParam = {
							"permiseId" : premisesData.permiseId,
							"name" : $scope.add.name,
							"address" : $scope.add.address,
							"state" : $scope.add.state,
							"city" : $scope.add.city,
							"zipcode" : $scope.add.zipcode,
							"active" : true,
							"id" : $scope.add.id,
							"companyId" : premisesData.generatedCompanyId
					};
				}
				else
				{
					postParam = {
							"permiseId" : premisesData.companyId +''+$scope.add.permiseId,
							"name" : $scope.add.name,
							"address" : $scope.add.address,
							"state" : $scope.add.state,
							"city" : $scope.add.city,
							"zipcode" : $scope.add.zipcode,
							"active" : true,
							"id" : $scope.add.id,
							"companyId" : premisesData.generatedCompanyId
					};
				}
				console.log(postParam);
				var res = $http.post('rest/premises/insertPremisesRecord', postParam);
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
		$scope.city = $scope.cityJSON[$scope.add.state];
	}
});

