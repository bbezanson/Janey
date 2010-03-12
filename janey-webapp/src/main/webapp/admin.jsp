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
	@import "/janey/css/janey.css";
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

	function loadPane(pane) {
		dijit.byId("stackContainer").selectChild(dijit.byId(pane));
	}
	
	function response(json) {
		console.log("response:" + json);
	}
	
	function init() {
		console.log("Hello World");
		dijit.byId("usersPane").attr("title", "Janey Users");
		dojo.connect(dijit.byId("newUserButton"), "onClick", dojo.hitch(null, "loadPane", "userinfo"));
		dojo.connect(dijit.byId("newProductButton"), "onClick", dojo.hitch(null, "loadPane", "prodinfo"));
		dojo.connect(dijit.byId("newVersionButton"), "onClick", dojo.hitch(null, "loadPane", "versinfo"));
		
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
<div dojoType="dijit.layout.BorderContainer" design="headline" gutters="true" liveSplitters="true" id="borderContainer">
	<div dojoType="dijit.layout.ContentPane" region="top">
		<h1>Administration</h1><span style="float:right;"><a href="index.jsp">Home</a></span>
	</div>
    <div dojoType="dijit.layout.AccordionContainer" spliter="true" region="leading" style="width:20%;">
		<div dojoType="dijit.layout.AccordionPane" title="Users" id="usersPane">
			<button dojoType="dijit.form.Button" id="newUserButton">New User</button>
		</div>
		<div dojoType="dijit.layout.AccordionPane" title="Products" id="productsPane">
			<button dojoType="dijit.form.Button" id="newProductButton">New Product</button>
		</div>
		<div dojoType="dijit.layout.AccordionPane" title="Product Versions" id="versionsPane">
			<button dojoType="dijit.form.Button" id="newVersionButton">New Version</button>
		</div>
	</div>
   	<div dojoType="dijit.layout.StackContainer" id="stackContainer" splitter="true" region="center">
   		<div dojoType="dijit.layout.ContentPane" id="userinfo" title="User Information">
	        <div class="optionheading">Define User (debug only - do this first!!)</div>
	        <div class="optiontable"><table>
	        	<tr>
	        		<td><label for="username">Username</label></td>
	        		<td><input type="text" id="username" dojoType="dijit.form.ValidationTextBox" trim="true"/></td>
	        	</tr><tr>
	        		<td><label for="password">Password</label></td>
	       			<td><input type="password" id="password" dojoType="dijit.form.ValidationTextBox" trim="true"/></td>
	       		</tr><tr>
	        		<td><label for="password">Email</label></td>
	        		<td><input type="text" id="email" dojoType="dijit.form.ValidationTextBox" trim="true" regExp=".*@.*"/></td>
	        	</tr><tr>
	        		<td colspan="2" class="right"><button dojoType="dijit.form.Button" id="saveuser">Save</button></td>
	        	</tr>
	        </table></div>
        </div>
        <div dojoType="dijit.layout.ContentPane" id="prodinfo" title="Product Information">
	        <div class="optionheading">Define Product (debug only - do this second)</div>
	        <div class="optiontable"><table>
	        	<tr>
	        		<td><label for="productname">Name</label></td>
	        		<td><input type="text" id="productname" dojoType="dijit.form.ValidationTextBox"/></td>
	        	</tr><tr>
			        <td><label for="productdesc">Description</label></td>
			        <td><textarea dojoType="dijit.form.Textarea" id="productdesc"></textarea></td>
			    </tr><tr>
			        <td><label for="productowner">Owner</label></td>
			        <td><select dojoType="dijit.form.Select" id="productowner"></select></td>
			    </tr><tr>
			        <td colspan="2" class="right"><button dojoType="dijit.form.Button" id="saveprod">Save</button></td>
	        	</tr>
	        </table></div>
        </div>
        <div dojoType="dijit.layout.ContentPane" id="versinfo" title="Version Information">
	        <div class="optionheading">Define Version (debug only - do this last)</div>
	        <div class="optiontable"><table>
	        	<tr>
	        		<td><label for="versionprod">Product</label></td>
			        <td><select dojoType="dijit.form.Select" id="versionprod"></select></td>
			    </tr><tr>
					<td><label for="version">Version</label></td>
			        <td><input type="text" id="version" dojoType="dijit.form.ValidationTextBox"/></td>
			    </tr><tr>
			        <td colspan="2" class="right"><button dojoType="dijit.form.Button" id="savevers">Save</button></td>
			    </tr>
	        </table></div>
        </div>
    </div>
    <div dojoType="dijit.layout.ContentPane" region="bottom">
    	<span style="float:right;font-weight:bold;">Janey</span>
    </div>
</div>
</body>
</html>