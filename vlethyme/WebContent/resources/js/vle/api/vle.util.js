define(['exports', 'require', 'jquery', 'underscore', 'jquery.autosuggest'], function(exports, require, $, _) {

	
	/**
     * All functionality related to the autosuggest component
     */
    var autoSuggest = exports.autoSuggest = function() {
    	/*!
         * Default options that will be used to supplement the provided options.
         * A list of the available options can be found at https://github.com/wuyuntao/jquery-autosuggest
         */
        var defaultOptions = {
            'canGenerateNewSelections': false,
            'minChars': 2,
            'retrieveLimit': 10,
            'url': '/api/search/general',
            'scroll': 117,
            'searchObjProps': 'id, displayName',
            'selectedItemProp': 'displayName',
            'selectedValuesProp': 'id'
        };

        /**
         * Initialize the autosuggest functionality by binding a custom listeners
         * that will be responsible for marking the autosuggest container as if it
         * were a focused input field.
         *
         * @api private
         */
        var init = function() {
            $(document).on('focus', 'ul.as-selections input', function() {
                $(this).parents('ul.as-selections').addClass('as-selections-focus');
            });
            $(document).on('focusout', 'ul.as-selections input', function() {
                $(this).parents('ul.as-selections').removeClass('as-selections-focus');
            });
        };
        
        var setup = function($element, options, resourceTypes, callback) {
        	if (!$element) {
                throw new Error('A valid input element should be provided.');
            }
        	$element = $($element);
        	if (!options.startText) {
                var placeholder = $element.attr('placeholder');
                options.startText = placeholder ? placeholder : 'Enter name here';
            }
        	// The `emptyText` is the text that will be shown when no suggested items could be found.
            // If no `emptyText` has been provided, we fall back to a default string
            if (!options.emptyText) {
                options.emptyText = 'No results found';
            }
            
            //Merge the supplied options with the default options. Default options will be overriden
            // by supplied options
            options = _.extend({}, defaultOptions, options);
            
            //Add the resourceTypes onto the additional querystring parameter that needs to be added to the request.
            // We need to do this as querystring-formatted string as the Autosuggest component is not able to deal with objects.
            if (!resourceTypes) {
                resourceTypes = ['user', 'group'];
            }
            
            options.extraParams = options.extraParams || '';
            $.each(resourceTypes, function(index, resourceType) {
                options.extraParams += '&resourceTypes=' + resourceType;
            });
            
            //By default, the autosuggest component will only show results in the suggested items that actually match the query
            // on one of the fields specified in the `searchObjProps` parameter. However, as we rely on the REST endpoint to do
            // the appropriate filtering and ordering, we undo this behavior by adding a `query` property onto each result that will
            // contain the current search query, causing all results to match and display.
            options.searchObjProps += ',query';
            
            //XSS escape the preFill items
            if (options.preFill) {
                $.each(options.preFill, function(index, preFillItem) {
                    preFillItem[options.selectedItemProp] = security().encodeForHTML(preFillItem[options.selectedItemProp]);
                });
            }

            //XSS escape the ghost items
            if (options.ghosts) {
                $.each(options.ghosts, function(index, ghostItem) {
                    ghostItem[options.selectedItemProp] = security().encodeForHTML(ghostItem[options.selectedItemProp]);
                });
            }
            
         // Function that will be called when an item is attempted to be removed from the autosuggest
            // field. We only remove the element when the element is not fixed and not a ghost. If a
            // `selectionRemoved` function has already been provided, we cache it so it can be executed
            // after this. If the item is fixed, we don't execute the cached `selectionRemoved` function
            var selectionRemoved = null;
            if (options.selectionRemoved) {
                selectionRemoved = options.selectionRemoved;
            }
            options.selectionRemoved = function(elem) {
                var isFixed = false;
                // Check if the removed element was one of the fixed elements in the preFill objects
                var originalData = $(elem).data('originalData');
                if (options.preFill) {
                    isFixed = _.some(options.preFill, function(preFilledItem) {
                        return preFilledItem.id === originalData.id && preFilledItem.fixed === true;
                    });
                }
                // Check if the removed element was one of the ghost elements in the ghosts objects
                if (!isFixed && options.ghosts) {
                    isFixed = _.some(options.ghosts, function(ghostItem) {
                        return ghostItem.id === originalData.id;
                    });
                }

                // If the item is fixed, we don't do anything
                if (!isFixed) {
                    if (selectionRemoved) {
                        selectionRemoved(elem);
                    } else {
                        elem.remove();
                    }
                    // Trigger the custom selection changed function
                    if (options.selectionChanged) {
                        options.selectionChanged();
                    }
                }
            };
            
         // Function that will be called when an item is added to the autosuggest field. We add the
            // thumbnail picure to the element. If a `selectionAdded` function has already been provided,
            // we cache it so it can be executed after this.
            var selectionAdded = null;
            if (options.selectionAdded) {
                selectionAdded = options.selectionAdded;
            }
            options.selectionAdded = function(elem) {
                var $elem = $(elem);
                // Make sure that the item cannot overflow
                $elem.addClass('oae-threedots');

                var originalData = $elem.data('originalData');
                if (originalData.resourceType) {
                    // Prepend a thumbnail to the item to add to the list
                    var $thumbnail = $('<div>').addClass('vle-thumbnail icon-oae-' + originalData.resourceType);
                    if (originalData.thumbnailUrl) {
                        $thumbnail.append($('<div>')
                            .css('background-image', 'url("' + originalData.thumbnailUrl + '")')
                            .attr('role', 'img')
                            .attr('aria-label', security().encodeForHTMLAttribute(originalData.displayName))
                        );
                    }
                    $elem.prepend($thumbnail);
                }

                if (selectionAdded) {
                    selectionAdded(elem);
                }
                // Trigger the custom selection changed function
                if (options.selectionChanged) {
                    options.selectionChanged();
                }
            };
            
         // Initialize the autoSuggest field
            var $autoSuggest = $element.autoSuggest(options.url, options);
            var $list = $autoSuggest.parents('ul');

            // Remove the delete (x) button from the fixed fields
            if (options.preFill) {
                $.each(options.preFill, function(index, preFillItem) {
                    if (preFillItem.fixed) {
                        $('li[data-value="' + preFillItem[options.selectedValuesProp] + '"]').addClass('as-fixed-item');
                    }
                });
            }
            
         // Add a label to the autosuggest input field for accessibility
            $('.as-input', $list).before('<label class="vle-aural-text" for="' + $('.as-input', $list).attr('id') + '">' + options.startText + '</label>');

            // Trigger the callback function
            if (callback) {
                callback();
            }
        };
        
        /**
         * Set the focus on the autosuggest field. This will end up setting the focus on the input field that is used
         * by the autosuggest component to enter the next item in the list
         *
         * @param  {Element|String}     $element             jQuery element or jQuery selector for the container in which the auto suggest was initialized. Note that this will *not* be the same element as the one used to setup the auto suggest.
         * @throws {Error}                                   Error thrown when no source element has been provided
         */
        var focus = function($element) {
            if (!$element) {
                throw new Error('A valid input element should be provided');
            }

            $element = $($element);
            $('.as-selections input.as-input', $element).focus();
        };
        
        var getSelection = function($element) {
            if (!$element) {
                throw new Error('An valid input element should be provided.');
            }

            $element = $($element);

            var selectedItems = [];

            // We cannot use the input.as-values field as that only gives us the IDs and we also need the other basic profile information
            $.each($element.find('.as-selections > li'), function(index, selection) {
                var $selection = $(selection);
                var id = $selection.attr('data-value');
                var selectionData = $selection.data().originalData;
                var isGhostItem = $selection.hasClass('as-ghost-item');
                // jQuery autosuggest will always prepare an empty item for the next item that needs to be
                // added to the list. Therefore, it is possible that an item in the list is empty
                if (id && selectionData) {
                    // In case this is a ghost item, we can only add it when it has been selected.
                    if (!isGhostItem || (isGhostItem && $selection.hasClass('as-ghost-selected'))) {
                        selectedItems.push({
                            'id': id,
                            'displayName': selectionData.displayName,
                            'resourceType': selectionData.resourceType,
                            'thumbnailUrl': selectionData.thumbnailUrl,
                            'visibility': selectionData.visibility
                        });
                    }
                }
            });
            return selectedItems;
        };
        
        return {
            'init': init,
            'setup': setup,
            'focus': focus,
            'getSelection': getSelection
        };
    };
});
