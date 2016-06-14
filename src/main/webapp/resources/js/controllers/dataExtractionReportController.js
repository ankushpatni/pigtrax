pigTrax.controller('DataExtractionReportController', function($scope, $http, $window,$modal, restServices) {	
	
	$scope.startDate;
	$scope.endDate;
	$scope.searchDataErrorMessage = false;
	$scope.eventList = [];
	$scope.eventTypeKeys = [];
	$scope.pigEventKeys = [];
	$scope.groupEventKeys = [];
	$scope.usedEventKeys = [];
	$scope.eventTypeValues = null;
	$scope.companyId = null;
	$scope.selectedGroup = -1;
	$scope.selectedPig = -1;
	
	
	$scope.getEventTypes = function()
	{
		restServices.getEventTypes(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.eventTypeKeys = responseList[0];
				$scope.eventTypeValues =responseList[1];
				
				 for(i = 0; i < $scope.eventTypeKeys.length; i++)
				  {
					  $scope.usedEventKeys.push($scope.eventTypeKeys[i]);
					  if($scope.eventTypeKeys[i] == 13)
					  {
						  $scope.groupEventKeys.push($scope.eventTypeKeys[i]);
						  $scope.pigEventKeys.push($scope.eventTypeKeys[i]);	
					  }
					  else if($scope.eventTypeKeys[i] >= 1 && $scope.eventTypeKeys[i] <= 10 && $scope.eventTypeKeys[i] != 9)
					  {						  
						  $scope.pigEventKeys.push($scope.eventTypeKeys[i]);		
						 if($scope.eventTypeKeys[i] >= 6 && $scope.eventTypeKeys[i] <= 10)
							  $scope.groupEventKeys.push($scope.eventTypeKeys[i]);
							  
					  }
					  else 
					  {
						  $scope.groupEventKeys.push($scope.eventTypeKeys[i]);					  
					  }
					  
					 
				  }
				
			}
		});
	}
	
	$scope.getEventTypes();
	
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
  
    
	$scope.changeSelection = function()
	{
		if($scope.reportOption == "pigId")
		{
			$scope.usedEventKeys =  $scope.pigEventKeys;
			$scope.selectedGroup = null;
		}
		else
		{
			$scope.usedEventKeys =  $scope.groupEventKeys;
			$scope.selectedPig = null;
		}
	}
	
	
    $scope.generate = function()
    {	
    	
    	document.getElementById("reportOption").value  = $scope.reportOption;
    	
    	
    	if($scope.selectedGroup == null)
    		$scope.selectedGroup = "";
    	if($scope.selectedPig == null)
    		$scope.selectedPig = "";
    	
    	if($scope.selectedPremise != null && $scope.startDate !=  null &&  $scope.endDate != null && $scope.selectedEvent != null)
    	{
    		$scope.criteriaMessage = false;
    		if($scope.selectedEvent == 13)
    		{
    			document.getElementById("companyId1").value	= $scope.companyId;
    			document.forms['generateDataExtractionForm'].action = "generateReport";
    		}
			document.forms['generateDataExtractionForm'].submit();
			
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
    
	$scope.loadData = function()
	{
		var res = $http.get('rest/entryEvent/getPigInfoList?companyId='+$scope.companyId+'&premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
		console.log(data.payload);
			$scope.pigInfoList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		
		var res = $http.get('rest/groupEvent/getGroupEventByPremiseWithoutStatus?premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
			$scope.groupList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
	}
	
    
});