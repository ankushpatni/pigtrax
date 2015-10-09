var pregnancyEventController = pigTrax.controller('FarrowEventController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.farrowEvent = {};
	$scope.confirmClick = false;
	$scope.farrowEventList = [];
	
	$scope.clearAllMessages = function()
	{
		$scope.searchDataErrorMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventDeleteMessage = false;
		$scope.searchErrorMessage = false;
		$scope.inValidPigIdFromServer = false;
		$scope.confirmClick = false;
		$scope.requiredPigIdMessage = false;
		$scope.inValidServiceIdFromServer = false;
		$scope.farrowEventValidation_ErrCode_1 = false;
		$scope.malePigIdentified = false;
		$scope.invalidFarrowValue = false;
		$scope.invalidFarrowCount = false;
		$scope.farrowDateRequired = false;
		$scope.pregnancyEventRequired = false;
		$scope.farrowEventValidation_ErrCode_2 = false;
		$scope.birthTypeRequired = false;
		$scope.farrowIdRequired = false;
		$scope.invalidFarrowId = false;
		$scope.entryEventDuplicateErrorMessage = false;
		$scope.pigletInformationRequired = false;
	};
	
	$scope.loadPage = function(companyId)
	{
		$scope.setCompanyId(companyId);		
		$scope.getPenList();		
	};
	
	
	$scope.getPenList = function(){
		restServices.getPenListForCompany($rootScope.companyId, function(data){
			 if(!data.error)
			 {
				 $scope.penInfo = data.payload;
			 }
		});
	};
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		
	};
	
	/**
	 * check the validity of pigId
	 */
	$scope.checkForPigId = function()
	{
		if($scope.farrowEvent.pigId != undefined && $scope.farrowEvent.pigId != "")
			{
			    var pigInfo = {
						searchText : $scope.farrowEvent.pigId,
						searchOption : "pigId",
						companyId : $rootScope.companyId
				};
				restServices.getPigInformation(pigInfo, function(data) {
					if(data.error)
					{
						$scope.clearAllMessages();
						$scope.inValidPigIdFromServer = true;				
					}
					else
					{
						$scope.inValidPigIdFromServer = false;
						$scope.requiredPigIdMessage = false;
						var pigInfo = data.payload;
						if(pigInfo.sexTypeId != 2)
						{
							$scope.clearAllMessages();
							$scope.malePigIdentified = true;
						}
					}
						
				});
			}
	};
	
	
	
		
	
	/**
	 * To add a farrow event
	 */
	$scope.addFarrowEvent = function()
    {	
		$scope.farrowEvent["inducedBirth"] = false;
		$scope.farrowEvent["assistedBirth"] = false;
		var farrowDate = document.getElementById("farrowDate").value;
		$scope.farrowEvent["farrowDateTime"] = farrowDate;
		var pattern = /^[a-z0-9]+$/i;
		var birthType = "";
		if(document.getElementById("birthType1").checked)
			 birthType = document.getElementById("birthType1").value;
		else if(document.getElementById("birthType2").checked)
			 birthType = document.getElementById("birthType2").value;
		
		
		
		var liveBorns = $scope.farrowEvent.liveBorns;
		if(liveBorns == null || liveBorns == undefined)
		{
			liveBorns = 0;
			$scope.farrowEvent.liveBorns = liveBorns
		}
		var maleBorns = $scope.farrowEvent.maleBorns;
		if(maleBorns == null || maleBorns == undefined)
		{
			maleBorns = 0;
			$scope.farrowEvent.maleBorns = maleBorns;
		}
		var femaleBorns = $scope.farrowEvent.femaleBorns;
		if(femaleBorns == null || femaleBorns == undefined)
		{
			femaleBorns = 0;
			$scope.farrowEvent.femaleBorns = femaleBorns;
		}
		var stillBorns = $scope.farrowEvent.stillBorns;
		if(stillBorns == null || stillBorns == undefined)
		{
			stillBorns = 0;
			$scope.farrowEvent.stillBorns = stillBorns;
		}
		var mummies = $scope.farrowEvent.mummies;
		if(mummies == null || mummies == undefined)
		{
			mummies = 0;
			$scope.farrowEvent.mummies = mummies;
		}
		
		/*if($scope.farrowEvent["farrowId"] != null && $scope.farrowEvent["farrowId"] != undefined && $scope.farrowEvent["farrowId"] != "")
		{	
			
			if(!pattern.test($scope.farrowEvent["farrowId"]))
				{
				$scope.farrowIdRequired = false;			
				$scope.invalidFarrowId = true;
				
				}
			else
				{
				$scope.farrowIdRequired = false;	
				$scope.invalidFarrowId = false;
				}
			
		}
		else
		{
			$scope.farrowIdRequired = true;
			$scope.invalidFarrowId = false;
			
		}*/
		
		if($scope.farrowEvent["farrowDateTime"] == null || $scope.farrowEvent["farrowDateTime"] == undefined || $scope.farrowEvent["farrowDateTime"] == "")
		{	
			
			$scope.farrowDateRequired = true;
			
		}	
		else
		{
			$scope.farrowDateRequired = false;
		}
		
		 if($scope.farrowEvent["pregnancyEventId"] == null || $scope.farrowEvent["pregnancyEventId"] == undefined || $scope.farrowEvent["pregnancyEventId"] == "")
		{
			 
			  $scope.pregnancyEventRequired = true;
		}
		 else
			 {
			 $scope.pregnancyEventRequired = false;
			 }
		 
		 	if(birthType != null && birthType == "induced")
			{
				$scope.farrowEvent["inducedBirth"]=true;
				$scope.farrowEvent["assistedBirth"]=false;
				
			}
			else if(birthType != null && birthType == "assisted")
			{
				$scope.farrowEvent["inducedBirth"]=false;
				$scope.farrowEvent["assistedBirth"]=true;
				
			}
			
			if($scope.farrowEvent["inducedBirth"] == false 
					&& $scope.farrowEvent["assistedBirth"] == false)
			{
				
				$scope.birthTypeRequired = true;
				
				
			}
			else
			{
				
				$scope.birthTypeRequired = false;
				
			}
		 
		if(parseInt(liveBorns)!= liveBorns || parseInt(maleBorns) != maleBorns 
				|| parseInt(femaleBorns) != femaleBorns || parseInt(stillBorns) != stillBorns || parseInt(mummies) != mummies )
		{
			$scope.invalidFarrowValue = true;
			$scope.invalidFarrowCount = false; 
			$scope.pigletInformationRequired = false;
			
		}	
		/*else if(liveBorns != (eval(maleBorns)+eval(femaleBorns)))
		{
			$scope.invalidFarrowValue = false;
			$scope.invalidFarrowCount = true; 
			$scope.pigletInformationRequired = false;
		}*/
		else if (liveBorns == 0 && maleBorns == 0 && femaleBorns == 0 && stillBorns == 0 && mummies == 0)
		{
			$scope.invalidFarrowValue = false;
			$scope.invalidFarrowCount = false;
			$scope.pigletInformationRequired = true;
		}
		else
        {
			$scope.invalidFarrowValue = false;
			$scope.invalidFarrowCount = false; 
			$scope.pigletInformationRequired = false;
        }
		
			if($scope.farroweventform.$valid && !$scope.birthTypeRequired && !$scope.pregnancyEventRequired && !$scope.farrowDateRequired 
					 && !$scope.invalidFarrowCount && !$scope.invalidFarrowValue && !$scope.pigletInformationRequired)
			{
						
				$scope.farrowEvent["companyId"] = $rootScope.companyId;
				
				var farrowDateVal = new Date(farrowDate);
				farrowDate = DateUtils.convertLocaleDateToServer(farrowDateVal);
				
				$scope.farrowEvent["farrowDateTime"] = farrowDate;
				
				
				restServices.validateFarrowEvent($scope.farrowEvent, function(data){
					if(!data.error)
					{
					   $scope.clearAllMessages();
					   var statusCode = data.payload;
					   //alert("statusCode = "+statusCode);
					   if(statusCode == 0)
					   {
							//alert(JSON.stringify($scope.farrowEvent));
							restServices.saveFarrowEventInformation($scope.farrowEvent, function(data){
								if(!data.error)
									{ 
										$('#birthType1').iCheck('uncheck');
										$('#birthType2').iCheck('uncheck');		
										$scope.clearAllMessages();
										$scope.entryEventSuccessMessage = true;
										$scope.farrowEvent = {};																		
									}
								else
									{
										$scope.clearAllMessages();
										if(data.duplicateRecord)
											$scope.entryEventDuplicateErrorMessage = true;
										else
											$scope.entryEventErrorMessage = true;
									}
									$window.scrollTo(0, 5);  
							});
					   }
					   else if(statusCode == 1)
						   $scope.farrowEventValidation_ErrCode_1 = true;
					   else if(statusCode == 2)
						   $scope.farrowEventValidation_ErrCode_2 = true;
					   $window.scrollTo(0, 5);  
					}
				});
				
				
			}
		
		
		
    };
    
    
    /**
     * Search for the pregnancy events
     */
    $scope.getFarrowEventInformation = function()
	{		
    	var option = "";
		if(document.getElementById("rad1").checked)
			 option = document.getElementById("rad1").value;
		 else if(document.getElementById("rad2").checked)
			 option = document.getElementById("rad2").value;
    	
		if($scope.searchText == undefined || $scope.searchText == "" || option == "")
		{
			   $scope.clearAllMessages();
			   $scope.searchErrorMessage = true;
		}
		else
		{
			var searchFarrowEvent = {
					searchText : $scope.searchText,
					searchOption : option,
					pigId : $scope.searchText, 
					companyId : $scope.companyId
					
			};				
			restServices.getFarrowEventInformation(searchFarrowEvent, function(data){
				$scope.clearAllMessages();
				if(!data.error){
					$scope.farrowEvent = {};
					$scope.farrowEventList = data.payload;						
				}
				else
				{
					$scope.farrowEvent = {};
					$scope.clearAllMessages(); 
					$scope.searchDataErrorMessage = true;
					$scope.farrowEventList = [];
					
				}
			});
		}
	};
	
	
	$scope.getFarrowEventDetails = function(farrowEventObj)
	{
		//alert("came here : "+JSON.stringify(pregnancyEventObj));
		$scope.farrowEvent = farrowEventObj;
		$scope.farrowEvent.pregnancyEventType = $scope.farrowEvent.pregnancyEventDto.pregnancyEventType + " ["+$scope.farrowEvent.pregnancyEventDto.resultDate+"]";
		if($scope.farrowEvent["inducedBirth"])
			$('#birthType1').iCheck('check');
		else if($scope.farrowEvent["assistedBirth"])
			$('#birthType2').iCheck('check');
	}
	
	/**
	 * Delete a pregnancy event by id
	 */
	$scope.deleteFarrowEvent = function()
	{
		restServices.deleteFarrowEvent($scope.farrowEvent.id, function(data){
			if(!data.error)
				{
				$scope.clearAllMessages();
				$scope.entryEventDeleteMessage = true;
				$scope.farrowEvent = {};
				$('#birthType1').iCheck('uncheck');
				$('#birthType2').iCheck('uncheck');
				//$scope.getFarrowEventInformation();
				$scope.updateSearchResults();
				$window.scrollTo(0, 5);
				}
		});
	}
	
	
	$scope.updateSearchResults = function(id)
	{
		var farrowEvents = $scope.farrowEventList;
		for (var i = 0; i < farrowEvents.length; i++) { 
			if(farrowEvents[i].id == id)
				$scope.farrowEventList.splice(i, 1); 
		}
	}
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.farrowEvent = {};
		$scope.changeText();
	} 
	
	
	$scope.searchPregnancyService = function(pigId, selectedCompanyId)
	{
		if(pigId == undefined  || pigId == "")
		{			
			$scope.requiredPigIdMessage = true;
			$('#searchPregnancyService').modal('hide');
		}
		else{
		
			var searchPregnancyEvent = {
					searchText : pigId,
					searchOption : "pigId",
					companyId : $rootScope.companyId,
					pregnancyEventType : "Pregnancy Event"
			};
			
			restServices.getPregnancyEventInformation(searchPregnancyEvent, function(data){
				$scope.clearAllMessages();
				 $("#searchPregnancyService").modal("show");
				if(!data.error){
					$scope.pregnancyEventList = data.payload;
				} 
				else
				{
					$scope.pregnancyEventList = [];  
				}
			}); 
		}
	}; 
	
	$scope.selectPergnancyEventService = function()
	{
		$scope.farrowEvent.pregnancyEventType = $scope.farrowEvent.pregnancyEventDto.pregnancyEventType + " ["+$scope.farrowEvent.pregnancyEventDto.resultDate+"]";
		$scope.farrowEvent.pregnancyEventId = $scope.farrowEvent.pregnancyEventDto.id;
		$('#searchPregnancyService').modal('hide');
	}
	
});


$(document).ready(function () { 
	$('input[class="icheck farrowevent"]').on('ifClicked', function (event) {
		angular.element("#FarrowEventControllerId").scope().clearAllMessages();
		angular.element("#FarrowEventControllerId").scope().$apply();
	});		
});