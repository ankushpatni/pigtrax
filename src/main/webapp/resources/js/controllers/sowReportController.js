pigTrax.controller('sowReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	var localCompany ;
	$scope.mentaoryField = false;
	
	$scope.loadPremises = function(comapnyId,dataStatus)
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
		if(dataStatus == "true")
			$scope.searchDataErrorMessage = true;
		else
			$scope.searchDataErrorMessage = false;
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
				$scope.pigId === '' || $scope.pigId === undefined )
			{
				$scope.mentaoryField = true;
				return true;
			}
			else
			{	
				$scope.mentaoryField = false;
			}
			var searchPigInfo = {
					searchText : $scope.pigId,
					searchOption : 'pigId',
					companyId : $scope.companyId,
					selectedPremise : $scope.selectedPremise
			};
			restServices.getPigInformationWithOutStatus(searchPigInfo, function(data)
			{
				if(!data.error){
					$scope.searchDataErrorMessagePig = false;
					document.getElementById("companyId1").value	= $scope.companyId;		
					document.forms['generateReportSow'].submit();
				}
				else
				{
					$scope.searchDataErrorMessagePig = true;
					
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