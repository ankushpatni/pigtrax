var feedEventController = pigTrax.controller('SalesEventController', function($scope,$rootScope,$modal,$http,$window,restServices,DateUtils) {
	
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
	$scope.saleTypeValues = [];
	$scope.selectedSalesTypes=[];
	$scope.selectedSalesReason;
	
	$scope.multiselectdropdownsettings = {
		    scrollableHeight: '200px',
		    scrollable: true
		};
	
	
	$scope.getTransportDestinationList = function(){		
		var res = $http.get('rest/transportDestination/getTransportDestination?generatedCompanyId='+$scope.companyId);
			res.success(function(data, status, headers, config) {
				$scope.destinationList = data.payload[0];		
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
	
	
	
	$scope.setCompanyId = function(companyId,removalTypeId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		console.log(companyId);
		//$scope.removalExceptSales.removalEventId = removalGeneratedId;
		$scope.removalTypeId = removalTypeId;
		$scope.removalExceptSales.removalEventId = removalTypeId;
		console.log(removalTypeId);
		
		$scope.getSaleTypes();
		$scope.getSaleReasons();
		
		
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
			
			var removalEventTypeMap = data.payload[0];
			$scope.removalEventTypeKeys = removalEventTypeMap['RemovalEventKey'];
			$scope.removalEventType = removalEventTypeMap['RemovalEventValue'];
			
			//$scope.removalEventType = data.payload[0];
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
		
		
		
		$scope.getTransportDestinationList();
		
	};
	
	
	
	$scope.getSaleTypes = function()
	{
		restServices.getSaleTypes(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.saleTypeKeys = responseList[0];
				$scope.saleTypeKeyValues =responseList[1];
				$scope.saleTypeValues = [];
				angular.forEach($scope.saleTypeKeys, function(key){					
                   var itemObj = {"id" : key, "label":$scope.saleTypeKeyValues[key]}  
				   $scope.saleTypeValues.push(itemObj);
               })	
			}
		});
	}
	

	$scope.getSaleReasons = function()
	{
		restServices.getSaleReasons(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.saleReasonKeys = responseList[0];
				$scope.saleReasonKeyValues =responseList[1];
			}
		});
	}
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
		
		if($scope.selectedSalesTypes != null && $scope.selectedSalesTypes.length > 0)
		{
			var salesTypes =[];
			salesTypes.push(parseInt($scope.selectedSalesTypes));
			$scope.removalExceptSales.salesTypes = salesTypes;
		}
		
		if($scope.selectedSalesReason != null && $scope.selectedSalesReason.length > 0)
			{
			   var salesReason = [];
			   salesReason.push(parseInt($scope.selectedSalesReason));
			   $scope.removalExceptSales.salesReasons = salesReason;
			}
		
		
		if($scope.removalExceptSales["salesDateTime"] === ""  || 
		$scope.removalExceptSales["salesDateTime"] === undefined)
		{
			$scope.removalDateTimerequired = true;
			return;
		}
		if($scope.sourceAndDestinationPremisesSameError == true)
		{
			return false;
		}
		
		if($scope.removalExceptSales.id === 'undefined' )
		{
			$scope.removalExceptSales.id = 0;
		}
		
		if(document.getElementById("rad1").checked)
		{
			console.log($scope.removalExceptSales.numberOfPigs);
			$scope.removalExceptSales.pigInfoId = 0;
			if($scope.removalExceptSales.numberOfPigs ==0 || $scope.removalExceptSales.numberOfPigs === undefined || 
			$scope.removalExceptSales.numberOfPigs === '')
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
				
				var salesDateTime = new Date($scope.removalExceptSales.salesDateTime);
				$scope.removalExceptSales.salesDateTime = DateUtils.convertLocaleDateToServer(salesDateTime);
				
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
		
	$scope.dateCheck = function(dateVal)
	{	
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
			$scope.removalDateTimerequired = false;			   
		   //var  dateObj = Date.parse(dateVal);	
		   var  dateObj = DateUtils.parse(dateVal,"dd/MM/yyyy");
		   if(dateObj == null)
			{
			  	   $scope.removalExceptSales["salesDateTime"] = "";
			}
		   else
			{
			   $scope.dateError = false;
				
				$scope.removalExceptSales.salesDateTime = DateUtils.convertLocaleDateToServer(dateObj);
			}
		}
		else
		{
			   $scope.removalExceptSales["salesDateTime"] = "";
		}
	  }
	}
	
});	