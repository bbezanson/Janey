dojo.provide("janey.store._base");

dojo.require("dojo.number");

dojo.declare("janey.store._base", null, {
	
	store:null,
	
	constructor:function() {
	},
	
	createStore:function(json, label, value) {
		// summary:
		//		create a store for a select from returned item list data
		var store = null;
		if ( json && json.items ) {
			var count = 0;
			var data = {identifier:"value",label:"label",items:[]};
			dojo.forEach(json.items, function(item) {
				var val = item[value];
				if ( !dojo.isString(val) ) {
					var val = dojo.number.format(item[value]);
				}
				data.items[count++] = {label:item[label], value:val};
			});
			store = new dojo.data.ItemFileReadStore({data:data});
		}
		return store;
	}
});