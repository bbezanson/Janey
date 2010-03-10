dojo.provide("janey.data.Response");

dojo.declare("janey.data.Response", null, {
	// summary:
	//		layer class that sits on top of the ajax/xhr code
	//		designed to be passed back from the janey.data.Request
	//		you do not instantiate one of these, the request will
	//		create one for you and pass it back as the response.
	_request:null,
	
	_json:null,
	
	constructor:function(request, json) {
		// summary:
		//		base c-tor
		this._request = request;
		this._json = json;
	},
	
	getRequest:function() {
		// summary:
		//		accessor for the original request object
		return this._request;
	},
	
	getJson:function() {
		// summary:
		// 		accessor for the received json response
		return this._json;
	}
});