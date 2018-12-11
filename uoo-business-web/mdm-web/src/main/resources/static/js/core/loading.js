function Loading () {
  var self = {
    $loadingMask: null,
    loadingMaskDom: null,
    spinner: null,
    spinnerArray: [],
    screenMaskEnable: function (pageId) {
      var $dialog = $('#' + pageId)
      self.$loadingMask = $dialog.find('.loadingMask')
      if (self.$loadingMask.length == 0) {
        self.loadingMaskDom = "<div class='loadingMask'></div>"
        $(self.loadingMaskDom).appendTo($dialog)
      }

      self.$loadingMask = $dialog.find('.loadingMask')

      var $spinner = $dialog.find('.spinner')
      if ($spinner.length == 0) {
        var opts = {
          lines: 10, // The number of lines to draw
          length: 0, // The length of each line
          width: 10, // The line thickness
          corners: 1,
          radius: 25, // The radius of the inner circle
          color: '#409eff', // #rbg or #rrggbb
          speed: 0.8, // Rounds per second
          trail: 40, // Afterglow percentage
          shadow: false, // Whether to render a shadow
        }
        self.spinner = new Spinner(opts).spin(self.$loadingMask.get(0))
        self.spinnerArray[pageId] = self.spinner
        self.spinner = null
      }
      else {
        self.spinnerArray[pageId].spin(self.$loadingMask.get(0))
      }
    },
    screenMaskDisable: function (pageId) {
      for (var tempPageId in self.spinnerArray) {
        if(tempPageId == pageId) {
          self.spinnerArray[tempPageId].spin();
          delete self.spinnerArray[tempPageId];
        }
      }

      self.$loadingMask = $('#' + pageId).find('.loadingMask');

      if (self.$loadingMask!= null) {
        self.$loadingMask.hide();
      }
    }
  }

  return self
}