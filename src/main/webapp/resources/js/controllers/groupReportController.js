pigTrax.controller('groupReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	var localCompany ;
	
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
	
	$scope.loadGroupInfo = function()
	{
		var res = $http.get('rest/groupEvent/getGroupEventByPremiseWithoutStatus?premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
			$scope.groupEventListSearch = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
        
    $scope.searchGroupInfo = function()
    {
			$scope.pigInfo = {};
			var searchPigInfo = {
					groupId : $scope.searchText,
					companyId : $scope.companyId,
					premiseId : $scope.selectedPremise
			};
			restServices.getGroupEventInformation(searchPigInfo, function(data)
			{
				if(!data.error){
					$scope.searchDataErrorMessage = false;
					document.getElementById("companyId1").value	= $scope.companyId;		
					document.forms['generateReportGroup'].submit();
				}
				else
				{
					$scope.searchDataErrorMessage = true;
					
				}
			});
    }
    
    
    $scope.generateActionListReport = function()
    {
		document.getElementById("companyId1").value	= $scope.companyId;		
		document.getElementById("selectedPremise").value	= $scope.selectedPremise;		
		document.forms['generateReportActionList'].submit();
			
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