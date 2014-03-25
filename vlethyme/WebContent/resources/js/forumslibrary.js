define(['jquery', 'vle.core', 'vle.forum', 'vle.util', 'angular'], function($, vle, vleForum, vleUtil, angular) {
	
	var vle = {};
	vle.util = vleUtil;
	vle.forum = vleForum;
	
	
	angular.module('app').controllerProvider.register('ForumsLibraryController', ['$scope', '$route', '$routeParams', '$rootScope', '$modal', '$http', 'parameters',
    	  function ($scope, $route, $routeParams, $rootScope, $modal, $http, parameters) {
			
			$scope.parameters = parameters;
		
			$scope.getForums = function() {
			    	if (parameters.userId) {
						vle.forum.getUserAllForums($scope.parameters.userId, function(error, data) {
				    		$scope.$apply(function() {
				    			if (!error) {
				    				$scope.forums = data;
				    			} else {
				    				alert("Error occured while getting the forums");
				    			}
				    		});
				    	});
			    	} else {
			    		vle.forum.getMyForums(function(error, data) {
				    		$scope.$apply(function() {
				    			if (!error) {
				    				$scope.forums = data;
				    			} else {
				    				alert("Error occured while getting the forums");
				    			}
				    		});
				    	});
			    	}
			    };
			    $scope.getForums();
			    
				$scope.startForum = function() {
					var modalInstance = $modal.open({
						templateUrl : 'StartForumTemplate',
						controller : 'StartForumController',
						resolve : {
							
						}
					});
					modalInstance.result.then(function(status) {
						if(status && status == "added") {
							$scope.getForums();
						}
					});
				};
		  }
	]);
	
	angular.module('app').controllerProvider.register('StartForumController', ['$scope', '$modalInstance', function($scope, $modalInstance) {
		
		var $rootel = "#creatediscussion-modal";
		$scope.view = "overview";
		$scope.forum = {visibility : 3};
		$scope.changeVisibility = function(val) {
			$scope.forum.visibility = val;
		};
		
		$scope.changeView = function(view) {
			$scope.view = view;
		}
		
		$scope.startForum = function() {
			vle.forum.createForum($scope.forum, function(error, data) {
	    		$scope.$apply(function() {
	    			if (!error) {
	    				$modalInstance.close("added");
	    			} else {
	    				alert("Error occured while creating the forum!");
	    			}
	    		});
	    	});
		};
		
		$scope.updatePermissions = function() {
			if (!$scope.forum.forumMembers) {
            	$scope.forum.forumMembers = [];
            }
			$scope.forum.forumMembers = getAutosuggestSelection();
			$scope.changeView("overview");
		};
		
		/**
         * Initializes the create forum form and validation
         */
        var setUpValidation = function() {

        	vle.util.validation().validate($('#creatediscussion-form', $rootel), {
            	'submitHandler': function() {$scope.$apply(
						function(){$scope.startForum();}
				 );}
            });
        };
        setTimeout(setUpValidation, 0);
        
        /////////////////
        // AUTOSUGGEST //
        /////////////////

        /**
         * Disable/enable the add button when an item has been added/removed from the autosuggest field.
         */
        var autoSuggestChanged = function() {
            //$('#manageaccess-share-update', $rootel).prop('disabled', getAutosuggestSelection().length ? false : true);
        };
        
		/**
         * Initializes the autosuggest used for sharing with other users or groups.
         */
        var setUpAutoSuggest = function() {
            vle.util.autoSuggest().setup($('#setpermissions-share', $rootel), {
                'selectionChanged': autoSuggestChanged,
                'url': '/user/findUsersByNameLike'
                
            }, null, function() {
                // Focus on the autosuggest field once it has been set up
                //focusAutoSuggest();
            });
        };
        
        /**
         * Focus on the autosuggest field used for sharing with other users or groups after the
         * autosuggest component has finished initializing and after the modal dialog has finished
         * loading.
         */
        var focusAutoSuggest = function() {
            // Only focus the autosuggest field when the share panel is showing
            if ($('#setpermissions-share', $rootel).is(':visible')) {
                vle.util.autoSuggest().focus($('#setpermissions-share', $rootel));
            }
        };
        
		var getAutosuggestSelection = function() {
            // Convert these into an object that reflects the members feed, using a `profile` property
            // for the principal profile and a `role` property for the new role
            var selectedItems = [];
            $.each(vle.util.autoSuggest().getSelection($rootel), function(index, selectedItem) {
                selectedItems.push({
                	'member': {displayName : selectedItem.displayName},
                	'user': {displayName : selectedItem.displayName,  id : selectedItem.id}
                });
            });
            return selectedItems;
        };
        
      //execute this after template has been rendered
        setTimeout(setUpAutoSuggest, 0);
	}]);
});