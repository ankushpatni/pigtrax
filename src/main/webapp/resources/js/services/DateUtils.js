pigTrax.service("DateUtils", function($resource, $filter, $cookieStore) {
    this.convertLocaleDateToServer = function(date) {
        if (date) {          
          var utcDate = new Date();
          utcDate.setUTCDate(date.getDate());
          utcDate.setUTCMonth(date.getMonth());
          utcDate.setUTCFullYear(date.getFullYear());
          return utcDate;
        } else {
          return null;
        }
      };
      
      
      this.getServerFormat = function(date) {
    	  var d = new Date(date),
          month = '' + (d.getMonth() + 1),
          day = '' + d.getDate(),
          year = d.getFullYear();
	
	      if (month.length < 2) month = '0' + month;
	      if (day.length < 2) day = '0' + day;

	      	return [year, month, day].join('-');
        };
      
      this.getFormatedDate = function(date)
      {
    	  if (date) {
    		  if($cookieStore.get('PT_DATEFORMAT') != null && $cookieStore.get('PT_DATEFORMAT') != undefined)
    			  return $filter('date')(date,$cookieStore.get('PT_DATEFORMAT'));
    		  else
    			  return $filter('date')(date,'MM/dd/YYYY');
    	  }
      };
      
      this.getFormatedDateTime = function(date)
      {
    	  if (date) {
    		  if($cookieStore.get('PT_DATEFORMAT') != null && $cookieStore.get('PT_DATEFORMAT') != undefined)
    			  return $filter('date')(date,$cookieStore.get('PT_DATEFORMAT'+' HH:mm:ss'));
    		  else
    			  return $filter('date')(date,'MM/dd/YYYY HH:mm:ss');
    	  }
      };
      
      
      this.parse = function(dateVal, format)
      {
    	  if(dateVal == null)
    		  return null;
    	  else
    	  {
    		  var parts = dateVal.split("/");
    		  var  dateObj = new Date(parseInt(parts[2], 10), parseInt(parts[1], 10)-1, parseInt(parts[0], 10));
    		  return dateObj;
    	  }
      }
});
