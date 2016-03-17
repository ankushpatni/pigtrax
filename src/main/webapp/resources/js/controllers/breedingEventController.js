var breedingEventController = pigTrax.controller('BreedingEventController', function($scope,$rootScope, $http,$window,restServices, DateUtils) {
	
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$rootScope.selectedEmployeeGroup = {};
	$scope.breedingEvent = {};
	$scope.confirmClick = false;
	$scope.DateUtils = DateUtils;
	
	$scope.clearAllMessages = function()
	{
		$scope.searchDataErrorMessage = false;
		$scope.entryEventErrorMessage = false;
		$scope.entryEventSuccessMessage = false;
		$scope.entryEventDeleteMessage = false;
		$scope.searchErrorMessage = false;
		$scope.inValidPigIdFromServer = false;
		$scope.breedingEventValidation_Success = false;
		$scope.breedingEventValidation_WarnCode_1 = false;
		$scope.breedingEventValidation_WarnCode_2 = false;
		$scope.breedingEventValidation_ErrCode_1 = false;
		$scope.breedingEventValidation_ErrCode_2 = false;
		$scope.breedingEventValidation_ErrCode_3 = false;
		$scope.breedingEventValidation_ErrCode_BirthDate = false;
		$scope.breedingEventValidation_ErrCode_EntryDate = false;
		$scope.entryEventDuplicateErrorMessage = false;
		$scope.confirmClick = false;
		$scope.malePigIdentified = false;
		$scope.AddMatingDetailsForm = false;
		$scope.matingDetailsDeleteErrorMessage = false;
		$scope.entryEventDeleteErrorMessage = false;
		$scope.breedingEventValidation_ErrCode_DuplicateMatingDate = false;
		$scope.breedingEventValidation_ErrCode_NextEvent = false;
		$scope.breedingEventIncompleteCycle = false;
		$scope.matingDetailsSuccessMessage = false;
		$scope.breedingEventValidation_ErrCode_PregCheckAdded = false;
		$scope.matingDateRequired = false;
	};
	
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		   //var  dateObj = Date.parse(dateVal);	
		   var  dateObj = DateUtils.parse(dateVal,"dd/MM/yyyy");
		   if(dateObj == null)
			{
			   if(fieldName == "matingDate")
				{
					   $scope.matingDateRequired = true;
					   $scope.matingDetails["matingDate"] = null;
				}
			   else if(fieldName == "semenDate")
			   {
				   $scope.matingDetails["semenDate"] = null;
			   }   
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "matingDate")
				{
				   $scope.matingDateRequired = false;
				   $scope.matingDetails["matingDate"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   else if(fieldName == "semenDate")
				{
				   $scope.matingDetails["semenDate"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			}
		}
		else
		{
			if(fieldName == "matingDate")
			{
				   $scope.matingDateRequired = true;
				   $scope.matingDetails["matingDate"] = null;
			}
		   else if(fieldName == "semenDate")
		   {
			   $scope.matingDetails["semenDate"] = null;
		   }   
		}
	  }
	}
	
	
	
	$scope.$watch("selectedEmployeeGroup", function(newValue, oldValue) {
		
		if (newValue != null && newValue != undefined) {
			$scope.selectedEmployeeGroup = newValue;
			
		}
	});
	
	
	$scope.loadPremises = function()
	{
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId+'&premisesType=1,6,8');
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.setCompanyId = function(companyId, selectedBreedingEventId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		$scope.getPenList();
		if(selectedBreedingEventId != null && selectedBreedingEventId != undefined && selectedBreedingEventId != "")
		{
			restServices.getBreedingEventDetails(selectedBreedingEventId, function(data){
				  if(!data.error)
				  {
				    $scope.clearAllMessages();
				    $scope.breedingEvent = data.payload;
				  }
			  });
		}
		$scope.loadPremises();
		//$scope.setupFormElements();
	};
		
	$scope.getPenList = function(){
		restServices.getPenListForCompany($rootScope.companyId, function(data){
			 if(!data.error)
			 {
				 $scope.penInfo = data.payload;
			 }
		});
	};
	
	$scope.getBreedingServiceType = function()
	{
		restServices.getBreedingServiceType(function(data){
			if(!data.error){
				$scope.breedingServiceTypes = data.payload;
			}
		});
	}
	$scope.getBreedingServiceType();
	
	 
	$scope.getBreedingEventDetails = function()
	{
		
		if($scope.breedingEvent["id"] != null)
			{
			  restServices.getBreedingEventDetails($scope.breedingEvent["id"], function(data){
				  if(!data.error)
					  {
					    $scope.clearAllMessages();
					    $scope.breedingEvent = data.payload;
					  }
			  });
			}
	}
	
	
	$scope.getBreedingEventInformation = function()
	{
		var option = "";
		if(document.getElementById("rad1").checked)
			 option = document.getElementById("rad1").value;
		 else if(document.getElementById("rad2").checked)
			 option = document.getElementById("rad2").value;
		
		if($scope.searchText == undefined || $scope.searchText == ""|| option == "" || $scope.selectedPremise == null || $scope.selectedPremise == "")
		{
			   $scope.clearAllMessages();
			   $scope.searchErrorMessage = true;
		}
		else
			{
				var searchBreedEvent = {
						searchText : $scope.searchText,
						searchOption : option,
						companyId : $scope.companyId,
						selectedPremise : $scope.selectedPremise
						
				};
				restServices.getBreedingEventInformation(searchBreedEvent, function(data){
					$scope.clearAllMessages();
					if(!data.error){
						$scope.breedingEventList = data.payload;
					}
					else
					{
						$scope.breedingEventList = [];
						$scope.breedingEvent = {};						
						$scope.clearAllMessages();
						$scope.searchDataErrorMessage = true;
						$scope.AddMatingDetailsForm = false;
					}
				});
			}
	};
	
	
	$scope.confirmAddBreedingEvent = function()
	{
		if($scope.breedingeventform.$valid)
		{
			
			$scope.checkForPigId();
			
			if(!$scope.inValidPigIdFromServer)
				{
					$scope.breedingEvent["companyId"] = $rootScope.companyId;
					//alert(JSON.stringify($scope.breedingEvent)); 
					restServices.saveBreedingEventInformation($scope.breedingEvent, function(data){
						if(!data.error)
							{
								$scope.clearAllMessages();
								$scope.breedingEvent = data.payload;
								 restServices.getBreedingEventDetails($scope.breedingEvent["id"], function(data){
									  if(!data.error)
										  {
										    $scope.clearAllMessages();
										    $scope.breedingEvent = data.payload;
										    $scope.entryEventSuccessMessage = true;	
										  }
								  });
								
							}
						else
							{
								$scope.clearAllMessages();
								if(data.duplicateRecord)
									$scope.entryEventDuplicateErrorMessage = true;
								else
								{
									var statusMessage = data.statusMessage;
									if(statusMessage.indexOf("INCOMPLETE_SERVICE_CYCLE") != -1)
										{
										$scope.clearAllMessages();
										$scope.breedingEventIncompleteCycle = true;
										}
									else
										{
										$scope.clearAllMessages();
										$scope.entryEventErrorMessage = true;
										}
								}
							}
							$window.scrollTo(0, 5);
					});
				}
			else
				$scope.inValidPigIdFromServer = true;
		}
	}
	
	
	$scope.confirmAddMatingDetails = function()
	{
		//var matingDate = document.getElementById("matingDate").value;
		//var matingDateVal = new Date(matingDate);
		//matingDate = DateUtils.convertLocaleDateToServer(matingDateVal);
		
		$scope.matingDetails["breedingEventId"] = $scope.breedingEvent["id"];
		//$scope.matingDetails["matingDate"] =matingDate;
		
		//alert(JSON.stringify($scope.breedingEvent)); 
		var matingDate = new Date($scope.matingDetails["matingDate"]);
		$scope.matingDetails["matingDate"] = DateUtils.convertLocaleDateToServer(matingDate);
		
		if($scope.matingDetails["semenDate"] != null)
		{
			var semenDate = new Date($scope.matingDetails["semenDate"]);
			$scope.matingDetails["semenDate"] = DateUtils.convertLocaleDateToServer(semenDate);
		}
		restServices.saveMatingDetails($scope.matingDetails, function(data){
			if(!data.error)
				{
					$scope.clearAllMessages();
					if($scope.breedingEvent["id"] != null)
					{
					  restServices.getBreedingEventDetails($scope.breedingEvent["id"], function(data){
						  if(!data.error)
							  {
							    $scope.clearAllMessages();
							    $scope.breedingEvent = data.payload;							    
							    $scope.matingDetailsSuccessMessage = true;		
							    $scope.AddMatingDetailsForm = false;
							  }
					  });
					}
					
				}
			else
				{
					$scope.clearAllMessages();
					if(data.duplicateRecord)
						$scope.entryEventDuplicateErrorMessage = true;
					else
						$scope.entryEventErrorMessage = true;
					
					$scope.AddMatingDetailsForm = true;
				}
				$window.scrollTo(0, 5);
		});
	}
	
	
	$scope.saveMatingDetails = function()
	{	
		$scope.matingDateRequired = false;
		
		//var matingDate = document.getElementById("matingDate").value;
		
		if($scope.matingDetails["matingDate"] == null || $scope.matingDetails["matingDate"] == undefined || $scope.matingDetails["matingDate"] == "")
		{
			$scope.matingDateRequired = true;
		}	
		
		if($scope.matingdetailsform.$valid && !$scope.matingDateRequired)
		{
			if($scope.confirmClick)
			{
				$scope.confirmAddMatingDetails();
			}
			else
			{	
				//var matingDate = document.getElementById("matingDate").value;
				//var matingDateVal = new Date(matingDate);
				//matingDate = DateUtils.convertLocaleDateToServer(matingDateVal);
				
				$scope.matingDetails["breedingEventId"] = $scope.breedingEvent["id"];
				//$scope.matingDetails["matingDate"] = matingDate;
				
				restServices.validateMatingDetails($scope.matingDetails, function(data){
			   		if(!data.error)
				   {
			   			var statusCode = data.payload;
					     $scope.clearAllMessages();
					     if(statusCode == "SUCCESS-00")
					     {
					    	     $scope.confirmAddMatingDetails();
					    }
					     else
					    {
					    	 if(statusCode == "ERR_BIRTHDATE_NOT_MATCHING")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_BirthDate = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 else if(statusCode == "ERR_ENTRYDATE_NOT_MATCHING")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_EntryDate = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 else if(statusCode == "ERR_CODE_DUPLICATE_DATE")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_DuplicateMatingDate = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 else if(statusCode == "ERR_CODE_PREG_CHECK_ADDED")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_PregCheckAdded = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 else if(statusCode == "WARN-01")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_WarnCode_1 = true;
						    	 $scope.confirmClick = true;						    	 
					    	 }
						     else if(statusCode == "WARN-02")
						     {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_WarnCode_2 = true;
						    	 $scope.confirmClick = true;
						     }
						     else if(statusCode == "ERR-01")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_1 = true;
						    	 $scope.confirmClick = false;	
						    	 }
						     else if(statusCode == "ERR-02")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_2 = true;
						    	 $scope.confirmClick = false;
						    	 }
						     else if(statusCode == "ERR-03")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_3 = true;
						    	 $scope.confirmClick = false;
						    	 }
						     else if(statusCode == "ERR-04")
					    	 {
					    	 $scope.clearAllMessages();
					    	 $scope.breedingEventValidation_ErrCode_4 = true;
					    	 $scope.confirmClick = false;
					    	 }
						     else if(statusCode == "ERR_GENERAL")
					    	 {
						    	 $scope.clearAllMessages();
					    	 $scope.entryEventErrorMessage = true;
					    	 $scope.confirmClick = false;
					    	 }
						     else if(statusCode == "ERR_CODE_NEXT_EVENT")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_NextEvent = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 
					    	 $scope.AddMatingDetailsForm = true;
					    //	 $scope.setupFormElements(); 
					    	$window.scrollTo(0, 5);
					   	 }
				   }
				});
			
			 }
		}
	}
	
	
	
	$scope.addBreedingEvent = function(){
		$scope.confirmClick = true;
		if($scope.breedingeventform.$valid)
		{
			if($scope.confirmClick)
				{
				$scope.confirmAddBreedingEvent();
				} 
			else
			{
				
				$scope.breedingEvent["companyId"] = $rootScope.companyId;				
				restServices.validateBreedingEvent($scope.breedingEvent, function(data){
			   		if(!data.error)
				   {
			   			var statusCode = data.payload;
					     $scope.clearAllMessages();
					     if(statusCode == "SUCCESS-00")
					     {
					    	   $scope.breedingEvent["gestationRecord"] = true; 
					    	   $scope.confirmAddBreedingEvent();
					    }
					     else
					    {
					    	 if(statusCode == "ERR_BIRTHDATE_NOT_MATCHING")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_BirthDate = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 if(statusCode == "ERR_ENTRYDATE_NOT_MATCHING")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_EntryDate = true;
						    	 $scope.confirmClick = false;
					    	 }
					    	 else if(statusCode == "WARN-01")
					    	 {
					    		 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_WarnCode_1 = true;
						    	 $scope.breedingEvent["gestationRecord"] = false; 
						    	 $scope.confirmClick = true;						    	 
					    	 }
						     else if(statusCode == "WARN-02")
						     {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_WarnCode_2 = true;
						    	 $scope.breedingEvent["gestationRecord"] = true; 
						    	 $scope.confirmClick = true;
						     }
						     else if(statusCode == "ERR-01")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_1 = true;
						    	 $scope.confirmClick = false;	
						    	 }
						     else if(statusCode == "ERR-02")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_2 = true;
						    	 $scope.confirmClick = false;
						    	 }
						     else if(statusCode == "ERR-03")
						    	 {
						    	 $scope.clearAllMessages();
						    	 $scope.breedingEventValidation_ErrCode_3 = true;
						    	 $scope.confirmClick = false;
						    	 }
						     else if(statusCode == "ERR-04")
					    	 {
					    	 $scope.clearAllMessages();
					    	 $scope.breedingEventValidation_ErrCode_4 = true;
					    	 $scope.confirmClick = false;
					    	 }
						     else if(statusCode == "ERR_GENERAL")
					    	 {
						    	 $scope.clearAllMessages();
					    	 $scope.entryEventErrorMessage = true;
					    	 $scope.confirmClick = false;
					    	 }
					    	 
					    //	 $scope.setupFormElements(); 
					    	$window.scrollTo(0, 5);
					   	 }
				   }
				});
			
			 }
		}
	};
	
	
	
	$scope.getEmployeeGroups = function()
	{
		restServices.getEmployeeGroups($scope.companyId, function(data) {
			if(!data.error)
				{
				   $scope.employeeGroups = data.payload;
				}
		});
	};
	
	$scope.deleteBreedingEventInfo = function()
	{	
		restServices.deleteBreedingEventInfo($scope.breedingEvent.id, function(data){
			
			if(data.error)
			{
				if(data.statusMessage == "ERROR : PREGCHECK-TRUE")
				{
					$scope.AddMatingDetailsForm = false;
					$scope.clearAllMessages();
					$scope.entryEventDeleteErrorMessage = true; 
				}
			}
			else
			{
				$scope.clearAllMessages();
				$scope.entryEventDeleteMessage = true;
				$scope.breedingEvent = {};			
				$scope.getBreedingEventInformation();				
			}
			$window.scrollTo(0, 5);
			
		});
			
	};
	
	$scope.deleteMatingDetails = function(matingDetailsObj)
	{	
		restServices.deleteMatingDetails(matingDetailsObj, function(data){
			if(!data.error)
				{
					$scope.clearAllMessages();
					if($scope.breedingEvent["id"] != null)
					{
					  restServices.getBreedingEventDetails($scope.breedingEvent["id"], function(data){
						  if(!data.error)
							  {
							    $scope.clearAllMessages();
							    $scope.breedingEvent = data.payload;
							    $scope.entryEventDeleteMessage = true; 
								$scope.AddMatingDetailsForm = false;	
							  }
					  });
					}
					
				}
			else
				{
					if(data.statusMessage == "ERROR : PREGCHECK-TRUE")
					{
						$scope.AddMatingDetailsForm = false;
						$scope.clearAllMessages();
						$scope.matingDetailsDeleteErrorMessage = true; 
					}
				}
			
			$window.scrollTo(0, 5);
		});
			
	};
	
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.breedingEvent = {};
		$scope.changeText();
		//$scope.setupFormElements();
	}
	
	$scope.viewEmployeeGroup = function()
	{
		$rootScope.viewAddForm = false;
		
	};
	
	/**
	 * check the validity of pigId
	 */
	$scope.checkForPigId = function()
	{
		if($scope.breedingEvent["premiseId"] != null && $scope.breedingEvent["premiseId"] != "" && $scope.breedingEvent["pigInfoId"] != null && $scope.breedingEvent["pigInfoId"] != "")
			{
			    var pigInfo = {
						searchText : $scope.breedingEvent.pigInfoId,
						searchOption : "pigId",
						companyId : $rootScope.companyId,
						selectedPremise : $scope.breedingEvent.premiseId
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
						var pigInfo = data.payload;
						if(pigInfo.sexTypeId == 2)
							{
								$scope.breedingEvent["pigBirthDate"] = pigInfo.birthDate;						
							}
						else
							{
								$scope.clearAllMessages();
								$scope.malePigIdentified = true;
							}
					}
						
				});
			}
	};
	
	
	$scope.validateBreedingDate = function()
	{
		var birthDate = $scope.breedingEvent.pigBirthDate;
		var breedingDate = $scope.breedingEvent.breedingDate;
		
	}
	
	$scope.getBreedingEventDetailsObj = function(breedingEventObj) 
	{
		$scope.AddMatingDetailsForm = false;
		$scope.breedingEvent = breedingEventObj;
	}
	
	$scope.addMatingDetailData = function()
	{
		$scope.clearAllMessages();
		$scope.AddMatingDetailsForm = true;
		$scope.matingDetails = {};
		$scope.matingDetails["breedingEventId"] = $scope.breedingEvent["id"];
	}
	
	
	$scope.editMatingDetails = function(matingDetailsObj)
	{	
		$scope.matingDetails = matingDetailsObj;		
		$scope.AddMatingDetailsForm = true;
	};  
	
}); 

$(document).ready(function () { 
	$('input[class="icheck breedingevent"]').on('ifClicked', function (event) {
		angular.element("#BreedingEventControllerId").scope().clearAllMessages();
		angular.element("#BreedingEventControllerId").scope().$apply();
	});
});