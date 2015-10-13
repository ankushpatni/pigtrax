var feedEventController = pigTrax.controller('SalesEventController', function($scope,$rootScope,$modal,$http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.transportDestination;
	$scope.transportTruck;
	$scope.transportTrailer;
	$scope.premiseList={};
	$scope.removalEventType={};
	$scope.groupEventList={};
	$scope.groupEventOriginalList={};
	$scope.pigInfoList={};
	$scope.pigInfoOriginalList={};
	$scope.removalExceptSales={};
	$scope.sourceAndDestinationPremisesSameError = false;
	
	
	$scope.setCompanyId = function(companyId,removalTypeId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		console.log(companyId);
		//$scope.removalExceptSales.removalEventId = removalGeneratedId;
		$scope.removalTypeId = removalTypeId;
		$scope.removalTypeId.removalEventId = removalTypeId;
		console.log(removalTypeId);
		
		var res1 = $http.get('rest/transportJourney/getTransportJourneyMasterData?generatedCompanyId='+$scope.companyId);
		res1.success(function(data, status, headers, config) {
			console.log(data);
			$scope.transportDestination = data.payload[0];	
			$scope.transportTruck = data.payload[1];
			$scope.transportTrailer = data.payload[2];
			
		});
		res1.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		var res2 = $http.get('rest/util/getRemovalExceptSalesMasterData?companyId='+$rootScope.companyId);
		res2.success(function(data, status, headers, config) {
			console.log(data);
			
			$scope.removalEventType = data.payload[0];
			$scope.pigInfoOriginalList = data.payload[1];
			$scope.premiseList = data.payload[2];
			$scope.groupEventOriginalList = data.payload[3];
			for( var x in $scope.groupEventOriginalList)
			{
				if( $scope.groupEventOriginalList[x].active && $scope.groupEventOriginalList[x].currentInventory > 0)
					{		
						$scope.groupEventList[x] = $scope.groupEventOriginalList[x];
					}
			}
			for( var x in $scope.pigInfoOriginalList)
			{
				if( $scope.pigInfoOriginalList[x].active )
					{		
						$scope.pigInfoList[x] = $scope.pigInfoOriginalList[x];
					}
			}
		});
		res2.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	};
	

	$scope.getRemovalExceptSales = function(removalExceptSalesId)
    {
		if(removalExceptSalesId && removalExceptSalesId != undefined )
		{
			$scope.removalExceptSales.id = removalExceptSalesId;
		}
		restServices.getRemovalExceptSales($scope.removalExceptSales, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.removalExceptSales = data.payload;
				}
			else
				{
					
				}
		});
    }
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.removalExceptSales = {};
		$scope.changeText();
	}
	
	$scope.clearAllMessages = function()
	{
		$scope.eventErrorMessage = false;
		$scope.eventDuplicateErrorMessage = false;
		$scope.noOfPigsrequired  = false;
		$scope.noOfPigWrongCount = false;
		$scope.errorRemovalDateTime = false;
	};
	
	$scope.$watch("removalExceptSales.destPremiseId", function(newValue, oldValue) {
		if (newValue != null && newValue != undefined && newValue !=0 && newValue === $scope.removalExceptSales.premiseId) {
			$scope.sourceAndDestinationPremisesSameError = true;
		}
		else
		{
			$scope.sourceAndDestinationPremisesSameError = false;
		}
	});
	
	/*$scope.decideGroupPremises = function()
	{
		var option = "";
		if(document.getElementById("rad1").checked)
		{
			 option = document.getElementById("rad1").value;
		}
		 else if(document.getElementById("rad2").checked)
		{
			 option = document.getElementById("rad2").value;
		}
	}*/
	
	$scope.addTransportJourney = function()
	{
		var modalInstance = $modal.open ({
			templateUrl: 'transportJourney',
			controller: 'addTransportJourneyCtrl',
			backdrop:true,
			windowClass : 'cp-model-window',
			resolve:{
				transportJourneyMasterData : function(){
					var transportJourneyMasterData={};
					transportJourneyMasterData.transportDestination = $scope.transportDestination;
					transportJourneyMasterData.transportTruck = $scope.transportTruck;
					transportJourneyMasterData.transportTrailer = $scope.transportTrailer;
					transportJourneyMasterData.transportJourney = $scope.removalExceptSales.transportJourney;					
					return transportJourneyMasterData;
				}
			}
		});
		
		modalInstance.result.then( function(res) { 
			console.log(res);
			$scope.removalExceptSales.transportJourney = res;
		});
	}
	
	$scope.addSalesEventDetails = function() 
	{
		$scope.clearAllMessages();
		if(document.getElementById("salesDateTime").value === "")
		{
			$scope.removalDateTimerequired = true;
			return;
		}
		if($scope.sourceAndDestinationPremisesSameError == true)
		{
			return false;
		}
		$scope.removalExceptSales.salesDateTime = document.getElementById("salesDateTime").value;
		if($scope.removalExceptSales.id === 'undefined' )
		{
			$scope.removalExceptSales.id = 0;
		}
		
		if(document.getElementById("rad1").checked)
		{
			$scope.removalExceptSales.pigInfoId = 0;
			if($scope.removalExceptSales.numberOfPigs ==0)
			{
				$scope.noOfPigsrequired = true;	
				return false;
			}
			var groupevent = $scope.groupEventList[$scope.removalExceptSales.groupEventId];
			if(groupevent.currentInventory < $scope.removalExceptSales.numberOfPigs)
			{
				$scope.noOfPigWrongCount  = true;
				return false;
			}			
			console.log(groupevent.groupStartDateTime);
			if($scope.removalExceptSales.salesDateTime < groupevent.groupStartDateTime)
			{
				$scope.errorRemovalDateTime = true;
				return false;
			}
		}
		 else if(document.getElementById("rad2").checked)
		{
			 $scope.removalExceptSales.groupEventId = 0;
			 $scope.removalExceptSales.numberOfPigs = 1;
		}
		
		
		if($scope.salesEventForm.$valid)
			{
				$scope.clearAllMessages();
				$scope.removalExceptSales.companyId = $scope.companyId;
				console.log($scope.removalExceptSales);
				restServices.addSalesEventDetails($scope.removalExceptSales, function(data){
				console.log(data);
					if(!data.error)
						{
							$scope.gotoRemovalEvent();
						}
					else
						{
							$scope.clearAllMessages();
							if(data.duplicateRecord)
							{
								$scope.removalEventDuplicateErrorMessage = true;
							}
							else
							{
								$scope.entryEventErrorMessage = true;
							}
						} 
				});
			}
		
		$window.scrollTo(0,5);
	}
	
	 $scope.gotoRemovalEvent = function()
	    {
	    	document.getElementById("selectedCompany").value = $scope.companyId;		
			document.getElementById("fromExcept").value = true;
			document.forms['salesEventForm'].action = 'removalEvent';
			document.forms['salesEventForm'].submit();
	    }	
	
});	