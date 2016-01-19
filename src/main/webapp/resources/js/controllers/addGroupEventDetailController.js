var addGroupEventDetailController = pigTrax.controller('AddGroupEventDetailController', function($scope, $http, $window, restServices,$rootScope, DateUtils) {	
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.alertMessage;
	$scope.groupEvent={};
	$scope.groupEvent.employeeGroupId={};
	$scope.groupEvent.employeeGroup = {};
	$scope.phaseOfProductionType ={};
	$scope.roomType = {};
	$scope.barnList = {};
	$scope.dateOfEntryFlag =  false;
	$scope.transportDestination;
	
	
	$scope.loadPremises = function()
	{
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId+'&premisesType=null');
		res.success(function(data, status, headers, config) {
			$scope.farmList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.getPremisesListBySowSource = function(){
		var res = $http.get('rest/premises/getPremisesListBySowSource?generatedCompanyId='+$rootScope.companyId);
			res.success(function(data, status, headers, config) {
				$scope.premiseList = data.payload;
				
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});	
	};
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		   var  dateObj = Date.parse(dateVal);		   
		   if(dateObj == null)
			{
			   if(fieldName == "dateOfEntry")
				{
					   $scope.dateOfEntryFlag = true;
					   $scope.groupEvent["dateOfEntry"] = null;
				}			   
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "dateOfEntry")
				{
				   $scope.dateOfEntryFlag = false;
				   $scope.groupEvent["dateOfEntry"] = DateUtils.convertLocaleDateToServer(dateObj);
				}			  
			}
		}
		else
		{
			if(fieldName == "dateOfEntry")
			{
				   $scope.dateOfEntryFlag = true;
				   $scope.groupEvent["dateOfEntry"] = null;
			}		  
		}
	  }
	}
	
	
	$scope.setCompanyId = function( companyId,groupId,groupDetailId,groupGeneratedId,groupStartDateTime, searchPremiseId)
	{
		$scope.groupAlphaId = groupId;
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		$scope.groupEvent.groupId = groupGeneratedId;
		$scope.groupStartDateTime = groupStartDateTime;
		$scope.groupEvent.companyId = companyId;
		$scope.searchPremiseId = searchPremiseId;
		console.log(groupGeneratedId);
		
		var res1 = $http.get('rest/transportJourney/getTransportJourneyMasterData?generatedCompanyId='+$scope.companyId);
		res1.success(function(data, status, headers, config) {
			console.log(data);
			$scope.transportDestination = data.payload[0];				
		});
		res1.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		var res2 = $http.get('rest/util/getPhaseOfProductionType?companyId='+$scope.companyId);
			res2.success(function(data, status, headers, config) {
				console.log(data);
				$scope.phaseOfProductionType = data.payload[0];	
				//$scope.roomType = data.payload[1];
				$scope.barnList = data.payload[2];
				
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
		
		$scope.loadPremises();
		
		$scope.getPremisesListBySowSource();
	};
	
	
	$scope.getRooms = function()
	{		
		restServices.getRoomsForPremise($scope.groupEvent["premiseId"], function(data){
			if(!data.error){
				$scope.roomType = data.payload;
			}
		});
	}
	
	$scope.getBarnDetailsByRoom = function()
	{
		restServices.getBarnDetailsByRoom($scope.groupEvent.roomId, function(data){
			if(!data.error){
				var barn = data.payload;
				$scope.groupEvent["barnId"] = barn.id;
				$scope.groupEvent["barnIdValue"] = barn.barnId;
			}
		});
	}
	
	
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
			var dateOfEntry = $scope.groupEvent["dateOfEntry"] ;
			
			if(DateUtils.getServerFormat(dateOfEntry) <$scope.groupStartDateTime)
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
		document.getElementById("searchPremiseId").value  = $scope.searchPremiseId;
		document.forms['groupEventDetailAddForm'].action = 'groupEvent';
		document.forms['groupEventDetailAddForm'].submit();
    }	
	
});

