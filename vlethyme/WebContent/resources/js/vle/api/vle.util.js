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
                $elem.addClass('vle-threedots');

                var originalData = $elem.data('originalData');
                if (originalData.resourceType) {
                    // Prepend a thumbnail to the item to add to the list
                    var $thumbnail = $('<div>').addClass('vle-thumbnail icon-vle-' + originalData.resourceType);
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
    
    /*!
     * All functionality related to validating forms
     */
    var validation = exports.validation = function() {

        /**
         * Initialize the validation utility functions by adding some custom validators
         * to jquery.validate
         *
         * @api private
         */
        var init = function() {
            // Don't allow the field to have more than 1000 characters
            $.validator.addMethod('maxlength-short', function(value, element) {
                return $.trim(value.length) <= 1000;
            });

            // Don't allow the field to have more than 10000 characters
            $.validator.addMethod('maxlength-medium', function(value, element) {
                return $.trim(value.length) <= 10000;
            });

            // Don't allow the field to have more than 100000 characters
            $.validator.addMethod('maxlength-long', function(value, element) {
                return $.trim(value.length) <= 100000;
            });

            // Don't allow spaces in the field
            $.validator.addMethod('nospaces', function(value, element) {
                return this.optional(element) || (value.indexOf(' ') === -1);
            });

            // Prepends http if no protocol has been provided
            $.validator.addMethod('prependhttp', function(value, element) {
                if ($.trim(value) !== '' && value.substring(0,7) !== 'http://' && value.substring(0,6) !== 'ftp://' && value.substring(0,8) !== 'https://') {
                    $(element).val('http://' + value);
                }
                return true;
            });
        };

        /**
         * Validate a form using the jquery.validate plugin. This will automatically style the error messages, as well as positioning
         * them appropriately and giving all of the required aria roles for accessibility. This function is mostly just a wrapper around
         * jquery.validate, and supports all of the options supported by jquery.validate (see http://bassistance.de/jquery-plugins/jquery-plugin-validation/)
         *
         * In order for forms to have the appropriate validation styles, each label and control should be wrapped in an element with a `control-group` class.
         * The label should have a `control-label` class. All input fields should be accompanied by a label, mostly for accessibility purposes.
         * More information on creating forms (including horizontal forms) can be found at http://twitter.github.com/bootstrap/base-css.html#forms
         *
         * Validation messages will by default be displayed underneath the input field. If a custom position for the validation needs to provided,
         * a placeholder element with the class `help` should be created inside of the `control-group` element.
         *
         * Metadata can be added directly onto the HTML fields to tell jquery.validate which validation rules to use. These should be added as a class onto
         * the input field. The available ones are:
         *
         * - `required`: Makes the element always required.
         * - `email`: Makes the element require a valid email.
         * - `number`: Makes the element require a decimal number.
         * - `url`: Makes the element require a valid url.
         * - `date`: Makes the element require a date.
         * - `dateISO`: Makes the element require a ISO date.
         * - `creditcard`: Makes the element require a creditcard number.
         *
         * Example:
         *
         * ```
         * <form id='form_id' role='main'>
         *      <div class='control-group'>
         *          <label for='firstName' class='control-label'>__MSG__FIRSTNAME__</label>
         *          <input type='text' maxlength='255' id='firstName' name='firstName' class='required' placeholder='Hiroyuki'/>
         *      </div>
         *      <div class='control-group'>
         *          <label for='lastName' class='control-label'>__MSG__LASTNAME__</label>
         *          <span class="help"></span>
         *          <input type='text' maxlength='255' id='lastName' name='lastName' class='required' placeholder='Sakai'/>
         *      </div>
         * </div>
         * ```
         *
         * All other validation configuration should be passed into the options object when calling `oae.api.util.validation().validate($form, options)`.
         *
         * OAE defines the additional validation methods:
         *
         * - `nospaces`: Makes the element require no spaces.
         * - `prependhttp`: Prepends http:// to a URL field if no protocal has been specified.
         *
         * @param  {Element|String}     $form                           jQuery form element or jQuery selector for that form which we want to validate
         * @param  {Object}             [options]                       JSON object containing options to pass to the to the jquery validate plugin, as defined on http://docs.jquery.com/Plugins/Validation/validate#options
         * @param  {Object}             [options.methods]               Extension to the jquery validate options, allowing to specify custom validators. The keys should be the validator identifiers. The value should be an object with a method key containing the validator function and a text key containing the validation message
         */
        var validate = function($form, options) {
            if (!$form) {
                throw new Error('A valid form should be provided');
            }
            // Make sure the form is a jQuery element
            $form = $($form);

            options = options || {};

            // We need to first handle the invalid and submit callback inside of this function, in order to set/remove all of the necessary
            // styles and aria attributes. Therefore, we cache these callback so they can be  called after those functions have finished
            var invalidCallback = null;
            if (options.invalidHandler && $.isFunction(options.invalidHandler)) {
                invalidCallback = options.invalidHandler;
            }
            var submitCallback = null;
            if (options.submitHandler && $.isFunction(options.submitHandler)) {
                submitCallback = options.submitHandler;
            }

            // Register the custom validation methods
            if (options.methods) {
                $.each(options.methods, function(key, value) {
                    $.validator.addMethod(key, value.method, value.text);
                });
            }

            // We register the submit handler. This will be called when the overall form
            // validation has succeeded
            options.submitHandler = function($thisForm, validator) {
                // We clear all the old validation styles
                clear($form);
                // Call the cached invalid handler callback
                if (submitCallback) {
                    submitCallback($thisForm, validator);
                }
                return false;
            };

            // We register the invalid handler. This will be called once when the overall
            // form validation has failed
            options.invalidHandler = function($thisForm, validator) {
                // We clear all the old validation styles
                clear($form);
                // Call the cached invalid handler callback
                if (invalidCallback) {
                    invalidCallback($thisForm, validator);
                }
            };

            // Function that will be called when an invalid form field should be marked
            // as invalid. In that case, we add an `error` class to the parent `control-group`
            // element
            options.highlight = function($element) {
                $($element).parents('.control-group').addClass('error');
            };

            // Function that will be called when a form field should be marked no longer
            // needs to be marked as invalid. In that case, we remove the `error` class from
            // the parent `control-group` element
            options.unhighlight = function($element) {
                $($element).parents('.control-group').removeClass('error');
            };

            // We register the error placement handler. This will be called for each field that
            // fails validation and will be used to customize the placement of the validation messages
            options.errorPlacement = options.errorPlacement || function($error, $element) {
                // Set the id on the validation message and set the aria-invalid and aria-describedby attributes
                $error.attr('id', $element.attr('name') + '-error');
                $element.attr('aria-invalid', 'true');
                $element.attr('aria-describedby', $element.attr('name') + '-error');
                // Set a class on the error message so it can be easily deleted again
                $error.addClass('vle-error');
                // Check if an error message placehold has been provided. If not, we default
                // to a `help-block` display and insert it after the input field
                var $helpPlaceholder = $('.help', $element.parents('.control-group'));
                if ($helpPlaceholder.length === 0) {
                    $error.addClass('help-block');
                    $error.insertAfter($element);
                } else {
                    $helpPlaceholder.append($error);
                }
            };

            // Set up the form with the provided options in jquery.validate
            $form.validate(options);
        };

        /**
         * Clear the validation on a form. This will remove all visible validation styles, as well as the aria roles.
         *
         * @param  {Element|String}     $form       jQuery form element or jQuery selector for that form for which we want to clear the validation
         * @throws {Error}                          Error thrown when no form has been provided
         */
        var clear = function($form) {
            if (!$form) {
                throw new Error('A valid form should be provided');
            }
            // Make sure the form is a jQuery element
            $form = $($form);
            // The Bootstrap `error` class will be set on the element that has the `control-group` class.
            // When clearing validation, we remove this `error` class. We also remove the actual error
            // messages from the dom
            $form.find('.vle-error').remove();
            $form.find('.error').removeClass('error');
            // When a field is invalid, the aria-invalid attribute on the field will be set to true, and
            // the aria-describedby attribute will be set to point to the validation message. When clearing
            // validation, we remove both of these
            $form.find('*[aria-invalid]').removeAttr('aria-invalid');
            $form.find('*[aria-describedby]').removeAttr('aria-describedby');
        };

        return {
            'init': init,
            'validate': validate,
            'clear': clear
        };
    };
});
