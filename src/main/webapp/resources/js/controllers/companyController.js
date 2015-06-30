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
    $scope.addCompanyData = function () {
    	
    	companyServices.openCompanyAdd();
    	return;
    	/*var postParam = {
    			"companyId" : $scope.companyId,
    			"name" : $scope.name,
    			"address" : $scope.address,
    			"city" : $scope.city,
    			"registrationNumber" : $scope.registrationNumber,
    			"email" : $scope.email,
    			"phone" : $scope.phone,
    			"contactName" : $scope.ContactName,
    			"payment" : $scope.payment,
    			"paymentDate" : $scope.paymentDate, 
    			"active" : $scope.active
    	};
    	
    	postParam = {
    			"companyId" : "ZXCV",
    			"name" : "ZXCV Company",
    			"address" : "Mysore Palace",
    			"city" : "Mysore",
    			"registrationNumber" : "0987890",
    			"email" : "ankushpa@gmail.com",
    			"phone" : 9876543210,
    			"contactName" : "OuR company",
    			"payment" : 12345678,
    			"paymentDate" : "2015-6-29", 
    			"active" : "true"
    	};*/
    	
    	//var res = $http.post('rest/company/insertCompanyRecord', postParam);
		//res.success(function(data, status, headers, config) {
		//row.active = !row.active;
			//console.log("adding data-->"+data);
		//});
		//res.error(function(data, status, headers, config) {
		//	console.log( "failure message: " + {data: data});
		//});	
       /* var index = $scope.rowCollection.indexOf(row);
        if (index !== -1) {
            $scope.rowCollection.splice(index, 1);
        }*/
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