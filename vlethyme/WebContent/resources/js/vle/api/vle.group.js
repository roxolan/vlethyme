define(['exports', 'jquery'], function(exports, $) {
	
	var createGroup = exports.createGroup = function (group, callback) {
        if (!group || !group.name) {
             throw new Error('A group displayName should be provided');
        }

        // Set a default callback function in case no callback function has been provided
        callback = callback || function() {};


        $.ajax({
            'url': '/group/createGroup',
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
    
	var getMyGroups = exports.getMyGroups = function(callback) {
		$.ajax({
            'url': '/group/mygroups',
            'type': 'GET',
            'success': function(data) {
                callback(null, data);
            },
            'error': function(jqXHR, textStatus) {
                callback({'code': jqXHR.status, 'msg': jqXHR.responseText});
            }
        });
	};
	
	var getGroup = exports.getGroup = function(groupId, callback) {
		$.ajax({
            'url': '/group/getGroupById',
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
});
