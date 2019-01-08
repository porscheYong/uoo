var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var engine;
var empty;

empty = Handlebars.compile($(".typeahead-menu").html());

$('#psnName').typeahead({
    hint: $('.typeahead-hint'),
    menu: $('.user-table'),
    minLength: 0,
    highlight:true,
    classNames: {
        open: 'is-open',
        empty: 'is-empty',
        cursor: 'is-active',
        suggestion: 'Typeahead-suggestion',
        selectable: 'Typeahead-selectable'
    }
}, {
    source: function(){
        // console.log(a,b,c,d)
        // initOrgPersonnelTable(checked,$("#psnName").val());
    },
    displayKey: 'orgName',
    templates: {
        suggestion: empty
    }
})
  .on('typeahead:change', function(event) {
      $('.Typeahead-spinner').show();
      initOrgPersonnelTable(checked,$("#psnName").val());
    })
  .on('typeahead:asynccancel', function() {
        $('.Typeahead-spinner').hide();
    });

Handlebars.registerHelper('eq', function(v1, v2, opts) {
    if(v1 == v2){
        return opts.fn(this);
    }
    else
        return opts.inverse(this);
});

Handlebars.registerHelper("addOne", function (index) {
    return index + 1;
});

