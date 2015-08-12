pigTrax.controller('CompanyController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.differentPages=[{"name":"Premises","value":"premises"}];
	
	$scope.hoverIn = function(){
        this.hoverEdit = true;
    };

    $scope.hoverOut = function(){
        this.hoverEdit = false;
    };

	
    //deactivate/activate to the real data holder
    $scope.removeItem = function removeItem(row) {
    	var postParam = {
    			"companyId" : row.companyId,
    			"isActive" : row.active
    	};
    	var res = $http.post('rest/company/updateCompanyStatus?companyId='+row.companyId +"&isActive="+row.active, postParam);
		res.success(function(data, status, headers, config) {
		row.active = !row.active;
			console.log(data);
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
    }
	
	 $scope.$watch($scope.editCompanyData, $scope.getCompanyList, true);
	
	$scope.gotToPage = function(row)
	{
		document.getElementById("generatedCompanyId").value = row.id;
		document.forms['companyForm'].action = 'premises';
		document.forms['companyForm'].submit();
		//$window.location = $scope.differentPages[index].value+'?generatedCompanyId='+row.id;
	}
    
	$scope.gotToPageFromDetails = function(id)
	{
		document.getElementById("generatedCompanyId").value = id;
		document.forms['companyDetailForm'].action = 'premises';
		document.forms['companyDetailForm'].submit();
		//$window.location = $scope.differentPages[index].value+'?generatedCompanyId='+row.id;
	}
	
	$scope.gotToTransportFromDetails = function()
	{
		//document.forms['companyDetailForm'].action = 'transportTrailerAndTruck';
		//document.forms['companyDetailForm'].submit();
		$window.location = 'transportTrailerAndTruck';
	}
	
	$scope.gotToTransportFromDetailsCompany = function(row, pageName)
	{
		document.getElementById("generatedCompanyId").value = row.id;
		document.forms['companyForm'].action = pageName;
		document.forms['companyForm'].submit();
	}
	
	$scope.addCompanyData = function () {
    		var modalInstance = $modal.open ({
    			templateUrl: 'addCompany',
    			controller: 'addCompanyCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
    				companyData : function(){
    					return null;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
					$scope.getCompanyList();				
				}
    		});
    }
	
	$scope.editCompanyData = function(companyRow){
		var modalInstance = $modal.open ({
    			templateUrl: 'addCompany',
    			controller: 'addCompanyCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
    			resolve:{
    				companyData : function(){
    					return companyRow;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="SUCCESS")
				{
					$scope.getCompanyList();				
				}
			});
		
    	}
	
	$scope.getCompanyList = function(){
		restServices.getCompanyList(function(data){
			 if(!data.error)
			 {
				$scope.rowCollection = data.payload;
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
			 }
		});
	};
	
	
	$scope.goToPigEvent= function(eventName, companyId)
	{
		if(eventName == "EntryEvent")
		{
			document.getElementById("companyForm").action="entryEvent";			
		}
		else if(eventName == "BreedingEvent")
		{
			document.getElementById("companyForm").action="breedingEvent";			
		}
		else if(eventName == "PregnancyEvent")
		{
			document.getElementById("companyForm").action="pregnancyEvent";		 
		}
		else if(eventName == "FarrowEvent")
		{
			document.getElementById("companyForm").action="farrowEvent";		 
		}
		document.getElementById("selectedCompany").value= companyId;
		document.getElementById("companyForm").submit();
	}
});