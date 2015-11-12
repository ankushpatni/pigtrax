pigTrax.controller('ProductionLogController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	
	$scope.productionLog = {};
	
	$scope.getProductionLogList = function(){
		var productionLog = {
				"companyId" : $rootScope.companyId,
				"startDate" : null,
				"endDate" : null
		};
		 restServices.getProductionLogList(productionLog, function(data){
				 if(!data.error)
				 {
					$scope.rowCollection = data.payload;
					$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
				 }
			});
		};
	
	$scope.setCompanyId = function(companyId, loggedInUserName)
	{
		$rootScope.companyId = companyId;
		$rootScope.loggedInUser = loggedInUserName;
		$scope.getProductionLogList();
		$scope.getRooms();
		$scope.getLogEventTypes();
	};
	
	
	$scope.getRooms = function()
	{
		restServices.getRoomsForCompany($rootScope.companyId, function(data){
			if(!data.error){
				$scope.roomMap = data.payload;
			}
		});
	}
	
	$scope.getLogEventTypes = function()
	{
		restServices.getLogEventTypes(function(data){
			if(!data.error){
				var responseList = data.payload;
				$scope.logEventKeys = responseList[0];
				$scope.logEventTypes =responseList[1];
			}
		});
	}
	
	$scope.clearAllMessages = function()
	{
		$scope.observationRequired = false;
		$scope.productionLogSaved = false;
		$scope.productionLogError = false;
		$scope.productionLogDeleted = false;
	}
	
	
	$scope.AddProductionLog = function()
	{
		$scope.productionLog = {};
	}
	
	
	$scope.searchProductionLog = function()
	{
		var dateRangeVal = document.getElementById("reservation").value;		
		if(dateRangeVal != null && dateRangeVal != "")
		{
			var dates = dateRangeVal.split("-");
			
			var productionLog = {
						"companyId" : $rootScope.companyId,
						"startDate" : DateUtils.convertLocaleDateToServer(new Date(dates[0])),
						"endDate" : DateUtils.convertLocaleDateToServer(new Date(dates[1]))
			};
		}
		else
			{
			var productionLog = {
					"companyId" : $rootScope.companyId,
					"startDate" : null,
					"endDate" : null
				};
			}
			
			 restServices.getProductionLogList(productionLog, function(data){
				 if(!data.error)
				 {
					$scope.rowCollection = data.payload;
					$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
				 }
			});
		
	}
	
	
	$scope.saveProductionLog = function()
	{	
		if($scope.productionLog["observation"] == null)
		{
			$scope.observationRequired = true;
			valid = false;
		}
		else
		{
			$scope.observationRequired = false;			
		}
		if(!$scope.observationRequired)
		{	
			var observationDate = document.getElementById("observationDate").value;
			
			$scope.productionLog["companyId"] = $rootScope.companyId;
			$scope.productionLog["observationDate"] = observationDate;
		   restServices.productionLog($scope.productionLog, function(data){
			   if(!data.error){
				   $scope.clearAllMessages();
				   $scope.productionLogSaved = true;	
				   $scope.productionLog = {};
				   $scope.getProductionLogList();				   
			   }
			   else
			   {
				   $scope.productionLogError = true;	
			   }
			   $window.scrollTo(0, 0);
		   });	
		}
	};
	
	$scope.selectProductionLogForEdit = function(selectedLog)
	{
		$scope.productionLog["observation"] = selectedLog["observation"];
		$scope.productionLog["id"] = selectedLog["id"];
		$scope.productionLog["logEventTypeId"] = selectedLog["logEventTypeId"]
		$scope.productionLog["roomId"] = selectedLog["roomId"]
		$scope.productionLog["eventId"] = selectedLog["eventId"]
		$scope.productionLog["observationDate"] = selectedLog["observationDate"]
		document.getElementById("observationDate").value = $scope.productionLog["observationDate"];
	}
	
	
	$scope.deleteProductionLog = function(id)
	{
		restServices.deleteProductionLog(id, function(data){
			if(!data.error)
			{
			 
				var productionLog = {
						"companyId" : $rootScope.companyId,
						"startDate" : null,
						"endDate" : null
				};
				 restServices.getProductionLogList(productionLog, function(data){
						 if(!data.error)
						 {
							$scope.rowCollection = data.payload;
							$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
							$scope.clearAllMessages();
							$scope.productionLogDeleted = true;
							$scope.productionLog = {};
						 }
					});	
				
			 
			}
			else
			{
				$scope.clearAllMessages();
				$scope.productionLogError = true;
			}
		});
	}
	
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.productionLog = {};
	}
});
