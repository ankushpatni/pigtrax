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
	$scope.transportTrailerTypeKeySet;
		
	$scope.hoverIn = function(){
        this.hoverEdit = true;
    };

    $scope.hoverOut = function(){
        this.hoverEdit = false;
    };
    
    
    $scope.getTrailerFunctions = function()
	{
		restServices.getTrailerFunctions(function(data)
		{
			if(!data.error){
				var responseList = data.payload;
				$scope.trailerFunctionKeys = responseList[0];
				$scope.trailerFunctionKeyTypes =responseList[1];
			}
		});
	}
    
    $scope.getTrailerFunctions();
    
    $scope.gotToPage = function(index,row)
	{
		document.getElementById("generatedSiloId").value = row.id;
		document.forms['siloForm'].action = $scope.differentPages[index].value;
		document.forms['siloForm'].submit();
	}
	
	$scope.deleteTransportTruckData = function removeItem(row) {
		$scope.clearAllMessages();
    	var postParam = {
				"id" : row.id							
			};
	
			console.log(postParam);
			var res = $http.post('rest/transportTrailerTruck/insertTransportTruckRecord', postParam);
			res.success(function(data, status, headers, config) {
				if(data.statusMessage==="SUCCESS")
				{
					$scope.getTransportTrailerTruck($scope.generatedCompanyId);
					$scope.truckDeleteMessage = true;
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
		$scope.clearAllMessages();
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
						truckData.transportTrailerTypeKeySet = $scope.transportTrailerTypeKeySet;
    					return truckData;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
    				$scope.getTransportTrailerTruck($scope.generatedCompanyId);		
    				$scope.truckSuccessMessage = true;
				}
    		});
    }
	
	$scope.deleteTransportTrailerData = function removeItem(row) {
    	var postParam = {
				"id" : row.id							
			};
    		$scope.clearAllMessages();
			console.log(postParam);
			var res = $http.post('rest/transportTrailerTruck/insertTransportTrailorRecord', postParam);
			res.success(function(data, status, headers, config) {
				if(data.statusMessage==="SUCCESS")
				{
					$scope.getTransportTrailerTruck($scope.generatedCompanyId);
					$scope.trailerDeleteMessage = true;
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
	
	$scope.clearAllMessages = function()
	{ 
		$scope.trailerDeleteMessage = false;
		$scope.trailerSuccessMessage = false;
		$scope.truckDeleteMessage = false;
		$scope.truckSuccessMessage = false;
	};	
	
	$scope.addTransportTrailerData = function () {
		$scope.clearAllMessages();
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
					trailorData.transportTrailerTypeKeySet = $scope.transportTrailerTypeKeySet;
					return trailorData;
				}
			}
		});
		
		modalInstance.result.then( function(res) {    			
			if(res.statusMessage==="SUCCESS")
			{
				$scope.getTransportTrailerTruck($scope.generatedCompanyId);	
				$scope.trailerSuccessMessage = true;
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
				$scope.transportTrailerTypeKeySet = data.payload[3];
				$scope.totalPagesTruck = Math.ceil($scope.rowCollectionTruck.length/10);
				$scope.totalPagesTrailer = Math.ceil($scope.rowCollectionTrailer.length/10);
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
});