pigTrax.controller('EmployeeController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.differentPages=[{"name":"Premises","value":"premises"}];
	
	$scope.hoverIn = function(){
        this.hoverEdit = true;
    };

    $scope.hoverOut = function(){
        this.hoverEdit = false;
    };
	$scope.activateEmployee = function activateEmployee(row , status) {
    	var postParam = {
    			"employeeId" : row
    	};
    	var res = $http.post('rest/employee/activateEmployeeStatus?employeeId='+row);
		res.success(function(data, status, headers, config) {
		$scope.getEmployeeListIndi();
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
    }
	$scope.deActivateEmployee = function deActivateEmployee(row , status) {
    	var postParam = {
    			"employeeId" : row
    	};
    	var res = $http.post('rest/employee/deActivateEmployeeStatus?employeeId='+row);
		res.success(function(data, status, headers, config) {
		$scope.getEmployeeListIndi();
			
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
    }
	$scope.addEmployeeData = function () {
    		var modalInstance = $modal.open ({
    			templateUrl: 'addEmployee',
    			controller: 'addEmployeeCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
    				employeeData : function(){
    					return null;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
					$scope.getEmployeeListIndi();				
				}
    		});
    }
	$scope.editEmployeeData = function(employeeRow){
		var modalInstance = $modal.open ({
    			templateUrl: 'editEmployee?empId='+employeeRow,
    			controller: 'addEmployeeCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
    			resolve:{
    				employeeData : function(){
    					return employeeRow;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="SUCCESS")
				{
					$scope.getEmployeeListIndi();				
				}
			});
		
    	}
	

	$scope.getEmployeeListIndi = function(){
		restServices.getEmployeeListIndi(function(data){
			 if(!data.error)
			 {
				    $scope.employeeList = data.payload;
					$scope.rowCollection = data.payload;					
				    $scope.totalPages = Math.ceil($scope.rowCollection.length/$scope.itemsByPage);
			 }
		});
	};
	
});	

