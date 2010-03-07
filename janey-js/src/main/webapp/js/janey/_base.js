dojo.provide("janey._base");

(function(){
	janey.actions = {
			CREATE_VERSION:"create_version",
			DELETE_VERSION:"delete_version",
			UPDATE_VERSION:"update_version",
			GET_ALL_VERSIONS:"get_all_versions",
	
			CREATE_ISSUE:"create_issue",
			DELETE_ISSUE:"delete_issue",
			UPDATE_ISSUE:"update_issue",
			GET_ISSUE:"get_issue",
			GET_ALL_ISSUES:"get_all_issues",
			GET_MATCHING_ISSUES:"get_matching_issues",
	
			CREATE_COMMENT:"create_comment",
			DELETE_COMMENT:"delete_comment",
			UPDATE_COMMENT:"update_comment",
			GET_ALL_COMMENTS:"get_all_comments",
	
			CREATE_PRODUCT:"create_product",
			DELETE_PRODUCT:"delete_product",
			UPDATE_PRODUCT:"update_product",
			GET_PRODUCT:"get_product",
			GET_ALL_PRODUCTS:"get_all_products",
			
			CREATE_CONFIG:"create_config",
			GET_CONFIG:"get_config"
	};
	
	janey.alert = function(msg, type) {
		// possible types {"message","warning","error","fatal"}
		console.log("[" + type + "]:" + msg);
		dijit.byId("alert").setContent(msg,type);
		dijit.byId("alert").show();
	};
})();