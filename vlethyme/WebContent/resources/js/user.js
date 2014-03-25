require(['require','jquery', 'bootstrap', 'angular', 'angular-route', 'ng-bootstrap', 'vle.core', 'vle.user', 'vle.util', 'jquery.clip'], function(require, $, bootstrap, angular, ngRoute, ngBootstrap, vleCore, vleUser, vleUtil, clip) {
	
	var vle = {};
	vle.user = vleUser;
	vle.util = vleUtil;
	
	// Get the user id from the URL. The expected URL is `/user/<userId>`.
    var userId =  window.location.pathname.split("/")[2];
    
	var app = angular.module('app', ['ngRoute', 'ui.bootstrap']);
	
	
	app.config(['$routeProvider', '$controllerProvider', '$compileProvider', '$filterProvider', '$provide', function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide) {
	    
		app.controllerProvider = $controllerProvider;
		app.compileProvider    = $compileProvider;
		app.routeProvider      = $routeProvider;
		app.filterProvider     = $filterProvider;
		app.provide            = $provide;
		
		$routeProvider.
	      when('/library', {
	        templateUrl: '/partials/contentlibrary',
	        controller: 'LibraryController',
	        resolve: {
	        	deps: function($q, $rootScope) {
	        		var deferred = $q.defer();
	        		var dependencies =
	                    [
	                        '/static/js/contentlibrary.js'
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
	        	}
	        }
	      }).
	      when('/discussions', {
	          templateUrl: '/discussions',
	          controller: 'DiscussionsController'
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
		        	
		        	parameters : function() { return {userId: userId, canManage : false};}
		        }
          }).
	      when('/network', {
	          templateUrl: '/network',
	          name : 'members',
	          controller: 'NetworkController'
	      }).
	      otherwise({
	        redirectTo: '/library'
	      });
  }]);
	
	app.controller('UserController', ['$scope', '$route', '$routeParams', '$rootScope', '$modal', '$http',
 	  function ($scope, $route, $routeParams, $rootScope, $modal, $http) {
		$scope.getUser = function(refresh) {
	    	if (refresh || !$rootScope.user) {
	    		vle.user.getUser(userId, function(error, data) {
	    			$scope.$apply(function() {
		    			if (!error) {
		    				$rootScope.user = data;
		    				if ($route.current.name == "library") {
		    					$scope.getUserLibraries();
		    				} else if ($route.current.name == "discussions") {
		    					$scope.getUserDiscussions();
		    				} else if ($route.current.name == "groups") {
		    					$scope.getUserGroups();
		    				} else if ($route.current.name == "network") {
		    					$scope.getUserNetwork();
		    				}
		    			} else {
		    				alert("Error occured while getting the user");
		    			}
		    		});
		    	});
	    	}
	    };
	    $scope.getUser();
	}]);

	require(['domReady!'], function (document) {
		angular.bootstrap(document, ['app']);
	});
});


