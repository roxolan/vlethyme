/**
 * jQuery plugin that will detect the clips that are present on the page, and
 * will take care of opening/collapsing them when clicked. When a user clicks
 * outside of an opened clip, the clip will be collapsed as well.
 */

define(['exports', 'jquery'], function (exports, jQuery) {
    (function($) {

        /**
         * Toggle clip visibility
         *
         * @param  {Object}  $clip  jQuery-wrapped clip to toggle
         */
    	var closeClip = exports.closeClip = function($clip) {
            // Toggle the clip options
            $('ul', $clip).hide();
            // Toggle the caret icons
            $('i.icon-caret-up', $clip).addClass('icon-caret-down');
            $('i.icon-caret-up', $clip).removeClass('icon-caret-up');
        };
        
    	var toggleClip = exports.toggleClip = function($clip) {
            // Toggle the clip options
            $('ul', $clip).toggle();
            // Toggle the caret icons
            $('i.icon-caret-down, i.icon-caret-up', $clip).toggleClass('icon-caret-down icon-caret-up');
        };

        // Hook all clicks on document to close clips as appropriate
        $(document).on('click', function(ev) {
            // No changes to underlying clips if user is interacting with a modal
            if ($('.modal.in').length || $(ev.target).parents('.modal').length) {
                return;
            }

            // Get any clips that were target of click
            var $clip = $(ev.target).parents('.vle-clip-content');

            // If target clip allows actions and target was clip's button or one of
            // its children, toggle the clip
            if (($('i.icon-caret-down, i.icon-caret-up', $clip).length > 0) &&
                (($(ev.target).is('.vle-clip-content > button:not(:disabled)')) ||
                 ($(ev.target).parents('.vle-clip-content > button:not(:disabled)').length > 0))) {
                toggleClip($clip);
            }

            // Close any other open clips on page
            $('.vle-clip-content').has('ul:visible').not($clip).each(function() {
                toggleClip($(this));
            });
        });
    })(jQuery);
});
