pigTrax.factory('sharedProperties', function ($http) {
        var validationType;
        var phaseType;
		 	
        return {
            getValidationType: function () {
            	 var res = $http.get('rest/util/getValidationType');
         		res.success(function(data, status, headers, config) {
         			console.log(data);
         			this.validationType = data.payload;
         			
         		});
         		res.error(function(data, status, headers, config) {
         			console.log( "failure message: " + {data: data});
         		});	
                return this.validationType;
            },
            getPhaseType: function() {
			 var res1 = $http.get('rest/util/getPhaseType');
      			res1.success(function(data, status, headers, config) {
				console.log(data);
      				phaseType = data.payload;					
      			});
      			res1.error(function(data, status, headers, config) {
      				console.log( "failure message: " + {data: data});
      			});
            },
			phaseTypeData : function(){
			console.log(phaseType);
			return phaseType;
			}
        };
    });