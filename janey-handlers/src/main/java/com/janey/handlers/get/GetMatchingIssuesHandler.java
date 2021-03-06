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

public class GetMatchingIssuesHandler extends BaseHandler implements Handler {

	public GetMatchingIssuesHandler() {
		super(Actions.GET_ALL_ISSUES);
		// TODO Auto-generated constructor stub
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		List<Issue> issues = daoManager.getIssueManager().search();
		
		if ( issues != null ) {
			out.object();
			out.array();
			for ( Issue issue : issues ) {
				issue.toJson(out);
			}
			out.endArray();
			out.endObject();
		}
	}

}
