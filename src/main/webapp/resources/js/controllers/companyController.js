pigTrax.controller('CompanyController', function($scope, $http, $window,$modal, restServices, DateUtils) {	
	$scope.today = new Date();
	
	$scope.formattedToday = DateUtils.getFormatedDate($scope.today);
	
	$scope.DateUtils = DateUtils;
	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.differentPages=[{"name":"Premises","value":"premises"}];
	$scope.country;
	$scope.cityJSON;
	$scope.city;
	
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
	
	$scope.showCompanyDetail = function(row)
	{
		var modalInstance = $modal.open ({
			templateUrl: 'addCompany',
			controller: 'addCompanyCtrl',
			backdrop:true,
			windowClass : 'cp-model-window',
			resolve:{
				companyData : function(){
					row.countryList = $scope.country;
					row.cityJSON = $scope.cityJSON;
					row.showDetail = true;
					return row;
				}
			}
		});    		
		
	}
	
	$scope.addCompanyData = function () {
    		var modalInstance = $modal.open ({
    			templateUrl: 'addCompany',
    			controller: 'addCompanyCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
    				companyData : function(){
					var companyData={};
						companyData.countryList = $scope.country;
						companyData.cityJSON = $scope.cityJSON;
						companyData.showDetail = false;
    					return companyData;
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
						companyRow.countryList = $scope.country;
						companyRow.cityJSON = $scope.cityJSON;
						companyRow.showDetail = false;
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
	restServices.getCityCountryList(function(data){
	console.log(data);
			 if(!data.error)
			 {
				$scope.country = data.payload[0];
				var temp = data.payload[1];
				$scope.cityJSON = temp[0];				
			 }
		});
		restServices.getCompanyList(function(data){
			 if(!data.error)
			 {
				$scope.rowCollection = data.payload;
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
			 }
		});
	};
	
	$scope.getCityName = function(countryId)
	{
		$scope.city = $scope.cityJSON[countryId];
		console.log($scope.city );		
	}
	
	
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
		else if(eventName === "GroupEvent")
		{
			document.getElementById("companyForm").action="groupEvent";		 
		}
		else if(eventName == "PigletEvent")
		{
			document.getElementById("companyForm").action="pigletEvent";		 
		}
		else if(eventName == "PigletStatusEvent")
		{
			document.getElementById("companyForm").action="pigletStatusEvent";		 
		}
		else if(eventName == "FeedEvent")
		{
			document.getElementById("companyForm").action="feedEvent";		 
		}
		else if(eventName == "RemovalEvent")
		{
			document.getElementById("companyForm").action="removalEvent";		 
		}
		else if(eventName == "Target")
		{
			document.getElementById("companyForm").action="companyTarget";		 
		}
		else if(eventName == "ChangePigId")
		{
			document.getElementById("companyForm").action="changeId";		 
		}
		else if(eventName == "ProductionLog")
		{
			document.getElementById("companyForm").action="loadProductionLogList";		 
		}

		
		document.getElementById("selectedCompany").value= companyId;
		document.getElementById("companyForm").submit();
	}
});