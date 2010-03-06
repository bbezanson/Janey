package com.janey.handlers.get;

import java.sql.SQLException;

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

public class GetIssueHandler extends BaseHandler implements Handler {

	public GetIssueHandler() {
		super(Actions.GET_ISSUE);
		// TODO Auto-generated constructor stub
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		long id = json.getLong("id");
		Issue issue = daoManager.getIssueManager().get(id);
		issue.toJson(out);
	}

}
