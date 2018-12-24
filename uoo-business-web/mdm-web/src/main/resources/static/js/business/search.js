var orgId = getQueryString('id');
var orgTreeId = getQueryString('orgTreeId');
var orgName = getQueryString('name');
var refCode = getQueryString('refCode');
var engine, template, empty, selectNode;

  template = Handlebars.compile($("#result-template").html());
  empty = Handlebars.compile($("#empty-template").html());

  engine = new Bloodhound({
    identify: function(o) { return o.id_str; },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'orgName'),
    dupDetector: function(a, b) { return a.id_str === b.id_str; },
    // prefetch: remoteHost + '/demo/prefetch',
    remote: {
      url: '/org/getOrgPage?orgRootId=1&search=%QUERY',
      wildcard: '%QUERY',
      filter: function (response) {
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
     var url = 'orgAdd.html?id=' + orgId  + '&orgTreeId=' + orgTreeId + '&refCode=' + refCode + '&name=' + encodeURI(orgName);
     $(this).attr('href', url);
  })
  
  function  addTreeNode () {
      if (!selectNode) {
          parent.layer.alert("请选择一个组织", {
              skin: 'layui-layer-lan'
              ,closeBtn: 0
          });
          return;
      }
      var loading = parent.loading;
      loading.screenMaskEnable('container');
      $http.post('/orgRel/addOrgRel', JSON.stringify({
          orgRootId: orgTreeId,
          orgTreeId: orgTreeId,
          supOrgId: orgId,
          orgId: selectNode.orgId
      }), function (data) {
        var newNode = {
            name: selectNode.orgName,
            id: selectNode.orgId
        }
        parent.addNodeById(orgId, newNode);
        loading.screenMaskDisable('container');
      }, function (err) {
          loading.screenMaskDisable('container');
      })
  }

  function cancel () {
    var url = 'list.html?id=' + orgId  + '&orgTreeId=' + orgTreeId +  '&name=' + encodeURI(orgName);
    window.location.href = url;
  }

  $('#orgName').html(orgName);
parent.getOrgExtInfo();


