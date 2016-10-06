pigTrax.controller('addPremisesCtrl', function($scope, $http, $window, $modalInstance,premisesData, restServices) {	
	$scope.premisesData = premisesData;
	$scope.edit = false;
	$scope.add ={};
	$scope.alertVisible = false;
	$scope.sowSourceRequired = false;
	$scope.alertMessage;
	//$scope.country=[{"name":"US","value":"US"},{"name":"UK","value":"UK"},{"name":"Germany","value":"Germany"}];
	//$scope.cityJSON={"US":[{"name":"BOSTON","value":"BOS"}],"UK":[{"name":"LONDON","value":"LON"}],"Germany":[{"name":"Moscow","value":"mOS"}]};
	$scope.country;
	$scope.cityJSON;
	$scope.city;
	$scope.add.companyId = premisesData.companyId;
	$scope.generatedCompanyId = premisesData.generatedCompanyId;
	console.log(premisesData);
	$scope.premiseOtherCityBox = false;
	 $scope.getCountryList = function(){		 
			restServices.getCityCountryList(function(data){
			console.log("getCityCountryList :" +data);
					 if(!data.error)
					 {
						$scope.country = data.payload[0];
						var temp = data.payload[1];
						$scope.cityJSON = temp[0];			
						if(premisesData != null && premisesData.permiseId !=null )
						{
							$scope.city = $scope.cityJSON[$scope.add.state];
							$scope.add.city = premisesData.city;
							if($scope.add.city.toUpperCase()==="OTHERS")
							{
							   $scope.premiseOtherCityBox = true;
							}
							else
							{
							  $scope.premiseOtherCityBox = false
							}
						}
					 }
				});
				
			};
	
			 $scope.getCountryList();
			 
	$scope.getPremiseTypes = function()
	{
		restServices.getPremiseTypes(function(data){
			if(!data.error)
			{
				var responseList = data.payload;
				$scope.premiseTypeKeys = responseList[0];
				$scope.premiseTypeKeyValues = responseList[1];
			}
		});
	}
	
	$scope.getPremiseTypes();
	
	
	if(premisesData != null && premisesData.permiseId !=null )
	{
		$scope.getCountryList();
		$scope.edit = true;
		$scope.add.permiseIdEdit = premisesData.permiseId,
		$scope.add.permiseId = 1,
    	$scope.add.name = premisesData.name,
    	$scope.add.address = premisesData.address,
		$scope.add.state = premisesData.state;
		$scope.add.zipcode = premisesData.zipcode;
       	$scope.add.active = premisesData.active;		
		//$scope.city = $scope.cityJSON[$scope.add.state];
		//$scope.add.city = premisesData.city;
		$scope.add.id = premisesData.id
		$scope.add.premiseTypeId = premisesData.premiseTypeId
		$scope.add.sowSource = premisesData.sowSource;		
		$scope.add.otherCity = premisesData.otherCity;
		$scope.add.lactationLengthInWeeks = premisesData.lactationLengthInWeeks
		
	}
	
	$scope.addPremise = function() {
		console.log($scope.premisesAddForm	.$valid);
		var sowSource = null;
		if(document.getElementById("rad1").checked)
			sowSource = document.getElementById("rad1").value;
		else if(document.getElementById("rad2").checked)
			sowSource = document.getElementById("rad2").value;
		
		if(sowSource == null)
		{
			  $scope.sowSourceRequired = true;
			  return;
		}
		else
			$scope.sowSourceRequired = false;
		
		if(($scope.add.premiseTypeId == 1 || $scope.add.premiseTypeId == 6 || $scope.add.premiseTypeId == 8) && ($scope.add.lactationLengthInWeeks == null || $scope.add.lactationLengthInWeeks == 0))
			{
			$scope.lactationLengthRequired = true;
			return;
			}
		else
			 $scope.lactationLengthRequired = false;
		
		if($scope.premisesAddForm.$valid)
			{			
				var postParam;
				if( premisesData != null && premisesData.permiseId !=null )
				{
				console.log();
					postParam = {
							"permiseId" : premisesData.permiseId,
							"name" : $scope.add.name,
							"address" : $scope.add.address,
							"state" : $scope.add.state,
							"city" : $scope.add.city,
							"zipcode" : $scope.add.zipcode,
							"active" : true,
							"id" : $scope.add.id,
							"companyId" : premisesData.generatedCompanyId,
							"gpsLatittude" : $scope.add.gpsLatittude,
							"gpsLongitude" : $scope.add.gpsLongitude,
							"premiseTypeId" : $scope.add.premiseTypeId,
							"sowSource" : sowSource,
							"otherCity" : $scope.add.otherCity,
							"lactationLengthInWeeks" : $scope.add.lactationLengthInWeeks,
					};
				}
				else
				{
					postParam = {
							"permiseId" : premisesData.companyId +''+$scope.add.permiseId,
							"name" : $scope.add.name,
							"address" : $scope.add.address,
							"state" : $scope.add.state,
							"city" : $scope.add.city,
							"zipcode" : $scope.add.zipcode,
							"active" : true,
							"id" : $scope.add.id,
							"companyId" : premisesData.generatedCompanyId,
							"gpsLatittude" : $scope.add.gpsLatittude,
							"gpsLongitude" : $scope.add.gpsLongitude,
							"premiseTypeId" : $scope.add.premiseTypeId,
							"sowSource" : sowSource,
							"otherCity" : $scope.add.otherCity,
							"lactationLengthInWeeks" : $scope.add.lactationLengthInWeeks,
					};
				}
				console.log(postParam);
				var res = $http.post('rest/premises/insertPremisesRecord', postParam);
				res.success(function(data, status, headers, config) {
					if(data.statusMessage==="SUCCESS")
					{
						$modalInstance.close(data);					
						return data;
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
			};
	}
	
	$scope.cancel = function(){
		$modalInstance.dismiss('add');
	}
	
	$scope.changeCity = function(){	
		$scope.city = $scope.cityJSON[$scope.add.state];
	}
	
	$scope.selectOtherCity = function(){	
		if($scope.add.city.toUpperCase()==="OTHERS")
		{
		   $scope.premiseOtherCityBox = true;
		}
		else
		{
		  $scope.premiseOtherCityBox = false
		  $scope.add.otherCity = "";
		}
	}
});

