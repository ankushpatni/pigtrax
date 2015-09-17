pigTrax.controller('EmployeeGroupController', function($scope, $rootScope, $http,$window,restServices) {
	$rootScope.viewAddForm = false;
	$scope.employeeGrp = {};
	$scope.employeeList = [];
	
	$scope.showAddGroupForm = function()
	{
		$rootScope.viewAddForm = true;
		$scope.employeeGrp = {};
		$scope.clearAllMessages();
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
		var employees = $scope.employeeList;
		for (var i = 0; i < employees.length; i++) { 
			if(employees[i].selected)
				selectedEmployees.push(employees[i]);
		}
		
		$scope.employeeGrp["employeeList"] = selectedEmployees;
		
		if($scope.employeeGrp.groupId == "" || $scope.employeeGrp.groupId == "undefined" || $scope.employeeGrp.groupId == undefined)
			$scope.employeeGrpGroupIdInvalid = true;
		else if($scope.employeeGrp.employeeJobFunction == undefined  || $scope.employeeGrp.employeeJobFunction == "undefined")
			$scope.employeeGrpJobFunctionInvalid = true;
		else if(selectedEmployees== null || selectedEmployees.length == 0)
			$scope.employeeGrpEmployeeInvalid = true;
		else
			{
				$scope.employeeGrp["companyId"] = $rootScope.companyId;
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
						if(data.duplicateRecord)
						{
							$scope.employeeGrpAddDuplicateError = true;
						}
						else
							$scope.employeeGrpAddError = true;
					}
				});
			}
	};
	
	$scope.getEmployeeGroups = function()
	{
		$scope.clearAllMessages();
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
		$scope.employeeGrpAddDuplicateError = false;
	}
		
	
	$scope.selectEmployeeGroup = function(eventObj)
	{
		var groups = $scope.employeeGroups;
		var selectedGroupId = $('input[name=employeeGrpId]:checked').val();
		eventObj["employeeGroupId"] = selectedGroupId;
		var employeeGroups = $scope.employeeGroups;
		
		for (var i = 0; i < employeeGroups.length; i++) { 
		
			if(employeeGroups[i].id == selectedGroupId)
			{
				$rootScope.selectedEmployeeGroup = employeeGroups[i];
				eventObj["employeeGroup"] = employeeGroups[i];
				break;
			}
		}
	};
	
	$scope.editEmployeeGroup = function(selectedEmployeeGroup)
	{
		$scope.employeeGrp = selectedEmployeeGroup;
		$scope.clearAllMessages();
		$rootScope.viewAddForm = true;
		var employeeGroup = {
				companyId : $rootScope.companyId,
				employeeJobFunction : "Any"
		};
		restServices.getEmployeeList(employeeGroup, function(data) {
			if(!data.error){
				$scope.employeeList = data.payload;
				var selectedGroupEmployees = $scope.employeeGrp.employeeList;
				var employees = $scope.employeeList;
				for (var i = 0; i < employees.length; i++) { 
				  for(var j=0; j<selectedGroupEmployees.length; j++)
				  {
					if(employees[i].id == selectedGroupEmployees[j].id)
						employees[i].selected = true;
				  }
				}
			}
		});
		
	};
	
	$scope.removeEmployeeGroup = function(selectedEmployeeGroup)
	{
		$scope.clearAllMessages();
		selectedEmployeeGroup["companyId"] = $rootScope.companyId;
		restServices.deleteEmployeeGroup(selectedEmployeeGroup, function(data){
			if(!data.error)
			{
				 $scope.clearAllMessages();
				  $scope.employeeGrpDeleteSuccess = true;
				  $rootScope.viewAddForm = false;
				  $scope.getEmployeeGroups();
			}
		});
	}
});