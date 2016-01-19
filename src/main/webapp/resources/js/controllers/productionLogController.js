pigTrax.controller('ProductionLogController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	
	$scope.productionLog = {};
	$scope.DateUtils = DateUtils;
	
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
	
		
		$scope.loadPremises = function()
		{
			var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId+'&premisesType=null');
			res.success(function(data, status, headers, config) {
				$scope.premiseList = data.payload;
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});	
		}
		
		
	$scope.setCompanyId = function(companyId, loggedInUserName)
	{
		$rootScope.companyId = companyId;
		$rootScope.loggedInUser = loggedInUserName;
		$scope.getProductionLogList();		
		$scope.getLogEventTypes();
		$scope.loadPremises();
	};
	
	
	$scope.getRooms = function()
	{
		restServices.getRoomsForPremise($scope.productionLog["premiseId"], function(data){
			if(!data.error){
				$scope.roomMap = data.payload;
			}
		});
	}
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		   var  dateObj = Date.parse(dateVal);		   
		   if(dateObj == null)
			{
			   if(fieldName == "observationDate")
				{
					   $scope.observationDateRequired = true;
					   $scope.productionLog["observationDate"] = null;
				}
			   
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "observationDate")
				{
				   $scope.observationDateRequired = false;
				   $scope.productionLog["observationDate"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			  
			}
		}
		else
		{
			if(fieldName == "observationDate")
			{
				   $scope.observationDateRequired = true;
				   $scope.productionLog["observationDate"] = null;
			}		 
		}
	  }
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
		$scope.observationDateRequired = false;
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
						"endDate" : DateUtils.convertLocaleDateToServer(new Date(dates[1])),
						"selectedPremise" : $scope.selectedPremise
			};
		}
		else
			{
			var productionLog = {
					"companyId" : $rootScope.companyId,
					"startDate" : null,
					"endDate" : null,
					"selectedPremise" : $scope.selectedPremise
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
		if($scope.productionLog["observationDate"] == null || $scope.productionLog["observationDate"] == undefined)
		{
			$scope.observationDateRequired = true;
		}
		else
		{
			$scope.observationDateRequired = false;			
		}
		
		if($scope.productionLogListForm.$valid && !$scope.observationDateRequired)
		{	
			
			$scope.productionLog["companyId"] = $rootScope.companyId;
			
		   restServices.productionLog($scope.productionLog, function(data){			   
			   $('#addProductionLogModal').modal('hide');
			   $('body').removeClass('modal-open');
				$('.modal-backdrop').remove();
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
		$scope.productionLog["logEventTypeId"] = selectedLog["logEventTypeId"];
		$scope.productionLog["roomId"] = selectedLog["roomId"];
		$scope.productionLog["eventId"] = selectedLog["eventId"];
		$scope.productionLog["groupId"] = selectedLog["groupId"];
		$scope.productionLog["observationDate"] = selectedLog["observationDate"];
		$scope.productionLog["observationDateStr"] = selectedLog["observationDateStr"];	
		$scope.productionLog["premiseId"] = selectedLog["premiseId"];
		$scope.getRooms();
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
