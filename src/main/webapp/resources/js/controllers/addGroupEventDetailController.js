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
	$scope.dateOfEntryFlag =  false;
	
	$scope.setCompanyId = function( companyId,groupId,groupDetailId,groupGeneratedId,groupStartDateTime)
	{
		$scope.groupAlphaId = groupId;
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		$scope.groupEvent.groupId = groupGeneratedId;
		$scope.groupStartDateTime = groupStartDateTime;
		$scope.groupEvent.companyId = companyId;
		console.log($scope.groupEvent.companyId);
		/*restServices.getPhaseOfProductionType($scope.companyId, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.phaseOfProductionType = data.payload[0];	
					$scope.roomType = data.payload[1];
									
				}
			else
				{
					console.log( "failure message: " + {data: data});
				} 
		});*/
		
		var res2 = $http.get('rest/util/getPhaseOfProductionType?companyId='+$scope.companyId);
			res2.success(function(data, status, headers, config) {
				console.log(data);
				$scope.phaseOfProductionType = data.payload[0];	
				$scope.roomType = data.payload[1];
				
			});
			res2.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});
		console.log(groupDetailId);
		if(groupDetailId != 'undefined' )
		{
		console.log(groupDetailId);
			$scope.getGroupEventDetail(groupDetailId);
		}
		
		
	};
	
	$scope.getGroupEventDetail = function(groupDetailId)
    {
		restServices.getGroupEventDetail(groupDetailId, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.groupEvent = data.payload;
					$scope.groupEvent.companyId = $rootScope.companyId;
				}
			else
				{
					
				}
		});
    }
	
	
	$scope.addGroupEventDetail = function()
    {
		if($scope.groupEventDetailAddForm.$valid)
		{
			var dateOfEntry = document.getElementById("dateOfEntry").value;
			$scope.groupEvent["dateOfEntry"] = dateOfEntry;
			
			if(dateOfEntry<$scope.groupStartDateTime)
			{
				$scope.dateOfEntryFlag =  true;
				return;
			}
			else
			{
				$scope.dateOfEntryFlag =  false;
			}
			
			if(angular.isObject($scope.groupEvent.employeeGroupId))
			{
				$scope.groupEvent.employeeGroupId = 0;
			}
			$scope.groupEvent.companyId = $rootScope.companyId;
			restServices.addGroupEventDetail($scope.groupEvent, function(data){
				console.log(data);
				if(!data.error)
					{
						$scope.entryEventSuccessMessage = true;
						$scope.gotoGroupEvent();
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
		document.getElementById("selectedCompany").value = $scope.companyId;		
		document.forms['groupEventDetailAddForm'].action = 'groupEvent';
		document.forms['groupEventDetailAddForm'].submit();
    }	
	
});

