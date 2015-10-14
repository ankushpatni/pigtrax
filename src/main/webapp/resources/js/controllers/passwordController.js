pigTrax.controller('PasswordController', function($scope, $http, $window,$modal, restServices) {	
	
	$scope.employee = {};
	
	$scope.clearAllMessages = function()
	{
		$scope.successMessage = false;
		$scope.errorMessage = false;
		$scope.invalidOTPMessage = false;
		$scope.passwordDoesnotMatch = false;
		$scope.changePasswordSuccess = false;
		$scope.changePasswordError = false;
	}
	
	
	$scope.forgotPassword = function()
	{
		restServices.forgotPassword($scope.employeeId, function(data){
			if(!data.error){
				$scope.clearAllMessages();
				var status = data.statusMessage;
				if(status == "success")
					$scope.successMessage = true;
				else
					$scope.errorMessage = true;
			}
		});
	}

	$scope.forgotChangePassword = function()
	{	
		var otp = document.getElementById("otp").value;
		var employeeId = document.getElementById("eid").value;
		
		var empDto = {"employeeId" : employeeId, "otPassword" : $scope.employee.oneTimePassword}
		restServices.validateOneTimePassword(empDto,function(data) {
			$scope.clearAllMessages();
			if(!data.error)
			{
				$scope.clearAllMessages();
				
				if($scope.employee["newPassword"] != $scope.employee.reEnterPassword)
					$scope.passwordDoesnotMatch = true;
				else
					{
					
						var emp = {
								"employeeId" : employeeId,
								"ptPassword" : $scope.employee.newPassword
						};
						  restServices.changePassword(emp, function(data){
							  $scope.clearAllMessages();
							  if(!data.error)
							  {
								$scope.changePasswordSuccess = true;  
							  }
							  else
								$scope.changePasswordError = true;  
						  });
					}
			}	
			else
				$scope.invalidOTPMessage = true;
			
		});
	}
	
});	


function demoMatchClick(inputtxt) {
	var letters =/^[a-z0-9]+$/i;
	var val = inputtxt.value.replace(/\s/g, '');
	  if (val.match(letters)) {
	document.getElementById("message").value = "Valid";


	  } else {
	   document.getElementById("message").value = "Only Alphanumeric";

	  }
	};
	function demoMatchClick1(inputtxt) {
	var letters = /^[a-z0-9]+$/i;
	var val = inputtxt.value.replace(/\s/g, '');
	  if (val.match(letters)) {
	document.getElementById("message1").value = "Valid";


	  } else {
	   document.getElementById("message1").value = "Only Alphanumeric";

	  }
	};
	function demoMatchClick2(inputtxt) {
	var letters = /^[a-z0-9]+$/i;
	var val = inputtxt.value.replace(/\s/g, '');
	  if (val.match(letters)) {
	document.getElementById("message2").value = "Valid";


	  } else {
	   document.getElementById("message2").value = "Only Alphanumeric";

	  }
	}
