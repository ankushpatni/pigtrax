pigTrax.controller('EntryEventController', function($scope, $http,$window,restServices) {
	
	$scope.clearAllMessages = function()
	{
		$scope.searchDataErrorMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventDeleteMessage = false;
		$scope.searchErrorMessage = false;
	};
	
	$scope.clearAllMessages();
	$scope.searchOption = "";
	$scope.pigInfo = {};
	$scope.populateBarns = function(companyId){
		$scope.pigInfo.companyId = companyId;
		restServices.getBarns(companyId, function(data){
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
							$scope.clearAllMessages();
							$scope.entryEventSuccessMessage = true;
						}
					else
						{
							$scope.clearAllMessages();
							$scope.entryEventErrorMessage = true;
						}
				});
			}
		};
		
		
		$scope.getPigInformation = function()
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
							searchOption : option
					};
					restServices.getPigInformation(searchPigInfo, function(data)
					{
						if(!data.error){
							$scope.clearAllMessages();
							$scope.pigInfo = data.payload;			
							document.getElementById("birthDate").value = $scope.pigInfo.birthDate;
							
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
		
		
		$scope.deletePigInfo = function()
		{
			
				
					restServices.deletePigInfo($scope.pigInfo.id, function(data){
						$scope.clearAllMessages();
						$scope.entryEventDeleteMessage = true;
					});
				
		};
		
});