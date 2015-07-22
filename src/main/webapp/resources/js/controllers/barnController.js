pigTrax.controller('BarnController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.premisesId;
	$scope.generatedPremisesId;
	$scope.phaseType; 
	$scope.differentPages=[{"name":"Room","value":"room"}];
	$scope.validationType;
	
	
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
	
	$scope.gotToPage = function(index,row)
	{
		console.log(index);
		console.log($scope.differentPages[index].value);
		console.log(document.getElementById("generatedBarnId").value);
		document.getElementById("generatedBarnId").value = row.id;
		document.forms['barnForm'].action = $scope.differentPages[index].value;
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
					$scope.validationType = data.payload[1];					
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