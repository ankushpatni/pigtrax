pigTrax.controller('editRemovalCtrlr', function($scope, $http, $window, $modalInstance,premisesData, restServices,DateUtils) {	
	console.log(premisesData);
	$scope.removalExceptSales={};
	$scope.removalExceptSales.numberOfPigs=premisesData.numberOfPigs;
	$scope.removalExceptSales.weightInKgs=premisesData.weightInKgs;
	$scope.removalExceptSales.remarks=premisesData.remarks;
	$scope.entryDateStr=DateUtils.getFormatedDate(premisesData.removalDateTime);
	console.log("Removal Date Time");
	console.log($scope.entryDateStr);
	console.log(premisesData.removalDateTime);
	
	
	$scope.updateRomvalData = function() {
			{			
				var postParam;
				if( premisesData.removalDateTime!=null)
				{
					postParam = {
							"companyId" : premisesData.companyId,
							"destPremiseId" : premisesData.destPremiseId,
							"fromGroupIdStr" : premisesData.fromGroupIdStr,
							"groupEventId" : premisesData.groupEventId,
							"groupIdStr" : premisesData.groupIdStr,
							"id" : premisesData.id,
							"lastUpdated" : premisesData.lastUpdated,
							"mortalityReasonId" : premisesData.mortalityReasonId,
							"numberOfPigs" : $scope.removalExceptSales.numberOfPigs,
							"pigInfoId" : premisesData.pigInfoId,
							"premiseId" : premisesData.premiseId,
							"remarks" : $scope.removalExceptSales.remarks,
							"removalDateTime" : premisesData.removalDateTime,//DateUtils.convertLocaleDateToServer(new Date( $scope.entryDateStr)) ,
							"removalEventId" : premisesData.removalEventId,
							"revenueUsd" : premisesData.revenueUsd,
							"roomId" : premisesData.roomId,
							"transportJourney" : premisesData.transportJourney,
							"transportJourneyId" : premisesData.transportJourneyId,
							"userUpdated" : premisesData.userUpdated,
							"weightInKgs" : $scope.removalExceptSales.weightInKgs
					};
				}
				else if (premisesData.salesDateTime!=null)
				{
					postParam = {
							"companyId" : premisesData.companyId,
//							"destPremiseId" : premisesData.destPremiseId,
//							"fromGroupIdStr" : premisesData.fromGroupIdStr,
							"groupEventId" : premisesData.groupEventId,
							"invoiceId" : premisesData.invoiceId,
//							"groupIdStr" : premisesData.groupIdStr,
							"id" : premisesData.id,
							"lastUpdated" : premisesData.lastUpdated,
//							"mortalityReasonId" : premisesData.mortalityReasonId,
							"numberOfPigs" : $scope.removalExceptSales.numberOfPigs,
							"pigInfoId" : premisesData.pigInfoId,
							"removalEventId" : premisesData.removalEventId,
//							"premiseId" : premisesData.premiseId,
							"remarks" : $scope.removalExceptSales.remarks,
							"salesDateTime" : premisesData.salesDateTime,//DateUtils.convertLocaleDateToServer(new Date( $scope.entryDateStr)) ,
//							"remarks" : $scope.removalExceptSales.remarks,
							"salesReasons" : premisesData.salesReasons,
							"salesReasonsAsString" : premisesData.salesReasonsAsString,
							"salesTypes" : premisesData.salesTypes,
							"salesTypesAsString" : premisesData.salesTypesAsString,
							"soldTo" : premisesData.soldTo,
							"ticketNumber" : premisesData.ticketNumber,
							"revenueUsd" : premisesData.revenueUsd,
//							"roomId" : premisesData.roomId,
							"transportJourney" : premisesData.transportJourney,
							"transportJourneyId" : premisesData.transportJourneyId,
							"userUpdated" : premisesData.userUpdated,
							"weightInKgs" : $scope.removalExceptSales.weightInKgs
					};
				}
				console.log(postParam);
				var res=null;
				if (premisesData.removalDateTime!=null){
					res = $http.post('rest/removalEvent/updateRemovalExceptSales', postParam);
				}
				else
				{
					res = $http.post('rest/removalEvent/updateSalesEventDetails', postParam);
					
				}
				
			
				res.success(function(data, status, headers, config) {
					if(data.statusMessage==="Success")
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
	}
	
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
	
});

