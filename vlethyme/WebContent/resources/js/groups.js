define(['jquery', 'vle.core', 'vle.group', 'vle.util', 'angular'], function($, vle, vleGroup, vleUtil, angular) {
	
	var vle = {};
	vle.util = vleUtil;
	vle.group = vleGroup;
	
	angular.module('app').controllerProvider.register('GroupsController', ['$scope', '$route', '$routeParams', '$rootScope', '$modal', '$http', 'parameters',
    	  function ($scope, $route, $routeParams, $rootScope, $modal, $http, parameters) {
			
			$scope.parameters = parameters;
		
			$scope.getGroups = function() {
			    	if (parameters.userId) {
						vle.group.getUserAllGroups($scope.parameters.userId, function(error, data) {
				    		$scope.$apply(function() {
				    			if (!error) {
				    				$scope.groups = data;
				    			} else {
				    				alert("Error occured while getting the groups");
				    			}
				    		});
				    	});
			    	} else {
			    		vle.group.getMyGroups(function(error, data) {
				    		$scope.$apply(function() {
				    			if (!error) {
				    				$scope.groups = data;
				    			} else {
				    				alert("Error occured while getting the groups");
				    			}
				    		});
				    	});
			    	}
			    };
			    $scope.getGroups();
			    
				$scope.createGroup = function() {
					var modalInstance = $modal.open({
						templateUrl : 'CreateGroupTemplate',
						controller : 'CreateGroupController',
						resolve : {
							
						}
					});
					modalInstance.result.then(function(status) {
						if(status && status == "added") {
							$scope.getGroups();
						}
					});
				};
		  }
	]);
	
	angular.module('app').controllerProvider.register('CreateGroupController', ['$scope', '$modalInstance', function($scope, $modalInstance) {
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
});