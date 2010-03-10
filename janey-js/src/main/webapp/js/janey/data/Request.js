dojo.provide("janey.data.Request");

dojo.require("janey.data.Response");

dojo.declare("janey.data.Request", janey.Ajax, {
	// summary:
	//		this is a layer that sits on top of the ajax interface
	//		and allows you to perform simple requests and get back
	//		simple responses, asynchronously. To use this class just
	//		call the constructor with the required fields and then
	//		handle the response when it comes back. You can also
	//		subscribe to 'janey/data/Response' and listen for
	//		the responses that way too.
	_request:null,
	
	_action:null,
	
	_completeHandler:null,
	
	_topic:null,
	
	constructor:function(params) {
		// summary:
		//		send a request with the action (defined in _base), the
		//		the request, and a handler if you want to have a function
		//		called, if not just subscribe to 'janey/data/Response', or
		//		provide your own topic.
		//	action:<required:string>
		//	request:<required:object>
		//	oncomplete:<optional:function>
		//	topic:<optional:string>
		this._request = ( 'request' in params ? params.request : null );
		this._action = ( 'action' in params ? params.action : null );
		this._completeHandler = ( 'oncomplete' in params ? params.oncomplete : null );
		this._topic = ( 'topic' in params ? params.topic : null );
		
		var json = null;
		if ( this._action ) {
			json = dojo.toJson(dojo.mixin(this._request, {action:this._action}));
		} else {
			json = dojo.toJson(this._request);
		}
		this.post(json);
	},
	
	handleResponse:function(json) {
		// summary:
		//		handles the response from the server, do not override or
		//		call directly, just pass in a function or topic in the 
		//		constructor to get the response from the server
		var response = new janey.data.Response(this, json);
		if ( this._completeHandler ) {
			this._completeHandler(response);
		} else {
			if ( this._topic ) {
				dojo.publish(this._topic, [{response:response}]);
			} else {
				dojo.publish("janey/data/Response", [{response:response}]);
			}
		}
	}
});