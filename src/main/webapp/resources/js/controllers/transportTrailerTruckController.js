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
	
	$scope.deleteTransportTruckData = function removeItem(row) {
    	var postParam = {
				"id" : row.id							
			};
	
			console.log(postParam);
			var res = $http.post('rest/transportTrailerTruck/insertTransportTruckRecord', postParam);
			res.success(function(data, status, headers, config) {
				if(data.statusMessage==="SUCCESS")
				{
					$scope.getTransportTrailerTruck($scope.generatedCompanyId);
				}
				else
				{
					$scope.alertMessage = data.payload;
					$scope.alertVisible = true;
				}
			});
			res.error(function(data, status, headers, config) {
				console.log( data);
				$scope.alertMessage = data.statusMessage;
				$scope.alertVisible = true;
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
						truckData.truck = true;
						truckData.transportTrailerType = $scope.transportTrailerType;
    					return truckData;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
    				$scope.getTransportTrailerTruck($scope.generatedCompanyId);				
				}
    		});
    }
	
	$scope.deleteTransportTrailerData = function removeItem(row) {
    	var postParam = {
				"id" : row.id							
			};
	
			console.log(postParam);
			var res = $http.post('rest/transportTrailerTruck/insertTransportTrailorRecord', postParam);
			res.success(function(data, status, headers, config) {
				if(data.statusMessage==="SUCCESS")
				{
					$scope.getTransportTrailerTruck($scope.generatedCompanyId);
				}
				else
				{
					$scope.alertMessage = data.payload;
					$scope.alertVisible = true;
				}
			});
			res.error(function(data, status, headers, config) {
				console.log( data);
				$scope.alertMessage = data.statusMessage;
				$scope.alertVisible = true;
			});
			
    }
	
	$scope.addTransportTrailerData = function () {
		var modalInstance = $modal.open ({
			templateUrl: 'addTruck',
			controller: 'addTruckTrailorCtrl',
			backdrop:true,
			windowClass : 'cp-model-window',
			resolve:{
				truckTrailorData : function(){
					var trailorData={};
					trailorData.generatedCompanyId =  $scope.generatedCompanyId;
					trailorData.truck = false;
					trailorData.transportTrailerType = $scope.transportTrailerType;
					return trailorData;
				}
			}
		});
		
		modalInstance.result.then( function(res) {    			
			if(res.statusMessage==="SUCCESS")
			{
				$scope.getTransportTrailerTruck($scope.generatedCompanyId);				
			}
		});
}
		
	$scope.getTransportTrailerTruck = function(generatedCompanyId){
		$scope.generatedCompanyId = generatedCompanyId;
		
		var res = $http.get('rest/transportTrailerTruck/getTransportTrailerTruck?generatedCompanyId='+generatedCompanyId);
			res.success(function(data, status, headers, config) {
			console.log(data);
				$scope.rowCollectionTruck = data.payload[0];
				$scope.rowCollectionTrailer = data.payload[1];
				$scope.transportTrailerType = data.payload[2];
				$scope.totalPagesTruck = Math.ceil($scope.rowCollectionTruck.length/10);
				$scope.totalPagesTrailer = Math.ceil($scope.rowCollectionTrailer.length/10);
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
});