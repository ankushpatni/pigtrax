pigTrax.factory("companyServices", function($modal) {
    var companyRow;
    return {
    	openCompanyAdd: function () {
    		var modalInstance = $modal.open ({
    			templateUrl: 'addCompany',
    			controller: 'addCompanyCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
				resolve:{
    				companyData : function(){
    					return null;
    				}
    			}
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			return res;
    		});
    	},
    	editCompanyData: function(companyRow){
		var modalInstance = $modal.open ({
    			templateUrl: 'addCompany',
    			controller: 'addCompanyCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window',
    			resolve:{
    				companyData : function(){
    					return companyRow;
    				}
    			}
    		});    		
    		modalInstance.result.then( function(res) {    			
    			return res;
    		});
    	},
    };
});