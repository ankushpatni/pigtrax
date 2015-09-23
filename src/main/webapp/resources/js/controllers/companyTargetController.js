pigTrax.service("CompanyTargetsService", function() {
    this.checkIfExists = function(companyTargets, newCompanyTarget) {
       var len = 0;
       if(companyTargets != null)
    	    len = companyTargets.length;
       if(len > 0)
    	   {
    	     for(i = 0; i<len; i++)
    	    	 {
    	    	   item = companyTargets[i];
    	    	   if(newCompanyTarget["id"] == null && item["targetId"] == newCompanyTarget["targetId"])
    	    	   		return true;
    	    	   else if(newCompanyTarget["id"] != null && newCompanyTarget["id"] != item["id"] && item["targetId"] == newCompanyTarget["targetId"])
    	    		   return true;
    	    	 }
    	   }
       return false;
      };
});



pigTrax.controller('CompanyTargetController', function($scope,$rootScope, $http,$window,restServices, DateUtils, CompanyTargetsService) {
	$scope.companyId = "";
	$scope.companyTarget = {};
	
	
	$scope.setCompanyId = function(companyId)
	{
		$rootScope.companyId = companyId;
		$scope.getCompanyTargets();
		$scope.getTargetTypes();
	};
	
	$scope.getTargetTypes = function()
	{
		restServices.getTargetTypes(function(data){
			if(!data.error)
				{
				  $scope.targetTypes = data.payload;
				}
		});
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
		$scope.remarksRequired = false;
	}
	
	
	
	$scope.saveCompanyTarget = function()
	{
		valid = true;
		var completionDate = document.getElementById("completionDate").value;
		$scope.companyTarget["completionDate"] = completionDate;
		
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
		if($scope.companyTarget["remarks"] == null)
		{
			$scope.remarksRequired = true;
			valid = false;
		}
		else
		{
			$scope.remarksRequired = false;			
		}
		
		
		if(valid)
		{
			
		  var exists = CompanyTargetsService.checkIfExists($scope.companyTargets,$scope.companyTarget);
		  if(!exists)
			  {
			
		   var completionDate = document.getElementById("completionDate").value;
		   $scope.companyTarget["completionDate"] = completionDate;
		   $scope.companyTarget["companyId"] = $rootScope.companyId;
			
		   restServices.saveCompanyTarget($scope.companyTarget, function(data){
			   if(!data.error){
				   $scope.clearAllMessages();
				   $scope.companyTargetSaved = true;	
				   var resultObj = data.payload;
				   $scope.modifyArray(resultObj);
				   $scope.companyTarget = {};
			   }
		   });	
			  }
		  else
			  {
			    $scope.clearAllMessages();
			    $scope.duplicateCompanyTarget = true;
			  }
		}
	};
	
	$scope.modifyArray = function(companyTargetObj)
	{
		var exists = false;
		
		for(i =0 ; i<$scope.companyTargets.length; i++)
		{
			  if($scope.companyTargets[i]["id"] == companyTargetObj["id"])
			  {
				  $scope.companyTargets[i] = companyTargetObj;
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
		});
	}
	
	
	$scope.editCompanyTarget = function(selectedObject)
	{
		$scope.companyTarget = selectedObject;
	}
	
	
	$scope.resetForm = function()
	{
		$scope.clearAllMessages();
		$scope.companyTarget = {};
		document.getElementById("completionDate").value = null;
	}
});
