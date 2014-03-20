/*!
 * Load all of the 3rd party libraries that need to be present from the very beginning, as well as the actual
 * core client-side OAE APIs
 */
define([
        'jquery',
        'bootstrap',
        
        'jquery.clip'
    ],

    function($) {
        $.ajaxSetup({
            // Make caching the default behavior for $.getScript
            'cache': true,
            'complete': function(xhr, textStatus) {
                
            }
        });
        // Make sure that arrays passed in as arguments are properly encoded
        $.ajaxSettings.traditional = true;
        // Tell IE9 that cross-domain requests are a possibility when Amazon S3 is enabled
        // as the content storage strategy
        $.support.cors = true;

        var vle= {};
        return vle;
    }
);
