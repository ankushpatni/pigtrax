pigTrax.service("DateUtils", function($resource) {
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
});
