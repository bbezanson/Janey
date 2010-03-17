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
	@import "/janey/js/dojo/dojox/grid/resources/Grid.css";
	@import "/janey/js/dojo/dojox/grid/resources/tundraGrid.css";
	@import "/janey/css/janey.css";
	html, body { width: 100%; height: 100%; margin: 0; } #borderContainer
    { width: 100%; height: 100%; }
</style>
<script type="text/javascript" src="/janey/js/dojo/dojo/dojo.js<%=compress%>" djConfig="isDebug:<%=isDebug%>,parseOnLoad:true"></script>
<script type="text/javascript" src="/janey/js/dojo/dojo/janeydojo.js<%=compress%>"></script>
<script type="text/javascript"><!--
	dojo.require("dojo.parser");
	dojo.require("dijit.TitlePane");
	dojo.require("dijit.TooltipDialog");
	dojo.require("dijit.layout.BorderContainer");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.form.Select");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.CheckBox");
	dojo.require("dijit.form.Textarea");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dijit.form.Button");
	dojo.require("dojox.grid.DataGrid");
	
	dojo.registerModulePath("janey", "../janey");
	dojo.require("janey._base");
	dojo.require("janey.data.Request");
	dojo.require("janey.store.Comment");
	dojo.require("janey.store.Product");
	dojo.require("janey.store.User");
	dojo.require("janey.store.Version");
//	dojo.require("janey.data.Config");

//	var config = null;

