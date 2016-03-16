pigTrax.controller('ChangePigIdController', function($scope, $rootScope, $http,$window,restServices, DateUtils) {
	$scope.companyId = "";
	
	$scope.clearAllMessages = function()
	{ 
		$scope.searchDataErrorMessage = false;
		$scope.changedPigIdSuccess = false;
		$scope.changedPigIdError = false;
		$scope.duplicatePigIdErrorMessage = false;
		$scope.newPigIdActiveError = false;
		$scope.searchErrorMessage = false;
		
	};
	
	$scope.clearAllMessages();
	$scope.searchOption = "";
	$scope.pigInfo = {};
	
	
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
	
	$scope.setCompanyId = function(companyId){
	$rootScope.companyId = companyId;
	$scope.loadPremises();
	};
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		//   var  dateObj = Date.parse(dateVal);
		   var  dateObj = DateUtils.parse(dateVal,"dd/MM/yyyy");
		   if(dateObj == null)
			{
			   if(fieldName == "changePigIdDate")
				{
					   $scope.changeDateRequired = true;
					   $scope.pigInfo["changePigIdDate"] = null;
				}			  
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "changePigIdDate")
				{
				   $scope.changeDateRequired = false;
				   $scope.pigInfo["changePigIdDate"] = DateUtils.convertLocaleDateToServer(dateObj);
				}			   
			}
		}
		else
		{
			if(fieldName == "changePigIdDate")
			{
				   $scope.changeDateRequired = true;
				   $scope.pigInfo["changePigIdDate"] = null;
			}		   
		}
	  }
	}
	
	
	
		$scope.getPigInformationForChangeId = function()
		{
			
			var option = "";
			if(document.getElementById("rad1").checked)
				 option = document.getElementById("rad1").value;
			 else if(document.getElementById("rad2").checked)
				 option = document.getElementById("rad2").value;
			 
			if($scope.searchText == undefined || option == "" || $scope.selectedPremise == undefined || $scope.selectedPremise == "")
			{
				   $scope.clearAllMessages();
				   $scope.searchErrorMessage = true;
			}
			else
				{
					$scope.pigInfo = {};
					var searchPigInfo = {
							searchText : $scope.searchText,
							searchOption : option,
							companyId : $rootScope.companyId,
							selectedPremise : $scope.selectedPremise
					};
					restServices.getPigInformation(searchPigInfo, function(data)
					{
						if(!data.error){
							$scope.clearAllMessages();
							$scope.pigInfo = data.payload;
						}
						else
						{
							$scope.pigInfo = {};
							$scope.clearAllMessages();
							$scope.searchDataErrorMessage = true;
							
						}
					});
				}
		};
		
		
		
		$scope.checkNewPigIdStatus = function()
		{				
			var searchPigInfo = {
					searchText : $scope.pigInfo.newPigId,
					searchOption : "pigId",
					companyId : $rootScope.companyId
			};
			restServices.getPigInformationForChangeId(searchPigInfo, function(data)
			{
				if(!data.error){
					$scope.clearAllMessages();
					var changePigIdInfo = data.payload;
					if(!changePigIdInfo.enableChangeId)
						$scope.newPigIdActiveError = true;
					else
						$scope.newPigIdActiveError = false;
				}
				else
					{
						$scope.clearAllMessages();
					}
			});
				
		};
		
		
		
		$scope.changePigId = function()
		{	
			
			if($scope.pigInfo["changePigIdDate"] == undefined || $scope.pigInfo["changePigIdDate"] == null)
			{
					$scope.changeDateRequired = true;
			}
			else if($scope.changeIdEventForm.$valid && !$scope.changeDateRequired)
			{
				$scope.pigInfo["selectedPremise"] = $scope.selectedPremise;
				
				restServices.changePigId($scope.pigInfo, function(data)
				{
					if(!data.error)
					{
						$scope.clearAllMessages();
						$scope.changedPigIdSuccess = true;
						$scope.pigInfo = {};
						$scope.searchText = "";		
						$('#rad1').iCheck('uncheck');
						$('#rad2').iCheck('uncheck');		
					}
					else
						{
							if(data.duplicateRecord)
								$scope.duplicatePigIdErrorMessage = true;
							else							
								$scope.changePigIdError = true;
						}
				});
			}
		}
		
});


$(document).ready(function () {
	$('input[class="icheck changepigId"]').on('ifClicked', function (event) {
		angular.element("#ChangePigIdControllerId").scope().clearAllMessages();		
		angular.element("#ChangePigIdControllerId").scope().$apply();
	});		
});