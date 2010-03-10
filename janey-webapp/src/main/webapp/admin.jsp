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
	dojo.require("diji.Toolbar");
	dojo.require("dijit.layout.BorderContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.layout.AccordionContainer");
	dojo.require("dijit.layout.AccordionPane");
	
	dojo.registerModulePath("janey", "../janey");

	function getUsers() {
		console.log("getting users...");
	}

	function getProducts() {
		console.log("getting products...");
	}
	
	function init() {
		console.log("Hello World");
		dijit.byId("usersPane").attr("title", "Janey Users");
		dojo.connect(dijit.byId("usersPane"), "onClick", dojo.hitch(null, "getUsers"));

		dojo.connect(dijit.byId("productsPane"), "onClick", dojo.hitch(null, "getProducts"));
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
        Hi, I'm center
        <input type="text" id="productname" dojoType="dijit.form.ValidationTextBox"/><br/>
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