var pigTrax = angular.module('pigTrax',['ngResource', 'ui.bootstrap',,'smart-table','rorymadden.date-dropdowns','ngCookies','ngMask','angularjs-dropdown-multiselect']);

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
} ]).directive('angularValidator',
    function() {
        return {
            restrict: 'A',
            link: function(scope, element, attrs, fn) {

                // This is the DOM form element
                var DOMForm = angular.element(element)[0];
                console.log(DOMForm.name);
                // This is the the scope form model
                // All validation states are containted here
                var scopeForm = scope[DOMForm.name];

                // Set the default submitted state to false
                scopeForm.submitted = false;

                // Intercept and handle submit events of the form
                element.on('submit', function(event) {
                    event.preventDefault();
                    scope.$apply(function() {
                        scopeForm.submitted = true;
                    });

                    // If the form is valid then call the function that is declared in the angular-validator-submit atrribute on the form element
                    if (scopeForm.$valid && DOMForm.attributes["angular-validator-submit"] != undefined) {
                        scope.$eval(DOMForm.attributes["angular-validator-submit"].value);
                    }
                });

                // Setup watches on all form fields 
                setupWatches(DOMForm);


                // Iterate through the form fields and setup watches on each one
                function setupWatches(formElement) {
                    for (var i = 0; i < formElement.length; i++) {
                        // This ensures we are only watching form fields
                        if (i in formElement) {
                            setupWatch(formElement[i]);
                        }
                    }
                }


                // Setup $watch on a single formfield
                function setupWatch(elementToWatch) {
                    scope.$watch(function() {
                            // We are watching both the value of the element, the value of form.submitted, the validity of the element and the $dirty property of the element
                            // We need to watch $dirty becuase angular will somtimes run $dirty checking after the watch functions have fired the first time.
                            // Adding the four items together is a bit of a trick
                            return elementToWatch.value + scopeForm.submitted + checkElementValididty(elementToWatch) + getDirtyValue(scopeForm[elementToWatch.name]); 
                        },
                        function() {
                            updateValidationMessage(elementToWatch);
                            updateValidationClass(elementToWatch);
                        });
                }


                // Returns the $dirty value of the element if it exists
                function getDirtyValue(element) {
                    if (element) {
                        if ("$dirty" in element) {
                            return element.$dirty;
                        }
                    }
                }



                function checkElementValididty(element) {
                    // If element has a custom validation function
                    if ("validator" in element.attributes) {
                        // Call the custom validator function
                        var isElementValid = scope.$eval(element.attributes.validator.value);
                        scopeForm[element.name].$setValidity("angularValidator", isElementValid);
                        return isElementValid;
                    }
                }


                // Adds and removes an error message as a sibling element of the form field
                // depending on the validity of the form field and the submitted state of the form.
                // Will use default message if a custom message is not given
                function updateValidationMessage(element) {

                    var defaultRequiredMessage = function() {
                        return "<i class='fa fa-times'></i> Required";
                    };
                    var defaultInvalidMessage = function() {
                        return "<i class='fa fa-times'></i> Invalid";
                    };

                    // Make sure the element is a form field and not a button for example
                    // Only form elements should have names. 
                    if (!(element.name in scopeForm)) {
                        return;
                    }

                    var scopeElementModel = scopeForm[element.name];

                    // Only add/remove validation messages if the form field is $dirty or the form has been submitted
                    if (scopeElementModel.$dirty || scope[element.form.name].submitted) {

                        // Remove all validation messages 
                        var validationMessageElement = isValidationMessagePresent(element);
                        if (validationMessageElement) {
                            validationMessageElement.remove();
                        }

                        if (scopeElementModel.$error.required) {
                        	// If there is a custom required message display it
                            if ("required-message" in element.attributes) {
                                angular.element(element).after(generateErrorMessage(element.attributes['required-message'].value));
                            }
                            // Display the default require message
                            else {
                                angular.element(element).after(generateErrorMessage(defaultRequiredMessage));
                            }
                        } else if (!scopeElementModel.$valid) {
                            // If there is a custom validation message add it
                            if ("invalid-message" in element.attributes) {
                                angular.element(element).after(generateErrorMessage(element.attributes['invalid-message'].value));
                            }
                            // Display the default error message
                            else {
                                angular.element(element).after(generateErrorMessage(defaultInvalidMessage));
                            }
                        }
                    }
                }


                function generateErrorMessage(messageText) {
                    return "<label style='color:red' class='control-label has-error validationMessage'>" + scope.$eval(messageText) + "</label>";
                }


                // Returns the validation message element or False
                function isValidationMessagePresent(element) {
                    var elementSiblings = angular.element(element).parent().children();
                    for (var i = 0; i < elementSiblings.length; i++) {
                        if (angular.element(elementSiblings[i]).hasClass("validationMessage")) {
                            return angular.element(elementSiblings[i]);
                        }
                    }
                    return false;
                }


                // Adds and removes .has-error class to both the form element and the form element's parent
                // depending on the validity of the element and the submitted state of the form
                function updateValidationClass(element) {

                    // Make sure the element is a form field and not a button for example
                    // Only form fields should have names. 
                    if (!(element.name in scopeForm)) {
                        return;
                    }
                    var formField = scopeForm[element.name];

                    // Only add/remove validation classes if the field is $dirty or the form has been submitted
                    if (formField.$dirty || scope[element.form.name].submitted) {
                        if (formField.$valid) {
                            angular.element(element.parentNode).removeClass('has-error');

                            // This is extra for users wishing to implement the .has-error class on the field itself
                            // instead of on the parent element. Note that Bootstrap requires the .has-error class to be on the parent element
                            angular.element(element).removeClass('has-error');
                        } else if (formField.$invalid) {
                            angular.element(element.parentNode).addClass('has-error');

                            // This is extra for users wishing to implement the .has-error class on the field itself
                            // instead of on the parent element. Note that Bootstrap requires the .has-error class to be on the parent element
                            angular.element(element).addClass('has-error');
                        }
                    }
                }

            }
        };
    }
).directive("formatDate", function(){
	  return {
		   require: 'ngModel',
		    link: function(scope, elem, attr, modelCtrl) {
		      modelCtrl.$formatters.push(function(modelValue){
		        return new Date(modelValue);
		      })
		    }
		  }
		})
 .directive('bootstrapSwitch', [
        function() {
            return {
                restrict: 'A',
                require: '?ngModel',
                link: function(scope, element, attrs, ngModel) {
                    element.bootstrapSwitch();

                    element.on('switchChange.bootstrapSwitch', function(event, state) {
                        if (ngModel) {
                            scope.$apply(function() {
                                ngModel.$setViewValue(state);
                            });
                        }
                    });

                    scope.$watch(attrs.ngModel, function(newValue, oldValue) {
						alert("old : "+oldValue+"/ new : "+newValue);
                        if (newValue) {
                            element.bootstrapSwitch('state', true, true);
                        } else {
                            element.bootstrapSwitch('state', false, true);
                        }
                    });
                }
            };
        }
    ])
