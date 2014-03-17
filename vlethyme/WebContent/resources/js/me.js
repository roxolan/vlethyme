require(['require','jquery', 'bootstrap', 'angular', 'angular-route', 'vle.core'], function(require, $, bootstrap, angular) {
	
	

		
	
	
	var meApp = angular.module('meApp', ['ngRoute']);
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
	 
	meApp.controller('GroupsController', ['$scope', '$routeParams',
	  function($scope, $routeParams) {
	    $scope.createGroup = function() {
	    	$('#creategroup-modal').modal({
                'backdrop': 'static'
            });
            $('#creategroup-modal').on('shown', function () {
                // Set focus to the group name field
                $('#creategroup-name', $rootel).focus();
            });
	    };
		
	  }]);
	
	require(['domReady!'], function (document) {
		angular.bootstrap(document, ['meApp']);
	});
	
	var baseUrl = '/me';
	var $rootel = $('#');
	
	
});


