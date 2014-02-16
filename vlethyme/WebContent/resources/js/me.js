/**
 * 
 */

var baseUrl = '/me';

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