dojo.provide("janey.data.Config");

dojo.require("janey._base");
dojo.require("janey.Ajax");

dojo.requireLocalization("janey", "Messages");

dojo.declare("janey.data.Config", janey.Ajax, {
	
	_messages:null,
	
	_oncompleteHandler:null,
	
	_config:null,
	
	constructor:function() {
		this._messages = dojo.i18n.getLocalization("janey", "Messages");
	},
	
	parseParams:function(params) {
		if ( params ) {
			this._oncompleteHandler = ( 'oncomplete' in params ? params.oncomplete : null );
		}
	},
	
	save:function(params) {
		this.parseParams(params);
		
		var json = dojo.toJson(dojo.mixin(params.data, {action:janey.actions.CREATE_CONFIG}));
		this.post(json);
	},
	
	restore:function(params) {
		this.parseParams(params);
		this._config = {save:true};
		
		var json = dojo.toJson({action:janey.actions.GET_CONFIG});
		this.post(json);
	},
	
	handleResponse:function(json) {
		if ( this._config && this._config.save ) {
			this._config = json;
		}
		if ( this._oncompleteHandler ) {
			this._oncompleteHandler(json);
		}
	},
	
	get:function() {
		return this._config;
	}
});