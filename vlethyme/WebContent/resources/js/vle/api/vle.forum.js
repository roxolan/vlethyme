define(['exports', 'jquery'], function(exports, $) {
	
	var createForum = exports.createForum = function (forum, callback) {
        if (!forum || !forum.title) {
             throw new Error('A forum title should be provided');
        }

        // Set a default callback function in case no callback function has been provided
        callback = callback || function() {};


        $.ajax({
            'url': '/forum/createForum',
            'type': 'POST',
            'data': JSON.stringify(forum),
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
    
	var getMyForums = exports.getMyForums = function(callback) {
		$.ajax({
            'url': '/forum/myforums',
            'type': 'GET',
            'success': function(data) {
                callback(null, data);
            },
            'error': function(jqXHR, textStatus) {
                callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
	};
	
	var getUserAllForums = exports.getUserAllForums = function(userId, callback) {
		$.ajax({
            'url': '/forum/getUserAllForums',
            'type': 'GET',
            'data': {userId: userId},
            'success': function(data) {
                callback(null, data);
            },
            'error': function(jqXHR, textStatus) {
                callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
	};
	
	var getForum = exports.getForum = function(forumId, callback) {
		$.ajax({
            'url': '/forum/getForumById',
            'type': 'GET',
            'data' : {forumId : forumId},
            'success': function(data) {
                callback(null, data);
            },
            'error': function(jqXHR, textStatus) {
                callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
	};
	
	var getForumMembers = exports.getForumMembers = function(forumId, callback) {
		$.ajax({
            'url': '/forum/getForumMembers',
            'type': 'GET',
            'data' : {forumId : forumId},
            'success': function(data) {
                callback(null, data);
            },
            'error': function(jqXHR, textStatus) {
                callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
	};
	
	var updateForumMembers = exports.updateForumMembers = function (forum, callback) {
		// Set a default callback function in case no callback function has been provided
        callback = callback || function() {};


        $.ajax({
            'url': '/forum/updateForumMembers',
            'type': 'POST',
            'data': JSON.stringify(forum),
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
