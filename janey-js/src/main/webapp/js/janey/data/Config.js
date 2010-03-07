dojo.provide("janey.data.Config");

dojo.require("janey._base");

dojo.requireLocalization("janey", "Messages");

dojo.declare("janey.data.Config", null, {
	_baseUrl:"/janey/Core",
	
	CONTENT_TYPE: "application/json",
	
	_messages:null,
	
	constructor:function() {
		this._messages = dojo.i18n.getLocalization("janey", "Messages");
	},
	
	save:function(data) {
		var json = dojo.toJson(dojo.mixin(data, {action:janey.actions.CREATE_CONFIG}));
		var self = this;
		dojo.xhrPost({
			url: self._baseUrl,
			handleAs:"json",
			contentType:self.CONTENT_TYPE,
			content: {json:json},
			handle: function(response, ioArgs) {
				console.log("HTTP status code: ", ioArgs.xhr.status);
				self.handlePost(response);
				return response;
			},
			error: function(response, ioArgs) {
				self.handleError(response);
				return response;
			}
		});
	},
	
	handlePost:function(json) {
		if ( json && json.status ) {
			janey.alert(this._messages[json.status], "message");
		} else {
			janey.alert("bad response from server", "error");
		}
	},
	
	handleError:function(json) {
		this.handlePost(json);
	},
	
	get:function() {
	}
});