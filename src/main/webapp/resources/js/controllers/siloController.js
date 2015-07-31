pigTrax.controller('SiloController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.differentPages=[{"name":"Pen","value":"pen"}];
	$scope.validationType;
	
	
	console.log($scope.differentPages);
	
	$scope.hoverIn = function(){
        this.hoverEdit = true;
    };

    $scope.hoverOut = function(){
        this.hoverEdit = false;
    };
    
    $scope.gotToPage = function(index,row)
	{
		console.log(index);
		console.log($scope.differentPages[index].value);
		console.log(document.getElementById("generatedSiloId").value);
		document.getElementById("generatedSiloId").value = row.id;
		document.forms['siloForm'].action = $scope.differentPages[index].value;
		document.forms['siloForm'].submit();
	}
	
	//deactivate/activate to the real data holder
    $scope.removeItem = function removeItem(row) {
    	var postParam = {
    			"siloID" : row.barnId,
    			"isActive" : row.active
    	};
    	var res = $http.post('rest/silo/updateSiloStatus?siloId='+row.siloId +"&isActive="+row.active, postParam);
		res.success(function(data, status, headers, config) {
		row.active = !row.active;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
    }
	
	$scope.addSiloData = function () {
			var modalInstance = $modal.open ({
    			templateUrl: 'addSilo',
    			controller: 'addSiloCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
					siloData : function(){
						var siloData={};
						siloData.barnId= $scope.barnId;
						siloData.generatedBarnId = $scope.generatedBarnId;	
    					return siloData;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
    				$scope.getSiloList($scope.barnId,$scope.generatedBarnId);				
				}
    		});
    }
	
	$scope.editSiloData = function(siloData){
		var modalInstance = $modal.open ({
				templateUrl: 'addSilo',
				controller: 'addSiloCtrl',
				backdrop:true,
				windowClass : 'cp-model-window',
    			resolve:{
    				siloData : function(){
						siloData.barnId= $scope.barnId;
						siloData.generatedBarnId = $scope.generatedBarnId;	
    					return siloData;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="SUCCESS")
				{
					$scope.getSiloList($scope.barnId,$scope.generatedBarnId);				
				}
			});
		
    	}
		
	$scope.getSiloList = function(barnId,generatedBarnId){
		$scope.barnId = barnId;
		$scope.generatedBarnId = generatedBarnId;
		
		var res = $http.get('rest/silo/getSiloList?generatedBarnId='+generatedBarnId);
			res.success(function(data, status, headers, config) {
				$scope.rowCollection = data.payload;
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
});