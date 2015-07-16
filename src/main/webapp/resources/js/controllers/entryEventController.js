pigTrax.controller('EntryEventController', function($scope, $http,$window,restServices) {	
	$scope.alertMessage = "";
	$scope.errorMessage = "";
	$scope.searchOption = "";
	$scope.pigInfo = {};
	$scope.parent = {birthDate:''};
	$scope.populateBarns = function(companyId){
		$scope.pigInfo.companyId = companyId;
		restServices.getBarns(function(data){
			 if(!data.error)
			 {
				    $scope.entryEventMap = data.payload;
				    $scope.barns = $scope.entryEventMap["barnList"];
				    $scope.sexTypes = $scope.entryEventMap["sexTypeMap"];
			 }
		});
	};
	
	
	 $scope.getPenList = function(){
			restServices.getPenList($scope.pigInfo.barnId, function(data){
				 if(!data.error)
				 {
					    $scope.penInfo = data.payload;
				 }
			});
		};
		
		
		$scope.addEntryEvent = function(){
			if($scope.entryEventForm.$valid)
			{
				var birthDate = document.getElementById("birthDate").value;
				$scope.pigInfo["birthDate"] = birthDate;
				restServices.saveEntryEventInformation($scope.pigInfo, function(data){
					if(!data.error)
						{
							$scope.errorMessage = "";
							$scope.alertMessage = "Pig information saved successfully";
						}
					else
						{
							$scope.alertMessage = "";
							$scope.errorMessage = data.statusMessage;
						}
				});
			}
		};
		
		
		$scope.getPigInformation = function()
		{
			
			var option = document.getElementById("rad1").value;
			if($scope.searchText == undefined || option == undefined)
			{
				   $scope.searchErrorMessage = "Please enter Pig Id/ Tattoo and select the corresponding option";
			}
			else
				{
					$scope.pigInfo = {};
					var searchPigInfo = {
							searchText : $scope.searchText,
							searchOption : option
					};
					restServices.getPigInformation(searchPigInfo, function(data)
					{
						if(!data.error){
							$scope.errorMessage = "";
							$scope.alertMessage = "";
							$scope.pigInfo = data.payload;			
							document.getElementById("birthDate").value = $scope.pigInfo.birthDate;
						}
						else
						{
							$scope.pigInfo = {};
							$scope.errorMessage = data.statusMessage;
							$scope.alertMessage = "";
						}
					});
				}
		}
		
});