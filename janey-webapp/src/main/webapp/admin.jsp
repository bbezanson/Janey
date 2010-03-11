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
	dojo.require("dijit.Toolbar");
	dojo.require("dijit.layout.BorderContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.layout.AccordionContainer");
	dojo.require("dijit.layout.AccordionPane");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.Select");
	dojo.require("dijit.form.Textarea");
	dojo.require("dojo.data.ItemFileReadStore");
		
	dojo.registerModulePath("janey", "../janey");
	dojo.require("janey._base");
	dojo.require("janey.data.Request");
	
	function getUsers() {
		var f = function(resp) {
			var json = resp.getJson();
			if ( json && json.items ) {
				var data = {identifier:"value",label:"label",items:[]};
				dojo.forEach(json.items, function(user) {
					data.items.push({label:user.id, value:user.id});
				});
				var store = new dojo.data.ItemFileReadStore({data:dojo.clone(data)});
				dijit.byId("productowner").setStore(store);
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
			var json = resp.getJson();
			if ( json && json.items ) {
				var data = {identifier:"value",label:"label",items:[]};
				dojo.forEach(json.items, function(item){
					data.items.push({label:item.name, value:item.id});
				});
				var store = new dojo.data.ItemFileReadStore({data:dojo.clone(data)});
				dijit.byId("versionprod").setStore(store);
			}
		};
		new janey.data.Request({
			request:{},
			action:janey.actions.GET_ALL_PRODUCTS,
			oncomplete:f
		});
	}

	function createUser() {
		var req = new janey.data.Request({
			request:{
				id:dijit.byId("username").attr("value"),
				password:dijit.byId("password").attr("value"),
				email:dijit.byId("email").attr("value")
			},
			action:janey.actions.CREATE_USER
		});
	}

	function createProduct() {
		new janey.data.Request({
			request:{
				name:dijit.byId("productname").attr("value"),
				description:dijit.byId("productdesc").attr("value"),
				owner:dijit.byId("productowner").attr("value")
			},
			action:janey.actions.CREATE_PRODUCT
		});
	}

	function createVersion() {
		new janey.data.Request({
			request:{
				product_id:dijit.byId("versionprod").attr("value"),
				version:dijit.byId("version").attr("value")
			},
			action:janey.actions.CREATE_VERSION
		});
	}
	
	function response(json) {
		console.log("response:" + json);
	}
	
	function init() {
		console.log("Hello World");
		dijit.byId("usersPane").attr("title", "Janey Users");
		dojo.connect(dijit.byId("usersPane"), "onClick", dojo.hitch(null, "getUsers"));
		dojo.connect(dijit.byId("productsPane"), "onClick", dojo.hitch(null, "getProducts"));
		
		dojo.subscribe("janey/data/Response", null, "response");
		dojo.connect(dijit.byId("saveuser"), "onClick", dojo.hitch(null, "createUser"));
		dojo.connect(dijit.byId("saveprod"), "onClick", dojo.hitch(null, "createProduct"));
		dojo.connect(dijit.byId("savevers"), "onClick", null, "createVersion");
		getUsers();
		getProducts();
	}

	dojo.ready(init);
</script>
</head>
<body class="tundra">
<div dojoType="dijit.layout.BorderContainer" design="sidebar" gutters="true" liveSplitters="true" id="borderContainer">
    <div dojoType="dijit.layout.AccordionContainer" spliter="true" region="leading" style="width:20%;">
		<div dojoType="dijit.layout.AccordionPane" title="Users" id="usersPane">
			<div dojoType="dijit.Toolbar"></div>
		</div>
		<div dojoType="dijit.layout.AccordionPane" title="Products" id="productsPane"></div>
	</div>
    <div dojoType="dijit.layout.ContentPane" splitter="true" region="center">
        Starting admin page, create stuff willy nilly, no permission, no error checking...yet
        <h1>Define User (debug only - do this first!!)</h1>
        <label for="username">Username</label>
        <input type="text" id="username" dojoType="dijit.form.ValidationTextBox"/><br/>
        <label for="password">Password</label>
        <input type="password" id="password" dojoType="dijit.form.ValidationTextBox"/><br/>
        <label for="password">Email</label>
        <input type="text" id="email" dojoType="dijit.form.ValidationTextBox"/><br/>
        <button dojoType="dijit.form.Button" id="saveuser">Save</button>
        <h1>Define Product (debug only - do this second)</h1>
        <label for="productname">Name</label>
        <input type="text" id="productname" dojoType="dijit.form.ValidationTextBox"/><br/>
        <label for="productdesc">Description</label>
        <textarea dojoType="dijit.form.Textarea" id="productdesc"></textarea><br/>
        <label for="productowner">Owner</label>
        <select dojoType="dijit.form.Select" id="productowner"></select><br/>
        <button dojoType="dijit.form.Button" id="saveprod">Save</button>
        <h1>Define Version (debug only - do this last)</h1>
        <label for="versionprod">Product</label>
        <select dojoType="dijit.form.Select" id="versionprod"></select><br/>
		<label for="version">Version</label>
        <input type="text" id="version" dojoType="dijit.form.ValidationTextBox"/><br/>
        <button dojoType="dijit.form.Button" id="savevers">Save</button>
    </div>
</div>
<!-- 
	<div dojoType="dijit.layout.BorderContainer" gutters="true">
		<div dojoType="dijit.layout.ContentPane" region="top" splitter="false">
			Janey
		</div>
		<div dojoType="dijit.layout.BorderContainer" livesplitters="true" design="sidebar" region="center">
			<div dojoType="dijit.layout.AccordionContainer" spliter="true" region="leading" style="width:20%;">
				<div dojoType="dijit.layout.AccordionPane" title="Users"></div>
				<div dojoType="dijit.layout.AccordionPane" title="Products"></div>
			</div>
			<div dojoType="dijit.layout.ContentPane" region="center" id="canvas"></div>
		</div>
	</div>
-->
</body>
</html>