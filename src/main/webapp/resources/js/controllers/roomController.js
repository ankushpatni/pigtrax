pigTrax.controller('RoomController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.premisesId;
	$scope.generatedPremisesId;
	$scope.phaseType; 
	$scope.differentPages=[{"name":"Pen","value":"pen"}];
	$scope.validationType;
	
	
	console.log($scope.differentPages);
	
	$scope.hoverIn = function(){
        this.hoverEdit = true;
    };

    $scope.hoverOut = function(){
        this.hoverEdit = false;
    };
    
    $scope.gotToPage = function(row)
	{
		document.getElementById("generatedRoomId").value = row.id;
		document.forms['roomForm'].action = 'pen';
		document.forms['roomForm'].submit();
	}
	
	//deactivate/activate to the real data holder
    $scope.removeItem = function removeItem(row) {
    	var postParam = {
    			"roomID" : row.barnId,
    			"isActive" : row.active
    	};
    	var res = $http.post('rest/room/updateRoomStatus?roomId='+row.roomId +"&isActive="+row.active, postParam);
		res.success(function(data, status, headers, config) {
		row.active = !row.active;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
    }
	
	$scope.addRoomData = function () {
			var modalInstance = $modal.open ({
    			templateUrl: 'addRoom',
    			controller: 'addRoomCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
					roomData : function(){
						var roomData={};
						roomData.barnId= $scope.barnId;
						roomData.generatedBarnId = $scope.generatedBarnId;	
    					return roomData;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
    				$scope.getRoomList($scope.barnId,$scope.generatedBarnId);				
				}
    		});
    }
	
	$scope.editRoomData = function(roomData){
		var modalInstance = $modal.open ({
				templateUrl: 'addRoom',
				controller: 'addRoomCtrl',
				backdrop:true,
				windowClass : 'cp-model-window',
    			resolve:{
    				roomData : function(){
						roomData.barnId= $scope.barnId;
						roomData.generatedBarnId = $scope.generatedBarnId;	
    					return roomData;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="SUCCESS")
				{
					$scope.getRoomList($scope.barnId,$scope.generatedBarnId);				
				}
			});
		
    	}
		
	$scope.getRoomList = function(barnId,generatedBarnId){
		$scope.barnId = barnId;
		$scope.generatedBarnId = generatedBarnId;
		
		var res = $http.get('rest/room/getRoomList?generatedBarnId='+generatedBarnId);
			res.success(function(data, status, headers, config) {
				$scope.rowCollection = data.payload;
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
});