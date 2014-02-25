var baseUrl = '/me';
var $rootel = $('#');

var setUpNavigation = function() {
	$(document).on('click', '#lhnavigation-navigation ul li', function(ev) {
		alert("it clicks!");
	});
};

setUpNavigation();
