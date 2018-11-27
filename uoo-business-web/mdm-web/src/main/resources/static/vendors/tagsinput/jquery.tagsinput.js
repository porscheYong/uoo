/*

  jQuery Tags Input Plugin 1.3.3

  Copyright (c) 2011 XOXCO, Inc

  Documentation for this plugin lives here:
  http://xoxco.com/clickable/jquery-tags-input

  Licensed under the MIT license:
  http://www.opensource.org/licenses/mit-license.php

  ben@xoxco.com

*/

(function($) {

    var delimiter = new Array();
    var tagslist;
    var tags_callbacks = new Array();
    $.fn.doAutosize = function(o){
        var minWidth = $(this).data('minwidth'),
            maxWidth = $(this).data('maxwidth'),
            val = '',
            input = $(this),
            testSubject = $('#'+$(this).data('tester_id'));

        if (val === (val = input.val())) {return;}

        // Enter new content into testSubject
        var escaped = val.replace(/&/g, '&amp;').replace(/\s/g,' ').replace(/</g, '&lt;').replace(/>/g, '&gt;');
        testSubject.html(escaped);
        // Calculate new width + whether to change
        var testerWidth = testSubject.width(),
            newWidth = (testerWidth + o.comfortZone) >= minWidth ? testerWidth + o.comfortZone : minWidth,
            currentWidth = input.width(),
            isValidWidthChange = (newWidth < currentWidth && newWidth >= minWidth)
                || (newWidth > minWidth && newWidth < maxWidth);

        // Animate width
        if (isValidWidthChange) {
            input.width(newWidth);
        }


    };
    $.fn.resetAutosize = function(options){
        // alert(JSON.stringify(options));
        var minWidth =  $(this).data('minwidth') || options.minInputWidth || $(this).width(),
            maxWidth = $(this).data('maxwidth') || options.maxInputWidth || ($(this).closest('.tagsinput').width() - options.inputPadding),
            val = '',
            input = $(this),
            testSubject = $('<tester/>').css({
                position: 'absolute',
                top: -9999,
                left: -9999,
                width: 'auto',
                fontSize: input.css('fontSize'),
                fontFamily: input.css('fontFamily'),
                fontWeight: input.css('fontWeight'),
                letterSpacing: input.css('letterSpacing'),
                whiteSpace: 'nowrap'
            }),
            testerId = $(this).attr('id')+'_autosize_tester';
        if(! $('#'+testerId).length > 0){
            testSubject.attr('id', testerId);
            testSubject.appendTo('body');
        }

        input.data('minwidth', minWidth);
        input.data('maxwidth', maxWidth);
        input.data('tester_id', testerId);
        input.css('width', minWidth);
    };

    $.fn.addTag = function(value,options) {
        options = jQuery.extend({focus:false,callback:true},options);
        this.each(function() {
            var id = $(this).attr('id');
            tagslist = value;

            if (options.unique) {
                var skipTag = $(this).tagExist(value);
                if(skipTag == true) {
                    //Marks fake input as not_valid to let styling it
                    // $('#'+id+'_tag').addClass('not_valid');
                }
            } else {
                var skipTag = false;
            }
            if (value == '') {
                $('#'+id+'_tagsinput').addClass('not_valid');
            } else  {
                $('#'+id+'_tagsinput').removeClass('not_valid');
            }
            $(this).val(value);
            var val = '';
            if (value !='' && skipTag != true) {
                for (var i = 0; i < value.length; i++) {
                    var name = value[i].name || value[i].orgTypeName || value[i].refName || value[i].positionName || value[i].postName;
                    var tagId = value[i].id;
                    val += value[i].name || value[i].orgTypeName;
                    $('<span>').addClass('tag').attr('tagId', tagId).append(
                        $('<span>').text(name).append('&nbsp;&nbsp;')
                    ).insertBefore('#' + id + '_addTag');
                }
            }

        });

        return false;
    };

    $.fn.removeTag = function(e) {
        var value = $(e.target).parent().attr('tagId');
        this.each(function() {
            var id = $(this).attr('id');

            $('#'+id+'_tagsinput .tag').remove();
            for (i=0; i< tagslist.length; i++) {
                if (tagslist[i].id === value) {
                    tagslist.splice(i, 1)
                }
            }

            $.fn.tagsInput.importTags(this);

            if (tags_callbacks[id] && tags_callbacks[id]['onRemoveTag']) {
                var f = tags_callbacks[id]['onRemoveTag'];
                f.call(this, value);
            }
        });

        return false;
    };

    $.fn.tagExist = function(val) {
        var id = $(this).attr('id');
        var tagslist = $(this).val().split(delimiter[id]);
        return (jQuery.inArray(val, tagslist) >= 0); //true when tag exists, false when not
    };

    // clear all existing tags and import new ones from a string
    $.fn.importTags = function(str) {
        var id = $(this).attr('id');
        $('#'+id+'_tagsinput .tag').remove();
        $.fn.tagsInput.importTags(this,str);
    }

    $.fn.tagsInput = function(options) {
        var settings = jQuery.extend({
            interactive:true,
            defaultText:'add a tag',
            minChars:0,
            width:'auto',
            height:'auto',
            minHeight: '40px',
            autocomplete: {selectFirst: false },
            hide:true,
            delimiter: ',',
            unique:true,
            removeWithBackspace:true,
            placeholderColor:'#666666',
            autosize: true,
            comfortZone: 20,
            inputPadding: 6*2
        },options);

        var uniqueIdCounter = 0;

        this.each(function() {
            // If we have already initialized the field, do not do it again
            if (typeof $(this).attr('data-tagsinput-init') !== 'undefined') {
                return;
            }

            // Mark the field as having been initialized
            $(this).attr('data-tagsinput-init', true);

            if (settings.hide) {
                // $(this).hide();
            }
            var id = $(this).attr('id');
            if (!id || delimiter[$(this).attr('id')]) {
                id = $(this).attr('id', 'tags' + new Date().getTime() + (uniqueIdCounter++)).attr('id');
            }

            var data = jQuery.extend({
                pid:id,
                real_input: '#'+id,
                holder: '#'+id+'_tagsinput',
                input_wrapper: '#'+id+'_addTag',
                fake_input: '#'+id+'_tag'
            },settings);

            delimiter[id] = data.delimiter;

            if (settings.onAddTag || settings.onRemoveTag || settings.onChange) {
                tags_callbacks[id] = new Array();
                tags_callbacks[id]['onAddTag'] = settings.onAddTag;
                tags_callbacks[id]['onRemoveTag'] = settings.onRemoveTag;
                tags_callbacks[id]['onChange'] = settings.onChange;
            }

            var markup = '<div id="'+id+'_tagsinput" class="tagsinput"><div id="'+id+'_addTag">';

            $(markup).insertAfter(this);

            $(data.holder).css('width',settings.width);
            $(data.holder).css('min-height',settings.minHeight);
            $(data.holder).css('height',settings.height);
            var a = $(data.real_input).val()
            if ($(data.real_input).val()!='') {
                $.fn.tagsInput.importTags($(data.real_input),$(data.real_input).val());
            }
        });

        return this;

    };



    $.fn.tagsInput.importTags = function(obj, tagslist) {
        $(obj).addTag(tagslist,{focus:false,callback:false});
    };

    $.fn.getTags = function(obj) {
        return tagslist;
    }
})(jQuery);
