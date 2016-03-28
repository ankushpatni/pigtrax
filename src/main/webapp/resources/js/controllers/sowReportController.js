pigTrax.controller('sowReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	var localCompany ;
	$scope.mentaoryField = false;
	
	$scope.loadPremises = function(comapnyId)
	{
		
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
	
	$scope.loadPigInfo = function()
	{
		var res = $http.get('rest/entryEvent/getPigInfoList?companyId='+localCompany+'&premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
		console.log(data.payload);
			$scope.pigInfoList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
        
    $scope.searchPigInfo = function()
    {
			$scope.pigInfo = {};
			
			if($scope.selectedPremise === '' || $scope.selectedPremise === undefined ||
				$scope.companyId === '' || $scope.companyId === undefined || 
				$scope.searchText === '' || $scope.searchText === undefined )
			{
				$scope.mentaoryField = true;
				return true;
			}
			else
			{	
				$scope.mentaoryField = false;
			}
			var searchPigInfo = {
					searchText : $scope.searchText,
					searchOption : 'pigId',
					companyId : $scope.companyId,
					selectedPremise : $scope.selectedPremise
			};
			restServices.getPigInformationWithOutStatus(searchPigInfo, function(data)
			{
				if(!data.error){
					$scope.searchDataErrorMessage = false;
					document.getElementById("companyId1").value	= $scope.companyId;		
					document.forms['generateReportSow'].submit();
				}
				else
				{
					$scope.searchDataErrorMessage = true;
					
				}
			});
    }
    
    $scope.getCompanyList = function(){
    	
    		restServices.getCompanyList(function(data){
			console.log(data);
    			 if(!data.error)
    			 {
    				$scope.companyMapList = data.payload;
    			 }
    		});
    	};
    	
	$scope.getCompanyList();
	
});