var KusaiRendererOptions = {
  mode: null,
  dataMode: null,
  htmlObject: null,
  width: null,
  height: null,
};

var KusaiRenderer = function (data, options) {
  this._options = _.extend(KusaiRendererOptions, options);
  this._hasProcessed = false;
  if (typeof Backbone !== "undefined") {
    _.extend(this, Backbone.Events);
  }
  if (data) {
    this._data = data;
    this.processData(data, options);
    this._hasProcessed = true;
  }
};

KusaiRenderer.prototype.render = function (data, options) {
  if (typeof Backbone !== "undefined") {
    this.trigger("render:start", this);
  }

  if (!this.hasProcessedData()) {
    this.processData(data, options);
  }
  var r = this._render(data, options);
  if (typeof Backbone !== "undefined") {
    this.trigger("render:end", this);
  }
  return r;
};

KusaiRenderer.prototype.processData = function (data, options) {
  if (typeof Backbone !== "undefined") {
    this.trigger("processData:start", this);
  }
  this._processData(data, options);
  if (typeof Backbone !== "undefined") {
    this.trigger("processData:end", this);
  }
};
KusaiRenderer.prototype.hasProcessedData = function () {
  return this._hasProcessed;
};

KusaiRenderer.prototype._render = function (data, options) {};
KusaiRenderer.prototype._processData = function (data, options) {};