//	function loadConfig(json) {
//		if ( config.get().length == 1 ) {
//			window.location = "configure.jsp";
//		}
//	}
	
	var stores = [];
	
	// ===== gettors =====

	function getIssues() {
		var f = function(resp) {
			stores.issue = new dojo.data.ItemFileReadStore({data:resp.getJson()}); 
			createGrid(stores.issue);
		};
		new janey.data.Request({
			request:{product_id:dijit.byId("products").attr("value")},
			action:janey.actions.GET_ALL_ISSUES,
			oncomplete:f
		});
	}

	function getComments(id) {
		var f =function(store) {
			stores.comment = store;
			createCommentGrid(store);
		};
		var s = new janey.store.Comment();
		s.load({oncomplete:createCommentGrid,issue_id:id});
	}
	
	function getProducts() {
		var s = new janey.store.Product();
		var f = function(store) {
			stores.fullproduct = store;
			stores.product = s.selectStore;
			createProductsGrid(store);
			dijit.byId("products").setStore(s.selectStore);
		};
		s.load({oncomplete:f});
	}

	function getVersions(id) {
		var s = new janey.store.Version();
		var f = function(store) {
			stores.version = s.selectStore;
			createVersionsGrid(store);
			dijit.byId("versions").setStore(s.selectStore);
		};
		if ( dojo.isString(id) && id.length === 0 ) {
			id = dijit.byId("products").attr("value");
		}
		s.load({oncomplete:f,product_id:id});
	}

	function getUsers() {
		var s = new janey.store.User();
		var f = function(store) {
			stores.user = s.selectStore;
			createUsersGrid(store);
			dijit.byId("reportedby").setStore(s.selectStore);
			dijit.byId("assignto").setStore(s.selectStore);
			dijit.byId("productowner").setStore(s.selectStore);
		}
		s.load({oncomplete:f});
	}
	
	// ===== creators =====
	
	function createIssue() {
		new janey.data.Request({
			request:{
				product_id:dijit.byId("products").attr("value"),
				status:dijit.byId("status").attr("value"),
				type:dijit.byId("type").attr("value"),
				severity:dijit.byId("severity").attr("value"),
				platform:dijit.byId("platform").attr("value"),
				title:dijit.byId("summary").attr("value"),
				description:dijit.byId("description").attr("value"),
				reported_by:dijit.byId("reportedby").attr("value"),
				reported_version:dijit.byId("versions").attr("value"),
				assigned_to:dijit.byId("assignto").attr("value")
			},
			action:janey.actions.CREATE_ISSUE,
			oncomplete:dojo.hitch(null, "getIssues")
		});
	}

	function createComment() {
		var issue_id = dojo.byId("issueid").innerHTML;
		new janey.data.Request({
			request:{
				issue_id:issue_id,
				type:dijit.byId("commenttype").attr("value"),
				comment:dijit.byId("comment").attr("value")
			}, 
			action:janey.actions.CREATE_COMMENT,
			oncomplete:dojo.hitch(null, "getComments", issue_id)
		});
	}

	function createProduct() {
		new janey.data.Request({
			request:{
				name:dijit.byId("productname").attr("value"),
				description:dijit.byId("productdesc").attr("value"),
				owner:dijit.byId("productowner").attr("value")
			},
			action:janey.actions.CREATE_PRODUCT,
			oncomplete:dojo.hitch(null, "getProducts")
		});
	}

	function createVersion() {
		var id = dojo.byId("productid").innerHTML;
		new janey.data.Request({
			request:{
				product_id:id,
				version:dijit.byId("version").attr("value")
			},
			action:janey.actions.CREATE_VERSION,
			oncomplete:dojo.hitch(null, "getVersions", id)
		});
	}

	function createUser() {
		var req = new janey.data.Request({
			request:{
				id:dijit.byId("username").attr("value"),
				password:dijit.byId("password").attr("value"),
				email:dijit.byId("email").attr("value"),
				active:dijit.byId("active").attr("value")
			},
			action:janey.actions.CREATE_USER,
			oncomplete:dojo.hitch(null, "getUsers")
		});
	}
	
	// ===== grids =====
	
	var issuesGrid = null;
	function createGrid(store) {
		if ( !issuesGrid ) {
			var getVal = function(store, id) {
				var val = "unknown";
				stores[store].fetch({onItem:function(item){
					if ( stores[store].getValue(item, "value") == id ) {
						val = stores[store].getValue(item, "label");
					}
				}});
				return val;
			}
			var getProduct = function(id) {
				return getVal("product", id);
			};
			var getStatus = function(id) {
				return getVal("status", id);
			};
			var getType = function(id) {
				return getVal("kind", id);
			};
			var getSeverity = function(id) {
				return getVal("severity", id);
			};
			var getPlatform = function(id) {
				return getVal("platform", id);
			};
			var shorten = function(s) {
				return s.length > 30 ? s.substring(0, 30) + "..." : s;
			};
			var layout = [
			            {field:"id",name:"ID",width:"50px"},
			      		{field:"product_id",name:"Product",width:"50px", formatter:getProduct},
			      		{field:"status",name:"Status",width:"50px",formatter:getStatus},
			      		{field:"type",name:"Type",width:"50px",formatter:getType},
			      		{field:"severity",name:"Severity",width:"50px",formatter:getSeverity},
			      		{field:"platform",name:"Platform",width:"50px",formatter:getPlatform},
			      		{field:"title",name:"Title",width:"200px"},
			      		{field:"description",name:"Description",width:"200px",formatter:shorten},
			      		{field:"reported_by",name:"Reported By",width:"100px"},
			      		{field:"reported_date",name:"Reported Date",width:"100px"},
			      		{field:"reported_version",name:"Reported Version",width:"100px"},
			      		{field:"assigned_to",name:"Assigned To",width:"100px"}
			      	//	{field:"resolved_by",name:"Resolved By",width:"100px"},
			      	//	{field:"resolved_date",name:"Resolved Date",width:"100px"},
			      	//	{field:"resolved_verison",name:"Resolved Version",width:"100px"}
			      	];
			issuesGrid = new dojox.grid.DataGrid({
	          	query:{id:'*'},
	          	structure:layout,
	          	store:store,
	          	rowsPerPage:20
	        }, 'gridNode');
	        
	      	issuesGrid.startup();
	        dojo.connect(issuesGrid, "onRowDblClick", function(){
	            var item = issuesGrid.selection.getSelected()[0];
	            dojo.byId("issueid").innerHTML = stores.issue.getValue(item, "id");
	            dojo.byId("issuetitle").innerHTML = stores.issue.getValue(item, "title");
	            dojo.byId("issuedescription").innerHTML = stores.issue.getValue(item, "description");
	            dijit.byId("commenttype").setStore(stores["commenttype"]);
	            dijit.byId("issuedialog").show();
	            getComments(stores.issue.getValue(item, "id"));
	        });
		} else {
			issuesGrid.setStore(store);
		}
	}

	var commentsGrid = null;
	function createCommentGrid(store) {
		if ( !commentsGrid ) {
			var getVal = function(store, id) {
				var val = "unknown";
				stores[store].fetch({onItem:function(item){
					if ( stores[store].getValue(item, "value") == id ) {
						val = stores[store].getValue(item, "label");
					}
				}});
				return val;
			}
			var getType = function(id) {
				return getVal("commenttype", id);
			}
			var layout = [
				       {field:"id",name:"ID",width:"50px"},
				       {field:"date",name:"Date",width:"100px"},
				       {field:"type",name:"Type",width:"100px",formatter:getType},
				       {field:"comment",name:"Comment",width:"400px"}
			];
			commentsGrid = new dojox.grid.DataGrid({
	          	query:{id:'*'},
	          	structure:layout,
	          	store:store,
	          	rowsPerPage:20
	        }, 'commentsGridNode');
	
			commentsGrid.startup();
		} else {
			commentsGrid.setStore(store);
		}
	}
	
	var productsGrid = null;
	function createProductsGrid(store) {
		if ( !productsGrid ) {
			var layout = [
			  			{field:"id",name:"ID",width:"50px"},
			  			{field:"owner",name:"Owner",width:"100px"},
			  			{field:"name",name:"Name",width:"200px"},
			  			{field:"description",name:"Description",width:"400px"}
			];
			productsGrid = new dojox.grid.DataGrid({
	          	query:{id:'*'},
	          	structure:layout,
	          	store:store,
	          	rowsPerPage:20
	        }, 'productsGridNode');
	        
			productsGrid.startup();
			dojo.connect(productsGrid, "onDblClick", function(){
				var item = productsGrid.selection.getSelected()[0];
				dojo.byId("productid").innerHTML = stores.fullproduct.getValue(item, "id");
				dojo.byId("productdialog_name").innerHTML = stores.fullproduct.getValue(item, "name");
				dijit.byId("productdialog").show();
				getVersions(stores.fullproduct.getValue(item, "id"));
			});
		} else {
			productsGrid.setStore(store);
		}
	}

	var versionsGrid = null;
	function createVersionsGrid(store) {
		if ( !versionsGrid ) {
			var layout = [{field:"version", name:"Version",width:"200px"}];
			versionsGrid = new dojox.grid.DataGrid({
	          	query:{version:'*'},
	          	structure:layout,
	          	store:store,
	          	rowsPerPage:20
	        }, 'versionsGridNode');
			versionsGrid.startup();
		} else {
			versionsGrid.setStore(store);
		}
	}

	var usersGrid = null;
	function createUsersGrid(store) {
		if ( !usersGrid ) {
			var layout = [
			              {field:"active",name:"Active",width:"50px"},
			              {field:"id",name:"ID",width:"200px"},
			              {field:"email",name:"Email",width:"200px"}
			];
			usersGrid = new dojox.grid.DataGrid({
	          	query:{id:'*'},
	          	structure:layout,
	          	store:store,
	          	rowsPerPage:20
	        }, 'usersGridNode');
	        usersGrid.startup();
		} else {
			usersGrid.setStore(store);
		}
	}
	
	function response(json) {
		console.log("response:" + json);
	}
	
	function init() {
	//	config = new janey.data.Config();
	//	config.restore({oncomplete:dojo.hitch(null, "loadConfig")});
		dojo.subscribe("janey/data/Response", null, "response");

		dojo.connect(dijit.byId("products"), "onChange", dojo.hitch(null, "getVersions", dijit.byId("products").attr("value")));
		stores["status"] = new dojo.data.ItemFileReadStore({id:"sstore",url:"json/status.txt"});
		stores["kind"] = new dojo.data.ItemFileReadStore({id:"kstore",url:"json/kind.txt"});
		stores["severity"] = new dojo.data.ItemFileReadStore({id:"kstore",url:"json/severity.txt"});
		stores["platform"] = new dojo.data.ItemFileReadStore({id:"pstore",url:"json/platforms.txt"});
		stores["commenttype"] = new dojo.data.ItemFileReadStore({id:"cstore",url:"json/commenttype.txt"});
		dijit.byId("status").setStore(stores["status"]);
		dijit.byId("type").setStore(stores["kind"]);
		dijit.byId("severity").setStore(stores["severity"]);
		dijit.byId("platform").setStore(stores["platform"]);

		getProducts();
		getIssues();
		getUsers();
		
		dojo.connect(dijit.byId("saveissue"), "onClick", null, "createIssue");
		dojo.connect(dijit.byId("savecomment"), "onClick", null, "createComment");
		dojo.connect(dijit.byId("closeissuedialog"), "onClick", function(){dijit.byId("issuedialog").hide();});
		// admin stuff
		dojo.connect(dijit.byId("saveprod"), "onClick", null, "createProduct");
		dojo.connect(dijit.byId("savevers"), "onClick", null, "createVersion");
		dojo.connect(dijit.byId("saveuser"), "onClick", null, "createUser");
	}

	dojo.ready(init);
