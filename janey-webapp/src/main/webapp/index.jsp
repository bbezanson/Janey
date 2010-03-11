<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String isDebug = request.getParameter("isDebug");
	String compress = "";
	if ( isDebug == null || isDebug.equals("") ) {
		isDebug = "false";
	}
	if ( isDebug.equals("true") ) {
		compress = ".uncompressed.js";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Janey - Easy Issue Tracking</title>
<style type='text/css'>
	@import "/janey/js/dojo/dijit/themes/tundra/tundra.css";
	@import "/janey/js/dojo/dojo/resources/dojo.css";
	html, body { width: 100%; height: 100%; margin: 0; } #borderContainer
    { width: 100%; height: 100%; }
</style>
<script type="text/javascript" src="/janey/js/dojo/dojo/dojo.js<%=compress%>" djConfig="isDebug:<%=isDebug%>,parseOnLoad:true"></script>
<script type="text/javascript" src="/janey/js/dojo/dojo/janeydojo.js<%=compress%>"></script>
<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.form.Select");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.Textarea");
	dojo.require("dijit.form.ValidationTextBox");
	
	dojo.registerModulePath("janey", "../janey");
	dojo.require("janey._base");
	dojo.require("janey.data.Request");
//	dojo.require("janey.data.Config");

//	var config = null;

//	function loadConfig(json) {
//		if ( config.get().length == 1 ) {
//			window.location = "configure.jsp";
//		}
//	}

	function createStore(json, label, value) {
		// summary:
		//		create a store for a select from returned item list data
		var store = null;
		if ( json && json.items ) {
			var data = {identifier:"value",label:"label",items:[]};
			dojo.forEach(json.items, function(item) {
				data.items.push({label:item[label], value:item[value]});
			});
			store = new dojo.data.ItemFileReadStore({data:dojo.clone(data)});
		}
		return store;
	}
	
	function getUsers() {
		var f = function(resp) {
			var store = createStore(resp.getJson(), "id", "id" );
			if ( store ) {
				dijit.byId("reportedby").setStore(store);
				dijit.byId("assignto").setStore(store);
			}
		}
		new janey.data.Request({
			request:{},
			action:janey.actions.GET_ALL_USERS,
			oncomplete:f
		});
	}

	function getProducts() {
		var f = function(resp) {
			var store = createStore(resp.getJson(), "name", "id");
			if ( store ) {
				dijit.byId("products").setStore(store);
			}
			getVersions();
		};
		new janey.data.Request({
			request:{},
			action:janey.actions.GET_ALL_PRODUCTS,
			oncomplete:f
		});
	}

	function getVersions() {
		var f = function(resp) {
			var store = createStore(resp.getJson(), "version", "version");
			if ( store ) {
				dijit.byId("versions").setStore(store);
			}
		};
		new janey.data.Request({
			request:{product_id:dijit.byId("products").attr("value")},
			action:janey.actions.GET_ALL_VERSIONS,
			oncomplete:f
		});
	}

	var stores = [];
	
	function init() {
	//	config = new janey.data.Config();
	//	config.restore({oncomplete:dojo.hitch(null, "loadConfig")});

		dojo.connect(dijit.byId("products"), "onChange", null, "getVersions");
		stores["status"] = new dojo.data.ItemFileReadStore({id:"sstore",url:"json/status.txt"});
		stores["kind"] = new dojo.data.ItemFileReadStore({id:"kstore",url:"json/kind.txt"});
		stores["severity"] = new dojo.data.ItemFileReadStore({id:"kstore",url:"json/severity.txt"});
		stores["platform"] = new dojo.data.ItemFileReadStore({id:"pstore",url:"json/platforms.txt"});
		dijit.byId("status").setStore(stores["status"]);
		dijit.byId("type").setStore(stores["kind"]);
		dijit.byId("severity").setStore(stores["severity"]);
		dijit.byId("platform").setStore(stores["platform"]);
		getProducts();
		getUsers();
	}

	dojo.ready(init);
</script>
</head>
<body class="tundra">
<div dojoType="dijit.layout.TabContainer" style="width: 100%; height: 100%;">
	<div dojoType="dijit.layout.ContentPane" title="New Issue">
		<h1>Create New Issue</h1>
		<label for="products">Product</label>
		<select dojoType="dijit.form.Select" id="products"></select><br/>
		<label for="versions">Version</label>
		<select dojoType="dijit.form.Select" id="versions"></select><br/>
		<label for="status">Status</label>
		<select dojoType="dijit.form.Select" id="status"></select><br/>
		<label for="type">Type</label>
		<select dojoType="dijit.form.Select" id="type"></select><br/>
		<label for="severity">Severity</label>
		<select dojoType="dijit.form.Select" id="severity"></select><br/>
		<label for="platform">Platform</label>
		<select dojoType="dijit.form.Select" id="platform"></select><br/>
		<label for="summary">Summary</label>
        <input type="text" id="summary" dojoType="dijit.form.ValidationTextBox"/><br/>
        <label for="description">Description</label>
        <textarea dojoType="dijit.form.Textarea" id="description"></textarea><br/>
        <label for="description">Reported By</label>
        <select dojoType="dijit.form.Select" id="reportedby"></select><br/>
        <label for="description">Assign To</label>
        <select dojoType="dijit.form.Select" id="assignto"></select><br/>
	</div>
	<div dojoType="dijit.layout.ContentPane" title="Misc">
		<a href="/janey/js/dojo/util/doh/runner.html">Dojo Tests</a><br>
		<a href="/janey/js/dojo/dijit/themes/themeTester.html">Dijit Themes Tester</a><br/>
		<a href="/janey/admin.jsp">Admin Page</a><br/>
		<a href="/janey/configure.jsp">Config Page</a>
	</div>
</div>
</body>
</html>