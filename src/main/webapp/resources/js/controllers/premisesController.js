pigTrax.controller('PremisesController', function($scope, $http, $window,$modal, restServices, sharedProperties) {	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.companyId = sharedProperties.getProperty();
	$scope.differentPages=[{"name":"Barn","value":"Barn"}];
	
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
	
	$scope.gotToPage = function(index)
	{
		$window.location = 'employee';
	}
    
	$scope.addPremiseData = function () {
    		var modalInstance = $modal.open ({
    			templateUrl: 'addPremises',
    			controller: 'addPremisesCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
					premisesData : function(){
    					return null;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
					$scope.getPremisesList();				
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
    					return premisesData;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="SUCCESS")
				{
					$scope.getPremisesList();				
				}
			});
		
    	}
		
	$scope.getPremisesList = function(){
	console.log($scope.companyId);
		restServices.getPremisesList(function(data){
			 if(!data.error)
			 {
				
				console.log(data.payload);
				$scope.rowCollection = data.payload;
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
			 }
		});
	};
});