--></script>
</head>
<body class="tundra">
<div dojoType="dijit.layout.BorderContainer" design="headline" gutters="true" liveSplitters="true" id="borderContainer">
	<div dojoType="dijit.layout.ContentPane" region="top">
		<h1>Janey - Simple Issue Management</h1><span style="float:right;"><a href="admin.jsp">Administration</a></span>
	</div>
	<div dojoType="dijit.layout.TabContainer" style="width: 100%; height: 100%;" region="center">
		<div dojoType="dijit.layout.ContentPane" title="Home">
			Double click an issue to open it up and view/add comments!
			<div dojoType="dijit.form.DropDownButton" style="float:right;clear:both;">
				<span>New Issue</span>
				<div dojoType="dijit.TooltipDialog">
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
			        <button dojoType="dijit.form.Button" type="submit" id="saveissue">Save</button>
				</div>
			</div>
			<div class="gridContainer"><div id="gridNode"></div></div>
		</div>
		<div dojoType="dijit.layout.ContentPane" title="Search">Coming Soon</div>
		<div dojoType="dijit.layout.ContentPane" title="Products">
			Double click a product to view/add versions!
			<div dojoType="dijit.form.DropDownButton" style="float:right;clear:both;">
				<span>New Product</span>
				<div dojoType="dijit.TooltipDialog">
					<label for="productname">Name</label>
		        	<input type="text" id="productname" dojoType="dijit.form.ValidationTextBox"/><br/>
				    <label for="productdesc">Description</label>
			        <textarea dojoType="dijit.form.Textarea" id="productdesc"></textarea><br/>
			        <label for="productowner">Owner</label>
			        <select dojoType="dijit.form.Select" id="productowner"></select><br/>
			        <button dojoType="dijit.form.Button" type="submit" id="saveprod">Save</button>
		        </div>
			</div>
			<div class="gridContainer"><div id="productsGridNode"></div></div>
		</div>
		<div dojoType="dijit.layout.ContentPane" title="Users">
			<div dojoType="dijit.form.DropDownButton" style="float:right;clear:both;">
				<span>New User</span>
				<div dojoType="dijit.TooltipDialog">
					<label for="username">Username</label>
	        		<input type="text" id="username" dojoType="dijit.form.ValidationTextBox" trim="true"/><br/>
	        		<label for="password">Password</label>
	       			<input type="password" id="password" dojoType="dijit.form.ValidationTextBox" trim="true"/><br/>
	        		<label for="password">Email</label>
	        		<input type="text" id="email" dojoType="dijit.form.ValidationTextBox" trim="true" regExp=".*@.*"/><br/>
	        		<input type="checkbox" id="active" dojoType="dijit.form.CheckBox"/>
	        		<label for="active">Activate</label><br/>
	        		<button dojoType="dijit.form.Button" type="submit" id="saveuser">Save</button>
				 </div>
			</div>
			<div class="gridContainer"><div id="usersGridNode"></div></div>
		</div>
		<div dojoType="dijit.layout.ContentPane" title="Misc">
			<h1>Misc/Starting Points</h1>
			<a href="/janey/js/dojo/util/doh/runner.html">Dojo Tests</a><br>
			<a href="/janey/js/dojo/dijit/themes/themeTester.html">Dijit Themes Tester</a><br/>
			<a href="/janey/configure.jsp">Config Page</a><br/>
			<p>First you need a database, currently janey has only been tested with
			postgresql, but the goal is to also support hsqldb for quick installation. Use the 
			setup script in the project root etc directory. After that is done, use the link 
			above to the <em>config</em> page and set up the config. Currently only the config for the
			db is looked at, we will add email support and insert the admin user soon. After that is
			done you might need to restart the server. This has only been tested on tomcat so far, but
			we plan to also support jetty. At this point you should come back here and use this
			page to create users, a project, and versions for the project. When that is done you can
			come back here and create issues (bugs) and comments for issues. There is currently no
			type checking on the fields, and there is still a lot to do with adding in searching for
			issues and displaying them, logging in users, giving users a home page, etc...</p>
			
		</div>
	</div>
	<div dojoType="dijit.layout.ContentPane" region="bottom">
    	<span style="float:right;font-weight:bold;">Janey</span>
    </div>
