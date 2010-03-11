package com.janey.handlers.get;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.core.objects.Issue;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseHandler;
import com.janey.handlers.Handler;

public class GetAllIssuesHandler extends BaseHandler implements Handler {
	public GetAllIssuesHandler() {
		super(Actions.GET_ALL_ISSUES);
	}
	
	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO make this get by product id
		List<Issue> issues = daoManager.getIssueManager().getAll();
		
		if ( issues != null ) {
			out.object();
			out.key("items");
			out.array();
			for ( Issue issue : issues ) {
				issue.toJson(out);
			}
			out.endArray();
			out.endObject();
		}
	}

}
