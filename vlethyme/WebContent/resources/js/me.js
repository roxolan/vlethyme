require(['require','jquery', 'bootstrap', 'angular', 'angular-route', 'ng-bootstrap', 'vle.core', 'vle.util', 'vle.group', 'vle.user', 'jquery.validate'], function(require, $, bootstrap, angular, ngRoute, ngBootstrap, vleCore, vleUtil, vleGroup, vleUser) {
	
	var vle = {};
	vle.util = vleUtil;
	vle.group = vleGroup;
	vle.user = vleUser;
	
	vle.util.validation().init();
	
	var app = angular.module('app', ['ngRoute', 'ui.bootstrap']);
	app.config(['$routeProvider', '$controllerProvider', '$compileProvider', '$filterProvider', '$provide', function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide) {
		
		app.controllerProvider = $controllerProvider;
		app.compileProvider    = $compileProvider;
		app.routeProvider      = $routeProvider;
		app.filterProvider     = $filterProvider;
		app.provide            = $provide;
		          
		$routeProvider.
	          when('/dashboard', {
	            templateUrl: 'me/dashboard',
	            controller: 'DashboardController'
	          }).
	          when('/groups', {
	            templateUrl: '/partials/groups',
	            controller: 'GroupsController',
	            resolve: {
		        	deps: function($q, $rootScope) {
		        		var deferred = $q.defer();
		        		var dependencies =
		                    [
		                     	'/static/js/groups.js'
		                    ];
		        		require(dependencies, function()
		        			    {
		        	        // all dependencies have now been loaded by so resolve the promise
		        	        $rootScope.$apply(function()
		        	        {
		        	            deferred.resolve();
		        	        });
		        	    });
		        		return deferred.promise;
		        	},
		        	
		        	parameters : function() { return {canManage : true};}
		        }
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
	          when('/forums', {
	            templateUrl: '/partials/forumslibrary',
	            controller: 'ForumsLibraryController',
	            resolve: {
		        	deps: function($q, $rootScope) {
		        		var deferred = $q.defer();
		        		var dependencies =
		                    [
		                     	'/static/js/forumslibrary.js'
		                    ];
		        		require(dependencies, function()
		        			    {
		        	        // all dependencies have now been loaded by so resolve the promise
		        	        $rootScope.$apply(function()
		        	        {
		        	            deferred.resolve();
		        	        });
		        	    });
		        		return deferred.promise;
		        	},
		        	
		        	parameters : function() { return {canManage : true};}
		        }
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
	
	app.controller('DashboardController', ['$scope', '$http',
	  function ($scope, $http) {
	    
	    $scope.orderProp = 'age';
	  }]);
	 
	require(['domReady!'], function (document) {
		angular.bootstrap(document, ['app']);
	});
	
	app.controller('UsersController', ['$scope', '$modal',
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
	
	app.controller('CreateUserController', ['$scope', '$modalInstance', function($scope, $modalInstance) {
		
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


