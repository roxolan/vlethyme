/**
 * 
 */

var toggleClip = function($clip) {
    // Toggle the clip options
    $('ul', $clip).toggle();
    // Toggle the caret icons
    $('i.icon-caret-down, i.icon-caret-up', $clip).toggleClass('icon-caret-down icon-caret-up');
};

$(document).on('click', function(ev) {
	var $clip = $(ev.target).parents('.clip-content');
	toggleClip($clip);
});

$(document).on('click', '.vle-list-options-toggle', function() {
	
});

function login() {
	
	var username = $('#topnavigation-signin-username').val();
	var password = $('#topnavigation-signin-password').val();
	
	    	 var newUrl = 'j_spring_security_check';
	    	 $.post(newUrl,{j_username:username,j_password:password})
	    	 .success(
	    	  function(xml, status, jqxhr)
	    	  {  
	    		  var loginRedirect = jqxhr.getResponseHeader("LoginRedirect"); 
				  if(loginRedirect == 1) {
					  alert("Invalid credentials");
				  }
				  else {
	    		  window.location.assign('me'); 
			      }
			 })
	    	  .error(function(){
	    	});
}