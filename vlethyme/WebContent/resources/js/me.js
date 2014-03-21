require(['require','jquery', 'bootstrap', 'angular', 'angular-route', 'ng-bootstrap', 'vle.core', 'vle.util', 'vle.group', 'vle.user', 'jquery.validate'], function(require, $, bootstrap, angular, ngRoute, ngBootstrap, vleCore, vleUtil, vleGroup, vleUser) {
	
	var vle = {};
	vle.util = vleUtil;
	vle.group = vleGroup;
	vle.user = vleUser;
	
	vle.util.validation().init();
	
	var meApp = angular.module('meApp', ['ngRoute', 'ui.bootstrap']);
	meApp.config(['$routeProvider',
	              function($routeProvider) {
	                $routeProvider.
	                  when('/dashboard', {
	                    templateUrl: 'me/dashboard',
	                    controller: 'DashboardController'
	                  }).
	                  when('/groups', {
	                    templateUrl: 'me/groups',
	                    controller: 'GroupsController'
	                  }).
	                  when('/courses', {
	                      templateUrl: 'me/courses',
	                      controller: 'CoursesController'
	                  }).
	                  when('/calendar', {
	                      templateUrl: 'me/calendar',
	                      controller: 'CalendarController'
	                  }).
	                  when('/content', {
	                      templateUrl: 'me/content',
	                      controller: 'ContentController'
	                  }).
	                  when('/discussions', {
	                      templateUrl: 'me/discussions',
	                      controller: 'DiscussionsController'
	                  }).
	                  when('/network', {
	                      templateUrl: 'me/network',
	                      controller: 'NetworkController'
	                  }).
	                  when('/users', {
	                      templateUrl: 'me/users',
	                      controller: 'UsersController'
	                  }).
	                  otherwise({
	                    redirectTo: '/dashboard'
	                  });
	              }]);
	
	
	
	 
	
	meApp.controller('DashboardController', ['$scope', '$http',
	  function ($scope, $http) {
	    
	    $scope.orderProp = 'age';
	  }]);
	 
	meApp.controller('GroupsController', ['$scope', '$modal',
	  function($scope, $modal) {
	    $scope.getMyGroups = function() {
	    	vle.group.getMyGroups(function(error, data) {
	    		$scope.$apply(function() {
	    			if (!error) {
	    				$scope.groups = data;
	    			} else {
	    				alert("Error occured while getting the groups");
	    			}
	    		});
	    	});
	    };
	    $scope.getMyGroups();
	    
		$scope.createGroup = function() {
			var modalInstance = $modal.open({
				templateUrl : 'CreateGroupTemplate',
				controller : 'CreateGroupController',
				resolve : {
					
				}
			});
			modalInstance.result.then(function(status) {
				if(status && status == "added") {
					$scope.getMyGroups();
				}
			});
		};
	}]);
	
	meApp.controller('CreateGroupController', ['$scope', '$modalInstance', function($scope, $modalInstance) {
		$scope.group = {visibility : 3};
		$scope.changeVisibility = function(val) {
			$scope.group.visibility = val;
		};
		
		$scope.createGroup = function() {
			vle.group.createGroup($scope.group, function(error, data) {
	    		$scope.$apply(function() {
	    			if (!error) {
	    				$modalInstance.close("added");
	    			} else {
	    				alert("Error occured while creating the group!");
	    			}
	    		});
	    	});
		};
		
		/**
         * Initializes the create group form and validation
         */
        var setUpValidation = function() {
            vle.util.validation().validate($('#creategroup-form', $rootel), {
                'submitHandler': createGroup
            });
        };
        setTimeout(setUpValidation, 0);
	}]);
	
	require(['domReady!'], function (document) {
		angular.bootstrap(document, ['meApp']);
	});
	
	meApp.controller('UsersController', ['$scope', '$modal',
     function($scope, $modal) {
		$scope.getUsers = function() {
	    	vle.user.getUsers(function(error, data) {
	    		$scope.$apply(function() {
	    			if (!error) {
	    				$scope.users = data;
	    			} else {
	    				alert("Error occured while getting the users");
	    			}
	    		});
	    	});
	    };
	    
	    $scope.getUsers();
	    
	    $scope.createUser = function() {
			var modalInstance = $modal.open({
				templateUrl : 'CreateUserTemplate',
				controller : 'CreateUserController',
				resolve : {
					
				}
			});
			modalInstance.result.then(function(status) {
				if(status && status == "added") {
					$scope.getUsers();
				}
			});
		};
	}]);
	
	meApp.controller('CreateUserController', ['$scope', '$modalInstance', function($scope, $modalInstance) {
		
		$rootel ="#createuser-modal";
		
		$scope.user = {role : {id:1}};
		$scope.changeRole = function(val) {
			$scope.user.role.id = val;
		};
		
		$scope.createUser = function() {
			vle.user.createUser($scope.user, function(error, data) {
	    		$scope.$apply(function() {
	    			if (!error) {
	    				$modalInstance.close("added");
	    			} else {
	    				alert("Error occured while creating the user!");
	    			}
	    		});
	    	});
		};
		
		var isUserNameAvailable = function(username) {
			var available = true;
			vle.user.isUserNameAvailable(username, function(error, data) {
	    		if (!error) {
	    				available = data;
	    			} else {
	    				available = true; 
	    			}
	    	});
			return available;
        };
        
		/**
         * Set up the validation on the register form, including the error messages
         */
        var setUpValidation = function() {
            var validateOpts = {
                'rules': {
                    'username': {
                        'minlength': 3,
                        'nospaces': true,
                        'validchars': true,
                        'usernameavailable': true
                    },
                    'password': {
                        'minlength': 6
                    },
                    'confirmPassword': {
                        'equalTo': '#createuser-confirmpassword'
                    }
                },
                'messages': {
                    'firstName': {
                        'required': 'Please enter first name'
                    },
                    'lastName': {
                        'required': 'Please enter last name'
                    },
                    'email': {
                        'required': 'Please enter a valid email address',
                        'email': 'This is an invalid email address'
                    },
                    'username': {
                        'required': 'Please enter your username',
                        'minlength': 'Username should be atleast three characters long',
                        'nospaces': 'Username should not contain spaces'
                    },
                    'password': {
                        'required': 'Please enter your password',
                        'minlength': 'Your password should be atleast 6 characters long',
                    },
                    'confirmPassword': {
                        'required': 'Please repeat your password',
                        'passwordmatch': 'This password does not match the first one'
                    }
                },
                'methods': {
                    'validchars': {
                        'method': function(value, element) {
                            return this.optional(element) || !(/[<>\\\/{}\[\]!@#\$%\^&\*,:]+/i.test(value));
                        },
                        'text': 'Username contains an invalid character'
                    },
                    'usernameavailable': {
                        'method': function(value, element) {
                            var available = false;
                            available = isUserNameAvailable(value);
                            if (available) {
                            	$('#register-username-available', $rootel).removeClass('icon-remove').addClass('icon-ok');
                            } else {
                            	$('#register-username-available', $rootel).removeClass('icon-ok').addClass('icon-remove');
                            }
                            return available;
                        },
                        'text': 'This username has already been taken'
                    }
                },
                'submitHandler': function() {$scope.$apply(
                						function(){$scope.createUser();}
                				 );}
            };
            vle.util.validation().validate($('#createuser-form', $rootel), validateOpts);
        };
        setTimeout(setUpValidation, 0);
	}]);
	
	
});


