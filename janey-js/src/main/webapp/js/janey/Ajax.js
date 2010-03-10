dojo.provide("janey.Ajax");

dojo.require("janey._base");

dojo.declare("janey.Ajax", null, {
	_baseUrl:"/janey/Core",
	
	CONTENT_TYPE: "application/json",
	
	post:function(json) {
		var self = this;
		dojo.xhrPost({
			url: self._baseUrl,
			handleAs:"json",
			contentType:self.CONTENT_TYPE,
			content: {json:json},
			handle: function(response, ioArgs) {
				console.log("HTTP status code: ", ioArgs.xhr.status);
				self.handleResponse(response);
				return response;
			},
			error: function(response, ioArgs) {
				self.handleError(response);
				return response;
			}
		});
	},
	
	handleResponse:function(json) {
		if ( json ) {
			janey.alert("[janey.Ajax]Unhandled json response");
		}
	},
	
	handleError:function(response) {
	}
});