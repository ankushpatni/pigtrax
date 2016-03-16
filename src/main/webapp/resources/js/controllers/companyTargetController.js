pigTrax.service("CompanyTargetsService", function() {
    this.checkIfExists = function(companyTargets, newCompanyTarget) {
       var len = 0;
       var newTargetYear = 0;
       var newTargetDate = new Date(newCompanyTarget["completionDate"]);
       	newTargetYear = newTargetDate.getFullYear();
       var targetYear = 0;
       if(companyTargets != null)
    	    len = companyTargets.length;
       if(len > 0)
    	   {
    	     for(i = 0; i<len; i++)
    	    	 {
    	    	   
    	    	   item = companyTargets[i];
    	    	   var targetDate = new Date(item["completionDate"]);
    	    	   targetYear = targetDate.getFullYear();
    	    	   if(newCompanyTarget["id"] == null && item["targetId"] == newCompanyTarget["targetId"] && targetYear == newTargetYear)
    	    	   		return true;
    	    	   else if(newCompanyTarget["id"] != null && newCompanyTarget["id"] != item["id"] && item["targetId"] == newCompanyTarget["targetId"] && targetYear == newTargetYear)
    	    		   return true;
    	    	 }
    	   }
       return false;
      };
});



pigTrax.controller('CompanyTargetController', function($scope,$rootScope, $http,$window,restServices, DateUtils, CompanyTargetsService) {
	$scope.companyId = "";
	$scope.companyTarget = {};
	$scope.DateUtils = DateUtils;
	$scope.ShowRationOption = false;
	$scope.usedTargetKeys = null;
	$scope.limitedTargetKeys = null;
	
	
	$scope.setCompanyId = function(companyId)
	{
		$rootScope.companyId = companyId;
		$scope.getCompanyTargets();
		$scope.getTargetTypes();
		$scope.loadPremises();
		$scope.getRationIdList();
	};
	
	$scope.getTargetTypes = function()
	{
		restServices.getTargetTypes(function(data){
			if(!data.error)
				{
				  var responseList = data.payload;
				  $scope.keys = responseList[0];
				  $scope.limitedTargetKeys = responseList[1];
				  $scope.targetTypes = responseList[2];
				  $scope.usedTargetKeys =$scope.keys ;
				}
		});
	}
	
	
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
	
	
	$scope.getRationIdList = function()
	{
		restServices.getRationIdList(function(data) {
			if(!data.error)
				{
					var responseList = data.payload;
					$scope.rationListKeys = responseList[0];
					$scope.rationListKeyValues =responseList[1];
				
				}
		});
	};
	
	$scope.checkTargetType = function()
	{
		if($scope.companyTarget.targetId == 91 || $scope.companyTarget.targetId == 92 || $scope.companyTarget.targetId == 93)
	        $scope.ShowRationOption = true;
		else
			{
			$scope.ShowRationOption = false;
			$scope.companyTarget.rationId = null;
			}
	}
	
	
	$scope.checkPremise = function()
	{
		var premiseId = $scope.companyTarget["premiseId"];
		var premiseObj = null;
		for(i =0 ; i<$scope.premiseList.length; i++)
		{
			premiseObj = $scope.premiseList[i];		  
			if(premiseObj["id"] == $scope.companyTarget["premiseId"])
				break;		   
		}
		 if(premiseObj["sowSource"] != "Yes")
		  {
			  $scope.usedTargetKeys = $scope.limitedTargetKeys;
		  }
		  else
		  {
			  $scope.usedTargetKeys = $scope.keys ;
		  }			
	}
	
	
	
	
	$scope.getCompanyTargets = function()
	{
		var companyTargetDto = {"companyId" : $rootScope.companyId};
		restServices.getCompanyTargets(companyTargetDto, function(data){
			if(!data.error)
			{
				  $scope.companyTargets = data.payload;
			}
		})
	};
	
	
	$scope.clearAllMessages = function()
	{
		$scope.companyTargetSaved = false;
		$scope.duplicateCompanyTarget = false;
		$scope.companyTargetDeleted = false;
		$scope.targetIdRequired = false;
		$scope.targetValueRequired = false;
		$scope.completionDateRequired = false;		
	}
	
	
	
	$scope.dateCheck = function(dateVal, fieldName)
	{			
	  if(dateVal != null && dateVal.length > 0) 
	  {
		if(dateVal.length == 10)
		{
		  // var  dateObj = Date.parse(dateVal);
		   var  dateObj = DateUtils.parse(dateVal,"dd/MM/yyyy");
		   if(dateObj == null)
			{
			   if(fieldName == "completionDate")
				{
					   $scope.completionDateRequired = true;
					   $scope.companyTarget["completionDate"] = null;
				}
			   
			}
		   else
			{
			   $scope.dateError = false;
			   if(fieldName == "completionDate")
				{
				   $scope.completionDateRequired = false;
				   $scope.companyTarget["completionDate"] = DateUtils.convertLocaleDateToServer(dateObj);
				}
			   
			}
		}
		else
		{
			if(fieldName == "completionDate")
			{
				   $scope.completionDateRequired = true;
				   $scope.companyTarget["completionDate"] = null;
			}		  
		}
	  }
	}
	
	
	$scope.saveCompanyTarget = function()
	{
		valid = true;
		//var completionDate = document.getElementById("completionDate").value;
		//$scope.companyTarget["completionDate"] = completionDate;
		
		if($scope.companyTarget["targetId"] == null)
		{
			$scope.targetIdRequired = true;
			valid = false;
		}
		else
		{
			$scope.targetIdRequired = false;			
		}
		if($scope.companyTarget["targetValue"] == null)
		{
			$scope.targetValueRequired = true;
			valid = false;
		}	
		else
		{
			$scope.targetValueRequired = false;			
		}
		if($scope.companyTarget["completionDate"] == null || $scope.companyTarget["completionDate"] == "")
		{
			$scope.completionDateRequired = true;
			valid = false;
		}
		else
		{
			$scope.completionDateRequired = false;			
		}		
		
		
		if(valid)
		{
			
		  var exists = CompanyTargetsService.checkIfExists($scope.companyTargets,$scope.companyTarget);
		  if(!exists)
			  {
			
		   //var completionDate = document.getElementById("completionDate").value;
		   //$scope.companyTarget["completionDate"] = completionDate;
		   $scope.companyTarget["companyId"] = $rootScope.companyId;
			
		   restServices.saveCompanyTarget($scope.companyTarget, function(data){
			   if(!data.error){
				   $scope.clearAllMessages();
				   $scope.companyTargetSaved = true;	
				   var resultObj = data.payload;
				   //$scope.modifyArray(resultObj);
				   $scope.getCompanyTargets();
				   $scope.companyTarget = {};
			   }
		   });	
			  }
		  else
			  {
			    $scope.clearAllMessages();
			    $scope.duplicateCompanyTarget = true;
			  }
			  
			  $window.scrollTo(0, 0);
		}
	};
	
	$scope.modifyArray = function(companyTargetObj)
	{
		var exists = false;
		
		for(i =0 ; i<$scope.companyTargets.length; i++)
		{
			  if($scope.companyTargets[i]["id"] == companyTargetObj["id"])
			  {
				  $scope.companyTargets[i]["targetValue"] = companyTargetObj["targetValue"];
				  $scope.companyTargets[i]["completionDate"] = companyTargetObj["completionDate"];
				  $scope.companyTargets[i]["remarks"] = companyTargetObj["remarks"];
				  $scope.companyTargets[i]["premiseId"] = companyTargetObj["premiseId"];
				  $scope.companyTargets[i]["rationId"] = companyTargetObj["rationId"];
				  exists = true;
				  break;
			  }
			  
		}
		if(!exists)
			$scope.companyTargets.push(companyTargetObj);
	}
	
	
	
	
	
	$scope.deleteTargetDetails = function(selectedObject)
	{
		restServices.deleteTargetDetails(selectedObject, function(data) {
			if(!data.error)
			{
				$scope.clearAllMessages();
				  $scope.companyTargets = data.payload;
				  $scope.companyTargetDeleted = true;
			}
			$window.scrollTo(0, 0);
		});
	}
	
	
	$scope.editCompanyTarget = function(selectedObject)
	{
		$scope.companyTarget["id"] = selectedObject["id"];
		$scope.companyTarget["targetId"] = selectedObject["targetId"];
		$scope.companyTarget["targetName"] = selectedObject["targetName"];
		$scope.companyTarget["targetValue"] = selectedObject["targetValue"];
		$scope.companyTarget["completionDate"] = selectedObject["completionDate"];
		$scope.companyTarget["completionDateStr"] = selectedObject["completionDateStr"];
		$scope.companyTarget["remarks"] = selectedObject["remarks"];
		$scope.companyTarget["premiseId"] = selectedObject["premiseId"];
		$scope.companyTarget["rationId"] = selectedObject["rationId"];
		if($scope.companyTarget["targetId"] == 91 || $scope.companyTarget["targetId"] == 92 || $scope.companyTarget["targetId"] == 93)
	        $scope.ShowRationOption = true;
		else
		{
			$scope.ShowRationOption = false;
			$scope.companyTarget["rationId"] = null;
		}
		$window.scrollTo(0, 0);
		
	}
	
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.companyTarget = {};		
		//document.getElementById("completionDate").value = null;
	}
});
