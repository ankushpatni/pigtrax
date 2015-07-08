pigTrax.service('sharedProperties', function () {
        var companyID;
        return {
            getProperty: function () {
                return companyID;
            },
            setProperty: function(value) {
                companyID = value;
            }
        };
    });