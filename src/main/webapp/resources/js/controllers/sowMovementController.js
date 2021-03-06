	var sowMovementController = pigTrax.controller('SowMovementController', function($scope,$rootScope, $modal, $http,$window,restServices, DateUtils) {
	$scope.companyId = ""; 
	$rootScope.companyId = "";
	$scope.roomList={};
	$scope.sowMovementList = {};	
	$scope.DateUtils = DateUtils;
	$scope.premisesMap = [];
	
	$scope.loadPremises = function()
	{
		var res = $http.get('rest/sowMomentEvent/getPremisesMapList?companyId='+$rootScope.companyId);
		res.success(function(data, status, headers, config) {
			$scope.farmMap = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}
	
	$scope.loadPremisesList = function()
	{
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+$rootScope.companyId+'&premisesType=null');
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
			for( var x in $scope.premiseList) {
					var obje = $scope.premiseList[x];
					$scope.premisesMap[obje.id] = obje.name;
				}
				
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});
		
	}
	
	$scope.getRooms = function()
		{
			var postParam = {
				"companyId" : $rootScope.companyId
			};
			restServices.getRoomsForCompany($rootScope.companyId, function(data){
				if(!data.error){
					
					$scope.roomMap = data.payload;
					
				}
			});
		}
	
	$scope.setCompanyId = function(companyId)
	{
		$scope.companyId = companyId;
		$rootScope.companyId = companyId;
		$scope.loadPremisesList();
		$scope.loadPremises();
		$scope.getRooms();
	};
	
	
	
	$scope.clearAllMessages = function()
	{
		$scope.searchDataErrorMessage = false;	
		$scope.sowMovementSuccessMessage = false;
		$scope.changedPremisesId = false;
	};
	
	$scope.getSowMovement = function ()
	{
		var postParam = {
				
				"pigInfo" : $scope.searchText,
				"companyId" : $scope.companyId,
				"premiseId" : $scope.premiseId
			};
		
		restServices.getSowMovement(postParam, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.clearAllMessages();
					$window.scrollTo(0,550);
					$scope.sowMovementList = data.payload;
					if($scope.sowMovementList == null)
					{
						$scope.sowMovementSuccessMessage = false;
						$scope.searchDataErrorMessage = true;
					}
					else
					{							
						$scope.sowMovementSuccessMessage = true;
						$scope.searchDataErrorMessage = false;
					}
				}
			else
				{
					$scope.sowMovementList = {};
					$scope.sowMovementSuccessMessage = false;
					$scope.searchDataErrorMessage = true;
				}
		});
	}
	
	$scope.editSowMovement = function(sowMovementData){
		var modalInstance = $modal.open ({
				templateUrl: 'editSowMovement',
				controller: 'editSowMovementCtrl',
				backdrop:true,
				windowClass : 'cp-model-window',
    			resolve:{
    				sowMovementData : function(){ 
    				sowMovementData.premisesMap = $scope.premisesMap;
    				sowMovementData.roomMap = $scope.roomMap;
					sowMovementData.pigInfo = $scope.searchText;
					sowMovementData.premiseList = $scope.premiseList;
					return sowMovementData;
					}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="Success")
				{
					if(res.changedPremisesId!=$scope.premiseId)
					{
						$scope.changedPremisesId = true;
						$scope.sowMovementSuccessMessage = false;
					}
					else
					{
						$scope.getSowMovement();	
					}					
				}
			});
		
    	}
		
		$scope.deleteSowMovement = function(id){		
		restServices.deleteSowMovement(id, function(data){
			console.log(data);
			if(!data.error)
				{
					$scope.getSowMovement();
				}
			});		
    	}
});