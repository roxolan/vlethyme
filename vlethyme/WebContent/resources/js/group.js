require(['require','jquery', 'bootstrap', 'angular', 'angular-route', 'ng-bootstrap', 'vle.core', 'vle.group', 'vle.util'], function(require, $, bootstrap, angular, ngRoute, ngBootstrap, vleCore, vleGroup, vleUtil) {
	
	var vle = {};
	vle.group = vleGroup;
	vle.util = vleUtil;
	
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
	    				$scope.getGroupMembers();
	    			} else {
	    				alert("Error occured while getting the group");
	    			}
	    		});
	    	});
	    };
	    $scope.getGroup();
		
		
		$scope.getGroupMembers = function() {
	    	vle.group.getGroupMembers($scope.groupId, function(error, data) {
	    		$scope.$apply(function() {
	    			if (!error) {
	    				$rootScope.group.groupUsers = data;
	    			} else {
	    				alert("Error occured while getting the group members");
	    			}
	    		});
	    	});
	    };
	    
	    
		$scope.addMembers = function() {
			var modalInstance = $modal.open({
				templateUrl : 'AddMembersTemplate',
				controller : 'AddMembersController',
				resolve : {
					group : function() {
						return angular.copy($scope.group);
					},
					view : function() {
						return 'member';
					}
				}
			});
			modalInstance.result.then(function(status) {
				if(status && status == "added") {
					$scope.getGroupMembers();
				}
			});
		};
		
		$scope.manageAccess = function() {
			var modalInstance = $modal.open({
				templateUrl : 'AddMembersTemplate',
				controller : 'AddMembersController',
				resolve : {
					group : function() {
						return angular.copy($scope.group);
					},
					view : function() {
						return 'access';
					}
				}
			});
			modalInstance.result.then(function(status) {
				if(status && status == "added") {
					$scope.getGroupMembers();
				}
			});
		};
	}]);
	
	groupApp.controller('AddMembersController', ['$scope', 'group', 'view', '$modalInstance', function($scope, group, view, $modalInstance) {
		$scope.member = {};
		$scope.view = view;
		
		$scope.group = group;
		
		$rootel ="#manageaccess-modal";
		
		$scope.showView = function(view) {
			$scope.view = view;
		};
		
		$scope.addMember = function() {
			console.log(getAutosuggestSelection());
			addNewMembers(getAutosuggestSelection());
            showPanel('overview');
            // Disable the add button in the share view
            $('#manageaccess-share-update', $rootel).prop('disabled', true);
		}
		
		$scope.updateGroupMembers = function() {
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
		
		/////////////////
        // AUTOSUGGEST //
        /////////////////

        /**
         * Disable/enable the add button when an item has been added/removed from the autosuggest field.
         */
        var autoSuggestChanged = function() {
            $('#manageaccess-share-update', $rootel).prop('disabled', getAutosuggestSelection().length ? false : true);
        };
        
		/**
         * Initializes the autosuggest used for sharing with other users or groups.
         */
        var setUpAutoSuggest = function() {
            vle.util.autoSuggest().setup($('#manageaccess-share-autosuggest', $rootel), {
                'selectionChanged': autoSuggestChanged,
                'url': '/user/findUsersByNameLike'
                
            }, null, function() {
                // Focus on the autosuggest field once it has been set up
                focusAutoSuggest();
            });
        };
        
        /**
         * Focus on the autosuggest field used for sharing with other users or groups after the
         * autosuggest component has finished initializing and after the modal dialog has finished
         * loading.
         */
        var focusAutoSuggest = function() {
            // Only focus the autosuggest field when the share panel is showing
            if ($('#manageaccess-share', $rootel).is(':visible')) {
                vle.util.autoSuggest().focus($('#manageaccess-share', $rootel));
            }
        };
        
		var getAutosuggestSelection = function() {
            // Convert these into an object that reflects the members feed, using a `profile` property
            // for the principal profile and a `role` property for the new role
            var selectedItems = [];
            $.each(vle.util.autoSuggest().getSelection($rootel), function(index, selectedItem) {
                selectedItems.push({
                    'profile': selectedItem,
                    'role': $('#manageaccess-share-role', $rootel).val()
                });
            });
            return selectedItems;
        };
        
        /**
         * Prepends members to the list that were selected through the autosuggest.
         *
         * @param  {Object[]}    autoSuggestMembers    Trimmed member object as used by the members feed containing all properties necessary to render a list item in the infinite scroll
         */
        var addNewMembers = function(autoSuggestMembers) {
        	if (!$scope.group.groupUsers) {
            	$scope.group.groupUsers = [];
            }
        	$.each(autoSuggestMembers, function(i, newMember) {
                membersUpdates[newMember.profile.id] = newMember.role;
                var alreadyPresent = false;
                $.each($scope.group.groupUsers, function(j, groupUser) {
                	if (groupUser.id == newMember.id) {
                		alreadyPresent = true;
                		groupUser.role.id = newMember.role.id;
                	}
                	if (!alreadyPresent) {
                		$scope.group.groupUsers.push(newMember.profile);
                	}
                });
            });

            infinityScroll.prependItems({'results': autoSuggestMembers});
        };
        
        //execute this after template has been rendered
        setTimeout(setUpAutoSuggest, 0);
        
	}]);
	
	require(['domReady!'], function (document) {
		angular.bootstrap(document, ['groupApp']);
	});
});


