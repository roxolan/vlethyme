require(['require','jquery', 'bootstrap', 'angular', 'angular-route', 'ng-bootstrap', 'vle.core', 'vle.group'], function(require, $, bootstrap, angular, ngRoute, ngBootstrap, vleCore, vleGroup) {
	
	var vle = {};
	vle.group = vleGroup;
	
	var groupApp = angular.module('groupApp', ['ngRoute', 'ui.bootstrap']);
	groupApp.config(['$routeProvider',
	              function($routeProvider) {
	                $routeProvider.
	                  when('/activity/:groupId', {
	                    templateUrl: 'group/activity',
	                    controller: 'ActivityController'
	                  }).
	                  when('/library/:groupId', {
	                    templateUrl: 'group/library',
	                    controller: 'LibraryController'
	                  }).
	                  when('/discussions/:groupId', {
	                      templateUrl: 'group/discussions',
	                      controller: 'DiscussionsController'
	                  }).
	                  when('/members/:groupId', {
	                      templateUrl: 'group/members',
	                      controller: 'MembersController'
	                  }).
	                  otherwise({
	                    redirectTo: '/activity'
	                  });
	              }]);
	
	groupApp.controller('ActivityController', ['$scope', '$http',
	  function ($scope, $http) {
	}]);
	
	groupApp.controller('LibraryController', ['$scope', '$http',
 	  function ($scope, $http) {
 	}]);
	groupApp.controller('DiscussionsController', ['$scope', '$http',
 	  function ($scope, $http) {
 	}]);
	
	groupApp.controller('MembersController', ['$scope', '$modal', '$routeParams', '$rootScope',
	  function($scope, $modal, $routeParams, $rootScope) {
		$scope.groupId =  $routeParams.groupId;
		
		$scope.getGroup = function() {
	    	vle.group.getGroup($scope.groupId, function(error, data) {
	    		$scope.$apply(function() {
	    			if (!error) {
	    				$rootScope.group = data;
	    			} else {
	    				alert("Error occured while getting the group");
	    			}
	    		});
	    	});
	    };
	    $scope.getGroup();
		
		
		$scope.getMembers = function() {
	    	vle.group.getMembers($scope.groupId, function(error, data) {
	    		$scope.$apply(function() {
	    			if (!error) {
	    				$scope.members = data;
	    			} else {
	    				alert("Error occured while getting the group members");
	    			}
	    		});
	    	});
	    };
	    //$scope.getMembers();
	    
		$scope.addMembers = function() {
			var modalInstance = $modal.open({
				templateUrl : 'AddMembersTemplate',
				controller : 'AddMembersController',
				resolve : {
					groupId : function() {
						return $scope.groupId;
					}
				}
			});
			modalInstance.result.then(function(status) {
				if(status && status == "added") {
					$scope.getMembers();
				}
			});
		};
	}]);
	
	groupApp.controller('AddMembersController', ['$scope', 'groupId', '$modalInstance', function($scope, groupId, $modalInstance) {
		$scope.member = {};
		
		$scope.addMembers = function() {
			vle.group.addMember(groupId, $scope.member, function(error, data) {
	    		$scope.$apply(function() {
	    			if (!error) {
	    				$modalInstance.close("added");
	    			} else {
	    				alert("Error occured while adding the member!");
	    			}
	    		});
	    	});
		};
	}]);
	
	require(['domReady!'], function (document) {
		angular.bootstrap(document, ['groupApp']);
	});
});


