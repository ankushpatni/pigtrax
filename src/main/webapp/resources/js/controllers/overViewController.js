pigTrax.controller('overViewController', function($scope, $http, $window,$modal, restServices) {	
	$scope.companyId = 0;
	var localCompany ;
	$scope.mentaoryField = false;
	
	$scope.loadPremises = function(comapnyId,dataStatus)
	{
		
		if(comapnyId === undefined )
		{
			localCompany = $scope.selectedCompany;
		}
		else
		{
			localCompany  = comapnyId;
		}
		var res = $http.get('rest/premises/getPremisesList?generatedCompanyId='+localCompany+'&premisesType=null');
		res.success(function(data, status, headers, config) {
			$scope.premiseList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
		$scope.companyId = localCompany;
		$scope.pigId = '';
		$scope.selectedPremise === '';
		
		if(dataStatus == "true")
			$scope.searchDataErrorMessage = true;
		else
			$scope.searchDataErrorMessage = false;
	}
	
	$scope.loadPigInfo = function()
	{
		$scope.pigId = '';
		var res = $http.get('rest/entryEvent/getPigInfoList?companyId='+localCompany+'&premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
		console.log(data.payload);
			$scope.pigInfoList = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	

		var res = $http.get('rest/groupEvent/getGroupEventByPremiseWithoutStatus?premiseId='+$scope.selectedPremise);
		res.success(function(data, status, headers, config) {
			$scope.groupEventListSearch = data.payload;
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + {data: data});
		});	
	}

	        
    $scope.searchSowHistory = function()
    {
			$scope.pigInfo = {};
			
			if($scope.selectedPremise === '' || $scope.selectedPremise === undefined ||
				$scope.companyId === '' || $scope.companyId === undefined || 
				$scope.pigId === '' || $scope.pigId === undefined )
			{
				$scope.mentaoryField = true;
				return true;
			}
			else
			{	
				$scope.mentaoryField = false;
			}
			var searchPigInfo = {
					searchText : $scope.pigId,
					searchOption : 'pigId',
					companyId : $scope.companyId,
					selectedPremise : $scope.selectedPremise
			};
			restServices.getPigInformationWithOutStatus(searchPigInfo, function(data)
			{
				if(!data.error){
					$scope.searchDataErrorMessagePig = false;
					document.getElementById("companyId1").value	= $scope.companyId;	
					document.forms['overViewForm'].action='generateReportSow';
					document.forms['overViewForm'].submit();
				}
				else
				{
					$scope.searchDataErrorMessage = true;
					
				}
			});
    }

	$scope.searchGroupHistory = function()
		{
				$scope.pigInfo = {};
				
				if($scope.selectedPremise === '' || $scope.selectedPremise === undefined ||
					$scope.companyId === '' || $scope.companyId === undefined || 
					$scope.groupId === '' || $scope.groupId === undefined)
				{
					$scope.mentaoryField = true;
					return true;
				}
				else
				{	
					$scope.mentaoryField = false;
				}
				
				$scope.pigInfo = {};
				var searchPigInfo = {
						groupId : $scope.groupId,
						companyId : $scope.companyId,
						premiseId : $scope.selectedPremise
				};
				restServices.getGroupEventInformation(searchPigInfo, function(data)
				{
					if(!data.error){
						$scope.searchDataErrorMessageGroup = false;
						document.getElementById("companyId1").value	= $scope.companyId;		
						document.forms['overViewForm'].action='generateReportGroup';
						document.forms['overViewForm'].submit();
					}
					else
					{
						$scope.searchDataErrorMessage = true;
						
					}
				});
		}
	
	  $scope.generateReport = function(reportType)
	    {

				$scope.mentaoryField = false;
				$scope.prmisesSelect = false;
				$scope.pigSelect = false;
				$scope.groupSelect = false;
				$scope.startSelect = false;
				$scope.endSelect = false;
				$scope.searchDataErrorMessage = false;
				
			if($scope.selectedPremise === '' || $scope.selectedPremise === undefined ||
					$scope.companyId === '' || $scope.companyId === undefined )
			{
				$scope.mentaoryField = true;
				$scope.prmisesSelect = true;
				return true;
			}
			else
			{	
				$scope.mentaoryField = false;
			}
			
			//
			
			
			 if(reportType!= undefined && reportType==="SowCardReport" && pigId != null)
			    {
			    }
			    else if(reportType!= undefined && reportType==="SowReport" && ( $scope.pigId === '' || $scope.pigId === undefined))
			    {
					$scope.mentaoryField = true;
					$scope.pigSelect = true;
					return true;
			    }
			    else if(reportType!= undefined && reportType==="GroupReport" && ( $scope.groupId === '' || $scope.groupId === undefined))
			    {
					$scope.mentaoryField = true;
					$scope.groupSelect = true;
					return true;
			    }
			    else if(reportType!= undefined && reportType==="SaleReport" && 
					($scope.endDate === '' || $scope.endDate === undefined || 
						$scope.startDate === '' || $scope.startDate === undefined))
			    {
					$scope.mentaoryField = true;
					$scope.startSelect = true;
					$scope.endSelect = true;
					return true;
			    }
			    else if(reportType!= undefined && reportType==="RemovalReport" && 
					($scope.endDate === '' || $scope.endDate === undefined || 
						$scope.startDate === '' || $scope.startDate === undefined))
			    {
					$scope.mentaoryField = true;
					$scope.startSelect = true;
					$scope.endSelect = true;
					return true;
			    }
			    			    
			    else if(reportType!= undefined && reportType==="LacationReport" && 
					($scope.endDate === '' || $scope.endDate === undefined || 
						$scope.startDate === '' || $scope.startDate === undefined))
			    {
					$scope.mentaoryField = true;
					$scope.startSelect = true;
					$scope.endSelect = true;
					return true;
				}
			    else if(reportType!= undefined && reportType==="Litterbalance" &&
						($scope.endDate === '' || $scope.endDate === undefined || 
						$scope.startDate === '' || $scope.startDate === undefined))
			    {
					$scope.mentaoryField = true;
					$scope.startSelect = true;
					$scope.endSelect = true;
					return true;	
			    }
			    else if(reportType!= undefined && reportType==="TargetReport" &&
				($scope.startDate === '' || $scope.startDate === undefined))
			    {
					$scope.mentaoryField = true;
					$scope.startSelect = true;
					return true;	
			    }
			    else if(reportType!= undefined && reportType==="ProductionLogReport" && 
				($scope.endDate === '' || $scope.endDate === undefined || 
						$scope.startDate === '' || $scope.startDate === undefined))
			    {
					$scope.mentaoryField = true;
					$scope.startSelect = true;
					$scope.endSelect = true;
					return true;
				 }
			
			
			// mandatory parameter
			$scope.searchDataErrorMessagePig = false;
			document.getElementById("companyId1").value	= $scope.companyId;				
			document.getElementById('reportType').value=reportType;

			document.forms['overViewForm'].action='generateOverViewReport';
			document.forms['overViewForm'].submit();
				
	    }
    
    $scope.getCompanyList = function(){
    	
    		restServices.getCompanyList(function(data){
			console.log(data);
    			 if(!data.error)
    			 {
    				$scope.companyMapList = data.payload;
    			 }
    		});
    	};
    	
	$scope.getCompanyList();
	
});