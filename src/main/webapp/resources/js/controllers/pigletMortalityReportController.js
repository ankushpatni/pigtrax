pigTrax.controller('PigletMortalityReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	$scope.startDate;
	$scope.endDate;
	$scope.searchDataErrorMessage = false;
	$scope.premiseList = [];
	$scope.completePremiseList = [];
	
	$scope.loadPremises = function(comapnyId, dataStatus)
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
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+localCompany+'&premisesType=null');
		res.success(function(data, status, headers, config) {
			$scope.completePremiseList = data.payload;
			
			for(i = 0; i < $scope.completePremiseList.length; i++)
			 {
			     var premiseObj = $scope.completePremiseList[i];
			     if(premiseObj.premiseTypeId == 1 || premiseObj.premiseTypeId == 6 || premiseObj.premiseTypeId == 8)
			    	 $scope.premiseList.push(premiseObj);
			 }
			
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		
		 
		
		$scope.companyId = localCompany;
		if(dataStatus == "true")
			$scope.searchDataErrorMessage = true;
		else
			$scope.searchDataErrorMessage = false;
	}    
  
    
    $scope.generatePigletMortalityReport = function()
    {	
    	
    	if($scope.companyId != null && $scope.selectedPremise !=  null && $scope.startDate !=  null &&  $scope.endDate != null)
    	{
    		$scope.criteriaMessage = false;
    			document.getElementById("companyId1").value	= $scope.companyId;		
				document.getElementById("selectedPremise").value	= $scope.selectedPremise;		
				document.forms['generatePigletMortalityReportForm'].submit();
    	}
    	else
    	{
    		$scope.criteriaMessage = true;
    	}
			
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