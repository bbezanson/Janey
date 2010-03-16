dojo.provide("janey.store.Comment");

dojo.require("janey.store._base");
dojo.require("janey.data.Request");

dojo.declare("janey.store.Comment", janey.store._base, {
	
	load:function(params) {
		var completeHandler = ('oncomplete' in params ? params.oncomplete : null);
		var self = this;
		var f = function(resp) {
			self.store = new dojo.data.ItemFileReadStore({data:resp.getJson()});
			if ( completeHandler ) {
				completeHandler(self.store);
			}
		};
		new janey.data.Request({
			request:{issue_id:params.issue_id},
			action:janey.actions.GET_ALL_COMMENTS,
			oncomplete:f
		});
	}
});