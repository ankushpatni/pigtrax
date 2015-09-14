var feedEventController = pigTrax.controller('RemovalExceptSalesController', function($scope,$rootScope,$modal,$http,$window,restServices) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.transportDestination;
	$scope.transportTruck;
	$scope.transportTrailer;
	$scope.premiseList={};
	$scope.removalEventType={};
	$scope.groupEventList={};
	$scope.pigInfoList={};
	$scope.removalExceptSales={};
	
	
	$scope.setCompanyId = function(companyId,removalIdEntered,removalGeneratedId,removalExceptSalesId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		$scope.removalId = removalIdEntered;
		$scope.removalGeneratedId = removalGeneratedId;
		$scope.removalExceptSalesId = removalExceptSalesId;
		$scope.removalExceptSales.removalEventId = removalGeneratedId;
		
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
			$scope.pigInfoList = data.payload[1];
			$scope.premiseList = data.payload[2];
			$scope.groupEventList = data.payload[3]
		});
		res2.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		
		if( removalExceptSalesId)
		{
			$scope.getRemovalExceptSales(removalExceptSalesId);
		}
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
		$scope.feedEvent = {};
		$scope.changeText();
	}
	
	$scope.clearAllMessages = function()
	{
		$scope.eventErrorMessage = false;
		$scope.eventDuplicateErrorMessage = false;
		$scope.noOfPigsrequired  = false;
	};
	
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
	
	$scope.addRemovalExceptSales = function() 
	{
		if(document.getElementById("removalDateTime").value === "")
		{
			$scope.removalDateTimerequired = true;
			return;
		}
		if($scope.removalExceptSales.id === 'undefined' )
		{
			$scope.removalExceptSales.id = 0;
		}
		
		if(document.getElementById("rad1").checked)
		{
			$scope.removalExceptSales.pigInfoId = 0;
		}
		 else if(document.getElementById("rad2").checked)
		{
			 $scope.removalExceptSales.groupEventId = 0;
			 $scope.removalExceptSales.numberOfPigs = 1;
		}
		
		if($scope.removalExceptSales.numberOfPigs ==0)
		{
			$scope.noOfPigsrequired = true;
		}
		if($scope.removalExceptFormSales.$valid)
			{
				$scope.clearAllMessages();
				$scope.removalExceptSales.removalDateTime = document.getElementById("removalDateTime").value;
				console.log($scope.removalExceptSales);
				restServices.addRemovalExceptSales($scope.removalExceptSales, function(data){
				console.log(data);
					if(!data.error)
						{
							
							/*$scope.entryEventSuccessMessage = true;
							$scope.getRemovalEvent($scope.removalEventForm.ticketNumber,true);*/
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
	    	document.getElementById("removalEventTicketNumber").value = $scope.removalId;	
			document.getElementById("selectedCompany").value = $scope.companyId;		
			document.forms['removalExceptFormSales'].action = 'removalEvent';
			document.forms['removalExceptFormSales'].submit();
	    }	
	
});