pigTrax.controller('TransportTrailerTruckController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollectionTruck = [];
	$scope.rowCollectionTrailer = [];
	$scope.generatedCompanyId;
	$scope.itemsByPage=10;
	$scope.totalPagesTruck;
	$scope.totalPagesTrailer;
	$scope.truckData;
	$scope.trailerData;
	$scope.transportTrailerType;
		
	$scope.hoverIn = function(){
        this.hoverEdit = true;
    };

    $scope.hoverOut = function(){
        this.hoverEdit = false;
    };
    
    $scope.gotToPage = function(index,row)
	{
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
	
	$scope.addTransportTruckData = function () {
			var modalInstance = $modal.open ({
    			templateUrl: 'addTruck',
    			controller: 'addTruckTrailorCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
					truckTrailorData : function(){
						var truckData={};
						truckData.generatedCompanyId =  $scope.generatedCompanyId;
    					return truckData;
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
						siloData.siloType = $scope.siloType;
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
		
	$scope.getTransportTrailerTruck = function(generatedCompanyId){
		$scope.generatedCompanyId = generatedCompanyId;
		
		var res = $http.get('rest/transportTrailerTruck/getTransportTrailerTruck?generatedCompanyId='+generatedCompanyId);
			res.success(function(data, status, headers, config) {
				$scope.rowCollectionTruck = data.payload[0];
				$scope.rowCollectionTrailer = data.payload[1];
				$scope.totalPagesTruck = Math.ceil($scope.rowCollectionTruck.length/10);
				$scope.totalPagesTrailer = Math.ceil($scope.rowCollectionTrailer.length/10);
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
});