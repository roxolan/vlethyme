define(['exports', 'jquery'], function(exports, $) {
	
	var createUser = exports.createUser = function (user, callback) {
        if (!user || !user.username || !user.firstName || !user.password) {
             throw new Error('Incomplete user information');
        }

        // Set a default callback function in case no callback function has been provided
        callback = callback || function() {};


        $.ajax({
            'url': '/user/createUser',
            'type': 'POST',
            'data': JSON.stringify(user),
            'contentType': "application/json",
			'dataType': "json",
            'success': function(data) {
                callback(null, data);
            },
            'error': function(jqXHR, textStatus) {
                callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
    };
    
    var isUserNameAvailable = exports.isUserNameAvailable = function(username, callback) {
        $.ajax({
            url: '/user/isUsernameAvailable',
            'type': 'GET',
            data: {username:username},
            async: false,
            success: function(data) {
            	callback(null, data);
            },
            error: function(xhr, textStatus, thrownError) {
            	callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
    };
    
	var getUsers = exports.getUsers = function(callback) {
		$.ajax({
            'url': '/user/getUsers',
            'type': 'GET',
            'success': function(data) {
                callback(null, data);
            },
            'error': function(jqXHR, textStatus) {
                callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
	};
	
	var getUser = exports.getUser = function(userId, callback) {
		$.ajax({
            'url': '/user/getUserById',
            'type': 'GET',
            'data' : {groupId : groupId},
            'success': function(data) {
                callback(null, data);
            },
            'error': function(jqXHR, textStatus) {
                callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
	};
	
	var updateUser = exports.updateUser = function (group, callback) {
		// Set a default callback function in case no callback function has been provided
        callback = callback || function() {};


        $.ajax({
            'url': '/group/updateGroupMembers',
            'type': 'POST',
            'data': JSON.stringify(group),
            'contentType': "application/json",
			'dataType': "json",
            'success': function(data) {
                callback(null, data);
            },
            'error': function(jqXHR, textStatus) {
                callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
    };
});
