pigTrax.controller('CompanyController', function($scope, $http, $window,$modal, restServices) {	
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
	
	 $scope.$watch($scope.editCompanyData, $scope.getCompanyList, true);
	
	$scope.gotToPage = function(index,row)
	{
		console.log(index);
		console.log($scope.differentPages[index].value);
		document.getElementById("generatedCompanyId").value = row.id;
		document.forms['companyForm'].action = $scope.differentPages[index].value;
		document.forms['companyForm'].submit();
		//$window.location = $scope.differentPages[index].value+'?generatedCompanyId='+row.id;
	}
    
	$scope.addCompanyData = function () {
    		var modalInstance = $modal.open ({
    			templateUrl: 'addCompany',
    			controller: 'addCompanyCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
    				companyData : function(){
    					return null;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
					$scope.getCompanyList();				
				}
    		});
    }
	
	$scope.editCompanyData = function(companyRow){
		var modalInstance = $modal.open ({
    			templateUrl: 'addCompany',
    			controller: 'addCompanyCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
    			resolve:{
    				companyData : function(){
    					return companyRow;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="SUCCESS")
				{
					$scope.getCompanyList();				
				}
			});
		
    	}
	
	$scope.getCompanyList = function(){
		restServices.getCompanyList(function(data){
			 if(!data.error)
			 {
				$scope.rowCollection = data.payload;
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
			 }
		});
	};
});