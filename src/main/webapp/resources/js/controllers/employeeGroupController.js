pigTrax.controller('EmployeeGroupController', function($scope, $rootScope, $http,$window,restServices) {
	$rootScope.viewAddForm = false;
	$scope.employeeGrp = {};
	
	$scope.showAddGroupForm = function()
	{
		$rootScope.viewAddForm = true;
		var employeeGroup = {
				companyId : $rootScope.companyId,
				employeeJobFunction : "Any"
		};
		restServices.getEmployeeList(employeeGroup, function(data) {
			if(!data.error){
				$scope.employeeList = data.payload;
			}
		});
	};
	
	$scope.addEmployeeGroup = function()
	{
		$scope.clearAllMessages();
		var selectedEmployees = [];
		var employees = $scope.employeeList
		for (var i = 0; i < employees.length; i++) { 
			if(employees[i].selected)
				selectedEmployees.push(employees[i]);
		}
		
		$scope.employeeGrp["employeeList"] = selectedEmployees;
		
		if($scope.employeeGrp.groupId == "")
			$scope.employeeGrpGroupIdInvalid = true;
		else if($scope.employeeGrp.employeeJobFunction)
			$scope.employeeGrpJobFunctionInvalid = true;
		else if(selectedEmployees== null || selectedEmployees.length == 0)
			$scope.employeeGrpEmployeeInvalid = true;
		
		
		restServices.saveEmployeeGroup($scope.employeeGrp, function(data) {
			if(!data.error)
			{
				 $scope.clearAllMessages();
				  $scope.employeeGrpAddSuccess = true;
				  $rootScope.viewAddForm = false;
				  $scope.getEmployeeGroups();
			}
			else
			{
				$scope.clearAllMessages();
				$scope.employeeGrpAddError = true;
			}
		});
	};
	
	$scope.getEmployeeGroups = function()
	{
		restServices.getEmployeeGroups($scope.companyId, function(data) {
			if(!data.error)
				{
				   $scope.employeeGroups = data.payload;
				}
		});
	};
	
	$scope.clearAllMessages=function()
	{
		$scope.employeeGrpGroupIdInvalid = false;
		$scope.employeeGrpJobFunctionInvalid = false;
		$scope.employeeGrpEmployeeInvalid = false;
		$scope.employeeGrpAddSuccess = false;
		 $scope.employeeGrpAddError = false;
	}
		
	
	$scope.selectEmployeeGroup = function()
	{
		var groups = $scope.employeeGroups;
		for (var i = 0; i < groups.length; i++) { 
			if(groups[i].selected)
				$rootScope.selectedEmployeeGroup = groups[i];
		}
	}
});