requirejs.config({
    'baseUrl': 'static',
    'paths': {
        // jQuery module is managed by require-jquery variation of require.js
        'jquery': 'js/vendor/jquery-1.9.1.min',

        // Vendor paths
        'bootstrap': 'js/vendor/bootstrap.min',
        'angular': 'js/vendor/angular.min',
        'angular-route': 'js/vendor/angular-route.min',
        'ng-bootstrap': 'js/vendor/ui-bootstrap-tpls-0.10.0.min',
        'domReady': 'js/vendor/domReady',
        


        // VLE API modules
        'vle.core': 'js/vle/api/vle.core',
        'vle.group': 'js/vle/api/vle.group'
    },
    'priority': ['jquery'],
    // angular does not support AMD out of the box, put it in a shim
    'shim': {
    	'angular': {
    		exports: 'angular'
    	},
    	'angular-route': {
    		deps: ['angular']
    	},
    	'ng-bootstrap': {
    		deps: ['angular']
    	}
    },
    'waitSeconds': 30
});

/*!
 * Load all of the dependencies, core OAE APIs, and the page-specific javascript file (if specified)
 */
require(['vle.core'], function() {
    // Find the script that has specified both the data-main (which loaded this bootstrap script) and a data-loadmodule attribute. The
    // data-loadmodule attribute tells us which script they wish to load *after* the core APIs have been properly bootstrapped.
	var $mainScript = $('script[data-main][data-loadmodule]');
    if ($mainScript.length > 0) {
        var loadModule = $mainScript.attr('data-loadmodule');
        if (loadModule) {
            // Require the module they specified in the data-loadmodule attribute
            require([loadModule]);
        }
    }
});
