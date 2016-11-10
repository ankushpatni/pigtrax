pigTrax.controller('GroupStatusReportController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	$scope.startDate;
	$scope.endDate;
	$scope.groupEventFromPremisesList = {};
	
	$scope.premiseValues = [];
	$scope.selectedPremises = [];
	
	$scope.multiselectdropdownsettings = {
		    scrollableHeight: '200px',
		    scrollable: true
		};
	
	
	$scope.loadPremises = function(comapnyId,dataStatus)
	{
		$scope.selectedPremises = [];
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
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;			
			$scope.premiseValues = [];	
			
           angular.forEach($scope.premiseList, function(premise, index){
        	   var itemObj = {"id" : premise.id, "label":premise.name}  
			   $scope.premiseValues.push(itemObj);
           });
			
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
  
    
    $scope.generateProdEventLog = function()
    {	
    	if($scope.generateGroupStatusReportForm.$valid)
    	{
			var selectedPremiseStr = '';
			if($scope.selectedPremises != null && 0 < $scope.selectedPremises.length)
			{
				angular.forEach($scope.selectedPremises, function(item, index){        	   
					selectedPremiseStr +=item.id;
					if(index < $scope.selectedPremises.length-1)
						selectedPremiseStr += ",";
				});
			}
			document.getElementById("companyId1").value	= $scope.companyId;		
			document.getElementById("selectedPremise").value	=selectedPremiseStr;
			alert(selectedPremiseStr);
			document.forms['generateGroupStatusReportForm'].submit();
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
	
	$scope.selectGroups = function()
    {
   
    	var res = $http.get('rest/groupEvent/getGroupEventByPremiseWithoutStatus?premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
			$scope.groupList = data.payload;
			$scope.loadActiveCloseGroupEvents();
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
    	
    }
	
	
	
	
	$scope.loadActiveCloseGroupEvents = function()
	{
		
	$scope.groupEventTempList = $scope.groupList;
		
	if($scope.groupStatus === undefined || $scope.groupStatus === 'active')
		{
		$scope.groupEventFromPremisesList = {};
		for( var x in $scope.groupList)
				{
					if( $scope.groupList[x].active )
						{		
							$scope.groupEventFromPremisesList[x] = $scope.groupList[x];
						}
				}
		}
		
		if($scope.groupStatus === 'closed')
		{
		
		$scope.groupEventFromPremisesList = {};
		for( var x in $scope.groupList)
				{
					if(! $scope.groupList[x].active )
						{		
							$scope.groupEventFromPremisesList[x] = $scope.groupList[x];
						}
				}
		}
		
		
	
	}
	
	
});