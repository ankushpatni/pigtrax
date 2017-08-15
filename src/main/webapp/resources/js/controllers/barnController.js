pigTrax.controller('BarnController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.premisesId;
	$scope.generatedPremisesId;
	$scope.phaseType; 
	$scope.differentPages=[{"name":"Room","value":"room"},{"name":"Silo","value":"silo"}];
	$scope.validationType;
	
	
	

	$scope.getBarnLocations = function()
	{
		restServices.getBarnLocations(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.barnLocationKeys = responseList[0];
				$scope.barnLocationKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getBarnLocations();
	
	$scope.getWaterTypes = function()
	{
		restServices.getWaterTypes(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.waterTypeKeys = responseList[0];
				$scope.waterTypeKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getWaterTypes();
	
	
	$scope.getBarnPositions = function()
	{
		restServices.getBarnPositions(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.barnPositionKeys = responseList[0];
				$scope.barnPositionKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getBarnPositions();
	
	
	$scope.getFeederTypes = function()
	{
		restServices.getFeederTypes(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.feederTypeKeys = responseList[0];
				$scope.feederTypeKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getFeederTypes();
	
	$scope.getVentilationTypes = function()
	{
		restServices.getVentilationTypes(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.validationTypeKeys1 = responseList[0];
				$scope.validationTypeKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getVentilationTypes();

	
	
	
	
	console.log($scope.differentPages);
	
	$scope.hoverIn = function(){
        this.hoverEdit = true;
    };

    $scope.hoverOut = function(){
        this.hoverEdit = false;
    };
	
	$scope.getPhaseType = function(phaseTypeId){
		console.log("phaseType--123-->");
	}
	
	$scope.gotToPage = function(page,row)
	{
		document.getElementById("generatedBarnId").value = row.id;
		document.forms['barnForm'].action = page;
		document.forms['barnForm'].submit();
	}
	
    //deactivate/activate to the real data holder
    $scope.removeItem = function removeItem(row) {
    	var postParam = {
    			"barnID" : row.barnId,
    			"isActive" : row.active
    	};
    	var res = $http.post('rest/barn/updateBarnStatus?barnID='+row.barnId +"&isActive="+row.active, postParam);
		res.success(function(data, status, headers, config) {
		row.active = !row.active;
			console.log(data);
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
    }
	
	$scope.addBarnData = function () {
			var modalInstance = $modal.open ({
    			templateUrl: 'addBarn',
    			controller: 'addBarnCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
					barnData : function(){
						var barnData={};
						barnData.premisesId= $scope.premisesId;
						barnData.generatedPremisesId = $scope.generatedPremisesId;
						barnData.phaseType = $scope.phaseType;
						barnData.phaseTypeKeys = $scope.phaseTypeKeys;
						barnData.validationType = $scope.validationType;	
						barnData.validationTypeKeys = $scope.validationTypeKeys;
    					return barnData;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
					$scope.getBarnList($scope.premisesId,$scope.generatedPremisesId);				
				}
    		});
    }
	
	$scope.editBarnData = function(barnData){
		var modalInstance = $modal.open ({
				templateUrl: 'addBarn',
				controller: 'addBarnCtrl',
				backdrop:true,
				windowClass : 'cp-model-window',
    			resolve:{
    				barnData : function(){
    					barnData.generatedPremisesId = $scope.generatedPremisesId;
						barnData.phaseType = $scope.phaseType;
						barnData.phaseTypeKeys = $scope.phaseTypeKeys;
						barnData.validationType = $scope.validationType;
    					return barnData;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="SUCCESS")
				{
					$scope.getBarnList($scope.premisesId,$scope.generatedPremisesId);				
				}
			});
		
    	}
		
	$scope.getBarnList = function(premisesId,generatedPremisesId,flag){
		$scope.premisesId = premisesId;
		$scope.generatedPremisesId = generatedPremisesId;
		if(flag)
		{
			 var res1 = $http.get('rest/util/getPhaseType');
      			res1.success(function(data, status, headers, config) {
					$scope.phaseType = data.payload[0];		
					$scope.phaseTypeKeys = data.payload[1];
					
					$scope.validationType = data.payload[2];
					$scope.validationTypeKeys = data.payload[3];
					
					console.log($scope.phaseType);
      			});
      			res1.error(function(data, status, headers, config) {
      				console.log( "failure message: " + {data: data});
      			});
		}
		var res = $http.get('rest/barn/getBarnList?generatedPremisesId='+generatedPremisesId);
			res.success(function(data, status, headers, config) {
				$scope.rowCollection = data.payload;
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
});