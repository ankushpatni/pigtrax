pigTrax.controller('RationReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	$scope.startDate;
	$scope.endDate;
	
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
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+localCompany+'&premisesType=null');
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});		
		$scope.companyId = localCompany;
	}    
  
    
    $scope.generateRationReport = function()
    {	
    	if($scope.companyId == null || $scope.selectedPremise == null || $scope.selectedGroup == null 
    			||  $scope.endDate == null)
		{
		  $scope.mandatoryField = true;
		}
    	else
		{
    		$scope.mandatoryField = false;
			document.getElementById("companyId1").value	= $scope.companyId;		
			document.getElementById("selectedPremise").value	= $scope.selectedPremise;		
			document.forms['generateRationReportForm'].submit();
		}
			
    }
    
    
    $scope.selectGroups = function()
    {
    	restServices.getActiveGroupEventsInPremise($scope.selectedPremise, function(data){
    		if(!data.error)
    		{
    			 $scope.groupList = data.payload;
    		}
    	});
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