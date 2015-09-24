pigTrax.controller('PenController', function($scope, $http, $window,$modal, restServices) {	
	$scope.rowCollection = [];
	$scope.itemsByPage=10;
	$scope.totalPages;
	$scope.roomId;
	$scope.generatedRoomId;
	 
	$scope.differentPages=[{"name":"Pen","value":"pen"}];
	
	
	
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
		console.log(document.getElementById("generatedPenId").value);
		document.getElementById("generatedPenId").value = row.id;
		document.forms['roomForm'].action = $scope.differentPages[index].value;
		document.forms['roomForm'].submit();
	}
	
	//deactivate/activate to the real data holder
    $scope.removeItem = function removeItem(row) {
    	var postParam = {
    			"penID" : row.penId,
    			"isActive" : row.active
    	};
    	var res = $http.post('rest/pen/updatePenStatus?penId='+row.penId +"&isActive="+row.active, postParam);
		res.success(function(data, status, headers, config) {
		row.active = !row.active;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
    }
	
	$scope.addPenData = function () {
			var modalInstance = $modal.open ({
    			templateUrl: 'addPen',
    			controller: 'addPenCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
					penData : function(){
						var penData={};
						penData.roomId= $scope.roomId;
						penData.generatedRoomId = $scope.generatedRoomId;	
    					return penData;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			if(res.statusMessage==="SUCCESS")
				{
    				$scope.getPenList($scope.roomId,$scope.generatedRoomId);				
				}
    		});
    }
	
	$scope.editPenData = function(penData){
		var modalInstance = $modal.open ({
				templateUrl: 'addPen',
				controller: 'addPenCtrl',
				backdrop:true,
				windowClass : 'cp-model-window',
    			resolve:{
    				penData : function(){
    					penData.roomId= $scope.roomId;
    					penData.generatedRoomId = $scope.generatedRoomId;	
    					return penData;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {
				if(res.statusMessage==="SUCCESS")
				{
					$scope.getPenList($scope.roomId,$scope.generatedRoomId);				
				}
			});
		
    	}
		
	$scope.getPenList = function(roomId,generatedRoomId){
		$scope.roomId = roomId;
		$scope.generatedRoomId = generatedRoomId;
		
		var res = $http.get('rest/pen/getPenList?generatedRoomId='+generatedRoomId);
			res.success(function(data, status, headers, config) {
				$scope.rowCollection = data.payload;
				$scope.totalPages = Math.ceil($scope.rowCollection.length/10);
			});
			res.error(function(data, status, headers, config) {
				console.log( "failure message: " + {data: data});
			});			
	};
});