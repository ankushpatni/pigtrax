pigTrax.controller('GroupPerformanceReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	$scope.targetListEmpty=false;

	
	$scope.loadPremises = function(comapnyId)
	{
		var localCompany ;
		if(comapnyId === undefined )
		{
			localCompany = $scope.selectedCompany;
		}
		else
		{
			localCompany  = comapnyId;
		}
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+localCompany);
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		$scope.companyId = localCompany;
		
//		var res = $http.get('rest/premises/getPremisesListBySowSource?generatedCompanyId='+localCompany);
		var res = $http.get('rest/premises/getPremisesListBySowSource?generatedCompanyId='+localCompany+'&premisesType=1,6,8');
//		var res = $http.get('rest/premises/getPremisesListBySowSource?generatedCompanyId='+localCompany+'&premisesType=1,2,3,4,5,6,7,8');
		res.success(function(data, status, headers, config) {
			$scope.sowSourceList = data.payload;
			
		});

	}
	
	$scope.getPremisesFromSowSource = function(comapnyId)
	{
		$scope.targetListEmpty=false;
		var localCompany ;
		if(comapnyId === undefined )
		{
			localCompany = $scope.selectedCompany;
		}
		else
		{
			localCompany  = comapnyId;
		}
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+localCompany+'&premisesType=2,3,4,5,7,8');
//		var res = $http.get('rest/premises/getPremisesListFromSowSource?generatedCompanyId='+localCompany+ '&sowSource='+$scope.selectedSowSource);
		res.success(function(data, status, headers, config) {
			
			$scope.premiseList = data.payload;
			if (data.payload.length <= 0){
				$scope.targetListEmpty=true;
			}
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
	}
	
        
    $scope.searchGroupInfo = function()
    {
			$scope.searchDataErrorMessage = false;
			document.getElementById("companyId1").value	= $scope.companyId;		
			document.forms['generateGroupPerformanceReport'].submit();
			
    }
    
    
    $scope.getCompanyList = function(){
    	
    		restServices.getCompanyList(function(data){
    			 if(!data.error)
    			 {
    				$scope.companyMapList = data.payload;
    			 }
    		});
    	};
    	
	$scope.getCompanyList();
	
});