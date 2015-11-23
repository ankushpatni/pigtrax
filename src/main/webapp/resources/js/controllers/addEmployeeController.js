pigTrax.controller('addEmployeeCtrl', function($scope, $http, $window, $modalInstance , employeeData, restServices) {	
	
	
		$scope.getCompanyList = function(){
			restServices.getCompanyList(function(data){
				 if(!data.error)
				 {
					$scope.companyList = data.payload;
				 }
			});
		};
		
		
		
		$scope.getRoleTypes = function(){
			restServices.getRoleTypes(function(data){
				 if(!data.error)
				 {
					 if(!data.error){
							var responseList = data.payload;
							$scope.roleTypeKeys = responseList[0];
							$scope.roleTypes = responseList[1];
						}
				 }
			});
		};
		
		
		$scope.getFunctionTypes = function(){
			restServices.getFunctionTypes(function(data){
				 if(!data.error)
				 {
					 if(!data.error){
							var responseList = data.payload;
							$scope.functionTypeKeys = responseList[0];
							$scope.functionTypeKeyValues = responseList[1];
						}
				 }
			});
		};
		
		$scope.gtJobFunctionRoles = function(){
			restServices.gtJobFunctionRoles(function(data){
				 if(!data.error)
				 {
					 if(!data.error){
							var responseList = data.payload;
							$scope.jobFunctionRoleKeys = responseList[0];
							$scope.jobFunctionRoleKeyValues = responseList[1];
						}
				 }
			});
		};
		
		
	
		$scope.getCompanyList();
		$scope.getRoleTypes();
		$scope.getFunctionTypes();
		$scope.gtJobFunctionRoles();
	
	$scope.addEmployee = function() {
	
		if($scope.employeeAddForm.$valid){
			
				var postParam = {
						"employeeId" : $scope.add.employeeId,
						"companyId" : $scope.add.companyId,
						"name" : $scope.add.name,
						"email" : $scope.add.email,
						"userRoleId" : $scope.add.userRoleId,
						"active"  : $scope.add.active,
						"portalUser" :  $scope.add.portalUser,
						"functionTypeId" : $scope.add.functionTypeId,
						"jobFunctionRoleId" : $scope.add.jobFunctionRoleId,
						"phoneNumber" : $scope.add.phoneNumber
						//"portalId" :  $scope.add.portalId
				};
				var res = $http.post('rest/employee/insertEmployeeRecordSubmit', postParam);
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
	$scope.editEmployee = function() {
	
		if($scope.employeeEditForm.$valid)
			{
			
				var postParam = {
						"id" : $scope.add.id,
						"employeeId" : $scope.add.employeeId,
						"companyId" : $scope.add.companyId,
						"name" : $scope.add.name,
						"email" : $scope.add.email,
						"userRoleId" : $scope.add.userRoleId,
						"active"  : $scope.add.active,
						"portalUser" :  $scope.add.portalUser,
						"functionTypeId" : $scope.add.functionTypeId,
						"jobFunctionRoleId" : $scope.add.jobFunctionRoleId,
						"phoneNumber" : $scope.add.phoneNumber
						//"portalId" : $scope.add.portalId 
						
				};
				var res = $http.post('rest/employee/editEmployeeRecord', postParam);
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
	
	
	
	$scope.getEmployeeDetails = function(employeeId)
	{
		restServices.getEmployeeDetails(employeeId, function(data){
			if(!data.error)
				{
				  $scope.add = data.payload;
				}
		})
	}
	
	$scope.getPreviousRoles = function(employeeId)
	{		
		if($scope.onPrevRoles)
			$scope.onPrevRoles = false;
		else
		{
			$scope.onPrevRoles = true;
			restServices.getEmployeeRoles(employeeId, function(data){
				if(!data.error)
				{
				  $scope.prevRoleList = data.payload;
				}
			});
		}
	}
});