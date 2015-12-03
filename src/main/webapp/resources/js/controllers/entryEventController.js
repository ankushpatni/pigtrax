pigTrax.controller('EntryEventController', function($scope, $http,$window,restServices, DateUtils, $rootScope) {
	$scope.companyId = "";
	$rootScope.companyId = "";
	$scope.object = {};
	
	 $scope.testData = {
		      blankDate: null,
		      realDate: new Date("September 30, 2010 15:30:00")
		    };
	
	
	$scope.clearAllMessages = function()
	{ 
		$scope.searchDataErrorMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventDeleteMessage = false;
		$scope.searchErrorMessage = false;
		$scope.entryEventDuplicateErrorMessage = false;
		$scope.entryDateRequired = false;
		$scope.invalidEntryDate = false;
		$scope.pigInfoEventsExistsMessage = false;
		$scope.invalidDateDuration = false;
		$scope.birthDateRequired = false;
	};
	
	
	$scope.loadPremises = function()
	{
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId);
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.clearAllMessages();
	$scope.searchOption = "";
	$scope.pigInfo = {};
	$scope.populateBarns = function(companyId){
		$scope.pigInfo.companyId = companyId;
		$rootScope.companyId = companyId;
		$scope.companyId  = companyId;
		restServices.getBarns(companyId, function(data){
			 if(!data.error)
			 {
				    $scope.entryEventMap = data.payload;
				    $scope.barns = $scope.entryEventMap["barnList"];
				    $scope.sexTypes = $scope.entryEventMap["sexTypeMap"];
				    $scope.getGfunctionTypes();
				    $scope.getGcompanyTypes();
				    $scope.getGlineTypes();
				    $scope.getOriginList();
				    $scope.loadPremises();				   
			 }
		});
		
	};
	
	
	$scope.getOriginList = function()
	{
		restServices.getOriginList( function(data){
			if(!data.error)
			{
				  $scope.originList = data.payload;
			}
		})
	};
	
	$scope.getGfunctionTypes = function()
	{
		restServices.getGfunctionTypes(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.functionKeys = responseList[0];
				$scope.gfunctionTypes =responseList[1];
			}
		});
	}
	
	$scope.getGcompanyTypes = function()
	{
		restServices.getGcompanyTypes(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.gcompanyKeys = responseList[0];
				$scope.gcompanyTypes =responseList[1];
			}
		});
	}
	
	$scope.getGlineTypes = function()
	{
		restServices.getGlineTypes(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.glineKeys = responseList[0];
				$scope.glineTypes =responseList[1];
			}
		});
	}
	
	 $scope.getPenList = function(){
			restServices.getPenList($scope.pigInfo.barnId, function(data){
				 if(!data.error)
				 {
					    $scope.penInfo = data.payload;
				 }
			});
		};
		
		
		$scope.addEntryEvent = function(){
			
			$scope.clearAllMessages();
			
			//var entryDate = document.getElementById("entryDate").value;
			//var birthDate = document.getElementById("birthDate").value;
			
			if($scope.pigInfo.entryDate == null || $scope.pigInfo.entryDate == undefined || $scope.pigInfo.entryDate == "")
			{
				$scope.entryDateRequired = true;
			}				
			
			if($scope.pigInfo.birthDate == null || $scope.pigInfo.birthDate == undefined || $scope.pigInfo.birthDate == "")
			{
				$scope.birthDateRequired = true;
			}				
			
			
			
			if($scope.entryEventForm.$valid && !$scope.entryDateRequired && !$scope.birthDateRequired)
			{
				$scope.clearAllMessages();
				
				//var birthDate = document.getElementById("birthDate").value;							
				var dateVal = new Date($scope.pigInfo.birthDate);
				
				var birthDate = DateUtils.convertLocaleDateToServer(dateVal);				
				
				var entrydateVal = new Date($scope.pigInfo.entryDate);
				var entryDate = DateUtils.convertLocaleDateToServer(entrydateVal);
				
				var duration  = 100;
				
				if(entryDate != null && birthDate != null)
					{
					duration = Math.round((entryDate-birthDate)/(1000*60*60*24));					
					}
				
				
				if(birthDate != null && birthDate >  entryDate)
				{
				   $scope.invalidEntryDate = true;	
				}
				else if(duration < 100 || duration > 300)
				{
					$scope.invalidDateDuration = true;
				}
					
				else
					{
						$scope.pigInfo["birthDate"] = birthDate;
						$scope.pigInfo["entryDate"] = entryDate;
						$scope.pigInfo["companyId"] = $scope.companyId;
						restServices.saveEntryEventInformation($scope.pigInfo, function(data){
							if(!data.error)
								{
									$scope.clearAllMessages();
									$scope.entryEventSuccessMessage = true;
									$scope.pigInfo = {};
									$scope.changeText();
									
								}
							else
								{
									$scope.clearAllMessages();
									if(data.duplicateRecord)
										$scope.entryEventDuplicateErrorMessage = true;
									else
										$scope.entryEventErrorMessage = true;
								}
							$window.scrollTo(0, 0);
						});
					}
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
							searchOption : option,
							companyId : $scope.companyId
					};
					restServices.getPigInformation(searchPigInfo, function(data)
					{
						if(!data.error){
							$scope.clearAllMessages();
							$scope.pigInfo = data.payload;			
							if($scope.pigInfo.birthDate != null)
								$scope.pigInfo.birthDate = new Date($scope.pigInfo.birthDate);
							//document.getElementById("birthDate").value = $scope.pigInfo.birthDate;
							if($scope.pigInfo.entryDate != null)
								$scope.pigInfo.entryDate = new Date($scope.pigInfo.entryDate);
							//document.getElementById("entryDate").value = $scope.pigInfo.entryDate;
							
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
				if(!data.error)
					{
					   var status = data.statusMessage;
					   if(status == "Success")
						   {
						   	$scope.clearAllMessages();
							$scope.entryEventDeleteMessage = true;
							$scope.pigInfo = {};
						   }
					   		else if(status == "Events")
						   {
					   			$scope.clearAllMessages();
					   			$scope.pigInfoEventsExistsMessage = true;
						   }
					}	
				
				$window.scrollTo(0, 0);
			});					
				
		};
		
		$scope.resetForm = function()
		{
			$scope.clearAllMessages();
			$scope.pigInfo = {};
			$scope.changeText();
		}
		
		
		$scope.getAvailablePigIds = function()
		{
			restServices.getAvailablePigIds({'companyId': $scope.companyId},function(data) {
				if(!data.error)
				{
					$scope.availablePigIdList = data.payload;
				}				
			});
		}
		
		$scope.selectAvailablePigId = function(selectedVal)
		{
			$scope.pigInfo.pigId = $scope.object.selectedAvailablePigID;
			if($scope.copyPigDetails)
			{
				$scope.pigInfo = {};
				var searchPigInfo = {
						searchText : $scope.object.selectedAvailablePigID,
						searchOption : "pigId",
						companyId : $scope.companyId
				};
				restServices.getInactivePigInformation(searchPigInfo, function(data){
					if(!data.error){
						var pigDetails = data.payload;
						$scope.pigInfo = pigDetails;
						$scope.pigInfo["id"] = null;
					}
				});
		    }
		}
		
});


$(document).ready(function () {
	$('input[class="icheck"]').on('ifClicked', function (event) {
		angular.element("#EntryEventControllerId").scope().clearAllMessages();
		angular.element("#EntryEventControllerId").scope().$apply();
	});		
});