.directive('myReset', function(){
	  return {
		     restrict: 'A',
		    /* scope: {
		       text: '='
		     },*/
		     link: function(scope, element, attrs){
		       scope.changeText = function(){
		          var DOMForm = angular.element(element)[0];

		                // This is the the scope form model
		                // All validation states are containted here
		                 var scopeForm = scope[DOMForm.name];

		                // Set the default submitted state to false
		               // scopeForm.submitted = false;
		                 for (var i = 0; i < DOMForm.length; i++) {
		                        // This ensures we are only watching form fields
		                        if (i in DOMForm) {
		                            var element1 = DOMForm[i];
		                            ////ankush
		                        if (!(element1.name in scopeForm)) {
		                                continue;
		                         }
		                    var formField = scopeForm[element1.name];

		                   /*  var elementSiblings = angular.element(element1).parent().children();
		                    for (var j = 0; i < elementSiblings.length; j++) {
		                        if (angular.element(elementSiblings[j]).hasClass("validationMessage")) {
		                            angular.element(elementSiblings[j]).remove();
		                        }
		                    }*/
		                     var elementSiblings = angular.element(element1).parent().children();
		                    for (var k = 0; k < elementSiblings.length; k++) {
		                        if (angular.element(elementSiblings[k]).hasClass("validationMessage")) {
		                            angular.element(elementSiblings[k]).remove();
		                        }
		                    }
		                    scope[element1.form.name].submitted = false;
							scope[element1.form.name].$dirty = false;

		                    // Only add/remove validation classes if the field is $dirty or the form has been submitted
		                    if (scope[element1.form.name].submitted) {
		                        
		                            angular.element(element1.parentNode).removeClass('has-error');

		                            // This is extra for users wishing to implement the .has-error class on the field itself
		                            // instead of on the parent element. Note that Bootstrap requires the .has-error class to be on the parent element
		                            angular.element(element1).removeClass('has-error');
		                       
		                    }     /////ankush
		                            
		                        }
		                    }
		       };
		     },
		  };
		})
		.directive('ngEnter', function () {
		    return function (scope, element, attrs) {		    	
		        element.bind("keydown keypress", function (event) {
		            if(event.which === 13) {
		                scope.$apply(function (){
		                    scope.$eval(attrs.ngEnter);
		                });
		 
		                event.preventDefault();
		            }
		        });
		    };
		})
		.filter('teatsrange', function(){
		    return function(n) {
		      var res = [];
		      for (var i = 0; i < n; i++) {
		        res.push(i);
		      }
		      return res;
		    };
		  }).filter('newline', function($sce) {
			    return function(text) {
					if(text != null)
					{		
						var finalStr = "";
						var words = text.split(" "); 
						var i = 0; 
						if(words.length > 1)
						{
						while(i<words.length)
						{
						  for(j = i;j < words.length && j<i+10; j++)
							  {
							  finalStr += " "+words[j];
							  }
						  finalStr += "<br/>";
						  i+=10;
						}
						}
						else
							finalStr = text;
					}
			        return $sce.trustAsHtml(finalStr);
			    }
		  });

