dojo.provide("janey.store.Version");

dojo.require("janey.store._base");
dojo.require("janey.data.Request");

dojo.declare("janey.store.Version", janey.store._base, {
	
	selectStore:null,
	
	load:function(params) {
		var completeHandler = ('oncomplete' in params ? params.oncomplete : null);
		var self = this;
		var f = function(resp) {
			self.store = new dojo.data.ItemFileReadStore({data:resp.getJson()});
			self.selectStore = self.createStore(resp.getJson(), "version", "version");
			if ( completeHandler ) {
				completeHandler(self.store);
			}
		};
		new janey.data.Request({
			request:{product_id:params.product_id},
			action:janey.actions.GET_ALL_VERSIONS,
			oncomplete:f
		});
	}
});