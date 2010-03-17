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
<title>Janey Configuration</title>
<style type='text/css'>
	@import "/janey/js/dojo/dijit/themes/tundra/tundra.css";
	@import "/janey/js/dojo/dojo/resources/dojo.css";
	@import "/janey/js/dojo/dojox/widget/Toaster/Toaster.css";
	html, body { width: 100%; height: 100%; margin: 0; } #borderContainer
    { width: 100%; height: 100%; }
</style>
<script type="text/javascript" src="/janey/js/dojo/dojo/dojo.js<%=compress%>" djConfig="isDebug:<%=isDebug%>,parseOnLoad:true"></script>
<script type="text/javascript" src="/janey/js/dojo/dojo/janeydojo.js<%=compress%>"></script>
<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dojox.widget.Toaster");
	
	dojo.registerModulePath("janey", "../janey");
	dojo.require("janey.data.Config");

	var config = null;

	function saveComplete(json) {
		if ( json && json.status && json.status == "0" ) {
			window.location = "index.jsp";
		}
	}
	
	function save() {
		var props = {
			"com.janey.db.driver":dijit.byId("dbdriver").attr("value"),
			"com.janey.db.url":dijit.byId("dburl").attr("value"),
			"com.janey.db.name":dijit.byId("dbname").attr("value"),
			"com.janey.db.username":dijit.byId("dbusername").attr("value"),
			"com.janey.db.password":dijit.byId("dbpassword").attr("value"),

			"com.janey.mail.server":dijit.byId("mailserver").attr("value"),
			"com.janey.mail.port":dijit.byId("mailport").attr("value"),
			"com.janey.mail.sender":dijit.byId("mailsender").attr("value"),
			"com.janey.mail.password":dijit.byId("mailpassword").attr("value"),

			"com.janey.security.type":dijit.byId("securitytype").attr("value"),
			"com.janey.security.admin.username":dijit.byId("securityadminusername").attr("value"),
			"com.janey.security.admin.password":dijit.byId("securityadminpassword").attr("value")
		};
		config.save({data:props,oncomplete:dojo.hitch(null, "saveComplete")});
	}

	function restore(json) {
		dojo.forEach(json.config, function(obj){
			switch(obj.key) {
			case "com.janey.db.driver":dijit.byId("dbdriver").attr("value", obj.val);break;
			case "com.janey.db.url":dijit.byId("dburl").attr("value", obj.val);break;
			case "com.janey.db.name":dijit.byId("dbname").attr("value", obj.val);break;
			case "com.janey.db.username":dijit.byId("dbusername").attr("value", obj.val);break;
			case "com.janey.db.password":dijit.byId("dbpassword").attr("value", obj.val);break;

			case "com.janey.mail.server":dijit.byId("mailserver").attr("value", obj.val);break;
			case "com.janey.mail.port":dijit.byId("mailport").attr("value", obj.val);break;
			case "com.janey.mail.sender":dijit.byId("mailsender").attr("value", obj.val);break;
			case "com.janey.mail.password":dijit.byId("mailpassword").attr("value", obj.val);break;

			case "com.janey.security.type":dijit.byId("securitytype").attr("value", obj.val); break;
			case "com.janey.security.admin.username":dijit.byId("securityadminusername").attr("value", obj.val);break;
			case "com.janey.security.admin.password":dijit.byId("securityadminpassword").attr("value", obj.val);break;
			default:
				console.log("unhandled key:" + obj.key + ":" + obj.val);
				break;
			}
		});
	}
	
	function setDbUrl() {
		var dbdriver = dijit.byId("dbdriver").attr("value"); 
		if ( dbdriver === "org.postgresql.Driver" ) {
			dijit.byId("dburl").attr("value", "jdbc:postgresql://localhost:5432/");
		} else if ( dbdriver === "org.hsqldb.jdbc.JDBCDriver" ) {
			dijit.byId("dburl").attr("value", "jdbc:hsqldb:file:/path/to/dir/;shutdown=true");
		}
	}
	
	function init() {
		config = new janey.data.Config();
		config.restore({oncomplete:dojo.hitch(null, "restore")});
		dojo.connect(dijit.byId("dbdriver"), "onChange", null, "setDbUrl");
		dojo.connect(dijit.byId("saveButton"), "onClick", null, "save");
		setDbUrl();
	}

	dojo.ready(init);
</script>
</head>
<body class="tundra">
	<div dojoType='dojox.widget.Toaster' id='alert' positionDirection='lr-left'></div>
	<div dojoType="dijit.layout.ContentPane" title="Database">
		<label for="dbdriver">Database Driver</label>
		<select id="dbdriver" dojoType="dijit.form.FilteringSelect">
			<option value="org.postgresql.Driver">PostgreSQL</option>
			<option value="org.hsqldb.jdbc.JDBCDriver">HSQLDB</option>
		</select><br/>
		<label for="dburl">Database URL</label>
		<input type="text" id="dburl" dojoType="dijit.form.ValidationTextBox"/><br/>
		<label for="name">Database Name</label>
		<input type="text" id="dbname" value="janey" dojoType="dijit.form.ValidationTextBox"/><br/>
		<label for="dbusername">Database Username</label>
		<input type="text" id="dbusername" value="SA" dojoType="dijit.form.ValidationTextBox"/><br/>
		<label for="dbpassword">Database Password</label>
		<input type="password" id="dbpassword" dojoType="dijit.form.ValidationTextBox"/><br/>
	</div>
	<div dojoType="dijit.layout.ContentPane" title="Mail">
		<label for="mailserver">Mail Server</label>
		<input type="text" id="mailserver" value="smtp.server.com" dojoType="dijit.form.ValidationTextBox"/><br/>
		<label for="mailport">Mail Port</label>
		<input type="text" id="mailport" value="25" dojoType="dijit.form.ValidationTextBox"/><br/>
		<label for="mailsender">Mail Sender</label>
		<input type="text" id="mailsender" dojoType="dijit.form.ValidationTextBox"/><br/>
		<label for="mailpassword">Mail Password</label>
		<input type="password" id="mailpassword" dojoType="dijit.form.ValidationTextBox"/><br/>
	</div>
	<div dojoType="dijit.layout.ContentPane" title="Security">
		<label for="securitytype">Security Type</label>
		<select id="securitytype" dojoType="dijit.form.FilteringSelect">
			<option value="standard">Built In</option>
			<option value="ldap">LDAP</option>
		</select><br/>
		<label for="securityadminusername">Administrator Username</label>
		<input type="text" id="securityadminusername" dojoType="dijit.form.ValidationTextBox"/><br/>
		<label for="securityadminpassword">Administrator Password</label>
		<input type="password" id="securityadminpassword" dojoType="dijit.form.ValidationTextBox"/><br/>
	</div>
	<button id="saveButton" dojoType="dijit.form.Button">Save</button>
</body>
</html>