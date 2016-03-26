pigTrax.controller('saleReportController', function($scope, $http, $window,$modal, restServices) {	
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
	
	$scope.loadGroupTattoInfo = function()
	{
		var res = $http.get('rest/groupEvent/getGroupEventByPremiseWithoutStatus?premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
			$scope.groupEventListSearch = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		var res1 = $http.get('rest/feedEvent/getFeedEventsByPremises?id='+$scope.selectedPremise);
		res1.success(function(data, status, headers, config) {
			console.log(data);
			$scope.feedEventTicketNumberDetailList = data.payload;			
		});
		res1.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		var res = $http.get('rest/entryEvent/getPigInfoList?companyId='+localCompany+'&premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
		console.log(data.payload);
			$scope.pigInfoListSearch = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.getBarnList = function(){
		
		var res = $http.get('rest/barn/getBarnList?generatedPremisesId='+$scope.selectedPremise);
			res.success(function(data, status, headers, config) {
				$scope.barnList = data.payload;
				});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
	
        
    $scope.searchPigInfo = function()
    {
			$scope.pigInfo = {};

			/*if($scope.pigId !== undefined && $scope.pigId !== '')
				{
					var searchPigInfo = {
							searchText : $scope.pigId,
							searchOption : 'pigId',
							companyId : $scope.companyId,
							selectedPremise : $scope.selectedPremise
					};
					restServices.getPigInformationWithOutStatus(searchPigInfo, function(data)
					{
						if(data.error){
							$scope.searchDataErrorMessage = true;
							return false;
						}				
					});
				}
			*/
			if($scope.groupId !== undefined && $scope.groupId !== '')
				{
					var searchGroupInfo = {
							groupId : $scope.groupId,
							companyId : $scope.companyId,
							premiseId : $scope.selectedPremise
					};
					restServices.getGroupEventInformation(searchGroupInfo, function(data)
					{
						if(data.error){
							$scope.searchDataErrorMessageGroup = true;
							return false;
						}
					});
				}
			
			//$scope.searchDataErrorMessage = false;
			$scope.searchDataErrorMessageGroup = false;
			document.getElementById("companyId1").value	= $scope.companyId;		
			document.forms['generateReportSale'].submit();
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