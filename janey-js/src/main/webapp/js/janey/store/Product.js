dojo.provide("janey.store.Product");

dojo.require("janey.store._base");
dojo.require("janey.data.Request");

dojo.declare("janey.store.Product", janey.store._base, {
	
	store:null,
	
	selectStore:null,
		
	load:function(params) {
		var completeHandler = ('oncomplete' in params ? params.oncomplete : null);
		var self = this;
		var f = function(resp) {
			self.store = new dojo.data.ItemFileReadStore({data:resp.getJson()});
			self.selectStore = self.createStore(resp.getJson(), "name", "id");
			if ( completeHandler ) {
				completeHandler(self.store);
			}
		};
		new janey.data.Request({
			request:{},
			action:janey.actions.GET_ALL_PRODUCTS,
			oncomplete:f
		});
	}
	
});