pigTrax.controller('TransportDestinationController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollection = [];
	$scope.generatedCompanyId;
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.transportDestinationData;
		
	$scope.hoverIn = function(){
        this.hoverEdit = true;
    };

    $scope.hoverOut = function(){
        this.hoverEdit = false;
    };
    
   $scope.deleteTransportDestinationData = function removeItem(row) {
    	var postParam = {
				"id" : row.id							
			};
	
			console.log(postParam);
			var res = $http.post('rest/transportDestination/insertTransportDestination', postParam);
			res.success(function(data, status, headers, config) {
				if(data.statusMessage==="SUCCESS")
				{
					$scope.getTransportDestinationList($scope.generatedCompanyId);
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
	
	$scope.addTransportDestinationData = function () {
			var modalInstance = $modal.open ({
    			templateUrl: 'addTransportDestination',
    			controller: 'addTransportDestinationCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
					transportDestinationData : function(){
						var transportDestinationData={};
						transportDestinationData.generatedCompanyId =  $scope.generatedCompanyId;
						return transportDestinationData;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
    				$scope.getTransportDestinationList($scope.generatedCompanyId);				
				}
    		});
    }
			
	$scope.getTransportDestinationList = function(generatedCompanyId){
		$scope.generatedCompanyId = generatedCompanyId;
		
		var res = $http.get('rest/transportDestination/getTransportDestination?generatedCompanyId='+generatedCompanyId);
			res.success(function(data, status, headers, config) {
				$scope.rowCollection = data.payload[0];
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);				
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
});