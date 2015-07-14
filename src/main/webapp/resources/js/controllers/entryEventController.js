pigTrax.controller('EntryEventController', function($scope, $http,$window,restServices) {	
  alert("Inside Test Controller");
  $scope.populateBarns = function(companyId){
		alert("Company Id : "+companyId); 
		restServices.getBarns(function(data){
			 if(!data.error)
			 {
				    $scope.barns = data.payload;
			 }
		});
	};
});