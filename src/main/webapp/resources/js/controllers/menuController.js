pigTrax.controller('MenuController', function($scope, $http, $window,$modal, restServices, $cookieStore, DateUtils) {	

   if($cookieStore.get('PT_DATEFORMAT') != null && $cookieStore.get('PT_DATEFORMAT') != undefined)
   {
	    if($cookieStore.get('PT_DATEFORMAT') == 'MM/dd/yyyy')
		{
			$scope.myDateFormat = true;
		}
		else
		{
			$scope.myDateFormat = false;
		}
   }
	else
	{
		document.getElementById("switch-state").checked = false;
		$cookieStore.put('PT_DATEFORMAT', 'dd/MM/yyyy');
	}
	
	
	$scope.$watch("myDateFormat", function (newValue, oldValue) {
		
		
	    if(newValue)
		{
			$cookieStore.put('PT_DATEFORMAT', 'MM/dd/yyyy');
			$scope.myDateFormat = true;
		}
		else
		{
			$cookieStore.put('PT_DATEFORMAT', 'dd/MM/yyyy');
			$scope.myDateFormat = false;
		}
	});
	

	$scope.setFormat = function()
	{
		alert("caling format")
		if(document.getElementById("switch-state").checked)
		{
			$cookieStore.put('PT_DATEFORMAT', 'MM/dd/yyyy');
		}	
		else{
			$cookieStore.put('PT_DATEFORMAT', 'dd/MM/yyyy');
		}
	}
});