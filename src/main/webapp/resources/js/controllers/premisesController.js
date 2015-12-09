pigTrax.controller('PremisesController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.companyId;
	$scope.generatedCompanyId;
	$scope.differentPages=[{"name":"Barn","value":"barn"}];
	$scope.country;
	$scope.cityJSON;
	
	$scope.hoverIn = function(){
        this.hoverEdit = true;
    };

    $scope.hoverOut = function(){
        this.hoverEdit = false;
    };

	
    //deactivate/activate to the real data holder
    $scope.removeItem = function removeItem(row) {
    	var postParam = {
    			"premisesID" : row.permiseId,
    			"isActive" : row.active
    	};
    	var res = $http.post('rest/premises/updatePremisesStatus?premisesID='+row.permiseId +"&isActive="+row.active, postParam);
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
			document.getElementById("generatedPremisesId").value = row.id;
			document.forms['premisesForm'].action = 'barn';
			document.forms['premisesForm'].submit();
			//$window.location = $scope.differentPages[index].value+'?generatedPremisesId='+row.id;
		}
    
	
		
	 
	$scope.addPremiseData = function () {
			var modalInstance = $modal.open ({
    			templateUrl: 'addPremises',
    			controller: 'addPremisesCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
					premisesData : function(){						
						var premisesData={};
						premisesData.companyId= $scope.companyId;
						premisesData.generatedCompanyId = $scope.generatedCompanyId;	
						premisesData.countryList = $scope.country;
						premisesData.cityJSON = $scope.cityJSON;
    					return premisesData;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
					$scope.getPremisesList($scope.companyId,$scope.generatedCompanyId);
				}
    		});
    }
	
	
	
	
	$scope.editPremiseData = function(premisesData){
		var modalInstance = $modal.open ({
    			templateUrl: 'addPremises',
    			controller: 'addPremisesCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
    			resolve:{
    				premisesData : function(){
    					premisesData.generatedCompanyId = $scope.premisesData;
    					premisesData.cityJSON = $scope.cityJSON;
    					return premisesData;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="SUCCESS")
				{
					$scope.getPremisesList($scope.companyId,$scope.generatedCompanyId);	
				}
			});
		
    	}
		
	$scope.getPremisesList = function(companyId,generatedCompanyId){
	console.log("companyId--->"+companyId)
	$scope.companyId = companyId;
	$scope.generatedCompanyId = generatedCompanyId;
	var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+generatedCompanyId);
		res.success(function(data, status, headers, config) {
			$scope.rowCollection = data.payload;
			$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		/*restServices.getPremisesList(function(data){
			 if(!data.error)
			 {
				console.log(data.payload);
				$scope.rowCollection = data.payload;
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
			 }
		},companyId);*/
	};
	
	
	$scope.deletePremise = function(premiseObj)
	{
		restServices.deletePremise(premiseObj.id, function(data) {
			if(!data.error)
			{
				$scope.premiseDeleteErrorMessage = false;
				$scope.getPremisesList($scope.companyId,$scope.generatedCompanyId);				
			}
			else
				{
				  $scope.premiseDeleteErrorMessage = true;
				}
		});
	}
});