</div>

<div dojoType="dijit.Dialog" title="Manage Issue" id="issuedialog" style="width:70%;height:70%;">
	<h1>Issue:<span id="issueid"></span> - <span id="issuetitle"></span></h1>
	<label for="issuedescription"><font style="font-weight:bold;">Description:</font></label><span id="issuedescription"></span>
	<div dojoType="dijit.form.DropDownButton" style="float:right;clear:both;">
		<span>New Comment</span>
		<div dojoType="dijit.TooltipDialog" id="newcommentdialog">
			<label for="commenttype">Type</label>
			<select dojoType="dijit.form.Select" id="commenttype"></select><br/>
		    <textarea dojoType="dijit.form.Textarea" id="comment"></textarea><br/>
			<button dojoType="dijit.form.Button" type="submit" id="savecomment">Save</button>
		</div>
	</div>
	<div class="gridContainer"><div id="commentsGridNode" style="width:400px;height:300px;"></div></div>
</div>

<div dojoType="dijit.Dialog" title="Manage Product" id="productdialog" style="width:25%;height:70%;">
	<h1>Product:<span id="productid"></span> - <span id="productdialog_name"></span></h1>
	<div dojoType="dijit.form.DropDownButton" style="float:right;clear:both;">
		<span>New Version</span>
		<div dojoType="dijit.TooltipDialog">
			<label for="version">Version</label>
	        <input type="text" id="version" dojoType="dijit.form.ValidationTextBox"/><br/>
	        <button dojoType="dijit.form.Button" type="submit" id="savevers">Save</button>
        </div>
	</div>
	<div class="gridContainer"><div id="versionsGridNode" style="width:200px;height:300px;"></div></div>
</div>

</body>
</html>