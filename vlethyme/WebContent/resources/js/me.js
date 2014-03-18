require(['require','jquery', 'bootstrap', 'angular', 'angular-route', 'ng-bootstrap', 'vle.core', 'vle.group'], function(require, $, bootstrap, angular, ngRoute, ngBootstrap, vleCore, vleGroup) {
	
	var vle = {};
	vle.group = vleGroup;
	
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
	                      controller: 'CalendarController'
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
	}]);
	
	require(['domReady!'], function (document) {
		angular.bootstrap(document, ['meApp']);
	});
	
	var baseUrl = '/me';
	var $rootel = $('#');
	
	
});


