
  var orgId = getQueryString('id');
  var engine, remoteHost, template, empty, selectNode;

  $.support.cors = true;

  remoteHost = 'http://134.96.253.221:11100';
  template = Handlebars.compile($("#result-template").html());
  empty = Handlebars.compile($("#empty-template").html());

  engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'orgName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    // prefetch: remoteHost + '/demo/prefetch',
    remote: {
      url: remoteHost + '/org/getOrgPage?orgRootId=1&search=%QUERY',
      wildcard: '%QUERY',
      filter: function (response) {
        // console.log('response', response)
        return response.data.records;
      }
    }
  });

  // ensure default users are read on initialization
  engine.get('1090217586', '58502284', '10273252', '24477185')

  function engineWithDefaults(q, sync, async) {
    if (q === '') {
      sync(engine.get('1090217586', '58502284', '10273252', '24477185'));
      async([]);
    }

    else {
      engine.search(q, sync, async);
    }
  }

  $('#demo-input').typeahead({
    hint: $('.typeahead-hint'),
    menu: $('.typeahead-menu'),
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
      suggestion: template,
      empty: empty
    }
  })
  .on('typeahead:asyncrequest', function() {
    $('.Typeahead-spinner').show();
  })
  .on('typeahead:asynccancel typeahead:asyncreceive', function() {
    $('.Typeahead-spinner').hide();
  });

  // typeahead获取选中的节点
  $('#demo-input').bind('typeahead:select', function(ev, suggestion) {
    selectNode = suggestion;
  });

  $('#addBtn').on('click', function () {
     var url = 'add.html?id=' + orgId;
     $(this).attr('href', url);
  })

  function cancel () {
    var url = "list.html?id=" + orgId;
    window.location.href = url;
  }
