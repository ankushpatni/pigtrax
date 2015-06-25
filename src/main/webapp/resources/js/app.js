var pigTrax = angular.module('pigTrax',['ngResource', 'ui.bootstrap',,'smart-table']);

//directive to show a confirm prompt
pigTrax.directive('ngConfirmClick', [ function() {
	return {
		priority : -1,
		restrict : 'A',
		link : function(scope, element, attrs) {
			element.bind('click', function(e) {
				var message = attrs.ngConfirmClick;
				if (message && !confirm(message)) {
					e.stopImmediatePropagation();
					e.preventDefault();
				}
			});
		}
	}
} ]);

