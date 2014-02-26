var baseUrl = '/me';
var $rootel = $('#');

var defaultPage = function(baseUrl){
	$(document).ready(function(){
		$('#lhnavigation-navigation ul #dashboard').addClass('active');
		$('#lhnavigation-page #dashboard').show();
	});
};



var renderPage = function(element) {
	var selmenu = '#lhnavigation-navigation ul #' + element;
	var selpage = '#lhnavigation-page #' + element;
	
	$('#lhnavigation-navigation ul li').removeClass('active');
	$('#lhnavigation-page > div').hide();
	$(selpage).show();
	$(selmenu).addClass('active');
};

var setUpNavListeners = function(baseUrl) {
	$(document).on('click', '#lhnavigation-navigation ul li', function(ev) {
		if (1 == 1) {
			var elem = $(this).attr('id');
			renderPage(elem);
		}
		ev.preventDefault();
	});
};

defaultPage(baseUrl);
setUpNavListeners(baseUrl);


