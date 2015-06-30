pigTrax.factory("companyServices", function($modal) {
    return {
    	openCompanyAdd: function () {
    		var modalInstance = $modal.open ({
    			templateUrl: 'addCompany',
    			controller: 'addCompanyCtrl',
    			backdrop:true,
    			windowClass : 'cp-model-window'
    		});
    		
    		modalInstance.result.then( function(res) {    			
    			return;
    		});
    	},
    };
});