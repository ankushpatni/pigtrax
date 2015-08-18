var addGroupEventDetailController = pigTrax.controller('AddGroupEventDetailController', function($scope, $http, $window, restServices,$rootScope) {	
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.groupEvent={};
	$scope.groupEvent.employeeGroupId={};
	$scope.groupEvent.employeeGroup = {};
	$scope.phaseOfProductionType ={};
	$scope.roomType = {};
	
	$scope.setCompanyId = function( companyId,groupId,groupDetailId,groupGeneratedId)
	{
		console.log("groupGeneratedId--->"+groupGeneratedId);
		console.log("companyId--->"+companyId);
		$scope.groupAlphaId = groupId;
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		$scope.groupEvent.groupId = groupGeneratedId;
		restServices.getPhaseOfProductionType("", function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.phaseOfProductionType = data.payload[0];	
									
				}
			else
				{
					console.log( "failure message: " + {data: data});
				} 
		});
		
	};
	
	
	
	$scope.addGroupEventDetail = function()
    {
		if($scope.groupEventDetailAddForm.$valid)
		{
			var dateOfEntry = document.getElementById("dateOfEntry").value;
			$scope.groupEvent["dateOfEntry"] = dateOfEntry;
			
			console.log($scope.groupEvent);
			
			restServices.addGroupEventDetail($scope.groupEvent, function(data){
				console.log(data);
				if(!data.error)
					{
						$scope.entryEventSuccessMessage = true;
						//$scope.gotoGroupEvent();
					}
				else
					{
						if(data.duplicateRecord)
						{
							$scope.groupEventDuplicateErrorMessage = true;
						}
						else
						{
						$scope.entryEventErrorMessage = true;
						}
					}
					$window.scrollTo(0, 5);  
			});			
			
		}
    };
    
    $scope.gotoGroupEvent = function()
    {
    	document.getElementById("searchedGroupid").value = $scope.groupAlphaId;		
		document.forms['groupEventDetailAddForm'].action = 'groupEvent';
		document.forms['groupEventDetailAddForm'].submit();
    }
	
	/*if(addGroupEventDetailData != null && addGroupEventDetailData.id !=null )
	{
		$scope.edit = true;
		$scope.add.barnIdEdit = barnData.barnId;
		console.log($scope.add.barnId);
    	$scope.add.phaseTypeId = barnData.phaseTypeId;
    	$scope.add.location = barnData.location;
		$scope.add.area = barnData.area;
		$scope.add.feederCount = barnData.feederCount;       
		$scope.add.waterAccessCount = barnData.waterAccessCount;
		$scope.add.active = barnData.active;		
		$scope.add.ventilationTypeId = barnData.ventilationTypeId;
		$scope.add.id = barnData.id
		$scope.add.barnId = 1;
    	
	}*/
	
	/*$scope.addBarn = function() {
	console.log($scope.add.barnId);
	console.log($scope.barnAddForm.$valid);
		if($scope.barnAddForm.$valid)
			{
			var postParam;
				if( barnData != null && barnData.barnId !=null )
				{
					postParam = {
							"barnId" : barnData.barnId,
							"phaseTypeId" : $scope.add.phaseTypeId,
							"location" : $scope.add.location,
							"area" : $scope.add.area,
							"feederCount" : $scope.add.feederCount,
							"waterAccessCount" : $scope.add.waterAccessCount,
							"active" : barnData.active,
							"id" : $scope.add.id,
							"premiseId" : barnData.generatedPremisesId,
							"ventilationTypeId" : $scope.add.ventilationTypeId
					};
				}
				else
				{
					postParam = {
							"barnId" : barnData.premisesId +''+$scope.add.barnId,
							"phaseTypeId" : $scope.add.phaseTypeId,
							"location" : $scope.add.location,
							"area" : $scope.add.area,
							"feederCount" : $scope.add.feederCount,
							"waterAccessCount" : $scope.add.waterAccessCount,
							"active" : true,
							"id" : $scope.add.id,
							"premiseId" : barnData.generatedPremisesId,
							"ventilationTypeId" : $scope.add.ventilationTypeId
					};
				}
				console.log(postParam);
				var res = $http.post('rest/barn/insertBarnRecord', postParam);
				res.success(function(data, status, headers, config) {
					if(data.statusMessage==="SUCCESS")
					{
						$modalInstance.close(data);					
						return data;
					}
					else
					{
						$scope.alertMessage = data.payload;
						$scope.alertVisible = true;
					}
				});
				res.error(function(data, status, headers, config) {
					console.log( data);
					$scope.alertMessage = data.statusMessage;
					$scope.alertVisible = true;
				});
			};
	}*/
	
	
});

