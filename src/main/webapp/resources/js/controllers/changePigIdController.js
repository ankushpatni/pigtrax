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
	$scope.setCompanyId = function(companyId){
	$rootScope.companyId = companyId;
	};
	
		$scope.getPigInformationForChangeId = function()
		{
			
			var option = "";
			if(document.getElementById("rad1").checked)
				 option = document.getElementById("rad1").value;
			 else if(document.getElementById("rad2").checked)
				 option = document.getElementById("rad2").value;
			 
			if($scope.searchText == undefined || option == "")
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
							companyId : $rootScope.companyId
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
			var changeDate = document.getElementById("changeDate").value;
			$scope.pigInfo["changePigIdDate"] = changeDate;
			
			if($scope.pigInfo["changePigIdDate"] == undefined || $scope.pigInfo["changePigIdDate"] == null)
			{
					$scope.changeDateRequired = true;
			}
			else if($scope.changeIdEventForm.$valid && !$scope.changeDateRequired)
			{
				$scope.pigInfo["changePigIdDate"] = changeDate;
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