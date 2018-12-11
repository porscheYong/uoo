var orgId = getQueryString('orgId');
var orgTreeId = getQueryString('orgTreeId');
var orgName = getQueryString('orgName');
var engine;
var empty;


empty = Handlebars.compile($(".typeahead-menu").html());

engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'acctName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    remote: {
        url: '/orgPersonRel/getUserOrgRelPage?search=%QUERY&pageNo=1&pageSize=10&orgId='+orgId+'&orgTreeId='+orgTreeId,
        wildcard: '%QUERY',
        filter: function (response) {
            if (response.data && response.data.records.length == 0)
                return;
            
        }
    }
});

function engineWithDefaults(q, sync, async) {
    if (q === '') {
        sortFlag = 0;
    }
    else {
        engine.search(q, sync, async);
    }
}

$('#acctName').typeahead({
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
    source: engineWithDefaults,
    displayKey: 'orgName',
    templates: {
        suggestion: empty
    }
})
  .on('typeahead:asyncrequest', function() {
        $('.Typeahead-spinner').show();
        sortFlag = 0;
        if($("#acctName").val() != ''){
            initMainTable(isCheck,$("#acctName").val());
        }else{
            initMainTable(isCheck,'');
        }
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

