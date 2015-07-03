pigTrax.controller('CompanyController', function($scope, $http, $window, restServices, companyServices) {	
	$scope.rowCollection = [];

    //deactivate/activate to the real data holder
    $scope.removeItem = function removeItem(row) {
    	var postParam = {
    			"companyId" : row.companyId,
    			"isActive" : row.active
    	};
    	var res = $http.post('rest/company/updateCompanyStatus?companyId='+row.companyId +"&isActive="+row.active, postParam);
		res.success(function(data, status, headers, config) {
		row.active = !row.active;
			console.log(data);
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
    }
    
  //add Company Data
    $scope.editCompanyData = function (companyRow) {    	
    	companyServices.editCompanyData(companyRow);
    	return;
    	
    }
    
  //add Company Data
    $scope.addCompanyData = function () {    	
    	companyServices.openCompanyAdd();
    	return;
    	
    }
    
    $scope.getCompanyList = function(){
		restServices.getCompanyList(function(data){
			 if(!data.error)
			 {
				    $scope.rowCollection = data.payload;
			 }
		});
	};
});