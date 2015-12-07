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
		$scope.myDateFormat = false;
		$cookieStore.put('PT_DATEFORMAT', 'dd/MM/yyyy');
	}
	
	
	$scope.$watch("myDateFormat", function (newValue, oldValue) {
		
	    if(newValue)
		{
			$cookieStore.put('PT_DATEFORMAT', 'MM/dd/yyyy');
			$scope.myDateFormat = true;
			document.getElementById("format-btn").innerHTML="MM/dd/YYYY"
		}
		else
		{
			$cookieStore.put('PT_DATEFORMAT', 'dd/MM/yyyy');
			$scope.myDateFormat = false;
			document.getElementById("format-btn").innerHTML="dd/MM/YYYY"
		}
	});
	
	$scope.toggle = function()
	{
		if($scope.myDateFormat)
		{
			$scope.myDateFormat = false;		
			document.getElementById("format-btn").innerHTML="dd/MM/YYYY"
		}
		else
		{
			$scope.myDateFormat = true;
			document.getElementById("format-btn").innerHTML="MM/dd/YYYY"
		}
	}
	
});